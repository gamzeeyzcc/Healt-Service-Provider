package com.example.healthserviceprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private int resourceLayout;
    private Context mContext;
    private List<User> items;
    //Kullanıcı bilgilerinin listview üzerinde listelenmesi için kullanılan adapter sınıfı
    public UserAdapter(Context context, int resource, List<User> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.items = items;
    }

    public User getItem(int position) {
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
        User p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.userrowUsername);
            tt1.setText(p.getNamesurname());
        }

        return v;
    }
}
