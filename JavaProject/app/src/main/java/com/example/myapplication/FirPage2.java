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
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FirPage2 extends AppCompatActivity {

    private static final int CAMERA_PERM_CODE =111 ;
    ImageView selectedImage;
    Button cameraBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fir_page2);
        selectedImage = findViewById(R.id.imageIdOfPerson);
        cameraBtn = findViewById(R.id.openCamera);

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askCameraPermission();
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
            Bitmap image =(Bitmap) data.getExtras().get("data");
            selectedImage.setImageBitmap(image);
        }
    }
}