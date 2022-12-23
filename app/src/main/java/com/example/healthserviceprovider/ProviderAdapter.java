package com.example.healthserviceprovider;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProviderAdapter extends ArrayAdapter<Provider> {
    private int resourceLayout;
    private Context mContext;
    private List<Provider> items;
    //Provider nesnelerinin listview'ler üzerinde görünmesi için kullanılır
    //Details butonuna basılırsa Provider sayfasının açılmasını sağlar
    //Add to Favorites düğmesine basılırsa, önce favorilerde olup olmadığı kontrol edilir, favorilerde yoksa veritabanına favorilere kaydedilir
    public ProviderAdapter(Context context, int resource, List<Provider> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.items = items;
    }

    public Provider getItem(int position) {
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
        Provider p = getItem(position);

        if (p != null) {
            int userid = ((PatientActivity) v.getContext()).userId;

            TextView tt1 = (TextView) v.findViewById(R.id.providerrowProviderName);
            tt1.setText(p.getName());

            TextView tt2 = (TextView) v.findViewById(R.id.providerrowProfession);
            tt2.setText(p.getProfession());

            Button addfavorites = (Button) v.findViewById(R.id.buttonAddFavorite);
            addfavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database db = new Database(v.getContext());
                    boolean inFavorites = db.IsinFavorites(userid,p.getId());
                    if(inFavorites){
                        Toast.makeText(v.getContext(),"Already in favorites...",Toast.LENGTH_LONG).show();
                    }
                    else{
                        db.RunQuery("INSERT INTO favorites(userid,providerid) VALUES("+userid+","+p.getId()+")");
                        Toast.makeText(v.getContext(),"Added to favorites!",Toast.LENGTH_LONG).show();
                    }
                }
            });

            Button details = (Button) v.findViewById(R.id.buttonProviderDetails);
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Provider item = p;
                    Intent intent = new Intent(v.getContext(), ProviderDetailsActivity.class);
                    intent.putExtra("providerId", item.getId());
                    intent.putExtra("userId", userid);
                    intent.putExtra("sender", "User");
                    mContext.startActivity(intent);
                }
            });
        }

        return v;
    }
}