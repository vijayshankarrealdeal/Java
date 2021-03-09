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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Login extends AppCompatActivity {
    EditText mEmail,mPassword;
    Button mLogin;
    TextView mCreateAccount;
    ProgressBar progressBar;
    FirebaseAuth firebaseAu;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf.format(new Date());
        mEmail = findViewById(R.id.emailLogin);
        mPassword = findViewById(R.id.PasswordLogin);
        mLogin = findViewById(R.id.loginButton);
        mCreateAccount = findViewById(R.id.changeToRegister);
        progressBar = findViewById(R.id.progressBarlogin);
        firebaseAu = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        if(firebaseAu.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email is Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password is Required");
                    return;
                }
                if (password.length() < 6) {
                    mPassword.setError("Password Must Be Greater than 6 Char");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                firebaseAu.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Welcome", Toast.LENGTH_SHORT).show();
                            DocumentReference documentReference =  db.collection("Users").document(firebaseAu.getCurrentUser().getUid());
                            documentReference.update("LastLogin",currentDateandTime).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    System.out.println(task.isComplete());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {

                            Toast.makeText(Login.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });

            }

        });
        mCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        }
    }