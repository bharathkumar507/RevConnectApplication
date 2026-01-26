package com.revconnect.dao;

import java.sql.*;
import java.util.*;

import com.revconnect.config.DbConfig;
import com.revconnect.domain.Notification;

public class NotificationDao implements NotificationRepo {

    public void save(Notification n){

        String sql="INSERT INTO notifications(user_id,message) VALUES(?,?)";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,n.getUserId());
            ps.setString(2,n.getMessage());
            ps.executeUpdate();
        }catch(Exception e){}
    }

    public List<Notification> getByUser(int userId){

        List<Notification> list=new ArrayList<>();

        String sql="SELECT * FROM notifications WHERE user_id=?";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs=ps.executeQuery();

            while(rs.next()){
                Notification n=new Notification();
                n.setId(rs.getInt("id"));
                n.setUserId(rs.getInt("user_id"));
                n.setMessage(rs.getString("message"));
                n.setRead(rs.getBoolean("is_read"));
                list.add(n);
            }
        }catch(Exception e){}

        return list;
    }

    public void markAsRead(int id){

        String sql="UPDATE notifications SET is_read=true WHERE id=?";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,id);
            ps.executeUpdate();
        }catch(Exception e){}
    }
    public int countUnread(int userId){

        String sql =
                "SELECT COUNT(*) FROM notifications " +
                        "WHERE user_id=? AND is_read=false";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }

        }catch(Exception e){}

        return 0;
    }

}
