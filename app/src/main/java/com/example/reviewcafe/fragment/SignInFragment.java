package com.example.reviewcafe.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.reviewcafe.MainActivity;
import com.example.reviewcafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.aviran.cookiebar2.CookieBar;

public class SignInFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    private String data;

    private SignInFragment() {

    }

    public static SignInFragment newInstance(String data) {
        Fragment fragment = new SignInFragment();
        Bundle bundle = new Bundle();
        bundle.putString("DATA",data);
        fragment.setArguments(bundle);
        return new SignInFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signin_fragment,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button btnSignin = view.findViewById(R.id.btnSignin);
        EditText edtEmailSignin,edtPWSignin;
        edtEmailSignin = view.findViewById(R.id.edtEmailSignin);
        edtPWSignin = view.findViewById(R.id.edtPWSignin);
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getActivity(), "Đăng nhập","Đợi tí tẹo thui (>\"<)", true);
                addEventSignin(edtEmailSignin.getText().toString(),edtPWSignin.getText().toString());

        }});
    }

    private void addEventSignin(String id,String pw) {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(id,pw)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            setMenu();
                            showDialog("Đăng nhập", "Đăng nhập thành công!");
                            HomeFragment homeFragment = HomeFragment.newInstance("slicckkkkk");
                            FragmentManager fragmentManager = getParentFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.containerContentMain,homeFragment,"TAG").commit();

                        } else {
                            showDialog("Đăng nhập", "Có cái gì đó khum đúng rồi í.");
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void setMenu() {
        NavigationView navigationView = getActivity().findViewById(R.id.navView);
        MainActivity mainActivity = new MainActivity();
        mainActivity.setMenuInflater(navigationView);
        TextView txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Trang chủ");
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