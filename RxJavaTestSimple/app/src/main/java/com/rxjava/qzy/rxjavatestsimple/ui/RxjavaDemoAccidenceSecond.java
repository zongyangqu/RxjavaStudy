package com.rxjava.qzy.rxjavatestsimple.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/10
 *
 * 类描述：Rxjava入门( 二 )  Action接口的使用
 */

/**
 * 除了 subscribe(Observer) 和 subscribe(Subscriber) ，subscribe() 还支持不完整定义的回调，
 * RxJava 会自动根据定义创建出 Subscriber 。形式如下：
 */
public class RxjavaDemoAccidenceSecond extends BaseActivity{
    public static String TAG = "RxjavaDemoAccidenceSecond----->";
    private String[] names = {"Tom","Jack","Kobe"};

    @Bind(R.id.btn_subscribe_action)
    Button btn_subscribe_action;
    @Bind(R.id.btn_set_image)
    Button btn_set_image;
    @Bind(R.id.btn_print_text)
    Button btn_print_text;
    @Bind(R.id.image_logo)
    ImageView image_logo;

    public static void startFrom(Activity context) {
        Intent intent = new Intent(context, RxjavaDemoAccidenceSecond.class);
        LauncherManager.launcher.launch(context, intent);
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_accidence_second;
    }

    @Override
    protected void onViewCreated() {

    }

    Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            Log.i(TAG, s);
        }
    };
    Action1<Throwable> onErrorAction = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
        }
    };
    Action0 onCompletedAction = new Action0() {
        @Override
        public void call() {
            Log.i(TAG, "completed");
        }
    };

    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });

    @OnClick({R.id.btn_subscribe_action,R.id.btn_set_image,R.id.btn_print_text})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_subscribe_action:
                /**
                 *
                 简单的说可以把一个Action当作的是一个观察者（Observer）来看，
                 Action1中的call方法因为有参数（如果参数是Throwable则相当与观察者的onError方法也就是只会执行onError方法，
                 否则相当于观察者的onNext方法也只会执行onNext方法），
                 Action0中的call方法没有参数可以看作的观察者的onCompleted方法。
                 */
                // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()，只会调用观察者的onNext
                observable.subscribe(onNextAction);
                // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
                observable.subscribe(onNextAction, onErrorAction);
                // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
                observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
               break;
            case R.id.btn_set_image://由 id 取得图片并显示
                final int drawableRes = R.mipmap.ic_launcher;
                Observable.create(new Observable.OnSubscribe<Drawable>() {
                    @Override
                    public void call(Subscriber<? super Drawable> subscriber) {
                        Drawable drawable = getTheme().getDrawable(drawableRes);
                        subscriber.onNext(drawable);
                        subscriber.onCompleted();
                    }
                }).subscribe(new Observer<Drawable>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        image_logo.setImageDrawable(drawable);
                    }
                });
                break;

            case R.id.btn_print_text://打印字符串数组
                Observable.from(names).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG,s);
                    }
                });
                break;
        }
    }
}
