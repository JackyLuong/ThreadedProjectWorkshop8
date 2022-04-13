package com.example.threaded_porject_workshop_7.Packages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.threaded_porject_workshop_7.R;

public class PackagesActivity extends AppCompatActivity {
    ListView lvPackages;
    Button btnAddPackages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

        lvPackages = findViewById(R.id.lvBookings);
        btnAddPackages = findViewById(R.id.btnAddPackages);

        btnAddPackages.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}