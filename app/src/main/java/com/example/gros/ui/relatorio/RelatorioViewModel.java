package com.example.gros.ui.relatorio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RelatorioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RelatorioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Relatório");
    }

    public LiveData<String> getText() {
        return mText;
    }
}