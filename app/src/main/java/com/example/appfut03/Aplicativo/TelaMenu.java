package com.example.appfut03.Aplicativo;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.appfut03.R;

import java.io.BufferedReader;


public class TelaMenu extends AppCompatActivity {

    private Button btnCriarCampeonato;
    private Button btnCriarPartida;
    private Button btnHistoricoTime;
    private Button btnListaTimes;
    private Button btnRegras;
    private Button btnConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_menu);

        btnCriarCampeonato = (Button) findViewById(R.id.btnCriarCampeonato);
        btnCriarPartida = (Button) findViewById(R.id.btnCriarPartida);
        btnHistoricoTime = (Button) findViewById(R.id.btnHistorico);
        btnListaTimes = (Button) findViewById(R.id.btnListaTimes);
        btnRegras = (Button) findViewById(R.id.btnRegras);
        btnConta = (Button) findViewById(R.id.btnConta);

        btnCriarCampeonato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaCampeonato = new Intent(TelaMenu.this, TelaCampeonato.class);
                startActivity(abreTelaCampeonato);
            }
        });

        btnCriarPartida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaPartida = new Intent(TelaMenu.this, TelaPartida.class);
                startActivity(abreTelaPartida);
            }
        });

        btnHistoricoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaHistorico = new Intent(TelaMenu.this, TelaHistorico.class);
                startActivity(abreTelaHistorico);
            }
        });

        btnListaTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaTimes = new Intent(TelaMenu.this, TelaTimes.class);
                startActivity(abreTelaTimes);
            }
        });

        btnRegras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String endereco = "http://www.cbfm.com.br/CBFM_Regras.php";
                Uri uri = Uri.parse(endereco);

                Intent regrr = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(regrr);
            }
        });


        btnConta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaConta = new Intent(TelaMenu.this, TelaConta.class);
                startActivity(abreTelaConta);
            }
        });
    }
}
