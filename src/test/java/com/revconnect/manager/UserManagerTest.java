package com.revconnect.manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.revconnect.dao.UserDao;
import com.revconnect.domain.User;

public class UserManagerTest {

    @Test
    void testLoginSuccess(){

        UserDao userDao = mock(UserDao.class);
        UserManager manager =
                new UserManager(userDao);

        User u = new User();
        u.setUsername("bharath");

        when(userDao.login("bharath","pass"))
                .thenReturn(u);

        User result =
                manager.login("bharath","pass");

        assertNotNull(result);
        assertEquals("bharath",
                result.getUsername());
    }

    @Test
    void testLoginFailure(){

        UserDao userDao = mock(UserDao.class);
        UserManager manager =
                new UserManager(userDao);

        when(userDao.login("x","y"))
                .thenReturn(null);

        User result =
                manager.login("x","y");

        assertNull(result);
    }
}
