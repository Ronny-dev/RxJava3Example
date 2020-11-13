package com.ronny.rxjavaexample.demo;

import com.ronny.rxjavaexample.BaseActivity;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.functions.Supplier;

/**
 * Created by Ronny on 2020/11/13
 *
 *      展示了一些Rxjava中的创建操作
 *          -- Create   使用一个函数从头创建一个Observable
 *          -- Defer    只有当订阅者订阅才创建Observable；为每个订阅创建一个新的Observable
 *          -- From     将一个Iterable, 一个Future, 或者一个数组转换成一个Observable
 *          -- Just     将一个或多个对象转换成发射这个或这些对象的一个Observable
 *          -- Range    创建一个发射指定范围的整数序列的Observable
 */
public class RxEmitterActivity extends BaseActivity {

    @Override
    public void OnBtnClick() {

    }

    /**
     * 创建一个正常的Observable对象
     */
    private void createByDefault() {
        @NonNull Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) throws Throwable {

            }
        });
    }

    /**
     * 创建一个正常的Observable对象
     *  当subscribe订阅动作发出后，才会进行defer发送动作
     */
    private void createByDefer() {
        @NonNull Observable<Integer> defer = Observable.defer(new Supplier<ObservableSource<Integer>>() {
            @Override
            public ObservableSource<Integer> get() throws Throwable {
                return null;
            }
        });
    }

    /**
     * 可将其他类型对象和数据类型转换为Observable
     */
    private void createByFrom() {
        Integer[] items = { 0, 1, 2, 3, 4, 5 };
        @NonNull Observable<Integer> observable = Observable.fromArray(items);
    }

    /**
     * 可将其他数据类型转换为Observable对象
     *      区别于from，From会将数组逐个发出
     *      Just是原样序列发出
     */
    private void createByJust() {
        Integer[] items = { 0, 1, 2, 3, 4, 5 };
        @NonNull Observable<Integer[]> just = Observable.just(items, items);
    }

    /**
     * 创建一个范围内的有序整数序列，可以指定范围的初始和长度
     */
    private void createByRange() {
        @NonNull Observable<Integer> range = Observable.range(1, 5);
    }
}
