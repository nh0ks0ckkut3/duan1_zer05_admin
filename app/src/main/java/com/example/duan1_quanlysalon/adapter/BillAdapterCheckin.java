package com.example.duan1_quanlysalon.adapter;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.fragment.Add_Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.Bill_Detail_Fragment;
import com.example.duan1_quanlysalon.fragment.Booking_Fragment;
import com.example.duan1_quanlysalon.fragment.Booking_Fragment_Employee;
import com.example.duan1_quanlysalon.fragment.Nhan_Khach_Fragment;
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

public class BillAdapterCheckin extends RecyclerView.Adapter<BillAdapterCheckin.ViewHolDer> implements Filterable {
    private Context context;
    private ArrayList<Bill> list;

    private ArrayList<Bill> listAtPhone;

    public BillAdapterCheckin(Context context, ArrayList<Bill> list) {
        this.context = context;
        this.list = list;
        this.listAtPhone=list;
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
        if(list.get(position).getStatus().equals("booking")){
            holder.tvCountTime.setVisibility(View.GONE);
        }else if(list.get(position).getStatus().equals("khach dang cho")){
            holder.tvNhanKhach.setText("Tư vấn");
        }else if(list.get(position).getStatus().equals("khach dang phuc vu")){
            holder.tvNhanKhach.setText("Hoàn tất");
            holder.tvDetail.setVisibility(View.VISIBLE);
            holder.tvDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)context).replayFragment(new Bill_Detail_Fragment(list.get(position), false));
                }
            });
        }
        holder.tvBookTime.setText(list.get(position).getBookTime());
        holder.tvNhanKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list.get(position).getStatus().equals("booking")){
                    setStatusBooking(list.get(position).getId(), "khach dang cho");
                    ((MainActivity)context).replayFragment(new Booking_Fragment());
                }else if(list.get(position).getStatus().equals("khach dang cho")){
                    ((MainActivity)context).setBillUpdate(list.get(position));
                    getListServiceSelected(list.get(position).getId());
                }else if(list.get(position).getStatus().equals("khach cho phuc vu")){
                    setStatusBooking(list.get(position).getId(),"khach dang phuc vu");
                    ((MainActivity)context).replayFragment(new Booking_Fragment_Employee());
                }else if(list.get(position).getStatus().equals("khach dang phuc vu")){
                    setStatusBooking(list.get(position).getId(), "khach cho thanh toan");
                    ((MainActivity)context).replayFragment(new Booking_Fragment_Employee());
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //tìm kiếm
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String lh1 = charSequence.toString();
                if (lh1.isEmpty()) {
                    list = listAtPhone;
                } else {
                    ArrayList<Bill> listbill = new ArrayList<>();
                    for (Bill bill : listAtPhone) {
                        if (bill.getPhoneNumberCustomer().toLowerCase().contains(lh1.toLowerCase())) {
                            listbill.add(bill);
                        }
                    }
                    list = listbill;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (ArrayList<Bill>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolDer extends RecyclerView.ViewHolder{
        TextView tvNameCustomer, tvPhoneCustomer, tvCountTime,tvBookTime, tvNhanKhach, tvDetail;
        public ViewHolDer(@NonNull View itemView) {
            super(itemView);
            tvNameCustomer = itemView.findViewById(R.id.tvNameCustomer);
            tvPhoneCustomer = itemView.findViewById(R.id.tvPhoneCustomer);
            tvCountTime = itemView.findViewById(R.id.tvCountTime);
            tvBookTime = itemView.findViewById(R.id.tvBookTime);
            tvNhanKhach = itemView.findViewById(R.id.tvNhanKhach);
            tvDetail = itemView.findViewById(R.id.tvDetail);
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
    private void getListServiceSelected(int idBill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListServiceChose(idBill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseListSV, this::handleError)
        );
    }
    private void handleResponseListSV(ArrayList<Service> result) {
        ((MainActivity)context).setListServiceSelectedUpdate(result);
        getListProductSelected(((MainActivity)context).getBillUpdate().getId());
    }
    private void getListProductSelected(int idBill){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListProductChose(idBill)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseListPRO, this::handleError)
        );
    }
    private void handleResponseListPRO(ArrayList<Product> result) {
        ((MainActivity)context).setListProductSelectedUpdate(result);
        ((MainActivity)context).replayFragment(new Nhan_Khach_Fragment());
    }

}
