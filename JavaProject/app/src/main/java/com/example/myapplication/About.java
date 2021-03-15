package com.example.myapplication;

   import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
   import android.graphics.Bitmap;
   import android.graphics.BitmapFactory;
   import android.location.LocationListener;
import android.location.LocationManager;
   import android.net.Uri;
   import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
   import com.google.android.gms.tasks.OnFailureListener;
   import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
   import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

   import java.io.IOException;
   import java.net.URL;

public class About extends AppCompatActivity {

    FirebaseAuth auth;
    ImageView imageView;
    TextView name;
    TextView email;
    TextView information;
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
        imageView =(ImageView) findViewById(R.id.thisWIkgraphShow);
        name = (TextView) findViewById(R.id.nameOfUser);
        email=(TextView)findViewById(R.id.emialOfTHepersion);
        information = (TextView)findViewById(R.id.ingorAnbdhsf);


        String url = "https://firebasestorage.googleapis.com/v0/b/women-e598c.appspot.com/o/download.png?alt=media&token=b45989ac-0d73-4e08-8ab8-96ee19e577b5";
        Uri u = Uri.parse(url);
        imageView.setImageURI(u);
        System.out.println(user.getEmail());

        firebaseFirestore.collection("Users").document(user.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
             task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                 @Override
                 public void onSuccess(DocumentSnapshot snapshot) {
                     email.setText(snapshot.getString("email"));
                   //  name.setText(snapshot.getId());
                     //information.setText(snapshot.getId());

                 }
             }).addOnFailureListener(new OnFailureListener() {
                 @Override
                 public void onFailure(@NonNull Exception e) {

                 }
             })   ;
            }
        });




//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot snapshot) {
//                mediaUrL = snapshot.getString("imageUrl");
//                textView.setText(mediaUrL);
//                System.out.println(snapshot.getString("imageUrl"));
//            }
//        });
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