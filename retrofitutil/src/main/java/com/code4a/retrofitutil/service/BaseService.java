package com.code4a.retrofitutil.service;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by code4a on 2017/5/22.
 */

public interface BaseService {

    @GET("{path}")
    Observable<String> getStringRequest(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, Object> map);

    @GET("{path}")
    Observable<ResponseBody> getResponseBodyRequest(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, Object> map);

    @POST("{path}")
    Observable<String> postStringRequest(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, Object> map, @Body RequestBody req);

    @POST("{path}")
    Observable<ResponseBody> postResponseBodyRequest(@Path(value = "path", encoded = true) String path, @QueryMap Map<String, Object> map, @Body RequestBody req);

}
