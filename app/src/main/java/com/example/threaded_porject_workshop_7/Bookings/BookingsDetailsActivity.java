package com.example.threaded_porject_workshop_7.Bookings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.threaded_porject_workshop_7.R;

public class BookingsDetailsActivity extends AppCompatActivity {
    Button btnSave;
    Button btnDelete;
    Button btnReturn;
    EditText etDate;
    EditText etBookingNo;
    EditText etTravelerCount;
    EditText etCustomerId;
    EditText etTripTypeId;
    EditText etPackageId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings_details);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnReturn = findViewById(R.id.btnReturn);
        etDate = findViewById(R.id.etDate);
        etBookingNo = findViewById(R.id.etBookingNo);
        etTravelerCount = findViewById(R.id.etTravelerCount);
        etCustomerId = findViewById(R.id.etCustomerId);
        etTripTypeId = findViewById(R.id.etTripTypeId);
        etPackageId = findViewById(R.id.etPackageId);

        btnReturn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}