package com.victo.sqliteew.model;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {
    @PrimaryKey
    private String name;
    private int age;
    private String birth;
    private String phone;


    public User(){
    }

    @NonNull
    @Override
    public String toString() {

        StringBuilder sbuider = new StringBuilder();
        sbuider.append(name).append(" - ").append(age).append(" - ").append(birth).append(" - ").append(phone);
        return sbuider.toString();
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age+"";
    }

    public void setAge(String age) {
        this.age = Integer.parseInt(age);
    }
}
