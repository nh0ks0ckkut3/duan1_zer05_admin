package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Bill;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Thong_Ke_Fragment extends Fragment {

    TextView tvService, tvSell, tvBoth, tvTotal;
    BarChart barChart;
    ImageView ivProgressBar;
    LinearLayout loading,layoutBarChart;
    Spinner spinnerMonth,spinnerYear;
    int totalMonth, totalWeek1, totalWeek2, totalWeek3, totalWeek4;
    int month = 1;
    int year;
    Calendar calendar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong__ke_, container, false);

        mapping(view);
        return view;
    }
    private void mapping(View view){
        tvService = view.findViewById(R.id.tvService);
        tvSell = view.findViewById(R.id.tvSell);
        tvBoth = view.findViewById(R.id.tvBoth);
        tvTotal = view.findViewById(R.id.tvTotal);
        barChart = view.findViewById(R.id.barChart);
        ivProgressBar = view.findViewById(R.id.ivProgressBar);
        loading = view.findViewById(R.id.loading);
        layoutBarChart = view.findViewById(R.id.layoutBarChart);
        spinnerMonth = view.findViewById(R.id.spinnerMonth);
        spinnerYear = view.findViewById(R.id.spinnerYear);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int yearCurrent = calendar.get(Calendar.YEAR);

        Glide.with(getContext()).load(getString(R.string.linkProgressBar2)).into(ivProgressBar);

        ArrayList<HashMap<String, Integer>> listSpn = new ArrayList<>();

        for(int i = 1; i <= 12; i++){
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put("month",i);
            listSpn.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),listSpn,R.layout.item_spinner_month,new String[]{"month"},new int[]{R.id.tvMonth});
        spinnerMonth.setAdapter(simpleAdapter);
        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loading.setVisibility(View.VISIBLE);
                layoutBarChart.setVisibility(View.GONE);
                month = position+1;
                getListBillTheoThang(month,year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayList<HashMap<String, Integer>> listSpnY = new ArrayList<>();


        for(int i = yearCurrent; i >= 2020; i--){
            HashMap<String, Integer> hashMap = new HashMap<>();
            hashMap.put("year",i);
            listSpnY.add(hashMap);
        }

        SimpleAdapter simpleAdapterY = new SimpleAdapter(getContext(),listSpnY,R.layout.item_spinner_month,new String[]{"year"},new int[]{R.id.tvMonth});
        spinnerYear.setAdapter(simpleAdapterY);
        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loading.setVisibility(View.VISIBLE);
                layoutBarChart.setVisibility(View.GONE);
                year = calendar.get(Calendar.YEAR)- position;
                getListBillTheoThang(month, year);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void getListBillTheoThang(int month, int year){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.getListBillTheoThang(month, year)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    private void handleResponse(ArrayList<Bill> result) {
        totalMonth = 0; totalWeek1 = 0 ; totalWeek2 = 0; totalWeek3 = 0; totalWeek4 = 0;
        for(Bill bill : result){
            int day = 0;
            try {
                day = Integer.parseInt(bill.getDate().substring(0,2));
            }catch (Exception e){
                day = Integer.parseInt(bill.getDate().substring(0,0));

            }

            if(day <= 7){
                totalWeek1+=bill.getTotalPrice();
            }else if(day <= 14){
                totalWeek2 += bill.getTotalPrice();
            }else if(day <= 21){
                totalWeek3 += bill.getTotalPrice();
            }else{
                totalWeek4 += bill.getTotalPrice();
            }
        }
        totalMonth = totalWeek1 + totalWeek2 + totalWeek3 + totalWeek4;
        NumberFormat currentLocale = NumberFormat.getInstance();
        tvTotal.setText(currentLocale.format(totalMonth));
        setChart();
    }
    private void setChart(){
        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(1,totalWeek1));
        visitors.add(new BarEntry(2,totalWeek2));
        visitors.add(new BarEntry(3,totalWeek3));
        visitors.add(new BarEntry(4,totalWeek4));

        BarDataSet barDataSet = new BarDataSet(visitors, "doanh thu salon 4 tuần");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("biểu đồ thống kê");
        barChart.animateY(2000);
        loading.setVisibility(View.GONE);
        layoutBarChart.setVisibility(View.VISIBLE);
    }

}