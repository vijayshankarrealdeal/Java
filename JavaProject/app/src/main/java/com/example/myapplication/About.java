package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.List;

public class About extends AppCompatActivity {

    FirebaseAuth auth;
    ImageView imageView;
    TextView textView;
    LocationListener locationListener;
    LocationManager locationManager;
    String mediaUrL;
    private  FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        imageView = findViewById(R.id.showGraphImage);
        textView = findViewById(R.id.jkbdsjfdvshf);
        firebaseFirestore.collection("crimeData").document("graph").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot snapshot) {
                mediaUrL = snapshot.getString("imageUrl");
                textView.setText(mediaUrL);
                System.out.println(snapshot.getString("imageUrl"));
            }
        });
        Glide.with(this).load(mediaUrL).into(imageView);
        System.out.println(mediaUrL);
        ///Location




        BottomNavigationView bottomNavigationView  =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.about);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(),Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.about:

                        return true;
                }
                return false;
            }
        });
    }


}