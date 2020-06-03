package com.example.gros.ui.descontar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DescontarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DescontarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Descontar");
    }

    public LiveData<String> getText() {
        return mText;
    }
}