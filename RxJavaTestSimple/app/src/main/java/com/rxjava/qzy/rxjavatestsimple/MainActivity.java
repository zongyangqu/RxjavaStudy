package com.rxjava.qzy.rxjavatestsimple;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

import com.rxjava.qzy.rxjavatestsimple.base.BaseActivity;
import com.rxjava.qzy.rxjavatestsimple.ui.RxJavaMap1Activity;
import com.rxjava.qzy.rxjavatestsimple.ui.RxJavaScheduler1Activity;
import com.rxjava.qzy.rxjavatestsimple.ui.RxjavaDemoAccidenceFirst;
import com.rxjava.qzy.rxjavatestsimple.ui.RxjavaDemoAccidenceSecond;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.btn_rxjava_accidence1)
    Button btn_rxjava_accidence1;
    @Bind(R.id.btn_rxjava_accidence2)
    Button btn_rxjava_accidence2;
    @Bind(R.id.btn_rxjava_Scheduler1)
    Button btn_rxjava_Scheduler1;
    @Bind(R.id.btn_rxjava_Scheduler2)
    Button btn_rxjava_Scheduler2;
    @Bind(R.id.btn_rxjava_map1)
    Button btn_rxjava_map1;
    @Bind(R.id.btn_rxjava_compose)
    Button btn_rxjava_compose;
    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated() {

    }

    @OnClick({R.id.btn_rxjava_accidence1,R.id.btn_rxjava_accidence2,R.id.btn_rxjava_Scheduler1,R.id.btn_rxjava_map1})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_rxjava_accidence1:
                RxjavaDemoAccidenceFirst.startFrom(getActivity());
                break;
            case R.id.btn_rxjava_accidence2:
                RxjavaDemoAccidenceSecond.startFrom(getActivity());
                break;
            case R.id.btn_rxjava_Scheduler1:
                RxJavaScheduler1Activity.startFrom(getActivity());
                break;
            case R.id.btn_rxjava_Scheduler2:
                break;
            case R.id.btn_rxjava_map1:
                RxJavaMap1Activity.startFrom(getActivity());
                break;
        }
    }
}
