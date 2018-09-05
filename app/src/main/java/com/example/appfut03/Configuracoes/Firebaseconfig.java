package com.example.appfut03.Configuracoes;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Firebaseconfig {

    private static DatabaseReference refFirebase;
    private static FirebaseAuth autentic;

    public static DatabaseReference getFirebaseConfig(){
        if (refFirebase == null){
            refFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return refFirebase;
    }

    public static FirebaseAuth getFirebaseAutentic(){
        if (autentic == null){
        }
        autentic = FirebaseAuth.getInstance();
        return autentic;
    }
}
