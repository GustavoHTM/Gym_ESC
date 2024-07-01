package com.udesc.healthier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.udesc.healthier.R;
import com.udesc.healthier.api.RetrofitClient;
import com.udesc.healthier.api.TokenManager;
import com.udesc.healthier.model.User;
import com.udesc.healthier.singleton.Helper;
import com.udesc.healthier.singleton.UserSingleton;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private MaterialToolbar topAppBar;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RetrofitClient.init(MainActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        loggedUser = Helper.findLoggedUser(this);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        topAppBar = findViewById(R.id.topAppBar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, topAppBar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
//            switch (item.getItemId()) {
//                case R.id.nav_workout:
//                    startActivity(new Intent(MainActivity.this, WorkoutActivity.class));
//                    drawerLayout.closeDrawers();
//                    return true;
//                case R.id.nav_diet:
//                    startActivity(new Intent(MainActivity.this, DietActivity.class));
//                    drawerLayout.closeDrawers();
//                    return true;
//                case R.id.nav_management:
//                    startActivity(new Intent(MainActivity.this, FormActivity.class));
//                    drawerLayout.closeDrawers();
//                    return true;
//                case R.id.nav_logout:
//                    logout();
//                    drawerLayout.closeDrawers();
//                    return true;
//                default:
//                    return false;
//            }
            return true;
        });

        // Atualizar o nome do usuário no cabeçalho de navegação
        updateNavHeader();
    }

    private void updateNavHeader() {
        TextView navUsername = navigationView.getHeaderView(0).findViewById(R.id.nav_username);
        navUsername.setText(loggedUser.getName());
    }

    private void logout() {
        new TokenManager(MainActivity.this).clearToken();
        RetrofitClient.destroy();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
