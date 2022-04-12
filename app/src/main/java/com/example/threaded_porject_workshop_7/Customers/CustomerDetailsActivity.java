package com.example.threaded_porject_workshop_7.Customers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threaded_porject_workshop_7.R;

public class CustomerDetailsActivity extends AppCompatActivity {
    Button btnSave,btnDelete;
    EditText etFirstName,etLastName,etAddress,etCity,etProvince,
            etPostal,etCountry,etHomePhone,etBusPhone,etEmail,etAgentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_customer_details);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName = findViewById(R.id.etLastName);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etProvince = findViewById(R.id.etProvince);
        etPostal = findViewById(R.id.etPostal);
        etCountry = findViewById(R.id.etCountry);
        etHomePhone = findViewById(R.id.etHomePhone);
        etBusPhone = findViewById(R.id.etBusPhone);
        etEmail = findViewById(R.id.etEmail);
        etAgentId = findViewById(R.id.etAgentId);

        Customers customer = (Customers) getIntent().getSerializableExtra("customer");

        if(customer != null){

            etFirstName.setText(customer.getCustFirstName());
            etLastName.setText(customer.getCustLastName());
            etAddress.setText(customer.getCustAddress());
            etCity.setText(customer.getCustCity());
            etProvince.setText(customer.getCustProvince());
            etPostal.setText(customer.getCustPostal());
            etCountry.setText(customer.getCustCountry());
            etHomePhone.setText(customer.getCustHomeNum());
            etBusPhone.setText(customer.getCustBusNum());
            etEmail.setText(customer.getCustEmail());
            etAgentId.setText(customer.getCustAgentId()+"");
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CustomerDetailsActivity.this, CustomersActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}