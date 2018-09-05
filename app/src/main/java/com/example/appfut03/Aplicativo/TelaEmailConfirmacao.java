package com.example.appfut03.Aplicativo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Timer;
import java.util.TimerTask;

public class TelaEmailConfirmacao extends AppCompatActivity {

    private TextView txtVerificar;
    private Button btnVerificar;
    private FirebaseAuth autentic;
    //private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_email_confirmacao);
        txtVerificar = (TextView) findViewById(R.id.txtVerificar);
        btnVerificar = (Button) findViewById(R.id.btnVerificar);
        autentic = Firebaseconfig.getFirebaseAutentic();


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if(user.isEmailVerified()){
                Intent intent32312 = new Intent(this, TelaMenu.class);
                startActivity(intent32312);
                finish();
            } else{
                //txtVerificar.setText("Email não Verificado");
                btnVerificar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(TelaEmailConfirmacao.this, "Email de Verificação Enviado.", Toast.LENGTH_SHORT).show();
                            }
                        });

                        Intent intent = new Intent(TelaEmailConfirmacao.this, TelaLogin.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        } else{
            Intent intent = new Intent(TelaEmailConfirmacao.this, TelaLogin.class);
            startActivity(intent);
            finish();
        }
    }
}