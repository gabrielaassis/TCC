package com.example.appfut03.Aplicativo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class TelaPartida extends AppCompatActivity {

    private TextView txtSuaEquipe;
    private FirebaseAuth autentic;
    private DatabaseReference fbconfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_partida);

        txtSuaEquipe = (TextView) findViewById(R.id.txtSuaEquipe);

        fbconfig = Firebaseconfig.getFirebaseConfig();
        autentic = Firebaseconfig.getFirebaseAutentic();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        final Query acharUsuario = fbconfig.child("Usuários").orderByChild("email").equalTo(user.getEmail());
        acharUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                   String message = (String) messageSnapshot.child("time").getValue().toString();
                    txtSuaEquipe.setText(message);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
