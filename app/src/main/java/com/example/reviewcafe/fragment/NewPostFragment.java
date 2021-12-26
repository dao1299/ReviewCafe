package com.example.reviewcafe.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewcafe.R;
import com.example.reviewcafe.adapter.PhotoUriAdapter;
import com.example.reviewcafe.model.PostModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class NewPostFragment extends Fragment {
    int size=0;
    Spinner spinnerQuan,spinnerLoai;
    EditText edtTitleNewPost,edtDescriptionNewPost,edtDetailAddressNewPost,edtPriceNewPost,edtTimeNewPost;
    AppCompatImageButton btnAddPhoto;
    Button btnPostNewPost;
    RecyclerView rcvListPhoto;
    PhotoUriAdapter photoUriAdapter;
    List<Uri> uriListImage=new ArrayList<>();
    ProgressDialog progressDialog;
    List<String> listUri2Upload=new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_post_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapId(view);
        setSpinnerQuan();
        setSpinnerLoai();
        photoUriAdapter = new PhotoUriAdapter(view.getContext());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcvListPhoto.setLayoutManager(manager);
        rcvListPhoto.setAdapter(photoUriAdapter);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermision();
            }
        });
        btnPostNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getActivity(), "Đăng bài","Đợi tí tẹo thôi mờ (>\"<)", true);
                post();
            }
        });
    }

    private void post() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String urlThumb="";
        if (listUri2Upload.size()>0) urlThumb=listUri2Upload.get(0);
        PostModel post = new PostModel(user.getUid(),
                edtTitleNewPost.getText().toString().trim(),
                urlThumb,
                user.getDisplayName(),
                edtPriceNewPost.getText().toString().trim(),
                edtDetailAddressNewPost.getText().toString().trim(),
                edtTimeNewPost.getText().toString().trim(),
                edtDescriptionNewPost.getText().toString().trim(),
                listUri2Upload,
                spinnerQuan.getSelectedItem().toString(),
                spinnerLoai.getSelectedItem().toString()
                );
        writeToFirebaseDatabase(post);

//        DatabaseReference mDatabase;
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("post").push();
//        mDatabase.child("users").child("idPost").setValue(user.getUid());
//        ref.child("titlePost").setValue(edtTitleNewPost.getText().toString().trim());
//        ref.child("srcImg").setValue(uriListImage.get(0));
//        ref.child("authorPost").setValue(user.getDisplayName());
//        ref.child("pricePost").setValue(edtPriceNewPost.getText().toString().trim());
//        ref.child("addressPost").setValue(edtDetailAddressNewPost.getText().toString().trim());
//        ref.child("time").setValue(edtTimeNewPost.getText().toString().trim());
//        ref.child("description").setValue(edtDescriptionNewPost.getText().toString().trim());
//        ref.child("listImg").setValue(uriListImage);
//        ref.child("district").setValue(spinnerQuan.getSelectedItem().toString());
//        ref.child("theLoai").setValue(spinnerLoai.getSelectedItem().toString());
    }

    private void mapId(View view){
        spinnerQuan = view.findViewById(R.id.spinnerQuanNewPost);
        spinnerLoai = view.findViewById(R.id.spinnerLoaiNewPost);
        btnAddPhoto = view.findViewById(R.id.btnAddPhoto);
        rcvListPhoto = view.findViewById(R.id.rcvListImage);
        edtTitleNewPost = view.findViewById(R.id.edtTitleNewPost);
        edtDescriptionNewPost = view.findViewById(R.id.edtDescriptionNewPost);
        edtDetailAddressNewPost = view.findViewById(R.id.edtDetailAddressNewPost);
        edtPriceNewPost = view.findViewById(R.id.edtPriceNewPost);
        edtTimeNewPost = view.findViewById(R.id.edtTimeNewPost);
        btnPostNewPost = view.findViewById(R.id.btnPostNewPost);
    }

    private void checkPermision() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                addPhoto();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getActivity(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .check();
    }

    private void addPhoto() {
        listUri2Upload.clear();
//        TedBottomPicker.with(getActivity())
//                .setPeekHeight(1600)
//                .showTitle(false)
//                .setCompleteButtonText("Done")
//                .setEmptySelectionText("No Select")
//                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
//                    @Override
//                    public void onImagesSelected(List<Uri> uriList) {
//                        if (uriList!=null){
//                            photoUriAdapter.setDataPhoto(uriList);
//                            uriListImage=uriList;
//                        }
//                    }
//                });
//        System.out.println("Result: ");
//        for (Uri x:uriListImage){
//            System.out.println(x.getPath());
//        }

        TedBottomPicker.with(getActivity())
                .setPeekHeight(1600)
                .showTitle(false)
                .setCompleteButtonText("Done")
                .setEmptySelectionText("No Select")
                .showMultiImage(new TedBottomSheetDialogFragment.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(List<Uri> uriList) {
                        if (uriList!=null){
//                            uriListImage=uriList;
                            photoUriAdapter.setDataPhoto(uriList);
                            size=uriList.size();
                            uploadList2Firebase(uriList);
                        }
                    }
                });
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
            ProgressDialog progressDialog= new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        listUri2Upload.add(uri.toString());
                                    }
                                });
                            })
                    .addOnFailureListener(e -> {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    })
                    .addOnProgressListener(taskSnapshot -> {
                        double progress= (100.0* taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                        progressDialog.setMessage("Uploaded "+ (int) progress + "%");
                    });
        }
    }

    private void writeToFirebaseDatabase(PostModel post) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("post").push().setValue(post, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@com.google.firebase.database.annotations.Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Thành công", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setSpinnerQuan(){
        String[] listQuanHN = new String[]{"Hai Bà Trưng","Ba Đình","Bắc Từ Liêm","Nam Từ Liêm","Hà Đông","Hoàn Kiếm","Long Biên","Đống Đa","Cầu Giấy","Thanh Xuân","Tây Hồ","Hoàng Mai",};
        Arrays.sort(listQuanHN);
        ArrayAdapter<String> adapterSpinnerQuan = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,listQuanHN);
        spinnerQuan.setAdapter(adapterSpinnerQuan);

    }
    private void setSpinnerLoai() {
        String[] listLoaiCafe = new String[]{"Chanh xả","Bình dân","Làm việc","Sân vườn","Tán ngẫu","Đấm nhau","Chém nhau","Vỉa hè","Từ trên cao nhìn xuống"};
        Arrays.sort(listLoaiCafe);
        ArrayAdapter<String> adapterSpinnerLoai = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item,listLoaiCafe);
        spinnerLoai.setAdapter(adapterSpinnerLoai);
    }
}
