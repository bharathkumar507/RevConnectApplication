package com.revconnect.dao;

import java.sql.*;
import com.revconnect.config.DbConfig;

public class LikeDao implements LikeRepo {

    public void like(int userId,int postId){

        String sql="INSERT INTO likes(user_id,post_id) VALUES(?,?)";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.setInt(2,postId);
            ps.executeUpdate();
        }catch(Exception e){}
    }

    public void unlike(int userId,int postId){

        String sql="DELETE FROM likes WHERE user_id=? AND post_id=?";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.setInt(2,postId);
            ps.executeUpdate();
        }catch(Exception e){}
    }

    public boolean isLiked(int userId,int postId){

        String sql="SELECT * FROM likes WHERE user_id=? AND post_id=?";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,userId);
            ps.setInt(2,postId);
            ResultSet rs=ps.executeQuery();
            return rs.next();
        }catch(Exception e){}

        return false;
    }

    public int countLikes(int postId){

        String sql="SELECT COUNT(*) FROM likes WHERE post_id=?";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,postId);
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return rs.getInt(1);
            }
        }catch(Exception e){}

        return 0;
    }
}
