package com.arno.demo.life.rxjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.arno.demo.life.R;

import org.reactivestreams.Subscriber;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxJavaActivity extends AppCompatActivity {
    private static final String TAG = "RxJavaActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
//        testRxjava();
//        testRxJava2();
//        testOperator();
//        testOperator2();
        testObserver();
    }

    private void testRxjava() {
        Observable.just(1, 2, 3, 4) // IO 线程，由 subscribeOn() 指定
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .map(integer -> {
                    Log.i(TAG, "map1: " + integer + ",thread= " + Thread.currentThread().getName());
                    return integer * 2;
                }) // 新线程，由 observeOn() 指定
                .observeOn(Schedulers.io())
                .map(integer -> {
                    Log.i(TAG, "map2: " + integer + ",thread= " + Thread.currentThread().getName());
                    return integer + 100;
                }) // IO 线程，由 observeOn() 指定
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() { // Android 主线程，由 observeOn() 指定
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.w(TAG, "subscribe onSubscribe() called with: d = [" + d + "]" + ",thread= " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.w(TAG, "subscribe onNext() called with: integer = [" + integer + "]" + ",thread= " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.w(TAG, "subscribe onError() called with: e = [" + e + "]" + ",thread= " + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.w(TAG, "subscribe onComplete() called" + ",thread= " + Thread.currentThread().getName());
                    }
                });


    }

    private void testRxJava2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> emitter) {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe:" + d.getClass().getName());
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }

    private void testOperator() {
        Observable.range(1, 10)
                // map操作符将每个发射的数据转换为另一种形式的数据
                .map(integer -> {
                    Log.i(TAG, "map: " + integer);
                    return integer * 2;
                })
                // flatMap操作符将每个发射的数据转换为一个Observable，然后将这些Observable发射的数据合并后一起发射
                .flatMap(integer -> {
                    Log.i(TAG, "flatMap: " + integer);
                    return Observable.just(integer, integer + 1);
                })
                // concatMap操作符类似于flatMap，但是它按照严格的顺序连接而不是合并那些生成的Observables，然后发射它们的数据
                .concatMap(integer -> {
                    Log.i(TAG, "concatMap: " + integer);
                    return Observable.just(integer, integer + 2);
                })
//                // switchMap操作符将每个发射的数据转换为一个Observable，然后只发射最近的Observable发射的数据
//                .switchMap(integer -> {
//                    Log.i(TAG, "switchMap: " + integer);
//                    return Observable.just(integer, integer + 3);
//                })
                // buffer操作符将Observable发射的数据分成一组一组的，然后发射这些数据组
                .buffer(3)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe:" + d.getClass().getName());
                    }

                    @Override
                    public void onNext(@NonNull List<Integer> integers) {
                        Log.d(TAG, "onNext: " + integers);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });
    }

    private void testOperator2() {
//        Observable.range(1, 5)
//                // concatMap操作符类似于flatMap，但是它按照严格的顺序连接而不是合并那些生成的Observables，然后发射它们的数据
//                .concatMap(integer -> {
//                    Log.i(TAG, "concatMap: " + integer);
//                    return Observable.just(integer, integer + 2).delay(1, TimeUnit.SECONDS);
//                })
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        Log.d(TAG, "concatMap onSubscribe:" + d.getClass().getName());
//                    }
//
//                    @Override
//                    public void onNext(@NonNull Integer integer) {
//                        Log.d(TAG, "concatMap onNext: " + integer);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Log.d(TAG, "concatMap onError: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "concatMap onComplete");
//                    }
//                });

        Observable.range(1, 5)
                // switchMap操作符将每个发射的数据转换为一个Observable，然后只发射最近的Observable发射的数据
                .switchMap(integer -> {
                    Log.i(TAG, "switchMap: " + integer);
                    return Observable.just(integer, integer + 2).delay(1, TimeUnit.SECONDS);
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "switchMap onSubscribe:" + d.getClass().getName());
                    }

                    @Override
                    public void onNext(@NonNull Integer integer) {
                        Log.d(TAG, "switchMap onNext: " + integer);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "switchMap onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "switchMap onComplete");
                    }
                });
    }

    private void testObserver() {
        // Observable示例
        Observable.just("Hello, world!")
                .subscribe(s -> Log.i(TAG, s));

// Flowable示例
        Flowable.range(1, 5)
                .subscribe(integer -> Log.i(TAG, String.valueOf(integer)));

// Single示例
        Single.just("Hello, world!")
                .subscribe(s -> Log.i(TAG, s));

// Maybe示例
        Maybe.just("Hello, world!")
                .subscribe(s -> Log.i(TAG, s));

// Completable示例
        Completable.complete()
                .subscribe(() -> Log.i(TAG, "Completed!"));
    }
}