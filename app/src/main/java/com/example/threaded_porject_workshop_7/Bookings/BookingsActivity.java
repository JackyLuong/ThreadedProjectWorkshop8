package com.example.threaded_porject_workshop_7.Bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threaded_porject_workshop_7.Customers.CustomerDetailsActivity;
import com.example.threaded_porject_workshop_7.R;

public class BookingsActivity extends AppCompatActivity {
    ListView lvBookings;
    Button btnAddBookings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        lvBookings = findViewById(R.id.lvBookings);
        btnAddBookings = findViewById(R.id.btnAddBookings);

        btnAddBookings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), BookingsDetailsActivity.class);
                startActivity(intent);
            }
        });
    }
}