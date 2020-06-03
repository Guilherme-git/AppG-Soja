package com.example.gros;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.gros.adapter.AdapterAmostra;
import com.example.gros.classes.API;
import com.example.gros.classes.Amostragem;
import com.example.gros.helpers.RecyclerItemClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Relatorio_Amostragem extends AppCompatActivity {


    RequestQueue request;
    RecyclerView recyclerAmostragem;
    AdapterAmostra adapterAmostra;
    List<Amostragem> listaAmostragem = new ArrayList<>();
    API api = new API();
    Amostragem amostragemSelecionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_amostragem);

        request = Volley.newRequestQueue(getApplicationContext());
        recyclerAmostragem = findViewById(R.id.recyclerAmostragem);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, api.URLlistarAmostrar(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i =0; i< response.length(); i++){
                    try {
                        Amostragem amostragem = new Amostragem();
                        JSONObject jsonObject = response.getJSONObject(i);
                        amostragem.setIdAmostragem(jsonObject.getInt("idAmostragem"));
                        amostragem.setPesoAmostragem(jsonObject.getString("pesoAmostragem"));
                        amostragem.setPlacaCaminhaoAmostragem(jsonObject.getString("placaCaminhaoAmostragem"));
                        listaAmostragem.add(amostragem);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapterAmostra = new AdapterAmostra(listaAmostragem);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerAmostragem.setLayoutManager(layoutManager);
                recyclerAmostragem.setHasFixedSize(true);
                recyclerAmostragem.setAdapter(adapterAmostra);

                recyclerAmostragem.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerAmostragem, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), Cadastrar_amostragem.class);
                        intent.putExtra("amostragemSelecionada", listaAmostragem.get(position));
                        startActivity(intent);

                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, final int position) {
                        amostragemSelecionada = listaAmostragem.get(position);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(Relatorio_Amostragem.this);
                        dialog.setTitle("Confirmar exclusão");
                        dialog.setMessage("Deseja realmente excluir ?");

                        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapterAmostra.remover(position);
                                Amostragem amostragemExcluir = amostragemSelecionada;
                                System.out.println("Respostaaaaaaaa "+amostragemExcluir.getIdAmostragem());
                                JsonObjectRequest jsonObjectRequestExcluir = new JsonObjectRequest(Request.Method.DELETE, api.URLexcluirAmostra(amostragemExcluir.getIdAmostragem()), null, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Removido", Toast.LENGTH_LONG);
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
