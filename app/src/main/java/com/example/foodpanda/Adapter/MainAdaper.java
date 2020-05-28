package com.example.foodpanda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.foodpanda.Model.MainModel;
import com.example.foodpanda.R;

import java.util.ArrayList;

public class MainAdaper extends RecyclerView.Adapter<MainAdaper.ViewHandler> {
    ArrayList<MainModel> mainModels;
    Context context;

    public MainAdaper(Context context, ArrayList<MainModel> mainModels){
        this.context = context;
        this.mainModels = mainModels;
    }

    @NonNull
    @Override
    public ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item,parent,false);
        return new ViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHandler holder, int position) {
        Glide.with(context).load(mainModels.get(position).getLangLogo())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHandler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }
}
