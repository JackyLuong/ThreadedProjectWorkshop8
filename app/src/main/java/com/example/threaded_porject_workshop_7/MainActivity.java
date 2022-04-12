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

public class MainActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;
    AlertDialog.Builder loginAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        loginAlert = new AlertDialog.Builder(this);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check login credentials if present
                if(!etUsername.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()){

                    //validate if credentials are valid
                    if((etUsername.getText().toString().equals("test") &&
                            (etPassword.getText().toString().equals("test")))){
                        //show login success toast
                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
                        Log.d("mark", "Login Successful");

                        String name = "John Doe";
                        Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                        intent.putExtra("name",name);
                        startActivity(intent);
                        finish();
                    }

                    //invalid login details
                    else{
                        //call method to show the alert
                        showAlertDialog(R.string.alert_title_failedAuth,R.string.alert_msg_failedAuth);
                    }
                }
                //missing login fields entry
                else{
                    //call method to show the alert
                    showAlertDialog(R.string.alert_title_missingInput,R.string.alert_msg_missingInput);

                }

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
}