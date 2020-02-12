package com.example.facebook.model;

import java.util.ArrayList;

public class Post
{
    private String uploader;
    private String message;
    private int type = 0;
    private Long date = System.currentTimeMillis();

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    private ArrayList<String> likes = new ArrayList<>();

    public Post(String uploader, String message, int type, ArrayList<String> likes)
    {
        this.uploader = uploader;
        this.message = message;
        this.type = type;
        this.likes = likes;
    }

    public Post(String uploader, String message, int type)
    {
        this.uploader = uploader;
        this.message = message;
        this.type = type;
    }

    public String getUploader()
    {
        return uploader;
    }

    public void setUploader(String uploader)
    {
        this.uploader = uploader;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Post{" +
                "uploader='" + uploader + '\'' +
                ", message='" + message + '\'' +
                ", type=" + type +
                '}';
    }
}
