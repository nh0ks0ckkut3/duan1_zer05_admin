package com.example.duan1_quanlysalon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassWord;
    CheckBox chkRemember;
    ImageButton icon_pass;
    SharedPreferences sharedPreferences;


    String userName, pass;
    EmployeeDAO employeeDAO;

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

                boolean isCorrect = false;
                if (userName.length() > 0 && pass.length() > 0){
                    for(Employee employee: employeeDAO.getListEmployee()) {
                        if (userName.equals(employee.getUserName()) && pass.equals(employee.getPassWord())) {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("employeeCurrent",employee);
                            intent.putExtras(bundle);

                            isCorrect = true;
                            boolean isRemember = chkRemember.isChecked();
                            sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isRemember", isRemember);
                            editor.putString("userName", userName);
                            editor.putString("pass", pass);
                            editor.apply();
                            finish();
                            startActivity(intent);
                        }
                    }
                    if(!isCorrect){
                        Toast.makeText(LoginActivity.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(LoginActivity.this, "Nhập user và pass", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
