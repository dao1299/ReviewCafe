package com.example.reviewcafe.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.reviewcafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.aviran.cookiebar2.CookieBar;

import java.util.ArrayList;
import java.util.List;

public class SignUpFragment extends Fragment {
    FirebaseAuth firebaseAuth;
    EditText edtNameUserSignup, edtEmailUserSignUp, edtPasswordSignup, edtConfirmPasswordSignup;
    Button btnSignup;
    Fragment fragment;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.signup_fragment,container,false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtNameUserSignup = view.findViewById(R.id.edtNameSignup);
        edtEmailUserSignUp = view.findViewById(R.id.edtOldPassword);
        edtPasswordSignup = view.findViewById(R.id.edtNewPassword);
        edtConfirmPasswordSignup = view.findViewById(R.id.edtConfirmNewPassword);
        btnSignup = view.findViewById(R.id.btnChangeProfile);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtConfirmPasswordSignup.getText().toString().equals(edtPasswordSignup.getText().toString())){
                    progressDialog = ProgressDialog.show(getActivity(), "Đăng ký","Đợi tí tẹo thôi mờ (>\"<)", true);
                    addEventSignup();
                }
                else{
                    showDialog("Mật khẩu không khớp rồi", "");
                }
            }

        });
    }

    private void addEventSignup() {
        firebaseAuth = FirebaseAuth.getInstance();
        if (!validateForm(edtEmailUserSignUp.getText().toString(),edtPasswordSignup.getText().toString())) return;
        firebaseAuth.createUserWithEmailAndPassword(edtEmailUserSignUp.getText().toString(),edtPasswordSignup.getText().toString())
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            updateProfile();
                            pushUserToFirebase();

                            showDialog("Đăng ký", "Đăng ký thành công roài nè!\nĐăng nhập đi nhó");
                            ViewPager viewPager = getActivity().findViewById(R.id.vpgLogin);
                            viewPager.setCurrentItem(0);
                        } else {
                            showDialog("Có cái gì đó khum đúng rồi í.", "");
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void pushUserToFirebase() {

    }

    private void updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(edtNameUserSignup.getText().toString())
                .build();
        List<String> listFavor = new ArrayList<>();
        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("user").push();
                        ref.child("email").setValue(user.getEmail());
                        ref.child("name").setValue(user.getDisplayName());
                        ref.child("Uid").setValue(user.getUid());
                        ref.child("list_favorite").setValue(listFavor);
                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateForm(String email, String pw) {
        boolean valid = true;
        if (email.isEmpty()) valid = false;
        if (pw.isEmpty() || pw.length() < 6) valid = false;
        return valid;
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
