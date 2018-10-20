package com.example.appfut03.Aplicativo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Firebaseconfig;
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

public class TelaAceitarPartida extends AppCompatActivity {

    private FirebaseAuth autentic;
    private TextView TxtView;
    private DatabaseReference fbconfig;
    private Spinner spinner;
    private Button confirmarPartidas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_aceitar_partida);
        confirmarPartidas = (Button) findViewById(R.id.buttonteste);
        TxtView = (TextView) findViewById(R.id.textView12312);
        spinner = (Spinner) findViewById(R.id.spinnerAceitar);
        fbconfig = Firebaseconfig.getFirebaseConfig();
        autentic = Firebaseconfig.getFirebaseAutentic();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        List<String> list = new ArrayList<String>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);

/*
        final Query listarTimes = fbconfig.child("Usuários").orderByChild("email").equalTo(user.getEmail());
        listarTimes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot messageSnapShot: dataSnapshot.getChildren()){
                    String times = (String) messageSnapShot.child("nome").getValue().toString();
                    adapter.add(times);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
*/


//------------------------------------------------------------- NÃO FUNCIONAL ------------- GUARDAODO PARA LÓGICA
        final Query listarTimesNaoConfirmados = fbconfig.child("Usuários").orderByChild("email").equalTo(user.getEmail());
        listarTimesNaoConfirmados.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    String message = (String) messageSnapshot.child("time").getValue().toString();

                    final Query acharPartida = fbconfig.child("Partida").orderByChild("nomeTime2").equalTo(message);
                    acharPartida.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            String confirmacaoTime2 = dataSnapshot.child("confirmacaoTime2").getValue().toString();
                            if (confirmacaoTime2.equals("false")) {

                            String string = dataSnapshot.child("id").getValue().toString();
                            adapter.add(string);
                            adapter.notifyDataSetChanged();
                            } else{
                                spinner.setVisibility(View.INVISIBLE);
                                TxtView.setVisibility(View.VISIBLE);
                                TxtView.setText("Não Há partidas para confirmar.");
                                confirmarPartidas.setVisibility(View.INVISIBLE);
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



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        confirmarPartidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String spinnerAtual = spinner.getSelectedItem().toString();
                fbconfig.child("Partida").child(spinnerAtual).child("confirmacaoTime2").setValue(true);
                Toast.makeText(getApplicationContext(), "Partida id: "+spinnerAtual+" confirmada com sucesso!" , Toast.LENGTH_SHORT).show();
                adapter.remove(spinnerAtual);

            }
        });






/*

        fbconfig.child("TimesRegistrados").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String string = dataSnapshot.getValue(String.class);
                if(string.equals("Visitante" )){

                }else {
                    adapter.add(string);
                    adapter.notifyDataSetChanged();
                    // Toast.makeText(TelaPartida.this, txtSuaEquipe.getText(), Toast.LENGTH_LONG).show();

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
        });*/



    }
}
