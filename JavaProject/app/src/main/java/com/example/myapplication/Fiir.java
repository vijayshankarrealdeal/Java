package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Fiir extends AppCompatActivity {
    Button navigate;
    EditText nameOfPerson;
    EditText emailOfPerson;
    EditText crimeOfPerson;
    EditText culpitOfPerson;
    EditText culpritDetails;
    EditText phoneNumber;
    EditText yourAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiir);
        navigate = findViewById(R.id.nextPageTO);

        nameOfPerson = findViewById(R.id.nameOfPerson);
        emailOfPerson = findViewById(R.id.emailOfPerson);
        crimeOfPerson = findViewById(R.id.crimeOfPerson);
        culpitOfPerson = findViewById(R.id.culpitOfPerson);
        culpritDetails = findViewById(R.id.culpritDetails);
        phoneNumber = findViewById(R.id.phoneNumber);
        yourAddress = findViewById(R.id.yourAddress);


        navigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameOfPe = nameOfPerson.getText().toString().trim();
                String emailOfPe = emailOfPerson.getText().toString().trim();
                String crimeOfPe = crimeOfPerson.getText().toString().trim();
                String culpitOfPe = culpitOfPerson.getText().toString().trim();
                String culpritDe = culpritDetails.getText().toString().trim();
                String phoneNu = phoneNumber.getText().toString().trim();
                String address =  yourAddress.getText().toString().trim();
                if (TextUtils.isEmpty(nameOfPe))
                {
                    nameOfPerson.setError("Name is Required");
                    return;
                }
                if (TextUtils.isEmpty(crimeOfPe))
                {
                    crimeOfPerson.setError("Add the Crime is Required");
                    return;
                }
                if (TextUtils.isEmpty(phoneNu) && phoneNu.length()!=10)
                {
                    phoneNumber.setError("Add your phone number");
                    return;
                }
                if (TextUtils.isEmpty(address))
                {
                    phoneNumber.setError("Add your Address");
                    return;
                }


                Intent i = new Intent(Fiir.this,FirPage2.class);
                i.putExtra("nameOfPerson",nameOfPe);
                i.putExtra("emailOfPerson",emailOfPe);
                i.putExtra("crimeOfPerson",crimeOfPe);
                i.putExtra("culpitOfPerson",culpitOfPe);
                i.putExtra("culpritDetails",culpritDe);
                i.putExtra("phoneNumber",phoneNu);
                i.putExtra("phoneNumber",address);

                startActivity(i);
            }
        });
    }
}