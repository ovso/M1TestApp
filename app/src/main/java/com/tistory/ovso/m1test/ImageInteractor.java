package com.tistory.ovso.m1test;

import com.tistory.ovso.m1test.model.Channel;
import com.tistory.ovso.m1test.model.Data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;
import retrofit2.http.Query;

public class ImageInteractor {
    private final static String api_key= "49a885dc3636e74c6371aeb1fd49d264";
    private Call<Data> mCall;

    public void execute(int page, final boolean isUpdate) {

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        client.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apis.daum.net/")
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create()) // create(gson)
                .build();

        Service service = retrofit.create(Service.class);
        mCall = service.create(
                api_key, "영화", page, 18, "json"
        );
        mCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()) {
                    Data data = response.body();
                    mOnResultListener.onChannel(data.channel, isUpdate);
                } else {
                    mOnResultListener.onFail();
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                t.printStackTrace();
                mOnResultListener.onFail();
            }
        });
    }

    private OnResultListener mOnResultListener;
    public void setOnResultListener(OnResultListener listener) {
        mOnResultListener = listener;
    }

    /**
     * 서버 연동 취소
     */
    public void cancel() {
        mCall.cancel();
    }

    /**
     * 날씨 연동 결과 수신기
     */
    public interface OnResultListener {
        void onChannel(Channel channel, boolean isUpdate);
        void onFail();
    }
    //search/image
    public interface Service {
        @POST("search/image")
        Call<Data> create(@Query("apikey")String apikey,
                          @Query("q") String q,
                          @Query("pageno") Integer pageno,
                          @Query("result") Integer result,
                          @Query("output") String format);
    }

}