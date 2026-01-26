package com.revconnect.dao;

import java.sql.*;
import java.util.*;

import com.revconnect.config.DbConfig;
import com.revconnect.domain.ConnectionRequest;

public class ConnectionDao implements ConnectionRepo {

    public void sendRequest(int senderId, int receiverId) {

        String sql =
                "INSERT INTO connection_requests(sender_id,receiver_id,status) VALUES(?,?,?)";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, senderId);
            ps.setInt(2, receiverId);
            ps.setString(3, "PENDING");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Request not sent");
        }
    }

    public List<ConnectionRequest> getPendingRequests(int userId) {

        List<ConnectionRequest> list = new ArrayList<>();

        String sql =
                "SELECT * FROM connection_requests WHERE receiver_id=? AND status='PENDING'";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                ConnectionRequest cr = new ConnectionRequest();
                cr.setId(rs.getInt("id"));
                cr.setSenderId(rs.getInt("sender_id"));
                cr.setReceiverId(rs.getInt("receiver_id"));
                cr.setStatus(rs.getString("status"));

                list.add(cr);
            }

        } catch (Exception e) {
            System.out.println("No pending requests");
        }

        return list;
    }

    public void updateStatus(int requestId, String status) {

        String sql = "UPDATE connection_requests SET status=? WHERE id=?";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Status not updated");
        }
    }

    public List<Integer> getConnections(int userId) {

        List<Integer> list = new ArrayList<>();

        String sql =
                "SELECT sender_id,receiver_id FROM connection_requests " +
                        "WHERE status='ACCEPTED' AND (sender_id=? OR receiver_id=?)";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                int s = rs.getInt("sender_id");
                int r = rs.getInt("receiver_id");

                if (s == userId) list.add(r);
                else list.add(s);
            }

        } catch (Exception e) {
            System.out.println("No connections");
        }

        return list;
    }
    public int getSenderByRequest(int id){

        String sql =
                "SELECT sender_id FROM connection_requests WHERE id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("sender_id");
            }

        }catch(Exception e){}

        return 0;
    }
    public void removeConnection(int u1,int u2){

        String sql =
                "DELETE FROM connection_requests " +
                        "WHERE status='ACCEPTED' AND " +
                        "((sender_id=? AND receiver_id=?) OR " +
                        "(sender_id=? AND receiver_id=?))";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1,u1);
            ps.setInt(2,u2);
            ps.setInt(3,u2);
            ps.setInt(4,u1);

            ps.executeUpdate();
        }catch(Exception e){}
    }
    public boolean requestExists(int senderId,int receiverId){

        String sql =
                "SELECT id FROM connection_requests " +
                        "WHERE (sender_id=? AND receiver_id=?) " +
                        "OR (sender_id=? AND receiver_id=?)";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1,senderId);
            ps.setInt(2,receiverId);
            ps.setInt(3,receiverId);
            ps.setInt(4,senderId);

            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return true;

        }catch(Exception e){}

        return false;
    }

}
