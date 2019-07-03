package com.victo.sqliteew;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.victo.sqliteew.databinding.ActivityMainBinding;
import com.victo.sqliteew.model.User;
import com.victo.sqliteew.sqlhelper.DataBaseSqliteHelper;
import com.victo.sqliteew.sqlhelper.SqliteHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    User vmObj;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewmain = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Realm.init(this);
        realm = Realm.getInstance(
                new RealmConfiguration.Builder().name("thanhRealm.realm")
                        .encryptionKey(Keyencrip())
                        .build());
        vmObj = new User();

        vmObj.setAge("12");
        vmObj.setName("tuanas vu");
        vmObj.setAddress("luowng bang quan hcm");
        vmObj.setBirth("23-09-2019");
        vmObj.setPhone("01567984654");

        viewmain.setUserVM(vmObj);

        viewmain.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addrecord(vmObj);
            }
        });
        viewmain.Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomVM();
            }
        });

        SqliteHelper sh = new SqliteHelper(this);
        //sh.insertData();
        //sh.copyDataIn("myfile");
        sh.readAsset2SysApp(this);
        User m = sh.QueryDB();
        vmObj.setName(m.getName());
        vmObj.setPhone(m.getPhone());
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
            Log.d("<<Result", u.toString());

    }
}
