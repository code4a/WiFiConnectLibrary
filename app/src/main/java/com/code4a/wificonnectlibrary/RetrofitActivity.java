package com.code4a.wificonnectlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.code4a.retrofitutil.RetrofitManager;
import com.code4a.retrofitutil.uitl.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * Created by code4a on 2017/5/23.
 */

public class RetrofitActivity extends AppCompatActivity {

    RetrofitManager retrofitManager;
    @BindView(R.id.get_test)
    Button getTest;
    @BindView(R.id.post_test)
    Button postTest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        retrofitManager = new RetrofitManager.Builder()
                .setBaseUrl("http://gank.io/api/data/")
                .build();

    }


    @OnClick({R.id.get_test, R.id.post_test})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_test:
                getStringTest();
                break;
            case R.id.post_test:
                break;
        }
    }

    public void getStringTest() {
        retrofitManager.getStringRequest("Android/10/1", new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String result) {
                LogUtil.e(result);
            }
        });
    }
}
