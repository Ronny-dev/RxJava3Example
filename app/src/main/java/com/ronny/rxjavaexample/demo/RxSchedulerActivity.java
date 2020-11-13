package com.ronny.rxjavaexample.demo;

import android.os.SystemClock;
import com.ronny.rxjavaexample.BaseActivity;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by Ronny on 2020/11/11
 */
public class RxSchedulerActivity extends BaseActivity {

    /**
     * 线程调度器
     *  -- Schedulers.computation():        适合运行在密集计算的操作，大多数异步操作符使用该调度器
     *  -- Scheduler.io():                  适合运行I/0和阻塞操作.
     *  -- Scheduler.single():              适合需要单一线程的操作
     *  -- Schedulers.trampoline():         适合需要顺序运行的操作
     *  -- AndroidSchedulers.mainThread()   安卓主线程操作
     */

    @Override
    public void OnBtnClick() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                appendLogNoDisplay("当前被观察者线程ID： " + Thread.currentThread().getName());
                appendLogNoDisplay("--------------");
                appendLogNoDisplay("Observable emit 1");
                emitter.onNext(1);
                appendLogNoDisplay("Observable emit 2");
                emitter.onNext(2);
                SystemClock.sleep(2000);
                appendLogNoDisplay("Observable emit 3");
                emitter.onNext(3);
                appendLogNoDisplay("Observable emit 4");
                emitter.onNext(4);
                appendLogNoDisplay("Observable emit 5");
                emitter.onNext(5);
                emitter.onComplete();
            }
        })      .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        appendLogNoDisplay("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        appendLogNoDisplay("onNext: " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        appendLogNoDisplay("onErr: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        appendLogNoDisplay("onComplete");
                        displayLog();
                    }
                });
    }
}
