package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    public static final String TAG ="TAG";
    EditText etUsername,etEmail,etPass,etconfirmpass;
    Button btnsignup;
    ImageView btnback;
    FirebaseAuth auth;
    FirebaseFirestore firestore;
    ProgressBar load;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmailSignUp);
        etPass = findViewById(R.id.etPasswordSignUp);
        etconfirmpass = findViewById(R.id.etConfirmPasswordSignUp);
        load = findViewById(R.id.load);
        btnsignup = findViewById(R.id.btnSignUp);
//        btnback = findViewById(R.id.back);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        load = findViewById(R.id.load);


        if (auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),ProfileUsers.class));
            finish();
        }

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email= etEmail.getText().toString().trim();
                String pass= etPass.getText().toString().trim();
                final String username= etUsername.getText().toString();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Email Kosong");
                    return;
                }

                if (TextUtils.isEmpty(pass)){
                    etPass.setError("Password Kosong");
                    return;
                }

                if (pass.length()<6){
                    etPass.setError("Password Harus Lebih Dari 6 character");
                    return;
                }

                load.setVisibility(View.VISIBLE);

                //Sign-up the user in firebase

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()){

                           //send verification link

                           FirebaseUser fuser = auth.getCurrentUser();
                           fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this,"Verikasi Email Terikirim",Toast.LENGTH_SHORT).show();
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"Email Tidak Terkirim");
                               }
                           });



                           Toast.makeText(Register.this,"Sucses",Toast.LENGTH_SHORT).show();
                           userid= auth.getCurrentUser().getUid();
                           DocumentReference documentReference = firestore.collection("user").document(userid);
                           Map<String,Object>user = new HashMap<>();
                           user.put("Username",username);
                           user.put("Email",email);
                           documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Log.d(TAG,"Profile User Succes Dibuat"+userid);
                               }
                           }).addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"onFailure"+e.toString());
                               }
                           });
                           startActivity(new Intent(getApplicationContext(),ProfileUsers.class));
                       }else {
                           Toast.makeText(Register.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                           load.setVisibility(View.GONE);
                       }
                    }
                });
            }
        });
    }
}
