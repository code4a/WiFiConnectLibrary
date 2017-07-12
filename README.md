## WiFiConnectLib
> 将Android手机WiFi连接到指定的热点

### 如何使用

1.在项目Module的`build.gradle`中添加依赖

`compile 'com.code4a:wificonnectlib:1.0.4'`

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

### 致谢

* [Kevin Yuan](https://github.com/mkch)的[android-wifi-connecter](https://github.com/mkch/android-wifi-connecter)
WiFiConnect Library中的代码从这个项目中抽取！
* [代码家](https://github.com/daimajia)的[干货集中营 API 文档](http://gank.io/api)