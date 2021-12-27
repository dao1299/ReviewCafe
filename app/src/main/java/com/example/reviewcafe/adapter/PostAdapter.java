package com.example.reviewcafe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reviewcafe.R;
import com.example.reviewcafe.model.PostModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> implements Filterable {

    ArrayList<PostModel> listPost;
    ArrayList<PostModel> listPostDiff;
    ClickListener clickListener;
    Context context;

    public PostAdapter(ArrayList<PostModel> listPost, ClickListener clickListener) {
        this.listPost = listPost;
        this.clickListener = clickListener;
        this.listPostDiff = listPost;
    }

    public PostAdapter(ArrayList<PostModel> listPost, ClickListener clickListener, Context context) {
        this.listPost = listPost;
        this.clickListener = clickListener;
        this.listPostDiff = listPost;
        this.context = context;
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
        holder.txtAddress.setText(postElement.getDistrict());
//        holder.imgPost.setImageURI(Uri.parse(postElement.getSrcImg()));
        Glide.with(context).load(postElement.getSrcImg()).into(holder.imgPost);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(holder.getLayoutPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listPost != null) return listPost.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String keySearch = charSequence.toString();
                if (keySearch.isEmpty()) {
                    listPost = listPostDiff;
                } else {
                    List<PostModel> listResult = new ArrayList<>();
                    for (PostModel postModel : listPostDiff) {
                        if (postModel.getTitlePost().toUpperCase(Locale.ROOT).contains(keySearch.toUpperCase())) {
                            listResult.add(postModel);
                        }
                    }
                    listPost = (ArrayList<PostModel>) listResult;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listPost;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listPost = (ArrayList<PostModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtAuthor, txtPrice, txtAddress;
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
