package com.example.facebook;

import android.os.Bundle;

import com.example.facebook.Logic.Memory;
import com.example.facebook.model.Post;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.facebook.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class HomepageActivity extends AppCompatActivity {

    public TextView username;
    public RecyclerView recycle;
    public EditText messager;
    public ArrayList<Post> posts = new ArrayList<>();
    PostListAdapter allposts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadPost();
        username = findViewById(R.id.userName);
        recycle = findViewById(R.id.recycle);
        messager = findViewById(R.id.messager);
        //setUpAdapter();

        username.setText(Memory.currentProfile.getName());

        Button fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                sendPost(messager.getText().toString());
            }
        });
    }

    public void setUpAdapter()
    {
        allposts = new PostListAdapter(posts, this);
        recycle.setAdapter(allposts);
        recycle.setLayoutManager(new LinearLayoutManager(this));
    }

    public void loadPost()
    {
        //ArrayList<String> likes = new ArrayList<>();
        //likes.add(Memory.currentProfile.getuID());
        //posts.add(new Post(Memory.currentProfile.getName(), "Blank", 0, likes));

        FirebaseFirestore.getInstance().collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot documentSnapshot, @Nullable FirebaseFirestoreException e)
            {
                if(e != null)
                {
                    Toast.makeText(HomepageActivity.this, "And I Opp- Error has Occured", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(documentSnapshot != null && !documentSnapshot.isEmpty())
                {
                    posts.clear();
                    for(DocumentSnapshot s : documentSnapshot.getDocuments())
                    {
                        Post pst = new Post(s.getData().get("uploader").toString(), s.getData().get("message").toString(), Integer.parseInt(s.getData().get("type").toString()));
                        pst.setDate(Long.parseLong(s.getData().get("date").toString()));
                        pst.setLikes((ArrayList<String>)s.getData().get("likes"));
                        posts.add(pst);
                    }
                    setUpAdapter();
                }
                else
                {
                    System.out.println("error\n" + documentSnapshot);
                }
            }
        });
    }

    public void sendPost(String message)
    {
        Post sendMessage = new Post(Memory.currentProfile.getName(), message, 0);
        FirebaseFirestore.getInstance().collection("Posts").document(String.format("%d", System.currentTimeMillis())).set(sendMessage).addOnSuccessListener(e->
        {
            messager.setText("");
            //allposts.updateData(sendMessage, posts.size());
            recycle.smoothScrollToPosition(0);
            loadPost();
        });

    }
}

