package com.example.appfut03.Aplicativo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class TelaEsqueciMinhaSenha extends AppCompatActivity {

    private Button btnEnviarEmailRecSenha;
    private EditText edtEmailRecSenha;
    private FirebaseAuth autentic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_esqueci_minha_senha);

        btnEnviarEmailRecSenha = (Button) findViewById(R.id.btnEnviarEmailRecSenha);
        edtEmailRecSenha = (EditText) findViewById(R.id.editEmailRecuperarSenha);
        autentic = Firebaseconfig.getFirebaseAutentic();

            btnEnviarEmailRecSenha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!edtEmailRecSenha.getText().toString().equals("")){
                        String userEmail = edtEmailRecSenha.getText().toString();
                        autentic.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()) {
                                    Toast.makeText(TelaEsqueciMinhaSenha.this, "E-mail de recuperação enviado", Toast.LENGTH_SHORT).show();
                                    abrirTelaInicial();
                                }else{
                                    String message = task.getException().getMessage();
                                    Toast.makeText(TelaEsqueciMinhaSenha.this, "Erro "+message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {
                        Toast.makeText(TelaEsqueciMinhaSenha.this, "Preencha o E-mail", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }

    public void abrirTelaInicial (){
        Intent abreTelaInicial = new Intent(TelaEsqueciMinhaSenha.this, TelaInicial.class);
        startActivity(abreTelaInicial);
        finish();
    }

}
