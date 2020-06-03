package com.example.gros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gros.classes.API;
import com.example.gros.classes.Amostragem;
import com.example.gros.classes.ClassificacaoSoja;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Descontos_soja extends AppCompatActivity {

    RequestQueue request;
    EditText avariados,umidade,impureza,esverdeados, partidosEquebrados, quantidadeGraos;
    Button btnDescontar;
    API api = new API();
    Amostragem amostragemAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descontos_soja);

        request = Volley.newRequestQueue(this);

        avariados = findViewById(R.id.descontarAvariados);
        umidade = findViewById(R.id.descontarUmidade);
        impureza = findViewById(R.id.descontarImpurezas);
        esverdeados = findViewById(R.id.descontarEsverdeados);
        partidosEquebrados = findViewById(R.id.descontarPartidosEquebrados);
        quantidadeGraos = findViewById(R.id.descontarQuantidadeGraos);
        btnDescontar = findViewById(R.id.btnDescontar);

        amostragemAtual = (Amostragem) getIntent().getSerializableExtra("amostraSelecionadaClassificar");


        Intent total_variados = getIntent();
        Bundle parametros = total_variados.getExtras();

        avariados.setText(parametros.getString("total_avariados"));
        impureza.setText(parametros.getString("Impureza"));
        umidade.setText(parametros.getString("Umidade"));
        esverdeados.setText(parametros.getString("Esverdeados"));
        partidosEquebrados.setText(parametros.getString("Quebrados e partidos"));

        btnDescontar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantidadeGraos.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Informe a quantidade de grãos", Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    toastView.setPadding(20,10,20,10);
                    toastView.setBackgroundColor(Color.parseColor("#FF0000"));
                    toast.show();
                }else {
                    final double valorImpureza = Double.parseDouble(impureza.getText().toString());
                    final double valorUmidade = Double.parseDouble(umidade.getText().toString());
                    final double valorEsverdeados = Double.parseDouble(esverdeados.getText().toString());
                    final double valorAvariados = Double.parseDouble(avariados.getText().toString());
                    final double valorPartidosEquebrados = Double.parseDouble(partidosEquebrados.getText().toString());
                    final double valorQuantidadeGraos = Double.parseDouble(quantidadeGraos.getText().toString().replace(".",""));

                    double desconto_impureza = 0;
                    double desconto_umidade = 0;
                    double desconto_avariados = 0;
                    double desconto_esverdeados = 0;
                    double desconto_partidosEquebrados = 0;

                    if(valorImpureza > 1){
                        desconto_impureza = Double.parseDouble(String.format(Locale.US,"%.2f", valorQuantidadeGraos * ((valorImpureza-1) / (100-1))));

                    }

                    if(valorUmidade > 14) {
                        desconto_umidade = Double.parseDouble(String.format(Locale.US, "%.2f", (valorQuantidadeGraos - desconto_impureza) * ((valorUmidade - 14) / (100-14))));
                    }

                    if(valorAvariados > 8) {
                       desconto_avariados = valorQuantidadeGraos * ((valorAvariados - 8) / (100 - 8));
                    }

                    if(valorEsverdeados > 8){
                        desconto_esverdeados = valorQuantidadeGraos * ((valorEsverdeados - 8) / (100 - 8));
                    }

                    if(valorPartidosEquebrados > 30){
                        desconto_partidosEquebrados = valorQuantidadeGraos * ((valorPartidosEquebrados - 30) / (100-30));
                    }


                    final double total_desconto_defeitos = Double.parseDouble(String.format(Locale.US,"%.0f",desconto_avariados + desconto_esverdeados + desconto_partidosEquebrados + desconto_umidade + desconto_impureza));
                    final double desconto_final = Double.parseDouble(String.format(Locale.US,"%.0f",valorQuantidadeGraos - total_desconto_defeitos));


                    AlertDialog.Builder dialog = new AlertDialog.Builder(Descontos_soja.this);
                    dialog.setTitle("Resultado final");
                    dialog.setMessage("Quantidade de grãos inicial\n"+valorQuantidadeGraos+" Kg"+"\n\n"+
                            "Quantidade de grãos descontados\n"+total_desconto_defeitos+" Kg"+"\n\n"+
                            "Quantidade de grãos final\n"+desconto_final+" Kg");

                    dialog.setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ClassificacaoSoja classificacaoSoja = new ClassificacaoSoja();
                            Gson gson = new Gson();

                            SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
                            Date data = new Date();

                            classificacaoSoja.setUmidadeSoja(String.valueOf(valorUmidade));
                            classificacaoSoja.setImpurezaSoja(String.valueOf(valorImpureza));
                            classificacaoSoja.setEsverdeadosSoja(String.valueOf(valorEsverdeados));
                            classificacaoSoja.setPartidosQuebradosAmassadosSoja(String.valueOf(valorPartidosEquebrados));
                            classificacaoSoja.setAvariadosSoja(String.valueOf(valorAvariados));
                            classificacaoSoja.setQuantidadeGraosInicialSoja(String.valueOf(valorQuantidadeGraos));
                            classificacaoSoja.setQuantidadeGraosDescontadoSoja(String.valueOf(total_desconto_defeitos));
                            classificacaoSoja.setQuantidadeGraosFinalSoja(String.valueOf(desconto_final));
                            classificacaoSoja.setDataAtualSoja(String.valueOf(dataFormat.format(data)));
                            classificacaoSoja.setAmostragem(amostragemAtual);

                            final String json = gson.toJson(classificacaoSoja);

                            JSONObject objectJson = null;
                            try {
                                objectJson = new JSONObject(json);
                            }catch (JSONException e){
                                e.printStackTrace();
                            }

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, api.URLsalvarDescontoSoja(), objectJson, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Salvo com sucesso", Toast.LENGTH_LONG);
                                    View toastView = toast.getView();
                                    toastView.setPadding(20, 10, 20, 10);
                                    toastView.setBackgroundColor(Color.parseColor("#2D572C"));
                                    toast.show();
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            });

                           request.add(jsonObjectRequest);
                           finish();
                        }
                    });
                    dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_SHORT).show();
                        }
                    });
                    dialog.create();
                    dialog.show();
                }

            }
        });


    }
}
