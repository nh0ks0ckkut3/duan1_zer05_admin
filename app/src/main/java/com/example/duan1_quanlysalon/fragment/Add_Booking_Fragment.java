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
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.ListStylistAdapter;
import com.example.duan1_quanlysalon.adapter.SlotAdapter;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Customer;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ItemSlotClick;
import com.example.duan1_quanlysalon.model.ItemStylistClick;
import com.example.duan1_quanlysalon.model.ProductDetail;
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

public class Add_Booking_Fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Employee> list;
    EditText sdt_add_booking,tenkh_add_booking;
    TextView select_service_add_booking,select_product_add_booking;
    RecyclerView rcl_stylist_add_booking,rcl_time_add_booking;
    AppCompatButton btn_complete_add_booking;
    String phoneNumberCustomer,nameCustomer,userNameEmployee,bookTime;
    ArrayList<Integer> listIDService,listIDProduct;
    ArrayList<Employee> listEmployee;
    int idBill;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__booking_, container, false);
        recyclerView = view.findViewById(R.id.rcl_stylist_add_booking);
        ((MainActivity)getContext()).isHaveReservationBefore = false;
        mapping(view);
        ((MainActivity)getContext()).setListIDServiceSelected(listIDService);
        ((MainActivity)getContext()).setListIDProductSelected(listIDProduct);
        userNameEmployee="";
        bookTime="";
        slotBook();
        getListEmployeeAPI();
        btn_complete_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumberCustomer = sdt_add_booking.getText().toString();
                nameCustomer = tenkh_add_booking.getText().toString();
                if(!(phoneNumberCustomer.length()==0 || nameCustomer.length() == 0 || userNameEmployee.length() == 0 || bookTime.length()==0)){
                    getBookingAPI(phoneNumberCustomer);

                    for(Integer x : listIDService){
                        addServiceDetailAPI(new ServiceDetail(x,idBill));
                    }
                    for(Integer x : listIDService){
                        addServiceDetailAPI(new ServiceDetail(x,idBill));
                    }

                }else{
                    Toast.makeText(getContext(), "không bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
        select_service_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getContext()).replayFragment(new ListSelectServiceFragment());
            }
        });
        select_product_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new ListSelectProductFragment());
            }
        });

        return view;
    }
    private void mapping(View view){
        ((MainActivity)getContext()).toolbar.setVisibility(View.GONE);
        list = new ArrayList<>();
        sdt_add_booking = view.findViewById(R.id.sdt_add_booking);
        tenkh_add_booking = view.findViewById(R.id.tenkh_add_booking);
        select_service_add_booking = view.findViewById(R.id.select_service_add_booking);
        select_product_add_booking = view.findViewById(R.id.select_product_add_booking);
        btn_complete_add_booking = view.findViewById(R.id.btn_complete_add_booking);
        sdt_add_booking = view.findViewById(R.id.sdt_add_booking);
        rcl_stylist_add_booking = view.findViewById(R.id.rcl_stylist_add_booking);
        rcl_time_add_booking = view.findViewById(R.id.rcl_time_add_booking);
        listIDService = new ArrayList<>();
        listIDProduct = new ArrayList<>();
        listEmployee = new ArrayList<>();
        select_service_add_booking.setText("Đã chọn "+((MainActivity)getContext()).getListIDServiceSelected().size()+" dịch vụ");
        select_product_add_booking.setText("Đã chọn "+((MainActivity)getContext()).getListIDProductSelected().size()+" sản phẩm");
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
    private void getBookingAPI(String phoneNumberCustomer) {

        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getBookingAPI(phoneNumberCustomer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetIDBill, this::handleError)
        );
    }
    private void updateBookingAPI(Bill bill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.updateBill(bill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdateBooking, this::handleError)
        );
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
                .subscribe(this::handleResponseAddBooking, this::handleError)
        );
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
                .subscribe(this::handleResponseAddSVPRO, this::handleError)
        );
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
        if(result){
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            ((MainActivity)getContext()).replayFragment(new Booking_Fragment());
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleResponseUpdateBooking(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
            ((MainActivity)getContext()).replayFragment(new Booking_Fragment());
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleResponseGetListEmployee(ArrayList<Employee> result) {
        if(result.size()>0){
            listEmployee = result;
            ListStylistAdapter adapter = new ListStylistAdapter(listEmployee, getContext(),userNameEmployee, new ItemStylistClick() {
                @Override
                public void onClickBook(Employee employee) {
                    userNameEmployee = employee.getUserName();
                }
            });

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),listEmployee.size());
            rcl_stylist_add_booking.setLayoutManager(gridLayoutManager);
            rcl_stylist_add_booking.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }


    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    private void handleResponseGetIDBill(Integer result) {
        if(result<0){
            Toast.makeText(getContext(), ""+idBill, Toast.LENGTH_SHORT).show();
            addCustomerAPI(new Customer(phoneNumberCustomer,"",nameCustomer));
            addBookingAPI(new Bill(phoneNumberCustomer,nameCustomer,userNameEmployee,bookTime,"booking"));
            getBookingAPI(phoneNumberCustomer);

        }else{
            updateBookingAPI(new Bill(idBill,phoneNumberCustomer,nameCustomer,userNameEmployee,bookTime,"booking"));
            deleteDetailBookingAPI(idBill);
        }
    }
    private void addCustomerAPI(Customer customer){
        
    }

    private void handleResponseAddSVPRO(Boolean result) {
        if(!result){
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }
    private void deleteDetailBookingAPI(int idBill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.deleteDetailBookingAPI(idBill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseAddSVPRO, this::handleError)
        );
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
        SlotAdapter adapter = new SlotAdapter(getContext(), listSlot, bookTime, new ItemSlotClick() {
            @Override
            public void onClickSlot(Slot slot) {
                bookTime = slot.getTime();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),10);
        rcl_time_add_booking.setLayoutManager(gridLayoutManager);
        rcl_time_add_booking.setAdapter(adapter);
    }

}