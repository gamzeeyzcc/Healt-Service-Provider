package com.example.healthserviceprovider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {

    int userId, providerId;
    String sender;
    TextView messageText;
    ListView messagesView;
    Button sendButton;
    Database db;
    UserMessageAdapter adapter;
    ArrayList<UserMessage> messages;
    //Bu activity, iki kişinin mesajlaşmasını sağlar
    //User ve Providerid'yi alır, ayrıca bu sayfadan kimin mesaj gönderdiğini alır ve veri tabanına mesaj gönderildikçe ekler
    //Ayrıca listview'e tüm mesajları listeler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        db = new Database(getApplicationContext());
        sendButton = (Button) findViewById(R.id.buttonSendMessage);
        messageText = (TextView) findViewById(R.id.messageTextView);
        messagesView = (ListView) findViewById(R.id.appointmentsListView);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            userId = b.getInt("userId");
            providerId = b.getInt("providerId");
            sender = b.getString("sender");
            messages = db.GetMessagesBetween(userId, providerId);
            adapter = new UserMessageAdapter(getApplicationContext(), R.layout.messagerow, messages);
            messagesView.setAdapter(adapter);
        } else {
            finish();
        }
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (messageText.getText() != "") {
                    db.RunQuery("INSERT INTO messages(userid,providerid,message,sender) VALUES(" + userId + "," + providerId + ",'" + messageText.getText() + "','" + sender + "')");
                    messages = db.GetMessagesBetween(userId, providerId);
                    adapter = new UserMessageAdapter(getApplicationContext(), R.layout.messagerow, messages);
                    messagesView.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "Enter your message", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}