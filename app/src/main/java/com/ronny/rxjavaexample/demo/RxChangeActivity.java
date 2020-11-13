package com.ronny.rxjavaexample.demo;

import com.ronny.rxjavaexample.BaseActivity;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observables.GroupedObservable;

/**
 * Created by Ronny on 2020/11/13
 *
 * 变换操作符
 */
public class RxChangeActivity extends BaseActivity {

    @Override
    public void OnBtnClick() {
        createMapOperator();
    }

    private void createFlatMapByOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                appendLogNoDisplay("emitter value: " + 1);
                emitter.onNext(1);
                appendLogNoDisplay("emitter value: " + 2);
                emitter.onNext(2);
                appendLogNoDisplay("emitter value: " + 3);
                emitter.onNext(3);
            }
        }).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Throwable {
                return Observable.just(String.valueOf(integer));
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Throwable {
                displayLog();
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Throwable {
                appendLogNoDisplay(o.toString());
            }
        });
    }

    private void createGroupByOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                appendLogNoDisplay("emitter value: " + 1);
                emitter.onNext(1);
                appendLogNoDisplay("emitter value: " + 2);
                emitter.onNext(2);
                emitter.onNext(2);
                appendLogNoDisplay("emitter value: " + 3);
                emitter.onNext(3);
            }
        }).groupBy(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Throwable {
                return String.valueOf(integer);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Throwable {
                displayLog();
            }
        }).subscribe(new Consumer<GroupedObservable<String, Integer>>() {
            @Override
            public void accept(GroupedObservable<String, Integer> group) throws Throwable {
                switch (group.getKey()) {
                    case "1":
                        appendLogNoDisplay("This is group 1~");
                        break;
                    case "2":
                        appendLogNoDisplay("This is group 2~");
                        break;
                }
            }
        });
    }

    private void createMapOperator() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {
                appendLogNoDisplay("emitter value: " + 1);
                emitter.onNext(1);
                appendLogNoDisplay("emitter value: " + 2);
                emitter.onNext(2);
                appendLogNoDisplay("emitter value: " + 3);
                emitter.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Throwable {
                return String.valueOf(integer);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Throwable {
                displayLog();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Throwable {
                appendLogNoDisplay("received value: " + s);
            }
        });
    }
}
