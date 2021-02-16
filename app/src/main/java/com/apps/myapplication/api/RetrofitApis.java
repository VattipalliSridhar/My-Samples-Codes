package com.apps.myapplication.api;

import com.apps.myapplication.BuildConfig;
import com.apps.myapplication.model.RequestNotificaton;
import com.apps.myapplication.model.SongModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitApis {

    class Factory {
        public static RetrofitApis create() {
            //create logger
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            // default time out is 15 seconds
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.URL_SEARCH)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
            return retrofit.create(RetrofitApis.class);
        }
    }

    @GET("songs.json")
    Call<SongModel> getSongList();


    @Headers({"Authorization: key=AAAAqOQGhXc:APA91bF00x4RYidp6680lOfq9AK-5C-LV5SN85IgONM4Vlo7n25aZpKqcyKjWIrO6IRIt4k61_emYbzEVi9DeXY2uM1cFM8FLYLeJqyZ4wCUct_QideXcTdIKJ_xZ0gEWdViScXHPODz",
            "Content-Type:application/json"})
    @POST("fcm/send")
    Call<ResponseBody> sendChatNotification(@Body RequestNotificaton requestNotificaton);
}
