package com.victo.sqliteew.RemoteApi;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class HttpBuider {
    private static OkHttpClient client;

    public HttpBuider() {
        if (client == null)
            initOkHttpConnection();
    }

    public static OkHttpClient getClient() {
        if (client == null)
            initOkHttpConnection();
        return client;
    }

    static void initOkHttpConnection() {
        client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        HttpLoggingInterceptor hlogin = new HttpLoggingInterceptor();
        hlogin.level(HttpLoggingInterceptor.Level.BODY);

        client.interceptors().add(hlogin);
        client.interceptors().add(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request reqOrigin = chain.request();

                Headers headers = new Headers.Builder()
                        .add("Authorization", "auth-value")
                        .add("User-Agent", "app-name-value")
                        .build();

                //override request
                Request req = reqOrigin.newBuilder()
                        .headers(headers)//override all headers defined before
                        .build();

                Response res = chain.proceed(req);
                //overide response
                ResponseBody newbody = newBodyResponse(res);
                if (newbody != null)
                    return res.newBuilder()
                            .body(newbody)
                            .build();
                return res;

            }
        });
    }

    static ResponseBody newBodyResponse(Response res) {
        try {
            JSONObject jsonObject = new JSONObject();
            if (res.code() == 200) {
                jsonObject.put("code", 200);
                jsonObject.put("status", "OK");
                jsonObject.put("message", new JSONObject(res.body().toString()));

            } else {
                jsonObject.put("code", 404);
                jsonObject.put("status", "ERROR");
                jsonObject.put("message", new JSONObject(res.body().toString()));
            }
            MediaType contentType = res.body().contentType();
            ResponseBody body =
                    ResponseBody.create(jsonObject.toString(), contentType);
        } catch (Exception ex) {
            Log.e("<<HTTP BUIDEr", ex.getMessage());
        }

        return null;
    }

}
