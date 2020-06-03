package com.teach.teach1907.interfaces;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * author : 芝龙      2020/6/3 17:10
 **/
public abstract class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onMyTextChanged(charSequence, i, i1, i2);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    public abstract void onMyTextChanged(CharSequence s, int start, int before, int count);
}
