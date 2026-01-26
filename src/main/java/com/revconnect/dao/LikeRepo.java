package com.revconnect.dao;

public interface LikeRepo {

    void like(int userId,int postId);
    void unlike(int userId,int postId);
    boolean isLiked(int userId,int postId);
    int countLikes(int postId);

}
