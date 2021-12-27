package com.example.reviewcafe.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.reviewcafe.R;
import com.google.android.material.navigation.NavigationView;

import org.aviran.cookiebar2.CookieBar;

import java.util.Arrays;

public class HomeFragment extends Fragment {
    private String nameUser;
    Spinner spinnerQuan, spinnerLoai;
    SearchView searchView;

    private HomeFragment() {

    }

    public static HomeFragment newInstance(String data) {
        HomeFragment fragment = new HomeFragment();
        Bundle bundle = new Bundle();
        System.out.println(data);
        bundle.putString("DATA", data);
        fragment.setArguments(bundle);
        return fragment;
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
        NavigationView navigationView = getActivity().findViewById(R.id.navView);
        TextView txtNameUserMenu = navigationView.getHeaderView(0).findViewById(R.id.txtNameUserMenu);
        txtNameUserMenu.setText(nameUser);
        spinnerQuan = view.findViewById(R.id.spinnerQuan);
        spinnerLoai = view.findViewById(R.id.spinnerLoai);
//        SearchManager manager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
//        searchView = (SearchView) view.findViewById(R.id.edtSearchHome);
//        searchView.setSearchableInfo(manager.getSearchableInfo(getActivity().getComponentName()));
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
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
//        spinnerQuan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                showDialog("hhh",spinnerQuan.getSelectedItem().toString());
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
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
