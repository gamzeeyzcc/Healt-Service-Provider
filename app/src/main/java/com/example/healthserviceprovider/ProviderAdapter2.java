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

public class ProviderAdapter2 extends ArrayAdapter<Provider> {
    private int resourceLayout;
    private Context mContext;
    private List<Provider> items;

    //Provider nesnelerinin listview'ler üzerinde görünmesi için kullanılır ANCAK BU Adapter FAVORITES sayfasında kullanılır
    //Details butonuna basılırsa Provider sayfasının açılmasını sağlar
    //Remove from Favorites düğmesine basılırsa, favorilerden çıkarılır
    public ProviderAdapter2(Context context, int resource, List<Provider> items) {
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
            int userid = ((FavoritesActivity) v.getContext()).userId;

            TextView tt1 = (TextView) v.findViewById(R.id.providerrowProviderName);
            tt1.setText(p.getName());

            TextView tt2 = (TextView) v.findViewById(R.id.providerrowProfession);
            tt2.setText(p.getProfession());

            Button removeFavorites = (Button) v.findViewById(R.id.buttonAddFavorite);
            removeFavorites.setText("REMOVE FROM FAVORITES");
            removeFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Database db = new Database(v.getContext());
                    db.RunQuery("DELETE FROM favorites WHERE userid="+userid+" AND providerid="+p.getId());
                    Toast.makeText(v.getContext(),"Removed from favorites!",Toast.LENGTH_LONG).show();
                    items.remove(position);
                    notifyDataSetChanged();
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