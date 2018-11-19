package com.example.ux32vd.simplifiedcampocadmin;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class MenuRegister extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText signupEmail, signupPassword;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_register);

        signupEmail = (EditText) findViewById(R.id.signupEmail);
        signupPassword = (EditText) findViewById(R.id.signupPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

        findViewById(R.id.buttonSignUp2).setOnClickListener(this);
        findViewById(R.id.textviewLogin).setOnClickListener(this);

    }

    private void registerAdmin() {
        String email = signupEmail.getText().toString().trim();
        String password = signupPassword.getText().toString().trim();

        //validasi kolom tidak boleh kosong
        if (email.isEmpty()) {
            signupEmail.setError("Email tidak boleh kosong");
            signupEmail.requestFocus();
            return;
        }

        // mengecek validasi email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmail.setError("Masukkan email yang valid");
            signupEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signupPassword.setError("Password tidak boleh kosong");
            signupPassword.requestFocus();
            return;
        }

        //mengecek panjang password
        if (password.length() < 8) {
            signupPassword.setError("Minimal panjang password adalah 8");
        }

        progressBar.setVisibility(View.VISIBLE);

        //Autorisasi login email dan password
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User berhasil dibuat", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MenuRegister.this, MenuAdmin.class);
                    //Menghilangkan activity sebelumnya, agar user tidak kembali ke menu login
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "User telah terdaftar", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonSignUp2:
                registerAdmin();
                break;
        }
    }
}