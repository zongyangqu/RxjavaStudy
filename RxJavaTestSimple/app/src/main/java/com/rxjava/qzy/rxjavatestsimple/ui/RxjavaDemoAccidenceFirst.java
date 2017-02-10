package com.rxjava.qzy.rxjavatestsimple.ui;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.rxjava.qzy.rxjavatestsimple.R;
import com.rxjava.qzy.rxjavatestsimple.base.BaseActivity;
import com.rxjava.qzy.rxjavatestsimple.utils.LauncherManager;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * 作者：quzongyang
 *
 * 创建时间：2017/2/10
 *
 * 类描述：Rxjava入门( 一 )
 */

/**
 * 1.RxJava 的观察者模式
 RxJava 有四个基本概念：Observable(可观察者，即被观察者)、 Observer(观察者)、 subscribe(订阅)、事件。
 Observable 和 Observer 通过 subscribe()方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。

 与传统观察者模式不同， RxJava 的事件回调方法除了普通事件 onNext() （相当于 onClick()/onEvent()）之外，
 还定义了两个特殊的事件：onCompleted()和 onError()。

 onCompleted():事件队列完结。RxJava 不仅把每个事件单独处理，还会把它们看做一个队列。RxJava 规定，
 当不会再有新的 onNext()发出时，需要触发 onCompleted()方法作为标志。

 onError():事件队列异常。在事件处理过程中出异常时，onError()会被触发，同时队列自动终止，不允许再有事件发出。
 在一个正确运行的事件序列中,onCompleted()和 onError()有且只有一个，并且是事件序列中的最后一个。
 需要注意的是，onCompleted()和 onError()二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。*/

public class RxjavaDemoAccidenceFirst extends BaseActivity {
    public static String TAG = "RxjavaDemoAccidenceFirst----->";

    public static void startFrom(Activity context) {
        Intent intent = new Intent(context, RxjavaDemoAccidenceFirst.class);
        LauncherManager.launcher.launch(context, intent);
    }

    @Bind(R.id.btn_subscribe)
    Button btn_subscribe;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_accidence_first;
    }
    @Override
    protected void onViewCreated() {
    }
    @Override
    protected Activity getActivity() {
        return this;
    }

    /**
     * 2. 基本实现
     基于以上的概念， RxJava 的基本实现主要有三点：

     1) 创建 Observer
     Observer 即观察者，它决定事件触发的时候将有怎样的行为。 RxJava 中的 Observer 接口的实现方式：

     除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：
     Subscriber。 Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的：
     */
    Observer<String> observer = new Observer<String>() {
        @Override
        public void onCompleted() {
            Log.i(TAG,"onCompleted");
        }
        @Override
        public void onError(Throwable e) {
            Log.i(TAG,"onError");
        }
        @Override
        public void onNext(String string) {
            Log.i(TAG,string);
        }
    };
    /*Subscriber<String> subscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
        }
        @Override
        public void onError(Throwable e) {
        }
        @Override
        public void onStart() {
            super.onStart();
        }
        @Override
        public void onNext(String o) {
        }
    };*/

    /**
     * 2) 创建 Observable
     Observable 即被观察者，它决定什么时候触发事件以及触发怎样的事件。
     RxJava 使用 create() 方法来创建一个 Observable ，并为它定义事件触发规则：
     */
    Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("Hi");
            subscriber.onNext("Aloha");
            subscriber.onCompleted();
        }
    });


    /**
     * create() 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列，例如：
     just(T...): 和from(T[]) 将传入的参数依次发送出来。just(T...) 的例子和 from(T[]) 的例子，都和 create(OnSubscribe) 的例子是等价的。
     */
    Observable observableJust = Observable.just("Hello", "Hi", "Aloha");

    /**
     * from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     */
    String[] words = {"Hello", "Hi", "Aloha"};
    Observable observableFrom = Observable.from(words);

    @OnClick({R.id.btn_subscribe})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_subscribe:
                /**
                 *3) Subscribe (订阅)
                 创建了 Observable 和 Observer 之后，再用 subscribe() 方法将它们联结起来，整条链子就可以工作了。代码形式很简单：
                 */
                observable.subscribe(observer);
                //或者
                //observable.subscribe(subscriber);
                break;
        }
    }



}
