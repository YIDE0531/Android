package com.example.foodpanda.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodpanda.Model.PIModel;
import com.example.foodpanda.R;
import com.example.foodpanda.menuActivity;

import java.util.ArrayList;

public class ProductInformationAdaper extends RecyclerView.Adapter<ProductInformationAdaper.ViewHandler> {
    ArrayList<com.example.foodpanda.Model.PIModel> PIModel;
    Context context;
    int viewID;

    public ProductInformationAdaper(Context context, ArrayList<PIModel> PIModel,int viewID){
        this.context = context;
        this.PIModel = PIModel;
        this.viewID = viewID;
    }

    @NonNull
    @Override
    public ProductInformationAdaper.ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewID,parent,false);
        return new ProductInformationAdaper.ViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductInformationAdaper.ViewHandler holder, int position) {
        Glide.with(context).load(PIModel.get(position).getlangLogoUrl()).into(holder.imageView);
        holder.tvStoreName.setText(PIModel.get(position).getShopName());
        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, menuActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return PIModel.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvStoreName;
        ConstraintLayout llMain;

        public ViewHandler(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.it_img_temp);
            tvStoreName = itemView.findViewById(R.id.tv_storeName);
            llMain = itemView.findViewById(R.id.ll_main);

        }
    }
}