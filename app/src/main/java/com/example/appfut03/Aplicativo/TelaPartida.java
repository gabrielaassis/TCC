package com.example.appfut03.Aplicativo;

import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.Configuracoes.Partidas;
import com.example.appfut03.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TelaPartida extends AppCompatActivity {

    private TextView txtSuaEquipe;
    private EditText edtDataPartida,edtGolsSeutime,edtGolsTimeAdv;
    private FirebaseAuth autentic;
    private DatabaseReference fbconfig;
    private Spinner spinner;
    private int teste;
    //private String message;
    private Partidas partidas;
    private Button btnConfirmaPartida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_partida);

        txtSuaEquipe = (TextView) findViewById(R.id.txtSuaEquipe);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnConfirmaPartida = (Button) findViewById(R.id.btnConfirmaPartida);
        edtDataPartida = (EditText) findViewById(R.id.edtDataPartida);
        edtGolsSeutime = (EditText) findViewById(R.id.edtGolSeuTime);
        edtGolsTimeAdv = (EditText) findViewById(R.id.edtGolsTimeAdv);


        fbconfig = Firebaseconfig.getFirebaseConfig();
        autentic = Firebaseconfig.getFirebaseAutentic();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        List<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);


        final Query acharUsuario = fbconfig.child("Usuários").orderByChild("email").equalTo(user.getEmail());
        acharUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                   String message = (String) messageSnapshot.child("time").getValue().toString();
                    txtSuaEquipe.setText(message);
                    adapter.remove(message);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });








        fbconfig.child("TimesRegistrados").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string = dataSnapshot.getValue(String.class);
                if(string.equals("Visitante" )){

                }else {
                    adapter.add(string);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(TelaPartida.this, txtSuaEquipe.getText(), Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




btnConfirmaPartida.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        partidas = new Partidas();
        partidas.setNomeTime2(spinner.getSelectedItem().toString());
        partidas.setNomeTime1(txtSuaEquipe.getText().toString());
        partidas.setDataPartida(edtDataPartida.getText().toString());
        partidas.setGolsTime1(edtGolsSeutime.getText().toString());
        partidas.setGolsTime2(edtGolsTimeAdv.getText().toString());

        DatabaseReference referenciaFireBase = Firebaseconfig.getFirebaseConfig();
        referenciaFireBase.child("Contador Partida").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                teste = Integer.parseInt(String.valueOf(dataSnapshot.getValue()));
                partidas.setId(teste);
                partidas.salvar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
});

        }

    }

