package com.victo.sqliteew.model;

import android.content.ContentValues;
import android.databinding.BaseObservable;
import android.util.Property;

import com.victo.sqliteew.BR;

import java.util.ArrayList;
import java.util.List;

public class MyClass<T> extends BaseObservable {
    public List<T> ListItem;
    public T item;
    private T item1;

    public MyClass(){
        ListItem = new ArrayList<>();
    }

    public void setItem(ContentValues obj){

        notifyPropertyChanged(BR.userVM);
    }
}
