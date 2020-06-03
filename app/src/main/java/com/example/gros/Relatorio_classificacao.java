package com.example.gros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Relatorio_classificacao extends AppCompatActivity {

    Button btnRelatorio_Soja;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relatorio_classificacao);


        btnRelatorio_Soja = findViewById(R.id.btnRelatorio_Soja);
        btnRelatorio_Soja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Relatorio_soja.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
