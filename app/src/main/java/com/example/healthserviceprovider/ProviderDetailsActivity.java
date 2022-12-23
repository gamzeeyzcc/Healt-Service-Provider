package com.example.healthserviceprovider;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class ProviderDetailsActivity extends AppCompatActivity {
    public Database db;
    public User user;
    public int userId, providerId;
    String sender;
    Button btnSendMessage,btnSelectDate;
    Button btnSeeOnMap, backButton, createAppointment;
    TextView providerName, profession;
    ArrayList<Appointment> appointments;
    private ListView appointmentsListView;
    private Provider provider;
    private AppointmentAdapter adapter;
    DatePickerDialog datePickerDialog;
    String date = "INVALID";

        //Bir provider'a ait detayları içerir
    //Tarih seçimi esnasında kullanıcı bugünden küçük tarih girerse tarih geçersiz olarak işaretlenir
    //Eğer tarih geçerliyse, create appointment düğmesi veritabanına yeni randevu kaydeder
    //Diğer düğmeler ise diğer activitylere yönlendirir
    //Her bir işlem bu provider'ın ID'si ile yapılır
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(ProviderDetailsActivity.this);
        userId = getIntent().getExtras().getInt("userId");
        providerId = getIntent().getExtras().getInt("providerId");
        sender = getIntent().getExtras().getString("sender");
        user = db.GetUser(userId);
        provider = db.GetProvider(providerId);
        setContentView(R.layout.activity_provider_details);
        backButton = (Button) findViewById(R.id.btnBackFromProvider);
        btnSendMessage = (Button) findViewById(R.id.btnSendMessageToProvider);
        btnSelectDate = (Button) findViewById(R.id.btnSelectDate);
        createAppointment = (Button) findViewById(R.id.btnCreateAppointment);
        btnSeeOnMap = (Button) findViewById(R.id.buttonSeeOnMap);
        providerName = (TextView) findViewById(R.id.providerdetailsName);
        profession = (TextView) findViewById(R.id.providerdetailsProfession);

        providerName.setText("Name: " + provider.getName());
        profession.setText("Profession: " + provider.getProfession());

        appointmentsListView = (ListView) findViewById(R.id.listviewAppointmentstoProvider);
        appointments = db.GetAppointmentsOfPatientAndProvider(userId, providerId);
        adapter = new AppointmentAdapter(ProviderDetailsActivity.this, R.layout.appointmentrow, appointments);
        appointmentsListView.setAdapter(adapter);

        btnSeeOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderDetailsActivity.this, MapsActivity.class);
                intent.putExtra("marker", (provider.getName() + "-" + provider.getProfession()));
                intent.putExtra("lat", provider.getLat());
                intent.putExtra("lng", provider.getLng());
                startActivity(intent);
            }
        });

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderDetailsActivity.this, MessageActivity.class);
                intent.putExtra("providerId", providerId);
                intent.putExtra("userId", userId);
                intent.putExtra("sender", "User");
                startActivity(intent);
            }
        });

        datePickerDialog = new DatePickerDialog(ProviderDetailsActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                    }
                }, 0, 0, 0);
        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar;
                int year, month, dayOfMonth;
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(ProviderDetailsActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                date = (year + "-" + (month<10?"0":"")+month + "-" + (day<10?"0":"")+day);
                            }
                        }, year, month+1, dayOfMonth);
                datePickerDialog.show();
            }
        });

        createAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date = db.getText("SELECT CASE WHEN '"+date+"' < date('now') THEN 'INVALID' WHEN "+date+"='INVALID' THEN 'INVALID' ELSE '"+date+"' END;");
                if (date.equals("INVALID")) {
                    Toast.makeText(ProviderDetailsActivity.this, "Invalid date selected.", Toast.LENGTH_LONG).show();
                } else {
                    db.RunQuery("INSERT INTO appointment(userid,providerid,date) VALUES(" + userId + "," + providerId + ",'" + date + "')");
                    Toast.makeText(ProviderDetailsActivity.this, "Appointment successfully created!.", Toast.LENGTH_LONG).show();
                    appointments = db.GetAppointmentsOfPatientAndProvider(userId, providerId);
                    adapter = new AppointmentAdapter(ProviderDetailsActivity.this, R.layout.appointmentrow, appointments);
                    appointmentsListView.setAdapter(adapter);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}