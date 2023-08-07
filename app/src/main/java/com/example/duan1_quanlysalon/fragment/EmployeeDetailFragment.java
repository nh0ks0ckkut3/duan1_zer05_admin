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
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.MainActivity;
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

    TextView tvDelete, tvEdit;
    EditText edName,edBirthDay,edGender,edPhoneNumber, edAddress,edClassify, edDateStart,edSalary;
    AlertDialog alertDialog;
    ImageView ivAvatar, ivBack;
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
        edName = view.findViewById(R.id.edName);
        edBirthDay = view.findViewById(R.id.edBirthDay);
        edGender = view.findViewById(R.id.edGender);
        edPhoneNumber = view.findViewById(R.id.edPhoneNumber);
        edAddress = view.findViewById(R.id.edAddress);
        edClassify = view.findViewById(R.id.edClassify);
        edDateStart = view.findViewById(R.id.edDateStart);
        edSalary = view.findViewById(R.id.edSalary);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvEdit = view.findViewById(R.id.tvEdit);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivBack = view.findViewById(R.id.ivBack);

        edName.setText(employee.getName());
        edBirthDay.setText(employee.getBirthDay());
        edGender.setText(employee.getGender());
        edPhoneNumber.setText(employee.getUserName());
        edAddress.setText(employee.getAddress());
        edClassify.setText(employee.getClassify());
        edDateStart.setText(employee.getDayStartWork());
        edSalary.setText(employee.getSalary()+"");

        Glide.with(this).load(employee.getImg()).into(ivAvatar);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int salary = 0;
                try {
                    salary = Integer.parseInt(edSalary.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getContext(), "nhập số lương", Toast.LENGTH_SHORT).show();
                }
                Employee employeeEdit = new Employee(employee.getUserName(),edName.getText().toString(), edBirthDay.getText().toString(),
                        edGender.getText().toString(),edPhoneNumber.getText().toString(),edAddress.getText().toString(),edClassify.getText().toString(),
                        edDateStart.getText().toString(),salary);
                updateEmployee(employeeEdit);
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Employee_Fragment());
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
    private void updateEmployee(Employee employee){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.updateEmployee(employee)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdate, this::handleError)
        );
    }
    private void handleResponseUpdate(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "đã sửa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "đã có dữ liệu với hóa đơn, không thể xóa", Toast.LENGTH_SHORT).show();
        }
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
            alertDialog.dismiss();
            ((MainActivity)getContext()).replayFragment(new List_Employee_Fragment());
        }else{
            Toast.makeText(getContext(), "đã có dữ liệu với hóa đơn, không thể xóa", Toast.LENGTH_SHORT).show();
        }
    }


    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}