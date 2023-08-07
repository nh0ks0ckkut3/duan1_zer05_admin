package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Add_Service_Fragment extends Fragment {

    TextView tvAddImg, tvAddService;
    EditText edID,edName,edPrice,edClassify;
    ImageView ivBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_service, container, false);
        tvAddImg = view.findViewById(R.id.tvAddImg);
        tvAddService = view.findViewById(R.id.tvAddService);
        edID = view.findViewById(R.id.edID);
        edID.setFocusable(false);
        edName = view.findViewById(R.id.edName);
        edPrice = view.findViewById(R.id.edPrice);
        edClassify = view.findViewById(R.id.edClassify);
        ivBack = view.findViewById(R.id.ivBack);

        tvAddService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                int price = 0;
                try {
                    price = Integer.parseInt(edPrice.getText().toString());
                }catch (Exception e){
                    Toast.makeText(getContext(), "vui lòng nhập giá", Toast.LENGTH_SHORT).show();
                }

                String classify = edClassify.getText().toString();
                String img = "";
                if(name.length()<1 || price < 1 || classify.length() < 1){
                    Toast.makeText(getContext(), "chỉ có thể bỏ trống mã dịch vụ", Toast.LENGTH_SHORT).show();
                }else {
                    addServiceAPI(new Service(name,price,classify,img));
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Service_Fragment());
            }
        });

        return view;
    }
    private void addServiceAPI(Service service){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.addService(service)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
        );
    }
    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
    private void handleResponse(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "đã thêm", Toast.LENGTH_SHORT).show();
            ((MainActivity)getContext()).replayFragment(new List_Service_Fragment());
        }
        else Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
    }
}