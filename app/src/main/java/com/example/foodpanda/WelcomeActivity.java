package com.example.foodpanda;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

public class WelcomeActivity extends Activity {
	
	private final String TAG = WelcomeActivity.class.getSimpleName();
	private CountDownTimer countDownTimer;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		countDownTimer = new CountDownTimer(2000,1000){            
            @Override
            public void onFinish() {
            	startActivity(MainActivity.class);
            }

            @Override
            public void onTick(long millisUntilFinished) {               
            }            
        }.start();
	}

	private void startActivity(Class<?> activityClass){
		Intent intent = new Intent();
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);        		
		intent.setClass(WelcomeActivity.this, activityClass); 
		startActivity(intent);
		finish();
	}
	
	@Override
    public void onStop() {
        super.onStop();       
        if(countDownTimer != null){
        	countDownTimer.cancel();
        }        
    }
	
	@Override
    public void onResume() {
        super.onResume();       
        if(countDownTimer != null){
        	countDownTimer.start();
        }

    }
	
}
