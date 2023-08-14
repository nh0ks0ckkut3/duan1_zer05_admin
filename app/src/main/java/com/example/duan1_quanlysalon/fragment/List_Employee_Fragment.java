package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.duan1_quanlysalon.adapter.EmployeeAdapter;
import com.example.duan1_quanlysalon.adapter.ProductAdapter;
import com.example.duan1_quanlysalon.model.Employee;
import com.example.duan1_quanlysalon.model.Product;
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

public class List_Employee_Fragment extends Fragment {
    RecyclerView rcViewStylist,rcViewSkinner,rcViewListAdmin;

    ArrayList<Employee> listStylist,listSkinner,listAdmin;
    FloatingActionButton fltAdd;
    LinearLayout lnProgressBar1,lnProgressBar2,lnProgressBar3;
    ImageView progressBar1,progressBar2, progressBar3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,@NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list__employee_, container, false);
        rcViewStylist = view.findViewById(R.id.rcViewListStylist);
        rcViewSkinner = view.findViewById(R.id.rcViewListSkinner);
        rcViewListAdmin = view.findViewById(R.id.rcViewListAdmin);
        lnProgressBar1 = view.findViewById(R.id.lnProgressBar1);
        lnProgressBar2 = view.findViewById(R.id.lnProgressBar2);
        lnProgressBar3 = view.findViewById(R.id.lnProgressBar3);
        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
        progressBar3 = view.findViewById(R.id.progressBar3);
        Glide.with(getContext()).load(getString(R.string.linkProgressBar2)).into(progressBar1);
        Glide.with(getContext()).load(getString(R.string.linkProgressBar2)).into(progressBar2);
        Glide.with(getContext()).load(getString(R.string.linkProgressBar2)).into(progressBar3);
        ((MainActivity)getContext()).toolbar.setVisibility(View.VISIBLE);
        ((MainActivity)getContext()).titleToolbar.setText("DS nhân viên");
        listStylist = new ArrayList<>();
        listSkinner = new ArrayList<>();
        listAdmin = new ArrayList<>();
        fltAdd = view.findViewById(R.id.floatAdd);


        LoadData();

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new Add_Employee_Fragment());
            }
        });

        return view;
    }
    private void LoadData(){
            ServiceAPI requestInterface = new Retrofit.Builder()
                    .baseUrl(BASE_API_ZERO5)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ServiceAPI.class);

            new CompositeDisposable().add(requestInterface.getListEmployee()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
            );
        }
        private void handleResponse(ArrayList<Employee> result) {
            if(result.size()>0){
                for(Employee employee : result){
                    if(employee.getClassify().equals("stylist")){
                        listStylist.add(employee);
                    }else if(employee.getClassify().equals("skinner")){
                        listSkinner.add(employee);
                    }else if(employee.getClassify().equals("admin")){
                        listAdmin.add(employee);
                    }
                }
                EmployeeAdapter adapterStylist = new EmployeeAdapter(listStylist, getContext());
                EmployeeAdapter adapterSkinner = new EmployeeAdapter(listSkinner, getContext());
                EmployeeAdapter adapterAdmin = new EmployeeAdapter(listAdmin, getContext());

                LinearLayoutManager linearLayoutManagerStylist = new LinearLayoutManager(getContext());
                LinearLayoutManager linearLayoutManagerSkinner = new LinearLayoutManager(getContext());
                LinearLayoutManager linearLayoutManagerAdmin = new LinearLayoutManager(getContext());

                rcViewStylist.setLayoutManager(linearLayoutManagerStylist);
                rcViewSkinner.setLayoutManager(linearLayoutManagerSkinner);
                rcViewListAdmin.setLayoutManager(linearLayoutManagerAdmin);

                rcViewStylist.setAdapter(adapterStylist);
                rcViewSkinner.setAdapter(adapterSkinner);
                rcViewListAdmin.setAdapter(adapterAdmin);

                lnProgressBar1.setVisibility(View.GONE);
                lnProgressBar2.setVisibility(View.GONE);
                lnProgressBar3.setVisibility(View.GONE);
                rcViewListAdmin.setVisibility(View.VISIBLE);
                rcViewStylist.setVisibility(View.VISIBLE);
                rcViewSkinner.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
            }
        }


        private void handleError(Throwable error) {
            Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
        }
}