package com.example.threaded_porject_workshop_7.Customers;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.threaded_porject_workshop_7.MainActivity;
import com.example.threaded_porject_workshop_7.MenuActivity;
import com.example.threaded_porject_workshop_7.R;
import com.example.threaded_porject_workshop_7.model.Customers;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.concurrent.Executors;

public class CustomerDetailsActivity extends AppCompatActivity {

    //declare class variables
    Button btnSave,btnDelete;
    EditText etFirstName,etLastName,etAddress,etCity,etProvince,
            etPostal,etCountry,etHomePhone,etBusPhone,etEmail,etAgentId;

    AlertDialog.Builder customerAlert;

    String action;
    int custId;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_customer_details);

        //initialize class variables
        requestQueue = Volley.newRequestQueue(this);
        customerAlert = new AlertDialog.Builder(this);

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

        //capture passed customer object
        Customers customer = (Customers) getIntent().getSerializableExtra("customer");


        //get action
        action = getIntent().getStringExtra("action");

        //check customer object
        if(customer != null){
            //get the customer id
            custId = customer.getCustId();
            //populate edittext field with customer object's properties
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

        //save button for new and update customer

        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //validate first name and last name are filled out
                if(!etFirstName.getText().toString().isEmpty() && !etLastName.getText().toString().isEmpty()){
                    //set default agentID
                    int agentId = 1;
                    if(!etAgentId.getText().toString().isEmpty() ){
                        //if agentId is define, get the value
                        agentId = Integer.parseInt( etAgentId.getText().toString());
                    }

                    //instantiate customer object from edit text value
                    Customers customer = new Customers(
                            custId,
                            etFirstName.getText().toString(),
                            etLastName.getText().toString(),
                            etAddress.getText().toString(),
                            etCity.getText().toString(),
                            etProvince.getText().toString(),
                            etPostal.getText().toString(),
                            etCountry.getText().toString(),
                            etHomePhone.getText().toString(),
                            etBusPhone.getText().toString(),
                            etEmail.getText().toString(),
                            agentId
                    );

                    //execute
                    Executors.newSingleThreadExecutor().execute(new PutPostCustomer(customer, action));
                }else{
                    //show error
                    showAlertDialog(R.string.alert_title_customerInput,R.string.alert_msg_customerInput);
                }

            }

            private  void showAlertDialog(int title, int message) {
                //set alert dialog message
                customerAlert.setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel(); //cancel dialog upon click
                            }
                        });
                //create dialog box
                AlertDialog alert = customerAlert.create();
                //set title and show
                alert.setTitle(title);
                alert.show();

                //set the OK dialog button to center.
                final Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
                LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
                positiveButton.setLayoutParams(positiveButtonLL);

            }
        });

        //delete selected customer
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //execute delete
                Executors.newSingleThreadExecutor().execute(new DeleteCustomer(custId));

            }
        });


    }

    //Inner class to add or update customer
    class PutPostCustomer implements Runnable{

        //class variables
        private  Customers customer;
        private String action;

        //variables needed to invoke rest api
        private int method;
        private String url;
        private JSONObject object;

        //constructor accepts customer object and action string
        public PutPostCustomer(Customers customer, String action){
            this.customer = customer;
            this.action = action;
        }


        @Override
        public void run() {
            //if action is to add customer
            if(action != null && action.equals("AddCustomer")){
                //send JSON data to customer add REST api
                method = 2; //PUT method
                url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/customers/add-customer";
                object = new JSONObject();
                try{
                    object.put("custFirstName", customer.getCustFirstName());
                    object.put("custLastName", customer.getCustLastName());
                    object.put("custAddress", customer.getCustAddress());
                    object.put("custCity", customer.getCustCity());
                    object.put("custProv", customer.getCustProvince());
                    object.put("custPostal", customer.getCustPostal());
                    object.put("custCountry", customer.getCustCountry());
                    object.put("custHomePhone", customer.getCustHomeNum());
                    object.put("custBusPhone", customer.getCustBusNum());
                    object.put("custEmail", customer.getCustEmail());
                    object.put("agent", customer.getCustAgentId());

                }catch (JSONException e){
                    e.printStackTrace();
                }
                //if action is to update customer
            } else {
                //send JSON data to customer update REST api
                method = 1; //POST method
                url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/customers/post-customer";
                object = new JSONObject();
                try{
                    object.put("id", customer.getCustId());
                    object.put("custFirstName", customer.getCustFirstName());
                    object.put("custLastName", customer.getCustLastName());
                    object.put("custAddress", customer.getCustAddress());
                    object.put("custCity", customer.getCustCity());
                    object.put("custProv", customer.getCustProvince());
                    object.put("custPostal", customer.getCustPostal());
                    object.put("custCountry", customer.getCustCountry());
                    object.put("custHomePhone", customer.getCustHomeNum());
                    object.put("custBusPhone", customer.getCustBusNum());
                    object.put("custEmail", customer.getCustEmail());
                    object.put("agent", customer.getCustAgentId());

                }catch (JSONException e){
                    e.printStackTrace();
                }

            }


            Log.d("mark", String.valueOf(object));

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,object,
                    (response) -> {
                        Log.d("mark", "response=" + response);
                        VolleyLog.wtf(response.toString(), "utf-8");

                        //display result message
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "Opening Customers..", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    },
                    //display error
                    (error) -> {
                        Log.d("mark", "Error: "+ error);
                        VolleyLog.wtf(error.getMessage(), "utf-8");

                    });
            requestQueue.add(jsonObjectRequest);

        }
    }

    //Inner class to delete customer
    class DeleteCustomer implements Runnable{
        //declare customer id to delete
        private int customerId;

        public DeleteCustomer(int custId){
            customerId = custId;
        }

        @Override
        public void run() {
            //call rest api to delete customer data
            JSONObject obj = new JSONObject();
            String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/customers/delete-customer/" + customerId;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {
                    VolleyLog.wtf(response.toString(), "utf-8");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //display response
                                Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_LONG).show();

                                //return to customer activity
                                Toast.makeText(getApplicationContext(), "Opening Customers..", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                                startActivity(intent);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.wtf(error.getMessage(), "utf-8");

                }
            });
            requestQueue.add(jsonObjectRequest);

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
                Intent intent = new Intent(getApplicationContext(), CustomersActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }
}