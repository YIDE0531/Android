package com.example.foodpanda.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.foodpanda.MainActivity;
import com.example.foodpanda.R;

public class DialogUtility {
	
	public static AlertDialog.Builder getBuilder(Context context, boolean alignCenter){
		AlertDialog.Builder builder = null;

		if(alignCenter){
			builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.alertdialog_dark_center));
		}else{
			builder = new AlertDialog.Builder(context);
		}
		return builder;
	}
	
	public static void showMessage(Context context, String title, String message) {
		AlertDialog.Builder builder = getBuilder(context, false);
		builder.setTitle(title);
		builder.setMessage(message);
		String lable = context.getResources().getString(R.string.action_leave);
		builder.setPositiveButton(lable, null);
		AlertDialog alert = builder.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				positiveButton.setLayoutParams(params);
				positiveButton.invalidate();
			}
		});
		alert.show();
	}
	
	public static void showMessage(Context context, String title, String message, boolean alignCenter, DialogInterface.OnClickListener listener) {

		AlertDialog.Builder builder = getBuilder(context, alignCenter);
		builder.setTitle(title);
		builder.setMessage(message);
		String lable = context.getResources().getString(R.string.action_leave);
		builder.setPositiveButton(lable, listener);
		AlertDialog alert = builder.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				positiveButton.setLayoutParams(params);
				positiveButton.invalidate();
			}
		});
		alert.show();
	}
	
	public static void showMessage(Context context, String title, String message, boolean alignCenter, String positiveLable, String negativeLable, DialogInterface.OnClickListener positiveListener, DialogInterface.OnClickListener negativeListener) {

		AlertDialog.Builder builder = getBuilder(context, alignCenter);
		builder.setTitle(title);
		builder.setMessage(message);			
		builder.setPositiveButton(positiveLable, positiveListener);
		builder.setNegativeButton(negativeLable, negativeListener);
		builder.setCancelable(false);
		AlertDialog alert = builder.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button negativeButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEGATIVE);
				Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				negativeButton.setLayoutParams(params);
				positiveButton.setLayoutParams(params);
				negativeButton.invalidate();
				positiveButton.invalidate();;
			}
		});
		alert.show();
	}
	
	public static void showMessage(Context context, String title, String message, boolean alignCenter) {

		AlertDialog.Builder builder = getBuilder(context, alignCenter);
		builder.setTitle(title);
		builder.setMessage(message);
		String lable = context.getResources().getString(R.string.action_leave);
		builder.setPositiveButton(lable, null);
		AlertDialog alert = builder.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				positiveButton.setLayoutParams(params);
				positiveButton.invalidate();
			}
		});
		alert.show();
	}
	
	public static void showMessage(Context context, String title, String message, String actionText) {
		AlertDialog.Builder builder = getBuilder(context, false);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(actionText, null);
		AlertDialog alert = builder.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				positiveButton.setLayoutParams(params);
				positiveButton.invalidate();
			}
		});
		alert.show();
	}
	
	public static void showDialog(final Context context, final Class<?> activityClass, String message){
		AlertDialog.Builder builder = getBuilder(context, false);
		builder.setTitle("");
		builder.setMessage(message);
		builder.setPositiveButton(context.getResources().getString(R.string.action_confirm), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichcountry){
				//SystemUtility.startActivity(context, activityClass, null);
			}
		});
		AlertDialog alert = builder.create();
		alert.setOnShowListener(new DialogInterface.OnShowListener() {
			@Override
			public void onShow(DialogInterface dialog) {
				Button positiveButton = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1);
				positiveButton.setLayoutParams(params);
				positiveButton.invalidate();
			}
		});
		alert.show();
	}

	public static void showDialog(final Context context){
		LayoutInflater inflater = LayoutInflater.from(context);
		final View v = inflater.inflate(R.layout.layout_meal_delivery_time, null);
		AlertDialog.Builder builder = getBuilder(context, false);
		builder.setView(v);
		Spinner day = (Spinner)v.findViewById(R.id.spinner_day);
		Spinner time = (Spinner)v.findViewById(R.id.spinner_time);

		ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
				context, R.array.day, android.R.layout.simple_spinner_item );
		nAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


		ArrayAdapter<CharSequence> nAdapter2 = ArrayAdapter.createFromResource(
				context, R.array.time, android.R.layout.simple_spinner_item );
		nAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		day.setAdapter(nAdapter);
		time.setAdapter(nAdapter2);

		builder.setNegativeButton(context.getResources().getString(R.string.action_cancel), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichcountry){
				//SystemUtility.startActivity(context, activityClass, null);
			}
		});
		builder.setPositiveButton(context.getResources().getString(R.string.action_confirm), new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int whichcountry){
				//SystemUtility.startActivity(context, activityClass, null);
			}
		}).show();
	}
}
