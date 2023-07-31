package com.example.duan1_quanlysalon;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassWord;
    CheckBox chkRemember;
    ImageButton icon_pass;
    SharedPreferences sharedPreferences;


    String userName, pass;
    EmployeeDAO employeeDAO;
    boolean isVerify = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = findViewById(R.id.btn_login);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        icon_pass = findViewById(R.id.icon_pass);
        chkRemember = findViewById(R.id.chkRemember);
        employeeDAO = new EmployeeDAO(this);
        TextView tv = findViewById(R.id.textView3);

        icon_pass.setBackgroundResource(R.drawable.hidepass);
        icon_pass.setOnClickListener(new View.OnClickListener() {
            boolean hidePass = false;
            @Override
            public void onClick(View v) {
                if (hidePass){
                    edtPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
                    icon_pass.setBackgroundResource(R.drawable.showpass);
                }else{
                    edtPassWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    icon_pass.setBackgroundResource(R.drawable.hidepass);
                }
                hidePass =! hidePass;
                edtPassWord.setSelection(edtPassWord.getText().length());
            }
        });

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("isRemember", false);
        if(check){
//            startActivity(new Intent(MainActivity.this,HomeActivity.class));
            String userName = sharedPreferences.getString("userName","");
            String pass = sharedPreferences.getString("pass","");
            chkRemember.setChecked(check);
            edtUserName.setText(userName);
            edtPassWord.setText(pass);
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = edtUserName.getText().toString();
                pass = edtPassWord.getText().toString();

                if(userName.length()>0 && pass.length() > 0){
                    loginCallAPI(userName, pass);
                }else{
                    Toast.makeText(LoginActivity.this, "không để trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void loginCallAPI(String userName, String pass) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.verify(new Employee(userName, pass))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Employee employee) {
        if(employee.getCode()==1){
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userName",employee.getUserName());
            bundle.putString("classify",employee.getClassify());
            intent.putExtras(bundle);
            boolean isRemember = chkRemember.isChecked();
            sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isRemember", isRemember);
            editor.putString("userName", userName);
            editor.putString("pass", pass);
            editor.apply();
            finish();
            startActivity(intent);
        }else{
            Toast.makeText(this, "sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(LoginActivity.this, "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}
