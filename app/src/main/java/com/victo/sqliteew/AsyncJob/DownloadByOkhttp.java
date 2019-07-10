package com.victo.sqliteew.AsyncJob;

import android.content.Context;
import android.os.AsyncTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadByOkhttp extends AsyncTask<String,Void, byte[]> {
    OkHttpClient clien = new OkHttpClient();
    Context context;

    @Override
    protected byte[] doInBackground(String[] objects) {

        Request.Builder builder = new Request.Builder().url(objects[0]);
        Request request = builder.build();

        try{
            Response response = clien.newCall(request).execute();
            return response.body().bytes();
        }catch (Exception ex){

        }

        return null;
    }
}
