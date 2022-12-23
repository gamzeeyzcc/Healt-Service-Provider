package com.example.healthserviceprovider;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AppointmentAdapter extends ArrayAdapter<Appointment> {
    private int resourceLayout;
    private Context mContext;
    private List<Appointment> items;
    //Listview üzerinde randevuların özel şekilde görüntülenmesini sağlayan sınıftır

    public AppointmentAdapter(Context context, int resource, List<Appointment> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.items = items;
    }

    public Appointment getItem(int position) {
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
        Appointment p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.appointmentPatientName);
            tt1.setText(p.getPatientName());

            TextView tt2 = (TextView) v.findViewById(R.id.appointmentProvider);
            tt2.setText(p.getProviderName());

            TextView tt3 = (TextView) v.findViewById(R.id.appointmentDate);
            tt3.setText(p.getDate());
            if(p.getStatus()==0){
                tt3.setText(p.getDate()+" TODAY");
                tt3.setTextColor(Color.RED);
            }
            else if(p.getStatus()==1){
                tt3.setTextColor(Color.GREEN);
            }

            //Cancel düğmesine basıldığında veritabanından randevuyu siler
            Button cancelButton = (Button) v.findViewById(R.id.buttonCancelAppointment);
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database db = new Database(v.getContext());
                    db.RunQuery("DELETE FROM appointment WHERE id="+p.getId());
                    items.remove(position);
                    notifyDataSetChanged();
                    return;
                }
            });
        }

        return v;
    }
}