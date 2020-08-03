package com.example.foodpanda;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class WelcomeActivity extends Activity {
	
	private final String TAG = WelcomeActivity.class.getSimpleName();
	private CountDownTimer countDownTimer;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);//Theme_Holo_Light
//		final AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeActivity.this,AlertDialog.THEME_HOLO_LIGHT);
//		String message = "SSL Certificate error.";
//
//		message += " Do you want to continue anyway?";
//
//		builder.setTitle("SSL Certificate Error");
//		builder.setMessage(message);
//		builder.setIcon(R.drawable.ic_test_no);
//
//		builder.setPositiveButton("continue", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//			}
//		});
//		builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//			}
//		});
//		final AlertDialog dialog = builder.create();
//		dialog.show();

		final ImageView imvIcon = findViewById(R.id.imv_icon);
		final AnimatorSet mSet = new AnimatorSet();
		final ObjectAnimator anim = ObjectAnimator.ofFloat(imvIcon, "rotation", 0, -45, 0);
		mSet.playTogether(anim);
		mSet.addListener(new AnimatorListenerAdapter() {
							 @Override
							 public void onAnimationEnd(Animator animation) {
								 ObjectAnimator anim2 = ObjectAnimator.ofFloat(imvIcon, "rotation", 0, 45, 0);
								 anim2.setDuration(1000);
								 anim2.start();
							 }
						 });
		mSet.setDuration(1000);
		mSet.start();
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
