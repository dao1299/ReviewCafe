package com.example.reviewcafe.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.reviewcafe.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class TestUploadImage extends AppCompatActivity {
    ImageView img,img2;
    List<Uri> uriListImage=new ArrayList<>();
    TextView txtUri;
    List<String> listUri2Upload=new ArrayList<>();
    int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_upload_image);
//        List<String> listUrl = new ArrayList<>();
//        listUrl.add("123333");
//        listUrl.add("32222222222222");
//        listUrl.add("dao1299");

//        StorageReference
        img = findViewById(R.id.img);
        img2 = findViewById(R.id.img2);
        txtUri = findViewById(R.id.txtUri);

        Button btnGet = findViewById(R.id.btnGet);
        btnGet.setOnClickListener(view -> {
            checkPermision();

        });

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//        reference.child("test").setValue(listUrl, new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
//                System.out.println("Done");
//            }
//        });
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
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void addPhoto() {
//        TedBottomPicker.with(this)
//                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
//                    @Override
//                    public void onImageSelected(Uri uri) {
////                        progressDialog = ProgressDialog.show(getActivity(), "Cập nhật hồ sơ!", "Đợi tí tẹo thui (>\"<)", true);
//                        img.setImageURI(uri);
//                        upload2Firebase(uri);
////                        changePhoto(uri);
//                    }
//                });
        TedBottomPicker.with(this)
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        if (uriList!=null){
//                            uriListImage=uriList;
                            size=uriList.size();
                            uploadList2Firebase(uriList);
                        }
                    }
                });


//        if (uriListImage!=null){
//            for (Uri x:uriListImage){
//                upload2Firebase(x);
//            }
//        }
    }

    private void uploadList2Firebase(List<Uri> uriList) {
        for (Uri x:uriList){
            upload2Firebase(x);
        }
//        writeToFirebaseDatabase();
    }

    private void upload2Firebase(Uri filePath) {
        FirebaseStorage storage;
        StorageReference storageReference;
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        if (filePath != null) {
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                progressDialog.dismiss();
                                Toast.makeText(TestUploadImage.this,"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        showImg2(uri);
                                        listUri2Upload.add(uri.toString());
                                        txtUri.setText(""+listUri2Upload.size());
                                        if (listUri2Upload.size()==size){
                                            writeToFirebaseDatabase();
                                        }
                                    }
                                });
                            })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(TestUploadImage.this,"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                                double progress= (100.0* taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded "+ (int) progress + "%");
                            });
        }
    }

    private void writeToFirebaseDatabase() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("test2").setValue(listUri2Upload, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                System.out.println("Done");
            }
        });
    }
    private void showImg2(Uri uri) {
        Glide.with(TestUploadImage.this).load(uri).into(img2);
    }
}