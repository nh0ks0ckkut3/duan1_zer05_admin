package com.example.duan1_quanlysalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;
import android.widget.TextView;

import com.example.duan1_quanlysalon.fragment.Add_Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.Confirm_Payment_Fragment;
import com.example.duan1_quanlysalon.fragment.List_Bill_Fragment;
import com.example.duan1_quanlysalon.fragment.List_Service_Fragment;
import com.example.duan1_quanlysalon.fragment.Menu_Fragment;
import com.example.duan1_quanlysalon.fragment.Nhan_Khach_Fragment;
import com.example.duan1_quanlysalon.fragment.SearchPhoneFragment;
import com.example.duan1_quanlysalon.fragment.Select_Product_Fragment;
import com.example.duan1_quanlysalon.fragment.Thong_Ke_Fragment;
import com.example.duan1_quanlysalon.fragment.ThuNhapFragment;
import com.example.duan1_quanlysalon.fragment.TotalDayFragment;
import com.example.duan1_quanlysalon.fragment.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    FragmentManager fragmentManager;
    String classify,userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        userName = bundle.getString("userName","");
        classify = bundle.getString("classify","");
        bottomNavigationView =findViewById(R.id.bottomNavigationView_admin);
        if(!classify.equals("admin")){
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_employe);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //title cho toolbar
        TextView titleToolbar = findViewById(R.id.tv_title_toolbar);

        bottomNavigationView =findViewById(R.id.bottomNavigationView_admin);
        fragmentManager = getSupportFragmentManager();
        fragment = new Booking_Fragment();
        fragmentManager.beginTransaction()
                            .replace(R.id.fl_admin, fragment)
                            .commit();

        // click item bottomNavigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    default:
                    case R.id.menu_employe_booking:
                    case R.id.menu_admin_booking:
                        fragment = new Booking_Fragment();
                        titleToolbar.setText("Lịch đặt");
                        break;
                    case R.id.menu_admin_thanhtoan:
                        fragment = new List_Bill_Fragment();
                        titleToolbar.setText("Thanh toán");
                        break;

                    case R.id.menu_admin_thongke:
                        // thống kê chưa làm được
                        fragment = new Thong_Ke_Fragment();
                        titleToolbar.setText("Thống kê");
                        break;
                    case R.id.menu_admin_news:
                    case R.id.menu_employe_news:
                        fragment = new NewsFragment();
                        titleToolbar.setText("Tin tức");
                        break;
                    case R.id.menu_admin_menu:
                        fragment = new Menu_Fragment();
                        titleToolbar.setText("Chức năng");
                        break;
                    case R.id.menu_employe_thunhap:
                        fragment = new ThuNhapFragment();
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_admin, fragment)
                        .commit();
                return true;
            }
        });



    }
}
