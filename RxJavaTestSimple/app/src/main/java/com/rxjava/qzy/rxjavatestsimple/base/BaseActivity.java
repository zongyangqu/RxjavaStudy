package com.rxjava.qzy.rxjavatestsimple.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/10
 *
 * 类描述：
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if (butterKnifeEnabled()) {
            ButterKnife.bind(this);
        }
        onViewCreated();
    }

    protected abstract Activity getActivity();

    protected abstract int getLayoutId();

    protected abstract void onViewCreated();

    protected boolean butterKnifeEnabled() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (butterKnifeEnabled()) {
            ButterKnife.unbind(this);
        }
    }
}
