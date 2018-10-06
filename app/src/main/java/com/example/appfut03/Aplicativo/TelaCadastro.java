package com.example.appfut03.Aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Base64Custom;
import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.Configuracoes.Preferencias;
import com.example.appfut03.Configuracoes.Usuarios;
import com.example.appfut03.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TelaCadastro extends AppCompatActivity {

    private TextView Cadastro;
    private EditText editNomeCadastro, editEmailCadastro, editSenhaCadastro, editSenhaCadastro1, editTimeCadastro;
    private Button btnRegistrar;
    private CheckBox cbCadastro;
    private Usuarios usuarios;
    private FirebaseAuth autentic;
    private DatabaseReference fbconfig;
    private int teste;
    private String editTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);

        Cadastro = (TextView) findViewById(R.id.txtCadastro);
        cbCadastro = (CheckBox) findViewById(R.id.cbCadastro);
        editNomeCadastro = (EditText) findViewById(R.id.editNomeCadastro);
        editEmailCadastro = (EditText) findViewById(R.id.editEmailCadastro);
        editSenhaCadastro = (EditText) findViewById(R.id.editSenhaCadastro);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        editTimeCadastro = (EditText) findViewById(R.id.editTimeCadastro);
        editSenhaCadastro1 = (EditText) findViewById(R.id.editSenhaCadastro1);


        cbCadastro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                EditText campoOutroMaterial = findViewById(R.id.editTimeCadastro);

                if (cbCadastro.isChecked()) {
                    campoOutroMaterial.setVisibility(View.VISIBLE);

                } else {
                    campoOutroMaterial.setVisibility(View.GONE);
                    campoOutroMaterial.setText("");
                }
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editSenhaCadastro.getText().toString().equals(editSenhaCadastro1.getText().toString())){

                    usuarios = new Usuarios();
                    usuarios.setNome(editNomeCadastro.getText().toString());
                    usuarios.setEmail(editEmailCadastro.getText().toString());
                    usuarios.setSenha(editSenhaCadastro.getText().toString());
                    usuarios.setTime(editTimeCadastro.getText().toString());

                    cadastrarUsuario();

                }else{
                    Toast.makeText(TelaCadastro.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    private void cadastrarUsuario(){

        fbconfig = Firebaseconfig.getFirebaseConfig();
        autentic = Firebaseconfig.getFirebaseAutentic();


        fbconfig.child("TimesRegistrados").child(editTimeCadastro.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                editTime = editTimeCadastro.getText().toString();

                //Toast.makeText(TelaCadastro.this, dataSnapshot.toString(), Toast.LENGTH_LONG).show();
                if(dataSnapshot.getValue() == null || editTime.isEmpty() == true) {




                    autentic.createUserWithEmailAndPassword(
                            usuarios.getEmail(),
                            usuarios.getSenha()
                    ).addOnCompleteListener(TelaCadastro.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(TelaCadastro.this, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();

                                final String identificadorUsuario = Base64Custom.codificarBase64(usuarios.getSenha());

                                FirebaseUser usuarioFirebase = task.getResult().getUser();
                                DatabaseReference refenciaFirebase = Firebaseconfig.getFirebaseConfig();
                                refenciaFirebase.child("Contador Users").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        teste = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                                        usuarios.setId(teste);
                                        usuarios.setSenha(identificadorUsuario);
                                        usuarios.salvar();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                Preferencias preferencias = new Preferencias(TelaCadastro.this);
                                preferencias.salvarUsuarioPreferences(identificadorUsuario, usuarios.getNome());
                                abrirLoginUsuario();


                            }else{
                                String erroExcecao ="";

                                try{
                                    throw task.getException();

                                }catch (FirebaseAuthWeakPasswordException e){
                                    erroExcecao = "Digite uma senha mais forte, contendo no minimo 6 caracteres de letras e/ou números";

                                }catch (FirebaseAuthInvalidCredentialsException e){
                                    erroExcecao = "\nO e-mail digitado é invalido, digite um novo e-mail";
                                }catch (FirebaseAuthUserCollisionException e) {
                                    erroExcecao = "\nEsse e-mail já está cadastrado no sistema";
                                }catch (Exception e){
                                    erroExcecao = "\nErro ao efetuar o cadastro";
                                    e.printStackTrace();
                                }
                                Toast.makeText(TelaCadastro.this, "Erro" + erroExcecao, Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                } else {
                    //editEmailCadastro.setText(dataSnapshot.toString());
                    editNomeCadastro.setText(editTimeCadastro.getText().toString());
                    Toast.makeText(TelaCadastro.this, "Time Ja Existente", Toast.LENGTH_LONG).show();
                    //editTimeCadastro.setText(editEmail);
                    //editTimeCadastro.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(TelaCadastro.this, TelaLogin.class);
        startActivity(intent);
        finish();
    }
}