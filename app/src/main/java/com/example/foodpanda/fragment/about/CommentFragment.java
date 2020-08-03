package com.example.foodpanda.fragment.about;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodpanda.AboutActivity;
import com.example.foodpanda.Adapter.CommentAdapter;
import com.example.foodpanda.Model.CommentModel;
import com.example.foodpanda.R;
import com.example.foodpanda.util.MyApplication;
import java.util.ArrayList;
import java.util.Random;

public class CommentFragment extends Fragment {
    private Context mContext;
    private View view;
    private RecyclerView recyclerView;
    private CommentAdapter productInformationAdaper;
    private ArrayList<CommentModel> piModel;
    private String comment;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_comment, container, false);
            init(view);
            initData();
            initListener();
        }
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // 保证容器Activity实现了回调接口 否则抛出异常警告
        try {
            MyApplication.fragment = this;
            comment = ((AboutActivity) context).getCommentParams();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Fragment1CallBack");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        MyApplication.fragment = this;

    }

    void init(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

    }

    void initData() {
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        piModel = new ArrayList<>();
        Random num = new Random();
        if(comment!=null && comment.length()>=3) {
            String[] owen = comment.split(",,,");
            CommentModel model = new CommentModel(owen.length/3 + " 評論", "", "", num.nextInt(5)+1);
            piModel.add(model);
            for (int i = 0; i < owen.length; i += 3) {
                model = new CommentModel(owen[i].trim(), owen[i + 1].trim(), owen[i + 2].trim(), num.nextInt(5)+1);
                piModel.add(model);
            }
        }else{
            CommentModel model = new CommentModel(" 0 評論", "", "", num.nextInt(5)+1);
            piModel.add(model);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        productInformationAdaper = new CommentAdapter(mContext, piModel);
        recyclerView.setAdapter(productInformationAdaper);

    }

    void initListener() {

    }

    public static CommentFragment newInstance() {
        return new CommentFragment();
    }
}
