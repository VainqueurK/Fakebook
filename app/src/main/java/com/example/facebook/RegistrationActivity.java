package com.example.facebook;

import android.content.Intent;
import android.os.Bundle;

import com.example.facebook.Logic.Validator;
import com.example.facebook.model.Profile;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.facebook.R;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationActivity extends AppCompatActivity {

    public EditText username;
    public EditText email;
    public EditText password;
    public Button regButton;
    public TextView gologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        email = findViewById(R.id.emailCreate);
        password = findViewById(R.id.pwordCreate);
        regButton = findViewById(R.id.regbutton);
        username = findViewById(R.id.usernameCreate);
        gologin = findViewById(R.id.logintxt);

        gologin.setOnClickListener(e->
        {
            finish();
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

        });

        email.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                email.setError(new Validator().validateEmail(s.toString()) ? null: getString(R.string.email_error));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                password.setError(new Validator().validatePassword(s.toString()) ? null: getString(R.string.pass_error));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        username.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                username.setError(s.toString().isEmpty() ? getString(R.string.gen_error) : null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        regButton.setOnClickListener(e->{
            if(new Validator().validateEmail(email.getText().toString()) && new Validator().validatePassword(password.getText().toString())&& !(username.getText().toString().isEmpty()))
            registerUserToDatabase(username.getText().toString(), email.getText().toString(), password.getText().toString());

        });
    }



    public void registerUserToDatabase(String username, String email, String password)
    {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult)
            {
                addProfileToDatabase(username, email, authResult.getUser().getUid());
            }
        });
    }

    public void addProfileToDatabase(String username, String email, String uID)
    {
        Profile p = new Profile(username, uID, email);

        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference("users/");
        dataRef.child(uID).setValue(p).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(RegistrationActivity.this, HomepageActivity.class));
                finish();
            }
        });
    }
}
