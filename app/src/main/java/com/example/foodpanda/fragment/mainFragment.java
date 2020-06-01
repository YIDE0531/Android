package com.example.foodpanda.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.foodpanda.Adapter.MainAdaper;
import com.example.foodpanda.Adapter.ProductInformationAdaper;
import com.example.foodpanda.Model.AllModel;
import com.example.foodpanda.Model.MainModel;
import com.example.foodpanda.Model.PIModel;
import com.example.foodpanda.R;
import com.example.foodpanda.util.MyApplication;
import java.util.ArrayList;

/**
 * Created by Charlie on 2/3/2016.
 */

//Our class extending fragment
public class mainFragment extends Fragment {
    private RecyclerView recyclerView, rvPayAgain, rvRecommend, rvallRestaurant;
    private ArrayList<MainModel> mainModels;
    private ArrayList<PIModel> piModel;
    private MainAdaper mainAdaper;

    private ProductInformationAdaper productInformationAdaper;
    private Context mContext;
    private TextView tvPayAgain, tvRecommend, tvAllRestaurant;
    private int nowShow = 20;
    private String[] shopNameArray, shopImageArray, shopInfoUrlArray ;
    private LinearLayoutManager manager;
    private boolean isScrolled = false;
    private SwipeRefreshLayout swipe_refresh_layout;
    private View view;


    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_main, container, false);
            init(view);
            initData();
            initListener();
            //Returning the layout file after inflating
            //Change R.layout.tab1 in you classes
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

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement Fragment1CallBack");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    void init(View view) {
        rvallRestaurant = view.findViewById(R.id.recycler_allRestaurant);
        rvPayAgain = view.findViewById(R.id.recycler_payAgain);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        rvRecommend = view.findViewById(R.id.recycler_recommend);

        tvPayAgain = (TextView) view.findViewById(R.id.tv_payAgain);
        tvRecommend = (TextView) view.findViewById(R.id.tv_recommend);
        tvAllRestaurant = (TextView) view.findViewById(R.id.tv_allRestaurant);
        //swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);//找到刷新对象

    }

    void initData() {
        manager = new LinearLayoutManager(getActivity());
    }

    void initListener(){
        rvallRestaurant.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //重写该方法主要是判断recyclerview是否在滑动
                //0停止 ，12都是滑动
                if (newState == 0) {
                    isScrolled = false;
                } else {
                    isScrolled = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //这个主要是recyclerview滑动时让tab定位的方法
                if (isScrolled) {
                    int top = manager.findFirstVisibleItemPosition();
                    int bottom = manager.findLastVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    //RecyclerView的滑动状态
                    int state = recyclerView.getScrollState();
                    if(top > 0 && bottom == totalItemCount - 1 ){
                        if(nowShow+10<shopNameArray.length){
                            for(int i=nowShow;i<nowShow+10;i++){
                                PIModel model = new PIModel(shopImageArray[i], shopNameArray[i], shopInfoUrlArray[i]);
                                piModel.add(model);
                            }

                            productInformationAdaper.notifyDataSetChanged();
                            nowShow += 10;
                        }
                    }
                }
            }
        });

//        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {//设置刷新监听器
//            @Override
//            public void onRefresh() {
//                ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();
//                progressDialogUtil.showProgressDialog(mContext);
//                new CallApiTask(mContext, progressDialogUtil, apiCallBack, "getData").execute(AppConfig.getUrlPath() + "getData.php");
//
//                new Handler().postDelayed(new Runnable() {//模拟耗时操作
//                    @Override
//                    public void run() {
//
//                        swipe_refresh_layout.setRefreshing(false);//取消刷新
//                    }
//                },2000);
//            }
//        });
    }

    public void showData(AllModel shopInfo) {
        tvPayAgain.setVisibility(View.VISIBLE);
        tvRecommend.setVisibility(View.VISIBLE);
        tvAllRestaurant.setVisibility(View.VISIBLE);

        Integer[] langlogo = {R.drawable.freead, R.drawable.discount, R.drawable.freead, R.drawable.discount, R.drawable.freead};
        mainModels = new ArrayList<>();
        for (int i = 0; i < langlogo.length; i++) {
            MainModel model = new MainModel(langlogo[i]);
            mainModels.add(model);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mainAdaper = new MainAdaper(mContext, mainModels);
        recyclerView.setAdapter(mainAdaper);

        shopImageArray = shopInfo.image.split(",");
        shopNameArray = shopInfo.name.split(",,,");
        shopInfoUrlArray = shopInfo.infoUrl.split(",");
        piModel = new ArrayList<>();
        ArrayList<PIModel> pi,op = null;
        pi = new ArrayList<>();
        op = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            PIModel model = new PIModel(shopImageArray[i], shopNameArray[i], shopInfoUrlArray[i]);
            piModel.add(model);
            if(i>5&&i<10){
                pi.add(model);
            }else if(i>11&&i<16){
                op.add(model);
            }
        }

        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rvPayAgain.setLayoutManager(linearLayoutManager);
        rvPayAgain.setItemAnimator(new DefaultItemAnimator());
        productInformationAdaper = new ProductInformationAdaper(mContext, pi,R.layout.list_item_template);
        rvPayAgain.setAdapter(productInformationAdaper);

        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rvRecommend.setLayoutManager(linearLayoutManager);
        rvRecommend.setItemAnimator(new DefaultItemAnimator());
        productInformationAdaper = new ProductInformationAdaper(mContext, op,R.layout.list_item_template);
        rvRecommend.setAdapter(productInformationAdaper);

        rvallRestaurant.setLayoutManager(manager);
        rvallRestaurant.setItemAnimator(new DefaultItemAnimator());
        productInformationAdaper = new ProductInformationAdaper(mContext, piModel,R.layout.list_item_template2);
        rvallRestaurant.setAdapter(productInformationAdaper);
    }

    public static mainFragment newInstance() {
        return new mainFragment();
    }
}