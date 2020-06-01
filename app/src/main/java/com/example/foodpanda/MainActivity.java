package com.example.foodpanda;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.MenuItem;

import com.example.foodpanda.Adapter.PagerAdapter;
import com.example.foodpanda.Model.AllModel;
import com.example.foodpanda.Service.CallApiTask;
import com.example.foodpanda.config.AppConfig;
import com.example.foodpanda.fragment.mainFragment;
import com.example.foodpanda.util.AnimationUtil;
import com.example.foodpanda.util.MyApplication;
import com.example.foodpanda.util.ViewUtils;
import com.example.foodpanda.views.ProgressDialogUtil;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CallApiTask.apiCallBack {
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Context mContext;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private PopupWindow mPopupWindow;
    private View mGrayLayout;
    private boolean isPopWindowShowing=false;
    int fromYDelta;
    private CallApiTask.apiCallBack apiCallBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        init();
        initListener();
        initData();

    }

    void init() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);
        viewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.toolbar_tab);
        mGrayLayout=findViewById(R.id.gray_layout);
        drawer = findViewById(R.id.drawer_layout);

    }

    void initData() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
            @Override
            public void onDrawerStateChanged(int newState) {
                if(isPopWindowShowing){
                    drawer.closeDrawer(GravityCompat.START);
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(MainActivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    },AnimationUtil.ANIMATION_OUT_TIME);
                }
            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SpannableString text = new SpannableString("送餐到：欲送達地址");
        text.setSpan(new ForegroundColorSpan(Color.parseColor("#D60665")), 4, 9, 0);
        getSupportActionBar().setTitle(text);

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(R.string.food));
        tabLayout.addTab(tabLayout.newTab().setText("外帶自取"));
        tabLayout.addTab(tabLayout.newTab().setText("生鮮雜貨"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //Creating our pager adapter
        PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    void initListener(){
        navigationView.setNavigationItemSelectedListener(this);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(mContext, "Toolbar title clicked", Toast.LENGTH_SHORT).show();
                if(isPopWindowShowing){
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(MainActivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    },AnimationUtil.ANIMATION_OUT_TIME);
                }else{
                    showPopupWindow();
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 1) {

                } else if (tab.getPosition() == 2) {

                } else {
                    ProgressDialogUtil progressDialogUtil = new ProgressDialogUtil();
                    progressDialogUtil.showProgressDialog(mContext);
                    apiCallBack = (CallApiTask.apiCallBack) mContext;
                    new CallApiTask(mContext, progressDialogUtil, apiCallBack, "getData", null).execute(AppConfig.getUrlPath() + "getData");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //对黑色半透明背景做监听，点击时开始退出动画并将popupwindow dismiss掉
        mGrayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPopWindowShowing){
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(MainActivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    },AnimationUtil.ANIMATION_OUT_TIME);
                }
            }
        });

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
//    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        if(isPopWindowShowing){
//            mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(MainActivity.this, fromYDelta));
//            mPopupWindow.getContentView().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mPopupWindow.dismiss();
//                }
//            },AnimationUtil.ANIMATION_OUT_TIME);
//        }
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        if(item.getItemId()==android.R.id.home){
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_order) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {

        } else if (id == R.id.nav_address) {

        } else if (id == R.id.nav_payment) {

        } else if (id == R.id.nav_voucher) {

        } else if (id == R.id.nav_service) {

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void result(AllModel s) {
        Fragment transFragment = MyApplication.fragment;
        ((mainFragment) transFragment).showData(s);
    }

    @Override
    public void result(String s) {
        Toast.makeText(mContext,s,Toast.LENGTH_SHORT).show();
    }

    private void showPopupWindow(){
        final View contentView= LayoutInflater.from(this).inflate(R.layout.selectlist,null);
        LinearLayout t1= (LinearLayout) contentView.findViewById(R.id.ll_item1);
        LinearLayout t2= (LinearLayout) contentView.findViewById(R.id.ll_item2);
        LinearLayout t3= (LinearLayout) contentView.findViewById(R.id.ll_item3);

        final ImageView imvTick1= (ImageView) contentView.findViewById(R.id.it_img_temp);
        final ImageView imvTick2= (ImageView) contentView.findViewById(R.id.it_img_temp2);
        final ImageView imvTick3= (ImageView) contentView.findViewById(R.id.it_img_temp3);

        mPopupWindow=new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        //将这两个属性设置为false，使点击popupwindow外面其他地方不会消失
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setFocusable(false);
        mGrayLayout.setVisibility(View.VISIBLE);
        //获取popupwindow高度确定动画开始位置
        int contentHeight= ViewUtils.getViewMeasuredHeight(contentView);
        mPopupWindow.showAsDropDown(toolbar, 0, 0);
        fromYDelta=-contentHeight-50;
        mPopupWindow.getContentView().startAnimation(AnimationUtil.createInAnimation(this, fromYDelta));

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                isPopWindowShowing=false;
                mGrayLayout.setVisibility(View.GONE);
            }
        });

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imvTick1.setVisibility(View.VISIBLE);
                imvTick2.setVisibility(View.INVISIBLE);
                imvTick3.setVisibility(View.INVISIBLE);

                SpannableString text = new SpannableString("送餐到：現在位置");
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#D60665")), 4, 8, 0);
                getSupportActionBar().setTitle(text);

                if(isPopWindowShowing){
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(MainActivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    },AnimationUtil.ANIMATION_OUT_TIME);
                }
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imvTick1.setVisibility(View.INVISIBLE);
                imvTick2.setVisibility(View.VISIBLE);
                imvTick3.setVisibility(View.INVISIBLE);

                SpannableString text = new SpannableString("送餐到：家");
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#D60665")), 4, 5, 0);
                getSupportActionBar().setTitle(text);

                if(isPopWindowShowing){
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(MainActivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    },AnimationUtil.ANIMATION_OUT_TIME);
                }
            }
        });

        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imvTick1.setVisibility(View.INVISIBLE);
                imvTick2.setVisibility(View.INVISIBLE);
                imvTick3.setVisibility(View.VISIBLE);

                SpannableString text = new SpannableString("送餐到：欲送達地址");
                text.setSpan(new ForegroundColorSpan(Color.parseColor("#D60665")), 4, 9, 0);
                getSupportActionBar().setTitle(text);

                if(isPopWindowShowing){
                    mPopupWindow.getContentView().startAnimation(AnimationUtil.createOutAnimation(MainActivity.this, fromYDelta));
                    mPopupWindow.getContentView().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mPopupWindow.dismiss();
                        }
                    },AnimationUtil.ANIMATION_OUT_TIME);
                }
            }
        });
        isPopWindowShowing=true;
    }
}
