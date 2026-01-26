package com.revconnect.dao;

import java.util.List;

public interface FollowRepo {

    void follow(int followerId, int followingId);

    void unfollow(int followerId, int followingId);

    List<Integer> getFollowers(int userId);

    List<Integer> getFollowing(int userId);
}

