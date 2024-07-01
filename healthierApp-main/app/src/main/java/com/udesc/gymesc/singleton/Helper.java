package com.udesc.gymesc.singleton;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udesc.gymesc.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

    public static final String STORAGE_FILE = "configuration_file";
    public static final String USER_REGISTERED = "user_registered";
    public static final String USER_LOGGED = "user_logged";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);


    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(STORAGE_FILE, 0);
    }

    public static void addNewUser(Context context, User user) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        Gson gson = new Gson();

        List<User> userList = gson.fromJson(preferences.getString(USER_REGISTERED, ""), new TypeToken<List<User>>() {}.getType());

        if (userList == null) {
            userList = new ArrayList<>();
        }

        UserSingleton.getInstance(gson.toJson(userList)).addUser(user);

        userList.add(user);

        preferences.edit().putString(USER_REGISTERED, gson.toJson(userList)).apply();
    }

    public static void doLogin(Context context, User user) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_LOGGED, new Gson().toJson(user)).apply();
        Helper.setLoggedUser(context, user);
    }

    public static void doLogout(Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_LOGGED, null).apply();
        Helper.setLoggedUser(context, null);
    }

    public static boolean validateEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static User findUser(Context context, String email) {
        return UserSingleton.getInstance(getDefaultSharedPreferences(context).getString(USER_REGISTERED, "")).getUser(email);
    }

    public static User findLoggedUser(Context context) {
        return UserSingleton.getInstance(getDefaultSharedPreferences(context).getString(USER_REGISTERED, "")).getLoggedUser();
    }

    public static void setLoggedUser(Context context, User user) {
        UserSingleton.getInstance(getDefaultSharedPreferences(context).getString(USER_REGISTERED, "")).setLoggedUser(user);
    }
}
