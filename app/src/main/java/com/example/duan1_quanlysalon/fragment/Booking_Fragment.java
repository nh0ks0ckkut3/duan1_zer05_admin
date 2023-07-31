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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.LoginActivity;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapterCheckin;
import com.example.duan1_quanlysalon.database.ProductDAO;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Booking_Fragment extends Fragment {
    RecyclerView rcvKhachDangCho,rcvKhachChuaToi,rcvKhachDangPhucVu;
    ArrayList<Bill> listKhachDangCho, listKhachChuaToi, listKhachDangPhucVu;
    FloatingActionButton floatAdd;
    LinearLayoutManager linearLayoutManagerKhachChuaToi,linearLayoutManagerDangCho,linearLayoutManagerKhachDangPhucVu;
    BillAdapterCheckin adapterKhachDangCho, adapterKhachChuaToi, adapterKhachDangPhucVu;
    boolean flag = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking_, container, false);
        
        mapping(view);

        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new Add_Booking_Fragment());
            }
        });
        return view;
    }

    public void loadData() {
        getListBookingAPI("booking");
        getListBookingAPI("khach dang cho");
        getListBookingAPI("khach dang phuc vu");


        adapterKhachDangCho = new BillAdapterCheckin(getContext(), listKhachDangCho);
        rcvKhachDangCho.setAdapter(adapterKhachDangCho);


        adapterKhachChuaToi = new BillAdapterCheckin(getContext(), listKhachChuaToi);
        rcvKhachChuaToi.setAdapter(adapterKhachChuaToi);


        adapterKhachDangPhucVu = new BillAdapterCheckin(getContext(), listKhachDangPhucVu);
        rcvKhachDangPhucVu.setAdapter(adapterKhachChuaToi);

    }
    private void getListBookingAPI(String status) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListBill(status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(ArrayList<Bill> listBill) {
        if(listBill.size()>0){
            switch (listBill.get(0).getStatus()){
                case "booking":
                    listKhachChuaToi = listBill;
                    break;
                case "khach dang cho":
                    listKhachDangCho = listBill;
                    break;
                case "khach dang phuc vu":
                    listKhachDangPhucVu = listBill;
                    break;
            }
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    
    private void mapping(View view){
        linearLayoutManagerKhachChuaToi = new LinearLayoutManager(getContext());
        linearLayoutManagerDangCho = new LinearLayoutManager(getContext());
        linearLayoutManagerKhachDangPhucVu = new LinearLayoutManager(getContext());
        listKhachChuaToi = new ArrayList<>();
        listKhachDangCho = new ArrayList<>();
        listKhachDangPhucVu = new ArrayList<>();
        rcvKhachDangCho = view.findViewById(R.id.rcvKhachDangCho);
        rcvKhachChuaToi = view.findViewById(R.id.rcvKhachChuaToi);
        rcvKhachDangCho = view.findViewById(R.id.rcvKhachDangCho);
        rcvKhachDangPhucVu = view.findViewById(R.id.rcvKhachDangPhucVu);
        rcvKhachDangCho.setLayoutManager(linearLayoutManagerDangCho);
        rcvKhachChuaToi.setLayoutManager(linearLayoutManagerKhachChuaToi);
        rcvKhachDangPhucVu.setLayoutManager(linearLayoutManagerKhachDangPhucVu);
        floatAdd = view.findViewById(R.id.floatAdd);

    }
}
