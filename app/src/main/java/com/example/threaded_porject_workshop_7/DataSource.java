package com.example.threaded_porject_workshop_7;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.threaded_porject_workshop_7.MainActivity;
import com.example.threaded_porject_workshop_7.model.Booking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DataSource
{
    /**
     * Takes bookings JSON data and updates the database with the given JSON data.
     * @param data
     * @param context
     */
    public static void postBooking (JSONObject data, Context context)
    {
        String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/bookings/post-booking";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Toast.makeText(context, "Updated successfully", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, "Update failed", Toast.LENGTH_LONG).show();
                VolleyLog.wtf(error.getMessage(), "utf-8");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Takes bookings JSON data and adds it to the database
     * @param data
     * @param context
     */
    public static void addBooking (JSONObject data, Context context)
    {
        String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/bookings/add-booking";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, data, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Toast.makeText(context, "Added successfully", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, "Add failed", Toast.LENGTH_LONG).show();
                VolleyLog.wtf(error.getMessage(), "utf-8");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Deletes selected booking
     * @param bookingId
     * @param context
     */
    public static void deleteBooking (int bookingId, Context context)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/bookings/delete-booking/" + bookingId;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                VolleyLog.wtf(response, "utf-8");
                Toast.makeText(context, "Booking was deleted successfully", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.wtf(error.getMessage(), "utf-8");
                Toast.makeText(context, "Failed to delete booking", Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    /**
     * Takes packages JSON data and updates it to the database
     * @param data
     * @param context
     */
    public static void postPackage(JSONObject data, Context context)
    {
        String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/packages/post-packages";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, data, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Toast.makeText(context, "Updated successfully", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, "Update failed", Toast.LENGTH_LONG).show();
                VolleyLog.wtf(error.getMessage(), "utf-8");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Takes packages JSON data and adds it to the database
     * @param data
     * @param context
     */
    public static void addPackage(JSONObject data, Context context)
    {
        String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/packages/add-package";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, data, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                Toast.makeText(context, "Added successfully", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Toast.makeText(context, "Add failed", Toast.LENGTH_LONG).show();
                VolleyLog.wtf(error.getMessage(), "utf-8");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    /**
     * Delete selected package
     * @param packageId
     * @param context
     */
    public static void deletePackage (int packageId, Context context)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/packages/delete-package/" + packageId;
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                VolleyLog.wtf(response, "utf-8");
                Toast.makeText(context, "Package was deleted successfully", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.wtf(error.getMessage(), "utf-8");
                Toast.makeText(context, "Failed to delete package", Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}
