package service;

import model.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class UserService {
    private static UserService userService = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return userService;
    }

    /* хранилище данных */
    private Map<Long, User> dataBase = Collections.synchronizedMap(new HashMap<>());
    /* счетчик id */
    private AtomicLong maxId = new AtomicLong(0);
    /* список авторизованных пользователей */
    private Map<Long, User> authMap = Collections.synchronizedMap(new HashMap<>());

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        dataBase.values().addAll(list);
        return list;
    }

    public User getUserById(Long id) {
        return dataBase.get(id);
    }

    public boolean addUser(User user) {
        boolean success = false;
        if (!dataBase.containsKey(user.getId())) {
            dataBase.put(user.getId(), user);
            success = true;
        }
        return success;
    }

    public void deleteAllUser() {
        dataBase.clear();
    }

    public boolean isExistsThisUser(User user) {
        boolean exist = false;
        for (User findUser : dataBase.values()) {
            if (user.equals(findUser)) {
                exist = true;
            }
        }
        return exist;
    }

    public List<User> getAllAuth() {
        List<User> list = new ArrayList<>();
        authMap.values().addAll(list);
        return list;
    }

    public boolean authUser(User user) {
        boolean auth = false;
        if (dataBase.containsKey(user.getId())) {
            auth = authMap.containsKey(user.getId());
        }
        return auth;
    }

    public void logoutAllUsers() {
        authMap.clear();
    }

    public boolean isUserAuthById(Long id) {
        return authMap.containsKey(id);
    }
}
