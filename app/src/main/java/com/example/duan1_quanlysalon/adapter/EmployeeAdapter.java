package com.example.duan1_quanlysalon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {
    ArrayList<Employee> list;
    Context context;
    EmployeeDAO employeeDAO;


    public EmployeeAdapter(ArrayList<Employee> list, Context context, EmployeeDAO employeeDAO){
        this.list = list;
        this.context = context;
        this.employeeDAO = employeeDAO;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_list_employee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      //  Employee employee =list.get(position);
        holder.tvName.setText(list.get(position).getName());
        holder.tvmanv.setText(String.valueOf(list.get(position).getUserName()));


//        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                showDialog();
//                return true;
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvName, tvmanv;
      //  ImageView imgEmployee;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvmanv = itemView.findViewById(R.id.tvmanv);
        }
    }
    public void showDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.dialog_choose_uod,null);
        builder.setView(view);

        Button btnSua=view.findViewById(R.id.btnUpdate_dialog_choose_uod);
        Button btnXoa=view.findViewById(R.id.btnDelete_dialog_choose_uod);

        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dùng tạm add_employee sau này chỉnh sửa thêm vào sau
                showAddDialog();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xác nhận xóa
            }
        });
        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
    public void showAddDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.add_employee,null);
        builder.setView(view);


        AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }
}