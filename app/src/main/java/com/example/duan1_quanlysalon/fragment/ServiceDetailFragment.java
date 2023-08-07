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

import com.bumptech.glide.Glide;
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

public class ServiceDetailFragment extends Fragment {

    private Service service;
    private TextView tvName,tvID,tvDelete,tvEdit;
    private EditText edPrice, edClassify;
    private ImageView ivAvatar,ivBack;


    public ServiceDetailFragment(Service service) {
        this.service = service;
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);
        tvName = view.findViewById(R.id.tvName);
        edPrice = view.findViewById(R.id.edPrice);
        edClassify = view.findViewById(R.id.edClassify);
        tvID = view.findViewById(R.id.tvID);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvEdit = view.findViewById(R.id.tvEdit);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        ivBack = view.findViewById(R.id.ivBack);
        
        tvName.setText(service.getName());
        edClassify.setText(service.getClassifyEmployee());
        tvID.setText(service.getId()+"");
        edPrice.setText(service.getPrice()+"");
        Glide.with(this).load(service.getImageService()).into(ivAvatar);
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "không thể xóa", Toast.LENGTH_SHORT).show();
            }
        });

        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int priceEdit = Integer.parseInt(edPrice.getText().toString());
                String classifyEdit = edClassify.getText().toString();
                Service serviceEdit = service;
                serviceEdit.setPrice(priceEdit);
                serviceEdit.setClassifyEmployee(classifyEdit);
                updateService(serviceEdit);
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
    private void updateService(Service service){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.updateService(service)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdate, this::handleError)
        );
    }
    private void handleResponseUpdate(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "đã sửa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "đã có dữ liệu với hóa đơn, không thể xóa", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}