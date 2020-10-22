package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class Login extends AppCompatActivity {
    public static final int GOOGLE_SIGN_IN_CODE=10005;
    TextInputEditText etemail,etPass;
    Button btnsignin;
    TextView btnforgetpass;
    FrameLayout btngoogle;

    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;

    ProgressBar load;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etemail = findViewById(R.id.etEmailSignIn);
        etPass = findViewById(R.id.etPasswordSignIn);
        btnsignin = findViewById(R.id.btnSignIn);
        btnforgetpass =  findViewById(R.id.btnForgetPass);
        load = findViewById(R.id.load);
        btngoogle = findViewById(R.id.btnGoogleSignIn);

        auth = FirebaseAuth.getInstance();

        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= etemail.getText().toString().trim();
                String pass= etPass.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    etemail.setError("Email Kosong");
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

                //autentic user

                auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this,"Succes Login",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }else {
                            Toast.makeText(Login.this,"Error, cek Email dan Password",Toast.LENGTH_SHORT).show();
                            load.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        btnforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail= new EditText(view.getContext());
                AlertDialog.Builder passreset = new AlertDialog.Builder(view.getContext());
                passreset.setTitle("Reset Password");
                passreset.setMessage("Masukan Email Untuk Reset");
                passreset.setView(resetMail);

                passreset.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link

                        String mail = resetMail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this,"Reset Link To Your Email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this,"Error ! Reset Link is Not Sent"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

                passreset.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Close the Dialog
                    }
                });
                passreset.create().show();
            }
        });

        //Login Akun GOOGLE
//        gso =new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("447803115834-ijs5r21dgp5ea2rffvu6itlu1777oekr.apps.googleusercontent.com")
//                .requestEmail()
//                .build();
//
//        signInClient = GoogleSignIn.getClient(this,gso);
//
//        GoogleSignInAccount signInAccount =GoogleSignIn.getLastSignedInAccount(this);
//        if (signInAccount!=null|| auth.getCurrentUser()!=null ){
//
//            startActivity(new Intent(this,Home.class));
//
//        }
//        btngoogle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent signgoogle=signInClient.getSignInIntent();
//                startActivityForResult(signgoogle,GOOGLE_SIGN_IN_CODE);
//            }
//        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode==GOOGLE_SIGN_IN_CODE){
//            Task<GoogleSignInAccount>signInAccountTask=GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                GoogleSignInAccount signInAccount=signInAccountTask.getResult(ApiException.class);
//
//                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAccount.getIdToken(),null);
//
//                auth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        Toast.makeText(getApplicationContext(),"Your Google Account is Connected",Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(getApplicationContext(),Home.class));
//                    }
//                });
//
//
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
