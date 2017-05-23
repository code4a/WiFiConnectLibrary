package com.code4a.retrofitutil.engine;

import com.code4a.retrofitutil.ssl.SSLHelper;
import com.code4a.retrofitutil.uitl.LogUtil;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by code4a on 2017/5/22.
 */

public class OkHttpClientEngine {

    Map<String, String> httpHeaderMap;

    public OkHttpClientEngine(Map<String, String> httpHeaderMap) {
        this.httpHeaderMap = httpHeaderMap;
    }

    public OkHttpClient defaultOkHttpClient(SSLHelper sslHelper, int timeoutSec) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor())
                .addInterceptor(getHttpHeaderInterceptor())
                .hostnameVerifier(sslHelper.getHostnameVerifier())
                .sslSocketFactory(sslHelper.getSSLCertification(), sslHelper.getX509TrustManager())
                .connectTimeout(timeoutSec, TimeUnit.SECONDS)
                .writeTimeout(timeoutSec, TimeUnit.SECONDS)
                .readTimeout(timeoutSec, TimeUnit.SECONDS)
                .build();
        return client;
    }

    public OkHttpClient defaultOkHttpClient(int timeoutSec) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(getHttpLoggingInterceptor())
                .addInterceptor(getHttpHeaderInterceptor())
                .connectTimeout(timeoutSec, TimeUnit.SECONDS)
                .writeTimeout(timeoutSec, TimeUnit.SECONDS)
                .readTimeout(timeoutSec, TimeUnit.SECONDS)
                .build();
        return client;
    }

    HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d("OkHttpClientEngine:" + message);
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    Interceptor getHttpHeaderInterceptor() {
        Interceptor httpHeaderInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                for (String key : httpHeaderMap.keySet()) {
                    builder.addHeader(key, httpHeaderMap.get(key));
                }
                Request request = builder.build();
                return chain.proceed(request);
            }
        };
        return httpHeaderInterceptor;
    }

}
