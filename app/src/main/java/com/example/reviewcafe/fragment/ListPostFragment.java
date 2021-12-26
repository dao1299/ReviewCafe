package com.example.reviewcafe.fragment;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewcafe.R;
import com.example.reviewcafe.adapter.PostAdapter;
import com.example.reviewcafe.model.PostModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class ListPostFragment extends Fragment {
    RecyclerView recyclerViewPost;
    PostAdapter postAdapter;
    ArrayList<PostModel> listPost = new ArrayList<>();
    ProgressDialog progressDialog;

    private ListPostFragment(){

    }
    public static ListPostFragment newInstance(String data){
        Fragment fragment = new ListPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA",data);
        fragment.setArguments(bundle);
        return new ListPostFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_list_post,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewPost = view.findViewById(R.id.rcvListPost);
        getData();
        showData();
    }
    private void showData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewPost.setLayoutManager(manager);
        postAdapter=new PostAdapter(listPost, new PostAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
//                System.out.println(position);
                PostModel postModel = listPost.get(position);
                Fragment fragment = DetailPostFragment.newInstance(postModel);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
            }
        },getActivity());
        recyclerViewPost.setAdapter(postAdapter);
    }

    private void getData() {
        progressDialog = ProgressDialog.show(getActivity(),"" ,"Đợi tí tẹo ha (>\"<)", true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    listPost.add(dataSnapshot.getValue(PostModel.class));
                }
                postAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
