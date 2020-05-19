package com.example.foodpanda.config;


import android.util.Log;

public class AppConfig {
	public static final Target target = Target.DEV;

	/***  檢查 ssl 憑證  */
	public static boolean CHECK_SSL_CERT = AppConfig.target== AppConfig.Target.PROD ? true:false;//啟用 SSL 憑證檢查
	public static final String CERT_FILE_NAME = "2019APP.YUANTALIFE.COM.TW.crt";//憑證檔名

	/*** 系統環境 */
	public enum Target {
		DEV, PROD
	}
	
	/*** 服務 URL */
    private static final String
	    	FILE_PATH_DEV = "https://172.16.2.114:5210/",
			FILE_PATH_PROD ="https://172.16.2.114:5210/";
			

    public static String getUrlPath(){
    	String path =  "";
    	switch (target) {
			case DEV:
				path = FILE_PATH_DEV;
				break;
			case PROD:

			default:
				path = FILE_PATH_PROD;
				break;
		}    	
    	return path;
    }
	
}
