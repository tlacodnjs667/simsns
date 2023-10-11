package com.simsns.ver02_1.controller;

// user 객체 정보 반환 시 현재 몇 번 인덱스에 있는지 체크해서 반환하기

import com.simsns.ver02_1.model.User;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl instance;
    private final HashMap<UUID, User> users ;

    private UserDaoImpl() {
        this.users = DataUtil.getUsers() != null ?
                DataUtil.getUsers() : new HashMap<>();
    }

    public static UserDaoImpl getInstance () {
        if (instance == null) {
            instance = new UserDaoImpl();
        }
        return instance;
    }


@Override
    public boolean save (String id, String email, String password) {
        Set<UUID> keySet = users.keySet();

        for (UUID index : keySet) {
            User user = users.get(index);

            boolean isEmailDuplicated = user.getEmail().equals(email);
            boolean isIdDuplicated = user.getId().equals(id);

            if (isEmailDuplicated || isIdDuplicated) {
                return false;
            }
        }

        UUID idx = UUID.randomUUID();
        User user = new User(id, email,password);
        users.put(idx, user);

        DataUtil.reserveUsers(users);
        return true;
    }

    @Override
    public UUID getUser (String id, String email) {
        Set<UUID> keySet = users.keySet();

        if(id == null) { // 아이디가 눌이면
            for (UUID index : keySet) {

                if (users.get(index).getEmail().equals(email)) {
                    return index;
                }
            }
        }else if (email == null) { // 이메일이 눌이면
            for (UUID index : keySet) {
                if (users.get(index).getId().equals(id)) {
                    return index;
                }
            }
        }else {
            for (UUID index : keySet) {
                if (users.get(index).getId().equals(id) && users.get(index).getEmail().equals(email)) {
                    return index;
                }
            }
        }
        return null;
    } // userView.signIn

    @Override
    public boolean checkUserPassword (UUID index, String password) {
        return users.get(index).checkPassword(password);
    } // userView.signIn && modifyUserInfo

    @Override
    public int update (UUID index, String password) {
        int result = 0;
        users.get(index).setPassword(password);
        DataUtil.reserveUsers(users);
        result = 1;
        return result;
    }

    @Override
    public String findUser (UUID index, String email) {
        return index != null ? users.get(index).getId() : users.get(getUser(null, email)).getId();
    }

    @Override
    public int update (UUID index, String password, String email) {
        int result = 0;
        User user = users.get(index);

        if (user == null) {
            return result;
        }

        if (password.isEmpty() && email.isEmpty()) {
            return result;
        }

        if (user.getEmail().equals(email) && user.checkPassword(password)) {
            return result;
        }

        if (!password.isEmpty()) {
            users.get(index).setPassword(password);
        }

        if (!email.isEmpty()) {
            users.get(index).setEmail(email);
        }

        DataUtil.reserveUsers(users);

        result = 1;
        return result;
    }

    @Override
    public boolean erase (UUID index) {
        boolean result = false;
        result = users.remove(index) != null;
        if (result) {
            DataUtil.reserveUsers(users);
        }
        return result;
    }

}