package com.example.reviewcafe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reviewcafe.R;
import com.example.reviewcafe.model.PhotoModel;

public class PhotoFragment extends Fragment {
    boolean like=false;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.photo_layout,container,false);
        Bundle bundle = getArguments();
        PhotoModel photo = (PhotoModel) bundle.get("objPhoto");
        ImageView imgPhoto = view.findViewById(R.id.imgPhoto);
        imgPhoto.setImageResource(photo.getSrcImg());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
