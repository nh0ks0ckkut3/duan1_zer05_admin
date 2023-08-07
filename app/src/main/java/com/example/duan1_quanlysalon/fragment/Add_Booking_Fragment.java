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
import android.widget.ImageView;
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

public class Add_Booking_Fragment extends Fragment {

    RecyclerView recyclerView;
    EditText sdt_add_booking,tenkh_add_booking;
    TextView select_service_add_booking,select_product_add_booking;
    RecyclerView rcl_stylist_add_booking,rcl_time_add_booking;
    AppCompatButton btn_complete_add_booking;
    ImageView imgback_add_booking;
    ArrayList<Employee> listEmployee;
    private Bill billAdd;
    private ArrayList<Service> listServiceSelectedAdd;
    private ArrayList<Product> listProductSelectedAdd;
    private boolean flagService = false, flagProduct = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add__booking_, container, false);
        recyclerView = view.findViewById(R.id.rcl_stylist_add_booking);
        mapping(view);
        billAdd = ((MainActivity)getContext()).getBillAdd();
        listServiceSelectedAdd = ((MainActivity)getContext()).getListServiceSelectedAdd();
        listProductSelectedAdd = ((MainActivity)getContext()).getListProductSelectedAdd();
        sdt_add_booking.setText(billAdd.getPhoneNumberCustomer());
        tenkh_add_booking.setText(billAdd.getNameCustomer());
        select_service_add_booking.setText("đã chọn "+listServiceSelectedAdd.size()+" dịch vụ");
        select_product_add_booking.setText("đã chọn "+listProductSelectedAdd.size()+" sản phẩm");
        tenkh_add_booking.setText(billAdd.getNameCustomer());
        slotBook();
        getListEmployeeAPI();

        btn_complete_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAdd.setNameCustomer(tenkh_add_booking.getText().toString());
                billAdd.setPhoneNumberCustomer(sdt_add_booking.getText().toString());
                ((MainActivity)getContext()).setBillAdd(billAdd);
                if(!(billAdd.getNameCustomer().equals("") || billAdd.getPhoneNumberCustomer().equals("") || billAdd.getUserNameEmployee().equals("") || billAdd.getTime().equals(""))){
                    getBookingAPI(billAdd.getPhoneNumberCustomer());

                }else{
                    Toast.makeText(getContext(), "không bỏ trống", Toast.LENGTH_SHORT).show();
                }
            }
        });
        select_service_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAdd.setNameCustomer(tenkh_add_booking.getText().toString());
                billAdd.setPhoneNumberCustomer(sdt_add_booking.getText().toString());
                ((MainActivity)getContext()).setBillAdd(billAdd);
                ((MainActivity)getContext()).replayFragment(new ListSelectServiceFragment(true));
            }
        });
        select_product_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAdd.setNameCustomer(tenkh_add_booking.getText().toString());
                billAdd.setPhoneNumberCustomer(sdt_add_booking.getText().toString());
                ((MainActivity)getContext()).setBillAdd(billAdd);
                ((MainActivity)getContext()).replayFragment(new ListSelectProductFragment(true));
            }
        });
        imgback_add_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billAdd.setNameCustomer(tenkh_add_booking.getText().toString());
                billAdd.setPhoneNumberCustomer(sdt_add_booking.getText().toString());
                ((MainActivity)getContext()).setBillAdd(billAdd);
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
            ListStylistAdapter adapter = new ListStylistAdapter(listEmployee, getContext(),billAdd.getUserNameEmployee(), new ItemStylistClick() {
                @Override
                public void onClickBook(Employee employee) {
                    billAdd.setUserNameEmployee(employee.getUserName());
                }
            });

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),listEmployee.size());
            rcl_stylist_add_booking.setLayoutManager(gridLayoutManager);
            rcl_stylist_add_booking.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }
    private void getBookingAPI(String phoneNumberCustomer) {
// kiểm tra xem sdt này có lịch đặt hôm nay chưa
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
    private void handleResponseGetIDBill(Integer result) {
        if(result<0){
            //check xem sđt khách hàng này đã có đăng ký tại salon chưa
            checkExistCustomer(billAdd.getPhoneNumberCustomer());

        }else{
            Toast.makeText(getContext(), "khách hàng này đã có lịch đặt hôm nay", Toast.LENGTH_SHORT).show();
        }
    }
    private void checkExistCustomer(String phoneNumberCustomer){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.checkExistCustomer(phoneNumberCustomer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCheckExistCustomer, this::handleError)
        );
    }
    private void handleResponseCheckExistCustomer(Boolean result) {
        if(result){
            //true: đã có tài khoản => tạo booking
            createNewBooking(billAdd);

        }else{
            //false: chưa có tài khoản => tạo tài khoản mới cho khách hàng => tạo booking
            createNewCustomer(billAdd.getPhoneNumberCustomer(), billAdd.getNameCustomer());
        }
    }

    private void createNewCustomer(String phoneNumberCustomer, String nameCustomer){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.createNewCustomer(phoneNumberCustomer,nameCustomer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseCreateNewcustomer, this::handleError)
        );
    }
    private void handleResponseCreateNewcustomer(Boolean result) {
        if(result){
            // đã thêm tài khoản khách hàng => thêm lịch đặt
            createNewBooking(billAdd);
        }else{
            Toast.makeText(getContext(), "errol1", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNewBooking(Bill bill) {
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
            if(listServiceSelectedAdd.size()==0 && listProductSelectedAdd.size()==0){
                ((MainActivity)getContext()).setBillAdd(null);
                ((MainActivity)getContext()).replayFragment(new Booking_Fragment());
            }else{
                // đã thêm lịch đặt booking => lấy lại idBill của bill vừa dc tạo => thêm detailService và detailProduct
                layIDBillVuaDuocThem(billAdd.getPhoneNumberCustomer());
            }
        }else{
            Toast.makeText(getContext(), "errol2", Toast.LENGTH_SHORT).show();
        }
    }

    private void layIDBillVuaDuocThem(String phoneNumberCustomer){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.layIDBillVuaDuocThem(phoneNumberCustomer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseLayIDBillVuaDuocThem, this::handleError)
        );
    }
    private void handleResponseLayIDBillVuaDuocThem(int result) {
        if(result >= 0){
            billAdd.setId(result);
            //đã có idBill => thêm detailService và detailProduct
            if(listServiceSelectedAdd.size() == 0 && listProductSelectedAdd.size() > 0){
                for(int i = 0 ; i < listProductSelectedAdd.size(); i++){
                    if(i == (listProductSelectedAdd.size()-1)){
                        flagProduct = true;
                    }
                    addProductDetailAPI(new ProductDetail(listProductSelectedAdd.get(i).getId(),billAdd.getId()));
                }
            }else if(listServiceSelectedAdd.size() > 0){
                for (int i = 0; i < listServiceSelectedAdd.size(); i++) {
                    if (i == (listServiceSelectedAdd.size() - 1)) {
                        flagService = true;
                    }
                    addServiceDetailAPI(new ServiceDetail(listServiceSelectedAdd.get(i).getId(), billAdd.getId()));
                }
            }

        }else{
            Toast.makeText(getContext(), "errol3", Toast.LENGTH_SHORT).show();
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
            if(listServiceSelectedAdd.size() > 0 && listProductSelectedAdd.size() > 0){
                if(flagService){
                    for(int i = 0 ; i < listProductSelectedAdd.size(); i++){
                        if(i == (listProductSelectedAdd.size()-1)){
                            flagProduct = true;
                        }
                        addProductDetailAPI(new ProductDetail(listProductSelectedAdd.get(i).getId(),billAdd.getId()));
                    }
                }
            }else if(listServiceSelectedAdd.size() > 0 && listProductSelectedAdd.size() == 0){
                ((MainActivity)getContext()).setBillAdd(null);
                ((MainActivity)getContext()).setListServiceSelectedAdd(new ArrayList<>());
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
            ((MainActivity)getContext()).setBillAdd(null);
            ((MainActivity)getContext()).setListServiceSelectedAdd(new ArrayList<>());
            ((MainActivity)getContext()).setListProductSelectedAdd(new ArrayList<>());
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
        SlotAdapter adapter = new SlotAdapter(getContext(), listSlot, billAdd.getTime(), new ItemSlotClick() {
            @Override
            public void onClickSlot(Slot slot) {
                billAdd.setTime(slot.getTime());
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),10);
        rcl_time_add_booking.setLayoutManager(gridLayoutManager);
        rcl_time_add_booking.setAdapter(adapter);
    }

}