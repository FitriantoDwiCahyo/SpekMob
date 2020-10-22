package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Editprofile extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    ImageView btnback,photoprofile;
    Button btnaddphoto,btnsave;
    EditText etusername,etemail,etphone,etbio;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    FirebaseUser user;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        btnback = findViewById(R.id.btnback);
        btnaddphoto = findViewById(R.id.btn_add_photo);
        btnsave = findViewById(R.id.btnsaveedit);

        etusername = findViewById(R.id.etUsername);
        etemail = findViewById(R.id.etEmail);
        etphone = findViewById(R.id.etphone);
        etbio = findViewById(R.id.etbio);

        photoprofile = findViewById(R.id.photo_profile);

        auth = FirebaseAuth.getInstance();
        firestore =FirebaseFirestore.getInstance();
        user=auth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        final StorageReference profileref = storageReference.child("user/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Picasso.get().load(uri).into(photoprofile);
            }
        });

        btnaddphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent opengallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(opengallery,1000);
            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etusername.getText().toString().isEmpty()||etemail.getText().toString().isEmpty()||etphone.getText().toString().isEmpty()||etbio.getText().toString().isEmpty()){
                    Toast.makeText(Editprofile.this,"Salah satu kolom kosong",Toast.LENGTH_SHORT).show();
                    return;
                }

                final String email=etemail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference= firestore.collection("user").document(user.getUid());
                        Map<String,Object>edit = new HashMap<>();
                        edit.put("Email",email);
                        edit.put("Username",etusername.getText().toString());
                        edit.put("Phone",etphone.getText().toString());
                        edit.put("Bio",etbio.getText().toString());
//                        edit.put("Foto",profileref);
                        documentReference.update(edit).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Editprofile.this,"Profile Berhasi Di Update",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),ProfileUsers.class));
                                finish();
                            }
                        });
                        Toast.makeText(Editprofile.this,"Data Berhasil Di Update",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Intent data = getIntent();
        String username= data.getStringExtra("Username");
        String email= data.getStringExtra("Email");
        String phone= data.getStringExtra("Phone");
        String bio= data.getStringExtra("Bio");

        etemail.setText(email);
        etphone.setText(phone);
        etusername.setText(username);
        etbio.setText(bio);

        Log.d(TAG,"onCreate"+ username+""+email+"");

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1000){
            if (resultCode == Activity.RESULT_OK){
                Uri imageuri = data.getData();
//                photo_profile.setImageURI(imageuri);

                uploadimagetofirebase(imageuri);

            }
        }
    }

    private void uploadimagetofirebase(Uri imageuri) {
        final StorageReference fileref= storageReference.child("user/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        fileref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(photoprofile);
                    }
                });
                Toast.makeText(Editprofile.this,"Gambar Terupload",Toast.LENGTH_SHORT).show();
                btnaddphoto.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Editprofile.this,"Upload Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
