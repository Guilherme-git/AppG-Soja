package com.example.gros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gros.classes.Amostragem;

import java.util.Locale;

public class Classificar_soja extends AppCompatActivity {

    private Button btnFazerDescontoClassificar, btnFazerClassificacao;
    private EditText classificarQueimados, classificarMofados, classificarGerminados,
            classificarFermentados, classificarArdidos, classificarDanificados,
            classificarImaturos, classificarChocos, classificarQuebradosEpartidos,
            classificarMassaAmostra, classificarImpureza, classificarUmidade, classificarEsverdeados;

    private TextView Resultado_classificarQueimados, Resultado_classificarMofados, Resultado_classificarGerminados,
            Resultado_classificarFermentados, Resultado_classificarArdidos, Resultado_classificarDanificados,
            Resultado_classificarImaturos, Resultado_classificarChocos, Resultado_classificarQuebradosEpartidos,
            resultadoClassificacao, Resultado_ClassificarImpureza, Resultado_classificarEsverdeados,
    erroMassaAmostra;



    Amostragem amostragemAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classificar_soja);

        btnFazerDescontoClassificar = findViewById(R.id.btnFazerDescontoClassificar);
        btnFazerClassificacao = findViewById(R.id.btnFazerClassificacao);
        classificarQueimados = findViewById(R.id.classificarQueimados);
        classificarMofados = findViewById(R.id.classificarMofados);
        classificarGerminados = findViewById(R.id.classificarGerminados);
        classificarFermentados = findViewById(R.id.classificarFermentados);
        classificarArdidos = findViewById(R.id.classificarArdidos);
        classificarDanificados = findViewById(R.id.classificarDanificados);
        classificarImaturos = findViewById(R.id.classificarImaturos);
        classificarChocos = findViewById(R.id.classificarChocos);
        classificarQuebradosEpartidos = findViewById(R.id.classificarQuebradosEPartidos);
        classificarMassaAmostra = findViewById(R.id.classificarMassaAmostra);
        classificarImpureza = findViewById(R.id.classificarImpureza);
        classificarUmidade = findViewById(R.id.classificarUmidade);
        classificarEsverdeados = findViewById(R.id.classificarEsverdeados);

        resultadoClassificacao = findViewById(R.id.resultadoClassificacao);

        Resultado_classificarQueimados = findViewById(R.id.resultadoClassificarQueimados);
        Resultado_classificarMofados = findViewById(R.id.resultadoClassificarMofados);
        Resultado_classificarGerminados = findViewById(R.id.resultadoClassificarGerminados);
        Resultado_classificarFermentados = findViewById(R.id.resultadoClassificarFermentados);
        Resultado_classificarArdidos = findViewById(R.id.resultadoClassificarArdidos);
        Resultado_classificarDanificados = findViewById(R.id.resultadoClassificarDanificados);
        Resultado_classificarImaturos = findViewById(R.id.resultadoClassificarImaturos);
        Resultado_classificarChocos = findViewById(R.id.resultadoClassificarChocos);
        Resultado_classificarQuebradosEpartidos = findViewById(R.id.resultadoClassificarQuebradosEPartidos);
        Resultado_ClassificarImpureza = findViewById(R.id.resultadoClassificarImpureza);
        Resultado_classificarEsverdeados = findViewById(R.id.resultadoClassificarEsverdeados);
        erroMassaAmostra = findViewById(R.id.erroMassaAmostra);

        amostragemAtual = (Amostragem) getIntent().getSerializableExtra("amostraSelecionadaClassificar");

        if(amostragemAtual != null){
            classificarMassaAmostra.setText(amostragemAtual.getPesoAmostragem());
        }

        btnFazerClassificacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (classificarMassaAmostra.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Informe o valor da massa da amostra", Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    toastView.setPadding(20,10,20,10);
                    toastView.setBackgroundColor(Color.parseColor("#FF0000"));
                    toast.show();
                } else {
                    double massaAmostra = Double.parseDouble(classificarMassaAmostra.getText().toString());

                    if(massaAmostra < 125) {
                        erroMassaAmostra.setText("É recomendado que o valor de amostra mínima seja maior que 125");
                        if (classificarQueimados.getText().toString().equals("")
                                || classificarMofados.getText().toString().equals("") || classificarGerminados.getText().toString().equals("")
                                || classificarFermentados.getText().toString().equals("") || classificarArdidos.getText().toString().equals("")
                                || classificarDanificados.getText().toString().equals("") || classificarImaturos.getText().toString().equals("")
                                || classificarChocos.getText().toString().equals("") || classificarQuebradosEpartidos.getText().toString().equals("") ||
                                classificarUmidade.getText().toString().equals("") || classificarImpureza.getText().toString().equals("") ||
                                classificarEsverdeados.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG);
                            View toastView = toast.getView();
                            toastView.setPadding(20,10,20,10);
                            toastView.setBackgroundColor(Color.parseColor("#FF0000"));
                            toast.show();
                        } else {

                            double impureza = Double.parseDouble(classificarImpureza.getText().toString());
                            final double resultadoImpureza = Double.valueOf(String.format(Locale.US, "%.2f",impureza/massaAmostra *100));

                            final double resultadoUmidade = Double.parseDouble(classificarUmidade.getText().toString());

                            double esverdeados = Double.parseDouble(classificarEsverdeados.getText().toString());
                            final double resultadoEsverdeados = Double.valueOf(String.format(Locale.US, "%.2f",esverdeados/massaAmostra *100));

                            double queimados = Double.parseDouble(classificarQueimados.getText().toString());
                            double resultadoQueimados = Double.valueOf(String.format(Locale.US,"%.2f",queimados / massaAmostra * 100))  ;


                            double mofados = Double.parseDouble(classificarMofados.getText().toString());
                            double resultadoMofados = Double.valueOf(String.format(Locale.US,"%.2f",mofados / massaAmostra * 100))  ;


                            double germinados = Double.parseDouble(classificarGerminados.getText().toString());
                            double resultadoGerminados = Double.valueOf(String.format(Locale.US,"%.2f",germinados / massaAmostra * 100))  ;


                            double fermentados = Double.parseDouble(classificarFermentados.getText().toString());
                            double resultadoFermentados = Double.valueOf(String.format(Locale.US,"%.2f",fermentados / massaAmostra * 100));


                            double ardidos = Double.parseDouble(classificarArdidos.getText().toString());
                            double resultadoArdidos = Double.valueOf(String.format(Locale.US,"%.2f",ardidos / massaAmostra * 100)) ;


                            double danificados = Double.parseDouble(classificarDanificados.getText().toString());
                            double resultadoDanificados = Double.valueOf(String.format(Locale.US,"%.2f",(danificados / massaAmostra * 100) / 4)) ;


                            double imaturos = Double.parseDouble(classificarImaturos.getText().toString());
                            double resultadoImaturos = Double.valueOf(String.format(Locale.US,"%.2f",imaturos / massaAmostra * 100)) ;


                            double chocos = Double.parseDouble(classificarChocos.getText().toString());
                            double resultadoChocos = Double.valueOf(String.format(Locale.US,"%.2f",chocos / massaAmostra * 100));

                            double quebradosEpartidos = Double.parseDouble(classificarQuebradosEpartidos.getText().toString());
                            final double resultadoQuebradosEpartidos = Double.valueOf(String.format(Locale.US,"%.2f", quebradosEpartidos / massaAmostra * 100));


                            Resultado_classificarQueimados.setText(String.valueOf(resultadoQueimados)+"%");
                            Resultado_classificarMofados.setText(String.valueOf(resultadoMofados)+"%");
                            Resultado_classificarGerminados.setText(String.valueOf(resultadoGerminados)+"%");
                            Resultado_classificarFermentados.setText(String.valueOf(resultadoFermentados)+"%");
                            Resultado_classificarArdidos.setText(String.valueOf(resultadoArdidos)+"%");
                            Resultado_classificarDanificados.setText(String.valueOf(resultadoDanificados)+"%");
                            Resultado_classificarImaturos.setText(String.valueOf(resultadoImaturos)+"%");
                            Resultado_classificarChocos.setText(String.valueOf(resultadoChocos)+"%");
                            Resultado_classificarQuebradosEpartidos.setText(String.valueOf(resultadoQuebradosEpartidos)+"%");
                            Resultado_ClassificarImpureza.setText(String.valueOf(resultadoImpureza)+"%");
                            Resultado_classificarEsverdeados.setText(String.valueOf(resultadoEsverdeados)+"%");

                            final double resultadoFinal = Double.valueOf(String.format(Locale.US,"%.2f", resultadoImaturos+resultadoQueimados+resultadoMofados+resultadoGerminados+resultadoFermentados+
                                    resultadoArdidos+resultadoDanificados+resultadoChocos));

                            Toast toast = Toast.makeText(getApplicationContext(), "Classificação realizada", Toast.LENGTH_LONG);
                            View toastView = toast.getView();
                            toastView.setPadding(20,10,20,10);
                            toastView.setBackgroundColor(Color.parseColor("#2D572C"));
                            toast.show();
                            resultadoClassificacao.setText("Total de avariados = "+String.valueOf(resultadoFinal)+"%");

                            btnFazerDescontoClassificar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getApplicationContext(), Descontos_soja.class);
                                    Bundle parametros = new Bundle();


                                    parametros.putString("total_avariados", String.valueOf(resultadoFinal));
                                    parametros.putString("Impureza", String.valueOf(resultadoImpureza));
                                    parametros.putString("Esverdeados", String.valueOf(resultadoEsverdeados));
                                    parametros.putString("Umidade", String.valueOf(resultadoUmidade));
                                    parametros.putString("Quebrados e partidos", String.valueOf(resultadoQuebradosEpartidos));
                                    intent.putExtras(parametros);

                                    intent.putExtra("amostraSelecionadaClassificar", amostragemAtual);

                                    startActivity(intent);
                                    finish();
                                }
                            });

                        }
                    }else {
                        erroMassaAmostra.setText("");

                        if (classificarQueimados.getText().toString().equals("")
                                || classificarMofados.getText().toString().equals("") || classificarGerminados.getText().toString().equals("")
                                || classificarFermentados.getText().toString().equals("") || classificarArdidos.getText().toString().equals("")
                                || classificarDanificados.getText().toString().equals("") || classificarImaturos.getText().toString().equals("")
                                || classificarChocos.getText().toString().equals("") || classificarQuebradosEpartidos.getText().toString().equals("") ||
                                classificarUmidade.getText().toString().equals("") || classificarImpureza.getText().toString().equals("") ||
                                classificarEsverdeados.getText().toString().equals("")) {
                            Toast toast = Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG);
                            View toastView = toast.getView();
                            toastView.setPadding(20,10,20,10);
                            toastView.setBackgroundColor(Color.parseColor("#FF0000"));
                            toast.show();
                        } else {

                            double impureza = Double.parseDouble(classificarImpureza.getText().toString());
                            final double resultadoImpureza = Double.valueOf(String.format(Locale.US, "%.2f",impureza/massaAmostra *100));

                            final double resultadoUmidade = Double.parseDouble(classificarUmidade.getText().toString());

                            double esverdeados = Double.parseDouble(classificarEsverdeados.getText().toString());
                            final double resultadoEsverdeados = Double.valueOf(String.format(Locale.US, "%.2f",esverdeados/massaAmostra *100));

                            double queimados = Double.parseDouble(classificarQueimados.getText().toString());
                            double resultadoQueimados = Double.valueOf(String.format(Locale.US,"%.2f",queimados / massaAmostra * 100))  ;


                            double mofados = Double.parseDouble(classificarMofados.getText().toString());
                            double resultadoMofados = Double.valueOf(String.format(Locale.US,"%.2f",mofados / massaAmostra * 100))  ;


                            double germinados = Double.parseDouble(classificarGerminados.getText().toString());
                            double resultadoGerminados = Double.valueOf(String.format(Locale.US,"%.2f",germinados / massaAmostra * 100))  ;


                            double fermentados = Double.parseDouble(classificarFermentados.getText().toString());
                            double resultadoFermentados = Double.valueOf(String.format(Locale.US,"%.2f",fermentados / massaAmostra * 100));


                            double ardidos = Double.parseDouble(classificarArdidos.getText().toString());
                            double resultadoArdidos = Double.valueOf(String.format(Locale.US,"%.2f",ardidos / massaAmostra * 100)) ;


                            double danificados = Double.parseDouble(classificarDanificados.getText().toString());
                            double resultadoDanificados = Double.valueOf(String.format(Locale.US,"%.2f",(danificados / massaAmostra * 100) / 4)) ;


                            double imaturos = Double.parseDouble(classificarImaturos.getText().toString());
                            double resultadoImaturos = Double.valueOf(String.format(Locale.US,"%.2f",imaturos / massaAmostra * 100)) ;


                            double chocos = Double.parseDouble(classificarChocos.getText().toString());
                            double resultadoChocos = Double.valueOf(String.format(Locale.US,"%.2f",chocos / massaAmostra * 100));

                            double quebradosEpartidos = Double.parseDouble(classificarQuebradosEpartidos.getText().toString());
                            final double resultadoQuebradosEpartidos = Double.valueOf(String.format(Locale.US,"%.2f", quebradosEpartidos / massaAmostra * 100));


                            Resultado_classificarQueimados.setText(String.valueOf(resultadoQueimados)+"%");
                            Resultado_classificarMofados.setText(String.valueOf(resultadoMofados)+"%");
                            Resultado_classificarGerminados.setText(String.valueOf(resultadoGerminados)+"%");
                            Resultado_classificarFermentados.setText(String.valueOf(resultadoFermentados)+"%");
                            Resultado_classificarArdidos.setText(String.valueOf(resultadoArdidos)+"%");
                            Resultado_classificarDanificados.setText(String.valueOf(resultadoDanificados)+"%");
                            Resultado_classificarImaturos.setText(String.valueOf(resultadoImaturos)+"%");
                            Resultado_classificarChocos.setText(String.valueOf(resultadoChocos)+"%");
                            Resultado_classificarQuebradosEpartidos.setText(String.valueOf(resultadoQuebradosEpartidos)+"%");
                            Resultado_ClassificarImpureza.setText(String.valueOf(resultadoImpureza)+"%");
                            Resultado_classificarEsverdeados.setText(String.valueOf(resultadoEsverdeados)+"%");

                            final double resultadoFinal = Double.valueOf(String.format(Locale.US,"%.2f", resultadoImaturos+resultadoQueimados+resultadoMofados+resultadoGerminados+resultadoFermentados+
                                    resultadoArdidos+resultadoDanificados+resultadoChocos));

                            Toast toast = Toast.makeText(getApplicationContext(), "Classificação realizada", Toast.LENGTH_LONG);
                            View toastView = toast.getView();
                            toastView.setPadding(20,10,20,10);
                            toastView.setBackgroundColor(Color.parseColor("#2D572C"));
                            toast.show();
                            resultadoClassificacao.setText("Total de avariados = "+String.valueOf(resultadoFinal)+"%");

                            btnFazerDescontoClassificar.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(getApplicationContext(), Descontos_soja.class);
                                    Bundle parametros = new Bundle();


                                    parametros.putString("total_avariados", String.valueOf(resultadoFinal));
                                    parametros.putString("Impureza", String.valueOf(resultadoImpureza));
                                    parametros.putString("Esverdeados", String.valueOf(resultadoEsverdeados));
                                    parametros.putString("Umidade", String.valueOf(resultadoUmidade));
                                    parametros.putString("Quebrados e partidos", String.valueOf(resultadoQuebradosEpartidos));
                                    intent.putExtras(parametros);

                                    intent.putExtra("amostraSelecionadaClassificar", amostragemAtual);

                                    startActivity(intent);
                                    finish();
                                }
                            });

                        }
                    }
                }


            }
        });

    }
}
