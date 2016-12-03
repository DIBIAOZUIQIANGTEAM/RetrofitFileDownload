package com.hengda.zwf.httputil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;

public class FileApi {

    public static final int DEFAULT_TIMEOUT = 5;
    public Retrofit retrofit;

    public FileApi(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .client(initOkHttpClient())
                .baseUrl(baseUrl)
                .build();
    }

    public OkHttpClient initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.networkInterceptors().add(chain -> {
            Response originalResponse = chain.proceed(chain.request());
            return originalResponse
                    .newBuilder()
                    .body(new FileResponseBody(originalResponse))
                    .build();
        });
        return builder.build();
    }

}
