package com.revconnect.dao;

import com.revconnect.domain.User;

public interface UserRepo {

    void saveUser(User user);

    User findByEmail(String email);

    User findByUsername(String username);

}
