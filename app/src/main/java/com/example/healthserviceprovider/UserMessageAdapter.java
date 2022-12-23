package com.example.healthserviceprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserMessageAdapter extends ArrayAdapter<UserMessage> {
    private int resourceLayout;
    private Context mContext;
    private List<UserMessage> items;
    //UserMessage sınıfı için adapter
    //ListView üzerinde mesajların istenilen formatta görüntülenmesini sağlar
    public UserMessageAdapter(Context context, int resource, List<UserMessage> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.items = items;
    }

    public UserMessage getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        UserMessage p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.messagerowSender);
            tt1.setText(p.getSender());

            TextView tt2 = (TextView) v.findViewById(R.id.messagerowMessage);
            tt2.setText(p.getMessage());
        }

        return v;
    }
}