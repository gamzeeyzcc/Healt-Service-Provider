package com.example.healthserviceprovider.ui.appointment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthserviceprovider.Appointment;
import com.example.healthserviceprovider.AppointmentAdapter;
import com.example.healthserviceprovider.Database;
import com.example.healthserviceprovider.Provider;
import com.example.healthserviceprovider.ProviderActivity;
import com.example.healthserviceprovider.R;

import java.util.ArrayList;

public class AppointmentsFragment extends Fragment {

    private AppointmentsViewModel appointmentsViewModel;
    private ListView appointmentsListView;
    private Provider provider;
    private Database db;
    private AppointmentAdapter adapter;

    //Provider'ın kontrolünü sağlar, ilk çalıştırıldığında provider'a ait randevuları getirerek ekranda listeler

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appointmentsViewModel =
                new ViewModelProvider(this).get(AppointmentsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_appointments, container, false);
        db = new Database(getContext());
        provider = ((ProviderActivity) getActivity()).provider;
        appointmentsListView = (ListView) root.findViewById(R.id.appointmentsListView);
        ArrayList<Appointment> appointments = db.GetAppointmentsOfProvider(provider.getId());
        adapter = new AppointmentAdapter(root.getContext(), R.layout.appointmentrow, appointments);
        appointmentsListView.setAdapter(adapter);
        return root;
    }
}