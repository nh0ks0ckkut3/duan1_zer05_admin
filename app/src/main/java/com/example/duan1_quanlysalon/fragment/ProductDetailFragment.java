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
import com.example.duan1_quanlysalon.model.Product;
import com.example.duan1_quanlysalon.model.Service;
import com.example.duan1_quanlysalon.model.ServiceAPI;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductDetailFragment extends Fragment {

    private Product product;
    private EditText edName,edPrice,edAmount,edClassify;
    private TextView tvID,tvDelete,tvEdit;
    private ImageView ivAvatar,ivBack;


    public ProductDetailFragment(Product product) {
        this.product = product;
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
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);
        edName = view.findViewById(R.id.edName);
        edPrice = view.findViewById(R.id.edPrice);
        tvID = view.findViewById(R.id.tvID);
        tvDelete = view.findViewById(R.id.tvDelete);
        tvEdit = view.findViewById(R.id.tvEdit);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        edClassify = view.findViewById(R.id.edClassify);
        edAmount = view.findViewById(R.id.edAmount);
        ivBack = view.findViewById(R.id.ivBack);

        edName.setText(product.getName());
        edClassify.setText(product.getClassify());
        tvID.setText(product.getId()+"");
        edPrice.setText(product.getPrice()+"");
        Glide.with(this).load(product.getImage()).into(ivAvatar);
        edAmount.setText(product.getAmount()+"");
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "không thể xóa", Toast.LENGTH_SHORT).show();
            }
        });
        tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = 0;
                int amount = 0;
                try {
                    price = Integer.parseInt(edPrice.getText().toString());
                    amount = Integer.parseInt(edAmount.getText().toString());
                    String name = edName.getText().toString();
                    String classify = edClassify.getText().toString();
                    if(name.length()==0 || classify.length() == 0){
                        Toast.makeText(getContext(), "vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
                    }else{
                        Product productEdit = product;
                        productEdit.setName(name);
                        productEdit.setPrice(price);
                        productEdit.setAmount(amount);
                        productEdit.setClassify(classify);
                        updateProduct(productEdit);
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(), "vui lòng nhập giá và số lượng", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Product_Fragment());
            }
        });


        return view;
    }
    private void updateProduct(Product product){
        ServiceAPI requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_API_ZERO5)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceAPI.class);

        new CompositeDisposable().add(requestInterface.updateProduct(product)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponseUpdate, this::handleError)
        );
    }
    private void handleResponseUpdate(Boolean result) {
        if(result){
            Toast.makeText(getContext(), "đã sửa", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(), "errol", Toast.LENGTH_SHORT).show();
        }
    }
    private void handleError(Throwable error) {
        Toast.makeText(getContext(), "lỗi, thử lại sau!", Toast.LENGTH_SHORT).show();
    }
}