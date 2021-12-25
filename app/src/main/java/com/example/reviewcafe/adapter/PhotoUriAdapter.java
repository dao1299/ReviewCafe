package com.example.reviewcafe.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewcafe.R;

import java.io.IOException;
import java.util.List;

public class PhotoUriAdapter extends RecyclerView.Adapter<PhotoUriAdapter.ViewHolder> {
    List<Uri> listPhoto;
    Context context;
    public PhotoUriAdapter(Context context) {
        this.context = context;
    }
    public void setDataPhoto(List<Uri> listPhoto){
        this.listPhoto=listPhoto;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.photo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Uri uri = listPhoto.get(position);
        if (uri==null) return;
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(),uri);
            if (bitmap!=null) holder.imgPhoto.setImageURI(listPhoto.get(position));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        if (listPhoto!=null) return listPhoto.size();
        return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto2);
        }
    }
}
