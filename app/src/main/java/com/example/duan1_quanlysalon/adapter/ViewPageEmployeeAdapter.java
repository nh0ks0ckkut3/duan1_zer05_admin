package com.example.duan1_quanlysalon.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachChuaToi;
import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachDangCho;
import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachDangCho_Employee_Fragment;
import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachPhucVu;
import com.example.duan1_quanlysalon.fragment.Booking_Tab_KhachPhucVu_Employee_Fragment;

public class ViewPageEmployeeAdapter extends FragmentStateAdapter {
    public ViewPageEmployeeAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Booking_Tab_KhachDangCho_Employee_Fragment();
            case 1:
                return new Booking_Tab_KhachPhucVu_Employee_Fragment();
            default:
                return new Booking_Tab_KhachDangCho_Employee_Fragment();

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
