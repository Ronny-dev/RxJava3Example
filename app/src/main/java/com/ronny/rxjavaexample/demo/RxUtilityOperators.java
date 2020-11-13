package com.ronny.rxjavaexample.demo;

import com.ronny.rxjavaexample.BaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Timed;

/**
 * Created by Ronny on 2020/11/13
 * This page lists various utility operators for working with Observables.
 *
 * @see <a href="https://github.com/ReactiveX/RxJava/wiki/Observable-Utility-Operators">
 *
 *     展示了相关Utility Operators的操作说明
 *          Delay操作符        {@link #createRxjavaUsedDelayOperator(int)}
 *          Time操作符         {@link #createRxjavaUsedTimeIntervalOperator()}
 *          Repeat操作符       {@link #createRxjavaUsedRepeatOperator()}
 *          Do***操作符        {@link #createRxjavaUsedDoOperator()}
 *          Materialize操作符  {@link #createRxjavaUsedMaterializeOperator()}
 */

public class RxUtilityOperators extends BaseActivity {
    @Override
    public void OnBtnClick() {
        createRxjavaUsedRepeatOperator();
    }

    /**
     * 重复操作符
     * repeat(int)：     重复指定次数
     * repeatWhen():     当特定Observable执行完毕后再次重复
     * repeatUntil()：   当指定boolean返回true时结束重复
     */
    private void createRxjavaUsedRepeatOperator() {
        createDefaultSubscribe(createDefaultObservable().repeat(2));

        createDefaultSubscribe(createDefaultObservable().repeatUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Throwable {
                return System.currentTimeMillis() % 5 == 0;
            }
        }));

        createDefaultSubscribe(createDefaultObservable().repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Throwable {
                return Observable.interval(2, TimeUnit.SECONDS);
            }
        }));
    }

    /**
     * 计时操作符
     * timeInterval： 拦截发射出来的数据，对数据进行计时操作，并在接收处提供获取方法
     * timestamp:     对每个数据重新包装，加上一个timestamp
     * 区别在于，timeInterval是时间间隔，timestamp是时间戳形式
     */
    private void createRxjavaUsedTimeIntervalOperator() {
        @NonNull Disposable disposable = createDefaultObservable().timestamp()
                .doOnComplete(new Action() {//这里要额外去监听complete周期，Timed不提供完成方法的监听
                    @Override
                    public void run() throws Throwable {
                        displayLog();
                    }
                })
                .subscribe(new Consumer<Timed<Integer>>() {
                    @Override
                    public void accept(Timed<Integer> integerTimed) throws Throwable {
                        appendLogNoDisplay("accept: " + integerTimed.value());
                        appendLogNoDisplay("expense time: " + integerTimed.time());
                    }
                });
    }

    /**
     * 转换对象操作符
     * 将接收者中的OnNext、OnError、OnComplete转换成一个Notification对象发出
     * materialize： 集合成Notification对象
     */
    private void createRxjavaUsedMaterializeOperator() {
        createDefaultObservable().materialize().subscribe(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Throwable {
                if (integerNotification.isOnNext()) {
                    //接收到一个消息
                    appendLogNoDisplay("received value: " + integerNotification.getValue());
                } else if (integerNotification.isOnComplete()) {
                    //完成了消息
                    displayLog();
                }
            }
        });
    }

    /**
     * Do***操作符
     * 给Observable生命周期的各个阶段加上一系列的回调监听
     * 当Observable执行到这个阶段，这些回调就会被触发
     */
    private void createRxjavaUsedDoOperator() {
        /**
         * 这里使用Consumer而不是普通的Subscribe.
         * 区别在于Consumer更清晰，只选择需要的监听即可；调用返回Disposable对象
         * {@link RxTypeActivity#createRxjavaByFlowable()}
         */
        @NonNull Disposable disposable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                appendLogNoDisplay("subscribe emitter 1");
                emitter.onNext(1);

                appendLogNoDisplay("subscribe emitter 2");
                emitter.onNext(2);
            }
        })
                .doOnEach(new Consumer<Notification<Integer>>() {
                    @Override
                    public void accept(Notification<Integer> integerNotification) throws Throwable {

                    }
                })//数据源每次发出数据都会调用此方法
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {

                    }
                })//数据源每次调用onNext之前都会调用此方法
                .doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {

                    }
                })//数据源每次调用Error之前都会调用此方法
                .doOnComplete(new Action() {
                    @Override
                    public void run() throws Throwable {

                    }
                })//数据源每次调用Complete之前都会调用此方法
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Throwable {

                    }
                })//数据源每次调用disposable之后都会调用此方法
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Throwable {

                    }
                });
    }

    /**
     * 延时操作符
     * 让发射数据的实时机延后处理，所有的发射都会依次延后一定时间
     * Delay: 延时数据的接收
     * DelaySubscription： 延时注册Subscriber，延后数据的发送
     */
    private void createRxjavaUsedDelayOperator(int delayTime) {
        appendLogNoDisplay(System.currentTimeMillis() + ": Current Time Stamp~");
        createDefaultSubscribe(createDefaultObservable().delaySubscription(delayTime, TimeUnit.SECONDS));
    }

    private @NonNull Observable<Integer> createDefaultObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                appendLogNoDisplay(System.currentTimeMillis() + ": emitter value: " + 1);
                emitter.onNext(1);

                appendLogNoDisplay(System.currentTimeMillis() + ": emitter value: " + 2);
                emitter.onNext(2);

                appendLogNoDisplay(System.currentTimeMillis() + ": emitter value: " + 3);
                emitter.onNext(3);

                emitter.onComplete();
            }
        });
    }

    private void createDefaultSubscribe(Observable<Integer> observable) {
        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                appendLogNoDisplay("start subscribe~~");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                appendLogNoDisplay(System.currentTimeMillis() + ": received value: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                displayLog();
            }
        });
    }
}
