package com.udesc.healthier.singleton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udesc.healthier.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSingleton {

    private static UserSingleton instance;

    private Map<String, User> userMap = new HashMap<>();
    private User loggedUser;

    private UserSingleton (String json) {
        initialize(json);
    }

    public static synchronized UserSingleton getInstance(String json) {
        if (instance == null) {
            instance = new UserSingleton(json);
        }

        return instance;
    }

    private void initialize(String json) {
        List<User> userList = new Gson().fromJson(json, new TypeToken<List<User>>() {}.getType());

        if (userList == null) {
            userList = new ArrayList<>();
        }

        for (User user : userList) {
            userMap.put(user.getEmail(), user);
        }
    }

    public User getUser(String email) {
        return userMap.get(email);
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User user) {
        this.loggedUser = user;
    }

    public void addUser (User user) {
        userMap.put(user.getEmail(), user);
    }
}
