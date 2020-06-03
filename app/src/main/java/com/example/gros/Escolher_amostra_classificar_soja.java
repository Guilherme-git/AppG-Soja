package com.example.gros;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Escolher_amostra_classificar_soja extends AppCompatActivity {

    RecyclerView recyclerAmostraClassificar;
    RequestQueue request;
    AdapterAmostra adapterAmostra;
    List<Amostragem> listaAmostragem = new ArrayList<>();
    API api = new API();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolher_amostra_classificar_soja);

        request = Volley.newRequestQueue(getApplicationContext());
        recyclerAmostraClassificar = findViewById(R.id.recyclerAmostraClassificar);

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
                recyclerAmostraClassificar.setLayoutManager(layoutManager);
                recyclerAmostraClassificar.setHasFixedSize(true);
                recyclerAmostraClassificar.setAdapter(adapterAmostra);

                recyclerAmostraClassificar.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), recyclerAmostraClassificar, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Amostragem amostragemSelecionada = listaAmostragem.get(position);
                        Double valorPesoAmostra = Double.parseDouble(amostragemSelecionada.getPesoAmostragem());

                        if(valorPesoAmostra < 125){
                            Toast toast = Toast.makeText(getApplicationContext(), "É recomendado que o valor de amostra mínima seja maior que 125", Toast.LENGTH_LONG);
                            View toastView = toast.getView();
                            toastView.setPadding(20,10,20,10);
                            toastView.setBackgroundColor(Color.parseColor("#FF0000"));
                            toast.show();

                            Intent intent = new Intent(getApplicationContext(), Classificar_soja.class);
                            intent.putExtra("amostraSelecionadaClassificar", listaAmostragem.get(position));
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(getApplicationContext(), Classificar_soja.class);
                            intent.putExtra("amostraSelecionadaClassificar", listaAmostragem.get(position));
                            startActivity(intent);
                        }

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
