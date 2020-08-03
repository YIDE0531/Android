package com.example.foodpanda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpanda.Model.EditModel;
import com.example.foodpanda.R;

import java.util.ArrayList;

public class EditOrderAdaper extends RecyclerView.Adapter<EditOrderAdaper.ViewHandler> {
    private ArrayList<EditModel> editModel;
    private Context context;
    private EditMethod editMethod;

    public EditOrderAdaper(Context context, ArrayList<EditModel> editModel, EditMethod editMethod){
        this.context = context;
        this.editModel = editModel;
        this.editMethod = editMethod;
    }

    @NonNull
    @Override
    public ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_edit_order ,parent ,false);
        return new ViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHandler holder, final int position) {
        holder.tvNum.setText(editModel.get(position).itemNum + "");
        holder.tvName.setText(editModel.get(position).itemName);
        holder.tvPrice.setText("$ " + editModel.get(position).itemTotalPrice);
        holder.viewBaseLine.setVisibility(position==editModel.size()-1?View.INVISIBLE:View.VISIBLE);

        holder.imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editModel.get(position).itemNum -= 1;
                if(editModel.get(position).itemNum==0){
                    editModel.remove(position);
                }else{
                    editModel.get(position).itemTotalPrice -= editModel.get(position).itemSinglePrice;
                    holder.tvNum.setText(editModel.get(position).itemNum + "");
                    holder.tvPrice.setText(" "+editModel.get(position).itemTotalPrice);
                }
                notifyDataSetChanged();
                editMethod.Count();
            }
        });

        holder.imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editModel.get(position).itemNum += 1;
                editModel.get(position).itemTotalPrice += editModel.get(position).itemSinglePrice;
                holder.tvNum.setText(editModel.get(position).itemNum + "");
                holder.tvPrice.setText(" "+editModel.get(position).itemTotalPrice);
                notifyDataSetChanged();
                editMethod.Count();
            }
        });

    }

    @Override
    public int getItemCount() {
        return editModel.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice, tvNum;
        ImageView imvDelete, imvAdd;
        View viewBaseLine;

        public ViewHandler(@NonNull View itemView) {
            super(itemView);
            imvDelete = itemView.findViewById(R.id.imv_delete);
            imvAdd = itemView.findViewById(R.id.imv_add);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvNum = itemView.findViewById(R.id.tv_num);
            viewBaseLine = itemView.findViewById(R.id.view_baseline);
        }
    }

    public interface EditMethod{
        void Count();

    }
}