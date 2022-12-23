package com.example.healthserviceprovider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthserviceprovider.ui.appointment.AppointmentsViewModel;

import java.util.ArrayList;

public class PatientAppointmentsActivity extends AppCompatActivity {
    public Database db;
    public User user;
    public int userId;

    private AppointmentsViewModel appointmentsViewModel;
    ArrayList<Appointment> appointments;
    private ListView appointmentsListView;
    private Provider provider;
    private AppointmentAdapter adapter;
    private Button backButton;
    //Hasta'nın tüm randevularının listelendiği sınıftır
    //Bİr randevu bu sayfadan iptal edilebilir
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(PatientAppointmentsActivity.this);
        userId = getIntent().getExtras().getInt("userId");
        user = db.GetUser(userId);
        setContentView(R.layout.activity_patient_appointments);
        appointmentsListView = (ListView) findViewById(R.id.listviewPatientAppointments);
        backButton = (Button) findViewById(R.id.buttonBackPatient);
        appointments = db.GetAppointmentsOfPatient(userId);
        adapter = new AppointmentAdapter(PatientAppointmentsActivity.this, R.layout.appointmentrow, appointments);
        appointmentsListView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        for (Appointment ap:appointments){
            if (ap.getStatus()==0){
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientAppointmentsActivity.this);
                builder.setTitle("Warning!");
                builder.setMessage("You have an appointment at "+ap.getProviderName()+" today!");
                builder.setNegativeButton("Cancel", null);
                builder.setPositiveButton("See Provider",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(PatientAppointmentsActivity.this, ProviderDetailsActivity.class);
                        intent.putExtra("providerId", ap.getProviderid());
                        intent.putExtra("userId", userId);
                        intent.putExtra("sender", "User");
                        startActivity(intent);
                    }
                });
                builder.show();
                break;
            }
        }
    }
}