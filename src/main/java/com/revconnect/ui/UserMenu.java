package com.revconnect.ui;

import java.util.*;

import com.revconnect.domain.*;
import com.revconnect.manager.*;

public class UserMenu {

    private User user;
    private Scanner sc = new Scanner(System.in);
    private ProfileService profileService = new ProfileManager();

    private PostManager postManager = new PostManager();
    private ConnectionManager connectionManager = new ConnectionManager();
    private FollowManager followManager = new FollowManager();
    private LikeManager likeManager = new LikeManager();
    private CommentManager commentManager = new CommentManager();
    private NotificationManager notificationManager = new NotificationManager();
    private UserManager userManager = new UserManager();
    public UserMenu(User user) {
        this.user = user;
    }

    public void start() {

        while (true) {

            int n =
                    notificationManager.unreadCount(user.getId());

            System.out.println("\n--- HOME ---");
            System.out.println("1. Feed");
            System.out.println("2. Profile");
            System.out.println("3. Post Menu");
            System.out.println("4. Network");
            System.out.println("5. Notifications (" + n + ")");
            System.out.println("6. Search Users");
            System.out.println("7. Delete My Account");

            if (user.getAccountType().equals("CREATOR")
                    || user.getAccountType().equals("BUSINESS"))
                System.out.println("8. Analytics");

            System.out.println("0. Logout");

            int ch =
                    Integer.parseInt(sc.nextLine());

            if (ch == 1)
                feed();

            else if (ch == 2)
                new ProfileMenu(user).start();

            else if (ch == 3)
                new PostMenu(user).start();

            else if (ch == 4)
                new NetworkMenu(user).start();

            else if (ch == 5)
                new NotificationMenu(user).start();
            else if (ch==6)
                searchUsers();
           else if(ch==7){
                deleteAccount();
                break;
            }


            else if (ch == 8 &&
                    (user.getAccountType().equals("CREATOR")
                            || user.getAccountType().equals("BUSINESS")))
                new AnalyticsMenu(user).start();

            else if (ch == 0)
                break;
        }
    }

    // ---------------- FEED ----------------

    private void feed() {

        System.out.println("1.All 2.Creators 3.Business 4.Promotional");
        int filter = Integer.parseInt(sc.nextLine());

        List<Integer> ids = new ArrayList<>();
        ids.addAll(connectionManager.getConnections(user.getId()));
        ids.addAll(followManager.following(user.getId()));
        ids.add(user.getId());

        List<Post> list =
                postManager.getFeedForUser(ids, filter);

        for (Post p : list) {

            System.out.println("\n" + p.getContent());
            System.out.println("Tags: " + p.getHashtags());

            int likes =
                    likeManager.countLikes(p.getId());
            int comments =
                    commentManager.getByPost(p.getId()).size();

            System.out.println("Likes:" + likes + " Comments:" + comments);

            System.out.println("1.Like 2.Comment 3.View 4.Share 0.Skip");
            int opt =
                    Integer.parseInt(sc.nextLine());

            if (opt == 1)
                likeManager.toggleLike(user.getId(), p.getId());

            else if (opt == 2) {
                System.out.print("Comment: ");
                String txt = sc.nextLine();

                Comment c = new Comment();
                c.setUserId(user.getId());
                c.setPostId(p.getId());
                c.setCommentText(txt);
                commentManager.add(c);
            }

            else if (opt == 3) {
                List<Comment> cs =
                        commentManager.getByPost(p.getId());
                if (cs.isEmpty())
                    System.out.println("No comments");
                else
                    for (Comment c : cs)
                        System.out.println(c.getCommentText());
            }

            else if (opt == 4)
                postManager.sharePost(user.getId(), p);
        }
    }




    private void searchUsers(){

        System.out.print("Enter username to search: ");
        String key = sc.nextLine();

        List<User> list =
                userManager.searchUsers(key);

        if(list.isEmpty()){
            System.out.println("No users found");
            return;
        }

        System.out.println("\n--- SEARCH RESULTS ---");

        for(User u : list){
            System.out.println(
                    u.getId() + " : " +
                            u.getUsername()
            );
        }

        System.out.print(
                "\nEnter User ID to view profile (0 to cancel): ");

        int id =
                Integer.parseInt(sc.nextLine());

        if(id == 0) return;

        Profile p =
                profileService.getProfileByUserId(id);

        if(p == null){
            System.out.println("Profile not found");
            return;
        }

        System.out.println("\n--- PROFILE DETAILS ---");
        System.out.println("Name: " + p.getName());
        System.out.println("Bio: " + p.getBio());
        System.out.println("Location: " + p.getLocation());
        System.out.println("Website: " + p.getWebsite());
    }


    private void deleteAccount(){

        System.out.println(
                "Are you sure you want to delete your account?");
        System.out.println("1. Yes");
        System.out.println("2. No");

        int ch =
                Integer.parseInt(sc.nextLine());

        if(ch != 1){
            return;
        }

        boolean ok =
                userManager.deleteAccount(
                        user.getId());

        if(ok){
            System.out.println(
                    "Account deleted successfully");
        }
        else{
            System.out.println(
                    "Unable to delete account");
        }
    }

}
