package com.example.healthserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {
    public Database db;
    public User user;
    public int userId;
    List<Provider> providers;
    private ListView favoritesListView;
    ProviderAdapter2 adapter;
    private Button backButton;
    //Kullanıcının favori providerlarını listeler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new Database(this.getApplicationContext());
        userId = getIntent().getExtras().getInt("userId");
        user = db.GetUser(userId);
        setContentView(R.layout.activity_favorites);
        favoritesListView = (ListView) findViewById(R.id.lsitviewFavorites);
        providers = db.GetProviders("SELECT * FROM provider WHERE id in (SELECT providerid FROM favorites WHERE userid="+userId+")");
        adapter = new ProviderAdapter2(FavoritesActivity.this, R.layout.providerrow, providers);
        favoritesListView.setAdapter(adapter);
        backButton = (Button) findViewById(R.id.buttonBackFavorites);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}