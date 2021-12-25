package com.example.reviewcafe.fragment;

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

import com.example.reviewcafe.R;
import com.example.reviewcafe.adapter.CommentAdapter;
import com.example.reviewcafe.adapter.PhotoAdapter;
import com.example.reviewcafe.adapter.PostAdapter;
import com.example.reviewcafe.model.CommentModel;
import com.example.reviewcafe.model.PhotoModel;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class DetailPostFragment extends Fragment {
    private ViewPager2 viewPager2;
    private CircleIndicator3 circleIndicator3;
    private List<PhotoModel> listPhoto=new ArrayList<>();
    private List<CommentModel> listComment = new ArrayList<>();
    private PhotoAdapter photoAdapter;
    private CommentAdapter commentAdapter;
    private ImageView imgLikePost;
    private TextView txtAuthor,txtTitle,txtPrice,txtTime,txtAddress,txtDescription;
    private EditText edtCommentPost;
    private RecyclerView rcvComment;
    boolean like=false;

    private DetailPostFragment(){

    }
    public static DetailPostFragment newInstance(String data){
        Fragment fragment = new DetailPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA",data);
        fragment.setArguments(bundle);
        return new DetailPostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        addEventClickLike();
        getDataComment();
        setDisplayComment();
    }

    private void setDisplayComment() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        rcvComment.setLayoutManager(manager);
        commentAdapter=new CommentAdapter(listComment, new CommentAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println(position);
            }
        });
        rcvComment.setAdapter(commentAdapter);
    }

    private void getDataComment() {
        listComment.add(new CommentModel(R.drawable.person,"Slick","Nice"));
        listComment.add(new CommentModel(R.drawable.person,"Slick 123","Nice"));
        listComment.add(new CommentModel(R.drawable.home,"Slic k","Nice try"));
        listComment.add(new CommentModel(R.drawable.person,"Sli ck","Nice cmm"));
        listComment.add(new CommentModel(R.drawable.person,"Sl ick","Nice vcl"));
        listComment.add(new CommentModel(R.drawable.person,"S   lick","Nice vch"));
        listComment.add(new CommentModel(R.drawable.person,"Slic  k","Nice oi la nice"));
        listComment.add(new CommentModel(R.drawable.person,"Sli    ck","Nice qua troi nice"));
    }
    private void getDetailPost(){
        txtAuthor.setText("Slick ");
    }
    private void addEventClickLike(){
        imgLikePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!like){
                    imgLikePost.setImageResource(R.drawable.favorite);
                    like=true;
                }else{
                    imgLikePost.setImageResource(R.drawable.nfavorite);
                    like=false;
                }
            }
        });
    }

    private void getDataPhoto() {
        listPhoto.add(new PhotoModel(R.drawable.nfavorite));
        listPhoto.add(new PhotoModel(R.drawable.favorite));
        listPhoto.add(new PhotoModel(R.drawable.person));
        listPhoto.add(new PhotoModel(R.drawable.home));
        listPhoto.add(new PhotoModel(R.drawable.location));
        listPhoto.add(new PhotoModel(R.drawable.person));
        listPhoto.add(new PhotoModel(R.drawable.home));
        listPhoto.add(new PhotoModel(R.drawable.person));
        listPhoto.add(new PhotoModel(R.drawable.home));
        listPhoto.add(new PhotoModel(R.drawable.person));
        listPhoto.add(new PhotoModel(R.drawable.home));
        listPhoto.add(new PhotoModel(R.drawable.logout));
    }
}
