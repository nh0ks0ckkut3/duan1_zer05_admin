package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.LoginActivity;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapterCheckin;
import com.example.duan1_quanlysalon.adapter.ViewPageAdapter;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;
import java.util.Calendar;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Booking_Fragment extends Fragment {
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    RecyclerView rcvKhachDangCho;
    ArrayList<Bill> listKhachDangCho;
    Button floatAdd;
    LinearLayoutManager linearLayoutManagerDangCho;
    BillAdapterCheckin adapterKhachDangCho;

    SearchView searchView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_, container, false);

        //tablayout set here
        tabLayout = view.findViewById(R.id.tab_booking);
        viewPager2 = view.findViewById(R.id.container_booking);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(this);
        viewPager2.setAdapter(viewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    View viewTab1 = inflater.inflate(R.layout.item_tablayout, container, false);
                    TextView tvTitle1 = viewTab1.findViewById(R.id.title);
                    ((MainActivity)getContext()).countChuaToi = viewTab1.findViewById(R.id.count);
                    tvTitle1.setText("Khách chưa tới");
                    tab.setCustomView(viewTab1);
                    break;
                case 1:
                    View viewTab2 = inflater.inflate(R.layout.item_tablayout, container, false);
                    TextView tvTitle2 = viewTab2.findViewById(R.id.title);
                    ((MainActivity)getContext()).countDangCho = viewTab2.findViewById(R.id.count);
                    tvTitle2.setText("Khách đang chờ");
                    tab.setCustomView(viewTab2);
                    break;
                case 2:
                    View viewTab3 = inflater.inflate(R.layout.item_tablayout, container, false);
                    TextView tvTitle3 = viewTab3.findViewById(R.id.title);
                    ((MainActivity)getContext()).countDangPhucVu = viewTab3.findViewById(R.id.count);
                    tvTitle3.setText("đang phục vụ");
                    tab.setCustomView(viewTab3);
                    break;
            }
        }).attach();
        //kết thúc tablayout

        mapping(view);

        loadData();
        //tắt tìm kiếm
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                rcvKhachDangCho.setVisibility(View.GONE);
                return false;
            }
        });


        //thêm mới
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(((MainActivity)getContext()).getBillAdd() != null)){
                    ((MainActivity)getContext()).setBillAdd(new Bill("","","","","",((MainActivity)getContext()).dateCurrent));
                    ((MainActivity)getContext()).setListServiceSelectedAdd(new ArrayList<>());
                    ((MainActivity)getContext()).setListProductSelectedAdd(new ArrayList<>());
                }
                ((MainActivity)getContext()).replayFragment(new Add_Booking_Fragment());
            }
        });
        return view;
    }

    public void loadData() {
        getListBookingAPI("booking", ((MainActivity)getContext()).dateCurrent);
        getListBookingAPI("khach dang cho", ((MainActivity)getContext()).dateCurrent);
        getListBookingAPI("khach dang phuc vu", ((MainActivity)getContext()).dateCurrent);
    }
    private void getListBookingAPI(String status, String date) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListBill(status, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListBill, this::handleError)
        );
    }

    private void handleResponseGetListBill(ArrayList<Bill> listBill) {
        if(listBill.size()>0){
            if(listBill.get(0).getStatus().equals("booking")){
                ((MainActivity)getContext()).countChuaToi.setText(listBill.size()+"");
            }else if(listBill.get(0).getStatus().equals("khach dang cho")){
                ((MainActivity)getContext()).countDangCho.setText(listBill.size()+"");
            }else if(listBill.get(0).getStatus().equals("khach dang phuc vu")) {
                ((MainActivity) getContext()).countDangPhucVu.setText(listBill.size() + "");
            }
            for(Bill bill : listBill){
                listKhachDangCho.add(bill);
            }
            adapterKhachDangCho = new BillAdapterCheckin(getContext(), listKhachDangCho);
            rcvKhachDangCho.setAdapter(adapterKhachDangCho);
            //tìm kiếm
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    adapterKhachDangCho.getFilter().filter(s);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    rcvKhachDangCho.setVisibility(View.VISIBLE);
                    adapterKhachDangCho.getFilter().filter(s);
                    return false;
                }

            });
        }else{
            if(listBill.get(0).getStatus().equals("booking")){
                ((MainActivity)getContext()).countChuaToi.setText(0+"");
            }else if(listBill.get(0).getStatus().equals("khach dang cho")){
                ((MainActivity)getContext()).countDangCho.setText(0+"");
            }else if(listBill.get(0).getStatus().equals("khach dang phuc vu")) {
                ((MainActivity) getContext()).countDangPhucVu.setText(0 + "");
            }
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    
    private void mapping(View view){
        linearLayoutManagerDangCho = new LinearLayoutManager(getContext());
        listKhachDangCho = new ArrayList<>();
        rcvKhachDangCho = view.findViewById(R.id.list_search_bookingadmin);
        rcvKhachDangCho.setLayoutManager(linearLayoutManagerDangCho);
        floatAdd = view.findViewById(R.id.floatAdd);

        searchView = view.findViewById(R.id.srch_booking);
    }
}
