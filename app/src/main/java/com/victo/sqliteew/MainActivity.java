package com.victo.sqliteew;

import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.victo.sqliteew.databinding.ActivityMainBinding;
import com.victo.sqliteew.model.MyClass;
import com.victo.sqliteew.model.User;
import com.victo.sqliteew.sqlhelper.DataBaseSqliteHelper;
import com.victo.sqliteew.sqlhelper.SqliteHelper;

import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    SqliteHelper sh;
    int count = 0;
    Timer t;
    MyClass<User> m;
    ActivityMainBinding viewmain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewmain = DataBindingUtil.setContentView(this, R.layout.activity_main);

        m = new MyClass<User>();
        Realm.init(this);
        realm = Realm.getInstance(
                new RealmConfiguration.Builder().name("thanhRealm.realm")
                        .encryptionKey(Keyencrip())
                        .build());
        m.item = new User();

        m.item.setAge("12");
        m.item.setName("tuanas vu");
        m.item.setAddress("luowng bang quan hcm");
        m.item.setBirth("23-09-2019");
        m.item.setPhone("01567984654");

        viewmain.setUserVM(m.item);

        viewmain.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrecord(m.item);
            }
        });
        viewmain.Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(it);
            }
        });

        sh = new SqliteHelper(this);
        //sh.insertData();
        MyClass<ContentValues> sks = sh.QueryList(SqliteHelper.DBbaseTbl, null, null, null, null, null, null, null);

        for (ContentValues cv : sks.ListItem) {
            Log.e("<<LogUser", cv.getAsString("name") + " - " + cv.getAsString("phone"));
        }


    }

    public void OnclickLog(View view) {

        count++;

    }


    void addrecord(String name, String age, String birth, String address, String phone) {
        realm.beginTransaction();
        User newuser = realm.createObject(User.class);

        newuser.setAge(age);
        newuser.setName(name);
        newuser.setAddress(address);
        newuser.setBirth(birth);
        newuser.setPhone(phone);

        realm.commitTransaction();
    }

    void addrecord(final User vmObj) {
        vmObj.setName(vmObj.getName() + System.currentTimeMillis());
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(vmObj);
            }
        });
    }

    byte[] Keyencrip() {
        byte[] n;
        String d = "kdjfkdhfytryrtyrtyyrtyrtyrtyrtyryrtyrtyrtrtytrkdhfytryrtyrtyyrtyrtyrtyrtyryrtyrtyrtrty";
        n = Base64.decode(d, Base64.DEFAULT);
        return n;
    }

    void randomVM() {
        RealmResults<User> results = realm.where(User.class).findAll();
        for (User u : results)
            sh.insertData(u);
        //Log.d("<<Result", u.toString());
        t.cancel();
        t = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
