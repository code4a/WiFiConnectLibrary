package com.code4a.retrofitutil.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.code4a.retrofitutil.service.BaseService;
import com.code4a.retrofitutil.ssl.SSLHelper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    /**
     * 创建Api Service
     *
     * @param clazz Service的class
     * @param <S>   泛型类
     * @return 返回api service类
     */
    public <S> S create(Class<S> clazz) {
        return retrofit.create(clazz);
    }

    /**
     * 发送get请求，获取字符串，响应在主线程
     *
     * @param path       请求的二级路径
     * @param subscriber 订阅者
     */
    public void getStringRequest(String path, Subscriber<String> subscriber) {
        getStringRequest(path, new HashMap<String, Object>(), subscriber);
    }

    /**
     * 发送get请求，获取字符串，响应在主线程
     *
     * @param path       请求的二级路径
     * @param paramMap   请求参数
     * @param subscriber 订阅者
     */
    public void getStringRequest(String path, @NonNull Map<String, Object> paramMap, Subscriber<String> subscriber) {
        doSubscribe(create(BaseService.class)
                .getStringRequest(path, paramMap), subscriber);
    }

    /**
     * 发送get请求，获取响应体，响应在IO线程
     *
     * @param path       请求的二级路径
     * @param subscriber 订阅者
     */
    public void getResponseBodyRequest(String path, Subscriber<ResponseBody> subscriber) {
        getResponseBodyRequest(path, new HashMap<String, Object>(), subscriber);
    }

    /**
     * 发送get请求，获取响应体，响应在IO线程
     *
     * @param path       请求的二级路径
     * @param paramMap   请求参数
     * @param subscriber 订阅者
     */
    public void getResponseBodyRequest(String path, @NonNull Map<String, Object> paramMap, Subscriber<ResponseBody> subscriber) {
        doIoSubscribe(create(BaseService.class)
                .getResponseBodyRequest(path, paramMap), subscriber);
    }

    /**
     * 发送post请求，获取字符串，响应在主线程
     *
     * @param path        请求的二级路径
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postStringRequest(String path, RequestBody requestBody, Subscriber<String> subscriber) {
        postStringRequest(path, new HashMap<String, Object>(), requestBody, subscriber);
    }

    /**
     * 发送post请求，获取字符串，响应在主线程
     *
     * @param path        请求的二级路径
     * @param paramMap    请求参数
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postStringRequest(String path, @NonNull Map<String, Object> paramMap, RequestBody requestBody, Subscriber<String> subscriber) {
        doSubscribe(create(BaseService.class)
                .postStringRequest(path, paramMap, requestBody), subscriber);
    }

    /**
     * 发送post请求，获取响应体，响应在IO线程
     *
     * @param path        请求的二级路径
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postResponseBodyRequest(String path, RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        postResponseBodyRequest(path, new HashMap<String, Object>(), requestBody, subscriber);
    }

    /**
     * 发送post请求，获取响应体，响应在IO线程
     *
     * @param path        请求的二级路径
     * @param paramMap    请求参数
     * @param requestBody 请求体
     * @param subscriber  订阅者
     */
    public void postResponseBodyRequest(String path, @NonNull Map<String, Object> paramMap, RequestBody requestBody, Subscriber<ResponseBody> subscriber) {
        doIoSubscribe(create(BaseService.class)
                .postResponseBodyRequest(path, paramMap, requestBody), subscriber);
    }

    /**
     * 订阅事件，变换各自执行线程，请求在IO线程，响应在主线程
     *
     * @param observable 事件
     * @param subscriber 订阅者
     * @param <T>        泛型类
     * @return 订阅者，可用于取消事件
     */
    public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 订阅事件，变换各自执行线程，请求在IO线程，响应在IO线程
     *
     * @param observable 事件
     * @param subscriber 订阅者
     * @param <T>        泛型类
     * @return 订阅者，可用于取消事件
     */
    public <T> Subscription doIoSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    /**
     * 变换各自执行线程，请求在IO线程，响应在主线程
     *
     * @param observable 事件
     * @param <T>        泛型类
     * @return 事件
     */
    public <T> Observable<T> doSubscribe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 类构造器
     */
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
            STRING, GSON, DEFAULT
        }

        /**
         * 设置上下文对象，用于获取证书流对象
         *
         * @param context 上下文对象
         * @return 构造器对象
         */
        public Builder setContext(@NonNull Context context) {
            this.context = context;
            return this;
        }

        /**
         * 设置请求的根地址
         *
         * @param baseUrl 请求根地址
         * @return 构造器对象
         */
        public Builder setBaseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        /**
         * 设置OKHttp超时时间
         *
         * @param timeoutSec 超时时间，单位s
         * @return 构造器对象
         */
        public Builder setTimeoutSec(@NonNull int timeoutSec) {
            this.timeoutSec = timeoutSec;
            return this;
        }

        /**
         * 设置验证的host
         *
         * @param hostnameVerifiers host数组
         * @return 构造器对象
         */
        public Builder setHostnameVerifiers(@NonNull String[] hostnameVerifiers) {
            this.hostnameVerifiers = hostnameVerifiers;
            return this;
        }

        /**
         * 设置证书名称，证书需放在asset目录下
         *
         * @param assetCertificateName 证书名称
         * @return 构造器对象
         */
        public Builder setAssetCertificateName(@NonNull String assetCertificateName) {
            this.assetCertificateName = assetCertificateName;
            return this;
        }

        /**
         * 设置请求头
         *
         * @param httpHeaderMap 请求头
         * @return 构造器对象
         */
        public Builder setHttpHeaderMap(@NonNull Map<String, String> httpHeaderMap) {
            this.httpHeaderMap = httpHeaderMap;
            return this;
        }

        /**
         * 设置http类型，http/https
         *
         * @param httpType 请求类型
         * @return 构造器对象
         */
        public Builder setHttpType(@NonNull HttpType httpType) {
            this.httpType = httpType;
            return this;
        }

        /**
         * 设置响应数据类型 string/json/default
         *
         * @param transferDataType 数据类型
         * @return 构造器对象
         */
        public Builder setTransferDataType(@NonNull TransferDataType transferDataType) {
            this.transferDataType = transferDataType;
            return this;
        }

        /**
         * 设置ssl, 可继承SSLHelper类，实现ssl的校验操作等
         *
         * @param sslHelper ssl帮助类
         * @return 构造器对象
         */
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
                okHttpClient = okHttpClientEngine.httpsOkHttpClient(sslHelper, timeoutSec);
            }
            if (transferDataType == null) {
                transferDataType = TransferDataType.DEFAULT;
            }
            if (transferDataType == TransferDataType.DEFAULT) {
                retrofit = RetrofitEngine.getDefaultRetrofit(baseUrl, okHttpClient);
            } else if (transferDataType == TransferDataType.STRING) {
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
