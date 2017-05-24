package com.code4a.wificonnectlibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.code4a.retrofitutil.engine.RetrofitManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by code4a on 2017/5/23.
 */

public class TestMainActivity extends AppCompatActivity {

    @BindView(R.id.wifi_list)
    Button wifiList;
    @BindView(R.id.retrofit_test)
    Button retrofitTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.wifi_list, R.id.retrofit_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wifi_list:
                startActivity(new Intent(this, WiFiListActivity.class));
                break;
            case R.id.retrofit_test:
                startActivity(new Intent(this, RetrofitActivity.class));
                break;
        }
    }

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
}
