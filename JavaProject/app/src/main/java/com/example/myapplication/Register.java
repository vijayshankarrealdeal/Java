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

public class Register extends AppCompatActivity {
    EditText emailAddress,passwordEnter,confirmPassword;
    Button signUP;
    TextView mSignUp;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        emailAddress = findViewById(R.id.emailRegister);
        passwordEnter = findViewById(R.id.passwordRegister);
        confirmPassword = findViewById(R.id.confrimPasswordReg);
        signUP = findViewById(R.id.SignUpREg);
        mSignUp = findViewById(R.id.backTOLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBarReg);

        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailAddress.getText().toString().trim();
                String password = passwordEnter.getText().toString();
                String confirmPas = confirmPassword.getText().toString();
                if (TextUtils.isEmpty(email))
                {
                    emailAddress.setError("Email is Required");
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