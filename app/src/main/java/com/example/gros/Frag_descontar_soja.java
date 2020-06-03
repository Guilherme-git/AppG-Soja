package com.example.gros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gros.classes.API;
import com.example.gros.classes.Amostragem;
import com.example.gros.classes.ClassificacaoSoja;
import com.example.gros.ui.descontar.DescontarFragment;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Frag_descontar_soja extends AppCompatActivity {

    RequestQueue request;
    EditText avariados,umidade,impureza,esverdeados, partidosEquebrados, quantidadeGraos;
    Button btnDescontar;
    ClassificacaoSoja classificacaoSojaAtual = new ClassificacaoSoja();
    Amostragem amostragemAtual;
    List<Amostragem> listaAmostragem = new ArrayList<>();
    API api = new API();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag_descontar_soja);


        request = Volley.newRequestQueue(this);

        btnDescontar = findViewById(R.id.FRAGbtnDescontar);
        avariados = findViewById(R.id.FRAGdescontarAvariados);
        umidade = findViewById(R.id.FRAGdescontarUmidade);
        impureza = findViewById(R.id.FRAGdescontarImpurezas);
        esverdeados = findViewById(R.id.FRAGdescontarEsverdeados);
        partidosEquebrados = findViewById(R.id.FRAGdescontarPartidosEquebrados);
        quantidadeGraos = findViewById(R.id.FRAGDescontarQuantidadeGraos);


        classificacaoSojaAtual = (ClassificacaoSoja) getIntent().getSerializableExtra("relatorioSelecionado");
        amostragemAtual = (Amostragem) getIntent().getSerializableExtra("amostraSelecionada");

        if(classificacaoSojaAtual != null){
            DecimalFormat format = new DecimalFormat("0");
            double quantidade = Double.parseDouble(classificacaoSojaAtual.getQuantidadeGraosInicialSoja());
            quantidadeGraos.setText(format.format(quantidade));
            avariados.setText(classificacaoSojaAtual.getAvariadosSoja());
            umidade.setText(classificacaoSojaAtual.getUmidadeSoja());
            esverdeados.setText(classificacaoSojaAtual.getEsverdeadosSoja());
            impureza.setText(classificacaoSojaAtual.getImpurezaSoja());
            partidosEquebrados.setText(classificacaoSojaAtual.getPartidosQuebradosAmassadosSoja());

            btnDescontar.setText("Editar");
        }


        btnDescontar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantidadeGraos.getText().toString().equals("") || avariados.getText().toString().equals("") ||
                        umidade.getText().toString().equals("") || esverdeados.getText().toString().equals("") ||
                        impureza.getText().toString().equals("") || partidosEquebrados.getText().toString().equals("")){
                    Toast toast = Toast.makeText(Frag_descontar_soja.this, "Preencha todos os campos", Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    toastView.setPadding(20,10,20,10);
                    toastView.setBackgroundColor(Color.parseColor("#FF0000"));
                    toast.show();
                }else {
                    if(classificacaoSojaAtual == null){
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


                        AlertDialog.Builder dialog = new AlertDialog.Builder(Frag_descontar_soja.this);
                        dialog.setTitle("Resultado final");
                        dialog.setMessage("Quantidade de grãos inicial\n"+valorQuantidadeGraos+" Kg"+"\n\n"+
                                "Quantidade de grãos descontados\n"+total_desconto_defeitos+" Kg"+"\n\n"+
                                "Quantidade de grãos final\n"+desconto_final+" Kg");

                        dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Calculo realizado", Toast.LENGTH_LONG);
                                View toastView = toast.getView();
                                toastView.setPadding(20, 10, 20, 10);
                                toastView.setBackgroundColor(Color.parseColor("#2D572C"));
                                toast.show();
                            }
                        });
                        dialog.setNegativeButton("Sair", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        dialog.create();
                        dialog.show();
                    }else {
                        final double valorImpureza = Double.parseDouble(impureza.getText().toString());
                        final double valorUmidade = Double.parseDouble(umidade.getText().toString());
                        final double valorEsverdeados = Double.parseDouble(esverdeados.getText().toString());
                        final double valorAvariados = Double.parseDouble(avariados.getText().toString());
                        final double valorPartidosEquebrados = (Double.parseDouble(partidosEquebrados.getText().toString()));
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


                        AlertDialog.Builder dialog = new AlertDialog.Builder(Frag_descontar_soja.this);
                        dialog.setTitle("Resultado final");
                        dialog.setMessage("Quantidade de grãos inicial\n"+valorQuantidadeGraos+" Kg"+"\n\n"+
                                "Quantidade de grãos descontados\n"+total_desconto_defeitos+" Kg"+"\n\n"+
                                "Quantidade de grãos final\n"+desconto_final+" Kg");

                        dialog.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClassificacaoSoja classificacaoSoja = new ClassificacaoSoja();
                                Gson gson = new Gson();

                                classificacaoSoja.setIdSoja(classificacaoSojaAtual.getIdSoja());
                                classificacaoSoja.setUmidadeSoja(String.valueOf(valorUmidade));
                                classificacaoSoja.setImpurezaSoja(String.valueOf(valorImpureza));
                                classificacaoSoja.setEsverdeadosSoja(String.valueOf(valorEsverdeados));
                                classificacaoSoja.setPartidosQuebradosAmassadosSoja(String.valueOf(valorPartidosEquebrados));
                                classificacaoSoja.setAvariadosSoja(String.valueOf(valorAvariados));
                                classificacaoSoja.setQuantidadeGraosInicialSoja(String.valueOf(valorQuantidadeGraos));
                                classificacaoSoja.setQuantidadeGraosDescontadoSoja(String.valueOf(total_desconto_defeitos));
                                classificacaoSoja.setQuantidadeGraosFinalSoja(String.valueOf(desconto_final));
                                classificacaoSoja.setDataAtualSoja(classificacaoSojaAtual.getDataAtualSoja());
                                classificacaoSoja.setAmostragem(amostragemAtual);

                                final String json = gson.toJson(classificacaoSoja);

                                JSONObject objectJson = null;
                                try {
                                    objectJson = new JSONObject(json);
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                                System.out.println("Respostaaa "+objectJson.toString());
                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, api.URLeditarDescontoSoja(), objectJson, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                });

                                request.add(jsonObjectRequest);
                                Toast toast = Toast.makeText(getApplicationContext(), "Editado com sucesso", Toast.LENGTH_LONG);
                                View toastView = toast.getView();
                                toastView.setPadding(20, 10, 20, 10);
                                toastView.setBackgroundColor(Color.parseColor("#2D572C"));
                                toast.show();
                                Intent intent  = new Intent(getApplicationContext(), Relatorio_classificacao.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        dialog.create();
                        dialog.show();
                    }
                }
            }
        });

    }
}
