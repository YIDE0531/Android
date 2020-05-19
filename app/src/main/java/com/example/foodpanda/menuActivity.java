package com.example.foodpanda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodpanda.Adapter.menuAdaper;
import com.example.foodpanda.Model.MenuModel;
import com.example.foodpanda.util.DialogUtility;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class menuActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private TabLayout toolbar_tab;
    private String[] title = {"※注意事項","季節限定","愛茶如牛","牧場鮮奶茶","綠光牧場鮮奶","手作特調"};
    private RecyclerView rvShow;
    private CollapsingToolbarLayout ctlScroll;
    private boolean isScrolled = false;
    private LinearLayoutManager manager;
    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.6f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleContainerVisible = true;
    private TextView tvTitle;
    private ImageView imvBack, imvInfo, imvBackWhite, imvInfoWhite ;
    private ConstraintLayout consTitleShrink, consMinute, consTime;
    private ArrayList dataList;
    private Context mContext;

    /**
     * 需要定位的地方，从小到大排列，需要和tab对应起来，长度一样
     */
    private int[] str1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mContext = menuActivity.this;

        initView();
        initData();
        initListener();
    }

    void initView(){
        toolbar_tab = (TabLayout)findViewById(R.id.toolbar_tab);
        rvShow = (RecyclerView)findViewById(R.id.recycler_menu_item);
        ctlScroll = (CollapsingToolbarLayout)findViewById(R.id.collapsing_tool_bar_test_ctl);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        imvBack = (ImageView) findViewById(R.id.imv_back);
        imvInfo = (ImageView) findViewById(R.id.imv_info);
        imvBack = (ImageView) findViewById(R.id.imv_back);
        imvBackWhite = (ImageView) findViewById(R.id.imv_back_white);
        imvInfoWhite = (ImageView) findViewById(R.id.imv_info_white);
        consTitleShrink = (ConstraintLayout) findViewById(R.id.cons_title_shrink);
        consMinute = (ConstraintLayout) findViewById(R.id.cons_minute);
        consTime = (ConstraintLayout) findViewById(R.id.cons_time);

        //msvMain = (MenuScrollView)findViewById(R.id.msv_main);

    }

    void initData() {
        toolbar_tab.removeAllTabs();
        for(int i=0;i<title.length;i++){
            TabLayout.Tab tab1 = toolbar_tab.newTab().setText(title[i]);
            toolbar_tab.addTab(tab1);
        }
        toolbar_tab.setTabMode(TabLayout.MODE_SCROLLABLE);

        dataList = new ArrayList<>();
        String[] title = {
                "※注意事項",
                "季節限定",
                "",
                "愛茶如牛",
                "","","","","","","","",
                "牧場鮮奶茶",
                "","","","","","","","","","","","","","","","","","","","","",
                "綠光牧場鮮奶",
                "","","","","","","","","","",
                "手作特調",
                "","","","","","","",""
        };

        int[] str2 = {0, 1, 3, 12, 34, 45};
        str1 = str2;
        
        String[] itemName = {
                "※為響應環保減塑，本店不主動提供塑膠袋。如需....",
                "焙茶鮮奶L",
                "焙茶鮮奶M",
                "大正紅茶L",
                "英倫伯爵紅茶L","初露青茶L","決明大麥M","英倫伯爵紅茶L","初露青茶L","決明大麥M","英倫伯爵紅茶L","初露青茶L",
                "珍珠大正紅茶拿鐵L",
                "珍珠伯爵紅茶拿鐵L","大正紅茶拿鐵L","紅茶大正紅茶拿鐵M","高峰烏龍拿鐵L","珍珠伯爵紅茶拿鐵L","大正紅茶拿鐵L","紅茶大正紅茶拿鐵M","高峰烏龍拿鐵L","珍珠伯爵紅茶拿鐵L","大正紅茶拿鐵L","紅茶大正紅茶拿鐵M","高峰烏龍拿鐵L","珍珠伯爵紅茶拿鐵L","大正紅茶拿鐵L","紅茶大正紅茶拿鐵M","高峰烏龍拿鐵L","珍珠伯爵紅茶拿鐵L","大正紅茶拿鐵L","紅茶大正紅茶拿鐵M","高峰烏龍拿鐵L","大正紅茶拿鐵L",
                "珍珠鮮奶L",
                "初雲抹茶拿鐵L","手炒黑糖鮮奶M","布丁鮮奶L","初雲抹茶拿鐵L","手炒黑糖鮮奶M","布丁鮮奶L","初雲抹茶拿鐵L","手炒黑糖鮮奶M","初雲抹茶拿鐵L","手炒黑糖鮮奶M",
                "大正紅茶鮮豆奶L",
                "伯爵紅茶鮮豆奶L","柳丁綠茶L","香柚綠茶M","養樂多綠L","伯爵紅茶鮮豆奶L","柳丁綠茶L","香柚綠茶M","養樂多綠L"
        };

        String[] itemPricce = {
                "$ 1",
                "$ 85 起",
                "$ 65 起",
                "$ 35 起",
                "$ 40 起","$ 35 起","$ 35 起","$ 30 起","$ 40 起","$ 35 起","$ 35 起","$ 30 起",
                "$ 70 起",
                "$ 40 起","$ 35 起","$ 35 起","$ 30 起","$ 40 起","$ 40 起","$ 35 起","$ 35 起","$ 30 起","$ 40 起","$ 40 起","$ 35 起","$ 35 起","$ 30 起","$ 40 起","$ 40 起","$ 35 起","$ 35 起","$ 30 起","$ 40 起","$ 30 起",
                "$ 90 起",
                "$ 95 起","$ 105 起","$ 95 起","$ 105 起","$ 95 起","$ 105 起","$ 95 起","$ 105 起","$ 95 起","$ 105 起",
                "$ 60 起",
                "$ 65 起","$ 60 起","$ 55 起","$ 45 起","$ 30 起","$ 65 起","$ 60 起","$ 60 起"
        };

        int[] itemPicture = {
                0,
                0,
                R.drawable.item,
                R.drawable.item,
                0,0,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,0,R.drawable.item,
                R.drawable.item,
                R.drawable.item,R.drawable.item,0,0,0,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item,0,
                0,
                R.drawable.item,R.drawable.item,R.drawable.item,0,0,0,0,R.drawable.item,0,0,
                R.drawable.item,
                0,0,0,0,R.drawable.item,R.drawable.item,R.drawable.item,R.drawable.item
        };

        for (int i = 0; i < title.length; i++) {
            MenuModel model = new MenuModel(title[i], itemName[i], itemPricce[i], true, itemPicture[i]);
            dataList.add(model);
        }
        
        manager = new LinearLayoutManager(this);
        rvShow.setLayoutManager(manager);
        menuAdaper menuAdaper = new menuAdaper(menuActivity.this, dataList,R.layout.layout_menu_item);
        rvShow.setAdapter(menuAdaper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        /*setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.back);
        ctlScroll.setTitle("  美食 15 分鐘 ");
        ctlScroll.setCollapsedTitleTextColor(Color.WHITE);
        ctlScroll.setExpandedTitleColor(Color.WHITE);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.id_appbarlayout);
        mAppBarLayout.addOnOffsetChangedListener(menuActivity.this);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        /*setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });*/
        //toolbar.inflateMenu(R.menu.main);
        startAlphaAnimation(consTitleShrink, 0, View.INVISIBLE);
    }

    void initListener(){
        consTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtility.showDialog(mContext);
            }
        });

        consMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtility.showDialog(mContext);
            }
        });

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imvBackWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                //if (!isScrolled) {
                    //滑动时不能点击,
                    //第一个参数是指定的位置，锚点
                    // 第二个参数表示 Item 移动到第一项后跟 RecyclerView 上边界或下边界之间的距离（默认是 0）
                    manager.scrollToPositionWithOffset(str1[pos], 0);
                //}
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        rvShow.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                    int pos = 0;
                    if (bottom == dataList.size() - 1) {
                        //先判断滑到底部，tab定位到最后一个
                        pos = str1.length - 1;
                    } else if (top == str1[str1.length - 1]) {
                        //如果top等于指定的位置，对应到tab即可，
                        pos = str1[str1.length - 1];
                    } else {
                        //循环遍历，需要比较i+1的位置，所以循环长度要减1，
                        //  如果 i<top<i+1,  那么tab应该定位到i位置的字符，不管是向上还是向下滑动
                        for (int i = 0; i < str1.length - 1; i++) {
                            if (top == str1[i]) {
                                pos = i;
                                break;
                            } else if (top > str1[i] && top < str1[i + 1]) {
                                pos = i;
                                break;
                            }
                        }
                    }

                    //设置tab滑动到第pos个
                    toolbar_tab.setScrollPosition(pos, 0f, true);
                }

            }
        });

    }

    public static void startAlphaAnimation (View cons, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE) ? new AlphaAnimation(0f, 1f) : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        cons.startAnimation(alphaAnimation);
    }
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        handleAlphaOnTitle(percentage);
        handleToolbarTitleVisibility(percentage);
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if(!mIsTheTitleVisible) {
                imvBackWhite.setVisibility(View.INVISIBLE);
                imvInfoWhite.setVisibility(View.INVISIBLE);
                startAlphaAnimation(consTitleShrink, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;

            }
        } else {
            if (mIsTheTitleVisible) {
                imvBackWhite.setVisibility(View.VISIBLE);
                imvInfoWhite.setVisibility(View.VISIBLE);
                startAlphaAnimation(consTitleShrink, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleContainerVisible) {
                mIsTheTitleContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleContainerVisible) {
                mIsTheTitleContainerVisible = true;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onPause", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_LONG).show();
    }
}
