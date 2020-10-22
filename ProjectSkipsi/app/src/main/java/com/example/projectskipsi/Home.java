package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
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

public class Home extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    TextView username,bio;
    ImageView btnmore,btntoyota,btnmazda,btnmithsubishi,btndaihatusu,btnsuzuki,btnnissan
            ,btnmercy,btnbmw,btnkia,btnhyundai,btnmini,btnlexus,btnvw;
    ImageView Photoprofile;
    LinearLayout btnsedan,btnlcgc,btnhatchback,btnmpv,btnsuv;
    TextView btnlihatsemua;
    LinearLayout btnreview,btnreview2,btnreview3,btnreview4;

    FirebaseAuth auth;
    FirebaseFirestore firestore;
    String userid;

    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = findViewById(R.id.username);
        bio = findViewById(R.id.bio);
        Photoprofile = findViewById(R.id.photo_profile);

        btnbmw = findViewById(R.id.btnbmw);
        btndaihatusu = findViewById(R.id.btndaihatsu);
        btnhyundai = findViewById(R.id.btnhyundai);
        btnkia = findViewById(R.id.btnkia);
        btnlexus = findViewById(R.id.btnlexus);
        btnmazda = findViewById(R.id.btnmazda);
        btnmercy = findViewById(R.id.btnmazda);
        btnmini = findViewById(R.id.btnmini);
        btntoyota = findViewById(R.id.btntoyota);
        btnnissan = findViewById(R.id.btnnissan);
        btnsuzuki = findViewById(R.id.btnsuzuki);
        btnvw = findViewById(R.id.btnvw);
        btnmithsubishi = findViewById(R.id.btnmitshubishi);
        btnmore = findViewById(R.id.btnmore);

        btnsedan = findViewById(R.id.btnsedan);
        btnlcgc = findViewById(R.id.btnlcgc);
        btnsuv = findViewById(R.id.btnsuv);
        btnmpv = findViewById(R.id.btnmpv);
        btnhatchback = findViewById(R.id.btnhatchback);
        btnlihatsemua = findViewById(R.id.btn_lihatSemua);

        btnreview = findViewById(R.id.review);
        btnreview2 = findViewById(R.id.review2);
        btnreview3 = findViewById(R.id.review3);
        btnreview4 = findViewById(R.id.review4);


        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        userid = auth.getCurrentUser().getUid();

        StorageReference profileref = storageReference.child("user/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        profileref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).centerCrop().fit().into(Photoprofile);
            }
        });

        DocumentReference documentReference = firestore.collection("user").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                username.setText(documentSnapshot.getString("Username"));
                bio.setText(documentSnapshot.getString("Bio"));
            }
        });


        btnmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Membuat Instance/Objek dari PopupMenu
                PopupMenu popupMenu = new PopupMenu(Home.this, view);
                popupMenu.setOnMenuItemClickListener(Home.this);
                popupMenu.inflate(R.menu.menuoption);
                popupMenu.show();

            }
        });

        btnlihatsemua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent look = new Intent(Home.this,ListReview.class);
                startActivity(look);
            }
        });

        btnsedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sedan = new Intent(Home.this,MobilVerJenis.class);
                sedan.putExtra("jenis_mobil", "Sedan");
                startActivity(sedan);
            }
        });

        btnsuv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent suv = new Intent(Home.this,MobilVerJenis.class);
                suv.putExtra("jenis_mobil", "Suv");
                startActivity(suv);
            }
        });

        btnlcgc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lcgc = new Intent(Home.this,MobilVerJenis.class);
                lcgc.putExtra("jenis_mobil", "Sedan");
                startActivity(lcgc);
            }
        });

        btnhatchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent hc = new Intent(Home.this,MobilVerJenis.class);
                hc.putExtra("jenis_mobil", "Hatchback");
                startActivity(hc);
            }
        });

        btnmpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mpv = new Intent(Home.this,MobilVerJenis.class);
                mpv.putExtra("jenis_mobil", "Mpv");
                startActivity(mpv);
            }
        });

        btntoyota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toyota = new Intent(Home.this,MobilVerBrand.class);
                toyota.putExtra("jenis_mobil", "toyota");
                toyota.putExtra("logo", "Toyota");
                startActivity(toyota);
            }
        });

        btnmazda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mazda = new Intent(Home.this,MobilVerBrand.class);
                mazda.putExtra("jenis_mobil", "mazda");
                mazda.putExtra("logo", "Mazda");
                startActivity(mazda);
            }
        });

        btnnissan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nissan = new Intent(Home.this,MobilVerBrand.class);
                nissan.putExtra("jenis_mobil", "nissan");
                nissan.putExtra("logo", "Nissan");
                startActivity(nissan);
            }
        });

        btnmithsubishi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mitshu = new Intent(Home.this,MobilVerBrand.class);
                mitshu.putExtra("jenis_mobil", "mitshubishi");
                mitshu.putExtra("logo", "Mitshubishi");
                startActivity(mitshu);
            }
        });

        btnsuzuki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent suzuki = new Intent(Home.this,MobilVerBrand.class);
                suzuki.putExtra("jenis_mobil", "suzuki");
                suzuki.putExtra("logo", "Suzuki");
                startActivity(suzuki);
            }
        });

        btndaihatusu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent daihatu = new Intent(Home.this,MobilVerBrand.class);
                daihatu.putExtra("jenis_mobil", "daihatsu");
                daihatu.putExtra("logo", "Daihatsu");
                startActivity(daihatu);
            }
        });
    }

    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.menu1:
                Intent review = new Intent(Home.this,Review.class);
                startActivity(review);
                break;
            case R.id.menu2:
                Intent profile = new Intent(Home.this,ProfileUsers.class);
                startActivity(profile);
                break;

            case R.id.menu3:
                Intent kuis = new Intent(Home.this,Quiz.class);
                startActivity(kuis);
                break;

        }
        return true;
    }






//    public void logout(final View view) {
//        FirebaseAuth.getInstance().signOut();
//        startActivity(new Intent(getApplicationContext(),Login.class));
//        finish();
//
//        GoogleSignIn.getClient(this,new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
//                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                startActivity(new Intent(view.getContext(),Login.class));
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(Home.this,"Sign Out Error",Toast.LENGTH_SHORT ).show();
//            }
//        });
//
//    }
}
