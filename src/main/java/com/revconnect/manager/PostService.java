package com.revconnect.manager;

import java.util.List;
import com.revconnect.domain.Post;

public interface PostService {

    void create(Post post);

    List<Post> getMyPosts(int userId);

    List<Post> getFeed();
    public void editPost(int postId, String content, String hashtags);

    void deletePost(int postId);
}
