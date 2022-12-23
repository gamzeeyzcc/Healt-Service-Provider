package com.example.healthserviceprovider.ui.messages;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthserviceprovider.Database;
import com.example.healthserviceprovider.MessageActivity;
import com.example.healthserviceprovider.Provider;
import com.example.healthserviceprovider.ProviderActivity;
import com.example.healthserviceprovider.R;
import com.example.healthserviceprovider.User;
import com.example.healthserviceprovider.UserAdapter;

import java.util.ArrayList;

public class MessagesFragment extends Fragment {

    private MessagesViewModel messagesViewModel;
    private ListView appointmentsListView;
    private Provider provider;
    private Database db;
    private UserAdapter adapter;

    //Provider'a gelen mesajların görünmesini sağlar
    //Provider'a en az 1 mesaj atan tüm hastaların kullanıcı adları görünür
    //Kullanıcı adına tıklandığında mesajlaşma sayfasına yönlendirilmesini sağlar
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        messagesViewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_messages, container, false);
        db = new Database(getContext());
        provider = ((ProviderActivity) getActivity()).provider;
        appointmentsListView = (ListView) root.findViewById(R.id.messagesListView);
        ArrayList<User> users = db.GetUsersThatSendMessage(provider.getId());
        adapter = new UserAdapter(root.getContext(), R.layout.userrow, users);
        appointmentsListView.setAdapter(adapter);
        appointmentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User item = (User) adapter.getItem(position);
                Intent intent = new Intent(getContext(), MessageActivity.class);
                intent.putExtra("providerId", provider.getId());
                intent.putExtra("userId", item.getId());
                intent.putExtra("sender", "Provider");
                startActivity(intent);
            }
        });
        return root;
    }
}