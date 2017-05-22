package com.code4a.wificonnectlib;

import android.content.Context;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.Toast;

import com.code4a.wificonnectlib.wifi.WiFi;
import com.code4a.wificonnectlib.wifi.WiFiConnect;

import java.util.List;

/**
 * Created by code4a on 2017/5/16.
 */

public final class WiFiConnectManager {

    private Context mContext;
    private WifiManager mWifiManager;

    /**
     * 构造方法
     * @param mContext 上下文对象
     */
    public WiFiConnectManager(Context mContext) {
        this.mContext = mContext;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        startScan();
    }

    /**
     * 开始扫描WiFi
     */
    public void startScan() {
        mWifiManager.startScan();
    }

    /**
     * 获取扫描结果
     * @return 扫描结果
     */
    public List<ScanResult> getScanResults() {
        return mWifiManager.getScanResults();
    }

    /**
     * 连接到指定SSID名称的WiFi热点上
     * @param ssid wifi名称
     * @param password wifi密码
     * @param wiFiConnectListener 连接监听
     */
    public void connectWiFi(String ssid, String password, WiFiConnectListener wiFiConnectListener) {
        ScanResult scanResult = getTargetScanResultBySSID(ssid);
        if (scanResult == null && wiFiConnectListener != null) {
            wiFiConnectListener.connectStart();
            wiFiConnectListener.connectResult(ssid, false);
            wiFiConnectListener.connectEnd();
        } else {
            connectWiFi(scanResult, password, wiFiConnectListener);
        }
    }

    /**
     * 连接到指定ScanResult的WiFi热点上
     * @param mScanResult wifi的扫描结果
     * @param password wifi密码
     * @param wiFiConnectListener 连接监听
     */
    public void connectWiFi(ScanResult mScanResult, String password, WiFiConnectListener wiFiConnectListener) {
        if (wiFiConnectListener != null) {
            wiFiConnectListener.connectStart();
        }
        final WifiConfiguration config = WiFiConnect.getWifiConfiguration(mWifiManager, mScanResult);
        if (config == null) {
            boolean connectResult = WiFiConnect.newNetworkToConnect(mContext, mWifiManager, mScanResult, password);
            if (wiFiConnectListener != null) {
                wiFiConnectListener.connectResult(mScanResult.SSID, connectResult);
            }
        } else {
            final boolean isCurrentNetwork_ConfigurationStatus = config.status == WifiConfiguration.Status.CURRENT;
            final WifiInfo info = mWifiManager.getConnectionInfo();
            final boolean isCurrentNetwork_WifiInfo = info != null
                    && TextUtils.equals(info.getSSID(), mScanResult.SSID)
                    && TextUtils.equals(info.getBSSID(), mScanResult.BSSID);
            if (isCurrentNetwork_ConfigurationStatus || isCurrentNetwork_WifiInfo) {
                // TODO 已连接当前 WiFi
                if (wiFiConnectListener != null) {
                    wiFiConnectListener.connectResult(mScanResult.SSID, true);
                }
            } else {
                boolean connectResult = WiFiConnect.configNetworkToConnect(mContext, mWifiManager, mScanResult);
                if (wiFiConnectListener != null) {
                    wiFiConnectListener.connectResult(mScanResult.SSID, connectResult);
                }
            }
        }
        if (wiFiConnectListener != null) {
            wiFiConnectListener.connectEnd();
        }
    }

    /**
     * 根据SSID名称 获取到ScanResult
     * @param ssid wifi名称
     * @return 扫描结果
     */
    ScanResult getTargetScanResultBySSID(String ssid) {
        ScanResult sr = null;
        List<ScanResult> wifiList = getScanResults();
        if (wifiList == null || TextUtils.isEmpty(ssid)) {
            return sr;
        }
        for (int i = 0; i < wifiList.size(); i++) {
            if (ssid.equals(wifiList.get(i).SSID)) {
                sr = wifiList.get(i);
                break;
            }
        }
        return sr;
    }

    /**
     * wifi连接监听
     */
    public interface WiFiConnectListener {
        void connectStart();

        void connectResult(String ssid, boolean isSuccess);

        void connectEnd();
    }
}
