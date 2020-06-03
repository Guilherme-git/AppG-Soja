package com.example.gros.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gros.R;
import com.example.gros.classes.Amostragem;

import java.util.List;

public class AdapterAmostra extends RecyclerView.Adapter<AdapterAmostra.MyViewHolder>{
    private List<Amostragem> listaAmostragem;

    public AdapterAmostra(List<Amostragem> lista){
        this.listaAmostragem = lista;
    }

    public void remover(int posisao){
        listaAmostragem.remove(posisao);
        notifyItemRemoved(posisao);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemAmostra = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_amostragem, parent, false);
        return new MyViewHolder(itemAmostra);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Amostragem amostragem = listaAmostragem.get(position);
        holder.adapterPesoAmostra.setText(amostragem.getPesoAmostragem()+"(g)");
        holder.adapterPlacaCaminhao.setText(amostragem.getPlacaCaminhaoAmostragem());
    }

    @Override
    public int getItemCount() {
        return this.listaAmostragem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView adapterPlacaCaminhao, adapterPesoAmostra;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            adapterPlacaCaminhao = itemView.findViewById(R.id.adapterPlacaCaminhao);
            adapterPesoAmostra = itemView.findViewById(R.id.adapterPesoAmostra);
        }
    }
}

