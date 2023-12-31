package com.example.duan1_quanlysalon;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.fragment.Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.Booking_Fragment_Employee;
import com.example.duan1_quanlysalon.fragment.Checkout_Fragment;
import com.example.duan1_quanlysalon.fragment.Menu_Fragment;
import com.example.duan1_quanlysalon.fragment.TaiKhoanFragment;
import com.example.duan1_quanlysalon.fragment.Thong_Ke_Fragment;
import com.example.duan1_quanlysalon.fragment.ThuNhapFragment;
import com.example.duan1_quanlysalon.fragment.NewsFragment;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Fragment fragment;
    public FragmentManager fragmentManager;
    public Employee currentUser;
    public Toolbar toolbar;
    public TextView titleToolbar;
    public ImageView home;

    private Bill billAdd;
    private ArrayList<Service> listServiceSelectedUpdate;
    private ArrayList<Product> listProductSelectedUpdate;

    private Bill billUpdate;
    private ArrayList<Service> listServiceSelectedAdd;
    private ArrayList<Product> listProductSelectedAdd;

    public String dateCurrent;

    BottomNavigationMenuView mbottomNavigationMenuView;
    BottomNavigationItemView itemView;

    public TextView countChuaToi, countDangCho, countDangPhucVu, countChoPhucVu, countChoThanhToan,countDangPhucVuEmployee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listServiceSelectedUpdate = new ArrayList<>();
        listProductSelectedUpdate = new ArrayList<>();
        home = findViewById(R.id.home);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        currentUser = (Employee) bundle.getSerializable("currentUser",Employee.class);
        bottomNavigationView =findViewById(R.id.bottomNavigationView_admin);
        if(!currentUser.getClassify().equals("admin")){
            bottomNavigationView.getMenu().clear();
            bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_employe);
        }else{
            mbottomNavigationMenuView =
                    (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

            View view = mbottomNavigationMenuView.getChildAt(2);

            itemView = (BottomNavigationItemView) view;

            View cart_badge = LayoutInflater.from(this)
                    .inflate(R.layout.item_checkout_nav,
                            mbottomNavigationMenuView, false);

            itemView.addView(cart_badge);
            countChoThanhToan = itemView.findViewById(R.id.countChoThanhToan);
        }
        Calendar calendar = Calendar.getInstance();
        String day,month;
        if((calendar.get(Calendar.MONTH)+1) < 10){
            month = "0" + (calendar.get(Calendar.MONTH)+1)+"/";
        }else{
            month = (calendar.get(Calendar.MONTH)+1)+"/";
        }
        if(calendar.get(Calendar.DAY_OF_MONTH) < 10){
            day = "0" + calendar.get(Calendar.DAY_OF_MONTH)+"/";
        }else{
            day = calendar.get(Calendar.DAY_OF_MONTH)+"/";
        }
        dateCurrent = day+month+calendar.get(Calendar.YEAR);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //title cho toolbar
        titleToolbar = findViewById(R.id.tv_title_toolbar);

        bottomNavigationView =findViewById(R.id.bottomNavigationView_admin);
        fragmentManager = getSupportFragmentManager();


        if(!currentUser.getClassify().equals("admin")){
            fragment = new Booking_Fragment_Employee();
        }else{
            fragment = new Booking_Fragment();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.fl_admin, fragment)
                .commit();
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new Booking_Fragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_admin, fragment)
                        .commit();
            }
        });

        // click item bottomNavigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                toolbar.setVisibility(View.VISIBLE);
                switch (item.getItemId()){
                    default:
                    case R.id.menu_employe_booking:
                        fragment = new Booking_Fragment_Employee();
                        titleToolbar.setText("Lịch đặt");
                        break;
                    case R.id.menu_admin_booking:
                        fragment = new Booking_Fragment();
                        titleToolbar.setText("Lịch đặt");
                        break;
                    case R.id.menu_admin_thanhtoan:
                        fragment = new Checkout_Fragment();
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
                        titleToolbar.setText("Thu Nhập");
                        break;
                    case R.id.menu_employe_taikhoan:
                        fragment = new TaiKhoanFragment();
                        titleToolbar.setText("Chức năng");
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.fl_admin, fragment)
                        .commit();
                return true;
            }
        });



    }
    public void replayFragment(Fragment f){
        fragmentManager.beginTransaction()
                .replace(R.id.fl_admin, f)
                .commit();
    }

    public Bill getBillAdd() {
        return billAdd;
    }

    public void setBillAdd(Bill billAdd) {
        this.billAdd = billAdd;
    }

    public ArrayList<Service> getListServiceSelectedUpdate() {
        return listServiceSelectedUpdate;
    }

    public void setListServiceSelectedUpdate(ArrayList<Service> listServiceSelectedUpdate) {
        this.listServiceSelectedUpdate = listServiceSelectedUpdate;
    }

    public ArrayList<Product> getListProductSelectedUpdate() {
        return listProductSelectedUpdate;
    }

    public void setListProductSelectedUpdate(ArrayList<Product> listProductSelectedUpdate) {
        this.listProductSelectedUpdate = listProductSelectedUpdate;
    }

    public Bill getBillUpdate() {
        return billUpdate;
    }

    public void setBillUpdate(Bill billUpdate) {
        this.billUpdate = billUpdate;
    }

    public ArrayList<Service> getListServiceSelectedAdd() {
        return listServiceSelectedAdd;
    }

    public void setListServiceSelectedAdd(ArrayList<Service> listServiceSelectedAdd) {
        this.listServiceSelectedAdd = listServiceSelectedAdd;
    }

    public ArrayList<Product> getListProductSelectedAdd() {
        return listProductSelectedAdd;
    }

    public void setListProductSelectedAdd(ArrayList<Product> listProductSelectedAdd) {
        this.listProductSelectedAdd = listProductSelectedAdd;
    }

    public void dummyDataBooking(){
        addBookingAPI(new Bill("0337295209","Hòa","minhthi98","8:00","booking"));
        addBookingAPI(new Bill("0964.060.068","Thanh","minhthi98","9:00","booking"));
        addBookingAPI(new Bill("0983.31.31.51","Nam","minhthi98","10:00","booking"));
        addBookingAPI(new Bill("0978.33.0002","Chiến","minhthi98","11:00","booking"));
        addBookingAPI(new Bill("0986.510.079","Tưởng","minhthi98","12:00","booking"));
        addBookingAPI(new Bill("0963.85.85.05","Hoàng","minhthi98","12:30","booking"));
        addBookingAPI(new Bill("0989.33.8884","Vương","minhthi98","13:00","booking"));
        addBookingAPI(new Bill("0988.04.0770","Tú","minhthi98","15:00","booking"));
        addBookingAPI(new Bill("0962.398.397","Sang","minhthi98","16:00","booking"));
        addBookingAPI(new Bill("0969.23.6660","Tâm","minhthi98","20:00","booking"));
        addBookingAPI(new Bill("0986.5678.37","Thành","minhthi98","20:30","booking"));
    }
    private void addBookingAPI(Bill bill) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.CreateNewBooking(bill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseAddBooking, this::handleError)
        );
    }

    private void handleResponseAddBooking(Boolean result) {
        if(!result)
            Toast.makeText(this, "errol", Toast.LENGTH_SHORT).show();
    }
    private void handleError(Throwable error) {
        Toast.makeText(this, "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }

}
