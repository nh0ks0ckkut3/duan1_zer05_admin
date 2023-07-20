package com.example.duan1_quanlysalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Employee;

public class LoginActivity extends AppCompatActivity {
    EditText edtUserName, edtPassWord;
    CheckBox chkRemember;
    ImageButton icon_pass;
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
        icon_pass = findViewById(R.id.icon_pass);
        chkRemember = findViewById(R.id.chkRemember);
        employeeDAO = new EmployeeDAO(this);


        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        boolean check = sharedPreferences.getBoolean("isRemember", false);
        if (check){
            edtUserName.setText(sharedPreferences.getString("isUser", ""));
            edtPassWord.setText(sharedPreferences.getString("isPass", ""));
            chkRemember.setChecked(true);
        }

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

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = edtUserName.getText().toString();
                pass = edtPassWord.getText().toString();
                boolean isRemember = chkRemember.isChecked();

                sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("isRemember", isRemember);
                editor.putString("isUser", user);
                editor.putString("isPass", pass);
                editor.apply();
                if (user.length() > 0 && pass.length() > 0){
                    String ten ="", mk="";
                    for(Employee employee: employeeDAO.getListEmployee()){
                        if(user.equals(employee.getUserName()) && pass.equals(employee.getPassWord())){
<<<<<<< HEAD
                            ten = employee.getUserName();
                            mk = employee.getPassWord();
=======
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            //finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Sai user hoặc pass", Toast.LENGTH_SHORT).show();
>>>>>>> 569aeb37634b280c4e9a111ee82332a2c6f59cdc
                        }
                    }
                    if (user.equals(ten) && pass.equals(mk)){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(LoginActivity.this, "Nhập user và pass", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}