package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
public class Register extends AppCompatActivity {
    EditText emailAddress,passwordEnter,confirmPassword;
    Button signUP;
    TextView mSignUp;
    TextView aadhaC;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;
    Map<String,Object> userData = new HashMap<>();
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());

        emailAddress = findViewById(R.id.emailRegister);
        passwordEnter = findViewById(R.id.passwordRegister);
        confirmPassword = findViewById(R.id.confrimPasswordReg);
        signUP = findViewById(R.id.SignUpREg);
        mSignUp = findViewById(R.id.backTOLogin);
        aadhaC = findViewById(R.id.aadharC);
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBarReg);

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailAddress.getText().toString().trim();
                String password = passwordEnter.getText().toString();
                String confirmPas = confirmPassword.getText().toString();
                String aadha = aadhaC.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    emailAddress.setError("Email is Required");
                    return;
                }
                if (aadha.length() != 12||aadha.length()>12||aadha.length()<12)
                {
                    aadhaC.setError("Enter Your Correct Aadhaar Number");
                    return;
                }
                if (TextUtils.isEmpty(password))
                {
                    passwordEnter.setError("Password is Required");
                    return;
                }
                if (password.length()<6)
                {
                    passwordEnter.setError("Password Must Be Greater than 6 Char");
                    return;
                }
                if (!password.equals(confirmPas))
                {
                    confirmPassword.setError("Both Password Must Be Same");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Register.this,"UserCreate",Toast.LENGTH_SHORT).show();
                            userData.put("Aadhaar",aadha);
                            userData.put("DateCreated",currentDateandTime);
                            userData.put("email",firebaseAuth.getCurrentUser().getEmail());
                            userData.put("uid",firebaseAuth.getCurrentUser().getUid());
                            DocumentReference documentReference =  db.collection("Users").document(firebaseAuth.getCurrentUser().getUid());
                            documentReference.set(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    System.out.println(task.isComplete());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Register.this,"Error"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

}