package com.example.gros.classes;

import java.io.Serializable;

public class Amostragem implements Serializable {
    private int idAmostragem;
    private String pesoAmostragem;
    private String placaCaminhaoAmostragem;

    public int getIdAmostragem() {
        return idAmostragem;
    }

    public void setIdAmostragem(int idAmostragem) {
        this.idAmostragem = idAmostragem;
    }

    public String getPesoAmostragem() {
        return pesoAmostragem;
    }

    public void setPesoAmostragem(String pesoAmostragem) {
        this.pesoAmostragem = pesoAmostragem;
    }

    public String toString() {
        return "Placa - "+placaCaminhaoAmostragem+" / "+" Peso - "+pesoAmostragem;
    }

    public String getPlacaCaminhaoAmostragem() {
        return placaCaminhaoAmostragem;
    }

    public void setPlacaCaminhaoAmostragem(String placaCaminhaoAmostragem) {
        this.placaCaminhaoAmostragem = placaCaminhaoAmostragem;
    }
}
