package com.revconnect.dao;

import java.sql.*;
import java.util.*;

import com.revconnect.config.DbConfig;
import com.revconnect.domain.Post;

public class PostDao implements PostRepo {

    public void save(Post post) {
        String sql = "INSERT INTO posts(user_id,content,hashtags,original_post_id,post_type) VALUES(?,?,?,?,?)";


        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, post.getUserId());
            ps.setString(2, post.getContent());
            ps.setString(3, post.getHashtags());
            ps.setObject(4, post.getOriginalPostId());
            ps.setString(5, post.getPostType());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Post not saved");
        }
    }

    public List<Post> findByUserId(int userId) {

        List<Post> list = new ArrayList<>();

        String sql = "SELECT * FROM posts WHERE user_id=?";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setContent(rs.getString("content"));
                p.setHashtags(rs.getString("hashtags"));
                p.setOriginalPostId((Integer) rs.getObject("original_post_id"));
                p.setCreatedAt(rs.getString("created_at"));
                p.setPostType(rs.getString("post_type"));


                list.add(p);
            }

        } catch (Exception e) {
            System.out.println("Posts not found");
        }

        return list;
    }

    public List<Post> findAll() {

        List<Post> list = new ArrayList<>();

        String sql = "SELECT * FROM posts";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setContent(rs.getString("content"));
                p.setHashtags(rs.getString("hashtags"));
                p.setOriginalPostId((Integer) rs.getObject("original_post_id"));
                p.setCreatedAt(rs.getString("created_at"));
                p.setPostType(rs.getString("post_type"));


                list.add(p);
            }

        } catch (Exception e) {
            System.out.println("Feed empty");
        }

        return list;
    }

    public void delete(int postId) {

        String sql = "DELETE FROM posts WHERE id=?";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Post not deleted");
        }
    }
    public int getPostOwner(int postId){

        String sql = "SELECT user_id FROM posts WHERE id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, postId);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getInt("user_id");
            }

        }catch(Exception e){}

        return 0;
    }
    public List<Post> getFeedByUsers(List<Integer> userIds){

        List<Post> list = new ArrayList<>();

        if(userIds.isEmpty())
            return list;

        StringBuilder sb = new StringBuilder(
                "SELECT * FROM posts WHERE user_id IN (");

        for(int i=0;i<userIds.size();i++){
            sb.append("?");
            if(i < userIds.size()-1)
                sb.append(",");
        }

        sb.append(")");

        try{

            Connection con = DbConfig.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sb.toString());

            for(int i=0;i<userIds.size();i++){
                ps.setInt(i+1,userIds.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setContent(rs.getString("content"));
                p.setHashtags(rs.getString("hashtags"));
                p.setOriginalPostId((Integer) rs.getObject("original_post_id"));
                p.setCreatedAt(rs.getString("created_at"));
                p.setPostType(rs.getString("post_type"));


                list.add(p);
            }

        }catch(Exception e){}

        return list;
    }
    public List<Post> getFeedByUsers(List<Integer> userIds, int filter){

        List<Post> list = new ArrayList<>();

        if(userIds.isEmpty())
            return list;

        StringBuilder sb = new StringBuilder(
                "SELECT p.* FROM posts p JOIN users u ON p.user_id=u.id WHERE p.user_id IN ("
        );

        for(int i=0;i<userIds.size();i++){
            sb.append("?");
            if(i < userIds.size()-1)
                sb.append(",");
        }

        sb.append(")");

        if(filter == 2)
            sb.append(" AND u.account_type='CREATOR'");

        if(filter == 3)
            sb.append(" AND u.account_type='BUSINESS'");

        if(filter == 4)
            sb.append(" AND p.post_type='PROMOTIONAL'");

        try{

            Connection con = DbConfig.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sb.toString());

            for(int i=0;i<userIds.size();i++){
                ps.setInt(i+1, userIds.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Post p = new Post();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setContent(rs.getString("content"));
                p.setHashtags(rs.getString("hashtags"));
                p.setPostType(rs.getString("post_type"));
                p.setOriginalPostId(
                        (Integer) rs.getObject("original_post_id")
                );

                list.add(p);
            }

        }catch(Exception e){}

        return list;
    }

    public int countShares(int postId){

        String sql =
                "SELECT COUNT(*) FROM posts WHERE original_post_id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return rs.getInt(1);

        }catch(Exception e){}

        return 0;
    }
    public void updatePost(int postId,String content,String hashtags){

        String sql =
                "UPDATE posts SET content=?, hashtags=? WHERE id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,content);
            ps.setString(2,hashtags);
            ps.setInt(3,postId);
            ps.executeUpdate();
        }catch(Exception e){}
    }


}
