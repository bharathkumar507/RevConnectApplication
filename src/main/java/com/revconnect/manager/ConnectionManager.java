package com.revconnect.manager;

import java.util.List;

import com.revconnect.dao.ConnectionDao;
import com.revconnect.dao.UserDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionManager {

    private static final Logger logger =
            LogManager.getLogger(ConnectionManager.class);

    private ConnectionDao dao = new ConnectionDao();
    private UserDao userDao = new UserDao();
    private NotificationManager notificationManager =
            new NotificationManager();

    public boolean sendRequest(int senderId,int receiverId){

        // cannot send to self
        if(senderId == receiverId){
            logger.warn("User {} tried to send connection request to self", senderId);
            return false;
        }

        // receiver must exist
        if(!userDao.existsById(receiverId)){
            logger.warn("Connection request failed. Receiver {} not found", receiverId);
            return false;
        }

        // duplicate request
        if(dao.requestExists(senderId,receiverId)){
            logger.warn("Duplicate connection request from {} to {}", senderId, receiverId);
            return false;
        }

        dao.sendRequest(senderId,receiverId);

        String senderName =
                userDao.getUsernameById(senderId);

        notificationManager.notify(
                receiverId,
                senderName + " sent you a connection request"
        );

        logger.info("Connection request sent from {} to {}", senderId, receiverId);

        return true;
    }

    public List getPending(int userId){

        logger.info("Fetching pending requests for userId {}", userId);

        return dao.getPendingRequests(userId);
    }

    public void accept(int requestId){

        dao.updateStatus(requestId,"ACCEPTED");

        int senderId = dao.getSenderByRequest(requestId);

        notificationManager.notify(senderId, "Your connection request was accepted"
        );
        logger.info("Connection request {} accepted", requestId);
    }

    public void reject(int requestId){
        dao.updateStatus(requestId,"REJECTED");
        logger.info("Connection request {} rejected", requestId);
    }

    public List<Integer> getConnections(int userId){
        logger.info("Fetching connections for userId {}", userId);
        return dao.getConnections(userId);
    }

    public void removeConnection(int u1,int u2){
        dao.removeConnection(u1,u2);
        logger.info("Connection removed between {} and {}", u1, u2);
    }
}
