package com.revconnect.manager;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.revconnect.dao.PostDao;
import com.revconnect.domain.Post;

public class PostManagerTest {

    @Mock
    private PostDao postDao;

    @InjectMocks
    private PostManager postManager;

    public PostManagerTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePost(){

        Post p = new Post();
        p.setContent("Hello");

        postManager.create(p);

        verify(postDao, times(1))
                .save(p);
    }

    @Test
    void testGetFeed(){

        List<Post> list =
                new ArrayList<>();

        when(postDao.getFeedByUsers(
                anyList(), eq(1)))
                .thenReturn(list);

        List<Post> result =
                postManager.getFeedForUser(
                        List.of(1,2),1);

        assertEquals(0, result.size());
    }
}
