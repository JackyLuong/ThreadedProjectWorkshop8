package com.example.threaded_porject_workshop_7.Customers;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.threaded_porject_workshop_7.R;

import java.util.ArrayList;
import java.util.List;

public class CustomersListAdapter extends ArrayAdapter<Customers> {

    private Context mcontext;
    private List<Customers> customerList ;


    public CustomersListAdapter(@NonNull Context context, int resource ,ArrayList<Customers> objects) {
        super(context, resource, objects);
        this.mcontext = context;
        customerList = objects;
    }

    //get view and attached to listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItem = convertView;

        if(listItem == null){
            listItem = LayoutInflater.from(mcontext).inflate(R.layout.customers_list_layout,parent,false);
        }

        Customers customer = customerList.get(position);
        TextView tvName = listItem.findViewById(R.id.tvName);
        TextView tvAddress = listItem.findViewById(R.id.tvAddress);
        TextView tvEmail = listItem.findViewById(R.id.tvEmail);
        TextView tvPhone = listItem.findViewById(R.id.tvPhone);

        tvName.setText(customer.getCustLastName()+", "+ customer.getCustFirstName());
        tvAddress.setText(customer.getCustCity()+", "+ customer.getCustProvince());

        String phoneNUm;
        if ((!customer.getCustHomeNum().isEmpty() )){
            phoneNUm = customer.getCustHomeNum();

        }else {
            phoneNUm = customer.getCustBusNum();
        }
        tvPhone.setText(phoneNUm);
        tvEmail.setText(customer.getCustEmail());


        return listItem;



    }
}
