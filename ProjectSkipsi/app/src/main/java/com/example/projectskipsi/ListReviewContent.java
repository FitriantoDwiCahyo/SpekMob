package com.example.projectskipsi;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListReviewContent extends RecyclerView.Adapter<ListReviewContent.MyViewHolder> {
    Context context;
    ArrayList<AdapterListReview> myList;

    public ListReviewContent(Context c,ArrayList<AdapterListReview>p){

        context = c;
        myList = p;
    }


    @NonNull
    @Override
    public ListReviewContent.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.itemlistreview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.brand.setText(myList.get(position).getNamamobil());
        holder.username.setText(myList.get(position).getUsername());
        holder.hasilreview.setText(myList.get(position).getReview());
        Picasso.get().load(myList.get(position).getPhoto_user()).centerCrop().fit().into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return (myList != null) ? myList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView brand,username,hasilreview;
        ImageView photo;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            brand = itemView.findViewById(R.id.brand);
            username = itemView.findViewById(R.id.nama);
            hasilreview = itemView.findViewById(R.id.hasilreview);
            photo = itemView.findViewById(R.id.photo_review);
        }
    }
}
