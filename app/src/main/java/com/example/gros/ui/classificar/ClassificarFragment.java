package com.example.gros.ui.classificar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gros.Escolher_amostra_classificar_soja;
import com.example.gros.R;

public class ClassificarFragment extends Fragment {

    private ClassificarViewModel classificarViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        classificarViewModel =
                ViewModelProviders.of(this).get(ClassificarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_classificar, container, false);

        Button btnSoja = root.findViewById(R.id.btnSoja);

        btnSoja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Escolher_amostra_classificar_soja.class);
                startActivity(intent);
            }
        });
        return root;
    }
}