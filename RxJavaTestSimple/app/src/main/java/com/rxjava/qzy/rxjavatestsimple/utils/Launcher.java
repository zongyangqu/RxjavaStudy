package com.rxjava.qzy.rxjavatestsimple.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/10
 *
 * 类描述：
 */

public interface Launcher {

    void launch(Activity launcher, Class<? extends Activity> actClass);

    void launchForResult(Activity launcher, Class<? extends Activity> actClass, int requestCode);

    void launchThenExit(Activity launcher, Class<? extends Activity> actClass);

    void launch(Activity launcher, Intent intent);

    void launchForResult(Activity launcher, Intent intent, int resultCode);

    void launchExit(Activity launcher, Intent intent);

    void launch(Fragment launcher, Class<? extends Activity> actClass);

    void launchForResult(Fragment launcher, Class<? extends Activity> actClass, int requestCode);

    void launchThenExit(Fragment launcher, Class<? extends Activity> actClass);

    void launch(Fragment launcher, Intent intent);

    void launchForResult(Fragment launcher, Intent intent, int resultCode);

    void launchExit(Fragment launcher, Intent intent);
}

