package com.udesc.gymesc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.udesc.gymesc.R;
import com.udesc.gymesc.api.RetrofitClient;
import com.udesc.gymesc.api.TokenManager;
import com.udesc.gymesc.fragment.WorkoutFragment;
import com.udesc.gymesc.fragment.ExerciseFragment;
import com.udesc.gymesc.fragment.AccountFragment;
import com.udesc.gymesc.model.User;
import com.udesc.gymesc.singleton.Helper;

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

        // Load the default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new WorkoutFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_workout);
            topAppBar.setTitle("Treinos");
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            String title = null;
            int id = item.getItemId();
            if (id == R.id.nav_workout) {
                selectedFragment = new WorkoutFragment();
                title = "Treinos";
            } else if (id == R.id.nav_diet) {
                selectedFragment = new ExerciseFragment();
                title = "Exercícios";
            } else if (id == R.id.nav_management) {
                selectedFragment = new AccountFragment();
                title = "Conta";
            } else if (id == R.id.nav_logout) {
                logout();
                drawerLayout.closeDrawers();
                return true;
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        selectedFragment).commit();
                topAppBar.setTitle(title);
                drawerLayout.closeDrawers();
            }
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
        Helper.doLogout(this);
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
