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
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.NumberFormat;
import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class listServiceCheckoutAdapter  extends RecyclerView.Adapter<listServiceCheckoutAdapter.ViewHolDer> {
    private Context context;
    private ArrayList<Service> list;


    public listServiceCheckoutAdapter(Context context, ArrayList<Service> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public listServiceCheckoutAdapter.ViewHolDer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_sv_pro_checkout, parent, false);
        return new listServiceCheckoutAdapter.ViewHolDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listServiceCheckoutAdapter.ViewHolDer holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(list.get(position).getName());
        NumberFormat currentLocale = NumberFormat.getInstance();
        holder.tvPrice.setText(currentLocale.format(list.get(position).getPrice()));
        holder.tvTotalPrice.setText(currentLocale.format(list.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolDer extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvTotalPrice;

        public ViewHolDer(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalPrice);
        }
    }
}