package com.example.foodpanda.Service;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class SslHelper {

    private static final String TAG = SslHelper.class.getSimpleName();

    public static void trustAllHosts(){
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }

    public static HostnameVerifier getTrustAllVerifier(){
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return allHostsValid;
    }

    public static SSLContext getSSLContext(){
        SSLContext sc = null;
        try{
            TrustManager[] trustCerts = new TrustManager[]{new EasyX509TrustManager(null)};
            sc = SSLContext.getInstance("TLS");
            sc.init(null, trustCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }catch(Exception e){
            Log.e(TAG, e.toString());
        }
        return sc;
    }

    /***  不檢查憑證: 有資安風險*/
    public static SSLSocketFactory getTrustAllSSLSocketFactory() {
        SSLContext sc = null;
        SSLSocketFactory sslFactory = null;
        try{
            TrustManager[] trustCerts = new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
                    return new X509Certificate[0];
                }
            }};
            sc = SSLContext.getInstance("TLS");//TLSv1.2
            sc.init(null, trustCerts, new java.security.SecureRandom());
            sslFactory = sc.getSocketFactory();
        }catch(Exception e){
            Log.e(TAG, e.toString());
        }
        return sslFactory;
    }

    public static  TrustManager[] getTrustManager(InputStream input) {
        TrustManager[] trustManagers = null;
        try {
            Certificate ca = loadCertificate(input);
            KeyStore keyStore = createKeyStore(ca);
            trustManagers = createTrustManager(keyStore);
        } catch (CertificateException e) {
            Log.e(TAG, "Failed to create certificate factory", e);
        } catch (KeyStoreException e) {
            Log.e(TAG, "Failed to get key store instance", e);
        }
        return trustManagers;
    }

    private static Certificate loadCertificate(InputStream input) throws CertificateException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        return cf.generateCertificate(input);
    }

    private static KeyStore createKeyStore(Certificate ca) throws KeyStoreException {
        try {
            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            return keyStore;
        } catch (IOException e) {
            Log.e(TAG, "Could not load key store", e);
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Could not load key store", e);
        } catch (CertificateException e) {
            Log.e(TAG, "Could not load key store", e);
        }
        return null;
    }

    private static TrustManager[] createTrustManager(KeyStore keyStore) throws KeyStoreException {
        try {
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
            return tmf.getTrustManagers();
        } catch (NoSuchAlgorithmException e) {
            Log.e(TAG, "Failed to get trust manager factory with default algorithm", e);
        }
        return null;
    }
}
