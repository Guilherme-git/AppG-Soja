package com.example.gros.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gros.R;
import com.example.gros.classes.ClassificacaoSoja;

import java.util.List;

public class AdapterRelatorioSoja extends RecyclerView.Adapter<AdapterRelatorioSoja.MyViewHolder> {

    private List<ClassificacaoSoja> listaClassificacaSoja;

    public AdapterRelatorioSoja(List<ClassificacaoSoja> lista){
        this.listaClassificacaSoja = lista;
    }

    public void remover(int posisao){
        listaClassificacaSoja.remove(posisao);
        notifyItemRemoved(posisao);
    }

    public void mudaLista(List<ClassificacaoSoja> lista){
        this.listaClassificacaSoja = lista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRelatorio = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_relatorio_soja, parent, false);
        return new MyViewHolder(itemRelatorio);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       ClassificacaoSoja classificacaoSoja = listaClassificacaSoja.get(position);
       holder.dataAtual.setText(classificacaoSoja.getDataAtualSoja());
       holder.placaAmostra.setText(classificacaoSoja.getAmostragem().getPlacaCaminhaoAmostragem());
       holder.pesoAmostra.setText(classificacaoSoja.getAmostragem().getPesoAmostragem()+"(g)");
       holder.umidade.setText(classificacaoSoja.getUmidadeSoja()+"%");
       holder.impureza.setText(classificacaoSoja.getImpurezaSoja()+"%");
       holder.esverdeados.setText(classificacaoSoja.getEsverdeadosSoja()+"%");
       holder.quebradosPartidosAmassados.setText(classificacaoSoja.getPartidosQuebradosAmassadosSoja()+"%");
       holder.avariados.setText(classificacaoSoja.getAvariadosSoja()+"%");
       holder.quantidadeInical.setText(classificacaoSoja.getQuantidadeGraosInicialSoja()+"Kg");
       holder.quantidadeDescontado.setText(classificacaoSoja.getQuantidadeGraosDescontadoSoja()+"Kg");
       holder.quantidadeFinal.setText(classificacaoSoja.getQuantidadeGraosFinalSoja()+"Kg");
    }

    @Override
    public int getItemCount() {
        return this.listaClassificacaSoja.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dataAtual, quantidadeInical, avariados, umidade, esverdeados, impureza, quebradosPartidosAmassados, quantidadeDescontado,
        quantidadeFinal, pesoAmostra, placaAmostra;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            dataAtual = itemView.findViewById(R.id.adapterAno);
            avariados = itemView.findViewById(R.id.adapterAvariados);
            umidade = itemView.findViewById(R.id.adapterUmidade);
            esverdeados = itemView.findViewById(R.id.adapterEsverdeados);
            impureza = itemView.findViewById(R.id.adapterImpureza);
            quebradosPartidosAmassados = itemView.findViewById(R.id.adapterQuebradosPartidosAmassados);
            quantidadeInical = itemView.findViewById(R.id.adapterQuantidadeInicial);
            quantidadeDescontado = itemView.findViewById(R.id.adapterQuantidadeDescontado);
            quantidadeFinal = itemView.findViewById(R.id.adapterQuantidadeFinal);
            pesoAmostra = itemView.findViewById(R.id.adapterAmostraClassificar);
            placaAmostra = itemView.findViewById(R.id.adapterPlacaClassificar);

        }
    }
}

