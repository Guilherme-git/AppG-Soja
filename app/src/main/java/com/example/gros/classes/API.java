package com.example.gros.classes;

public class API {
    private String url = "http://192.168.1.173:8080";

    public String URLsalvarDescontoSoja(){
        String url = this.url+"/classificacaosoja/salvar";
        return url;
    }

    public String URLeditarDescontoSoja(){
        String url = this.url+"/classificacaosoja/editar";
        return url;
    }

    public String URLexcluirDescontoSoja(int id){
        String url = this.url+"/classificacaosoja/excluir/"+id;
        return url;
    }

    public String URLlistarDescontoSoja(){
        String url = this.url+"/classificacaosoja/listar";
        return url;
    }

    public String URLsalvarAmostra(){
        String url = this.url+"/amostragem/salvar";
        return url;
    }

    public String URLlistarAmostrar(){
        String url = this.url+"/amostragem/mostrar";
        return url;
    }

    public String URLeditarAmostra(){
        String url = this.url+"/amostragem/editar";
        return url;
    }

    public String URLexcluirAmostra(int id){
        String url = this.url+"/amostragem/excluir/"+id;
        return url;
    }
}
