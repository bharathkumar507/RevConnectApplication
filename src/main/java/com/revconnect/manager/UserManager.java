package com.revconnect.manager;

import com.revconnect.dao.UserDao;
import com.revconnect.domain.User;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserManager implements UserService {

    private static final Logger logger =
            LogManager.getLogger(UserManager.class);

    private UserDao userDao;

    public UserManager(){
        this.userDao = new UserDao();
    }

    public UserManager(UserDao userDao){
        this.userDao = userDao;
    }

    public void register(User user) {
        userDao.saveUser(user);
        logger.info("User registered: {}", user.getUsername());
    }

    public User login(String username, String password) {

        logger.info("Login attempt for user: {}", username);

        User user =
                userDao.login(username,password);

        if(user != null)
            logger.info("Login successful for user: {}", username);
        else
            logger.warn("Invalid login attempt for user: {}", username);

        return user;
    }

    public List<User> search(String keyword){

        logger.info("User search with keyword: {}", keyword);

        return userDao.searchByUsername(keyword);
    }

    public boolean changePassword(int userId, String oldPass, String newPass){

        boolean ok =
                userDao.updatePassword(
                        userId, oldPass, newPass);

        if(ok)
            logger.info("Password changed for userId {}", userId);
        else
            logger.warn("Password change failed for userId {}", userId);

        return ok;
    }

    public String getSecurityQuestion(String username){

        logger.info("Security question requested for {}", username);

        return userDao.getSecurityQuestion(username);
    }

    public boolean resetPassword(String username, String answer, String newPass){

        boolean ok =
                userDao.resetPassword(
                        username,answer,newPass);

        if(ok)
            logger.info("Password reset successful for {}", username);
        else
            logger.warn("Password reset failed for {}", username);

        return ok;
    }

    public void updatePrivacy(int userId,
                              boolean isPrivate){

        userDao.updatePrivacy(userId,isPrivate);

        logger.info("Privacy updated for userId {} -> {}",
                userId,
                isPrivate ? "PRIVATE" : "PUBLIC");
    }

    public boolean usernameExists(String username){

        return userDao.usernameExists(username);
    }

    public boolean emailExists(String email){

        return userDao.emailExists(email);
    }

    public boolean isValidPassword(String password){

        if(password.length() < 8 || password.length() > 20)
            return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for(char ch : password.toCharArray()){

            if(Character.isUpperCase(ch))
                hasUpper = true;
            else if(Character.isLowerCase(ch))
                hasLower = true;
            else if(Character.isDigit(ch))
                hasDigit = true;
            else
                hasSpecial = true;
        }

        return hasUpper && hasLower &&
                hasDigit && hasSpecial;
    }
    public List<User> searchUsers(String keyword){
        return userDao.searchByUsername(keyword);
    }
    public boolean deleteAccount(int userId){
        return userDao.deleteUser(userId);
    }

}
