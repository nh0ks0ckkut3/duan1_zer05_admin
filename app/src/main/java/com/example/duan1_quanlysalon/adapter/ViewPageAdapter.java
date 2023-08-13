package com.example.duan1_quanlysalon.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachChuaToi;
import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachDangCho;
import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachPhucVu;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }



    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Booking_Tab_KhachChuaToi();
            case 1:
                return new Booking_Tab_KhachDangCho();
            case 2:
                return new Booking_Tab_KhachPhucVu();
            default:
                return new Booking_Tab_KhachChuaToi();

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
