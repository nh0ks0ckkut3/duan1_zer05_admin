package com.example.duan1_quanlysalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.duan1_quanlysalon.fragment.Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.Menu_Fragment;
import com.example.duan1_quanlysalon.fragment.SearchPhoneFragment;
import com.example.duan1_quanlysalon.fragment.TotalDayFragment;
import com.example.duan1_quanlysalon.fragment.NewsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                    case R.id.menu_admin_booking:
                        fragment = new Booking_Fragment();
                        break;
                    case R.id.menu_admin_thanhtoan:
                        fragment = new SearchPhoneFragment();
                        break;

                    case R.id.menu_admin_thongke:
                        fragment = new TotalDayFragment();
                        break;
                    case R.id.menu_admin_news:
                        fragment = new NewsFragment();
                        break;
                    case R.id.menu_admin_menu:
                        fragment = new Menu_Fragment();
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_admin, fragment)
                        .commit();
                return true;
            }
        });



    }
}
