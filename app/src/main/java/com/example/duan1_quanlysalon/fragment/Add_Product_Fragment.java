package com.example.duan1_quanlysalon.fragment;

import static com.example.duan1_quanlysalon.model.ServiceAPI.BASE_API_ZERO5;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
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
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Add_Product_Fragment extends Fragment {

    ImageView img_add_product;
    EditText classify_add_product, name_add_product, unit_add_product, amount_add_product,
            brand_add_product, price_add_product;
    AppCompatButton btn_complete_add_product, btn_add_img_product;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add__product_, container, false);

        img_add_product = view.findViewById(R.id.img_add_product);
        classify_add_product = view.findViewById(R.id.classify_add_product);
        name_add_product = view.findViewById(R.id.name_add_product);
        unit_add_product = view.findViewById(R.id.unit_add_product);
        amount_add_product = view.findViewById(R.id.amount_add_product);
        brand_add_product = view.findViewById(R.id.brand_add_product);
        price_add_product = view.findViewById(R.id.price_add_product);
        btn_complete_add_product = view.findViewById(R.id.btn_complete_add_product);
        btn_add_img_product = view.findViewById(R.id.btn_add_img_product);

        btn_complete_add_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_add_product.getText().toString();
                String price = price_add_product.getText().toString();
                String unit = unit_add_product.getText().toString();
                String amount = amount_add_product.getText().toString();
                String brand = brand_add_product.getText().toString();
                String classify = classify_add_product.getText().toString();
                String img = "";
                if(name.length()<1 || price.length() < 1 || classify.length() < 1 ||
                    unit.length() < 1 || amount.length() < 1 || brand.length() < 1){
                    Toast.makeText(getContext(), "Không bỏ trống", Toast.LENGTH_SHORT).show();
                }else {
                    addProductAPI(new Product(name,Integer.parseInt(price),unit,Integer.parseInt(amount),brand,classify,img));
                }
            }
        });

        return view;
    }
    private void addProductAPI(Product product){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.addProduct(product)
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
            ((MainActivity)getContext()).replayFragment(new List_Product_Fragment());
        }
        else Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
    }
}