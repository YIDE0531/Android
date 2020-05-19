package com.example.foodpanda;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;

import com.example.foodpanda.Adapter.MainAdaper;
import com.example.foodpanda.Model.AllModel;
import com.example.foodpanda.Model.MainModel;
import com.example.foodpanda.Model.PIModel;
import com.example.foodpanda.Adapter.ProductInformationAdaper;
import com.example.foodpanda.Service.CallApiTask;
import com.example.foodpanda.Service.JsonAnalysis;
import com.example.foodpanda.config.AppConfig;
import com.example.foodpanda.views.ProgressDialogUtil;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, CallApiTask.apiCallBack {
    private RecyclerView recyclerView, rvPayAgain, rvRecommend, rvallRestaurant;
    private ArrayList<MainModel> mainModels;
    private ArrayList<PIModel> piModel;
    private MainAdaper mainAdaper;

    private ProductInformationAdaper productInformationAdaper;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private Context mContext;
    private ProgressDialogUtil progressDialogUtil;
    private CallApiTask.apiCallBack apiCallBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;
        progressDialogUtil = new ProgressDialogUtil();
        apiCallBack = MainActivity.this;


        recyclerView = findViewById(R.id.recycler_view);
        Integer[] langlogo = {R.drawable.freead, R.drawable.discount, R.drawable.freead, R.drawable.discount, R.drawable.freead};
        mainModels = new ArrayList<>();
        for (int i = 0; i < langlogo.length; i++) {
            MainModel model = new MainModel(langlogo[i]);
            mainModels.add(model);
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mainAdaper = new MainAdaper(MainActivity.this, mainModels);
        recyclerView.setAdapter(mainAdaper);

        //1233444
        rvPayAgain = findViewById(R.id.recycler_payAgain);
        Integer[] langlogo2 = {R.drawable.shop, R.drawable.kfc, R.drawable.shop, R.drawable.kfc, R.drawable.shop};
        piModel = new ArrayList<>();
        for (int i = 0; i < langlogo2.length; i++) {
            PIModel model = new PIModel(langlogo2[i]);
            piModel.add(model);
        }

        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvPayAgain.setLayoutManager(linearLayoutManager);
        rvPayAgain.setItemAnimator(new DefaultItemAnimator());

        productInformationAdaper = new ProductInformationAdaper(MainActivity.this, piModel,R.layout.list_item_template);
        rvPayAgain.setAdapter(productInformationAdaper);

        rvRecommend = findViewById(R.id.recycler_recommend);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        rvRecommend.setLayoutManager(linearLayoutManager);
        rvRecommend.setItemAnimator(new DefaultItemAnimator());
        productInformationAdaper = new ProductInformationAdaper(MainActivity.this, piModel,R.layout.list_item_template);
        rvRecommend.setAdapter(productInformationAdaper);


        init();
        initData();
        initListener();

        progressDialogUtil.showProgressDialog(mContext);
        new CallApiTask(mContext, progressDialogUtil, apiCallBack).execute(AppConfig.getUrlPath() + "getData.php");
    }

    void init() {
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nav_view);

    }

    void initData() {
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        SpannableString text = new SpannableString("送餐到：欲送達地址");
        text.setSpan(new ForegroundColorSpan(Color.parseColor("#D60665")), 4, 9, 0);
        getSupportActionBar().setTitle(text);
    }

    void initListener(){
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void result(AllModel shopName) {
        String[] shopNameArray, shopImageArray ;
        shopImageArray = shopName.image.split(",");
        shopNameArray = shopName.name.split(",");
        piModel = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            PIModel model = new PIModel(shopImageArray[i], shopNameArray[i]);
            piModel.add(model);
        }
        rvallRestaurant = findViewById(R.id.recycler_allRestaurant);
        rvallRestaurant.setItemAnimator(new DefaultItemAnimator());
        productInformationAdaper = new ProductInformationAdaper(MainActivity.this, piModel,R.layout.list_item_template2);
        rvallRestaurant.setAdapter(productInformationAdaper);
    }

}
