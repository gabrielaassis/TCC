package com.example.appfut03.Aplicativo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TelaTimes extends AppCompatActivity {

    private ListView LVtimes1;
    private DatabaseReference fbconfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_times);

        LVtimes1 = (ListView) findViewById(R.id.LVtimes1);
        ArrayList<String> ltimes = preenchertimes();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ltimes);
        LVtimes1.setAdapter(arrayAdapter);
    }

    private ArrayList<String> preenchertimes() {
        final ArrayList<String> dados = new ArrayList<String>();
        fbconfig = Firebaseconfig.getFirebaseConfig();
        fbconfig.child("TimesRegistrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dados.add(dataSnapshot.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return dados;
    }

}
