package com.udesc.gymesc.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import com.udesc.gymesc.R;
import com.udesc.gymesc.enums.TrainingLevel;
import com.udesc.gymesc.model.User;
import com.udesc.gymesc.singleton.Helper;

public class RegistrationActivity extends AppCompatActivity {

    private static final Integer SPINNER_DEFAULT_POSITION = 0;

    private EditText nameEditText, emailEditText, weightEditText, heightEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private Boolean isPasswordVisible = false, isConfirmPasswordVisible = false;
    private Spinner trainingLevelSpinner;
    private ImageView passwordToggleIcon, confirmPasswordToggleIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        nameEditText = findViewById(R.id.nameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        weightEditText = findViewById(R.id.weightEditText);
        heightEditText = findViewById(R.id.heightEditText);
        trainingLevelSpinner = findViewById(R.id.trainingLevelSpinner);
        passwordToggleIcon = findViewById(R.id.passwordToggleIcon);
        confirmPasswordToggleIcon = findViewById(R.id.repeatPasswordToggleIcon);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(v -> performLogin());
        passwordToggleIcon.setOnClickListener(v -> togglePasswordVisibility(passwordEditText, passwordToggleIcon, true));
        confirmPasswordToggleIcon.setOnClickListener(v -> togglePasswordVisibility(confirmPasswordEditText, confirmPasswordToggleIcon, false));

        ArrayAdapter<TrainingLevel> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, TrainingLevel.values());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        trainingLevelSpinner.setAdapter(adapter);

        formatTitle();
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
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String weight = weightEditText.getText().toString().trim();
        String height = heightEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = confirmPasswordEditText.getText().toString().trim();
        Integer trainingLevel = trainingLevelSpinner.getSelectedItemPosition();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(weight) ||
                TextUtils.isEmpty(height) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repeatPassword)) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!Helper.validateEmail(email)) {
            Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(repeatPassword)) {
            Toast.makeText(this, "As senhas não conferem", Toast.LENGTH_SHORT).show();
            return;
        }

        if (SPINNER_DEFAULT_POSITION.equals(trainingLevel)) {
            Toast.makeText(this, "Por favor, selecione o nível de treinamento", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = new User(name, email, password, Double.parseDouble(weight), Double.parseDouble(height), TrainingLevel.values()[trainingLevel]);
        Helper.addNewUser(this, user);

        Toast.makeText(RegistrationActivity.this, "Cadastro bem-sucedido.", Toast.LENGTH_SHORT).show();
        redirectToLogin();

//        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
//        Call<Void> call = apiService.registry(new CreateUserRequestDTO(name, email, password));
//
//        call.enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(RegistrationActivity.this, "Cadastro bem-sucedido.", Toast.LENGTH_SHORT).show();
//                    redirectToLogin();
//                } else {
//                    Toast.makeText(RegistrationActivity.this, "Não foi possível efetuar seu cadastro. Tente novamente", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                // Erro de rede
//                Toast.makeText(RegistrationActivity.this, "Erro de rede", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void togglePasswordVisibility(EditText passwordEditText, ImageView passwordToggleIcon, boolean normal) {
        if (normal) {
            if (isPasswordVisible) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordToggleIcon.setImageDrawable(AppCompatResources.getDrawable(getBaseContext(), R.drawable.baseline_remove_red_eye_24));
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordToggleIcon.setImageDrawable(AppCompatResources.getDrawable(getBaseContext(), R.drawable.baseline_remove_red_eye_24_active));
            }
            isPasswordVisible = !isPasswordVisible;
            passwordEditText.setSelection(passwordEditText.length());
        } else {
            if (isConfirmPasswordVisible) {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                passwordToggleIcon.setImageDrawable(AppCompatResources.getDrawable(getBaseContext(), R.drawable.baseline_remove_red_eye_24));
            } else {
                passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                passwordToggleIcon.setImageDrawable(AppCompatResources.getDrawable(getBaseContext(), R.drawable.baseline_remove_red_eye_24_active));
            }
            isConfirmPasswordVisible = !isConfirmPasswordVisible;
            passwordEditText.setSelection(passwordEditText.length());
        }
    }

    private void redirectToLogin() {
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}