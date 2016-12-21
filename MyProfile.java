package com.example.rsalesarm.events;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
    }

    public void create(View view)
    {
        Intent i = new Intent(this, CreateEvent.class);
        startActivity(i);
    }

    public void events(View view)
    {
        Intent i = new Intent(this, Events.class);
        startActivity(i);
    }
}
