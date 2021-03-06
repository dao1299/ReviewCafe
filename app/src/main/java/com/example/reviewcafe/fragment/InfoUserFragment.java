package com.example.reviewcafe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.reviewcafe.MainActivity;
import com.example.reviewcafe.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InfoUserFragment extends Fragment {
    Fragment fragment;
    TextView txtNameUserInfo, txtPhoneNumberInfo, txtEmailInfo, txtListFavorite, txtListPostUser, txtTitle;
    Button btnChangeInfo, btnChangePassword;
    MainActivity mainActivity = new MainActivity();
    ImageView imgUserInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.info_user, container, false);
        getData(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        getData(view);
        btnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ChangeInfoUserFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain, fragment, "TAG").commit();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ChangePasswordUserFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain, fragment, "TAG").commit();
            }
        });
        txtListPostUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitle.setText("Danh s??ch b??i ????ng g??p");
                fragment = ListPostFragment.newInstance("location");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain, fragment, "TAG").commit();
            }
        });
        txtListFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtTitle.setText("Danh s??ch b??i y??u th??ch");
                fragment = ListPostFragment.newInstance("favorite");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain, fragment, "TAG").commit();
            }
        });

    }
    private void mapId(View view) {
        btnChangeInfo = view.findViewById(R.id.btnChangeInfo);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        txtNameUserInfo = view.findViewById(R.id.txtNameUserInfo);
        txtPhoneNumberInfo = view.findViewById(R.id.txtPhoneNumberInfo);
        txtEmailInfo = view.findViewById(R.id.txtEmailInfo);
        imgUserInfo = view.findViewById(R.id.imgUserInfo);
        txtListFavorite = view.findViewById(R.id.txtListFavorite);
        txtListPostUser = view.findViewById(R.id.txtListPostUser);
        txtTitle = getActivity().findViewById(R.id.txtTitle);
    }
    public void getData(View view) {
        mapId(view);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null) {
            txtNameUserInfo.setText(""+user.getDisplayName());
            txtPhoneNumberInfo.setText(""+user.getPhoneNumber());
            txtEmailInfo.setText(""+user.getEmail());
            Glide.with(this).load(user.getPhotoUrl()).into(imgUserInfo);
        }
    }
}
