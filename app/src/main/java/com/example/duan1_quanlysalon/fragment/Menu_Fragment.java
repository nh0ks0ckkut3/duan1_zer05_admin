package com.example.duan1_quanlysalon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.duan1_quanlysalon.ChangePassActivity;
import com.example.duan1_quanlysalon.DSdichvuActivity;
import com.example.duan1_quanlysalon.DSnhanvienActivity;
import com.example.duan1_quanlysalon.DSsanphamActivity;
import com.example.duan1_quanlysalon.LoginActivity;
import com.example.duan1_quanlysalon.MainActivity;
import com.example.duan1_quanlysalon.R;
import com.google.android.material.navigation.NavigationView;

public class Menu_Fragment extends Fragment {
    private TextView tvDSnhanvien, tvDSdichvu, tvDSsanpham, tvmautoc, tvquydinh,tvphuthu, tvInfor, tvChangePassword, tvLogOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
//nghia
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        tvDSnhanvien = view.findViewById(R.id.tvDSnhanvien);
        tvDSdichvu = view.findViewById(R.id.tvDSdichvu);
        tvDSsanpham = view.findViewById(R.id.tvDSsanpham);
        tvmautoc = view.findViewById(R.id.tvmautoc);
        tvquydinh = view.findViewById(R.id.tvquydinh);
        tvphuthu = view.findViewById(R.id.tvphuthu);
        tvInfor = view.findViewById(R.id.tvInfor);
        tvChangePassword = view.findViewById(R.id.tvChangePassword);
        tvLogOut = view.findViewById(R.id.tvLogOut);
        tvDSnhanvien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Employee_Fragment());
            }
        });

        tvDSdichvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Service_Fragment());
            }
        });

        tvDSsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getContext()).replayFragment(new List_Product_Fragment());
            }
        });


        tvmautoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), List_Hairmodel_Fragment.class));
            }
        });

        tvquydinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), List_Regulations.class));
            }
        });

        tvphuthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), List_Surcharge.class));
            }
        });

        tvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ChangePassActivity.class));
            }
        });

        tvLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        return view;
    }
}
