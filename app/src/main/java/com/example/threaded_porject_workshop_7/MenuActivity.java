package com.example.threaded_porject_workshop_7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.threaded_porject_workshop_7.Customers.CustomersActivity;
import com.example.threaded_porject_workshop_7.Packages.PackagesActivity;


public class MenuActivity extends AppCompatActivity {

    TextView tvWelcome;
    LinearLayout llCustomers,llPackages,llBookings,llAgents,llAgencies;

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("TravelExperts");
        getSupportActionBar().setIcon(R.drawable.logowhite);

        Intent intent = getIntent();
        user = intent.getStringExtra("name");


        setContentView(R.layout.activity_menu);

        tvWelcome = findViewById(R.id.tvWelcome);
        llCustomers = findViewById(R.id.llCustomers);
        llPackages = findViewById(R.id.llPackages);
        llBookings = findViewById(R.id.llBookings);
        llAgents = findViewById(R.id.llAgents);
        llAgencies = findViewById(R.id.llAgencies);

        tvWelcome.setText("Welcome back, " + user + "!");

        llCustomers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Opening Customers..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                startActivity(intent);
            }
        });

        llPackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PackagesActivity.class);
                startActivity(intent);
            }
        });
    }






}