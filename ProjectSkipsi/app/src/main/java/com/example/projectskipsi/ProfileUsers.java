package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;

public class ProfileUsers extends AppCompatActivity {


    TextView txtemail,txtusername,txtphone,txtverif;
    Button edtprofile,resetpass,btnaddphoto;
    ImageView photo_profile;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userid;

    StorageReference storageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_users);

        txtemail = findViewById(R.id.txtemail);
        txtusername = findViewById(R.id.txtusername);
        txtphone = findViewById(R.id.txtphone);
        txtverif = findViewById(R.id.txtverifikasi);

        edtprofile = findViewById(R.id.btn_editprofile);
        photo_profile = findViewById(R.id.photo_profile);
//        btnaddphoto = findViewById(R.id.btn_add_photo);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        final FirebaseUser user=auth.getCurrentUser();

        if (!user.isEmailVerified()){
            txtverif.setVisibility(View.VISIBLE);

            txtverif.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileUsers.this,"Verikasi Email Terikirim",Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("tag","Email Tidak Terkirim");
                        }
                    });

                }
            });
        }

        StorageReference profileref = storageReference.child("user/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(photo_profile);
            }
        });

        userid = auth.getCurrentUser().getUid();

        DocumentReference documentReference = firestore.collection("user").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                txtusername.setText(documentSnapshot.getString("Username"));
                txtemail.setText(documentSnapshot.getString("Email"));
                txtphone.setText(documentSnapshot.getString("Phone"));
            }
        });



//        btnaddphoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent opengallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(opengallery,1000);
//            }
//        });

        edtprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent edit= new Intent(ProfileUsers.this,Editprofile.class);
                edit.putExtra("Username",txtusername.getText().toString());
                edit.putExtra("Email",txtemail.getText().toString());
                edit.putExtra("Phone",txtphone.getText().toString());
                startActivity(edit);
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

                        Picasso.get().load(uri).into(photo_profile);
                    }
                });
                Toast.makeText(ProfileUsers.this,"Gambar Terupload",Toast.LENGTH_SHORT).show();
                btnaddphoto.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ProfileUsers.this,"Upload Gagal",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}
