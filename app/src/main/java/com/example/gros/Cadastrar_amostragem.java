package com.example.gros;

import androidx.appcompat.app.AppCompatActivity;

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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

public class Cadastrar_amostragem extends AppCompatActivity {
    RequestQueue request;
    Button btnCadastrarAmostra;
    EditText placaCaminhao, pesoAmostra;
    Amostragem amostragem = new Amostragem();
    API api = new API();
    Amostragem amostragemAtual = new Amostragem();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_amostragem);

        request = Volley.newRequestQueue(this);

        placaCaminhao = findViewById(R.id.placaCaminhao);
        pesoAmostra = findViewById(R.id.pesoAmostra);
        btnCadastrarAmostra = findViewById(R.id.btnCadastrarAmostra);

        amostragemAtual = (Amostragem) getIntent().getSerializableExtra("amostragemSelecionada");

        if(amostragemAtual != null){
            pesoAmostra.setText(amostragemAtual.getPesoAmostragem());
            placaCaminhao.setText(amostragemAtual.getPlacaCaminhaoAmostragem());

            btnCadastrarAmostra.setText("Editar");
        }

        btnCadastrarAmostra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (placaCaminhao.getText().toString().equals("") || pesoAmostra.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "Preencha todos os campos", Toast.LENGTH_LONG);
                    View toastView = toast.getView();
                    toastView.setPadding(20,10,20,10);
                    toastView.setBackgroundColor(Color.parseColor("#FF0000"));
                    toast.show();
                }else {
                    if(amostragemAtual == null){
                        amostragem.setPesoAmostragem(pesoAmostra.getText().toString().replace(".",""));
                        amostragem.setPlacaCaminhaoAmostragem(placaCaminhao.getText().toString());

                        Gson gson = new Gson();

                        String json = gson.toJson(amostragem);
                        JSONObject objectJson = null;
                        try {
                            objectJson = new JSONObject(json);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, api.URLsalvarAmostra(), objectJson, new Response.Listener<JSONObject>() {
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
                        pesoAmostra.setText("");
                        placaCaminhao.setText("");
                    }else {
                        amostragem.setIdAmostragem(amostragemAtual.getIdAmostragem());
                        amostragem.setPesoAmostragem(pesoAmostra.getText().toString().replace(".",""));
                        amostragem.setPlacaCaminhaoAmostragem(placaCaminhao.getText().toString());

                        Gson gson = new Gson();

                        String json = gson.toJson(amostragem);
                        JSONObject objectJson = null;
                        try {
                            objectJson = new JSONObject(json);
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        System.out.println("Respostaaaaa "+objectJson.toString());
                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, api.URLeditarAmostra(), objectJson, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast toast = Toast.makeText(getApplicationContext(), "Editado com sucesso", Toast.LENGTH_LONG);
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

                }
            }
        });
    }
}
