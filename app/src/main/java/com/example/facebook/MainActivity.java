package com.example.facebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.facebook.Interface.onGetDataListener;
import com.example.facebook.Logic.Memory;
import com.example.facebook.model.Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseAuth.getInstance().signOut();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



         if(currentUser == null)
         {
             startActivity(new Intent(this, LoginActivity.class));
             finish();
         }
         else
        {
            getProfile(currentUser.getUid(), FirebaseDatabase.getInstance().getReference("users"), new onGetDataListener() {
                @Override
                public void onSucess(DataSnapshot dataSnapshot)
                {
                    finish();
                    startActivity(new Intent(MainActivity.this, HomepageActivity.class));
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }
            });
        }

    }

    public void getProfile(String uID, DatabaseReference databaseReference, final onGetDataListener listen)
    {
        listen.onStart();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Memory.currentProfile = dataSnapshot.getValue(Profile.class); //this is magic
                listen.onSucess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
