package com.example.threaded_porject_workshop_7.Bookings;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.threaded_porject_workshop_7.model.Booking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.concurrent.Executors;

public class BookingsActivity extends AppCompatActivity {
    ListView lvBookings;
    RequestQueue requestQueue;
    ArrayAdapter<Booking> bookingsAdapter;
    Button btnAddBookings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //display back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bookings);

        lvBookings = findViewById(R.id.lvBookings);
        btnAddBookings = findViewById(R.id.btnAddBookings);
        requestQueue = Volley.newRequestQueue(this);

        //Directs to booking details with no preloaded information
        btnAddBookings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), BookingsDetailsActivity.class);
                intent.putExtra("mode", "add"); // change mode to add
                startActivity(intent);
            }
        });
        //Display all bookings
        Executors.newSingleThreadExecutor().execute(new GetBookings());
    }

    /**
     * Create menu
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.travel_experts_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Display menu options
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.miHome:
                Intent homeIntent = new Intent (getApplicationContext(), MenuActivity.class);
                startActivity(homeIntent);
                finish();
                break;
            case R.id.miLogout:
                Intent logoutIntent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(logoutIntent);
                finish();
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
     * Sends selected booking to be modified
     */
    @Override
    protected void onStart() {
        super.onStart();
        lvBookings.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Intent intent = new Intent(getApplicationContext(), BookingsDetailsActivity.class);
                intent.putExtra("booking", (Booking) lvBookings.getAdapter().getItem(i)); // add booking information
                intent.putExtra("mode", "edit"); // change mode to edit
                startActivity(intent); //direct to booking details
                finish();
            }
        });
    }

    /**
     * Get all bookings from the webservice and display it in the list view
     */
    private class GetBookings implements Runnable
    {
        @Override
        public void run()
        {
            String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/bookings";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response)
                {
                    bookingsAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1);

                    //Read and parse booking dates
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

                            //parsed booking date
                            LocalDate bookingDate = LocalDate.parse(jsonObject.getString("bookingDate"), dateTimeFormatter);
                            Booking booking = new Booking(jsonObject.getInt("id"),
                                    Date.valueOf(bookingDate.toString()),
                                    jsonObject.getString("bookingNo"),
                                    jsonObject.getDouble("bookingTotal"),
                                    jsonObject.getDouble("travelerCount"),
                                    jsonObject.getInt("customer"),
                                    jsonObject.getString("tripType"),
                                    jsonObject.getInt("_package")
                            );
                            bookingsAdapter.add(booking);
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
                            lvBookings.setAdapter(bookingsAdapter);
                        }
                    });
                }
            }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                    Log.d("jacky", "failed to load bookings");
                }
            });
            requestQueue.add(stringRequest);
        }
    }
}