package com.example.duan1_quanlysalon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.Product;

import java.util.ArrayList;

public class ListSelectProductAdapter extends BaseAdapter {
    ArrayList<Product> list;
    int layout;
    Context context;
    ProductDAO productDAO;


    public ListSelectProductAdapter(ArrayList<Product> list, int layout, Context context, ProductDAO productDAO) {
        this.list = list;
        this.layout = layout;
        this.context = context;
        this.productDAO = productDAO;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        TextView txtTensp, txtGiasp;
        ImageView imgsp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if(view == null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();

            //anh xa
            viewHolder.txtTensp = view.findViewById(R.id.tensp_select_product);
            viewHolder.txtGiasp = view.findViewById(R.id.giasp_select_product);
            viewHolder.imgsp =  view.findViewById(R.id.img_select_product);

            view.setTag(viewHolder);
        }else {
            viewHolder =(ViewHolder)view.getTag();
        }

        viewHolder.txtTensp.setText(list.get(i).getName());
        viewHolder.txtGiasp.setText(list.get(i).getPrice()+"");
        return view;
    }


}
