package com.example.ux32vd.simplifiedcampocadmin;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ux32vd.simplifiedcampocadmin.helper.LocationList;
import com.example.ux32vd.simplifiedcampocadmin.helper.Model;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuAdmin extends AppCompatActivity {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    ImageButton createbutton, updatebutton, deletebutton;
    ListView listViewLocation;

    //a list to store all the artist from firebase database
    List<Model> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        mRef = FirebaseDatabase.getInstance().getReference("Data");

        listViewLocation = (ListView) findViewById(R.id.listViewLocation);


        locations = new ArrayList<>();

        ImageButton CreateButton = (ImageButton) findViewById(R.id.createbutton);
        ImageButton UpdateButton = (ImageButton) findViewById(R.id.updatebutton);
        ImageButton DeleteButton = (ImageButton) findViewById(R.id.deletebutton);

        CreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuAdmin.this, MenuCreate.class);
                startActivity(intent);
            }
        });

        listViewLocation.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Model model = locations.get(position);
                showUpdateDeleteDialog(model.getId(), model.getDeskripsi(), model.getFoto(), model.getLokasi());

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                locations.clear();

                for(DataSnapshot locationSnapshot : dataSnapshot.getChildren()){
                    Model model = locationSnapshot.getValue(Model.class);
                    locations.add(model);

                }

                LocationList adapter = new LocationList(MenuAdmin.this, locations);
                listViewLocation.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDeleteDialog (final String id, String deskripsi, String foto, String lokasi) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText EditDeskripsi = (EditText) dialogView.findViewById(R.id.EditDeskripsi);
        final EditText EditFoto = (EditText) dialogView.findViewById(R.id.EditFoto);
        final EditText EditLokasi = (EditText) dialogView.findViewById(R.id.EditLokasi);
        final Button ButtonUpdate = (Button) dialogView.findViewById(R.id.ButtonUpdate);
        final Button ButtonDelete = (Button) dialogView.findViewById(R.id.ButtonDelete);

        dialogBuilder.setTitle("Mengupdate lokasi" + lokasi);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        ButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String deskripsi = EditDeskripsi.getText().toString().trim();
                String foto = EditFoto.getText().toString().trim();
                String lokasi = EditLokasi.getText().toString().trim();

                //Validasi jika kolom update kosong
                if (TextUtils.isEmpty(deskripsi)) {
                    EditDeskripsi.setError("Deskripsi diperlukan");
                    return;
                }

                if (TextUtils.isEmpty(foto)) {
                    EditFoto.setError("URL foto diperlukan");
                    return;
                }

                if (TextUtils.isEmpty(lokasi)) {
                    EditLokasi.setError("Lokasi diperlukan");
                    return;
                }

                updateLokasi(id, deskripsi, foto, lokasi);

                alertDialog.dismiss();

            }
        });

        ButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLokasi(id);
            }
        });
    }

    private void deleteLokasi(String id){
        DatabaseReference mLokasi = FirebaseDatabase.getInstance().getReference("Data").child(id);
        mLokasi.removeValue();
        Toast.makeText(this, "Lokasi berhasil dihapus", Toast.LENGTH_SHORT).show();
    }

    private boolean updateLokasi(String id, String deskripsi, String foto, String lokasi) {
        //getting the specified artist reference
        mRef = FirebaseDatabase.getInstance().getReference("Data").child(id);

        //updating artist
        Model model = new Model(id, deskripsi, foto, lokasi);
        mRef.setValue(model);
        Toast.makeText(getApplicationContext(), "Lokasi Updated", Toast.LENGTH_LONG).show();
        return true;
    }


}



