package com.example.appfut03.Aplicativo;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appfut03.R;


public class TelaInicial extends AppCompatActivity {

    private TextView BemVindo;
    private Button btnCadastrar;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tela_inicial);

        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        BemVindo = (TextView) findViewById(R.id.txtBV);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaCadastro = new Intent(TelaInicial.this, TelaCadastro.class);
                startActivity(abreTelaCadastro);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abreTelaLogin = new Intent(TelaInicial.this, TelaLogin.class);
                startActivity(abreTelaLogin);
            }
        });

    }
}