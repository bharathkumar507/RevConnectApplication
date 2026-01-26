package com.revconnect.dao;

import java.util.List;
import com.revconnect.domain.Comment;

public interface CommentRepo {

    void add(Comment c);
    void update(int commentId,String text);
    void delete(int commentId);
    List<Comment> getByPost(int postId);
    public int getPostOwner(int postId);
}
