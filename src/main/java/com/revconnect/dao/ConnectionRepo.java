package com.revconnect.dao;

import java.util.List;
import com.revconnect.domain.ConnectionRequest;

public interface ConnectionRepo {

    void sendRequest(int senderId, int receiverId);

    List<ConnectionRequest> getPendingRequests(int userId);

    void updateStatus(int requestId, String status);

    List<Integer> getConnections(int userId);
}
