package com.revconnect.manager;

import java.util.List;

import com.revconnect.dao.NotificationDao;
import com.revconnect.domain.Notification;

public class NotificationManager {

    private NotificationDao dao=new NotificationDao();

    public void notify(int userId,String msg){

        Notification n=new Notification();
        n.setUserId(userId);
        n.setMessage(msg);
        dao.save(n);
    }

    public List<Notification> getMyNotifications(int userId){
        return dao.getByUser(userId);
    }

    public void markRead(int id){
        dao.markAsRead(id);
    }
    public int unreadCount(int userId){
        return dao.countUnread(userId);
    }

}
