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
import com.example.gros.adapter.AdapterAmostra;
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

public class Editar_amostragem_relatorio extends AppCompatActivity {

    RequestQueue request;
    RecyclerView recyclerAmostragemEditar;
    List<Amostragem> listaAmostragem = new ArrayList<>();
    AdapterAmostra adapterAmostra;
    EditText placaAtualEditar, pesoAtualEditar;
    Amostragem amostragemAtual;
    ClassificacaoSoja classificacaoSojaAtual;

    API api = new API();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_amostragem_relatorio);

        request = Volley.newRequestQueue(getApplicationContext());

        recyclerAmostragemEditar = findViewById(R.id.recyclerAmostragemEditar);
        placaAtualEditar = findViewById(R.id.placaAtualEditar);
        pesoAtualEditar = findViewById(R.id.pesoAtualEditar);

        amostragemAtual = (Amostragem) getIntent().getSerializableExtra("amostragemSelecionada");
        classificacaoSojaAtual = (ClassificacaoSoja) getIntent().getSerializableExtra("classificacaoSojaSelecionada");

        if(amostragemAtual != null){
            placaAtualEditar.setText(amostragemAtual.getPlacaCaminhaoAmostragem());
            pesoAtualEditar.setText(amostragemAtual.getPesoAmostragem());
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, api.URLlistarAmostrar(), null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0; i< response.length(); i++){
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
                recyclerAmostragemEditar.setLayoutManager(layoutManager);
                recyclerAmostragemEditar.setHasFixedSize(true);
                recyclerAmostragemEditar.setAdapter(adapterAmostra);

                recyclerAmostragemEditar.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerAmostragemEditar, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Amostragem amostragem = listaAmostragem.get(position);
                        classificacaoSojaAtual.setAmostragem(amostragem);
                        placaAtualEditar.setText(amostragem.getPlacaCaminhaoAmostragem());
                        pesoAtualEditar.setText(amostragem.getPesoAmostragem());

                        Gson gson = new Gson();
                        String json = gson.toJson(classificacaoSojaAtual);
                        JSONObject objectJson = null;
                        try{
                            objectJson = new JSONObject(json);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        System.out.println("Respotaaa "+objectJson.toString());
                        JsonObjectRequest jsonObjectRequestEditar = new JsonObjectRequest(Request.Method.PUT, api.URLeditarDescontoSoja(), objectJson, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                finish();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                        request.add(jsonObjectRequestEditar);
                        Toast toast = Toast.makeText(getApplicationContext(), "Editado com sucesso", Toast.LENGTH_LONG);
                        View toastView = toast.getView();
                        toastView.setPadding(20, 10, 20, 10);
                        toastView.setBackgroundColor(Color.parseColor("#2D572C"));
                        toast.show();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

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