package com.example.myapplication;

import com.google.firebase.auth.*;

public class AuthFirebase {

    private FirebaseAuth _refrence = FirebaseAuth.getInstance();

    public void  checkUser()
    {
        FirebaseUser currentUser = _refrence.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }
    public  void signUp(String email,String password)
    {
        _refrence.createUserWithEmailAndPassword(email,password);
    }

}
