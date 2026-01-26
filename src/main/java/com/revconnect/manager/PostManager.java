package com.revconnect.manager;

import java.util.List;

import com.revconnect.dao.PostDao;
import com.revconnect.dao.UserDao;
import com.revconnect.domain.Post;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PostManager implements PostService {

    private static final Logger logger =
            LogManager.getLogger(PostManager.class);

    private PostDao dao = new PostDao();
    private NotificationManager notificationManager = new NotificationManager();
    private UserDao userDao = new UserDao();

    public void create(Post post) {

        dao.save(post);

        logger.info("Post created by userId {}", post.getUserId());
    }

    public List<Post> getMyPosts(int userId) {

        logger.info("Fetching posts for userId {}", userId);

        return dao.findByUserId(userId);
    }

    public List<Post> getFeed() {

        logger.info("Fetching global feed");

        return dao.findAll();
    }

    public void deletePost(int postId) {

        dao.delete(postId);

        logger.info("Post deleted with id {}", postId);
    }

    public List<Post> getFeedForUser(List<Integer> ids, int filter){

        logger.info("Fetching feed for user list with filter {}", filter);

        return dao.getFeedByUsers(ids, filter);
    }

    public void sharePost(int userId, Post originalPost) {

        Post p = new Post();
        p.setUserId(userId);
        p.setContent(originalPost.getContent());
        p.setHashtags(originalPost.getHashtags());
        p.setOriginalPostId(originalPost.getId());

        // save repost
        dao.save(p);

        logger.info("User {} shared post {}", userId,
                originalPost.getId());

        // get owner
        int ownerId =
                dao.getPostOwner(originalPost.getId());

        String sharerName =
                userDao.getUsernameById(userId);

        // notify
        notificationManager.notify(
                ownerId,
                sharerName + " shared your post"
        );
    }

    public int countShares(int postId){

        return dao.countShares(postId);
    }

    public void editPost(int postId,
                         String content,
                         String hashtags){

        dao.updatePost(postId,content,hashtags);

        logger.info("Post {} edited", postId);
    }
}
