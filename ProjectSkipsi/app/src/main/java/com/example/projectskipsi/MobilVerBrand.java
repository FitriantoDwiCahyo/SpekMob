package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MobilVerBrand extends AppCompatActivity {


    DatabaseReference reference,reference2;

    ImageView header;
    RecyclerView listmobil;
    ArrayList<AdapterListBrandVer> list;
    ListBrandVerContent content;

    ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil_ver_brand);


        load = findViewById(R.id.load);
        header =findViewById(R.id.header1);

        listmobil= findViewById(R.id.listmobil);
        listmobil.setLayoutManager(new LinearLayoutManager(this));

        load.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        final String jenis_merek_dipilih = bundle.getString("jenis_mobil");

        Bundle bundle1 = getIntent().getExtras();
        final String jenis_logo_dipilih = bundle1.getString("logo");

        reference = FirebaseDatabase.getInstance().getReference().child("Mobil").child(jenis_merek_dipilih);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AdapterListBrandVer p = dataSnapshot1.getValue(AdapterListBrandVer.class);

                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                    p.setKey(dataSnapshot.getKey());
                    list.add(p);

                }

                //mereplace
                content = new ListBrandVerContent(MobilVerBrand.this, list);
                //mensetting adapter
                listmobil.setAdapter(content);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        reference2 = FirebaseDatabase.getInstance().getReference().child("LogoMobil").child(jenis_logo_dipilih);
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picasso.get()
                        .load(snapshot.child("logo")
                                .getValue().toString()).centerCrop().fit()
                        .into(header);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        load.setVisibility(View.GONE);
    }
}
