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
import com.example.duan1_quanlysalon.adapter.EmployeeAdapter;
import com.example.duan1_quanlysalon.adapter.ListStylistAdapter;
import com.example.duan1_quanlysalon.database.EmployeeDAO;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Add_Booking_Fragment extends Fragment {

    RecyclerView recyclerView;

    EmployeeDAO employeeDAO;

    ArrayList<Employee> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__booking_, container, false);
        recyclerView = view.findViewById(R.id.rcl_stylist_add_booking);
        employeeDAO = new EmployeeDAO(getContext());
        LoadData();
        return view;
    }
    private void LoadData(){
        list = employeeDAO.getListEmployeeStylist();
        LinearLayoutManager linearLayoutManager =new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListStylistAdapter adapter = new ListStylistAdapter(list, getContext(), employeeDAO);
        recyclerView.setAdapter(adapter);
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
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            ((MainActivity)getContext()).replayFragment(new Booking_Fragment());
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}