package com.example.threaded_porject_workshop_7;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    AlertDialog.Builder loginAlert;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loginAlert = new AlertDialog.Builder(this);
        requestQueue = Volley.newRequestQueue(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                if(!username.isEmpty() && !password.isEmpty()){
                    Executors.newSingleThreadExecutor().execute(new AgentLogin(username , password));
                }
                else
                {
                    showAlertDialog(R.string.alert_title_missingInput,R.string.alert_msg_missingInput);
                }

//                //check login credentials if present
//                if(!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){
//
//                    //validate if credentials are valid
//                    if((etUsername.getText().toString().equals("test") &&
//                            (etPassword.getText().toString().equals("test")))){
//                        //show login success toast
//                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
//                        Log.d("mark", "Login Successful");
//
//                        String name = "John Doe";
//                        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
//                        intent.putExtra("name",name);
//                        startActivity(intent);
//                        finish();
//                    }
//
//                    //invalid login details
//                    else{
//                        //call method to show the alert
//                        showAlertDialog(R.string.alert_title_failedAuth,R.string.alert_msg_failedAuth);
//                    }
//                }
//                //missing login fields entry
//                else{
//                    //call method to show the alert
//                    showAlertDialog(R.string.alert_title_missingInput,R.string.alert_msg_missingInput);
//                }
            }

            private void showAlertDialog(int title, int message) {
                //set alert dialog message
                loginAlert.setMessage(message)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel(); //cancel dialog upon click
                            }
                        });
                //create dialog box
                AlertDialog alert = loginAlert.create();
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

    }

    private class AgentLogin implements Runnable{

        private String username;
        private String password;

        public AgentLogin(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        public void run() {
            String url = "http://192.168.50.39:8080/TravelExperts_Web_Services_war_exploded/api/agents/get-agent/" + username;
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(getApplicationContext(),"jsonpass" + jsonObject.getString("agtPassword"),Toast.LENGTH_SHORT).show();
                        if(jsonObject.getString("agtPassword").equals(password)){//show login success toast
                            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();


                            String name = jsonObject.getString("agtFirstName") +" "+ jsonObject.getString("agtLastName");
                            Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                            intent.putExtra("name",name);
                            finish();
                            startActivity(intent);

                        } else {
                            //call method to show the alert
                            showAlertDialog(R.string.alert_title_failedAuth,R.string.alert_msg_failedAuth);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        //call method to show the alert
                        showAlertDialog(R.string.alert_title_failedAuth,R.string.alert_msg_failedAuth);
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.wtf(error.getMessage(), "utf-8");
                    //handle runtime errors
                    showAlertDialog(R.string.alert_title_unknown,R.string.alert_msg_unknown);

                }
            });
            requestQueue.add(stringRequest);

        }

        private void showAlertDialog(int title, int message) {
            //set alert dialog message
            loginAlert.setMessage(message)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel(); //cancel dialog upon click
                        }
                    });
            //create dialog box
            AlertDialog alert = loginAlert.create();
            //set title and show
            alert.setTitle(title);
            alert.show();

            //set the OK dialog button to center.
            final Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            LinearLayout.LayoutParams positiveButtonLL = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
            positiveButtonLL.width = ViewGroup.LayoutParams.MATCH_PARENT;
            positiveButton.setLayoutParams(positiveButtonLL);

        }
    }
}