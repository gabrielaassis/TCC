package com.example.appfut03.Aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.Configuracoes.Usuarios;
import com.example.appfut03.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TelaLogin extends AppCompatActivity {

    private EditText editEmailLogin;
    private EditText editSenhaLogin;
    private Button btnEntrar;
    private FirebaseAuth autentic;
    private Usuarios usuarios;
    private TextView abreCadastro;
    private TextView recuperarSenha;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);

        editEmailLogin = (EditText) findViewById(R.id.editEmailLogin);
        editSenhaLogin = (EditText) findViewById(R.id.editSenhaLogin);
        btnEntrar = (Button) findViewById(R.id.btnLogin);
        abreCadastro = (TextView) findViewById(R.id.txtCadastrar);
        recuperarSenha = (TextView) findViewById(R.id.txtRecuperarSenha);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editEmailLogin.getText().toString().equals("") && !editSenhaLogin.getText().toString().equals("")) {

                    usuarios = new Usuarios();
                    usuarios.setEmail(editEmailLogin.getText().toString());
                    usuarios.setSenha(editSenhaLogin.getText().toString());
                    validarLogin();

                } else {
                    Toast.makeText(TelaLogin.this, "Preencha os campos de e-mail e senha", Toast.LENGTH_SHORT).show();
                }
            }
        });

        abreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });

        recuperarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreRecuperarSenha();
            }
        });

    }

        private void validarLogin () {

            autentic = Firebaseconfig.getFirebaseAutentic();
            autentic.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful()){
                        abrirTelaMenu();
                        Toast.makeText(TelaLogin.this, "Login efetuado com sucesso", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TelaLogin.this, "Usuário ou Senha inválidos", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void abrirTelaMenu(){
            Intent intentAbrirTelaMenu = new Intent(TelaLogin.this, TelaEmailConfirmacao.class);
            startActivity(intentAbrirTelaMenu);
            finish();
        }

        public void abreCadastroUsuario(){
            Intent intent = new Intent(TelaLogin.this, TelaCadastro.class);
            startActivity(intent);
            finish();
        }

        public  void abreRecuperarSenha(){
            Intent intent = new Intent(TelaLogin.this, TelaEsqueciMinhaSenha.class);
            startActivity(intent);
            finish();
        }
    }
