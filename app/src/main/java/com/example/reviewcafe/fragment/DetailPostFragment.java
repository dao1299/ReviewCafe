package com.example.reviewcafe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.reviewcafe.R;
import com.example.reviewcafe.adapter.CommentAdapter;
import com.example.reviewcafe.adapter.PhotoAdapter;
import com.example.reviewcafe.model.CommentModel;
import com.example.reviewcafe.model.PhotoModel;
import com.example.reviewcafe.model.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class DetailPostFragment extends Fragment {
    PostModel postModel;
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<PhotoModel> listPhoto=new ArrayList<>();
    private List<CommentModel> listComment = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private CommentAdapter commentAdapter;
    private ImageView imgLikePost,imgDeletePost,imgEditPost,btnCommentPost;
    private TextView txtAuthor,txtTitle,txtPrice,txtTime,txtAddress,txtDescription,txtPostComment;
    private EditText edtCommentPost;
    private RecyclerView rcvComment;
    boolean like=false;

    private DetailPostFragment(){

    }
    public static DetailPostFragment newInstance(PostModel data){
        DetailPostFragment fragment = new DetailPostFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA",data);
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
        postModel=postTemp;
        View view = inflater.inflate(R.layout.detail_post,container,false);
        viewPager2 = view.findViewById(R.id.viewPagerDetailPost);
        circleIndicator3 = view.findViewById(R.id.circleIndicator3);
        getDataPhoto();
        photoAdapter = new PhotoAdapter(this,listPhoto);
        viewPager2.setAdapter(photoAdapter);
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
        btnCommentPost= view.findViewById(R.id.btnCommentPost);
        showDetailPost();
        addEventClick();
        getDataComment();
        setDisplayComment();
    }

    private void showDetailPost() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (!user.getUid().equals(postModel.getIdAuthor())){
            imgDeletePost.setVisibility(View.INVISIBLE);
            imgEditPost.setVisibility(View.INVISIBLE);
        }
        txtAuthor.setText(postModel.getAuthorPost());
        txtPrice.setText(postModel.getPricePost());
        txtTime.setText(postModel.getTime());
        txtAddress.setText(postModel.getAddressPost()+", "+postModel.getDistrict());
        txtDescription.setText(postModel.getDescription());
        txtTitle.setText(postModel.getTitlePost());
        for (String x: postModel.getListImg()){
            listPhoto.add(new PhotoModel(x));
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
                if (!like){
                    imgLikePost.setImageResource(R.drawable.favorite);
                    like=true;
                    showDialog("Thông báo","Vừa thêm vào danh sách yêu thích cho b rồi nhó");

                }else{
                    imgLikePost.setImageResource(R.drawable.nfavorite);
                    like=false;
                    showDialog("Thông báo","Đã gỡ danh sách yêu thích cho b rồi nhó");
                }
            }
        });
        imgEditPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        imgDeletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        txtPostComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtCommentPost.getText().toString().isEmpty()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.getPhotoUrl()==null){
                        listComment.add(new CommentModel("drawable://" + R.drawable.person,user.getDisplayName(),edtCommentPost.getText().toString()));
                    }else{
                        listComment.add(new CommentModel(user.getPhotoUrl().toString(),user.getDisplayName(),edtCommentPost.getText().toString()));
                    }
                    commentAdapter.notifyDataSetChanged();
                    edtCommentPost.setText("");
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


    private void getDataPhoto() {

    }
    private void showDialog(String title,String content){
        CookieBar.build(getActivity())
                .setTitle(title)
                .setIconAnimation(R.animator.scale_with_alpha)
                .setCookiePosition(CookieBar.TOP)
                .setMessage(content)
                .setDuration(3000) // 5 seconds
                .show();
    }
}
