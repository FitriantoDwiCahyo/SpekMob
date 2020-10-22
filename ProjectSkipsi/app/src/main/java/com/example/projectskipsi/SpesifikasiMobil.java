package com.example.projectskipsi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class SpesifikasiMobil extends AppCompatActivity {

    ExpandableTextView zoom,zoom1,zoom2,zoom3;
    ImageView foto1,foto2,foto3;
    TextView transmisi,bahanbakar,mesin,seat,nama_mobil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spesifikasi_mobil);

        zoom = findViewById(R.id.zoomtext);
        zoom1 = findViewById(R.id.zoomtext1);
        zoom2 = findViewById(R.id.zoomtext2);
        zoom3 = findViewById(R.id.zoomtext3);

        foto1 = findViewById(R.id.foto1);
        foto2 = findViewById(R.id.foto2);
        foto3 = findViewById(R.id.foto3);

        transmisi = findViewById(R.id.transmisi);
        mesin = findViewById(R.id.mesin);
        seat = findViewById(R.id.seat);
        bahanbakar = findViewById(R.id.bahanbakar);
        nama_mobil = findViewById(R.id.nama_mobil);

        getData();

    }
    public void getData(){

        final String getNama_mobil= getIntent().getExtras().getString("nama_mobil");
        final String getDimensi= getIntent().getExtras().getString("dimensi");
        final String getDetailmesin= getIntent().getExtras().getString("detailmesin");
        final String getInterior= getIntent().getExtras().getString("interior");
        final String getEksterior= getIntent().getExtras().getString("eksterior");
        final String getMesin= getIntent().getExtras().getString("mesin");
        final String getTransmisi= getIntent().getExtras().getString("transmisi");
        final String getBahanbakar= getIntent().getExtras().getString("bahan_bakar");
        final String getSeat= getIntent().getExtras().getString("seat");
        final String getFoto1= getIntent().getExtras().getString("foto1");
        final String getFoto2= getIntent().getExtras().getString("foto2");
        final String getFoto3= getIntent().getExtras().getString("foto3");


        nama_mobil.setText(getNama_mobil);
        mesin.setText(getMesin);
        transmisi.setText(getTransmisi);
        bahanbakar.setText(getBahanbakar);
        seat.setText(getSeat);
        zoom.setText(getDimensi);
        zoom1.setText(getDetailmesin);
        zoom2.setText(getEksterior);
        zoom3.setText(getInterior);

        Picasso.get().load(getFoto1).centerCrop().fit().into(foto1);
        Picasso.get().load(getFoto2).centerCrop().fit().into(foto2);
        Picasso.get().load(getFoto3).centerCrop().fit().into(foto3);

    }

}
