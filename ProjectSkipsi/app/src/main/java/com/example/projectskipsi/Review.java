package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Review extends AppCompatActivity {

    Button btnplus,btnsubmit;
    ImageView picmobil,fotoprofile;
    EditText namamobil,review;
    TextView namapengguna,textspinner;
    Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storageReference;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userid;

//    String USERNAME_KEY = "usernamekey";
//    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        btnplus = findViewById(R.id.btn_add_photo);
        btnsubmit = findViewById(R.id.btnsubmit);
        picmobil = findViewById(R.id.picmobil);
        fotoprofile = findViewById(R.id.fotoprofile);
        namamobil = findViewById(R.id.namamobil);
        review = findViewById(R.id.hasilreview);
        namapengguna=findViewById(R.id.namapengguna);
        spinner = findViewById(R.id.spinner);
        textspinner = findViewById(R.id.textspinner);

        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPhoto();
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userid = auth.getCurrentUser().getUid();

        final StorageReference profileref = storageReference.child("user/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).centerCrop().fit().into(fotoprofile);
            }
        });

        DocumentReference documentReference = firestore.collection("user").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                namapengguna.setText(documentSnapshot.getString("Username"));
            }
        });

        adapter = ArrayAdapter.createFromResource(this,R.array.Daftar_Merek,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {
                textspinner.setText(adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                // menyimpan data kepada local storage (handphone)
//                SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString(username_key, namapengguna.getText().toString());
//                editor.apply();

                // menyimpan kepada firebase
                reference = FirebaseDatabase.getInstance().getReference()
                        .child("Review").child(namapengguna.getText().toString());
                storageReference = FirebaseStorage.getInstance().getReference().child("PhotoMobilUser").child(namapengguna.getText().toString());

                // validasi untuk file (apakah ada?)
                if (photo_location !=null){
                    final StorageReference storageReference1= storageReference.child(System.currentTimeMillis()+"."+
                            getFileExtension(photo_location));

                    storageReference1.putFile(photo_location).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String uri_photo = uri.toString();
                                    reference.getRef().child("photo_mobil").setValue(uri_photo);
                                    reference.getRef().child("username").setValue(namapengguna.getText().toString());
                                    reference.getRef().child("namamobil").setValue(namamobil.getText().toString());
                                    reference.getRef().child("review").setValue(review.getText().toString());
                                    reference.getRef().child("photo_user").setValue(profileref.toString());
                                }
                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    Intent conti= new Intent(Review.this,Home.class);
                                    Toast.makeText(getApplicationContext(),"Sukses Input Review",Toast.LENGTH_LONG).show();
                                    startActivity(conti);
                                }
                            });
                        }
                    });
                }
            }
        });




    }

    String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findPhoto(){
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == photo_max && resultCode == RESULT_OK && data != null && data.getData() != null)
        {

            photo_location = data.getData();
            Picasso.get().load(photo_location).centerCrop().fit().into(picmobil);

        }

    }
}
