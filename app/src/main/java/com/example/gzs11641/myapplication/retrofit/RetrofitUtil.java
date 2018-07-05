package com.example.gzs11641.myapplication.retrofit;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class RetrofitUtil {
    private static final String TAG = "RetrofitUtil";


    public static void init() {

//                .addConverterFactory()
//                .addCallAdapterFactory(sCallAdapterFactoryCreator.create());
    }

    public static <T> T create(Class<T> service) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(ReqApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        return retrofitBuilder.build().create(service);

    }


    public static void create() {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(ReqApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
//                .addCallAdapterFactory(RxJa.create());

        ReqApi reqApi = retrofitBuilder.build().create(ReqApi.class);
        Call<RepResult> call = reqApi.getCameraList(ReqApi.ACCESS_TOKEN);

        call.enqueue(new Callback<RepResult>() {
            @Override
            public void onResponse(Call<RepResult> call, Response<RepResult> response) {
                Log.e(TAG, response.body().toJson());
            }

            @Override
            public void onFailure(Call<RepResult> call, Throwable t) {

            }
        });

        Observable<RepResult> obs=reqApi.getCameraList2(ReqApi.ACCESS_TOKEN);
        obs.observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<RepResult>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError");
                    }


                    @Override
                    public void onNext(RepResult repResult) {
                        Log.e(TAG, "onNext----" + repResult.toJson() + " \ncurrentThread:" + Thread.currentThread().getName());
                    }
                });


    }

}
