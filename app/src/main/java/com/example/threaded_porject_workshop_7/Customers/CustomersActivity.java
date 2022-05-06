package com.example.threaded_porject_workshop_7.Customers;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threaded_porject_workshop_7.MainActivity;
import com.example.threaded_porject_workshop_7.MenuActivity;
import com.example.threaded_porject_workshop_7.R;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.threaded_porject_workshop_7.model.Customers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

public class CustomersActivity extends AppCompatActivity
{
    //declare instance variables
    ListView lvCustomers;
    Button btnCustAdd;
    ArrayList<Customers> customersList;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //set actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_customers);

        // instantiate
        lvCustomers = findViewById(R.id.lvCustomers);
        btnCustAdd = findViewById(R.id.btnAddCustomers);
        customersList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        // execute get customer class
        Executors.newSingleThreadExecutor().execute(new GetCustomers());

        //show customer details
        lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                intent.putExtra("customer", customersList.get(i));
                finish();
                startActivity(intent);
            }
        });

        //show new customer form.
        btnCustAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                //set action for adding customer
                intent.putExtra("action","AddCustomer");
                finish();
                startActivity(intent);

            }
        });
    }

    //blueprint to retrieve customers data
    private class GetCustomers implements Runnable{

        @Override
        public void run() {

            //declare rest api endpoint
            String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/customers";
            //initiate request to api
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                //process response
                @Override
                public void onResponse(String response) {

                    //instantiate customized adapter
                    CustomersListAdapter adapter = new CustomersListAdapter(getApplicationContext(),R.layout.activity_customers, customersList);
                    //process response json
                    try{
                        JSONArray jsonArray  = new JSONArray(response);
                        //loop through the json array response
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            //instantiate customer object using the response json data
                            Customers customer = new Customers(
                                    jsonObject.getInt("id"),
                                    jsonObject.getString("custFirstName"),
                                    jsonObject.getString("custLastName"),
                                    jsonObject.getString("custAddress"),
                                    jsonObject.getString("custCity"),
                                    jsonObject.getString("custProv"),
                                    jsonObject.getString("custPostal"),
                                    jsonObject.getString("custCountry"),
                                    jsonObject.getString("custHomePhone"),
                                    jsonObject.getString("custBusPhone"),
                                    jsonObject.getString("custEmail"),
                                    jsonObject.getInt("agent")
                            );
                            //add customer object to the adapter
                            adapter.add(customer);
                        }
                        //catch exception
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        //set the listview adapter
                        public void run() {
                            lvCustomers.setAdapter(adapter);
                        }
                    });
                }
                //process error
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                }
            });
            requestQueue.add(stringRequest);
        }
    }


    // inflate the actionbar menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu)
    {
        getMenuInflater().inflate(R.menu.travel_experts_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // inflate the actionbar menu
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
                Intent intent = new Intent(CustomersActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }
}