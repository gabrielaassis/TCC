package com.example.appfut03.Aplicativo;

import android.content.Intent;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Base64Custom;
import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.Configuracoes.Usuarios;
import com.example.appfut03.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.net.PasswordAuthentication;

import javax.security.auth.callback.PasswordCallback;

public class TelaConta extends AppCompatActivity {

    private TextView txtTelaConta;
    private Button btnAtualizarEmail;
    private Button btnAtualizarSenha;
    private EditText edtEmailNovo;
    private EditText edtEmailSenha;
    private EditText edtSenhaAtualEmailNovo;
    private EditText edtSenhaAtual;
    private EditText edtNovaSenha;
    private EditText edtNovaSenhaConfirma;
    private FirebaseAuth autentic;
    private DatabaseReference fbconfig;
    private String message;
    private String message1;
    private String message2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conta);

        txtTelaConta = (TextView) findViewById(R.id.txtAtualizarDados);
        //btnAtualizarEmail = (Button) findViewById(R.id.btnAtualizarEmail);
        btnAtualizarSenha = (Button) findViewById(R.id.btnAtualizarSenha);
        //edtEmailNovo = (EditText) findViewById(R.id.editEmailAtualizar);
        //edtSenhaAtualEmailNovo = (EditText) findViewById(R.id.editSenhaAtualizarEmail);
        edtSenhaAtual = (EditText) findViewById(R.id.editSenhaAtualizar);
        edtNovaSenha = (EditText) findViewById(R.id.editSenhaAtualizarSenha);
        edtNovaSenhaConfirma = (EditText) findViewById(R.id.editSenhaAtualizarSenha1);
        edtEmailSenha = (EditText) findViewById(R.id.editEmailAtualizarSenha);

        fbconfig = Firebaseconfig.getFirebaseConfig();
        autentic = Firebaseconfig.getFirebaseAutentic();

       /* btnAtualizarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                final String novoEmail = edtEmailNovo.getText().toString();
                Query acharUsuario = fbconfig.child("Usuários").orderByChild("email").equalTo(user.getEmail());
                acharUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                            message1 = (String) messageSnapshot.child("senha").getValue();
                            message2 = (String) messageSnapshot.child("id").getValue().toString();
                            }
                            if(!edtEmailNovo.getText().toString().equals("")) {
                                final String identificadorUsuario = Base64Custom.codificarBase64(edtSenhaAtualEmailNovo.getText().toString());
                                if (identificadorUsuario.equals(message1)) {
                                    user.updateEmail(novoEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                fbconfig.child("Usuários").child(message2).child("email").setValue(novoEmail);
                                                Toast.makeText(TelaConta.this, "E-mail atualizado", Toast.LENGTH_SHORT).show();
                                                edtEmailNovo.setText("");
                                                edtSenhaAtualEmailNovo.setText("");
                                            } else {
                                                String erroExcecao = "";

                                                try {
                                                    throw task.getException();

                                                } catch (FirebaseAuthWeakPasswordException e) {
                                                    erroExcecao = "Digite uma senha contendo no minimo 6 caracteres de letras e/ou números";

                                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                                    erroExcecao = "\nO e-mail digitado é invalido, digite um novo e-mail";
                                                } catch (FirebaseAuthUserCollisionException e) {
                                                    erroExcecao = "\nEsse e-mail já está cadastrado no sistema";
                                                } catch (Exception e) {
                                                    erroExcecao = "\nErro ao efetuar o cadastro";
                                                    e.printStackTrace();
                                                }
                                                Toast.makeText(TelaConta.this, "Erro" + erroExcecao, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(TelaConta.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                                Toast.makeText(TelaConta.this, "Digite um E-mail valido", Toast.LENGTH_SHORT).show();
                            }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                });
            }
        });*/

        btnAtualizarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (!edtEmailSenha.getText().toString().equals("") && !edtSenhaAtual.getText().toString().equals("")) {
                    if (edtNovaSenha.getText().toString().equals(edtNovaSenhaConfirma.getText().toString())) {
                        if (user != null) {
                            final String newPassword = (edtNovaSenha.getText().toString());
                            AuthCredential credencial = EmailAuthProvider.getCredential(edtEmailSenha.getText().toString(), edtSenhaAtual.getText().toString());
                            user.reauthenticate(credencial).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful() && newPassword.length()>=6){
                                        user.updatePassword(newPassword);
                                        Toast.makeText(TelaConta.this, "Senha atualizada", Toast.LENGTH_SHORT).show();
                                        final Query acharUsuario = fbconfig.child("Usuários").orderByChild("email").equalTo(user.getEmail());
                                        acharUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                                                    message = (String) messageSnapshot.child("id").getValue().toString();
                                                }
                                                    String identificadorUsuario = Base64Custom.codificarBase64(newPassword);
                                                    fbconfig.child("Usuários").child(message).child("senha").setValue(identificadorUsuario);
                                                    abrirMenu();
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                            }
                                        });
                                    }else {
                                        Toast.makeText(TelaConta.this, "E-mail ou Senha Inválidos", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(TelaConta.this, "Sem Usuario", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TelaConta.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(TelaConta.this, "Preencha E-mail e Senha Atuais", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public void abrirMenu() {
        Intent intent = new Intent(TelaConta.this, TelaMenu.class);
        startActivity(intent);
        finish();
    }

}
