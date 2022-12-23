package com.example.healthserviceprovider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;

public class ProviderRegister extends AppCompatActivity {
    Database db;
    Button btnRegister, btnBack, btnPin;
    TextView textName, textDiploma, textPassword, textProfession;
    boolean areaSelected = false;
    LatLng point;
    //PRovider kayıt sayfası
    //eğer bilgiler girildiyse ve konum seçildiyse, veri tabanına provider kaydı yapılır
    //Aksi taktirde bilgi girilene kadar kayıt yapılmaz
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle b = data.getExtras();
                if (b != null) {
                    areaSelected = true;
                    point = new LatLng(b.getDouble("Lat"), b.getDouble("Lng"));
                    Toast.makeText(ProviderRegister.this, "Location successfully selected!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ProviderRegister.this, "Could not get data!", Toast.LENGTH_LONG).show();
                }
            } else if (resultCode == 0) {
                Toast.makeText(ProviderRegister.this, "Location should be selected!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_register);
        db = new Database(getApplicationContext());
        btnRegister = (Button) findViewById(R.id.btnProviderRegister);
        btnPin = (Button) findViewById(R.id.btnPinLocation);
        btnBack = (Button) findViewById(R.id.btnBackProviderRegister);
        textName = (TextView) findViewById(R.id.textProviderRegisterName);
        textDiploma = (TextView) findViewById(R.id.textProviderRegisterDiploma);
        textPassword = (TextView) findViewById(R.id.textProviderRegisterPassword);
        textProfession = (TextView) findViewById(R.id.textProviderRegisterProfession);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textName.getText() != "" && textDiploma.getText() != "" && textPassword.getText() != "" && textProfession.getText() != "" && areaSelected) {
                    double lat = point.latitude, lng = point.longitude;
                    if (db.RunQuery("INSERT INTO Provider(diplomano,password,name,profession,lat,lng) " +
                            "VALUES('" + textDiploma.getText().toString() + "'," +
                            "'" + textPassword.getText().toString() + "'," +
                            "'" + textName.getText().toString() + "'," +
                            "'" + textProfession.getText().toString() + "'," +
                            lat + "," + lng + ")")) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Diploma no already exists!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all the blanks!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProviderRegister.this, PinLocationActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }
}