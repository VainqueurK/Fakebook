package com.example.facebook.Interface;

import com.google.firebase.database.DataSnapshot;

public interface onGetDataListener
{
    void onSucess(DataSnapshot dataSnapshot);
    void onStart();
    void onFailure();
}
