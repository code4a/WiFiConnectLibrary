package com.code4a.wificonnectlibrary.net;

import com.code4a.wificonnectlibrary.bean.GankIoBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by code4a on 2017/5/24.
 */

public interface GankIoService {

    @GET("{type}/{size}/{page}")
    Observable<GankIoBean> getBeanRequest(
            @Path(value = "type", encoded = true) String type,
            @Path(value = "size", encoded = true) int size,
            @Path(value = "page", encoded = true) int page
    );
}
