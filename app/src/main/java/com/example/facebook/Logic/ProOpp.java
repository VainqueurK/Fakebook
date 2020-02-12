package com.example.facebook.Logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.example.facebook.LoginActivity;
import com.example.facebook.MainActivity;
import com.example.facebook.model.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProOpp
{
    public void getProfile(String uID, Context context, Activity activity)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");

        ref.child(uID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                Memory.currentProfile = dataSnapshot.getValue(Profile.class); //this is magic
                activity.finish();
                context.startActivity(new Intent(context, MainActivity.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

