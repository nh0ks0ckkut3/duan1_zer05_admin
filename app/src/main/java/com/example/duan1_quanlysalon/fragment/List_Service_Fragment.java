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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.adapter.ListSelectServiceAdapter;
import com.example.duan1_quanlysalon.adapter.ListStylistAdapter;
import com.example.duan1_quanlysalon.adapter.ServiceAdapter;
import com.example.duan1_quanlysalon.database.ServiceDAO;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.ItemClickGetDetailService;
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class List_Service_Fragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<Service> listAllService;
    FloatingActionButton fltAdd;
    private Service servieClick;
    LinearLayout lnProgressBar;
    ImageView progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_list__service_, container, false);
        recyclerView = view.findViewById(R.id.rcl_list_service);
        listAllService = new ArrayList<>();
        fltAdd = view.findViewById(R.id.floatAdd);
        lnProgressBar = view.findViewById(R.id.lnProgressBar);
        progressBar = view.findViewById(R.id.progressBar);
        Glide.with(getContext()).load(getString(R.string.linkProgressBar2)).into(progressBar);
        ((MainActivity)getContext()).toolbar.setVisibility(View.VISIBLE);
        ((MainActivity)getContext()).titleToolbar.setText("DS dịch vụ");
        LoadData();

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new Add_Service_Fragment());
            }
        });

        return view;
    }

    private void LoadData(){
        GetListServiceAPI();
    }

    private void GetListServiceAPI(){
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
            ServiceAdapter adapter = new ServiceAdapter(listAllService, getContext(), new ItemClickGetDetailService() {
                @Override
                public void onClick(Service service) {
                    servieClick = service;
                }
            });

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
        lnProgressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}