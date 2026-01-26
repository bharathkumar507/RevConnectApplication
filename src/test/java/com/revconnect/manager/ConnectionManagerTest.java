package com.revconnect.manager;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.revconnect.dao.ConnectionDao;
import com.revconnect.dao.UserDao;

public class ConnectionManagerTest {

    @Mock
    private ConnectionDao dao;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private ConnectionManager manager;

    public ConnectionManagerTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSendRequestValid(){

        when(userDao.existsById(2))
                .thenReturn(true);

        when(dao.requestExists(1,2))
                .thenReturn(false);

        boolean result =
                manager.sendRequest(1,2);

        assertTrue(result);
    }

    @Test
    void testSendRequestSelf(){

        boolean result =
                manager.sendRequest(1,1);

        assertFalse(result);
    }
}
