package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Confirm_Payment_Fragment extends Fragment {
    private Bill billCheckout;
    private int totalBill;
    private TextView tvID, tvNameCustomer, tvPhoneCustomer, tvTotalPrice;
    private AppCompatButton btnConfirm;
    private ConstraintLayout screen;
    TextView sotienthua;
    private String loaiGD= "cash";

    public Confirm_Payment_Fragment(Bill billCheckout, int totalBill) {
        this.billCheckout = billCheckout;
        this.totalBill = totalBill;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm__payment_, container, false);
        TextView btntructiep = view.findViewById(R.id.btn_typepayment_tructiep);
        TextView btnchuyenkhoan = view.findViewById(R.id.btn_typepayment_chuyenkhoan);

        TextView tvtkd = view.findViewById(R.id.textView_tienkhacdua);
        TextView tvtt = view.findViewById(R.id.textView_tienthua);
        EditText sotiendua = view.findViewById(R.id.edMoney);
        sotienthua = view.findViewById(R.id.payment_detail_bill_tienthua);

        ImageView qr = view.findViewById(R.id.qrcode);

        tvID = view.findViewById(R.id.tvID);
        tvNameCustomer = view.findViewById(R.id.tvNameCustomer);
        tvPhoneCustomer = view.findViewById(R.id.tvPhoneCustomer);
        tvTotalPrice = view.findViewById(R.id.tvTotalPrice);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        screen = view.findViewById(R.id.screen);

        tvID.setText("Mã HĐ: "+billCheckout.getId());
        tvPhoneCustomer.setText(billCheckout.getPhoneNumberCustomer());
        tvNameCustomer.setText(billCheckout.getNameCustomer());
        tvTotalPrice.setText(String.valueOf(totalBill));

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStatusBooking(billCheckout.getId(), "da thanh toan");
            }
        });

        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tienDua = 0;
                int tienthua = 0;
                try {
                    tienDua = Integer.parseInt(sotiendua.getText().toString());
                    tienthua = tienDua - totalBill;
                    sotienthua.setText(tienthua+"");

                }catch (Exception e){

                }
            }
        });

        //nhấn nút ẩn qr code hiện 4 textview
        btntructiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaiGD = "cash";
                btntructiep.setBackgroundResource(R.drawable.btn_typepayment_select);
                btnchuyenkhoan.setBackgroundResource(R.drawable.btn_typepayment_nonselect);
                tvtkd.setVisibility(View.VISIBLE);
                tvtt.setVisibility(View.VISIBLE);
                sotiendua.setVisibility(View.VISIBLE);
                sotienthua.setVisibility(View.VISIBLE);
                qr.setVisibility(View.GONE);
            }
        });

        //ấn nút hiện qr code ẩn 4 textview
        btnchuyenkhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loaiGD = "banking";
                btntructiep.setBackgroundResource(R.drawable.btn_typepayment_nonselect);
                btnchuyenkhoan.setBackgroundResource(R.drawable.btn_typepayment_select);
                tvtkd.setVisibility(View.GONE);
                tvtt.setVisibility(View.GONE);
                sotiendua.setVisibility(View.GONE);
                sotienthua.setVisibility(View.GONE);
                qr.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

        private void setStatusBooking(int idBill, String status){
            ServiceAPI requestInterface = new Retrofit.Builder()
                    .baseUrl(BASE_API_ZERO5)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ServiceAPI.class);

            new CompositeDisposable().add(requestInterface.setStatusBill(idBill, status)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
            );
        }

        private void handleResponse(Boolean result) {
            if(!result){
                Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
            }else{
                ((MainActivity)getContext()).replayFragment(new Payment_Complete_Fragment(billCheckout, totalBill, loaiGD));
            }
        }

        private void handleError(Throwable error) {
            Toast.makeText(getContext(), "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
        }
}