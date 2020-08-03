package com.example.foodpanda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.foodpanda.Adapter.AboutAdapter;
import com.example.foodpanda.Adapter.PagerAdapter;
import com.example.foodpanda.Service.CallApiTask;
import com.example.foodpanda.config.AppConfig;
import com.example.foodpanda.util.SystemUtility;
import com.example.foodpanda.views.ProgressDialogUtil;
import com.google.android.material.tabs.TabLayout;

public class AboutActivity extends AppCompatActivity {
    private ImageView imvBackWhite, imvShop;
    private TextView tvShopName;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext;
    private String address, date, comment, shopName, shopImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mContext = AboutActivity.this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            shopName = bundle.getString("shopName");
            shopImage = bundle.getString("shopImage");
            address = bundle.getString("address");
            date = bundle.getString("date");
            comment = bundle.getString("comment");
        }
        initView();
        initData();
        initListener();
    }

    private void initView(){
        imvBackWhite = (ImageView) findViewById(R.id.imv_back_white);
        tabLayout = (TabLayout) findViewById(R.id.toolbar_tab);
        viewPager = (ViewPager) findViewById(R.id.pager);
        imvShop = (ImageView) findViewById(R.id.imv_header);
        tvShopName = (TextView) findViewById(R.id.tv_shop_name);
    }

    private void initData(){
        //Creating our pager adapter
        AboutAdapter adapter = new AboutAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        Glide.with(mContext).load(shopImage)
                .thumbnail(Glide.with(mContext).load(R.drawable.loading))
                .into(imvShop);
        tvShopName.setText(shopName);
    }

    private void initListener(){
        imvBackWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {

                } else if (tab.getPosition() == 2) {

                } else {

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //宿主activity中的getTitles()方法
    public String[] getIntentParams(){
        String[] fofo = new String[3];
        fofo[0] = address;
        fofo[1] = date;
        fofo[2] = shopName;
        return fofo;
    }

    //宿主activity中的getTitles()方法
    public String getCommentParams(){
        return comment;
    }
}
