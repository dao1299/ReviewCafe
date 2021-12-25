package com.example.reviewcafe.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reviewcafe.R;

import java.util.Arrays;

public class HomeFragment extends Fragment {
    private String nameUser;
    Spinner spinnerQuan, spinnerLoai;
    private HomeFragment() {

    }

    public static HomeFragment newInstance(String data) {
        Fragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        System.out.println(data);
        bundle.putString("DATA", data);
        fragment.setArguments(bundle);
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            this.nameUser = getArguments().getString("DATA");
        }
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerQuan = view.findViewById(R.id.spinnerQuan);
        spinnerLoai = view.findViewById(R.id.spinnerLoai);
        setSpinnerQuan();
        setSpinnerLoai();
        Fragment fragment = ListPostFragment.newInstance("home");
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayoutRcvPost, fragment, "TAG").commit();
    }

    private void setSpinnerLoai() {
        String[] listLoaiCafe = new String[]{" Loại cà phê", "Chanh xả", "Bình dân", "Làm việc", "Sân vườn", "Tán ngẫu", "Đấm nhau", "Chém nhau", "Vỉa hè", "Từ trên cao nhìn xuống"};
        Arrays.sort(listLoaiCafe);
        ArrayAdapter<String> adapterSpinnerLoai = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listLoaiCafe);
        spinnerLoai.setAdapter(adapterSpinnerLoai);
    }

    private void setSpinnerQuan() {
        String[] listQuanHN = new String[]{" Quận", "Hai Bà Trưng", "Ba Đình", "Bắc Từ Liêm", "Nam Từ Liêm", "Hà Đông", "Hoàn Kiếm", "Long Biên", "Đống Đa", "Cầu Giấy", "Thanh Xuân", "Tây Hồ", "Hoàng Mai",};
        Arrays.sort(listQuanHN);
        ArrayAdapter<String> adapterSpinnerQuan = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, listQuanHN);
        spinnerQuan.setAdapter(adapterSpinnerQuan);
    }
}
