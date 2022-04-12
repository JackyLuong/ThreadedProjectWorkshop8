package com.example.threaded_porject_workshop_7.Bookings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threaded_porject_workshop_7.R;

public class BookingsActivity extends AppCompatActivity {
    ListView lvPackages;
    Button btnMainMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        lvPackages = findViewById(R.id.lvCustomers);
        btnMainMenu = findViewById(R.id.btnMainMenu);

        btnMainMenu.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}