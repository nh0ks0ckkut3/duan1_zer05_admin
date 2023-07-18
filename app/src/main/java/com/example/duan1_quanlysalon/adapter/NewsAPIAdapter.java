package com.example.duan1_quanlysalon.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.WedViewActivity;
import com.example.duan1_quanlysalon.model.Item;

import java.util.ArrayList;

public class NewsAPIAdapter extends RecyclerView.Adapter<NewsAPIAdapter.ViewHolder>{
    Context context;
    ArrayList<Item> list;
    String linkImage;

    public NewsAPIAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_channel, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDay.setText(list.get(position).getPubDate());
        holder.tvTitle.setText(list.get(position).getTitle());
        linkImage = list.get(position).getDescription().get__cdata();
        int begin = linkImage.indexOf("<img src=");
        if(begin > 0){
            begin+=10;
            int end = linkImage.indexOf("\" ></a></br>");
            if (begin > 0 && end > 0){
                linkImage = linkImage.substring(begin, end);
                Glide.with(context).load(linkImage).into(holder.imgView);
            }
        }else{
            Glide.with(context).load("@drawable/icon_course.png").into(holder.imgView);
        }
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WedViewActivity.class);
                intent.putExtra("linkNews", list.get(position).getLink());
                ((Activity)context).startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDay;
        ImageView imgView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDay = itemView.findViewById(R.id.tvDay);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            imgView = itemView.findViewById(R.id.imgView);
        }
    }

}
