package com.example.duan1_quanlysalon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    ArrayList<Product> list;
    Context context;
    ProductDAO productDAO;


    public ProductAdapter(ArrayList<Product> list, Context context, ProductDAO productDAO){
        this.list = list;
        this.context = context;
        this.productDAO = productDAO;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTensp.setText(list.get(position).getName());
        holder.txtGiasp.setText(String.valueOf(list.get(position).getPrice()));
        holder.txtPhanloai.setText(list.get(position).getClassify());
        holder.txtThuonghieu.setText(list.get(position).getBrand());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTensp, txtGiasp, txtPhanloai, txtThuonghieu;
        ImageView imgSanpham;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTensp = itemView.findViewById(R.id.tensp_item_list_sanpham);
            txtGiasp = itemView.findViewById(R.id.giasp_item_list_sanpham);
            txtPhanloai = itemView.findViewById(R.id.phanloai_item_list_sanpham);
            txtThuonghieu = itemView.findViewById(R.id.thuonghieu_item_list_sanpham);
            imgSanpham = itemView.findViewById(R.id.img_item_list_sanpham);
        }
    }
}