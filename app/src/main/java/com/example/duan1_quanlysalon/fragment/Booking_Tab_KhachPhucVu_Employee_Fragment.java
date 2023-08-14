package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapterCheckin;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Booking_Tab_KhachPhucVu_Employee_Fragment extends Fragment {
    RecyclerView rcvKhachDangPhucVu;
    ArrayList<Bill> listKhachDangPhucVu;
    LinearLayoutManager linearLayoutManagerKhachDangPhucVu;
    BillAdapterCheckin adapterKhachDangPhucVu;
    LinearLayout lnProgressBar;
    ImageView progressBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking__tab__khach_phuc_vu__employee_, container, false);
        mapping(view);

        loadData();


        return view;
    }
    public void loadData() {
        getListBookingAPI("khach dang phuc vu", ((MainActivity)getContext()).currentUser.getUserName(), ((MainActivity)getContext()).dateCurrent);

    }
    private void getListBookingAPI(String status, String userName, String date) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListBill2(status,userName, date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListBill, this::handleError)
        );
    }

    private void handleResponseGetListBill(ArrayList<Bill> listBill) {
        if(listBill.size()>0){
            if(listBill.get(0).getStatus().equals("khach dang phuc vu")){
                listKhachDangPhucVu = listBill;
                adapterKhachDangPhucVu = new BillAdapterCheckin(getContext(), listKhachDangPhucVu);
                rcvKhachDangPhucVu.setAdapter(adapterKhachDangPhucVu);
            }
        }
        lnProgressBar.setVisibility(View.GONE);
        rcvKhachDangPhucVu.setVisibility(View.VISIBLE);
    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }

    private void mapping(View view){
        linearLayoutManagerKhachDangPhucVu = new LinearLayoutManager(getContext());
        listKhachDangPhucVu = new ArrayList<>();
        rcvKhachDangPhucVu = view.findViewById(R.id.rcvKhachDangPhucVuEmployee);
        rcvKhachDangPhucVu.setLayoutManager(linearLayoutManagerKhachDangPhucVu);
        progressBar = view.findViewById(R.id.progressBar);
        lnProgressBar = view.findViewById(R.id.lnProgressBar);
        Glide.with(getContext()).load(getString(R.string.linkProgressBar2)).into(progressBar);
    }
}