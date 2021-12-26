package com.example.reviewcafe;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.reviewcafe.fragment.HomeFragment;
import com.example.reviewcafe.fragment.InfoUserFragment;
import com.example.reviewcafe.fragment.ListPostFragment;
import com.example.reviewcafe.fragment.LoginFragment;
import com.example.reviewcafe.fragment.NewPostFragment;
import com.example.reviewcafe.fragment.SignInFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView txtTitle;
    Menu nav_Menu;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        txtTitle = findViewById(R.id.txtTitle);
        drawerLayout = findViewById(R.id.drawer_layout_menu);
        navigationView = findViewById(R.id.navView);
        setMenuInflater(navigationView);
        navigationView.bringToFront();
        nav_Menu = navigationView.getMenu();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        ImageView imgShowMenu = findViewById(R.id.imgShowMenu);
        imgShowMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        displayView(R.id.menu_add_post);
    }

    public void setMenuInflater(NavigationView navigationView) {
        navigationView.getMenu().clear();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        TextView txtNameUserMenu = navigationView.getHeaderView(0).findViewById(R.id.txtNameUserMenu);
        ImageView imgAvarUserMenu = navigationView.getHeaderView(0).findViewById(R.id.imgAvarMenu);
        CardView cardView = navigationView.getHeaderView(0).findViewById(R.id.cardViewAvarMenu);
        if (firebaseUser==null)
        {
            navigationView.inflateMenu(R.menu.main_menu1);
            txtNameUserMenu.setText("");
            cardView.setVisibility(View.INVISIBLE);
            imgAvarUserMenu.setVisibility(View.INVISIBLE);
        }
            else
        {
            txtNameUserMenu.setText(firebaseUser.getDisplayName()+"");
            cardView.setVisibility(View.VISIBLE);
            if (firebaseUser.getPhotoUrl()==null){
                imgAvarUserMenu.setImageResource(R.drawable.person);
            }else{
                imgAvarUserMenu.setImageURI(firebaseUser.getPhotoUrl());
            }
            navigationView.inflateMenu(R.menu.main_menu2);
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displayView(item.getItemId());
        drawerLayout.closeDrawer(Gravity.LEFT);
        return true;
    }


    public void displayView(int itemId) {
        Fragment fragment = null;
        switch (itemId){
            case R.id.menu_favorite:
                txtTitle.setText("Danh sách yêu thích");
                fragment= ListPostFragment.newInstance("favorite");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
                break;
            case R.id.menu_login:
                txtTitle.setText("Đăng nhập");
                fragment = LoginFragment.newInstance("321");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
                break;
            case R.id.menu_logout:
                progressDialog = ProgressDialog.show(MainActivity.this, "Đăng xuất","Đợi tí tẹo thôi mờ (>\"<)", true);
                FirebaseAuth.getInstance().signOut();
                drawerLayout.closeDrawer(Gravity.LEFT);
                setMenuInflater(navigationView);
                displayView(R.id.menu_home);
                progressDialog.dismiss();
                break;
            case R.id.menu_location:
                txtTitle.setText("Danh sách bài đóng góp");
                fragment= ListPostFragment.newInstance("location");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
                break;
            case R.id.menu_home:
                txtTitle.setText("Trang chủ");
                fragment = HomeFragment.newInstance("data");
                getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
                break;
            case R.id.menu_profile:
                txtTitle.setText("Trang cá nhân");
                fragment = new InfoUserFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
                break;
            case R.id.menu_add_post:
                txtTitle.setText("Thêm bài mới");
                fragment = new NewPostFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.containerContentMain,fragment,"TAG").commit();
                break;
        }
    }
}
