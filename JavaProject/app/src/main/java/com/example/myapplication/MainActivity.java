package com.example.myapplication;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.*;


public class MainActivity extends AppCompatActivity {
    LocationManager locationManager;
    Button logout;
    Button emergency;
    LocationListener locationListener;
    TextView verifyMessage;
    Button verifyEmail;
    FrameLayout sosButton;
    Map<String,Object> userData = new HashMap<>();
    Map<String,Object> emergencyModel = new HashMap<>();
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db ;
    FirebaseAuth auth;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
    String currentDateandTime = sdf.format(new Date());
    Location location;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                // Check
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intilize the
        BottomNavigationView bottomNavigationView  =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
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
                        return true;
                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });









        //sosButton = findViewById(R.id.sosButton);
        emergency = findViewById(R.id.sosEmergency);
       verifyMessage = findViewById(R.id.verifyTextView);
        verifyEmail = findViewById(R.id.verifyButtom);
        auth = FirebaseAuth.getInstance();
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        logout = findViewById(R.id.Logout);
        db = FirebaseFirestore.getInstance();





        locationListener  = new LocationListener()
        {

            @Override
            public void onLocationChanged(@NonNull Location location) {
                location = location;
                final String uid = user.getUid();
                userData.put("email",user.getEmail());
                userData.put("uid",user.getUid());
                userData.put("longitude",location.getLatitude());
                userData.put("longitude",location.getLongitude());
             //   userData.put("location",location.toString().split(",")[0]);




      DocumentReference documentReference =  db.collection("Users").document(uid);
      documentReference.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(MainActivity.this, "Done", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
               // Log.i("Location",location.toString());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }
        };
        //Ask for permission Logic
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            // Ask Permission
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
        if(!auth.getCurrentUser().isEmailVerified())
        {
            verifyEmail.setVisibility(View.VISIBLE);
            verifyMessage.setVisibility(View.VISIBLE);

        }
        verifyEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                   Toast.makeText(MainActivity.this,"Verification Send",Toast.LENGTH_SHORT).show();
                        verifyEmail.setVisibility(View.GONE);
                        verifyMessage.setVisibility(View.GONE);
                    }
                });
            }
        });
        emergencyModel.put("email",user.getEmail());
        emergencyModel.put("gpsLast",location);
        emergencyModel.put("time",currentDateandTime.toString());

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.collection("EmergencyRecord")
                        .document(user.getUid()).collection("EmergencyCall").document(currentDateandTime)
                .set(emergencyModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this,"Alert Send",Toast.LENGTH_SHORT).show();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Alert Failed"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}