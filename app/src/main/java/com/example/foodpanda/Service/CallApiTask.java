package com.example.foodpanda.Service;

import android.content.Context;
import android.os.AsyncTask;

import com.example.foodpanda.Model.AllModel;
import com.example.foodpanda.config.AppConfig;
import com.example.foodpanda.views.ProgressDialogUtil;

import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class CallApiTask extends AsyncTask<String, Void, String> {     //2020/4/8 嘗試使用AsyncTask去call api
    private Context mContext;
    private ProgressDialogUtil progressDialogUtil;
    private apiCallBack apiCallBack;
    private JsonAnalysis jsonAnalysis;
    private String phpName;
    private HashMap<String, String> paramsString;

    public CallApiTask(Context context, ProgressDialogUtil progressDialogUtil, apiCallBack apiCallBack, String apiName, HashMap<String, String> paramsString){
        this.mContext = context;
        this.progressDialogUtil = progressDialogUtil;
        this.apiCallBack = apiCallBack;
        this.phpName = apiName;
        this.jsonAnalysis = new JsonAnalysis();
        this.paramsString = paramsString;

    }

    protected String doInBackground(String... urls) {
        String response = null;
        try {
            /* Open a connection to that URL. */
            URL url = new URL(urls[0]);
            final HttpsURLConnection aHttpURLConnection = (HttpsURLConnection)url.openConnection();
            aHttpURLConnection.setDoInput(true);
            aHttpURLConnection.setConnectTimeout(5000);//連線逾時
            aHttpURLConnection.setReadTimeout(10000);//資訊傳遞逾時

            if(!AppConfig.CHECK_SSL_CERT) {
                aHttpURLConnection.setHostnameVerifier(SslHelper.getTrustAllVerifier());
                aHttpURLConnection.setSSLSocketFactory(SslHelper.getTrustAllSSLSocketFactory());
            }else{
                aHttpURLConnection.setSSLSocketFactory(new StrictSSLSocketFactory(mContext));
            }

            /*** query string */
            if(paramsString == null){
                aHttpURLConnection.setRequestMethod("GET");
            }else{
                aHttpURLConnection.setRequestMethod("POST");
                OutputStream wr = aHttpURLConnection.getOutputStream();
                wr.write(getQueryString(paramsString).getBytes("UTF-8"));
                wr.flush();
                wr.close();
            }

            if (aHttpURLConnection.getResponseCode() == 200) {
                /* Define InputStreams to read from the URLConnection. */
                InputStream aInputStream = aHttpURLConnection.getInputStream();
                BufferedInputStream aBufferedInputStream = new BufferedInputStream(
                        aInputStream);
                /* Read bytes to the Buffer until there is nothing more to read(-1) */
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                byte[] buffer = new byte[1024];
                int length;
                StringBuilder sb = new StringBuilder();
                if(aBufferedInputStream != null) {//fortify
                    while ((length = aBufferedInputStream.read(buffer)) != -1) {
                        baos.write(buffer, 0, length);
                    }
                    response = sb.append(baos).toString();
                    baos.close();
                }
                /* Convert the Bytes read to a String. */
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    protected void onPostExecute(String jsonMsg) {
        progressDialogUtil.dismiss();
        try {
            if(jsonMsg==null){
                apiCallBack.result("網路有問題哦");
            }else{
                apiCallBack.result(jsonAnalysis.parser(phpName,jsonMsg));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface apiCallBack {
        void result(AllModel s);
        void result(String s);
    }

    public String getQueryString(HashMap<String, String> params){
        String queryString = "";
        int i = params.size();
        for (String key:params.keySet()) {
            if(i<params.size()-1) {
                queryString += key + "=" + params.get(key) + "&";
            }else{
                queryString += key + "=" + params.get(key) ;
            }
            i++;
        }
        return queryString;
    }

}