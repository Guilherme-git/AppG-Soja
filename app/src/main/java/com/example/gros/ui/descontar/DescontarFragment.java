package com.example.gros.ui.descontar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gros.Frag_descontar_soja;
import com.example.gros.R;

import java.util.Locale;

public class DescontarFragment extends Fragment {

    private DescontarViewModel descontarViewModel;


    Button btnSojaDescontar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        descontarViewModel =
                ViewModelProviders.of(this).get(DescontarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_descontar, container, false);

        btnSojaDescontar = root.findViewById(R.id.btnSojaDescontar);


        btnSojaDescontar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Frag_descontar_soja.class);
                startActivity(intent);
            }
        });

        return root;
    }


}