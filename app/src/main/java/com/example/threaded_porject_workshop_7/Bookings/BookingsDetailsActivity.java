package com.example.threaded_porject_workshop_7.Bookings;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threaded_porject_workshop_7.DataSource;
import com.example.threaded_porject_workshop_7.MainActivity;
import com.example.threaded_porject_workshop_7.MenuActivity;
import com.example.threaded_porject_workshop_7.R;
import com.example.threaded_porject_workshop_7.Validator;
import com.example.threaded_porject_workshop_7.model.Booking;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;

public class BookingsDetailsActivity extends AppCompatActivity {
    Button btnSave;
    Button btnDelete;
    EditText etDate;
    EditText etBookingNo;
    EditText etBookingTotal;
    EditText etTravelerCount;
    EditText etCustomerId;
    EditText etTripTypeId;
    EditText etPackageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_bookings_details);

        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        etDate = findViewById(R.id.etBookingDate);
        etBookingNo = findViewById(R.id.etBookingNo);
        etBookingTotal = findViewById(R.id.etBookingTotal);
        etTravelerCount = findViewById(R.id.etTravelerCount);
        etCustomerId = findViewById(R.id.etCustomerId);
        etTripTypeId = findViewById(R.id.etTripTypeId);
        etPackageId = findViewById(R.id.etPackageId);

        Intent intent = getIntent();

        //get mode
        String mode = intent.getStringExtra("mode");

        //edit mode
        if(mode.equals("edit"))
        {
            initBooking(intent);
            btnDelete.setEnabled(true);
        }
        else
        {
            btnDelete.setEnabled(false);
        }

        //Save added/updated booking into the database and return to bookings list
        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //Add/ modify data
                saveData(intent);
            }
        });

        //Delete selected booking and return to bookings list
        btnDelete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //get selected booking id
                Booking selectedBooking = (Booking) intent.getSerializableExtra("booking");
                int bookingId = selectedBooking.getId();

                //Delete selected booking
                DataSource.deleteBooking(bookingId, getApplicationContext());

                //Exit this activity
                Intent intent = new Intent(getApplicationContext(), BookingsActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
     * Have menu buttons redirect to different pages
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
                break;
            case R.id.miLogout:
                Intent logoutIntent = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(logoutIntent);
                break;
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), BookingsActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Display selected booking information into its respective fields
     * @param intent
     */
    private void initBooking(Intent intent)
    {
        Booking selectedBooking = (Booking) intent.getSerializableExtra("booking");
        etDate.setText(selectedBooking.getBookingDate().toString());
        etBookingNo.setText(selectedBooking.getBookingNo());
        etBookingTotal.setText(selectedBooking.getBookingTotal().toString());
        etTravelerCount.setText(selectedBooking.getTravelerCount().toString());
        etCustomerId.setText(selectedBooking.getCustomer().toString());
        etTripTypeId.setText(selectedBooking.getTripType());
        etPackageId.setText(selectedBooking.get_package().toString());
    }

    private void saveData(Intent intent)
    {
        //get values from text fields
        int bookingId;
        if (intent.getStringExtra("mode").equals("edit"))
        {
            Booking selectedBooking = (Booking) intent.getSerializableExtra("booking");
            bookingId = selectedBooking.getId();
        }
        else
        {
            bookingId = 0;
        }

        //Validates data before modifying the database
        if (Validator.isPresent(etDate) &&
                Validator.isPresent(etBookingNo) &&
                Validator.isIntPositive(etCustomerId) &&
                Validator.isPresent(etTripTypeId) &&
                Validator.isIntPositive(etPackageId) &&
                Validator.isDoublePositive(etBookingTotal) &&
                Validator.isDoublePositive(etTravelerCount))
        {
            //collect data from text fields
            Date bookingDate = Date.valueOf(etDate.getText().toString());
            String bookingNo = etBookingNo.getText().toString();
            double bookingTotal = Double.parseDouble(etBookingTotal.getText().toString());
            double travelerCount = Double.parseDouble(etTravelerCount.getText().toString());
            int customerId = Integer.parseInt(etCustomerId.getText().toString());
            String tripTypeId = etTripTypeId.getText().toString();
            int packageId = Integer.parseInt(etPackageId.getText().toString());

            //create booking JSON object
            JSONObject bookingJSON = new JSONObject();
            try
            {
                bookingJSON.put("id", bookingId + "");
                bookingJSON.put("bookingDate", bookingDate + "");
                bookingJSON.put("bookingNo", bookingNo + "");
                bookingJSON.put("bookingTotal", bookingTotal + "");
                bookingJSON.put("travelerCount", travelerCount + "");
                bookingJSON.put("customer", customerId + "");
                bookingJSON.put("tripType", tripTypeId + "");
                bookingJSON.put("_package", packageId + "");
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

            //update booking with new data
            if(intent.getStringExtra("mode").equals("edit"))
            {
                //url to modify booking
                DataSource.postBooking(bookingJSON, getApplicationContext());
            }
            else // add booking with new data
            {
                DataSource.addBooking(bookingJSON, getApplicationContext());
            }

            //Exit this activity
            Intent bookingIntent = new Intent(getApplicationContext(), BookingsActivity.class);
            startActivity(bookingIntent);
            finish();
        }
    }
}