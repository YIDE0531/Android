package com.example.foodpanda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class DiscountActivity extends AppCompatActivity {
    private ImageView imvBack;
    private EditText edtVoucher;
    private Button btnUse;
    private String voucherName;
    private int voucher = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        initView();
        initListener();
        initData();
    }

    private void initView(){
        imvBack = (ImageView)findViewById(R.id.imv_back_white);

        edtVoucher = (EditText)findViewById(R.id.edt_voucher);
        btnUse = (Button) findViewById(R.id.btn_use);

    }

    private void initListener(){
        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voucherName = edtVoucher.getText().toString();
                if(voucherName.equals("7414")){
                    goBackSuccess();
                }else{
                    Toast.makeText(DiscountActivity.this, "查無優惠碼", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initData(){

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
        setResult(RESULT_CANCELED);
        finish();
    }

    private void goBackSuccess(){
        Intent intent = new Intent();
        intent.putExtra("voucherName", voucherName);
        intent.putExtra("voucher", voucher);
        setResult(RESULT_OK, intent);
        finish();
    }
}
