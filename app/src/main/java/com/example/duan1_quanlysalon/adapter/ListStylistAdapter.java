package com.example.duan1_quanlysalon.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;

import java.util.ArrayList;

public class ListStylistAdapter extends RecyclerView.Adapter<ListStylistAdapter.ViewHolder> {
    ArrayList<Employee> list;
    Context context;
    EmployeeDAO employeeDAO;


    public ListStylistAdapter(ArrayList<Employee> list, Context context, EmployeeDAO employeeDAO){
        this.list = list;
        this.context = context;
        this.employeeDAO = employeeDAO;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_stylist_add_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtHoten.setText(list.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtHoten;
        ImageView imgEmployee;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHoten = itemView.findViewById(R.id.hoten_item_stylist_add_booking);
            imgEmployee = itemView.findViewById(R.id.img_item_stylist_add_booking);
        }
    }
}
