package com.revconnect.dao;

import java.util.List;
import com.revconnect.domain.Notification;

public interface NotificationRepo {

    void save(Notification n);

    List<Notification> getByUser(int userId);

    void markAsRead(int notificationId);
}
