package com.revconnect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revconnect.config.DbConfig;
import com.revconnect.domain.User;

public class UserDao implements UserRepo {

    public void saveUser(User user) {

        String sql = "INSERT INTO users(username,email,password,account_type,status,\n" +
                "security_question,security_answer,is_private)\n" +
                "VALUES(?,?,?,?,?,?,?,?)";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getAccountType());
            ps.setString(5, user.getStatus());
            ps.setString(6,user.getSecurityQuestion());
            ps.setString(7,user.getSecurityAnswer());
            ps.setBoolean(8,false);

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("User not saved");
        }
    }

    public User findByUsername(String username) {

        String sql = "SELECT * FROM users WHERE username=?";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAccountType(rs.getString("account_type"));
                user.setStatus(rs.getString("status"));

                return user;
            }

        } catch (Exception e) {
            System.out.println("User not found");
        }

        return null;
    }

    public User findByEmail(String email) {

        String sql = "SELECT * FROM users WHERE email=?";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                User user = new User();

                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAccountType(rs.getString("account_type"));
                user.setStatus(rs.getString("status"));

                return user;
            }

        } catch (Exception e) {
            System.out.println("User not found");
        }

        return null;
    }
    public List<User> searchByUsername(String keyword) {

        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users WHERE username LIKE ?";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setAccountType(rs.getString("account_type"));

                list.add(u);
            }

        } catch (Exception e) {
            System.out.println("Search failed");
        }

        return list;
    }
    public String getUsernameById(int id){

        String sql = "SELECT username FROM users WHERE id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return rs.getString("username");
            }

        }catch(Exception e){}

        return "";
    }
    public boolean updatePassword(int userId, String oldPass, String newPass){

        String checkSql =
                "SELECT * FROM users WHERE id=? AND password=?";

        String updateSql =
                "UPDATE users SET password=? WHERE id=?";

        try{

            Connection con = DbConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(checkSql);

            ps.setInt(1,userId);
            ps.setString(2,oldPass);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                PreparedStatement ps2 =
                        con.prepareStatement(updateSql);

                ps2.setString(1,newPass);
                ps2.setInt(2,userId);
                ps2.executeUpdate();

                return true;
            }

        }catch(Exception e){}

        return false;
    }
    public String getSecurityQuestion(String username){

        String sql =
                "SELECT security_question FROM users WHERE username=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return rs.getString(1);

        }catch(Exception e){}

        return null;
    }
    public boolean resetPassword(String username, String answer, String newPass){

        String checkSql =
                "SELECT * FROM users WHERE username=? AND security_answer=?";

        String updateSql =
                "UPDATE users SET password=? WHERE username=?";

        try{

            Connection con = DbConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(checkSql);

            ps.setString(1,username);
            ps.setString(2,answer);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                PreparedStatement ps2 =
                        con.prepareStatement(updateSql);

                ps2.setString(1,newPass);
                ps2.setString(2,username);
                ps2.executeUpdate();
                return true;
            }

        }catch(Exception e){}

        return false;
    }
    public void updatePrivacy(int userId, boolean isPrivate){

        String sql =
                "UPDATE users SET is_private=? WHERE id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1,isPrivate);
            ps.setInt(2,userId);
            ps.executeUpdate();
        }catch(Exception e){}
    }
    public boolean existsById(int id){

        String sql = "SELECT id FROM users WHERE id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return true;

        }catch(Exception e){}

        return false;
    }
    public boolean usernameExists(String username){

        String sql =
                "SELECT id FROM users WHERE username=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs=ps.executeQuery();

            if(rs.next())
                return true;

        }catch(Exception e){}

        return false;
    }
    public boolean emailExists(String email){

        String sql =
                "SELECT id FROM users WHERE email=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);
            ps.setString(1,email);
            ResultSet rs=ps.executeQuery();

            if(rs.next())
                return true;

        }catch(Exception e){}

        return false;
    }
    public User login(String username, String password){

        String sql =
                "SELECT * FROM users WHERE username=? AND password=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setAccountType(rs.getString("account_type"));
                u.setStatus(rs.getString("status"));
                return u;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public boolean deleteUser(int userId){

        String sql =
                "DELETE FROM users WHERE id=?";

        try{
            Connection con =
                    DbConfig.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1,userId);

            ps.executeUpdate();
            return true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return false;
    }




}
