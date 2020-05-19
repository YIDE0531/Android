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
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CallApiTask extends AsyncTask<String, Void, String> {     //2020/4/8 嘗試使用AsyncTask去call api
    private Context mContext;
    private ProgressDialogUtil progressDialogUtil;
    private apiCallBack apiCallBack;
    private JsonAnalysis jsonAnalysis;
    private String phpName;

    public CallApiTask(Context context, ProgressDialogUtil progressDialogUtil, apiCallBack apiCallBack){
        this.mContext = context;
        this.progressDialogUtil = progressDialogUtil;
        this.apiCallBack = apiCallBack;
        this.jsonAnalysis = new JsonAnalysis();

    }

    protected String doInBackground(String... urls) {
        String response = null;
        try {
            /* Open a connection to that URL. */
            URL url = new URL(urls[0]);
            phpName = "getData";
            final HttpsURLConnection aHttpURLConnection = (HttpsURLConnection)url.openConnection();
            aHttpURLConnection.setDoInput(true);

            if(!AppConfig.CHECK_SSL_CERT) {
                aHttpURLConnection.setHostnameVerifier(SslHelper.getTrustAllVerifier());
                aHttpURLConnection.setSSLSocketFactory(SslHelper.getTrustAllSSLSocketFactory());
            }else{
                aHttpURLConnection.setSSLSocketFactory(new StrictSSLSocketFactory(mContext));
            }
            aHttpURLConnection.connect();

            if (aHttpURLConnection.getResponseCode() == 200) {
                /* Define InputStreams to read from the URLConnection. */
                InputStream aInputStream = aHttpURLConnection.getInputStream();
                BufferedInputStream aBufferedInputStream = new BufferedInputStream(
                        aInputStream);
                /* Read bytes to the Buffer until there is nothing more to read(-1) */
                StringBuffer aByteArrayBuffer = new StringBuffer();
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
            apiCallBack.result(jsonAnalysis.parser(phpName,jsonMsg));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface apiCallBack {
        void result(AllModel s);
    }
}