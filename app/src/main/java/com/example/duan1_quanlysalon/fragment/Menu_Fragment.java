package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.DSdichvuActivity;
import com.example.duan1_quanlysalon.DSnhanvienActivity;
import com.example.duan1_quanlysalon.DSsanphamActivity;
import com.example.duan1_quanlysalon.LoginActivity;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.google.android.material.navigation.NavigationView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Menu_Fragment extends Fragment {

    TextView tvName, tvClassify,tvBirthDay, tvUserName,
            tvListEmployee,tvListService, tvListProduct, tvHairModel,
            tvRoles, tvMinorFee, tvOption, tvChangePassWord,
            tvGetOTPVerify, tvLogOut;
    Employee currentUser;
    ImageView ivAvatar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mapping(view);

        tvName.setText(currentUser.getName());
        tvClassify.setText(currentUser.getClassify());
        tvBirthDay.setText("Tuổi: " + currentUser.getAge());
        tvUserName.setText("Tài khoản: "+currentUser.getUserName());
        Glide.with(this).load(currentUser.getImg()).into(ivAvatar);

        ((MainActivity)getContext()).toolbar.setVisibility(View.GONE);

        tvListEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Employee_Fragment());
            }
        });
        tvListService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Service_Fragment());
            }
        });

        tvListProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Product_Fragment());
            }
        });
        tvHairModel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "tính năng này chưa hoàn thiện", Toast.LENGTH_SHORT).show();
            }
        });
        tvMinorFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "tính năng này chưa hoàn thiện", Toast.LENGTH_SHORT).show();
            }
        });
        tvOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "tính năng này chưa hoàn thiện", Toast.LENGTH_SHORT).show();
            }
        });
        tvGetOTPVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "tính năng này chưa hoàn thiện", Toast.LENGTH_SHORT).show();
            }
        });
        tvChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogChangePassword();
            }
        });
        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogConfirm();
            }
        });


        return view;
    }
    private void mapping(View view){
        tvName = view.findViewById(R.id.tvName);
        tvClassify = view.findViewById(R.id.tvClassify);
        tvBirthDay = view.findViewById(R.id.tvBirthDay);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvListEmployee = view.findViewById(R.id.tvListEmployee);
        tvListService = view.findViewById(R.id.tvListService);
        tvListProduct = view.findViewById(R.id.tvListProduct);
        tvHairModel = view.findViewById(R.id.tvHairModel);
        tvRoles = view.findViewById(R.id.tvRoles);
        tvMinorFee = view.findViewById(R.id.tvMinorFee);
        tvOption = view.findViewById(R.id.tvOption);
        tvGetOTPVerify = view.findViewById(R.id.tvGetOTPVerify);
        tvChangePassWord = view.findViewById(R.id.tvChangePassWord);
        tvLogOut = view.findViewById(R.id.tvLogOut);
        currentUser = ((MainActivity)getContext()).currentUser;
        ivAvatar = view.findViewById(R.id.ivAvatar);
    }
    private void showDialogChangePassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.change_password,null);
        EditText edOldPass = view.findViewById(R.id.edOldPass);
        EditText edNewPass = view.findViewById(R.id.edNewPass);
        EditText edConfirmNewPass = view.findViewById(R.id.edConfirmNewPass);
        TextView btnOke = view.findViewById(R.id.btnOke);

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();

        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPass = edOldPass.getText().toString();
                String newPass = edNewPass.getText().toString();
                String confirmNewPass = edConfirmNewPass.getText().toString();

                if(oldPass.length()==0 || newPass.length()==0 || confirmNewPass.length() == 0){
                    Toast.makeText(getContext(), "không bỏ trống", Toast.LENGTH_SHORT).show();
                }else if(!newPass.equals(confirmNewPass)){
                    Toast.makeText(getContext(), "mật khẩu mới không trùng", Toast.LENGTH_SHORT).show();
                }else if(newPass.equals(oldPass)){
                    Toast.makeText(getContext(), "giống mật khẩu cũ", Toast.LENGTH_SHORT).show();
                }else if(!oldPass.equals(currentUser.getPassWord())){
                    Toast.makeText(getContext(), "nhập mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                }else if(newPass.length()<5){
                    Toast.makeText(getContext(), "mật khẩu ít nhất 5 ký tự", Toast.LENGTH_SHORT).show();
                }else{
                    Employee employeeEdit = new Employee(currentUser.getUserName(),newPass);
                    changePassWordAPI(employeeEdit);
                }

            }
        });
    }
    private void changePassWordAPI(Employee employee){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.changePassWord(employee)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "đã lưu", Toast.LENGTH_SHORT).show();
            ((MainActivity)getContext()).finish();
            Intent intent = new Intent(getContext(), LoginActivity.class);
            ((MainActivity)getContext()).startActivity(intent);
        }else{
            Toast.makeText(getContext(), "errol!", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi đường truyền", Toast.LENGTH_SHORT).show();
    }

    private void showDialogConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_confirm_delete,null);
        TextView btnCancel = view.findViewById(R.id.btnCancel);
        TextView btnOke = view.findViewById(R.id.btnOke);
        TextView title = view.findViewById(R.id.tvContent);
        title.setText("Bạn muốn đăng xuất?");

        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btnOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).finish();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                ((MainActivity)getContext()).startActivity(intent);
            }
        });
    }

}
