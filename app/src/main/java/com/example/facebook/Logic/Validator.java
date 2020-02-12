package com.example.facebook.Logic;

public class Validator
{
    public boolean validateEmail(String email)
    {
        boolean valid = false;

        if(email.contains("@") && email.contains("."))
        {
            valid = true;
        }
        return valid;
    }

    public boolean validatePassword(String psword)
    {
        boolean valid = false;
        String pattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
        if(psword.matches(pattern) && !(psword.isEmpty()))
        {
            valid = true;
        }

        return valid;
    }
}
