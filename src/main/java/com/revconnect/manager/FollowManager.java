package com.revconnect.manager;

import java.util.List;

import com.revconnect.dao.FollowDao;
import com.revconnect.dao.UserDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FollowManager {

    private static final Logger logger =
            LogManager.getLogger(FollowManager.class);

    private FollowDao dao = new FollowDao();
    private UserDao userDao = new UserDao();
    private NotificationManager notificationManager =
            new NotificationManager();

    public boolean follow(int followerId,int followingId){

        // cannot follow self
        if(followerId == followingId){
            logger.warn("User {} tried to follow self", followerId);
            return false;
        }

        // 🔥 CHECK USER EXISTS
        if(!userDao.existsById(followingId)){
            logger.warn("Follow failed. User {} not found", followingId);
            return false;
        }

        // already following
        if(dao.isFollowing(followerId,followingId)){
            logger.warn("User {} already follows {}", followerId, followingId);
            return false;
        }

        dao.follow(followerId,followingId);

        String followerName =
                userDao.getUsernameById(followerId);

        notificationManager.notify(
                followingId,
                followerName + " started following you"
        );

        logger.info("User {} followed {}", followerId, followingId);

        return true;
    }

    public void unfollow(int followerId,int followingId){

        dao.unfollow(followerId,followingId);

        logger.info("User {} unfollowed {}", followerId, followingId);
    }

    public List<Integer> followers(int u){

        logger.info("Fetching followers for userId {}", u);

        return dao.getFollowers(u);
    }

    public List<Integer> following(int u){

        logger.info("Fetching following list for userId {}", u);

        return dao.getFollowing(u);
    }
}
