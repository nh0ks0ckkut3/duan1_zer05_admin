package com.example.duan1_quanlysalon.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.duan1_quanlysalon.DSdichvuActivity;
import com.example.duan1_quanlysalon.DSnhanvienActivity;
import com.example.duan1_quanlysalon.DSsanphamActivity;
import com.example.duan1_quanlysalon.R;

public class Menu_Fragment extends Fragment {
    DrawerLayout drawerLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        Toolbar toolbar = view.findViewById(R.id.toolBar);

        CardView cvMenu1 = view.findViewById(R.id.cvMenu1);
        CardView cvMenu2 = view.findViewById(R.id.cvMenu2);
        CardView cvMenu3 = view.findViewById(R.id.cvMenu3);

        drawerLayout = view.findViewById(R.id.drawerLayout);

        cvMenu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DSnhanvienActivity.class));
            }
        });

        cvMenu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DSdichvuActivity.class));
            }
        });

        cvMenu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DSsanphamActivity.class));
            }
        });
        return view;
    }
}
