package com.udesc.healthier.activity;

import static com.udesc.healthier.singleton.Helper.USER_LOGGED;
import static com.udesc.healthier.singleton.Helper.USER_REGISTERED;
import static com.udesc.healthier.singleton.Helper.doLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.google.gson.Gson;
import com.udesc.healthier.api.ApiService;
import com.udesc.healthier.api.dto.LoginRequestDTO;
import com.udesc.healthier.api.dto.LoginResponseDTO;
import com.udesc.healthier.R;
import com.udesc.healthier.api.RetrofitClient;
import com.udesc.healthier.api.TokenManager;
import com.udesc.healthier.model.User;
import com.udesc.healthier.singleton.Helper;
import com.udesc.healthier.singleton.UserSingleton;

import org.apache.commons.lang3.StringUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private TextView registryButton;
    private boolean isPasswordVisible = false;
    private ImageView passwordToggleIcon;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registryButton = findViewById(R.id.registryTextView);
        passwordToggleIcon = findViewById(R.id.passwordToggleIcon);

        preferences = Helper.getDefaultSharedPreferences(this);
        User loggedUser = new Gson().fromJson(preferences.getString(USER_LOGGED, null), User.class);
        if (loggedUser != null) {
            Helper.doLogin(this, loggedUser);
            redirectToMain();
        }

        validateAlreadyLogged();
        loginButton.setOnClickListener(v -> performLogin());
        registryButton.setOnClickListener(v -> redirectToRegistration());
        passwordToggleIcon.setOnClickListener(v -> togglePasswordVisibility());

        this.formatTitle();
    }

    private void formatTitle() {
        TextView logoTextView = findViewById(R.id.logoTextView);
        String text = "GymESC";

        SpannableString spannable = new SpannableString(text);

        ForegroundColorSpan gymColorSpan = new ForegroundColorSpan(Color.parseColor("#1F1F3D"));
        spannable.setSpan(gymColorSpan, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ForegroundColorSpan escColorSpan = new ForegroundColorSpan(Color.WHITE); // ou substitua por outra cor desejada
        spannable.setSpan(escColorSpan, 3, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        logoTextView.setText(spannable);
    }
    private void performLogin() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        User loggedUser = Helper.findUser(this, email);

        if (loggedUser == null) {
            Toast.makeText(LoginActivity.this, "Usuário inexistente", Toast.LENGTH_SHORT).show();
        } else if (loggedUser.isValidPassword(password)) {
            RetrofitClient.init(LoginActivity.this);
            Toast.makeText(LoginActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
            Helper.doLogin(this, loggedUser);
            redirectToMain();
        } else {
            Toast.makeText(LoginActivity.this, "Senha incorreta", Toast.LENGTH_SHORT).show();
        }

//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Call<LoginResponseDTO> call = apiService.login(new LoginRequestDTO(email, password));
//
//        call.enqueue(new Callback<LoginResponseDTO>() {
//            @Override
//            public void onResponse(Call<LoginResponseDTO> call, Response<LoginResponseDTO> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    TokenManager tokenManager = new TokenManager(LoginActivity.this);
//                    tokenManager.saveToken(response.body().getToken());
//                    RetrofitClient.init(LoginActivity.this);
//                    Toast.makeText(LoginActivity.this, "Login bem-sucedido", Toast.LENGTH_SHORT).show();
//                    redirectToMain();
//                } else {
//                    Toast.makeText(LoginActivity.this, "Não foi possível efetuar seu Login. Tente novamente", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
//                // Erro de rede
//                Toast.makeText(LoginActivity.this, "Erro de rede", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            passwordToggleIcon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_remove_red_eye_24));
        } else {
            passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            passwordToggleIcon.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.baseline_remove_red_eye_24_active));
        }
        isPasswordVisible = !isPasswordVisible;
        passwordEditText.setSelection(passwordEditText.length());
    }

    private void validateAlreadyLogged() {
        TokenManager tokenManager = new TokenManager(LoginActivity.this);
        if (tokenManager.getToken() != null) redirectToMain();
    }

    private void redirectToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void redirectToRegistration() {
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }
}