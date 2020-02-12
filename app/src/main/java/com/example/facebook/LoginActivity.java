package com.example.facebook;

import android.content.Intent;
import android.os.Bundle;

import com.example.facebook.Logic.Memory;
import com.example.facebook.Logic.Validator;
import com.example.facebook.model.Profile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    public TextView registerHere;
    public Button login;
    public EditText username;
    public EditText email;
    public EditText password;
    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        email = findViewById(R.id.username);
        password = findViewById(R.id.pword);
        registerHere = findViewById(R.id.reg); // do this to link UI elements
        login = findViewById(R.id.loginbutton);
        progressBar = findViewById(R.id.proBar);
        //password.setError(new Validator().validatePassword(s.toString()) ? null: getString(R.string.pass_error));

        registerHere.setOnClickListener(e->
        {
            finish();
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));

        });


        login.setOnClickListener(e->
        {
            email.setError(!email.getText().toString().isEmpty() ? null : getString(R.string.gen_error));
            password.setError(!password.getText().toString().isEmpty() ? null : getString(R.string.gen_error));

            if(!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
            {
                progressBar.setVisibility(View.VISIBLE);
                signin(email.getText().toString(), password.getText().toString());
            }

        });

    }

    public void signin(String email, String password)
    {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult)
            {
                getProfile(authResult.getUser().getUid());
            }
        });
    }

    public void getProfile(String uID)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Memory.currentProfile = dataSnapshot.getValue(Profile.class); //this is magic
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
