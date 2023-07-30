package com.example.duan1_quanlysalon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.database.ServiceDAO;
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;

import java.util.ArrayList;

public class ListSelectServiceAdapter extends BaseAdapter {
    ArrayList<Service> list;
    int layout;
    Context context;
    ServiceDAO serviceDAO;


    public ListSelectServiceAdapter(ArrayList<Service> list, int layout, Context context, ServiceDAO serviceDAO) {
        this.list = list;
        this.layout = layout;
        this.context = context;
        this.serviceDAO = serviceDAO;
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
        TextView txtTendv, txtGiadv;
        ImageView imgsp;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListSelectServiceAdapter.ViewHolder viewHolder;

        if(view == null){
            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder = new ListSelectServiceAdapter.ViewHolder();

            //anh xa
            viewHolder.txtTendv = view.findViewById(R.id.tendv_select_service);
            viewHolder.txtGiadv = view.findViewById(R.id.giadv_select_service);
            viewHolder.imgsp =  view.findViewById(R.id.img_select_service);

            view.setTag(viewHolder);
        }else {
            viewHolder =(ListSelectServiceAdapter.ViewHolder)view.getTag();
        }

        viewHolder.txtTendv.setText(list.get(i).getName());
        viewHolder.txtGiadv.setText(list.get(i).getPrice()+"");
        return view;
    }
}
