package com.example.appfut03.Configuracoes;

import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class Partidas {

    private static int id=0;
    private static int id1=1;
    private String nomeTime1;
    private String nomeTime2;
    private String golsTime1;
    private String golsTime2;
    private String juizPartida;
    private String dataPartida;

    public Partidas() {
    }

    public void salvar(){
        final DatabaseReference referenciaFirebase = Firebaseconfig.getFirebaseConfig();
        referenciaFirebase.child("Contador Partida").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                id1=(Integer.parseInt(String.valueOf(dataSnapshot.getValue())));
                referenciaFirebase.child("Contador Partida1").setValue(id1);
                id = id1+1;
                referenciaFirebase.child("Contador Partida").setValue(id);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        referenciaFirebase.child("Partida").child(String.valueOf(id)).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapPartida = new HashMap<>();

        hashMapPartida.put("id",getId());
        hashMapPartida.put("nome_time_1",getNomeTime1());
        hashMapPartida.put("nome_time_2",getNomeTime2());
        hashMapPartida.put("gols_time_1",getGolsTime1());
        hashMapPartida.put("gols_time_2",getGolsTime2());
       // hashMapPartida.put("juiz",getJuizPartida());
        hashMapPartida.put("data",getDataPartida());

        return hashMapPartida;
    }

    public String getDataPartida() {
        return dataPartida;
    }

    public void setDataPartida(String dataPartida) {
        this.dataPartida = dataPartida;
    }

    public String getJuizPartida() {
        return juizPartida;
    }

    public void setJuizPartida(String juizPartida) {
        this.juizPartida = juizPartida;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Partidas.id = id;
    }

    public static int getId1() {
        return id1;
    }

    public static void setId1(int id1) {
        Partidas.id1 = id1;
    }

    public String getNomeTime1() {
        return nomeTime1;
    }

    public void setNomeTime1(String nomeTime1) {
        this.nomeTime1 = nomeTime1;
    }

    public String getNomeTime2() {
        return nomeTime2;
    }

    public void setNomeTime2(String nomeTime2) {
        this.nomeTime2 = nomeTime2;
    }

    public String getGolsTime1() {
        return golsTime1;
    }

    public void setGolsTime1(String golsTime1) {
        this.golsTime1 = golsTime1;
    }

    public String getGolsTime2() {
        return golsTime2;
    }

    public void setGolsTime2(String golsTime2) {
        this.golsTime2 = golsTime2;
    }
}