package com.example.foodpanda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class EmptyActivity extends AppCompatActivity {
    private ImageView imvClose;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        init();
        initListener();
        initData();

    }

    private void init(){
        imvClose = (ImageView)findViewById(R.id.imv_back_white);
        btnBack = (Button) findViewById(R.id.btn_back);

    }

    private void initListener(){
        imvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
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
        //setResult(RESULT_OK);
        finish();
    }
}
