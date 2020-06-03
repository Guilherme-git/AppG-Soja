package com.example.gros.classes;

import java.io.Serializable;


public class ClassificacaoSoja implements Serializable {
    private int idSoja;
    private String umidadeSoja;
    private String impurezaSoja;
    private String esverdeadosSoja;
    private String partidosQuebradosAmassadosSoja;
    private String avariadosSoja;
    private String quantidadeGraosInicialSoja;
    private String quantidadeGraosDescontadoSoja;
    private String quantidadeGraosFinalSoja;
    private String dataAtualSoja;
    private Amostragem amostragem;


    public int getIdSoja() {
        return idSoja;
    }

    public void setIdSoja(int idSoja) {
        this.idSoja = idSoja;
    }

    public String getUmidadeSoja() {
        return umidadeSoja;
    }

    public void setUmidadeSoja(String umidadeSoja) {
        this.umidadeSoja = umidadeSoja;
    }

    public String getImpurezaSoja() {
        return impurezaSoja;
    }

    public void setImpurezaSoja(String impurezaSoja) {
        this.impurezaSoja = impurezaSoja;
    }

    public String getEsverdeadosSoja() {
        return esverdeadosSoja;
    }

    public void setEsverdeadosSoja(String esverdeadosSoja) {
        this.esverdeadosSoja = esverdeadosSoja;
    }

    public String getPartidosQuebradosAmassadosSoja() {
        return partidosQuebradosAmassadosSoja;
    }

    public void setPartidosQuebradosAmassadosSoja(String partidosQuebradosAmassadosSoja) {
        this.partidosQuebradosAmassadosSoja = partidosQuebradosAmassadosSoja;
    }

    public String getAvariadosSoja() {
        return avariadosSoja;
    }

    public void setAvariadosSoja(String avariadosSoja) {
        this.avariadosSoja = avariadosSoja;
    }

    public String getQuantidadeGraosInicialSoja() {
        return quantidadeGraosInicialSoja;
    }

    public void setQuantidadeGraosInicialSoja(String quantidadeGraosInicialSoja) {
        this.quantidadeGraosInicialSoja = quantidadeGraosInicialSoja;
    }

    public String getQuantidadeGraosDescontadoSoja() {
        return quantidadeGraosDescontadoSoja;
    }

    public void setQuantidadeGraosDescontadoSoja(String quantidadeGraosDescontadoSoja) {
        this.quantidadeGraosDescontadoSoja = quantidadeGraosDescontadoSoja;
    }

    public String getQuantidadeGraosFinalSoja() {
        return quantidadeGraosFinalSoja;
    }

    public void setQuantidadeGraosFinalSoja(String quantidadeGraosFinalSoja) {
        this.quantidadeGraosFinalSoja = quantidadeGraosFinalSoja;
    }

    public String getDataAtualSoja() {
        return dataAtualSoja;
    }

    public void setDataAtualSoja(String dataAtualSoja) {
        this.dataAtualSoja = dataAtualSoja;
    }

    public Amostragem getAmostragem() {
        return amostragem;
    }

    public void setAmostragem(Amostragem amostragem) {
        this.amostragem = amostragem;
    }
}
