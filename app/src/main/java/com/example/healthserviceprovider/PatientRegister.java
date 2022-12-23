package com.example.healthserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PatientRegister extends AppCompatActivity {
    Database db;
    Button btnRegister, btnBack;
    TextView textName, textIdentity, textPassword;
    //Hasta kayıt sayfasıdır, bilgiler alınarak veri tabanına kaydedilir
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_register);
        db = new Database(getApplicationContext());
        btnRegister = (Button) findViewById(R.id.btnPatientRegister);
        btnBack = (Button) findViewById(R.id.btnBackRegisterPatient);
        textName = (TextView) findViewById(R.id.textRegisterNameSurname);
        textIdentity = (TextView) findViewById(R.id.textRegisterIdentity);
        textPassword = (TextView) findViewById(R.id.textPatientPassword);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textName.getText() != "" && textIdentity.getText() != "" && textPassword.getText() != "") {
                    if (db.RunQuery("INSERT INTO User(identity,password,namesurname) " +
                            "VALUES('" + textIdentity.getText().toString() + "'," +
                            "'" + textPassword.getText().toString() + "'," +
                            "'" + textName.getText().toString() + "')")) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Identity already exists!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill all the blanks!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}