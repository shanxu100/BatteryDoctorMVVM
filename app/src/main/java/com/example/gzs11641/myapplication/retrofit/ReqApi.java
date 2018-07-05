package com.example.gzs11641.myapplication.retrofit;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ReqApi {

    static final String ACCESS_TOKEN="at.d85o970d0z5i98oc16poun5k50y9e8hr-67uwogdgx5-1cluq09-wy1wjw5nn";

     static final String APP_KEY="41b01cf05f30438084f4a4dd23b4f2af";

     static final String BASE_URL="https://open.ys7.com/";

     @POST("api/lapp/camera/list")
     @FormUrlEncoded
     Call<RepResult> getCameraList(@Field("accessToken")String accessToken);


     Observable<RepResult> getCameraList2(@Field("accessToken") String accessToken);


}
