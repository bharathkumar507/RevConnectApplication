package com.revconnect.manager;

import com.revconnect.dao.LikeDao;
import com.revconnect.dao.PostDao;
import com.revconnect.dao.UserDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LikeManager {

    private static final Logger logger =
            LogManager.getLogger(LikeManager.class);

    private LikeDao likeDao = new LikeDao();
    private PostDao postDao = new PostDao();
    private UserDao userDao=new UserDao();
    private NotificationManager notificationManager =
            new NotificationManager();

    public void toggleLike(int userId,int postId){

        if(likeDao.isLiked(userId,postId)){

            likeDao.unlike(userId,postId);

            logger.info("User {} unliked post {}", userId, postId);
        }
        else{

            likeDao.like(userId,postId);

            logger.info("User {} liked post {}", userId, postId);

            int ownerId =
                    postDao.getPostOwner(postId);

            String name =
                    userDao.getUsernameById(userId);

            notificationManager.notify(
                    ownerId,
                    name + " liked your post"
            );
        }
    }

    public int countLikes(int postId){

        return likeDao.countLikes(postId);
    }
}
