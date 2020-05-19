package com.example.foodpanda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodpanda.Model.MenuModel;
import com.example.foodpanda.R;

import java.util.ArrayList;

public class menuAdaper extends RecyclerView.Adapter<menuAdaper.ViewHandler> {
    ArrayList<com.example.foodpanda.Model.MenuModel> MenuModel;
    Context context;
    int viewID;

    public menuAdaper(Context context, ArrayList<MenuModel> MenuModel,int viewID){
        this.context = context;
        this.MenuModel = MenuModel;
        this.viewID = viewID;
    }

    @NonNull
    @Override
    public menuAdaper.ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewID,parent,false);
        return new menuAdaper.ViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull menuAdaper.ViewHandler holder, int position) {
        if(MenuModel.get(position).getLangLogo()==0){
            holder.imvItem.setVisibility(View.GONE);
        }else{
            holder.imvItem.setVisibility(View.VISIBLE);
            Glide.with(context).load(MenuModel.get(position).getLangLogo()).into(holder.imvItem);
        }
        if(MenuModel.get(position).itemTitle.equals("")){
            holder.tvTitle.setVisibility(View.GONE);
        }else{
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(MenuModel.get(position).itemTitle);
        }
        if(position < MenuModel.size()-1) {
            if (!MenuModel.get(position + 1).itemTitle.equals("")) {
                holder.viewItem.setVisibility(View.GONE);
                holder.viewGroup.setVisibility(View.VISIBLE);
            } else {
                holder.viewItem.setVisibility(View.VISIBLE);
                holder.viewGroup.setVisibility(View.GONE);
            }
        }
        holder.tvName.setText(MenuModel.get(position).itemName);
        holder.tvPrice.setText(MenuModel.get(position).itemPrice);

        /*holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, menuActivity.class);
                context.startActivity(intent);
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return MenuModel.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        TextView tvName, tvTitle, tvPrice;
        ImageView imvItem;
        View viewItem, viewGroup;
        //ConstraintLayout llMain;

        public ViewHandler(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_item_cost);
            imvItem = itemView.findViewById(R.id.imb_controls);
            viewItem = itemView.findViewById(R.id.view_item);
            viewGroup = itemView.findViewById(R.id.view_group);
            //llMain = itemView.findViewById(R.id.ll_main);
        }
    }
}