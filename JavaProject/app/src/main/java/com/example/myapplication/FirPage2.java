package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;

public class FirPage2 extends AppCompatActivity {

    private static final int CAMERA_PERM_CODE = 111;
    TextView addh;
    Button finalSubmit;

    String nameOfPerson;
    String emailOfPerson;
    String crimeOfPerson;
    String culpitOfPerson;
    String culpritDetails;
    String phoneNumber;
    String address;

    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_page2);

        nameOfPerson = getIntent().getStringExtra("nameOfPerson");
        emailOfPerson = getIntent().getStringExtra("emailOfPerson");
        crimeOfPerson = getIntent().getStringExtra("crimeOfPerson");
        culpitOfPerson = getIntent().getStringExtra("culpitOfPerson");
        culpritDetails = getIntent().getStringExtra("culpritDetails");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        address = getIntent().getStringExtra("address");

        UUID uuid = UUID.randomUUID();
        addh = findViewById(R.id.AddharCardNumber);
        finalSubmit = findViewById(R.id.SubmitButtonAllThings);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        HashMap<String, String> hashMap = new HashMap<>();
        FirebaseUser user = auth.getCurrentUser();

        finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashMap.put("nameofperson",nameOfPerson);
                hashMap.put("emailOfPerson",emailOfPerson);
                hashMap.put("crimeOfPerson",crimeOfPerson);
                hashMap.put("culpitOfPerson",culpitOfPerson);
                hashMap.put("culpritDetails",culpritDetails);
                hashMap.put("phoneNumber",phoneNumber);
                hashMap.put("address",address);
                hashMap.put("addh",addh.getText().toString());
                DocumentReference documentReferenceC =  firebaseFirestore.collection("Fir").document(uuid.toString());
                documentReferenceC.set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println(task.isComplete());
                    }
                });
                DocumentReference documentReference =  firebaseFirestore.collection("Users").document(auth.getCurrentUser().getUid())
                        .collection("Fir").document(uuid.toString());
                documentReference.set(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println(task.isComplete());
                    }
                });
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });




    }
}
