package com.example.duan1_quanlysalon.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.database.BillDAO;
import com.example.duan1_quanlysalon.model.Bill;

import java.util.ArrayList;
import java.util.Calendar;

public class TotalDayFragment extends Fragment {
    TextView tvTotal, tvStatus, tvOut;
    EditText edtDay;
    ImageButton imgSearch;

    ArrayList<Bill> list;
    BillDAO billDAO;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_total_day, container, false);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvOut = view.findViewById(R.id.tvOut);
        edtDay = view.findViewById(R.id.edtDay);
        imgSearch = view.findViewById(R.id.imgSearch);

        list = new ArrayList<>();
        billDAO = new BillDAO(getContext());

        edtDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDaiLog();
            }
        });

        tvOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = edtDay.getText().toString();
               if (day.length() > 0){
                   String status = "", time = "";
                   int total = 0;
                   list = billDAO.getTotalDay(day);
                   for(Bill bill: list){
                       total += bill.getTotalPrice();
                       status = bill.getStatus();
                       time = bill.getTime();
                   }

                   if (day.equals(time)){
                       tvTotal.setText(total+" Đồng");
                       tvStatus.setText(status);
                   }else{
                       Toast.makeText(getContext(), "Điều sai ngày!", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(getContext(), "Điền ngày cần thống kê", Toast.LENGTH_SHORT).show();
               }
            }
        });

        return view;

    }
    public void showDateDaiLog(){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int month = i1 + 1;
                        edtDay.setText(i2 + " / " + month + " / " + i);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}
