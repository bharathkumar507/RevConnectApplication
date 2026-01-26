package com.revconnect.ui;

import java.util.List;
import java.util.Scanner;

import com.revconnect.domain.Comment;
import com.revconnect.domain.Post;
import com.revconnect.domain.User;
import com.revconnect.manager.PostManager;
import com.revconnect.manager.PostService;
import com.revconnect.manager.LikeManager;
import com.revconnect.manager.CommentManager;


public class PostMenu {

    private Scanner sc = new Scanner(System.in);
    private PostService service = new PostManager();
    private User user;
    private LikeManager likeManager = new LikeManager();
    private CommentManager commentManager = new CommentManager();


    public PostMenu(User user) {
        this.user = user;
    }

    public void start() {

        while (true) {

            System.out.println("\n--- POST MENU ---");
            System.out.println("1. Create Post");
            System.out.println("2. My Posts");
            System.out.println("3. Delete Post");
            System.out.println("4. Edit Post");
            System.out.println("0. Back");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) createPost();
            if (ch == 2) myPosts();
            if (ch == 3) deletePost();
            if(ch==4) editPost();
            if (ch == 0) break;
        }
    }

    private void createPost() {

        Post p = new Post();
        p.setUserId(user.getId());

        System.out.print("Enter post: ");
        p.setContent(sc.nextLine());

        System.out.print("Enter hashtags: ");
        p.setHashtags(sc.nextLine());
        System.out.println("1. Normal Post");
        System.out.println("2. Promotional Post");

        int ch = Integer.parseInt(sc.nextLine());

        if(ch==1)
            p.setPostType("NORMAL");
        else
            p.setPostType("PROMOTIONAL");


        service.create(p);

        System.out.println("Post created");
    }

    private void myPosts() {

        List<Post> list = service.getMyPosts(user.getId());

        for (Post p : list) {

            System.out.println("\nPost Id: " + p.getId());
            System.out.println(p.getContent());
            System.out.println("Tags: " + p.getHashtags());

            int likes = likeManager.countLikes(p.getId());
            int comments = commentManager.getByPost(p.getId()).size();

            System.out.println("Likes: " + likes + " | Comments: " + comments);
            System.out.println("1. Like/Unlike  2. Comment  3. View Comments  0. Skip");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) {
                likeManager.toggleLike(user.getId(), p.getId());
            }

            if (ch == 2) {
                System.out.print("Enter comment: ");
                Comment c = new Comment();
                c.setUserId(user.getId());
                c.setPostId(p.getId());
                c.setCommentText(sc.nextLine());
                commentManager.add(c);
            }

            if (ch == 3) {

                List<Comment> listC =
                        commentManager.getByPost(p.getId());

                if (listC.isEmpty()) {
                    System.out.println("Be the first one to comment");
                } else {

                    for (Comment c : listC) {
                        System.out.println(c.getId() + " : "
                                + c.getCommentText()
                                + " (" + c.getUsername() + ")");
                    }

                    System.out.println("1. Edit Comment");
                    System.out.println("2. Delete Comment");
                    System.out.println("0. Back");

                    int opt = Integer.parseInt(sc.nextLine());

                    if (opt == 1) {

                        System.out.print("Enter Comment Id: ");
                        int cid = Integer.parseInt(sc.nextLine());

                        boolean found = false;

                        for(Comment c : listC){
                            if(c.getId() == cid && c.getUserId() == user.getId()){

                                System.out.print("Enter new text: ");
                                String txt = sc.nextLine();

                                commentManager.edit(cid, txt);
                                System.out.println("Comment updated");
                                found = true;
                            }
                        }

                        if(!found){
                            System.out.println("You can edit only your own comments");
                        }
                    }


                    if (opt == 2) {

                        System.out.print("Enter Comment Id: ");
                        int cid = Integer.parseInt(sc.nextLine());

                        boolean found = false;

                        for(Comment c : listC){
                            if(c.getId() == cid && c.getUserId() == user.getId()){

                                commentManager.delete(cid);
                                System.out.println("Comment deleted");
                                found = true;
                            }
                        }

                        if(!found){
                            System.out.println("You can delete only your own comments");
                        }
                    }

                }
            }

        }
    }



    private void deletePost() {

        System.out.print("Enter Post Id: ");
        int id = Integer.parseInt(sc.nextLine());

        service.deletePost(id);
        System.out.println("Post deleted");
    }
    private void editPost(){

        List<Post> posts =
                service.getMyPosts(user.getId());

        if(posts.isEmpty()){
            System.out.println("No posts");
            return;
        }

        for(Post p:posts){
            System.out.println(
                    p.getId()+" : "+p.getContent());
        }

        System.out.print("Enter post id to edit: ");
        int id=Integer.parseInt(sc.nextLine());

        System.out.print("New content: ");
        String c=sc.nextLine();

        System.out.print("New hashtags: ");
        String h=sc.nextLine();

        service.editPost(id,c,h);

        System.out.println("Post updated");
    }

}
