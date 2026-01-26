package com.revconnect.dao;

import java.sql.*;
import java.util.*;

import com.revconnect.config.DbConfig;

public class FollowDao implements FollowRepo {

    public void follow(int followerId, int followingId) {

        String sql = "INSERT INTO follows(follower_id,following_id) VALUES(?,?)";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Follow failed");
        }
    }

    public void unfollow(int followerId, int followingId) {

        String sql = "DELETE FROM follows WHERE follower_id=? AND following_id=?";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, followerId);
            ps.setInt(2, followingId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("Unfollow failed");
        }
    }

    public List<Integer> getFollowers(int userId) {

        List<Integer> list = new ArrayList<>();

        String sql = "SELECT follower_id FROM follows WHERE following_id=?";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("follower_id"));
            }

        } catch (Exception e) {
            System.out.println("No followers");
        }

        return list;
    }

    public List<Integer> getFollowing(int userId) {

        List<Integer> list = new ArrayList<>();

        String sql = "SELECT following_id FROM follows WHERE follower_id=?";

        try {
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(rs.getInt("following_id"));
            }

        } catch (Exception e) {
            System.out.println("Not following anyone");
        }

        return list;
    }
    public boolean isFollowing(int followerId,int followingId){

        String sql =
                "SELECT id FROM follows " +
                        "WHERE follower_id=? AND following_id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,followerId);
            ps.setInt(2,followingId);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return true;

        }catch(Exception e){}

        return false;
    }

}
