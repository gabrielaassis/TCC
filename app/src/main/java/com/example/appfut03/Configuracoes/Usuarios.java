package com.example.appfut03.Configuracoes;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class Usuarios {

    private static int id=0;
    private static int id1=1;
    private String nome;
    private String email;
    private String senha;
    private String time;

    public Usuarios() {
    }

    public void salvar(){
        final DatabaseReference referenciaFirebase = Firebaseconfig.getFirebaseConfig();
        referenciaFirebase.child("Contador Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id1=(Integer.parseInt(String.valueOf(dataSnapshot.getValue())));
                referenciaFirebase.child("Contador Users1").setValue(id1);
                id = id1+1;
                referenciaFirebase.child("Contador Users").setValue(id);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        referenciaFirebase.child("Usuários").child(String.valueOf(id)).setValue(this);
        referenciaFirebase.child("TimesRegistrados").child(time).setValue(time);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("id",getId());
        hashMapUsuario.put("nome",getNome());
        hashMapUsuario.put("email",getEmail());
        hashMapUsuario.put("senha",getSenha());
        hashMapUsuario.put("time",getTime());
        return hashMapUsuario;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}