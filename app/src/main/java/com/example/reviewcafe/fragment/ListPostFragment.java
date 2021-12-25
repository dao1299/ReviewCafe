package com.example.reviewcafe.fragment;

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

import java.util.ArrayList;
import java.util.Arrays;

public class ListPostFragment extends Fragment {
    RecyclerView recyclerViewPost;
    PostAdapter postAdapter;
    ArrayList<PostModel> listPost = new ArrayList<>();

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
                Fragment fragment = DetailPostFragment.newInstance("data");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
            }
        });
        recyclerViewPost.setAdapter(postAdapter);
    }

    private void getData() {
        listPost.add(new PostModel("P001", "Highlands coffee 1", R.drawable.highlands, "Slick 123", "70000-100000", "Thanh Xuan"));
        listPost.add(new PostModel("P002", "Highlands coffee 2", R.drawable.favorite, "Slick 123", "70000-100000", "Thanh Xuan"));
        listPost.add(new PostModel("P003", "Highlands coffee 3", R.drawable.location, "Slick 123", "70000-100000", "Thanh Xuan"));
        listPost.add(new PostModel("P004", "Highlands coffee 4", R.drawable.logout, "Slick 123", "70000-100000", "Thanh Xuan"));
        listPost.add(new PostModel("P005", "Highlands coffee 5", R.drawable.person, "Slick 123", "70000-100000", "Thanh Xuan"));
        listPost.add(new PostModel("P006", "Highlands coffee 6", R.drawable.nfavorite, "Slick 123", "70000-100000", "Thanh Xuan"));
    }

}
