package com.example.duan1_quanlysalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.fragment.Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.List_Employee_Fragment;
import com.example.duan1_quanlysalon.fragment.List_Product_Fragment;
import com.example.duan1_quanlysalon.fragment.List_Service_Fragment;
import com.example.duan1_quanlysalon.model.Employee;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ánh xạ
        bottomNavigationView =findViewById(R.id.bottomNavigationView_admin) ;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_admin,new Booking_Fragment()).commit();
        // click item bottomNavigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()){

                    case R.id.menu_admin_booking:
                        fragment = new Booking_Fragment();
                        break;

                }
                if (fragment!=null){
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fl_admin,fragment).commit();
                }
                // fragment booking để test code
                // đang thiếu fragment chờ có thêm vào sau

                return false;
            }
        });



    }
}