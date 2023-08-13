package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapterCheckin;
import com.example.duan1_quanlysalon.adapter.ViewPageAdapter;
import com.example.duan1_quanlysalon.adapter.ViewPageEmployeeAdapter;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Booking_Fragment_Employee extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    RecyclerView rcvKhachDangPhucVu;
    ArrayList<Bill> listKhachDangPhucVu;
    LinearLayoutManager linearLayoutManagerKhachDangPhucVu;
    BillAdapterCheckin adapterKhachDangPhucVu;

    SearchView searchView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking___employee, container, false);

        //tablayout set here
        tabLayout = view.findViewById(R.id.tab_booking_employee);
        viewPager2 = view.findViewById(R.id.container_booking_employee);
        ViewPageEmployeeAdapter viewPageAdapter = new ViewPageEmployeeAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);


        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Khách đang chờ bạn");
                    break;
                case 1:
                    tab.setText("Khách đang phục vụ");
                    break;
            }
        }).attach();
        //kết thúc tablayout
        mapping(view);

        loadData();

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                rcvKhachDangPhucVu.setVisibility(View.GONE);
                return false;
            }
        });



        return view;
    }

    public void loadData() {
        getListBookingAPI("khach dang phuc vu", ((MainActivity)getContext()).currentUser.getUserName());
        getListBookingAPI("khach cho phuc vu", ((MainActivity)getContext()).currentUser.getUserName());
    }
    private void getListBookingAPI(String status, String userName) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListBill2(status,userName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListBill, this::handleError)
        );
    }

    private void handleResponseGetListBill(ArrayList<Bill> listBill) {
        if(listBill.size()>0) {
            listKhachDangPhucVu = listBill;
            adapterKhachDangPhucVu = new BillAdapterCheckin(getContext(), listKhachDangPhucVu);
            rcvKhachDangPhucVu.setAdapter(adapterKhachDangPhucVu);
            //tìm kiếm

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    adapterKhachDangPhucVu.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    rcvKhachDangPhucVu.setVisibility(View.VISIBLE);
                    adapterKhachDangPhucVu.getFilter().filter(s);
                    return false;
                }

            });
        }

    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }

    private void mapping(View view){
        linearLayoutManagerKhachDangPhucVu = new LinearLayoutManager(getContext());
        listKhachDangPhucVu = new ArrayList<>();
        searchView = view.findViewById(R.id.srch_booking_employee);
        rcvKhachDangPhucVu = view.findViewById(R.id.list_search_booking);
        rcvKhachDangPhucVu.setLayoutManager(linearLayoutManagerKhachDangPhucVu);
    }
}
