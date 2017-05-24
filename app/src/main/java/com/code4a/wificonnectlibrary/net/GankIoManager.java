package com.code4a.wificonnectlibrary.net;

import com.code4a.retrofitutil.engine.RetrofitManager;
import com.code4a.wificonnectlibrary.bean.GankIoBean;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by code4a on 2017/5/24.
 */

public final class GankIoManager {

    final static String BASE_RUL = "http://gank.io/api/data/";

    static GankIoManager mInstance;

    RetrofitManager retrofitManager;

    GankIoManager() {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Accept", "application/json");
        retrofitManager = new RetrofitManager.Builder()
                .setBaseUrl(BASE_RUL)
                .setTransferDataType(RetrofitManager.Builder.TransferDataType.GSON)
                .setHttpHeaderMap(headerMap)
                .setHttpType(RetrofitManager.Builder.HttpType.HTTP)
                .setTimeoutSec(15)
                .build();
    }

    public static synchronized GankIoManager create() {
        if (mInstance == null) {
            mInstance = new GankIoManager();
        }
        return mInstance;
    }

    GankIoService getService() {
        return retrofitManager.create(GankIoService.class);
    }

    public Observable<GankIoBean> getResource(String type, int page, int size) {
        return getService().getBeanRequest(type, size, page);
    }

    public Subscription getResourceDoSubcribe(String type, int page, int size, Subscriber<GankIoBean> subscriber) {
        return retrofitManager.doSubscribe(getResource(type, page, size), subscriber);
    }
}
