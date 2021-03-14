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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

public class FirPage2 extends AppCompatActivity {

    private static final int CAMERA_PERM_CODE =111 ;
    ImageView selectedImage;
    Button cameraBtn;
    TextView addh;
    Button finalSubmit;

    String nameOfPerson;
    String emailOfPerson;
    String crimeOfPerson;
    String culpitOfPerson;
    String culpritDetails;
    String phoneNumber;
    String address;
    Bitmap image;
    FirebaseAuth auth;
    FirebaseStorage Fstorage;
    StorageReference sref;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_page2);

        nameOfPerson  = getIntent().getStringExtra("nameOfPerson");
        emailOfPerson  = getIntent().getStringExtra("emailOfPerson");
        crimeOfPerson  = getIntent().getStringExtra("crimeOfPerson");
        culpitOfPerson  = getIntent().getStringExtra("culpitOfPerson");
        culpritDetails  = getIntent().getStringExtra("culpritDetails");
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        address = getIntent().getStringExtra("address");


        selectedImage = findViewById(R.id.imageIdOfPerson);
        cameraBtn = findViewById(R.id.openCamera);
        addh = findViewById(R.id.AddharCardNumber);
        finalSubmit = findViewById(R.id.SubmitButtonAllThings);
        Fstorage = FirebaseStorage.getInstance();
        auth  = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        sref = Fstorage.getReference();
        HashMap<String,String> hashMap = new HashMap<>();
        StorageReference imagesRef = sref.child("images");
        Bitmap bitmap = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        FirebaseUser user = auth.getCurrentUser();
        UploadTask uploadTask = sref.child("hello").child(user.getUid()).putBytes(data);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
            }
        });
        finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String url = taskSnapshot.getStorage().getDownloadUrl().toString();
                        hashMap.put("url",url);
                        firebaseFirestore.collection("Users").document(user.getUid())
                                .collection("ComplainsFir").document().set(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                            System.out.println("DOne");
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }
        });
    }

    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERM_CODE);
        }else{
            openCamera();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[]permission,@NonNull int[] grantResults){
        if(requestCode == CAMERA_PERM_CODE)
        {
            if(grantResults.length <0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                openCamera();
            }else{
                Toast.makeText(this,"Camera Permission is Requird",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private  void openCamera()
    {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera,102);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 102)
        {
            image =(Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(image);
        }
    }
}