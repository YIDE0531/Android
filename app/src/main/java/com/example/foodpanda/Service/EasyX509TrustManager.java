package com.example.foodpanda.Service;

import android.util.Log;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class EasyX509TrustManager implements X509TrustManager {

	private String TAG = EasyX509TrustManager.class.getSimpleName();
	private X509TrustManager standardTrustManager = null;
;

	public EasyX509TrustManager(KeyStore keystore) throws NoSuchAlgorithmException, KeyStoreException {
		super();
		TrustManagerFactory factory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		factory.init(keystore);
		TrustManager[] trustmanagers = factory.getTrustManagers();
		if (trustmanagers.length == 0) {
			throw new NoSuchAlgorithmException("no trust manager found");
		}
		this.standardTrustManager = (X509TrustManager) trustmanagers[0];
	}

	public void checkClientTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
	}
	
	public void checkServerTrusted(X509Certificate[] certificates, String authType) throws CertificateException {
		String issuerName = certificates[0].getIssuerDN().getName();
		Log.v(TAG, "issuerName: "+ issuerName);
	}

	public X509Certificate[] getAcceptedIssuers() {
		X509Certificate[] certificates = this.standardTrustManager.getAcceptedIssuers();
		return certificates;
	}
}
