## WiFiConnectLib
> 将Android手机WiFi连接到指定的热点

### 如何使用

1.在项目Module的`build.gradle`中添加依赖

`compile 'com.code4a:wificonnectlib:1.0.3'`

2.然后创建`WiFiConnectManager`对象

`WiFiConnectManager mWifiManager = new WiFiConnectManager(context);`

3.可通过如下API进行WiFi的连接操作

```
// 通过ScanResult和WiFi密码连接到指定的WiFi热点上
connectWiFi(ScanResult mScanResult, String password, WiFiConnectListener wiFiConnectListener)
// 通过WiFi名称和WiFi密码连接到指定的WiFi热点上
connectWiFi(String ssid, String password, WiFiConnectListener wiFiConnectListener)
```

例如：

```
mWifiManager.connectWiFi(result, password.getText().toString().trim(), new WiFiConnectManager.WiFiConnectListener() {
    @Override
    public void connectStart() {
        showToast("开始连接");
    }

    @Override
    public void connectResult(String ssid, boolean isSuccess) {
        showToast("连接到" + ssid + (isSuccess ? "成功" : "失败"));
    }

    @Override
    public void connectEnd() {
        showToast("连接完成");
    }
});
```

## RetrofitUtil
> Retrofit + OKHttp + RxJava 简单二次封装

### 如何使用

1.在项目Module的`build.gradle`中添加依赖

`compile 'com.code4a:retrofit-util:0.0.4'`

2.创建RetrofitManager对象

```
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
```

3.Api简述

* 创建ApiService

    `public <S> S create(Class<S> clazz)`
    
* 订阅事件

    `public <T> Subscription doSubscribe(Observable<T> observable, Subscriber<T> subscriber)`
    
4.eg

* http get请求获取字符串，响应在主线程中

```
void httpGetString(){
    new RetrofitManager.Builder()
            .setBaseUrl("http://www.code4a.com/")
            .setTransferDataType(RetrofitManager.Builder.TransferDataType.STRING)
            .build()
            .getStringRequest("android", new Subscriber<String>() {
                @Override
                public void onCompleted() {
                    
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(String s) {

                }
            });
}
```

* http get请求获取ResponseBody，响应在IO线程中，可进行下载等操作

```
void httpGetResponseBody(){
    new RetrofitManager.Builder()
            .setBaseUrl("http://www.code4a.com/")
            .build()
            .getResponseBodyRequest("android", new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ResponseBody body) {

                }
            });
}
```

* 自定义Service 进行json数据类型传输，自动转换bean对象

     1.Service类
     
    ```
    @GET("{type}/{size}/{page}")
    Observable<GankIoBean> getBeanRequest(
            @Path(value = "type", encoded = true) String type,
            @Path(value = "size", encoded = true) int size,
            @Path(value = "page", encoded = true) int page
    );
    ```
    
    2.封装转换
    
    ```
    GankIoService getService() {
        return retrofitManager.create(GankIoService.class);
    }

    public Observable<GankIoBean> getResource(String type, int page, int size) {
        return getService().getBeanRequest(type, size, page);
    }

    public Subscription getResourceDoSubcribe(String type, int page, int size, Subscriber<GankIoBean> subscriber) {
        return retrofitManager.doSubscribe(getResource(type, page, size), subscriber);
    }
    ```
    
    3.获取结果
    
    ```
    protected void loadData(String type, int page, int size) {
        unsubscribe();
        subscription = getGankIoManager().getResourceDoSubcribe(type, page, size, new Subscriber<GankIoBean>() {
            @Override
            public void onCompleted() {
                onLoadDataCompleted();
            }

            @Override
            public void onError(Throwable e) {
                onLoadDataError();
            }

            @Override
            public void onNext(GankIoBean gankIoBean) {
                if (gankIoBean.isError()) {
                    onLoadDataFailed();
                } else {
                    onLoadDataSuccess(gankIoBean.getResults());
                }
            }
        });
    ```

#### 还可用作HTTPS请求，可用默认SSLHelper，未校验证书，可以继承该类，通过`setSSLHelper(SSLHelper sslHelper)`实现自定义ssl规则

### 致谢

* [Kevin Yuan](https://github.com/mkch)的[android-wifi-connecter](https://github.com/mkch/android-wifi-connecter)
WiFiConnect Library中的代码从这个项目中抽取！
* [代码家](https://github.com/daimajia)的[干货集中营 API 文档](http://gank.io/api)