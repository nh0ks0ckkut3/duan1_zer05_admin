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
import com.example.duan1_quanlysalon.adapter.ListSelectServiceAdapter;
import com.example.duan1_quanlysalon.model.Bill;
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
public class ListSelectServiceFragment extends Fragment {

    private ArrayList<Service> listAllService;
    private RecyclerView rcViewListService;
    private ListSelectServiceAdapter adapter;
    private GridLayoutManager gridLayoutManager;
    private TextView btnOk;
    private ImageView ivBack;

    public ListSelectServiceFragment() {
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
        listAllService = new ArrayList<>();
        rcViewListService = view.findViewById(R.id.rcl_list_service);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcViewListService.setLayoutManager(gridLayoutManager);
        btnOk = view.findViewById(R.id.btnOke);
        btnOk.setText("Chọn dịch vụ");
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
        getListAllService();
    }
    private void getListAllService(){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListService()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseGetListService, this::handleError)
        );
    }
    private void handleResponseGetListService(ArrayList<Service> result) {
        if(result.size()>0){
            listAllService = result;
            ListSelectServiceAdapter adapter = new ListSelectServiceAdapter(listAllService, getContext());
            rcViewListService.setAdapter(adapter);
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