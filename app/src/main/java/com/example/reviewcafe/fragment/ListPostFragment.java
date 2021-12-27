package com.example.reviewcafe.fragment;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewcafe.R;
import com.example.reviewcafe.adapter.PostAdapter;
import com.example.reviewcafe.model.PostModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;

public class ListPostFragment extends Fragment {
    int type;
    RecyclerView recyclerViewPost;
    PostAdapter postAdapter;
    ArrayList<PostModel> listPost = new ArrayList<>();
    ProgressDialog progressDialog;

    private ListPostFragment(){

    }
    public static ListPostFragment newInstance(String data){
        ListPostFragment fragment = new ListPostFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA",data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_list_post,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            switch (getArguments().getString("DATA")) {
                case "location":
                    type = 1;
                    break;
                case "favorite":
                    type = 2;
                    break;
                default:
                    addEventSearch();
                    type = 0;
                    break;
            }
        }
        super.onViewCreated(view, savedInstanceState);
        recyclerViewPost = view.findViewById(R.id.rcvListPost);
        getData();
        showData();
    }

    private void addEventSearch() {
        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) getActivity().findViewById(R.id.edtSearchHome);
        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                postAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                postAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }

    private void showData() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewPost.setLayoutManager(manager);
        postAdapter = new PostAdapter(listPost, new PostAdapter.ClickListener() {
            @Override
            public void onItemClick(int position) {
//                System.out.println(position);
                PostModel postModel = listPost.get(position);
                Fragment fragment = DetailPostFragment.newInstance(postModel, true);
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
            }
        },getActivity());
        recyclerViewPost.setAdapter(postAdapter);
    }

    private void getData() {
        progressDialog = ProgressDialog.show(getActivity(),"" ,"Đợi tí tẹo ha (>\"<)", true);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = database.getReference("post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    PostModel post = dataSnapshot.getValue(PostModel.class);
                    post.setIdPost(dataSnapshot.getKey());
                    switch (type) {
                        case 1:
                            if (post.getIdAuthor().equals(user.getUid())) {
                                listPost.add(post);
                            }
                            //danh sach's
                            break;
                        case 2:
                            if (post.getListUserLike() != null) {
                                if (post.getListUserLike().size() > 0) {
                                    for (String userId : post.getListUserLike()) {
                                        if (userId.equals(user.getUid())) {
                                            listPost.add(post);
                                        }
                                    }
                                }
                            }

                            break;
                        case 0:
                            listPost.add(post);
                            break;
                    }
                }
                postAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void showDialog(String title, String content) {
        CookieBar.build(getActivity())
                .setTitle(title)
                .setIconAnimation(R.animator.scale_with_alpha)
                .setCookiePosition(CookieBar.TOP)
                .setMessage(content)
                .setDuration(3000) // 5 seconds
                .show();
    }
}
