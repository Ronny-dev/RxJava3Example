package com.ronny.rxjavaexample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ronny.rxjavaexample.demo.RxEmitterActivity;
import com.ronny.rxjavaexample.demo.RxTypeActivity;
import com.ronny.rxjavaexample.demo.RxSchedulerActivity;
import com.ronny.rxjavaexample.demo.RxUtilityOperators;

/**
 * Something Important
 * https://github.com/ReactiveX/RxJava
 *   source
 *      .operator1()
 *      .operator2()
 *      .operator3()
 *      .subscribe(consumer)
 *
 *  -----------------
 *
 * RxJava3 Source
 *  -- Observable：  发送0个N个的数据，不支持背压
 *  -- Flowable：    发送0个N个的数据，支持Reactive-Streams和背压
 *  -- Single：      只能发送单个数据或者一个错误
 *  -- Completable： 没有发送任何数据，但只处理 onComplete 和 onError 事件。
 *  -- Maybe：       能够发射0或者1个数据，要么成功，要么失败。
 *
 *  ------------------
 *
 * 线程调度器
 *  -- Schedulers.computation():        适合运行在密集计算的操作，大多数异步操作符使用该调度器
 *  -- Scheduler.io():                  适合运行I/0和阻塞操作.
 *  -- Scheduler.single():              适合需要单一线程的操作
 *  -- Schedulers.trampoline():         适合需要顺序运行的操作
 *  -- AndroidSchedulers.mainThread()   安卓主线程操作
 *
 *  ------------------
 *
 *  What's BackPressure
 *
 * 当数据流通过异步的步骤执行时，这些步骤的执行速度可能不一致。也就是说上流数据发送太快，下流没有足够的能力去
 * 处理。为了避免这种情况，一般要么缓存上流的数据，要么抛弃数据。但这种处理方式，有时会带来很大的问题。为此，
 * RxJava带来了背压的概念。背压是一种流量的控制步骤，在不知道上流还有多少数据的情形下控制内存的使用，
 * 表示它们还能处理多少数据。
 *
 * 支持背压的有Flowable类，不支持背压的有Observable，Single, Maybe and Completable类。
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_demo1:
                /*
                  How to create default rxjava by difference source.
                 */
                startActivity(new Intent(this, RxTypeActivity.class));
                break;
            case R.id.btn_demo2:
                /*
                  How to schedule Rxjava in other thread.
                 */
                startActivity(new Intent(this, RxSchedulerActivity.class));
                break;
            case R.id.btn_demo3:
                /*
                  How to usb utility operators in rxjava
                 */
                startActivity(new Intent(this, RxUtilityOperators.class));
                break;
            case R.id.btn_demo4:
                /*
                  How to create emitter in rxjava
                 */
                startActivity(new Intent(this, RxEmitterActivity.class));
                break;
        }
    }
}