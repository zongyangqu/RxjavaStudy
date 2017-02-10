package com.rxjava.qzy.rxjavatestsimple.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/10
 *
 * 类描述：Activity启动工具类
 */

public class LauncherManager {
    private LauncherManager() {

    }

    private final static class DefaultLauncher implements Launcher {

        @Override
        public void launch(Activity launcher, Class<? extends Activity> actClass) {
            launch(launcher, new Intent(launcher, actClass));
        }

        @Override
        public void launchForResult(Activity launcher, Class<? extends Activity> actClass, int requestCode) {
            launchForResult(launcher, new Intent(launcher, actClass), requestCode);
        }

        @Override
        public void launchThenExit(Activity launcher, Class<? extends Activity> actClass) {
            launchExit(launcher, new Intent(launcher, actClass));
        }

        @Override
        public void launch(Activity launcher, Intent intent) {
            launcher.startActivity(intent);
        }

        @Override
        public void launchForResult(Activity launcher, Intent intent, int resultCode) {
            launcher.startActivityForResult(intent, resultCode);
        }

        @Override
        public void launchExit(Activity launcher, Intent intent) {
            launcher.startActivity(intent);
            launcher.finish();
        }

        @Override
        public void launch(Fragment launcher, Class<? extends Activity> actClass) {
            launch(launcher, new Intent(launcher.getActivity(), actClass));
        }

        @Override
        public void launchForResult(Fragment launcher, Class<? extends Activity> actClass, int requestCode) {
            launchForResult(launcher, new Intent(launcher.getActivity(), actClass), requestCode);
        }

        @Override
        public void launchThenExit(Fragment launcher, Class<? extends Activity> actClass) {

        }

        @Override
        public void launch(Fragment launcher, Intent intent) {
            launcher.startActivity(intent);
        }

        @Override
        public void launchForResult(Fragment launcher, Intent intent, int resultCode) {
            launcher.startActivityForResult(intent, resultCode);
        }

        @Override
        public void launchExit(Fragment launcher, Intent intent) {

        }
    }

    public static Launcher launcher = new DefaultLauncher();

    public static void setLauncher(Launcher launcher) {
        LauncherManager.launcher = launcher;
    }
}
