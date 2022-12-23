package com.example.healthserviceprovider;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
    Button loginButton;
    Button registerPatient, registerProvider;
    TextView textDiplomaNo, textPassword;
    Database db;

    //Giriş, kayıt ol düğmelerini içeren giriş sayfası
    //Giriş düğmesine basılınca user ve provider tabloları kontrol edilir, bilgiler doğruysa giriş yapılır
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        db = new Database(getApplicationContext());
        loginButton = (Button) findViewById(R.id.btnLogin);
        registerPatient = (Button) findViewById(R.id.btnRegisterPatient);
        registerProvider = (Button) findViewById(R.id.btnRegisterProvider);
        textDiplomaNo = (TextView) findViewById(R.id.textDiplomaNo);
        textPassword = (TextView) findViewById(R.id.textPassword);

        loginButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User ulogin = db.UserLogin(textDiplomaNo.getText().toString(), textPassword.getText().toString());
                        Provider plogin;
                        if (ulogin == null) {
                            plogin = db.ProviderLogin(textDiplomaNo.getText().toString(), textPassword.getText().toString());
                            if (plogin == null) {
                                Toast.makeText(getApplicationContext(), "Login Failed!", Toast.LENGTH_LONG).show();
                            } else {
                                Intent intent = new Intent(MainActivity.this, ProviderActivity.class);
                                intent.putExtra("providerId", plogin.getId());
                                startActivity(intent);
                            }
                        } else {
                            Intent intent = new Intent(MainActivity.this, PatientActivity.class);
                            intent.putExtra("userId", ulogin.getId());
                            startActivity(intent);
                        }
                    }
                }
        );
        registerPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PatientRegister.class);
                startActivity(intent);
            }
        });
        registerProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProviderRegister.class);
                startActivity(intent);
            }
        });
    }
}