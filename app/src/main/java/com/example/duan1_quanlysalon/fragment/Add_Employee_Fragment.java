package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Add_Employee_Fragment extends Fragment {

    TextView id_add_employee, name_add_employee,id_add_birth,
            id_add_gender, id_add_phonenum, id_add_email, id_add_address, id_add_usname,
            id_add_password, id_add_classify, id_add_salary, id_add_daywork;
    AppCompatButton btn_complete_add_employee;
    ImageView img_add_employee;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__employee_, container, false);
        mapping(view);
        id_add_employee.setFocusable(false);
        btn_complete_add_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = name_add_employee.getText().toString();
                String userName = id_add_usname.getText().toString();
                String birthDay = id_add_birth.getText().toString();
                String passWord = id_add_password.getText().toString();
                String gender = id_add_gender.getText().toString();
                String phoneNumber = id_add_phonenum.getText().toString();
                String email = id_add_email.getText().toString();
                String img = "";
                int salary=0;
                try {
                    salary = Integer.parseInt(id_add_salary.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getContext(), "nhập lương cơ bản", Toast.LENGTH_SHORT).show();
                }
                String address = id_add_address.getText().toString();
                String classify = id_add_classify.getText().toString();
                String dayStartWork = id_add_daywork.getText().toString();

                if(!(name.length() < 1 || userName.length() < 1 ||
                        passWord.length() < 1 || classify.length() < 1)){
                    Employee employeeAdd = new Employee(userName, passWord, name, birthDay, phoneNumber, email, address, gender, salary, dayStartWork, classify, img);
                    addEmployee(employeeAdd);
                }
            }
        });
        return view;
    }
    private void mapping(View view){
        img_add_employee = view.findViewById(R.id.img_add_employee);
        id_add_employee = view.findViewById(R.id.id_add_employee);
        name_add_employee = view.findViewById(R.id.name_add_employee);
        id_add_birth = view.findViewById(R.id.id_add_birth);
        id_add_gender = view.findViewById(R.id.id_add_gender);
        id_add_phonenum = view.findViewById(R.id.id_add_phonenum);
        id_add_email = view.findViewById(R.id.id_add_email);
        id_add_address = view.findViewById(R.id.id_add_address);
        id_add_usname = view.findViewById(R.id.id_add_usname);
        id_add_password = view.findViewById(R.id.id_add_password);
        id_add_classify = view.findViewById(R.id.id_add_classify);
        id_add_salary = view.findViewById(R.id.id_add_salary);
        id_add_daywork = view.findViewById(R.id.id_add_daywork);
        btn_complete_add_employee = view.findViewById(R.id.btn_complete_add_employee);
    }
    private void addEmployee(Employee employee){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.addEmployee(employee)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    private void handleResponse(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "đã thêm", Toast.LENGTH_SHORT).show();
            ((MainActivity)getContext()).replayFragment(new List_Employee_Fragment());
        }
        else Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
    }
}