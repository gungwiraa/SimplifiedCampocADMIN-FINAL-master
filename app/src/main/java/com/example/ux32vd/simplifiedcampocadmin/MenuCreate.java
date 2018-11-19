package com.example.ux32vd.simplifiedcampocadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ux32vd.simplifiedcampocadmin.helper.Model;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MenuCreate extends AppCompatActivity {

    DatabaseReference mRef;

    EditText createDeskripsi, createFoto, createLokasi;
    Button buttonCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_create);

        //mFirebaseDatabase = FirebaseDatabase.getInstance();
        //getting the reference of artists node
        mRef = FirebaseDatabase.getInstance().getReference("Data");

        createDeskripsi = (EditText) findViewById(R.id.CreateDeskripsi);
        createFoto = (EditText) findViewById(R.id.CreateFoto);
        createLokasi = (EditText) findViewById(R.id.CreateLokasi);
        buttonCreate = (Button) findViewById(R.id.ButtonCreateData);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addArtist()
                //the method is defined below
                //this method is actually performing the write operation
                addLokasi();
            }
        });
    }

    private void addLokasi() {
        //getting the values to save
        String deskripsi = createDeskripsi.getText().toString().trim();
        String foto = createFoto.getText().toString().trim();
        String lokasi = createLokasi.getText().toString().trim();

        //checking if the value is provided
        if (!TextUtils.isEmpty(deskripsi)) {


            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = mRef.push().getKey();

            //creating an Artist Object
            Model model = new Model(id, deskripsi, foto, lokasi);
            //Saving the Artist
            mRef.child(id).setValue(model);

            //setting edittext to blank again
            createDeskripsi.setText("");
            createFoto.setText("");
            createLokasi.setText("");

            //displaying a success toast
            Toast.makeText(this, "Lokasi ditambahkan", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Masukkan input dengan benar", Toast.LENGTH_LONG).show();
        }
    }
}
