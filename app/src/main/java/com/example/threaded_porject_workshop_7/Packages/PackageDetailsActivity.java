package com.example.threaded_porject_workshop_7.Packages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.threaded_porject_workshop_7.Customers.CustomersActivity;
import com.example.threaded_porject_workshop_7.MainActivity;
import com.example.threaded_porject_workshop_7.MenuActivity;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.travel_experts_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.miHome:
                Intent homeIntent = new Intent (getApplicationContext(), MenuActivity.class);
                startActivity(homeIntent);
                break;
            case R.id.miLogout:
                Intent logoutIntent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(logoutIntent);
                break;
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), PackagesActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }
}