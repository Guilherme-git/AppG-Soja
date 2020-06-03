package com.example.gros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.gros.adapter.AdapterRelatorioSoja;
import com.example.gros.classes.API;
import com.example.gros.classes.Amostragem;
import com.example.gros.classes.ClassificacaoSoja;
import com.example.gros.helpers.RecyclerItemClickListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Relatorio_soja extends AppCompatActivity {

    RequestQueue request;
    private RecyclerView recyclerView;
    AdapterRelatorioSoja adapterRelatorioSoja;
    final List<ClassificacaoSoja> listaClassificacaoSoja = new ArrayList<>();
    List<Amostragem> listaAmostragem = new ArrayList<>();
    ClassificacaoSoja classificacaoSojaSelecionada;
    API api = new API();
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_soja);

        request = Volley.newRequestQueue(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerRelatorio);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, api.URLlistarDescontoSoja(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        ClassificacaoSoja classificacaoSoja = new ClassificacaoSoja();
                        Amostragem amostragem = new Amostragem();
;
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject jsonAmostragem = jsonObject.getJSONObject("amostragem");

                        amostragem.setIdAmostragem(jsonAmostragem.getInt("idAmostragem"));
                        amostragem.setPlacaCaminhaoAmostragem(jsonAmostragem.getString("placaCaminhaoAmostragem"));
                        amostragem.setPesoAmostragem(jsonAmostragem.getString("pesoAmostragem"));

                        classificacaoSoja.setIdSoja(jsonObject.getInt("idSoja"));
                        classificacaoSoja.setDataAtualSoja(jsonObject.getString("dataAtualSoja"));
                        classificacaoSoja.setUmidadeSoja(jsonObject.getString("umidadeSoja"));
                        classificacaoSoja.setImpurezaSoja(jsonObject.getString("impurezaSoja"));
                        classificacaoSoja.setEsverdeadosSoja(jsonObject.getString("esverdeadosSoja"));
                        classificacaoSoja.setPartidosQuebradosAmassadosSoja(jsonObject.getString("partidosQuebradosAmassadosSoja"));
                        classificacaoSoja.setAvariadosSoja(jsonObject.getString("avariadosSoja"));
                        classificacaoSoja.setQuantidadeGraosInicialSoja(jsonObject.getString("quantidadeGraosInicialSoja"));
                        classificacaoSoja.setQuantidadeGraosDescontadoSoja(jsonObject.getString("quantidadeGraosDescontadoSoja"));
                        classificacaoSoja.setQuantidadeGraosFinalSoja(jsonObject.getString("quantidadeGraosFinalSoja"));
                        classificacaoSoja.setAmostragem(amostragem);

                        listaClassificacaoSoja.add(classificacaoSoja);

                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                //Configurar adapter
                adapterRelatorioSoja = new AdapterRelatorioSoja(listaClassificacaoSoja);


                //Configurar Recyclerview
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(adapterRelatorioSoja);

                recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Relatorio_soja.this);
                        dialog.setTitle("Escolha");
                        dialog.setMessage("Qual opção deseja alterar ?");
                        dialog.setPositiveButton("Amostra", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClassificacaoSoja classificacaoSoja = listaClassificacaoSoja.get(position);
                                Amostragem amostragem = new Amostragem();
                                amostragem.setIdAmostragem(classificacaoSoja.getAmostragem().getIdAmostragem());
                                amostragem.setPesoAmostragem(classificacaoSoja.getAmostragem().getPesoAmostragem());
                                amostragem.setPlacaCaminhaoAmostragem(classificacaoSoja.getAmostragem().getPlacaCaminhaoAmostragem());

                                Intent intent = new Intent(getApplicationContext(), Editar_amostragem_relatorio.class);
                                intent.putExtra("amostragemSelecionada", amostragem);
                                intent.putExtra("classificacaoSojaSelecionada", classificacaoSoja);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialog.setNegativeButton("Desconto", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ClassificacaoSoja classificacaoSoja = listaClassificacaoSoja.get(position);
                                Amostragem amostragem = new Amostragem();
                                amostragem.setIdAmostragem(classificacaoSoja.getAmostragem().getIdAmostragem());
                                amostragem.setPesoAmostragem(classificacaoSoja.getAmostragem().getPesoAmostragem());
                                amostragem.setPlacaCaminhaoAmostragem(classificacaoSoja.getAmostragem().getPlacaCaminhaoAmostragem());

                                Intent intent = new Intent(getApplicationContext(), Frag_descontar_soja.class);
                                intent.putExtra("relatorioSelecionado", listaClassificacaoSoja.get(position));
                                intent.putExtra("amostraSelecionada", amostragem);
                                startActivity(intent);
                                finish();
                            }
                        });
                        dialog.create();
                        dialog.show();
                    }

                    @Override
                    public void onLongItemClick(View view, final int position) {
                        classificacaoSojaSelecionada = listaClassificacaoSoja.get(position);
                        System.out.println("Resultadooo "+classificacaoSojaSelecionada.getIdSoja());

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Relatorio_soja.this);
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja realmente excluir ?");

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterRelatorioSoja.remover(position);
                                ClassificacaoSoja classificacaoSojaExcluir = classificacaoSojaSelecionada;

                                JsonObjectRequest jsonObjectRequestExcluir = new JsonObjectRequest(Request.Method.DELETE, api.URLexcluirDescontoSoja(classificacaoSojaExcluir.getIdSoja()), null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast toast = Toast.makeText(Relatorio_soja.this, "Removido", Toast.LENGTH_LONG);
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
                                request.add(jsonObjectRequestExcluir);

                            }
                        });
                        dialog.setNegativeButton("Não", null);
                        dialog.create();
                        dialog.show();
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonArrayRequest);
    }
}
