package com.code4a.wificonnectlibrary.frag;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.code4a.wificonnectlibrary.RetrofitActivity;
import com.code4a.wificonnectlibrary.bean.GankIoBean;
import com.code4a.wificonnectlibrary.net.GankIoManager;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;

/**
 * Created by code4a on 2017/5/24.
 */

public abstract class BaseFragment extends Fragment {

    protected final String TAG = BaseFragment.this.getClass().getSimpleName();

    protected Subscription subscription;
    protected RetrofitActivity holdingActivity;
    Unbinder unbinder;

    protected GankIoManager getGankIoManager() {
        return holdingActivity == null ? null : holdingActivity.getGankIoManager();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getFragmentLayoutId(), null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        holdingActivity = (RetrofitActivity) getActivity();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unsubscribe();
        unbinder.unbind();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    protected void makeToast(String text) {
        Toast.makeText(holdingActivity, text, Toast.LENGTH_SHORT).show();
    }

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

    }

    protected abstract void onLoadDataSuccess(List<GankIoBean.ResultsBean> results);

    protected abstract void onLoadDataFailed();

    protected abstract void onLoadDataError();

    protected abstract void onLoadDataCompleted();

    @LayoutRes
    protected abstract int getFragmentLayoutId();

    protected void initData() {
    }

    protected void initView() {
    }
}
