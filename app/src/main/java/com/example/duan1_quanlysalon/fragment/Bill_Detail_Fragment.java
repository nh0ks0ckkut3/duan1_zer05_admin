package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.BillAdapterCheckout;
import com.example.duan1_quanlysalon.adapter.listProductCheckoutAdapter;
import com.example.duan1_quanlysalon.adapter.listServiceCheckoutAdapter;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Bill_Detail_Fragment extends Fragment {
    private Bill billCheckout;
    private TextView tvID,tvTime,tvNameCustomer, tvPhoneCustomer, tvNameEmployee, tvTotalPrice;
    private RecyclerView rcViewService, rcViewProduct;
    private AppCompatButton btnPrint, btnPayment;
    private ArrayList<Service> listService;
    private ArrayList<Product> listProduct;
    private listServiceCheckoutAdapter serviceAdapter;
    private listProductCheckoutAdapter productAdapter;
    private LinearLayoutManager linearLayoutManagerService;
    private LinearLayoutManager linearLayoutManagerProduct;
    private int toTalBill=0;
    private Boolean isReady;
    private String nameStylist="";
    LinearLayout lnProgressBar;
    ImageView progressBar;
    ConstraintLayout contraint;

    public Bill_Detail_Fragment(Bill billCheckout, Boolean isReady) {
        this.billCheckout = billCheckout;
        this.isReady = isReady;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill__detail_, container, false);
        tvID = view.findViewById(R.id.tvID);
        tvTime = view.findViewById(R.id.tvTime);
        tvNameCustomer = view.findViewById(R.id.tvNameCustomer);
        tvPhoneCustomer = view.findViewById(R.id.tvPhoneCustomer);
        tvNameEmployee = view.findViewById(R.id.tvNameEmployee);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        rcViewService = view.findViewById(R.id.rcViewService);
        rcViewProduct = view.findViewById(R.id.rcViewProduct);
        btnPrint = view.findViewById(R.id.btnPrint);
        btnPayment = view.findViewById(R.id.btnPayment);
        lnProgressBar = view.findViewById(R.id.lnProgressBar);
        contraint = view.findViewById(R.id.contraint);
        progressBar = view.findViewById(R.id.progressBar);
        Glide.with(getContext()).load(getString(R.string.linkProgressBar2)).into(progressBar);
        listService = new ArrayList<>();
        listProduct = new ArrayList<>();
        if(!((MainActivity)getContext()).currentUser.getClassify().equals("admin")){
            btnPrint.setVisibility(View.GONE);
            btnPayment.setText("OK");
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)getContext()).replayFragment(new Booking_Fragment_Employee());
                }
            });
        }else{
            btnPrint.setVisibility(View.VISIBLE);
            btnPayment.setText("Thanh Toán");
            btnPayment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)getContext()).replayFragment(new Confirm_Payment_Fragment(billCheckout, toTalBill));
                }
            });
        }


        getNameEmployee(billCheckout.getUserNameEmployee());
        return view;
    }
    private void getNameEmployee(String userNameEmployee){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getDetailEmployee(userNameEmployee)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(String name) {
        nameStylist = name;
        getListService(billCheckout.getId());
    }

    private void getListService(int idBill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListServiceChose(idBill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListSV, this::handleError)
        );
    }

    private void handleResponseGetListSV(ArrayList<Service> result) {
        listService = result;
        getListProduct(billCheckout.getId());
    }
    private void getListProduct(int idBill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListProductChose(idBill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListPRO, this::handleError)
        );
    }

    private void handleResponseGetListPRO(ArrayList<Product> result) {
        if (result.size() > 0){
        listProduct = result;}
        setDataToView();
    }

    private void setDataToView(){
        for(Service service : listService){
            toTalBill += service.getPrice();
        }
        for(Product product : listProduct){
            toTalBill += product.getPrice();
        }
        if(isReady){
            ((MainActivity)getContext()).replayFragment(new Confirm_Payment_Fragment(billCheckout, toTalBill));
        }else{
            tvNameEmployee.setText("Thợ chính: "+ nameStylist);
            tvID.setText("Mã HD: "+billCheckout.getId());
            tvNameCustomer.setText("Tên Khách: "+billCheckout.getNameCustomer());
            tvTime.setText(billCheckout.getTime());
            tvPhoneCustomer.setText(billCheckout.getPhoneNumberCustomer());
            linearLayoutManagerService = new LinearLayoutManager(getContext());
            linearLayoutManagerProduct = new LinearLayoutManager(getContext());
            serviceAdapter = new listServiceCheckoutAdapter(getContext(), listService);
            rcViewService.setLayoutManager(linearLayoutManagerService);
            rcViewService.setAdapter(serviceAdapter);
            productAdapter = new listProductCheckoutAdapter(getContext(), listProduct);
            rcViewProduct.setLayoutManager(linearLayoutManagerProduct);
            rcViewProduct.setAdapter(productAdapter);
            tvTotalPrice.setText(toTalBill+ ",000 VNĐ");
            lnProgressBar.setVisibility(View.GONE);
            contraint.setVisibility(View.VISIBLE);
        }

    }

    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}