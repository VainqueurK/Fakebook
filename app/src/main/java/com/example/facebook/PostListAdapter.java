package com.example.facebook;

import android.content.Context;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.facebook.Logic.Memory;
import com.example.facebook.model.Post;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.PostHolder>
{
    ArrayList<Post> posts;
    Context context;

    public PostListAdapter(ArrayList<Post> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.post_item,parent,false);

        return new PostHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position)
    {
        holder.title.setText(posts.get(position).getUploader());
        holder.desc.setText(posts.get(position).getMessage());
        holder.countr.setText(String.format("%d", posts.get(position).getLikes().size()));
        holder.likon.setImageDrawable(context.getResources().getDrawable(posts.get(position).getLikes().contains(Memory.currentProfile.getuID())? R.drawable.liked : R.drawable.heart));
        holder.likon.setOnClickListener(e->{
            updateLikes(posts.get(position), holder) ;
        });
    }

    public void updateLikes(Post post, PostHolder holder)
    {
        if(post.getLikes().contains(Memory.currentProfile.getuID()))
        {
            post.getLikes().remove(Memory.currentProfile.getuID());
        }
        else
        {
            post.getLikes().add(Memory.currentProfile.getuID());
        }

        FirebaseFirestore.getInstance().collection("posts").document(String.format("%d", post.getDate())).update("likes", post.getLikes()).addOnSuccessListener(S->{
            holder.likon.setImageDrawable(context.getResources().getDrawable(post.getLikes().contains(Memory.currentProfile.getuID())? R.drawable.liked : R.drawable.heart));

        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView desc;
        TextView countr;
        ImageButton likon;

        public PostHolder(@NonNull View itemView)
        {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            desc= itemView.findViewById(R.id.desc);
            countr=itemView.findViewById(R.id.countr);
            likon=itemView.findViewById(R.id.likon);


        }
    }

    public void updateData(Post post, int position)
    {
        posts.add(post);
        notifyItemInserted(position);
    }
}
