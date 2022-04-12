package com.example.threaded_porject_workshop_7.Packages;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.threaded_porject_workshop_7.R;

public class PackageDetailsActivity extends AppCompatActivity {
    Button btnSave;
    Button btnDelete;
    Button btnReturn;
    EditText etPkgName;
    EditText etStartDate;
    EditText etEndDate;
    EditText etDescription;
    EditText etBasePrice;
    EditText etCommission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnReturn = findViewById(R.id.btnReturn);
        etPkgName = findViewById(R.id.etDate);
        etStartDate = findViewById(R.id.etBookingNo);
        etEndDate = findViewById(R.id.etTravelerCount);
        etDescription = findViewById(R.id.etCustomerId);
        etBasePrice = findViewById(R.id.etTripTypeId);
        etCommission = findViewById(R.id.etPackageId);

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