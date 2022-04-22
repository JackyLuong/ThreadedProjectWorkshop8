package com.example.threaded_porject_workshop_7.Packages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.threaded_porject_workshop_7.MainActivity;
import com.example.threaded_porject_workshop_7.MenuActivity;
import com.example.threaded_porject_workshop_7.R;
import com.example.threaded_porject_workshop_7.model.TravelPackage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.concurrent.Executors;

public class PackagesActivity extends AppCompatActivity {
    ListView lvPackages;
    RequestQueue requestQueue;
    ArrayAdapter<TravelPackage> packageAdapter;
    Button btnAddPackages;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_packages);

        lvPackages = findViewById(R.id.lvPackages);
        btnAddPackages = findViewById(R.id.btnAddPackages);
        requestQueue = Volley.newRequestQueue(this);

        btnAddPackages.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent (getApplicationContext(), PackageDetailsActivity.class);
                intent.putExtra("mode", "add"); // change mode to add
                startActivity(intent);
            }
        });
        //Display all packages
        Executors.newSingleThreadExecutor().execute(new GetPackages());
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
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Sends selected package to be modified
     */
    @Override
    protected void onStart() {
        super.onStart();
        lvPackages.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(), PackageDetailsActivity.class);
                intent.putExtra("package", (TravelPackage) lvPackages.getAdapter().getItem(i)); // add package information
                intent.putExtra("mode", "edit"); // change mode to edit
                startActivity(intent); //direct to package details
                //Display all packages
                Executors.newSingleThreadExecutor().execute(new GetPackages());
            }
        });
    }

    /**
     * Get all packages from the webservice and display it in the list view
     */
    private class GetPackages implements Runnable
    {
        @Override
        public void run()
        {
            String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/packages";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    packageAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

                    //Read and parse package dates
                    DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                            .parseCaseInsensitive()
                            .appendPattern("MMM d, yyyy, h:mm:ss a")
                            .toFormatter(Locale.ENGLISH);
                    try
                    {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i=0; i<jsonArray.length(); i++)
                        {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            //parsed startDate date
                            LocalDate startDate = LocalDate.parse(jsonObject.getString("pkgStartDate"), dateTimeFormatter);
                            //parsed endDate date
                            LocalDate endDate = LocalDate.parse(jsonObject.getString("pkgEndDate"), dateTimeFormatter);

                            TravelPackage newPackage = new TravelPackage(jsonObject.getInt("id"),
                                    jsonObject.getString("pkgName"),
                                    Date.valueOf(startDate.toString()),
                                    Date.valueOf(endDate.toString()),
                                    jsonObject.getString("pkgDesc"),
                                    jsonObject.getDouble("pkgBasePrice"),
                                    jsonObject.getDouble("pkgAgencyCommission")
                            );
                            packageAdapter.add(newPackage);
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            lvPackages.setAdapter(packageAdapter);
                        }
                    });
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                    Log.d("jacky", "failed to load packages");
                }
            });
            requestQueue.add(stringRequest);
        }
    }
}