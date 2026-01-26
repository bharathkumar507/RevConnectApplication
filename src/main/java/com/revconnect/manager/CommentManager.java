package com.revconnect.manager;

import java.util.List;

import com.revconnect.dao.CommentDao;
import com.revconnect.dao.PostDao;
import com.revconnect.dao.UserDao;
import com.revconnect.domain.Comment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommentManager {

    private static final Logger logger =
            LogManager.getLogger(CommentManager.class);

    private CommentDao dao = new CommentDao();
    private PostDao postDao = new PostDao();
    private NotificationManager notificationManager =
            new NotificationManager();
    private UserDao userDao=new UserDao();

    public void add(Comment c){

        dao.add(c);

        logger.info("User {} commented on post {}",
                c.getUserId(),
                c.getPostId());

        int ownerId =
                postDao.getPostOwner(c.getPostId());

        String name =
                userDao.getUsernameById(c.getUserId());

        notificationManager.notify(
                ownerId,
                name + " commented on your post"
        );
    }

    public void edit(int id,String text){

        dao.update(id,text);

        logger.info("Comment {} edited", id);
    }

    public void delete(int id){

        dao.delete(id);

        logger.info("Comment {} deleted", id);
    }

    public List<Comment> getByPost(int postId){

        logger.info("Fetching comments for post {}", postId);

        return dao.getByPost(postId);
    }
}
