package com.example.reviewcafe.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.reviewcafe.R;
import com.example.reviewcafe.adapter.CommentAdapter;
import com.example.reviewcafe.adapter.PhotoAdapter;
import com.example.reviewcafe.model.CommentModel;
import com.example.reviewcafe.model.PhotoModel;
import com.example.reviewcafe.model.PostModel;
import com.example.reviewcafe.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class DetailPostFragment extends Fragment {
    PostModel postModel;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private final List<PhotoModel> listPhoto = new ArrayList<>();
    private final List<CommentModel> listComment = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private CommentAdapter commentAdapter;
    boolean like;
    private TextView txtAuthor, txtTitle, txtPrice, txtTime, txtAddress, txtDescription, txtPostComment;
    private EditText edtCommentPost;
    private RecyclerView rcvComment;
    List<String> listUserLike = new ArrayList<>();
    private ImageView imgLikePost, imgDeletePost, imgEditPost, btnCommentPost, imgAvarDetailPost;
    private List<String> listFavor;
    private String idUserInDB;
    private UserModel userCurrent;

    private DetailPostFragment() {

    }

    public static DetailPostFragment newInstance(PostModel data, boolean liked) {
        DetailPostFragment fragment = new DetailPostFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA", data);
        bundle.putBoolean("LIKED", liked);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PostModel postTemp = (PostModel) getArguments().getSerializable("DATA");
        postModel = postTemp;
        System.out.println(postModel);
        View view = inflater.inflate(R.layout.detail_post,container,false);
        viewPager2 = view.findViewById(R.id.viewPagerDetailPost);
        circleIndicator3 = view.findViewById(R.id.circleIndicator3);
        getDataPhoto();
        photoAdapter = new PhotoAdapter(this,listPhoto);
        viewPager2.setAdapter(photoAdapter);
        getUserCurrent();
        circleIndicator3.setViewPager(viewPager2);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgLikePost = view.findViewById(R.id.btnLikePost);
        rcvComment = view.findViewById(R.id.rcvCommentDetailPost);
        txtAuthor = view.findViewById(R.id.txtAuthorDetailPost);
        txtTitle = view.findViewById(R.id.txtTitleDetailPost);
        txtAddress = view.findViewById(R.id.txtAddressDetailPost);
        txtPrice = view.findViewById(R.id.txtPriceDetailPost);
        txtDescription = view.findViewById(R.id.txtDescriptionDetailPost);
        txtTime = view.findViewById(R.id.txtTimeDetailPost);
        edtCommentPost = view.findViewById(R.id.edtCommentDetailPost);
        imgDeletePost = view.findViewById(R.id.btnDelete);
        imgEditPost = view.findViewById(R.id.btnEdit);
        txtPostComment = view.findViewById(R.id.txtPostComment);
        btnCommentPost = view.findViewById(R.id.btnCommentPost);
        imgAvarDetailPost = view.findViewById(R.id.imgAvarDetailPost);
        showDetailPost();
        addEventClick();
        getDataComment();
        setDisplayComment();
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

    private void showDetailPost() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            imgDeletePost.setVisibility(View.INVISIBLE);
            imgEditPost.setVisibility(View.INVISIBLE);
        } else {
//            findUser(user.getUid());
            if (!user.getUid().equals(postModel.getIdAuthor())) {
                imgDeletePost.setVisibility(View.INVISIBLE);
                imgEditPost.setVisibility(View.INVISIBLE);
            }
            if (userCurrent == null) {
                imgLikePost.setImageResource(R.drawable.nfavorite);
            } else {

            }
//            if (userCurrent.getListFavorite()!=null){
//                if (userCurrent.getListFavorite().size()>0) {
//                    for (String idPost : userCurrent.getListFavorite()) {
//                        if (idPost.equals(postModel.getIdPost())) {
//                            imgLikePost.setImageResource(R.drawable.favorite);
//                            break;
//                        }
//                        imgLikePost.setImageResource(R.drawable.nfavorite);
//                    }
//                }
//            }else{
//
//            }
        }


        txtAuthor.setText(postModel.getAuthorPost());
        txtPrice.setText(postModel.getPricePost());
        txtTime.setText(postModel.getTime());
        txtAddress.setText(postModel.getAddressPost() + ", " + postModel.getDistrict());
        txtDescription.setText(postModel.getDescription());
        txtTitle.setText(postModel.getTitlePost());
        if (postModel.getListImg() != null) {
            if (postModel.getListImg().size() > 0)
                for (String x : postModel.getListImg()) {
                    listPhoto.add(new PhotoModel(x));
                }
        }
        imgLikePost.setImageResource(R.drawable.nfavorite);
        if (postModel.getListUserLike() != null) {
            if (postModel.getListUserLike().size() > 0) {
                for (String x : postModel.getListUserLike()) {
                    listUserLike.add(x);
                    if (x.equals(user.getUid())) {
                        imgLikePost.setImageResource(R.drawable.favorite);
                        like = true;
                    }
                }
            }
        }
        if (postModel.getImgAuthor() != null) {
            Glide.with(getActivity()).load(postModel.getImgAuthor()).into(imgAvarDetailPost);
        }

    }

    private void setDisplayComment() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rcvComment.setLayoutManager(manager);
        commentAdapter=new CommentAdapter(listComment, new CommentAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println(position);
            }
        },getContext());
        rcvComment.setAdapter(commentAdapter);
    }

    private void getDataComment() {

    }
    private void getDetailPost(){
        txtAuthor.setText("Slick ");
    }
    private void addEventClick(){
        imgLikePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    showDialog("Thông báo", "Bạn chưa đăng nhập mà!");
                } else {
                    if (!like) {
                        imgLikePost.setImageResource(R.drawable.favorite);
                        like = true;
                        updateFavorite(like);
                        showDialog("Thông báo", "Vừa thêm vào danh sách yêu thích cho bạn rồi nhó");
                    } else {
                        imgLikePost.setImageResource(R.drawable.nfavorite);
                        like = false;
                        updateFavorite(like);
                        showDialog("Thông báo", "Đã gỡ danh sách yêu thích cho b rồi nhó");
                    }
                }
            }
        });
        imgEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = NewPostFragment.newInstance(postModel);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain, fragment).commit();
            }
        });
        imgDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setTitle("Xác nhận");
                dialogBuilder.setMessage("Bạn có chắc chắn xóa bài viết này không?");
                dialogBuilder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference reference = database.getReference("post/" + postModel.getIdPost());
                        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                showDialog("Xóa bài viết", "Thành công");
                            }
                        });

                    }
                });
                dialogBuilder.setPositiveButton("Thôi", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });
        txtPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtCommentPost.getText().toString().isEmpty()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user == null) {
                        showDialog("Thông báo", "Bạn chưa đăng nhập mà!");
                    } else {
                        if (user.getPhotoUrl() == null) {
                            listComment.add(new CommentModel("drawable://" + R.drawable.person, user.getDisplayName(), edtCommentPost.getText().toString()));
                        } else {
                            listComment.add(new CommentModel(user.getPhotoUrl().toString(), user.getDisplayName(), edtCommentPost.getText().toString()));
                        }
                        commentAdapter.notifyDataSetChanged();
                        edtCommentPost.setText("");
                        rcvComment.requestFocus();
                    }
                }else{
                    showDialog("Thông báo","Bình luận thì phải viết gì gì vào chứ");
                }
            }
        });
        btnCommentPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtCommentPost.requestFocus();
            }
        });
    }

    private void updateFavorite(boolean like) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
//        if (like==true){
//            String userId = findUser(uid);
//            for (String idPost: userCurrent.getListFavorite()){
//                if (idPost)
//            }
//            userCurrent.getListFavorite()
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference reference = database.getReference("user/"+userId);
//            reference.child("listFavorite")
//        }else{
//
//        }
//        List<String> listFavor=new ArrayList<>();
        if (like == true) {
//            if (userCurrent.getListFavorite()==null){
//                List<String> list = new ArrayList<>();
//                list.add(postModel.getIdPost());
//                userCurrent.setListFavorite(list);
//            }else{
//                userCurrent.getListFavorite().add(postModel.getIdPost());
//            }
            listUserLike.add(userCurrent.getUid());
        } else {
            listUserLike.removeIf(element -> (element.equals(uid)));
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("post/" + postModel.getIdPost() + "/listUserLike");
        reference.setValue(listUserLike, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {

            }
        });
    }

    private void findUser(String uid) {
        final String[] resultFunction = new String[1];
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("user");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    userCurrent = dataSnapshot.getValue(UserModel.class);
                    if (userCurrent.getUid().equals(uid)) {
                        resultFunction[0] = dataSnapshot.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
//        return resultFunction[0];
    }


    private void getDataPhoto() {

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
