package com.revconnect.dao;

import java.util.List;
import com.revconnect.domain.Post;

public interface PostRepo {

    void save(Post post);

    List<Post> findByUserId(int userId);

    List<Post> findAll();

    void delete(int postId);
    public List<Post> getFeedByUsers(List<Integer> userIds, int filter);

}
