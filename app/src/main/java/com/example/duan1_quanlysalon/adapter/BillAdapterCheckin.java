package com.example.duan1_quanlysalon.adapter;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.fragment.Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.Nhan_Khach_Fragment;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BillAdapterCheckin extends RecyclerView.Adapter<BillAdapterCheckin.ViewHolDer>{
    private Context context;
    private ArrayList<Bill> list;


    public BillAdapterCheckin(Context context, ArrayList<Bill> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_checkin, parent, false);
        return new ViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolDer holder, @SuppressLint("RecyclerView") int position) {

        holder.tvPhoneCustomer.setText(list.get(position).getPhoneNumberCustomer());
        holder.tvNameCustomer.setText(list.get(position).getNameCustomer());
//        int countTime = 0;
//        run(countTime);
        if(list.get(position).getStatus().equals("booking")){
            holder.tvCountTime.setVisibility(View.GONE);
        }else if(list.get(position).getStatus().equals("khach dang cho")){
            holder.tvNhanKhach.setText("Tư vấn");
        }
        holder.tvBookTime.setText(list.get(position).getBookTime());
        holder.tvNhanKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getStatus().equals("booking")){
                    setStatusBooking(list.get(position).getId(), "khach dang cho");
                    ((MainActivity)context).replayFragment(new Booking_Fragment());
                }else if(list.get(position).getStatus().equals("khach dang cho")){
                    ((MainActivity)context).billTarget = list.get(position);
                    ((MainActivity)context).replayFragment(new Nhan_Khach_Fragment());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolDer extends RecyclerView.ViewHolder{
        TextView tvNameCustomer, tvPhoneCustomer, tvCountTime,tvBookTime, tvNhanKhach;
        public ViewHolDer(@NonNull View itemView) {
            super(itemView);
            tvNameCustomer = itemView.findViewById(R.id.tvNameCustomer);
            tvPhoneCustomer = itemView.findViewById(R.id.tvPhoneCustomer);
            tvCountTime = itemView.findViewById(R.id.tvCountTime);
            tvBookTime = itemView.findViewById(R.id.tvBookTime);
            tvNhanKhach = itemView.findViewById(R.id.tvNhanKhach);
        }
    }
    public void run(int countTime) {
        try {
            while (true) {
                Thread.sleep(5000); // ??
                countTime+=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            Toast.makeText(context, "errol", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(Throwable error) {
        Toast.makeText(context, "lỗi load trang, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}
