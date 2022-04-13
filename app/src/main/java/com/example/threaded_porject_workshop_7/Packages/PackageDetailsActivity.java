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
        etPkgName = findViewById(R.id.etName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etDescription = findViewById(R.id.etDescription);
        etBasePrice = findViewById(R.id.etBasePrice);
        etCommission = findViewById(R.id.etCommission);
    }
}