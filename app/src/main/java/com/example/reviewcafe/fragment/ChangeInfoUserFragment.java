package com.example.reviewcafe.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.reviewcafe.MainActivity;
import com.example.reviewcafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ChangeInfoUserFragment extends Fragment {
    ProgressDialog progressDialog;
    CardView cardViewAvar;
    ImageView imgAvarChangeProfileUser;
    EditText edtNameUserChangeProfile,edtPhoneNumberUserChangeProfile,edtEmailUserChangeProfile;
    Button btnChangeProfile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_profile_user,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapId(view);
        showCurrentInfo();
        addEventClick();
    }

    private void addEventClick() {
        btnChangeProfile.setOnClickListener(view ->{
            progressDialog = ProgressDialog.show(getActivity(), "Cập nhật hồ sơ!","Đợi tí tẹo thui (>\"<)", true);
            changeProfile();
        });
    }

    private void changeProfile() {
        if (!validate()) return;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(edtNameUserChangeProfile.getText().toString().trim())
                .build();


        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                        }
                    }
                });
        user.updateEmail(edtEmailUserChangeProfile.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                            changeMenu();
                            getParentFragmentManager().beginTransaction().replace(R.id.containerContentMain,HomeFragment.newInstance("data"),"TAG").commit();

                        }
                    }
                });

    }

    private boolean validate() {
        if (edtEmailUserChangeProfile.getText().toString().isEmpty()
        || edtNameUserChangeProfile.getText().toString().isEmpty()) return false;
        return true;
    }

    private void showCurrentInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getPhotoUrl()!=null) Glide.with(this).load(user.getPhotoUrl()).into(imgAvarChangeProfileUser);
        if (user.getDisplayName()!=null) edtNameUserChangeProfile.setText(user.getDisplayName());
        if (user.getPhoneNumber()!=null) edtPhoneNumberUserChangeProfile.setText(user.getPhoneNumber());
        edtEmailUserChangeProfile.setText(user.getEmail());
    }

    private void mapId(View view) {
        imgAvarChangeProfileUser = view.findViewById(R.id.imgAvarChangeProfileUser);
        edtNameUserChangeProfile = view.findViewById(R.id.edtNameUserChangeProfile);
        edtPhoneNumberUserChangeProfile = view.findViewById(R.id.edtPhoneNumberUserChangeProfile);
        edtEmailUserChangeProfile = view.findViewById(R.id.edtEmailUserChangeProfile);
        btnChangeProfile = view.findViewById(R.id.btnChangeProfile);
    }
    private void changeMenu(){
        NavigationView navigationView=getActivity().findViewById(R.id.navView);
        MainActivity mainActivity =new MainActivity();
        mainActivity.setMenuInflater(navigationView);
        TextView txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Trang chủ");
    }

}
