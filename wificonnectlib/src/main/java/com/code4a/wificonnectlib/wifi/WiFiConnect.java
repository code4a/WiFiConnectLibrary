package com.code4a.wificonnectlib.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.provider.Settings;

/**
 * Created by code4a on 2017/5/16.
 */

public class WiFiConnect {

    public static boolean changePassword(Context mContext, WifiManager mWifiManager, ScanResult mScanResult, String newPassword) {
        final WifiConfiguration config = getWifiConfiguration(mWifiManager, mScanResult);
        boolean saveResult = false;
        if (config != null) {
            saveResult = WiFi.changePasswordAndConnect(mContext, mWifiManager, config
                    , newPassword
                    , getNumOpenNetworksKept(mContext));
        }
        return saveResult;
    }

    public static boolean forgetCurrentNetwork(WifiManager mWifiManager, ScanResult mScanResult) {
        final WifiConfiguration config = getWifiConfiguration(mWifiManager, mScanResult);
        boolean result = false;
        if (config != null) {
            result = mWifiManager.removeNetwork(config.networkId)
                    && mWifiManager.saveConfiguration();
        }
        return result;
    }

    public static boolean newNetworkToConnect(Context mContext, WifiManager mWifiManager, ScanResult mScanResult, String password) {
        boolean connResult;
        if (isOpenNetwork(mScanResult)) {
            connResult = WiFi.connectToNewNetwork(mContext, mWifiManager, mScanResult, null, getNumOpenNetworksKept(mContext));
        } else {
            connResult = WiFi.connectToNewNetwork(mContext, mWifiManager, mScanResult
                    , password
                    , getNumOpenNetworksKept(mContext));
        }
        return connResult;
    }

    public static boolean configNetworkToConnect(Context mContext, WifiManager mWifiManager, ScanResult mScanResult) {
        final WifiConfiguration config = getWifiConfiguration(mWifiManager, mScanResult);
        boolean connResult = false;
        if (config != null) {
            connResult = WiFi.connectToConfiguredNetwork(mContext, mWifiManager, config, false);
        }
        return connResult;
    }

    public static WifiConfiguration getWifiConfiguration(WifiManager mWifiManager, ScanResult mScanResult) {
        return WiFi.getWifiConfiguration(mWifiManager, mScanResult, getScanResultSecurity(mScanResult));
    }

    public static String getScanResultSecurity(ScanResult mScanResult) {
        return WiFi.ConfigSec.getScanResultSecurity(mScanResult);
    }

    public static boolean isOpenNetwork(ScanResult mScanResult) {
        return WiFi.ConfigSec.isOpenNetwork(getScanResultSecurity(mScanResult));
    }

    public static boolean isAdHoc(ScanResult scanResule) {
        return scanResule.capabilities.indexOf("IBSS") != -1;
    }

    public static String readableSecurity(ScanResult scanResult) {
        final String rawSecurity = WiFi.ConfigSec.getDisplaySecirityString(scanResult);
        return WiFi.ConfigSec.isOpenNetwork(rawSecurity) ? "Open" : rawSecurity;
    }

    public static int getNumOpenNetworksKept(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(),
                Settings.Secure.WIFI_NUM_OPEN_NETWORKS_KEPT, 10);
    }
}
