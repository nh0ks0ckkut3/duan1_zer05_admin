package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.Detail_Service_Activity;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.ListStylistAdapter;
import com.example.duan1_quanlysalon.adapter.SlotAdapter;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ItemSlotClick;
import com.example.duan1_quanlysalon.model.ItemStylistClick;
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.ProductDetail;
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.example.duan1_quanlysalon.model.ServiceDetail;
import com.example.duan1_quanlysalon.model.Slot;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Nhan_Khach_Fragment extends Fragment {

    RecyclerView recyclerView;
    EditText sdt_add_booking,tenkh_add_booking;
    TextView select_service_add_booking,select_product_add_booking;
    RecyclerView rcl_stylist_add_booking,rcl_time_add_booking;
    AppCompatButton btn_complete_add_booking;
    ImageView imgback_add_booking;
    ArrayList<Employee> listEmployee;
    private Bill billUpdate;
    private ArrayList<Service> listServiceSelectedUpdate;
    private ArrayList<Product> listProductSelectedUpdate;
    private boolean flagService = false, flagProduct = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__booking_, container, false);
        recyclerView = view.findViewById(R.id.rcl_stylist_add_booking);
        mapping(view);
        billUpdate = ((MainActivity)getContext()).getBillUpdate();
        listServiceSelectedUpdate = ((MainActivity)getContext()).getListServiceSelectedUpdate();
        listProductSelectedUpdate = ((MainActivity)getContext()).getListProductSelectedUpdate();
        sdt_add_booking.setText(billUpdate.getPhoneNumberCustomer());
        tenkh_add_booking.setText(billUpdate.getNameCustomer());
        select_service_add_booking.setText("đã chọn "+listServiceSelectedUpdate.size()+" dịch vụ");
        select_product_add_booking.setText("đã chọn "+listProductSelectedUpdate.size()+" sản phẩm");
        tenkh_add_booking.setText(billUpdate.getNameCustomer());
        sdt_add_booking.setFocusable(false);
        tenkh_add_booking.setFocusable(false);
        slotBook();
        getListEmployeeAPI();

        btn_complete_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).setBillUpdate(billUpdate);
                if(!(billUpdate.getUserNameEmployee().equals("") || billUpdate.getTime().equals(""))){
                    billUpdate.setStatus("khach cho phuc vu");
                    updateBill(billUpdate);

                }else{
                    Toast.makeText(getContext(), "không bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
        select_service_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new ListSelectServiceFragment(false));
            }
        });
        select_product_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new ListSelectProductFragment(false));
            }
        });
        imgback_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new Booking_Fragment());
            }
        });

        return view;
    }
    private void mapping(View view){
        ((MainActivity)getContext()).toolbar.setVisibility(View.GONE);
        sdt_add_booking = view.findViewById(R.id.sdt_add_booking);
        tenkh_add_booking = view.findViewById(R.id.tenkh_add_booking);
        select_service_add_booking = view.findViewById(R.id.select_service_add_booking);
        select_product_add_booking = view.findViewById(R.id.select_product_add_booking);
        btn_complete_add_booking = view.findViewById(R.id.btn_complete_add_booking);
        rcl_stylist_add_booking = view.findViewById(R.id.rcl_stylist_add_booking);
        rcl_time_add_booking = view.findViewById(R.id.rcl_time_add_booking);
        imgback_add_booking = view.findViewById(R.id.imgback_add_booking);
        listEmployee = new ArrayList<>();
    }

    private void getListEmployeeAPI(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListEmployee()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListEmployee, this::handleError)
        );
    }
    private void handleResponseGetListEmployee(ArrayList<Employee> result) {
        if(result.size()>0){
            listEmployee = result;
            ListStylistAdapter adapter = new ListStylistAdapter(listEmployee, getContext(),billUpdate.getUserNameEmployee(), new ItemStylistClick() {
                @Override
                public void onClickBook(Employee employee) {
                    billUpdate.setUserNameEmployee(employee.getUserName());
                    ((MainActivity)getContext()).setBillUpdate(billUpdate);
                }
            });

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),listEmployee.size());
            rcl_stylist_add_booking.setLayoutManager(gridLayoutManager);
            rcl_stylist_add_booking.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }
    private void updateBill(Bill bill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.updateBill(bill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdateBill, this::handleError)
        );
    }

    private void handleResponseUpdateBill(Boolean result) {
        if(!result){
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }else{
            // tìm và xóa toàn bộ serviceDetail, productDetail cũ
            deleteServiceDetail(billUpdate.getId());
        }
    }
    private void deleteServiceDetail(int idBill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.deleteDetailBookingAPI(idBill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseDeleteSVPRODetail, this::handleError)
        );
    }

    private void handleResponseDeleteSVPRODetail(Boolean result) {
        if(!result){
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }else{
            // thêm serviceDetail và productDetail mới
            if(listServiceSelectedUpdate.size() == 0 && listProductSelectedUpdate.size() == 0){
                ((MainActivity)getContext()).replayFragment(new Booking_Fragment());
            }else if(listServiceSelectedUpdate.size() == 0){
                for(int i = 0; i < listProductSelectedUpdate.size(); i++){
                    if(i == (listProductSelectedUpdate.size()-1)){
                        flagProduct = true;
                    }
                    addProductDetailAPI(new ProductDetail(listProductSelectedUpdate.get(i).getId(),billUpdate.getId()));
                }
            }else{
                for(int i = 0; i < listServiceSelectedUpdate.size(); i++){
                    if(i == (listServiceSelectedUpdate.size()-1)){
                        flagService = true;
                    }
                    addServiceDetailAPI(new ServiceDetail(listServiceSelectedUpdate.get(i).getId(), billUpdate.getId()));
                }
            }
        }
    }


    private void addServiceDetailAPI(ServiceDetail serviceDetail) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.addServiceDetail(serviceDetail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseAddServiceDetail, this::handleError)
        );
    }
    private void handleResponseAddServiceDetail(Boolean result) {
        if(result){
            if(listProductSelectedUpdate.size() > 0){
                if(flagService){
                    for(int i = 0 ; i < listProductSelectedUpdate.size(); i++){
                        if(i == (listProductSelectedUpdate.size()-1)){
                            flagProduct = true;
                        }
                        addProductDetailAPI(new ProductDetail(listProductSelectedUpdate.get(i).getId(),billUpdate.getId()));
                    }
                }
            }else {
                flagService = false;
                flagProduct = false;
                ((MainActivity)getContext()).replayFragment(new Booking_Fragment());
            }
        }else{
            Toast.makeText(getContext(), "errol4", Toast.LENGTH_SHORT).show();
        }
    }

    private void addProductDetailAPI(ProductDetail productDetail) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.addProductDetail(productDetail)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseAddProductDetail, this::handleError)
        );
    }
    private void handleResponseAddProductDetail(Boolean result) {
        if(result){
            // đã add detailService và productService
            if(flagProduct){
                flagService = false;
                flagProduct = false;
                ((MainActivity)getContext()).replayFragment(new Booking_Fragment());}
        }else{
            Toast.makeText(getContext(), "errol5", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    private void slotBook(){
        ArrayList<Slot> listSlot = new ArrayList<>();
        listSlot.add(new Slot("07h00"));
        listSlot.add(new Slot("07h30"));
        listSlot.add(new Slot("08h00"));
        listSlot.add(new Slot("08h30"));
        listSlot.add(new Slot("09h00"));
        listSlot.add(new Slot("09h30"));
        listSlot.add(new Slot("10h00"));
        listSlot.add(new Slot("10h30"));
        listSlot.add(new Slot("11h00"));
        listSlot.add(new Slot("11h30"));
        listSlot.add(new Slot("12h00"));
        listSlot.add(new Slot("12h30"));
        listSlot.add(new Slot("13h00"));
        listSlot.add(new Slot("13h30"));
        listSlot.add(new Slot("14h00"));
        listSlot.add(new Slot("14h30"));
        listSlot.add(new Slot("15h00"));
        listSlot.add(new Slot("15h30"));
        listSlot.add(new Slot("16h00"));
        listSlot.add(new Slot("16h30"));
        listSlot.add(new Slot("17h00"));
        listSlot.add(new Slot("17h30"));
        listSlot.add(new Slot("18h00"));
        listSlot.add(new Slot("18h30"));
        listSlot.add(new Slot("19h00"));
        listSlot.add(new Slot("19h30"));
        listSlot.add(new Slot("20h00"));
        listSlot.add(new Slot("20h30"));
        listSlot.add(new Slot("21h00"));
        listSlot.add(new Slot("21h30"));
        SlotAdapter adapter = new SlotAdapter(getContext(), listSlot, billUpdate.getTime(), new ItemSlotClick() {
            @Override
            public void onClickSlot(Slot slot) {
                billUpdate.setTime(slot.getTime());
                ((MainActivity)getContext()).setBillUpdate(billUpdate);
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),10);
        rcl_time_add_booking.setLayoutManager(gridLayoutManager);
        rcl_time_add_booking.setAdapter(adapter);
    }

}