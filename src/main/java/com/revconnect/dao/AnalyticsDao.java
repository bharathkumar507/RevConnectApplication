package com.revconnect.dao;

import java.sql.*;

import com.revconnect.config.DbConfig;
import com.revconnect.domain.Analytics;

public class AnalyticsDao {

    public Analytics getAnalytics(int userId){

        Analytics a = new Analytics();

        try{

            Connection con = DbConfig.getConnection();

            // total posts
            PreparedStatement ps1 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM posts WHERE user_id=?");
            ps1.setInt(1,userId);
            ResultSet r1 = ps1.executeQuery();
            if(r1.next()) a.setTotalPosts(r1.getInt(1));

            // total likes on my posts
            PreparedStatement ps2 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM likes l " +
                                    "JOIN posts p ON l.post_id=p.id " +
                                    "WHERE p.user_id=?");
            ps2.setInt(1,userId);
            ResultSet r2 = ps2.executeQuery();
            if(r2.next()) a.setTotalLikes(r2.getInt(1));

            // total comments
            PreparedStatement ps3 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM comments c " +
                                    "JOIN posts p ON c.post_id=p.id " +
                                    "WHERE p.user_id=?");
            ps3.setInt(1,userId);
            ResultSet r3 = ps3.executeQuery();
            if(r3.next()) a.setTotalComments(r3.getInt(1));

            // total shares
            PreparedStatement ps4 =
                    con.prepareStatement(
                            "SELECT COUNT(*) FROM posts WHERE original_post_id IN " +
                                    "(SELECT id FROM posts WHERE user_id=?)");
            ps4.setInt(1,userId);
            ResultSet r4 = ps4.executeQuery();
            if(r4.next()) a.setTotalShares(r4.getInt(1));

            // reach (unique users who liked or commented)
            PreparedStatement ps5 =
                    con.prepareStatement(
                            "SELECT COUNT(DISTINCT user_id) FROM (" +
                                    "SELECT l.user_id FROM likes l " +
                                    "JOIN posts p ON l.post_id=p.id WHERE p.user_id=? " +
                                    "UNION " +
                                    "SELECT c.user_id FROM comments c " +
                                    "JOIN posts p ON c.post_id=p.id WHERE p.user_id=?" +
                                    ") x");
            ps5.setInt(1,userId);
            ps5.setInt(2,userId);
            ResultSet r5 = ps5.executeQuery();
            if(r5.next()) a.setReach(r5.getInt(1));

        }catch(Exception e){}

        return a;
    }
}
