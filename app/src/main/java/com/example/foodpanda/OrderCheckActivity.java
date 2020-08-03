package com.example.foodpanda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.foodpanda.Adapter.EditOrderAdaper;
import com.example.foodpanda.Model.EditModel;
import com.example.foodpanda.util.DialogUtility;
import com.example.foodpanda.util.SystemUtility;

import java.util.ArrayList;

public class OrderCheckActivity extends AppCompatActivity implements EditOrderAdaper.EditMethod {
    private TextView tvShopName, tvCheckTableWare, tvNum, tvPrice, tvDiscount, tvTotal, tvDelivery, tvDiscountName, tvTotalOrder;
    private Switch swCheckTableWare;
    private ImageView imvBack, imvInfo, imvBackWhite, imvInfoWhite, imvHeader ;
    private RecyclerView rvEditOrder;
    private LinearLayout llSend;
    private Context context;
    private String shopName, voucherName;
    int voucher = 20,  plasticBag = 2;
    private ArrayList<EditModel> editModel;
    private EditOrderAdaper.EditMethod editMethod;
    private static final  int REQUEST_CODE_DISCOUNT = 105 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_check);
        context = OrderCheckActivity.this;
        editMethod = OrderCheckActivity.this;
        shopName = getIntent().getExtras().getString("shopName","迷克夏");
        editModel = (ArrayList<EditModel>) getIntent().getExtras().getSerializable("MenuModel");

        init();
        initListener();
        initData();
    }

    private void init(){
        imvBackWhite = (ImageView) findViewById(R.id.imv_back_white);
        tvShopName = (TextView) findViewById(R.id.tv_shopName);
        tvCheckTableWare = (TextView)findViewById(R.id.tv_check_tableware);
        swCheckTableWare = (Switch)findViewById(R.id.sw_check_tableware);

        rvEditOrder = (RecyclerView)findViewById(R.id.rv_edit_order);
        llSend = (LinearLayout) findViewById(R.id.ll_send);
        tvNum = (TextView)findViewById(R.id.tv_num);
        tvPrice = (TextView)findViewById(R.id.tv_price);

        tvTotal = (TextView)findViewById(R.id.tv_total_price);
        tvDiscount = (TextView)findViewById(R.id.tv_discount);
        tvDiscountName = (TextView)findViewById(R.id.tv_discount_name);
        tvDelivery = (TextView)findViewById(R.id.tv_delivery);
        tvTotalOrder = (TextView)findViewById(R.id.tv_Total_order);
    }

    private void initListener(){
        swCheckTableWare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tvCheckTableWare.setText(isChecked?"地球乾我闢室，7414啦。":"免洗餐具和吸管都不需要，謝謝你和我們一起減塑，實踐環保愛地球。");
            }
        });

        imvBackWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        llSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String leftText = "取消";
                String rightText = "確定";
                DialogInterface.OnClickListener rightListener = new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int whichcountry){
                        SystemUtility.startActivity(context, MainActivity.class, null);
                    }
                };
                DialogUtility.showMessage(context, "提示", "確定要送出訂單？", false, leftText, rightText, null, rightListener);

            }
        });

        tvDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvDiscount.getText().toString().equals(" (刪除) ")){
                    voucher = 20;
                    tvDiscountName.setVisibility(View.GONE);
                    tvDelivery.setText(" $ "+ voucher);
                    tvDiscount.setText("有優惠碼嗎?");

                    int price = 0, num = 0;
                    for(EditModel menuModel:editModel){
                        price += menuModel.itemTotalPrice;
                        num += menuModel.itemNum;
                    }

                    tvNum.setText(num+"");
                    tvTotal.setText(" $ " + price);

                    price += voucher + plasticBag;
                    tvPrice.setText(" $ " + price);
                    tvTotalOrder.setText(" $ " + price);
                }else{
                    SystemUtility.startActivity(context, DiscountActivity.class, null, REQUEST_CODE_DISCOUNT);
                }
            }
        });
    }

    private void initData(){
        int price = 0, num = 0;
        for(EditModel menuModel:editModel){
            price += menuModel.itemTotalPrice;
            num += menuModel.itemNum;
        }
        tvShopName.setText(shopName);
        tvNum.setText(num+"");
        tvTotal.setText(" $ " + price);

        price += voucher + plasticBag;
        tvPrice.setText(" $ " + price);
        tvTotalOrder.setText(" $ " + price);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvEditOrder.setLayoutManager(manager);
        EditOrderAdaper menuAdaper = new EditOrderAdaper(context, editModel, editMethod);
        rvEditOrder.setAdapter(menuAdaper);
    }

    @Override
    public void Count() {
        int price = 0, num = 0;
        for(EditModel menuModel:editModel){
            price += menuModel.itemTotalPrice;
            num += menuModel.itemNum;
        }
        tvNum.setText(num+"");
        tvTotal.setText(" $ " + price);

        price += voucher + plasticBag;
        tvPrice.setText(" $ " + price);
        tvTotalOrder.setText(" $ " + price);
        AnimatorSet mSet = new AnimatorSet();
        ObjectAnimator anim = ObjectAnimator.ofFloat(tvNum, "scaleY", 1f, 1.5f, 1f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(tvNum, "scaleX", 1f, 1.5f, 1f);
        mSet.playTogether(anim, anim2);
        mSet.setDuration(500);
        mSet.start();

        if(num == 0){
            goBack();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//捕捉返回鍵
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goBack(){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("MenuModel", editModel);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_DISCOUNT:
                if (resultCode == RESULT_OK) {
                    voucherName = data.getStringExtra("voucherName");
                    voucher = data.getIntExtra("voucher",20);

                    tvDiscountName.setVisibility(View.VISIBLE);
                    tvDiscountName.setText(voucherName);
                    tvDiscount.setText(" (刪除) ");
                    tvDelivery.setText(" $ "+ voucher);

                    int price = 0, num = 0;
                    for(EditModel menuModel:editModel){
                        price += menuModel.itemTotalPrice;
                        num += menuModel.itemNum;
                    }
                    tvNum.setText(num+"");
                    tvTotal.setText(" $ " + price);

                    price += voucher + plasticBag;
                    tvPrice.setText(" $ " + price);
                    tvTotalOrder.setText(" $ " + price);
                }
                break;
        }
    }
}
