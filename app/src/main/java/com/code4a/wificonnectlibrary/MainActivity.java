package com.code4a.wificonnectlibrary;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TwoLineListItem;

import com.code4a.wificonnectlib.WiFiConnectManager;

import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;

public class MainActivity extends ListActivity {
    private WiFiConnectManager mWifiManager;
    private List<ScanResult> mScanResults;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWifiManager = new WiFiConnectManager(this);

        setListAdapter(mListAdapter);

        getListView().setOnItemClickListener(mItemOnClick);
    }

    @Override
    public void onResume() {
        super.onResume();
        final IntentFilter filter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(mReceiver, filter);
        mWifiManager.startScan();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                mScanResults = mWifiManager.getScanResults();
                mListAdapter.notifyDataSetChanged();

                mWifiManager.startScan();
            }

        }
    };

    private BaseAdapter mListAdapter = new BaseAdapter() {

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null || !(convertView instanceof TwoLineListItem)) {
                convertView = View.inflate(getApplicationContext(),
                        android.R.layout.simple_list_item_2, null);
                convertView.setBackgroundColor(Color.BLACK);
            }

            final ScanResult result = mScanResults.get(position);
            ((TwoLineListItem) convertView).getText1().setText(result.SSID);
            ((TwoLineListItem) convertView).getText2().setText(
                    String.format("%s  %d", result.BSSID, result.level)
            );
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public int getCount() {
            return mScanResults == null ? 0 : mScanResults.size();
        }
    };

    private AdapterView.OnItemClickListener mItemOnClick = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            final ScanResult result = mScanResults.get(position);
            showInputPasswordDialog(result);
        }
    };

    MaterialDialog materialDialog;

    void showInputPasswordDialog(final ScanResult result) {
        View configView = LayoutInflater.from(MainActivity.this)
                .inflate(R.layout.input_password_layout, null);
        TextView message = (TextView) configView.findViewById(R.id.wifi_config_message);
        message.setText("请输入" + result.SSID + "的密码进行连接");
        final EditText password = (EditText) configView.findViewById(R.id.wifi_config_password);
        materialDialog = new MaterialDialog(MainActivity.this)
                .setTitle("WiFi Connect")
                .setContentView(configView)
                .setCanceledOnTouchOutside(true)
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                        materialDialog.dismiss();
                    }
                })
                .setNegativeButton("CANCEL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDialog.dismiss();
                    }
                });

        materialDialog.show();
    }

    public void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
    }
}
