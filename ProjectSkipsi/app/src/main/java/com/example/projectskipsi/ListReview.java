package com.example.projectskipsi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListReview extends AppCompatActivity {

    DatabaseReference reference;

    RecyclerView listreview;
    ArrayList<AdapterListReview> list;
    ListReviewContent content;

    String USERNAME_KEY="usernamekey";
    String username_key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_review);


        listreview= findViewById(R.id.listreview);
        listreview.setLayoutManager(new LinearLayoutManager(this));



        reference = FirebaseDatabase.getInstance().getReference().child("Review");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    AdapterListReview p = dataSnapshot1.getValue(AdapterListReview.class);

                    //Mengambil Primary Key, digunakan untuk proses Update dan Delete
                    p.setKey(dataSnapshot.getKey());
                    list.add(p);

                }
                //mereplace
                content = new ListReviewContent(ListReview.this,list);
                //mensetting adapter
                listreview.setAdapter(content);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

//    public void getUsernameLocal(){
//        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
//        username_key = sharedPreferences.getString(username_key, "");
//    }
}
