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
import android.widget.Toast;

import com.example.threaded_porject_workshop_7.DataSource;
import com.example.threaded_porject_workshop_7.MainActivity;
import com.example.threaded_porject_workshop_7.MenuActivity;
import com.example.threaded_porject_workshop_7.R;
import com.example.threaded_porject_workshop_7.Validator;
import com.example.threaded_porject_workshop_7.model.TravelPackage;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

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

        Intent intent = getIntent();

        //get mode
        String mode = intent.getStringExtra("mode");

        //edit mode
        if(mode.equals("edit"))
        {
            initPackages(intent);
            btnDelete.setEnabled(true);
        }
        else
        {
            btnDelete.setEnabled(false);
        }

        //Save added/updated package into the database and return to packages list
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Add or modify data
                saveData(intent);
            }
        });

        //Delete selected package and return to packages list
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //get selected package id
                TravelPackage selectedPackage = (TravelPackage) intent.getSerializableExtra("package");
                int packageId = selectedPackage.getId();

                //Delete selected package
                DataSource.deletePackage(packageId, getApplicationContext());

                //Exit this activity
                Intent intent = new Intent(getApplicationContext(), PackagesActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

    private void saveData(Intent intent)
    {
        //get values from text fields
        int packageId;
        if (intent.getStringExtra("mode").equals("edit"))
        {
            TravelPackage selectedPackage = (TravelPackage) intent.getSerializableExtra("package");
            packageId = selectedPackage.getId();
        }
        else
        {
            packageId = 0;
        }

        double basePrice = Double.parseDouble(etBasePrice.getText().toString());
        //Validates data before modifying the database

        if (Validator.isPresent(etPkgName) &&
                Validator.isPresent(etBasePrice) &&
                Validator.isDoublePositive(etBasePrice) &&
                Validator.isStartDateValid(etStartDate) &&
                Validator.isEndDateValid(etStartDate, etEndDate) &&
                Validator.isDoubleInRange(0,basePrice,etCommission)
            )
        {
            Toast.makeText(getApplicationContext(), "Validation passed", Toast.LENGTH_LONG).show();
            //collect data from text fields
            String pkgName = etPkgName.getText().toString();
            Date pkgStartDate = Date.valueOf(etStartDate.getText().toString());
            Date pkgEndDate = Date.valueOf(etEndDate.getText().toString());
            String pkgDesc = etDescription.getText().toString();
            Double pkgBasePrice = Double.parseDouble(etBasePrice.getText().toString());
            Double pkgAgencyCommission = Double.parseDouble(etCommission.getText().toString());

            //create packages JSON object
            JSONObject packageJSON = new JSONObject();
            try
            {
                packageJSON.put("id", packageId + "");
                packageJSON.put("pkgName", pkgName + "");
                packageJSON.put("pkgStartDate", pkgStartDate + "");
                packageJSON.put("pkgEndDate", pkgEndDate + "");
                packageJSON.put("pkgDesc", pkgDesc + "");
                packageJSON.put("pkgBasePrice", pkgBasePrice + "");
                packageJSON.put("pkgAgencyCommission", pkgAgencyCommission + "");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //update package with new data
            if(intent.getStringExtra("mode").equals("edit"))
            {
                //url to modify package
                DataSource.postPackage(packageJSON, getApplicationContext());
            }
            else // add package with new data
            {
                DataSource.addPackage(packageJSON, getApplicationContext());
            }

            //Exit this activity
            Intent packageIntent = new Intent(getApplicationContext(), PackagesActivity.class);
            startActivity(packageIntent);
            finish();
        }
    }
    private void initPackages(Intent intent)
    {
        TravelPackage selectedPackage = (TravelPackage) intent.getSerializableExtra("package");
        etPkgName.setText(selectedPackage.getPkgName());
        etStartDate.setText(selectedPackage.getPkgStartDate().toString());
        etEndDate.setText(selectedPackage.getPkgEndDate().toString());
        etDescription.setText(selectedPackage.getPkgDesc());
        etBasePrice.setText(selectedPackage.getPkgBasePrice().toString());
        etCommission.setText(selectedPackage.getPkgAgencyCommission().toString());
    }
}