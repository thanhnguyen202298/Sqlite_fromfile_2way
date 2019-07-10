package com.victo.sqliteew;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.victo.sqliteew.AsyncJob.DownloadByOkhttp;

public class Main2Activity extends AppCompatActivity {

    ImageView getImgBtn;
    private final String URL = "http://i2.kym-cdn.com/photos/images/newsfeed/000/101/781/Y0UJC.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        getImgBtn = findViewById(R.id.imgshow);
        fetchByteFile(URL);
    }

    void fetchByteFile(String url){
        DownloadByOkhttp dlOk = new DownloadByOkhttp();
        try{
            byte[] arrByte =
            dlOk.execute(url).get();
            if(arrByte!=null & arrByte.length>0){
                Bitmap img = BitmapFactory.decodeByteArray(arrByte,0,arrByte.length);
                getImgBtn.setImageBitmap(img);
            }
        }catch (Exception e){

        }
    }
}
