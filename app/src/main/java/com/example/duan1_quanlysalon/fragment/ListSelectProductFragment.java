package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.ListSelectProductAdapter;
import com.example.duan1_quanlysalon.adapter.ListSelectServiceAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListSelectServiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListSelectProductFragment extends Fragment {

    private ArrayList<Product> listAllProduct;
    private RecyclerView rcViewListProduct;
    private ListSelectProductAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private TextView btnOk;
    private ImageView ivBack;

    public ListSelectProductFragment() {
        // Required empty public constructor
    }

    public static ListSelectServiceFragment newInstance(String param1, String param2) {
        ListSelectServiceFragment fragment = new ListSelectServiceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_select_service, container, false);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        tvTitle.setText("Chọn sản phẩm");
        listAllProduct = new ArrayList<>();
        rcViewListProduct = view.findViewById(R.id.rcl_list_service);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcViewListProduct.setLayoutManager(gridLayoutManager);
        btnOk = view.findViewById(R.id.btnOke);
        btnOk.setText("Chọn sản phẩm");
        ivBack = view.findViewById(R.id.ivBack);

        loadData();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAndSave();
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backAndSave();
            }
        });

        return view;
    }
    private void loadData(){
        getListAllProduct();
    }
    private void getListAllProduct(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListProduct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListProduct, this::handleError)
        );
    }
    private void handleResponseGetListProduct(ArrayList<Product> result) {
        if(result.size()>0){
            listAllProduct = result;
            ListSelectProductAdapter adapter = new ListSelectProductAdapter(listAllProduct, getContext());
            rcViewListProduct.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }


    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    private void backAndSave(){
        if(((MainActivity)getContext()).isHaveReservationBefore){
            ((MainActivity)getContext()).replayFragment(new Nhan_Khach_Fragment());
        }else
            ((MainActivity)getContext()).replayFragment(new Add_Booking_Fragment());
    }
}