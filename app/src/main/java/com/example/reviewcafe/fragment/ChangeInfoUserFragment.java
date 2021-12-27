package com.example.reviewcafe.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.reviewcafe.MainActivity;
import com.example.reviewcafe.R;
import com.example.reviewcafe.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import org.aviran.cookiebar2.CookieBar;

import java.util.List;
import java.util.UUID;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class ChangeInfoUserFragment extends Fragment {
    ProgressDialog progressDialog;
    CardView cardViewAvar;
    ImageView imgAvarChangeProfileUser;
    EditText edtNameUserChangeProfile, edtPhoneNumberUserChangeProfile, edtEmailUserChangeProfile;
    Button btnChangeProfile;
    String srcAvar, idUserInDB;
    UserModel userCurrent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_profile_user, container, false);
        getUserCurrent();
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
        btnChangeProfile.setOnClickListener(view -> {
            progressDialog = ProgressDialog.show(getActivity(), "Cập nhật hồ sơ!", "Đợi tí tẹo thui (>\"<)", true);
            changeProfile();
        });
        imgAvarChangeProfileUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermision();
            }
        });
    }

    private void checkPermision() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                addPhoto();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
//                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                showDialog("Cấp quyền bị từ chối", "");
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void addPhoto() {
        TedBottomPicker.with(getActivity())
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        progressDialog = ProgressDialog.show(getActivity(), "Cập nhật hồ sơ!", "Đợi tí tẹo thui (>\"<)", true);
                        imgAvarChangeProfileUser.setImageURI(uri);
                        upload2Firebase(uri);
                        changePhoto(uri);
                    }
                });
    }

    private void changePhoto(Uri uri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
//                            Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
                            showDialog("Thay hÌnh ảnh", "Thành công");
                            changeMenu();
                        }
                    }
                });
    }

    private void changeProfile() {
        if (!validate()) return;
        progressDialog.show();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(edtNameUserChangeProfile.getText().toString().trim())
                .build();

        changeMenu();


        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
//                            changeMenu();
                            showDialog("", "Thay đổi tên không thành công");
                        } else {
//                            NavigationView nav = getActivity().findViewById(R.id.navView);
//                            TextView txtNameUserMenu = nav.getHeaderView(0).findViewById(R.id.txtNameUserMenu);
//                            txtNameUserMenu.setText(edtNameUserChangeProfile.getText().toString().trim());
                        }
                    }
                });
        user.updateEmail(edtEmailUserChangeProfile.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (!task.isSuccessful()) {

                            showDialog("", "Thay đổi email không thành công");
                        }
                    }
                });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("user/" + idUserInDB);
        userCurrent.setName(edtNameUserChangeProfile.getText().toString().trim());
        userCurrent.setEmail(user.getEmail());
        reference.setValue(userCurrent, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                showDialog("Cập nhật hồ sơ thành công!", "");
                changeMenu();
                TextView txtTitle = getActivity().findViewById(R.id.txtTitle);
                txtTitle.setText("Trang chủ");
                getParentFragmentManager().beginTransaction().replace(R.id.containerContentMain, HomeFragment.newInstance(edtNameUserChangeProfile.getText().toString().trim()), "TAG").commit();
            }
        });
    }

    public void getUserCurrent() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid;
        if (user != null) {
            uid = user.getUid();
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("user");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        UserModel userTemp = dataSnapshot.getValue(UserModel.class);
                        if (userTemp.getUid().equals(uid)) {
                            userCurrent = userTemp;
                            idUserInDB = dataSnapshot.getKey();
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private boolean validate() {
        return !edtEmailUserChangeProfile.getText().toString().isEmpty()
                && !edtNameUserChangeProfile.getText().toString().isEmpty();
    }

    private void showCurrentInfo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getPhotoUrl() != null)
            Glide.with(this).load(user.getPhotoUrl()).into(imgAvarChangeProfileUser);
        if (user.getDisplayName() != null) edtNameUserChangeProfile.setText(user.getDisplayName());
        if (user.getPhoneNumber() != null)
            edtPhoneNumberUserChangeProfile.setText(user.getPhoneNumber());
        edtEmailUserChangeProfile.setText(user.getEmail());
    }

    private void mapId(View view) {
        imgAvarChangeProfileUser = view.findViewById(R.id.imgAvarChangeProfileUser);
        edtNameUserChangeProfile = view.findViewById(R.id.edtNameUserChangeProfile);
        edtPhoneNumberUserChangeProfile = view.findViewById(R.id.edtPhoneNumberUserChangeProfile);
        edtEmailUserChangeProfile = view.findViewById(R.id.edtEmailUserChangeProfile);
        btnChangeProfile = view.findViewById(R.id.btnChangeProfile);
    }

    private void changeMenu() {
        NavigationView navigationView = getActivity().findViewById(R.id.navView);
        MainActivity mainActivity = new MainActivity();
        mainActivity.setMenuInflater(navigationView);
//        TextView txtTitle = getActivity().findViewById(R.id.txtTitle);
//        txtTitle.setText("Trang chủ");
//        getParentFragmentManager().beginTransaction().replace(R.id.containerContentMain, HomeFragment.newInstance("data"), "TAG").commit();
    }

    private void upload2Firebase(Uri filePath) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        if (filePath != null) {
            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                progressDialog.dismiss();
//                                Toast.makeText(getActivity(),"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                                showDialog("Đã upload ảnh", "");
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        userCurrent.setUrlImg(uri.toString());
                                    }
                                });
                            })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        showDialog("Upload không thành công", "");
//                        Toast.makeText(getActivity(),"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded " + (int) progress + "%");
                    });
        }
    }

    private void showDialog(String title, String content) {
        CookieBar.build(getActivity())
                .setTitle(title)
                .setIconAnimation(R.animator.scale_with_alpha)
                .setCookiePosition(CookieBar.TOP)
                .setMessage(content)
                .setDuration(3000) // 5 seconds
                .show();
    }
}
