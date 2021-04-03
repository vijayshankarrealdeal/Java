package com.example.myapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class Validation {
    public Validation(String email, String password) {
        this.email = email;
        this.password = password;
    }
    final String email;
    final String password;

    public  String verifyEmail()
    {
        if(checkValidity())
        {
            return  "Email is Success";
        }
        else
        {
            return  "Email is Wrong";
        }
    }
    public  String verifyPassword()
    {
        if(validPassword())
        {
            return  "Success";
        }
        else
        {
            return  "Password is Wrong";
        }
    }

    private   boolean checkValidity(
          ) {
        String emailText = email;
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%*+/=?^_{|}~-]+@gmail.com");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();


        }
    private   boolean validPassword() {
        Pattern pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})");
        Matcher matcher = pattern.matcher(password);

        return  matcher.matches();
    }

}
