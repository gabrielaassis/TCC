package com.example.appfut03.Aplicativo;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfut03.Configuracoes.Base64Custom;
import com.example.appfut03.Configuracoes.Firebaseconfig;
import com.example.appfut03.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class TelaHistorico extends AppCompatActivity {

    private TextView txtVitorias, txtDerrotas,txtEmpates, txtHistorico,txtNomeTime;
    private FirebaseAuth autentic;
    private ProgressBar loading;
    private String vitorias,derrotas,empates,time;
    private DatabaseReference fbconfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_historico);

        txtVitorias = (TextView) findViewById(R.id.txtVitorias);
        txtDerrotas = (TextView)findViewById(R.id.txtDerrotas);
        txtEmpates = (TextView)findViewById(R.id.txtEmpates);
        txtHistorico = (TextView)findViewById(R.id.txtHistorico);
        loading = (ProgressBar)findViewById(R.id.BolinhaLoad);
        txtNomeTime = (TextView)findViewById(R.id.txtNomeTime);

        txtHistorico.setVisibility(View.INVISIBLE);
        txtDerrotas.setVisibility(View.INVISIBLE);
        txtEmpates.setVisibility(View.INVISIBLE);
        txtVitorias.setVisibility(View.INVISIBLE);
        txtNomeTime.setVisibility(View.INVISIBLE);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        fbconfig = Firebaseconfig.getFirebaseConfig();
        autentic = Firebaseconfig.getFirebaseAutentic();

        //fbconfig.child("TimesRegistrados").child(editTimeCadastro.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {


         Query acharUsuario = fbconfig.child("Usuários").orderByChild("email").equalTo(user.getEmail());
        acharUsuario.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    vitorias =  messageSnapshot.child("vitorias").getValue().toString();
                    derrotas =  messageSnapshot.child("derrotas").getValue().toString();
                    empates =  messageSnapshot.child("empates").getValue().toString();
                    time = messageSnapshot.child("time").getValue().toString();
                }

                //fbconfig.child("Usuários").child(message).child("senha").setValue(identificadorUsuario);
                txtVitorias.setText("Vitórias: "+ vitorias);
                txtDerrotas.setText("Derrotas: "+ derrotas);
                txtEmpates.setText("Empates: "+ empates);
                txtNomeTime.setText(time);
                txtHistorico.setVisibility(View.VISIBLE);
                txtDerrotas.setVisibility(View.VISIBLE);
                txtEmpates.setVisibility(View.VISIBLE);
                txtNomeTime.setVisibility(View.VISIBLE);
                txtVitorias.setVisibility(View.VISIBLE);
                loading.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




    }
}
