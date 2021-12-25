package com.example.reviewcafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewcafe.R;
import com.example.reviewcafe.model.PostModel;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    ArrayList<PostModel> listPost;
    ClickListener clickListener;

    public PostAdapter(ArrayList<PostModel> listPost, ClickListener clickListener) {
        this.listPost = listPost;
        this.clickListener = clickListener;
    }

    public PostAdapter(ArrayList<PostModel> listPost) {
        this.listPost = listPost;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_of_post,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PostModel postElement = listPost.get(position);
        holder.txtTitle.setText(postElement.getTitlePost());
        holder.txtAuthor.setText(postElement.getAuthorPost());
        holder.txtPrice.setText(postElement.getPricePost());
        holder.txtAddress.setText(postElement.getAddressPost());
        holder.imgPost.setImageResource((int) postElement.getSrcImg());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPost.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitle,txtAuthor,txtPrice,txtAddress;
        ImageView imgPost;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitlePost);
            txtAddress = itemView.findViewById(R.id.txtQuanPost);
            txtAuthor = itemView.findViewById(R.id.txtAuthorPost);
            txtPrice = itemView.findViewById(R.id.txtPricePost);
            imgPost = itemView.findViewById(R.id.imgThumbPost);
            layout = itemView.findViewById(R.id.layoutItemPost);
        }
    }
    public interface ClickListener{
        void onItemClick(int position);
    }
}
