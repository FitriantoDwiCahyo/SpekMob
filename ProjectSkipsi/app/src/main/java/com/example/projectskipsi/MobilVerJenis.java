package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MobilVerJenis extends AppCompatActivity {

    DatabaseReference reference;

    ImageView header;

    RecyclerView listmobil;
    ArrayList<AdapterListBrandVer> list;
    ListBrandVerContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobil_ver_jenis);
//        load = findViewById(R.id.load);

        listmobil= findViewById(R.id.listmobil);

        listmobil.setLayoutManager(new LinearLayoutManager(this));

//        load.setVisibility(View.VISIBLE);

        Bundle bundle = getIntent().getExtras();
        final String jenis_merek_dipilih = bundle.getString("jenis_mobil");

        reference = FirebaseDatabase.getInstance().getReference().child("MobilJenis").child(jenis_merek_dipilih);
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
                content = new ListBrandVerContent(MobilVerJenis.this, list);
                //mensetting adapter
                listmobil.setAdapter(content);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        load.setVisibility(View.GONE);
    }
}
