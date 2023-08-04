package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.EmployeeAdapter;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmployeeDetailFragment extends Fragment {

    TextView tvName, tvAge, tvGender, tvPhoneNumber, tvAddress, tvID, tvClassify, tvDateStart, tvSalary, tvDelete, tvEdit;
    AlertDialog alertDialog;
    private Employee employee;

    public EmployeeDetailFragment(Employee employee){
        this.employee = employee;
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
        View view = inflater.inflate(R.layout.fragment_employee_detail, container, false);
        tvName = view.findViewById(R.id.tvName);
        tvAge = view.findViewById(R.id.tvAge);
        tvGender = view.findViewById(R.id.tvGender);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvID = view.findViewById(R.id.tvID);
        tvClassify = view.findViewById(R.id.tvClassify);
        tvDateStart = view.findViewById(R.id.tvDateStart);
        tvSalary = view.findViewById(R.id.tvSalary);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvEdit = view.findViewById(R.id.tvEdit);

        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm_delete,null);
        builder.setView(view);

        TextView tvContent = view.findViewById(R.id.tvContent);
        AppCompatButton btnCancel = view.findViewById(R.id.btnCancel);
        AppCompatButton btnOk = view.findViewById(R.id.btnOke);
        tvContent.setText("Xác nhận xóa nhân viên");
        alertDialog = builder.create();
        alertDialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee(employee.getUserName());
            }
        });


    }
    private void deleteEmployee(String userName){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.deleteEmployee(userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void handleResponse(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "đã xóa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }


    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}