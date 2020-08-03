package com.example.foodpanda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodpanda.Model.CommentModel;
import com.example.foodpanda.R;
import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHandler> {
    ArrayList<CommentModel> PIModel;
    Context context;

    public CommentAdapter(Context context, ArrayList<CommentModel> PIModel){
        this.context = context;
        this.PIModel = PIModel;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_comment_item, parent,false);
        return new CommentAdapter.ViewHandler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHandler holder, final int position) {
        if(position==0){
            holder.llStar.setVisibility(View.GONE);
            holder.tvName.setText(PIModel.get(position).itemName);
            holder.tvDate.setVisibility(View.GONE);
            holder.tvComment.setVisibility(View.GONE);
        }else{
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(30, 30);
            holder.llStar.removeAllViews();
            for(int i=0;i<5;i++) {
                ImageView ii= new ImageView(context);
                ii.setBackgroundResource(i<PIModel.get(position).starScore?R.drawable.signs:R.drawable.sign_gray);
                ii.setLayoutParams(layoutParams);
                holder.llStar.addView(ii);
            }

            holder.llStar.setVisibility(View.VISIBLE);
            holder.tvName.setText((PIModel.get(position).itemName.equals("noperson"))?"":PIModel.get(position).itemName);
            holder.tvDate.setText(PIModel.get(position).itemComDate);
            holder.tvComment.setText(PIModel.get(position).itemComment);
            holder.tvDate.setVisibility(View.VISIBLE);
            holder.tvComment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return PIModel.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        LinearLayout llStar;
        TextView tvName, tvDate, tvComment;

        public ViewHandler(@NonNull View itemView) {
            super(itemView);
            llStar = itemView.findViewById(R.id.ll_star);
            tvName = itemView.findViewById(R.id.tv_name);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvComment = itemView.findViewById(R.id.tv_comment);

        }
    }
}