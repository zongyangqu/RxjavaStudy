package com.rxjava.qzy.rxjavatestsimple.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.rxjava.qzy.rxjavatestsimple.R;
import com.rxjava.qzy.rxjavatestsimple.base.BaseActivity;
import com.rxjava.qzy.rxjavatestsimple.utils.LauncherManager;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/13
 *
 * 类描述：线程控制 —— Scheduler (一)
 */

/**
 * 说明：
 * 在RxJava 中，Scheduler ——调度器，相当于线程控制器，RxJava 通过它来指定每一段代码应该运行在什么样的线程。
 * RxJava 已经内置了几个 Scheduler ，它们已经适合大多数的使用场景：

 1.Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。

 2.Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。

 3.Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。行为模式和 newThread() 差不多，
 区别在于 io() 的内部实现是是用一个无数量上限的线程池，可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。
 不要把计算工作放在 io() 中，可以避免创建不必要的线程。

 4.Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
 例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，
 否则 I/O 操作的等待时间会浪费 CPU。

 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
 有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。 * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。 * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
 */

public class RxJavaScheduler1Activity extends BaseActivity{
    public static String TAG = "RxJavaScheduler1Activity----->";
    int drawableRes = R.mipmap.ic_launcher;
    @Bind(R.id.btn_scheduler)
    Button btn_scheduler;
    @Bind(R.id.btn_scheduler_img)
    Button btn_scheduler_img;
    @Bind(R.id.image_scheduler)
    ImageView image_scheduler;

    public static void startFrom(Activity context) {
        Intent intent = new Intent(context, RxJavaScheduler1Activity.class);
        LauncherManager.launcher.launch(context, intent);
    }
    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scheduler_first;
    }

    @Override
    protected void onViewCreated() {

    }

    @OnClick({R.id.btn_scheduler,R.id.btn_scheduler_img})
    public void onViewClick(View view){
        switch (view.getId()){
            case R.id.btn_scheduler:
                Observable.just(1,2,3,4)
                        .subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                        .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                        .subscribe(new Action1<Integer>() {
                            @Override
                            public void call(Integer integer) {
                                Log.i(TAG,integer+"");//这里是主线程
                            }
                        });
                break;
            case R.id.btn_scheduler_img:
                Observable.create(new Observable.OnSubscribe<Drawable>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void call(Subscriber<? super Drawable> subscriber) {
                        //这部分会执行在子线程（IO线程）
                        Drawable drawable = getTheme().getDrawable(drawableRes);
                        subscriber.onNext(drawable);
                        subscriber.onCompleted();
                    }
                }) .subscribeOn(Schedulers.io())// 指定 subscribe() 发生在 IO 线程
                    .observeOn(AndroidSchedulers.mainThread())// 指定 Subscriber 的回调发生在主线程
                    .subscribe(new Subscriber<Drawable>() {
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable e) {

                        }
                        @Override
                        public void onNext(Drawable drawable) {
                            image_scheduler.setImageDrawable(drawable);
                        }
                    });
                break;

        }
    }
}
