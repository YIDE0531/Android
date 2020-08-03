package com.example.foodpanda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.foodpanda.Model.EditModel;
import com.example.foodpanda.Model.MenuModel;
import com.example.foodpanda.R;

import java.util.ArrayList;

public class menuAdaper extends RecyclerView.Adapter<menuAdaper.ViewHandler> {
    private ArrayList<MenuModel> menuModel;
    private Context context;
    private int viewID;
    private NumPrice numPrice;

    public menuAdaper(Context context, ArrayList<MenuModel> MenuModel,int viewID,NumPrice numPrice){
        this.context = context;
        this.menuModel = MenuModel;
        this.viewID = viewID;
        this.numPrice = numPrice;
    }

    @NonNull
    @Override
    public menuAdaper.ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(viewID,parent,false);
        return new menuAdaper.ViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final menuAdaper.ViewHandler holder, final int position) {
        if(menuModel.get(position).getLangLogo().equals("notfind")){
            holder.imvItem.setVisibility(View.GONE);
        }else{
            holder.imvItem.setVisibility(View.VISIBLE);
            Glide.with(context).load(menuModel.get(position).getLangLogo())
                    .thumbnail(Glide.with(context).load(R.drawable.loading))
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(holder.imvItem);
        }
        if(menuModel.get(position).itemTitle.equals("notfind")){
            holder.tvTitle.setVisibility(View.GONE);
        }else{
            holder.tvTitle.setVisibility(View.VISIBLE);
            holder.tvTitle.setText(menuModel.get(position).itemTitle);
        }
        if(position < menuModel.size()-1) {
            if (!menuModel.get(position + 1).itemTitle.equals("notfind")) {
                holder.viewItem.setVisibility(View.GONE);
                holder.viewGroup.setVisibility(View.VISIBLE);
            } else {
                holder.viewItem.setVisibility(View.VISIBLE);
                holder.viewGroup.setVisibility(View.GONE);
            }
        }
        if(menuModel.get(position).itemNum>0){
            holder.tvNum.setVisibility(View.VISIBLE);
            holder.tvNum.setText(menuModel.get(position).itemNum+"");
        }else{
            holder.tvNum.setVisibility(View.GONE);
        }
        holder.tvName.setText(menuModel.get(position).itemName);
        holder.tvPrice.setText(menuModel.get(position).itemPrice);

        holder.llMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int price = stringPriceToInt(menuModel.get(position).itemPrice);
                if(price>0){
                    menuModel.get(position).itemNum += 1;
                    numPrice.itemClick(menuModel.get(position));
                    holder.tvNum.setVisibility(View.VISIBLE);
                    holder.tvNum.setText(menuModel.get(position).itemNum+"");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuModel.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        TextView tvName, tvTitle, tvPrice, tvNum;
        ImageView imvItem;
        View viewItem, viewGroup;
        ConstraintLayout llMain;

        public ViewHandler(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_item_cost);
            tvNum = itemView.findViewById(R.id.tv_num);
            imvItem = itemView.findViewById(R.id.imb_controls);
            viewItem = itemView.findViewById(R.id.view_item);
            viewGroup = itemView.findViewById(R.id.view_group);
            llMain = itemView.findViewById(R.id.cons_main);

        }
    }

    public interface NumPrice{
        void itemClick(MenuModel menuModel);
    }

    public void reLoadList(ArrayList<MenuModel> menuModel){
        this.menuModel = menuModel;
        notifyDataSetChanged();
    }

    public ArrayList<MenuModel> getMenuModel(){
        for(MenuModel menuModel:menuModel){
            menuModel.itemNum = 0;
        }
        return menuModel;
    }

    public static int stringPriceToInt(String price){
        int resultPrice;
        price = price.substring(price.indexOf("$")+1, price.indexOf("."));
        resultPrice = Integer.parseInt(price);
        return resultPrice;
    }
}