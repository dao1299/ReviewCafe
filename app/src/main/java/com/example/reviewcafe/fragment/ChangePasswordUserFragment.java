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

import com.example.reviewcafe.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.aviran.cookiebar2.CookieBar;

public class ChangePasswordUserFragment extends Fragment {
    EditText edtOldPassword,edtNewPassword,edtConfirmNewPassword;
    Button btnChangePassword;
    ProgressDialog progressDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_password_user,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edtOldPassword = view.findViewById(R.id.edtOldPassword);
        edtNewPassword = view.findViewById(R.id.edtNewPassword);
        edtConfirmNewPassword = view.findViewById(R.id.edtConfirmNewPassword);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        TextView txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtTitle.setText("Thay đổi mật khẩu");
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(getActivity(), "Thay đổi mật khẩu", "Đợi tí có được khum? (>\"<)", true);
                progressDialog.setCancelable(true);
                changePasswordEvent();
            }
        });
    }

    private void changePasswordEvent() {
        String newPw,newCPw;
        newPw=edtNewPassword.getText().toString();
        newCPw=edtConfirmNewPassword.getText().toString();
        if (newPw.isEmpty() || newCPw.isEmpty() || !newPw.equals(newCPw)) {
//            Toast.makeText(getActivity(), "Kiểm tra lại các mật khẩu!", Toast.LENGTH_SHORT).show();
            showDialog("Kiểm tra lại các mật khẩu!", "");
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPw)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            showDialog("Đổi mật khẩu thành công!", "");
//                            Toast.makeText(getActivity(), "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            showDialog("Đổi mật khẩu suýt thành công!", "");
//                            Toast.makeText(getActivity(), "Đổi mật khẩu suýt thành công!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
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
