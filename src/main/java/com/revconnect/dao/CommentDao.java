package com.revconnect.dao;

import java.sql.*;
import java.util.*;

import com.revconnect.config.DbConfig;
import com.revconnect.domain.Comment;

public class CommentDao implements CommentRepo {

    public void add(Comment c){

        String sql="INSERT INTO comments(user_id,post_id,comment_text) VALUES(?,?,?)";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,c.getUserId());
            ps.setInt(2,c.getPostId());
            ps.setString(3,c.getCommentText());
            ps.executeUpdate();
        }catch(Exception e){}
    }

    public void update(int commentId,String text){

        String sql="UPDATE comments SET comment_text=? WHERE id=?";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,text);
            ps.setInt(2,commentId);
            ps.executeUpdate();
        }catch(Exception e){}
    }

    public void delete(int commentId){

        String sql="DELETE FROM comments WHERE id=?";

        try{
            Connection con=DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setInt(1,commentId);
            ps.executeUpdate();
        }catch(Exception e){}
    }

    public List<Comment> getByPost(int postId){

        List<Comment> list = new ArrayList<>();

        String sql =
                "SELECT c.id, c.user_id, c.post_id, c.comment_text, u.username " +
                        "FROM comments c JOIN users u ON c.user_id = u.id " +
                        "WHERE c.post_id=?";

        try{

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, postId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){

                Comment c = new Comment();
                c.setId(rs.getInt("id"));
                c.setUserId(rs.getInt("user_id"));
                c.setPostId(rs.getInt("post_id"));
                c.setCommentText(rs.getString("comment_text"));
                c.setUsername(rs.getString("username"));

                list.add(c);
            }

        }catch(Exception e){}

        return list;
    }
    public int getPostOwner(int postId){

        String sql="SELECT user_id FROM posts WHERE id=?";

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
