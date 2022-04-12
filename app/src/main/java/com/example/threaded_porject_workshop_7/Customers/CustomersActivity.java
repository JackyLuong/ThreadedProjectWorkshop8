package com.example.threaded_porject_workshop_7.Customers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.threaded_porject_workshop_7.MenuActivity;
import com.example.threaded_porject_workshop_7.R;

import java.util.ArrayList;

public class CustomersActivity extends AppCompatActivity {
    ListView lvCustomers;
    Button btnCustAdd;
    ArrayList<Customers> customersList;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            setContentView(R.layout.activity_customers);

            lvCustomers = findViewById(R.id.lvCustomers);
            btnCustAdd = findViewById(R.id.btnCustAdd);
            customersList = new ArrayList<>();

            loadCustomers();
            CustomersListAdapter adapter = new CustomersListAdapter(this,R.layout.activity_customers, customersList);
            lvCustomers.setAdapter(adapter);

            lvCustomers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                    intent.putExtra("customer", customersList.get(i));
                    startActivity(intent);
                }
            });

            btnCustAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), CustomerDetailsActivity.class);
                    startActivity(intent);
                }
            });



        }

        private void loadCustomers() {
            customersList.add(new Customers(1,"John","Doe","123 Street",
                    "Calgary","AB","t2t123","Canada","","33333333",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(2,"Jean","Aoe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(3,"Lean","Boe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(4,"Mean","Zoe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(5,"Dean","Noe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(1,"John","Doe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(2,"Jean","Aoe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(3,"Lean","Boe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(4,"Mean","Zoe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(5,"Dean","Noe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(1,"John","Doe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(2,"Jean","Aoe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(3,"Lean","Boe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(4,"Mean","Zoe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
            customersList.add(new Customers(5,"Dean","Noe","123 Street",
                    "Calgary","AB","t2t123","Canada","123456","123445",
                    "tesst@gamil.com",1));
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CustomersActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}