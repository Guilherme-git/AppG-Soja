package com.example.gros.ui.relatorio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.gros.R;
import com.example.gros.Relatorio_Amostragem;
import com.example.gros.Relatorio_classificacao;

public class RelatorioFragment extends Fragment {

    private RelatorioViewModel relatorioViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        relatorioViewModel =
                ViewModelProviders.of(this).get(RelatorioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_relatorio, container, false);

        Button btnFRAG_Relatorio_Classificacao = root.findViewById(R.id.btnFRAG_Relatorio_Classificacao);
        Button btnFRAG_Relatorio_Amostragem = root.findViewById(R.id.btnFRAG_Relatorio_Amostragem);

        btnFRAG_Relatorio_Classificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Relatorio_classificacao.class);
                startActivity(intent);
            }
        });

        btnFRAG_Relatorio_Amostragem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Relatorio_Amostragem.class);
                startActivity(intent);
            }
        });


        return root;
    }

}