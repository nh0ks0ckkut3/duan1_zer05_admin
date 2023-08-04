package com.example.duan1_quanlysalon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Service;

public class ServiceDetailFragment extends Fragment {

    private Service service;
    private TextView tvName,tvPrice,tvClassify,tvID,tvDelete,tvEdit;
    private ImageView ivAvatar;


    public ServiceDetailFragment(Service service) {
        this.service = service;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);
        tvName = view.findViewById(R.id.tvName);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvClassify = view.findViewById(R.id.tvClassify);
        tvID = view.findViewById(R.id.tvID);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvEdit = view.findViewById(R.id.tvEdit);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        
        tvName.setText(service.getName());
        tvClassify.setText(service.getClassifyEmployee());
        tvID.setText(service.getId()+"");
        tvPrice.setText(service.getPrice()+",000 VNĐ");
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "không thể xóa", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}