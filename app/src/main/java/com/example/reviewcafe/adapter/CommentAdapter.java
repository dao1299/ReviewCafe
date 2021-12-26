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

import com.bumptech.glide.Glide;
import com.example.reviewcafe.R;
import com.example.reviewcafe.model.CommentModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    List<CommentModel> listComment;
    ClickListener clickListener;
    Context context;

    public CommentAdapter(List<CommentModel> listComment, ClickListener clickListener, Context context) {
        this.listComment = listComment;
        this.clickListener = clickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_comment,null);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapter.ViewHolder holder, int position) {
        CommentModel commentElement = listComment.get(position);
        Glide.with(context).load(commentElement.getImgUser()).into(holder.imgUser);
        holder.txtNameUser.setText(commentElement.getNameUser());
        holder.txtComment.setText(commentElement.getContentComment());
//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                clickListener.onItemClick(holder.getLayoutPosition());
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return listComment.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgUser;
        TextView txtNameUser,txtComment;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUserComment);
            txtNameUser = itemView.findViewById(R.id.txtNameUserComment);
            txtComment=itemView.findViewById(R.id.txtContentCommentPost);
        }
    }
    public interface ClickListener {
        void onItemClick(int position);
    }
}
