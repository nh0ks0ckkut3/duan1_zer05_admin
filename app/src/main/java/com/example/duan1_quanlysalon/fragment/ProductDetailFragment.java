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
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;

public class ProductDetailFragment extends Fragment {

    private Product product;
    private TextView tvName,tvPrice,tvGain,tvID,tvDelete,tvEdit,tvAmount,tvClassify;
    private ImageView ivAvatar;


    public ProductDetailFragment(Product product) {
        this.product = product;
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
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        tvName = view.findViewById(R.id.tvName);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvGain = view.findViewById(R.id.tvGain);
        tvID = view.findViewById(R.id.tvID);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvEdit = view.findViewById(R.id.tvEdit);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvClassify = view.findViewById(R.id.tvClassify);
        tvAmount = view.findViewById(R.id.tvAmount);

        tvName.setText(product.getName());
        tvClassify.setText(product.getClassify());
        tvID.setText(product.getId()+"");
        tvPrice.setText(product.getPrice()+",000 VNĐ");
        tvAmount.setText(product.getAmount()+"");
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "không thể xóa", Toast.LENGTH_SHORT).show();
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        tvGain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }
}