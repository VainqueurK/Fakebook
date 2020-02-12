package com.example.facebook.model;

public class Profile
{
    private String name;
    private String uID;
    private String email;

    public Profile(String name, String uID, String email)
    {
        this.name = name;
        this.uID = uID;
        this.email = email;
    }

    public Profile() {

    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getuID()
    {
        return uID;
    }

    public void setuID(String uID)
    {
        this.uID = uID;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
