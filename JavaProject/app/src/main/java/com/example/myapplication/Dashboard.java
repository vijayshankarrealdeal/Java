package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.*;
import android.widget.ListView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.firebase.ui.firestore.SnapshotParser;
import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements FirestoreAdapter.OnListItemClick {

    private RecyclerView mFrecyclerView;
    private FirestoreAdapter adapter;
    FirebaseFirestore cloudStore;
   // ProgressBar dataLoad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
         mFrecyclerView =  findViewById(R.id.recyelcwithFirestore);
        cloudStore = FirebaseFirestore.getInstance();
      //  dataLoad = findViewById(R.id.DataLoadingIndicator);
        Query query =  cloudStore.collection("adminContent").document("Videos")
                .collection("data");
        PagedList.Config config = new PagedList.Config.Builder().setInitialLoadSizeHint(10).setPageSize(6).build();


        FirestorePagingOptions<AdminModel> options = new
                FirestorePagingOptions.Builder<AdminModel>()
                .setLifecycleOwner(this)
                .setQuery(query, config, new SnapshotParser<AdminModel>() {
                    @NonNull
                    @Override
                    public AdminModel parseSnapshot(@NonNull DocumentSnapshot snapshot) {
                        AdminModel adminModel = snapshot.toObject(AdminModel.class);
                        String itemId = snapshot.getId();
                        adminModel.setItemId(itemId);
                        return adminModel;
                    }
                }).build( );
         adapter = new FirestoreAdapter(options,this);
         mFrecyclerView.setHasFixedSize(true);
         mFrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFrecyclerView.setAdapter(adapter);








        BottomNavigationView bottomNavigationView  =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.dashboard);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onItemClick(DocumentSnapshot snapshot,int position) {
        //Pass video
        startActivity(new Intent(getApplicationContext(),VideoPage.class));
    }

    private  class AdminViewHolder extends  RecyclerView.ViewHolder {
        private  TextView textView;
        private ImageView img;
        public AdminViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textOFCard);
            img = itemView.findViewById(R.id.imageView);
        }
    }

}