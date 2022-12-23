package com.example.healthserviceprovider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class PatientActivity extends AppCompatActivity {

    public Database db;
    public User user;
    public int userId;

    TextView searchText;
    Button buttonFilter, buttonFavorites, buttonAppointments;
    ListView providerListView;
    List<Provider> providers;
    ProviderAdapter adapter;
    //Hasta giriş yapınca karşılaşılan sayfadır
    //Filtre düğmesine basılınca girilen metni içeren providerlar listelenir
    //Ayrıca diğer düğmeler ile diğer activityler açılır
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(this.getApplicationContext());
        userId = getIntent().getExtras().getInt("userId");
        user = db.GetUser(userId);
        setContentView(R.layout.activity_patient);
        if (user == null) {
            finish();
        }
        searchText = (TextView) findViewById(R.id.textFilter);
        buttonAppointments = (Button) findViewById(R.id.buttonAppointments);
        buttonFilter = (Button) findViewById(R.id.buttonFilter);
        buttonFavorites = (Button) findViewById(R.id.buttonFavorites);
        providerListView = (ListView) findViewById(R.id.patientProviderListView);
        providers = db.GetProviders("SELECT * FROM provider");
        adapter = new ProviderAdapter(PatientActivity.this, R.layout.providerrow, providers);
        providerListView.setAdapter(adapter);
        providerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Provider item = (Provider) adapter.getItem(position);
                Intent intent = new Intent(PatientActivity.this, ProviderDetailsActivity.class);
                intent.putExtra("providerId", item.getId());
                intent.putExtra("userId", userId);
                intent.putExtra("sender", "User");
                startActivity(intent);
            }
        });
        buttonFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchText.getText()!=""){
                    String text = searchText.getText().toString();
                    providers = db.GetProviders("SELECT * FROM provider WHERE name LIKE '%"+text+"%' OR profession LIKE '%"+text+"%'");
                    adapter = new ProviderAdapter(PatientActivity.this, R.layout.providerrow, providers);
                    providerListView.setAdapter(adapter);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter any text to search!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, FavoritesActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });
        buttonAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientActivity.this, PatientAppointmentsActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);
            }
        });


    }
}