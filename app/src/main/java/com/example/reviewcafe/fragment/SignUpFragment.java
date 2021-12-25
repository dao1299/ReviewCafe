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

import com.example.reviewcafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUpFragment extends Fragment {
    FirebaseAuth  firebaseAuth;
    EditText edtNameUserSignup,edtEmailUserSignUp,edtPasswordSignup,edtConfirmPasswordSignup;
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
                    Toast.makeText(getActivity(), "Kiem tra lai mat khau", Toast.LENGTH_LONG).show();
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
                            Toast.makeText(getActivity(), "Đăng ký thành công roài nè!",
                                    Toast.LENGTH_SHORT).show();
                            fragment = HomeFragment.newInstance(edtNameUserSignup.getText().toString());
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
                        } else {
                            Toast.makeText(getActivity(), "Có cái gì đó khum đúng rồi í.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    private void updateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(edtNameUserSignup.getText().toString())
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateForm(String email,String pw) {
        boolean valid = true;
        if (email.isEmpty()) valid = false;
        if (pw.isEmpty() || pw.length()<6) valid = false;
        return valid;
    }

}
