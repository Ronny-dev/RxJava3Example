package com.ronny.rxjavaexample.demo;

import com.ronny.rxjavaexample.BaseActivity;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.FlowableEmitter;
import io.reactivex.rxjava3.core.FlowableOnSubscribe;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.MaybeEmitter;
import io.reactivex.rxjava3.core.MaybeObserver;
import io.reactivex.rxjava3.core.MaybeOnSubscribe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.core.SingleOnSubscribe;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;

/**
 * Created by Ronny on 2020/11/11
 *
 *      展示了五种常用的Rxjava创建类别
 *          -- Observable
 *          -- Flowable
 *          -- Single
 *          -- Completable
 *          -- Maybe
 */
public class RxTypeActivity extends BaseActivity {

    /**
     * 当在创建rxjava时，subscribe回调可以通过对应的SourceObservable创建{@link #createRxjavaByObservable()}
     * 也可以通过Consumer创建对应需要的回调 {@link #createRxjavaByFlowable()}
     *
     * 通常来说：
     *     如果需要BackPressure处理，直接选用flowable即可；
     *     其余情况直选Observable即可。
     */

    @Override
    public void OnBtnClick() {
        createRxjavaByObservable();
    }

    /**
     * Maybe 是 RxJava2.x 之后才有的新类型，可以看成是Single和Completable的结合。
     * Maybe 也只能发射单个事件或错误事件，即使发射多个数据，后面发射的数据也不会处理。
     * 只有 onSuccess 、 onError 、onComplete事件，没有 onNext 事件。
     */
    private void createRxjavaByMaybe() {
        Maybe.create(new MaybeOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull MaybeEmitter<Integer> emitter) throws Throwable {
                displayLog("Maybe emit 1");
                emitter.onSuccess(1);
                displayLog("Maybe emit 2");
                emitter.onSuccess(2);
                displayLog("Maybe emit 3");
                emitter.onSuccess(3);
                displayLog("Maybe emit 4");
                emitter.onSuccess(4);
                displayLog("Maybe emit 5");
                emitter.onSuccess(5);
            }
        }).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                displayLog("onSubscribe");
            }

            @Override
            public void onSuccess(@NonNull Integer integer) {
                displayLog("onSuccess : value : " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                displayLog("onErr: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                displayLog("onComplete");
            }
        });
    }

    /**
     * Completable只发送成功与否的消息。
     * 只有 onComplete 和 onError事件，没有 onNext 、onSuccess事件。
     */
    private void createRxjavaByCompletable() {
        Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) throws Throwable {
                emitter.onComplete();
            }
        }).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                displayLog("onSubscribe");
            }

            @Override
            public void onComplete() {
                displayLog("onComplete");
            }

            @Override
            public void onError(@NonNull Throwable e) {
                displayLog("onErr: " + e.toString());
            }
        });
    }

    /**
     * Single只发射单个数据或错误事件，即使发射多个数据，后面发射的数据也不会处理。
     * 只有 onSuccess 和 onError事件，没有 onNext 、onComplete事件。
     */
    private void createRxjavaBySingle() {
        Single.create(new SingleOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<Integer> emitter) throws Throwable {
                displayLog("Single emit 1");
                emitter.onSuccess(1);
                //以下的消息被发送，但不会被接收
                displayLog("Single emit 2");
                emitter.onSuccess(2);
                displayLog("Single emit 3");
                emitter.onSuccess(3);
                displayLog("Single emit 4");
                emitter.onSuccess(4);
                displayLog("Single emit 5");
                emitter.onSuccess(5);
            }
        }).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                displayLog("onSubscribe");
            }

            @Override
            public void onSuccess(@NonNull Integer integer) {
                displayLog("onSuccess: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                displayLog("onErr: " + e.toString());
            }
        });
    }

    private void createRxjavaByFlowable() {
        @NonNull Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull FlowableEmitter<Integer> emitter) throws Throwable {
                displayLog("Flowable emit 1");
                emitter.onNext(1);
                displayLog("Flowable emit 2");
                emitter.onNext(2);
                displayLog("Flowable emit 3");
                emitter.onNext(3);
                displayLog("Flowable emit 4");
                emitter.onNext(4);
                displayLog("Flowable emit 5");
                emitter.onNext(5);
            }
        }, BackpressureStrategy.ERROR);

        @NonNull Disposable disposable = flowable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) {
                displayLog("accept : value : " + integer);
            }
        });
    }

    private void createRxjavaByObservable() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                displayLog("Observable emit 1");
                emitter.onNext(1);
                displayLog("Observable emit 2");
                emitter.onNext(2);
                displayLog("Observable emit 3");
                emitter.onNext(3);
                displayLog("Observable emit 4");
                emitter.onNext(4);
                displayLog("Observable emit 5");
                emitter.onNext(5);
            }
        }).subscribe(new Observer<Integer>() {

            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                displayLog("onSubscribe");
                this.mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                displayLog("onNext : value : " + integer);
                if (integer == 3) {
                    mDisposable.dispose();
                    displayLog("dispose~!");
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                displayLog("onErr: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                displayLog("onComplete");
            }
        });
    }
}
