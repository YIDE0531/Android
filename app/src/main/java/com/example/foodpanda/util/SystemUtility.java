package com.example.foodpanda.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.foodpanda.R;

public class SystemUtility {

	private static String TAG = SystemUtility.class.getSimpleName();
	
	private static ProgressDialog progressDialog;

	/*** start acitvity: 指定 requestCode */
	public static void startActivity(Context context, Class<?> activityClass, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(context, activityClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		((Activity) context).startActivityForResult(intent, requestCode);
	}

	public static void startActivity(Context context, Class<?> targetClass, Bundle bundle) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setClass(context, targetClass);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		((Activity) context).startActivity(intent);
	}

	public static void startActivity(Context context, String uristr) {
		Intent intent = new Intent();
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		Uri uri= Uri.parse(uristr);
		Intent i=new Intent(Intent.ACTION_VIEW,uri);
		((Activity) context).startActivity(i);
	}
	
	public static void closeApp(Context context){
		Activity activity = (Activity) context;
		activity.finish();
		if(getAndroidSDK()>=16){
			activity.finishAffinity();
		}		
	}

	/*** 檢查網路是否連線 */
	public static boolean checkNetworkConnected(Context context) {
		NetworkInfo info = (NetworkInfo) ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();

		if (info == null) {
			return false;
		} else {
			if (!info.isConnected()) {
				return false;
			}
			if (info.isRoaming()) {
				return false;
			}
		}
		return true;
	}

	/***
	 * 取得 Android SDK code
	 * 
	 * @return int: 例如，Android 4.0.3 環境，回傳 15，若取不到 android api，回傳 0
	 */
	public static int getAndroidSDK() {
		int sdk = 0;
		try {
			sdk = Build.VERSION.SDK_INT;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sdk;
	}

	/***
	 * 取得螢幕解析度: density
	 */
	public static float getScreenDensity(Context context) {
		float density = context.getResources().getDisplayMetrics().density;
//		LogUtility.v(TAG, "density:" + density);
		return density;
	}

	/*** 依 dimen 取得 dp 或 sp */
	public static float getDensitySize(Context context, int dimenRourceID) {
		float pixels = context.getResources().getDimensionPixelSize(dimenRourceID);		
		float size = pixels / getScreenDensity(context);		
		return size;
	}	
	
	public static int getPixelSize(Context context, int size){
		int pixels = (int) (size * getScreenDensity(context));
		return pixels;
	 }
}
