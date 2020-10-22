package com.example.projectskipsi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListBrandVerContent extends RecyclerView.Adapter<ListBrandVerContent.MyViewHolder> {
    Context context;
    ArrayList<AdapterListBrandVer> myList;

    public ListBrandVerContent(Context c,ArrayList<AdapterListBrandVer>p){

        context = c;
        myList = p;
    }


    @NonNull
    @Override
    public ListBrandVerContent.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.itemlistbrandver,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListBrandVerContent.MyViewHolder holder, final int position) {

        holder.nama_mobil.setText(myList.get(position).getNama_mobil());
        holder.transmisi.setText(myList.get(position).getTransmisi());
        holder.bahan_bakar.setText(myList.get(position).getBahan_bakar());
        holder.mesin.setText(myList.get(position).getMesin());
        holder.seat.setText(myList.get(position).getSeat());
        Picasso.get().load(myList.get(position).getFoto1()).centerCrop().fit().into(holder.fotomobil);

        String mobil = myList.get(position).getNama_mobil();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle1 =new Bundle();
                bundle1.putString("nama_mobil",myList.get(position).getNama_mobil());
                bundle1.putString("bahan_bakar",myList.get(position).getBahan_bakar());
                bundle1.putString("mesin",myList.get(position).getMesin());
                bundle1.putString("seat",myList.get(position).getSeat());
                bundle1.putString("transmisi",myList.get(position).getTransmisi());
                bundle1.putString("foto1",myList.get(position).getFoto1());
                bundle1.putString("foto2",myList.get(position).getFoto2());
                bundle1.putString("foto3",myList.get(position).getFoto3());
                bundle1.putString("dimensi",myList.get(position).getDimensi());
                bundle1.putString("detailmesin",myList.get(position).getDetailmesin());
                bundle1.putString("eksterior",myList.get(position).getEksterior());
                bundle1.putString("interior",myList.get(position).getInterior());

                Intent spesifikasi = new Intent(context,SpesifikasiMobil.class);
                spesifikasi.putExtras(bundle1 );
                context.startActivity(spesifikasi);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (myList != null) ? myList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nama_mobil,transmisi,bahan_bakar,mesin,seat;
        ImageView fotomobil;
        public MyViewHolder(View itemView) {
            super(itemView);
            nama_mobil=itemView.findViewById(R.id.nama_mobil);
            transmisi=itemView.findViewById(R.id.transmisi);
            bahan_bakar=itemView.findViewById(R.id.bahanbakar);
            mesin=itemView.findViewById(R.id.mesin);
            seat=itemView.findViewById(R.id.seat);
            fotomobil =itemView.findViewById(R.id.fotomobil);
        }
    }
}
