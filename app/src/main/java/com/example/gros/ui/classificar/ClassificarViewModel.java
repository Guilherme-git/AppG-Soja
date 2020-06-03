package com.example.gros.ui.classificar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ClassificarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ClassificarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Classificação");
    }

    public LiveData<String> getText() {
        return mText;
    }
}