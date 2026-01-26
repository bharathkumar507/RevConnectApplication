package com.revconnect.ui;

import java.util.List;
import java.util.Scanner;

import com.revconnect.domain.Notification;
import com.revconnect.domain.User;
import com.revconnect.manager.NotificationManager;

public class NotificationMenu {

    private User user;
    private Scanner sc=new Scanner(System.in);
    private NotificationManager manager=new NotificationManager();

    public NotificationMenu(User user){
        this.user=user;
    }

    public void start(){

        List<Notification> list=
                manager.getMyNotifications(user.getId());

        if(list.isEmpty()){
            System.out.println("No notifications");
        }else{
            for(Notification n:list){
                System.out.println(
                        n.getId()+" : "+
                                n.getMessage()+
                                (n.isRead()?"":" (NEW)")
                );
            }

            System.out.print("Enter notification id to mark read (0 skip): ");
            int id=Integer.parseInt(sc.nextLine());

            if(id!=0){
                manager.markRead(id);
            }
        }
    }
}
