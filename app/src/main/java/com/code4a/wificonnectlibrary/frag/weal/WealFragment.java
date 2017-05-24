package com.code4a.wificonnectlibrary.frag.weal;

import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.code4a.retrofitutil.uitl.LogUtil;
import com.code4a.wificonnectlibrary.R;
import com.code4a.wificonnectlibrary.adapter.GankIoGridviewAdapter;
import com.code4a.wificonnectlibrary.bean.GankIoBean;
import com.code4a.wificonnectlibrary.frag.BaseFragment;

import java.util.List;

import butterknife.BindView;

/**
 * Created by code4a on 2017/5/24.
 */

public class WealFragment extends BaseFragment {

    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    GankIoGridviewAdapter gankIoGridviewAdapter;
    int page = 1;

    public static WealFragment getInstance() {
        return new WealFragment();
    }

    @Override
    protected void initView() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        gridRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    protected void initData() {
        page = 1;
        loadData(getString(R.string.title_weal), page, 20);
        gankIoGridviewAdapter = new GankIoGridviewAdapter();
        gridRv.setAdapter(gankIoGridviewAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LogUtil.d(TAG, " ----- onRefresh ---- ");
                loadData(getString(R.string.title_weal), page, 20);
            }
        });
    }

    @Override
    protected void onLoadDataSuccess(List<GankIoBean.ResultsBean> results) {
        gankIoGridviewAdapter.addDatas(results);
        page++;
    }

    @Override
    protected void onLoadDataFailed() {
        makeToast("数据刷新失败！");
    }

    @Override
    protected void onLoadDataError() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onLoadDataCompleted() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_weal;
    }

}
