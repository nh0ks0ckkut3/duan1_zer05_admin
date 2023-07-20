package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.CustomerDAO;
import com.example.duan1_quanlysalon.model.Customer;

import java.util.ArrayList;

public class SearchPhoneFragment extends Fragment {
    TextView tvPhoneCustomer, tvNameCustomer, tvOut;
    ImageButton imgSearch;
    EditText edtSearchPhone;
    ArrayList<Customer> list;
    CustomerDAO customerDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_search_phonenumber, container, false);
        tvPhoneCustomer = view.findViewById(R.id.tvPhoneCustomer);
        tvNameCustomer = view.findViewById(R.id.tvNameCustomer);
        tvOut = view.findViewById(R.id.tvOut);
        imgSearch = view.findViewById(R.id.imgSearch);
        edtSearchPhone = view.findViewById(R.id.edtSearchPhone);
        list = new ArrayList<>();
        customerDAO = new CustomerDAO(getContext());

        tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sdt = edtSearchPhone.getText().toString();
                String name = "", phone = "";
                if (sdt.length() > 0){
                    list = customerDAO.checkInforCustomer(sdt);
                    for(Customer customer : list){
                         name = customer.getName();
                         phone = customer.getPhoneNumber();
                    }
                    tvNameCustomer.setText(name);
                    tvPhoneCustomer.setText(phone);
                }else{
                    Toast.makeText(getContext(), "Nhập số điện thoại", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
}
