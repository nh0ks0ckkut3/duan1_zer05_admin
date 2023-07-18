package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.Service.ServiceAPI.BASE_SERVICE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.Service.ServiceAPI;
import com.example.duan1_quanlysalon.adapter.NewsAPIAdapter;
import com.example.duan1_quanlysalon.model.Item;
import com.example.duan1_quanlysalon.model.Model_Channel;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Item> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_news_fragment, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        list = new ArrayList<>();
        demoCallAPI();
        loadData();

        return view;
    }

    private void demoCallAPI() {
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_SERVICE)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);
        new CompositeDisposable().add(requestInterface.getChannel()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }

    private void handleResponse(Model_Channel model_channel){
        list = model_channel.getChannel().getItem();
        loadData();
    }

    private void handleError(Throwable throwable){
        Toast.makeText(getContext(), "Lỗi API", Toast.LENGTH_SHORT).show();
    }
    private void loadData(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        NewsAPIAdapter adapter = new NewsAPIAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);
    }
}
