package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapterCheckin;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Booking_Tab_KhachChuaToi extends Fragment {
    RecyclerView rcvKhachChuaToi;
    ArrayList<Bill>  listKhachChuaToi;
    LinearLayoutManager linearLayoutManagerKhachChuaToi;
    BillAdapterCheckin adapterKhachChuaToi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking__tab__khach_chua_toi, container, false);
        mapping(view);

        loadData();

        return view;
    }
    public void loadData() {
        getListBookingAPI("booking");
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
                .subscribe(this::handleResponseGetListBill, this::handleError)
        );
    }

    private void handleResponseGetListBill(ArrayList<Bill> listBill) {
        if(listBill.size()>0){
            if (listBill.get(0).getStatus().equals("booking")){
                listKhachChuaToi = listBill;
                adapterKhachChuaToi = new BillAdapterCheckin(getContext(), listKhachChuaToi);
                rcvKhachChuaToi.setAdapter(adapterKhachChuaToi);
            }
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }

    private void mapping(View view){
        linearLayoutManagerKhachChuaToi = new LinearLayoutManager(getContext());
        listKhachChuaToi = new ArrayList<>();
        rcvKhachChuaToi = view.findViewById(R.id.rcvKhachChuaToi);
        rcvKhachChuaToi.setLayoutManager(linearLayoutManagerKhachChuaToi);
    }
}