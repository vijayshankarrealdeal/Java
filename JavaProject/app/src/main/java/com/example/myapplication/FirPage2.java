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
import java.util.UUID;

public class FirPage2 extends AppCompatActivity {

    private static final int CAMERA_PERM_CODE = 111;
    private ImageView photo;
    private Button cameraBtn;
    private Bitmap image;
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
    FirebaseStorage Fstorage;
    StorageReference sref;
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


        photo = findViewById(R.id.imageIdOfPerson);
        cameraBtn = findViewById(R.id.openCamera);
        addh = findViewById(R.id.AddharCardNumber);
        finalSubmit = findViewById(R.id.SubmitButtonAllThings);
        Fstorage = FirebaseStorage.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        HashMap<String, String> hashMap = new HashMap<>();

        FirebaseUser user = auth.getCurrentUser();
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, 0);
            }
        });
        finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,100,stream);
                final String idImage  = UUID.randomUUID().toString();
                StorageReference reference = Fstorage.getReference().child("image"+idImage);
                byte[] b = stream.toByteArray();
                reference.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri url = uri;
                               System.out.println(uri);
                            }
                        });
                    }
                });
            }
        });


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        image = ((BitmapDrawable) data.getExtras().get("data")).getBitmap();
        photo.setImageBitmap(image);
    }
}
