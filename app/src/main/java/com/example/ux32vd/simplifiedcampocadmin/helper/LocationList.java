package com.example.ux32vd.simplifiedcampocadmin.helper;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ux32vd.simplifiedcampocadmin.R;

import org.w3c.dom.Text;

import java.util.List;

public class LocationList extends ArrayAdapter<Model> {

    private Activity context;
    private List<Model> locations;

    public LocationList(Activity context, List<Model> locations) {
        super(context, R.layout.layout_location_list, locations);
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.layout_location_list, null, true);

        TextView textViewDeskripsi = (TextView) listViewItem.findViewById(R.id.ReadDeskripsi);
        TextView textViewFoto = (TextView) listViewItem.findViewById(R.id.ReadFoto);
        TextView textViewLokasi = (TextView) listViewItem.findViewById(R.id.ReadLokasi);

        Model model = locations.get(position);

        textViewDeskripsi.setText(model.getDeskripsi());
        textViewFoto.setText(model.getFoto());
        textViewLokasi.setText(model.getLokasi());

        return listViewItem;
    }
}
