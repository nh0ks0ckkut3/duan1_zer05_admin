package com.example.duan1_quanlysalon.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_quanlysalon.R;
import com.example.duan1_quanlysalon.model.Item;
import com.example.duan1_quanlysalon.WedViewActivity;

import java.util.ArrayList;

public class NewsAPIAdapter extends RecyclerView.Adapter<NewsAPIAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Item> list;
    private String linkImg;

    public NewsAPIAdapter(Context context, ArrayList<Item> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_channel,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvPubDate.setText(list.get(position).getPubDate());
        linkImg = list.get(position).getDescription().get__cdata();
        int begin = linkImg.indexOf("<img src=");
        if(begin > 0){
            begin+=10;
            int end = linkImg.indexOf("\" ></a>",begin);
            if(begin > 0 && end > 0){
                linkImg=linkImg.substring(begin,end);
                Glide
                        .with(context)
                        .load(linkImg)
                        .into(holder.ivTest);
            }
        }else{
            Glide
                    .with(context)
                    .load("@drawable/producttemp")
                    .into(holder.ivTest);
        }


        holder.layoutItem.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle, tvPubDate;
        ImageView ivTest;
        LinearLayout layoutItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPubDate = itemView.findViewById(R.id.tvPubDate);
            layoutItem = itemView.findViewById(R.id.layoutItem);
            ivTest = itemView.findViewById(R.id.ivTest);
        }
    }
}
