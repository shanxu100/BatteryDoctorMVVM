package com.example.gzs11641.myapplication.rxJava;

import android.os.HandlerThread;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;

public class RxUtil {

    private static Scheduler sWorkScheduler;
    static {
        HandlerThread handlerThread = new HandlerThread("Rxutil WorkerThread");
        handlerThread.start();
        sWorkScheduler = AndroidSchedulers.from(handlerThread.getLooper());
    }




    /**
     * 在工作线程执行，在主线程回调
     *
     * @return
     */
    public static Observable getObservable(final BaseSource baseSource) {
        return Observable
                .create(new Observable.OnSubscribe<BaseResult>() {
                    @Override
                    public void call(Subscriber<? super BaseResult> subscriber) {
                        try {
                            if (!subscriber.isUnsubscribed()){
                                subscriber.onNext(baseSource.call());
                            }
                            if (!subscriber.isUnsubscribed()) {
                                subscriber.onCompleted();
                            }
                        } catch (Exception e) {
                            subscriber.onError(Exceptions.propagate(e));

                        }
                    }
                })
                .subscribeOn(sWorkScheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }


    public interface Source<T> {
        T call() throws Exception;
    }

    public interface BaseSource {
        BaseResult call() throws Exception;
    }


    public static class BaseResult {

        public String str;

    }


}
