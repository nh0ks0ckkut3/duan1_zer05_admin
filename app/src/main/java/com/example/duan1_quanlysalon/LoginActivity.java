package com.example.duan1_quanlysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassWord;
    CheckBox chkRemember;
    SharedPreferences sharedPreferences;

    String user, pass;
    EmployeeDAO employeeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btn_login = findViewById(R.id.btn_login);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        chkRemember = findViewById(R.id.chkRemember);
        employeeDAO = new EmployeeDAO(this);

        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("isRemember", false);
        if (check){
            edtUserName.setText(sharedPreferences.getString("isUser", ""));
            edtPassWord.setText(sharedPreferences.getString("isPass", ""));
        }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isRemember = chkRemember.isChecked();
                sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isRemember", isRemember);
                editor.putString("isUser", user);
                editor.putString("isPass", pass);
                editor.apply();

                user = edtUserName.getText().toString();
                pass = edtPassWord.getText().toString();

                if (user.length() > 0 && pass.length() > 0){
                    for(Employee employee: employeeDAO.getListEmployee()){
                        if(user.equals(employee.getUserName()) && pass.equals(employee.getPassWord())){
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Sai user hoặc pass", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Nhập user và pass", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}