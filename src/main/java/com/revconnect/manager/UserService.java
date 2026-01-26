package com.revconnect.manager;

import com.revconnect.domain.User;

import java.util.List;

public interface UserService {

    void register(User user);

    User login(String username, String password);
    public List<User> search(String keyword);
    boolean changePassword(int userId, String oldPass, String newPass);
    String getSecurityQuestion(String username);

    boolean resetPassword(String username, String answer, String newPass);

    void updatePrivacy(int userId, boolean isPrivate);
    boolean usernameExists(String username);
    boolean emailExists(String email);
    boolean isValidPassword(String password);
    boolean deleteAccount(int userId);


}
