package com.code4a.retrofitutil.ssl;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Created by code4a on 2017/5/22.
 */

public class SSLHelper {

    protected Context context;
    final String PROTOCOL_TYPE = "TLS";
    private String[] hostnameVerifiers;
    private java.lang.String assetCertificateName;

    public SSLHelper(Context context, String[] hostnameVerifiers, String assetCertificateName) {
        this.context = context;
        this.hostnameVerifiers = hostnameVerifiers;
        this.assetCertificateName = assetCertificateName;
    }

    /**
     * 获取证书工厂
     *
     * @return 证书工厂
     */
    public SSLSocketFactory getSSLCertification() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            InputStream certificateInputStream = context.getAssets().open(assetCertificateName);
            if (certificateInputStream == null) return sslSocketFactory;
            // 证书工厂
            CertificateFactory factory = CertificateFactory.getInstance("X.509");
            // 获取到证书
            Certificate certificate = factory.generateCertificate(certificateInputStream);
            // 读取证书
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null);
            trustStore.setCertificateEntry("1", certificate);
            certificateInputStream.close();
            // 初始化SSLContext
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(trustStore);
            SSLContext sslContext = SSLContext.getInstance(PROTOCOL_TYPE);
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new java.security.SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    /**
     * 域名验证
     *
     * @return 域名验证
     */
    public HostnameVerifier getHostnameVerifier() {
        HostnameVerifier TRUSTED_VERIFIER = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                boolean ret = false;
                for (String host : hostnameVerifiers) {
                    if (host.equalsIgnoreCase(hostname)) {
                        ret = true;
                        break;
                    }
                }
                return ret;
            }
        };
        return TRUSTED_VERIFIER;
    }

    public X509TrustManager getX509TrustManager() {
        X509TrustManager manager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
        return manager;
    }
}
