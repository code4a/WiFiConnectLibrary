package com.code4a.retrofitutil;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.code4a.retrofitutil.engine.OkHttpClientEngine;
import com.code4a.retrofitutil.engine.RetrofitEngine;
import com.code4a.retrofitutil.service.BaseService;
import com.code4a.retrofitutil.ssl.SSLHelper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by code4a on 2017/5/22.
 */

public final class RetrofitManager {

    Retrofit retrofit;

    RetrofitManager(Retrofit retrofit) {
        this.retrofit = retrofit;
    }

    public <S> S create(Class<S> clazz) {
        return retrofit.create(clazz);
    }

    public void getStringRequest(String path, Subscriber<String> subscriber) {
        getStringRequest(path, new HashMap<String, Object>(), subscriber);
    }

    public void getStringRequest(String path, @NonNull Map<String, Object> headerMap, Subscriber<String> subscriber) {
        doSubscribe(create(BaseService.class)
                .getStringRequest(path, headerMap), subscriber);
    }

    public void postStringRequest(String path, @NonNull Map<String, Object> headerMap, RequestBody requestBody, Subscriber<String> subscriber) {
        doSubscribe(create(BaseService.class)
                .postStringRequest(path, headerMap, requestBody), subscriber);
    }

    public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public <T> Subscription doIoSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    public <T> Observable<T> doSubscribe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final class Builder {
        private Context context;
        private String baseUrl;
        private int timeoutSec = 30;
        private String[] hostnameVerifiers;
        private java.lang.String assetCertificateName;
        private Map<String, String> httpHeaderMap;
        private HttpType httpType;
        private TransferDataType transferDataType;
        private SSLHelper sslHelper;

        public enum HttpType {
            HTTP, HTTPS
        }

        public enum TransferDataType {
            STRING, GSON
        }

        public Builder setContext(@NonNull Context context) {
            this.context = context;
            return this;
        }

        public Builder setBaseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setTimeoutSec(@NonNull int timeoutSec) {
            this.timeoutSec = timeoutSec;
            return this;
        }

        public Builder setHostnameVerifiers(@NonNull String[] hostnameVerifiers) {
            this.hostnameVerifiers = hostnameVerifiers;
            return this;
        }

        public Builder setAssetCertificateName(@NonNull String assetCertificateName) {
            this.assetCertificateName = assetCertificateName;
            return this;
        }

        public Builder setHttpHeaderMap(@NonNull Map<String, String> httpHeaderMap) {
            this.httpHeaderMap = httpHeaderMap;
            return this;
        }

        public Builder setHttpType(@NonNull HttpType httpType) {
            this.httpType = httpType;
            return this;
        }

        public Builder setTransferDataType(@NonNull TransferDataType transferDataType) {
            this.transferDataType = transferDataType;
            return this;
        }

        public Builder setSSLHelper(SSLHelper sslHelper) {
            this.sslHelper = sslHelper;
            return this;
        }

        public RetrofitManager build() {
            checkParams();
            Retrofit retrofit = null;
            OkHttpClient okHttpClient = null;
            if (httpHeaderMap == null) {
                httpHeaderMap = new HashMap<>();
            }
            OkHttpClientEngine okHttpClientEngine = new OkHttpClientEngine(httpHeaderMap);
            if (httpType == null) {
                httpType = HttpType.HTTP;
            }
            if (httpType == HttpType.HTTP) {
                okHttpClient = okHttpClientEngine.defaultOkHttpClient(timeoutSec);
            } else if (httpType == HttpType.HTTPS) {
                if (sslHelper == null) {
                    sslHelper = new SSLHelper(context, hostnameVerifiers, assetCertificateName);
                }
                okHttpClient = okHttpClientEngine.defaultOkHttpClient(sslHelper, timeoutSec);
            }
            if (transferDataType == null) {
                transferDataType = TransferDataType.STRING;
            }
            if (transferDataType == TransferDataType.STRING) {
                retrofit = RetrofitEngine.getScalarsConverterRetrofit(baseUrl, okHttpClient);
            } else if (transferDataType == TransferDataType.GSON) {
                retrofit = RetrofitEngine.getGsonConverterRetrofit(baseUrl, okHttpClient);
            }
            RetrofitManager retrofitManager = new RetrofitManager(retrofit);
            return retrofitManager;
        }

        private void checkParams() {
            if (TextUtils.isEmpty(baseUrl)) {
                throw new RuntimeException("Please call setBaseUrl method, baseUrl can't be null");
            }
            if (httpType != null && httpType == HttpType.HTTPS && sslHelper == null) {
                if (context == null) {
                    throw new RuntimeException("Please call setContext method, context can't be null");
                }
                if (TextUtils.isEmpty(assetCertificateName)) {
                    throw new RuntimeException("Please call setAssetCertificateName method, assetCertificateName can't be null");
                }
                if (hostnameVerifiers == null) {
                    throw new RuntimeException("Please call setHostnameVerifiers method, hostnameVerifiers can't be null");
                }
            }
        }
    }

}
