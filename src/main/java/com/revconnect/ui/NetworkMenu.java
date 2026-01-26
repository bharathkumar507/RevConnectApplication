package com.revconnect.ui;

import java.util.List;
import java.util.Scanner;

import com.revconnect.dao.UserDao;
import com.revconnect.domain.ConnectionRequest;
import com.revconnect.domain.User;
import com.revconnect.manager.ConnectionManager;
import com.revconnect.manager.FollowManager;

public class NetworkMenu {

    private User user;
    private Scanner sc = new Scanner(System.in);

    private ConnectionManager connectionManager = new ConnectionManager();
    private FollowManager followManager = new FollowManager();

    public NetworkMenu(User user) {
        this.user = user;
    }

    public void start() {

        while (true) {

            System.out.println("\n--- NETWORK MENU ---");
            System.out.println("1. Send Connection Request");
            System.out.println("2. View Pending Requests");
            System.out.println("3. My Connections");
            System.out.println("4. Remove Connection");
            System.out.println("5. Follow User");
            System.out.println("6. Unfollow User");
            System.out.println("7. My Following");
            System.out.println("8. My Followers");
            System.out.println("0. Back");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) sendRequest();
            if (ch == 2) pendingRequests();
            if (ch == 3) myConnections();
            if (ch == 5) follow();
            if(ch==4) removeConnection();


            if (ch == 6) unfollow();
            if (ch == 7) following();
            if (ch == 8) followers();
            if (ch == 0) break;
        }
    }

    private void sendRequest() {

        System.out.print("Enter Receiver User ID: ");
        int rid = Integer.parseInt(sc.nextLine());

        boolean ok = connectionManager.sendRequest(user.getId(), rid);

        if(ok)
            System.out.println("Request sent");
        else
            System.out.println("Invalid request / Already sent / User not found");

    }

    private void pendingRequests() {

        List<ConnectionRequest> list =
                connectionManager.getPending(user.getId());

        for (ConnectionRequest cr : list) {

            System.out.println("Request ID: " + cr.getId() +
                    " From User: " + cr.getSenderId());

            System.out.println("1. Accept  2. Reject");

            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1)
                connectionManager.accept(cr.getId());

            if (ch == 2)
                connectionManager.reject(cr.getId());
        }
    }

    private void myConnections() {

        List<Integer> list =
                connectionManager.getConnections(user.getId());

        if(list.isEmpty()){
            System.out.println("No connections");
            return;
        }

        UserDao userDao = new UserDao();

        System.out.println("Your Connections:");

        for(int id : list){

            String name = userDao.getUsernameById(id);

            System.out.println(id + " : " + name);
        }
    }


    private void follow() {

        System.out.print("Enter User ID to follow: ");
        int id = Integer.parseInt(sc.nextLine());
        boolean ok =
                followManager.follow(
                        user.getId(), id);

        if(ok)
            System.out.println("Followed successfully");
        else
            System.out.println(
                    "Invalid follow / Already following / User not found");


    }

    private void unfollow() {
        int id = Integer.parseInt(sc.nextLine());
        boolean ok =
                followManager.follow(
                        user.getId(), id);

        if(ok)
            System.out.println("UnFollowed successfully");
        else
            System.out.println(
                    "Invalid Unfollow / Already Unfollowed / User not found");

    }

    private void following() {

        System.out.println("Following: " +
                followManager.following(user.getId()));
    }

    private void followers() {

        System.out.println("Followers: " +
                followManager.followers(user.getId()));
    }
    private void removeConnection(){

        List<Integer> list =
                connectionManager
                        .getConnections(user.getId());

        if(list.isEmpty()){
            System.out.println("No connections");
            return;
        }

        for(int id:list){
            System.out.println("User ID: "+id);
        }

        System.out.print("Enter user id to remove: ");
        int id=Integer.parseInt(sc.nextLine());

        connectionManager
                .removeConnection(user.getId(),id);

        System.out.println("Connection removed");
    }

}
