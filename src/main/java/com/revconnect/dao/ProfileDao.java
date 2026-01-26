package com.revconnect.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.revconnect.config.DbConfig;
import com.revconnect.domain.Profile;

public class ProfileDao implements ProfileRepo {

    public void saveProfile(Profile profile) {

        String sql = "INSERT INTO profiles\n" +
                "(user_id,name,bio,location,website,\n" +
                " business_name,category,contact_info,address,business_hours)\n" +
                "VALUES(?,?,?,?,?,?,?,?,?,?)\n";

        try {

            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getName());
            ps.setString(3, profile.getBio());
            ps.setString(4, profile.getLocation());
            ps.setString(5, profile.getWebsite());
            ps.setString(6, profile.getBusinessName());
            ps.setString(7, profile.getCategory());
            ps.setString(8, profile.getContactInfo());
            ps.setString(9, profile.getAddress());
            ps.setString(10, profile.getBusinessHours());


            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Profile not saved");
        }

    }

    public Profile findByUserId(int id){

        String sql =
                "SELECT * FROM profiles WHERE user_id=?";

        try{

            Connection con = DbConfig.getConnection();
            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){

                Profile p = new Profile();

                p.setUserId(rs.getInt("user_id"));
                p.setName(rs.getString("name"));
                p.setBio(rs.getString("bio"));
                p.setLocation(rs.getString("location"));
                p.setWebsite(rs.getString("website"));

                // 🔥 IMPORTANT MAPPINGS
                p.setBusinessName(rs.getString("business_name"));
                p.setCategory(rs.getString("category"));
                p.setContactInfo(rs.getString("contact_info"));
                p.setAddress(rs.getString("address"));
                p.setBusinessHours(rs.getString("business_hours"));

                return p;
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public boolean existsByUserId(int userId){

        String sql =
                "SELECT id FROM profiles WHERE user_id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet rs = ps.executeQuery();

            if(rs.next())
                return true;

        }catch(Exception e){}

        return false;
    }
    public void update(Profile p){

        String sql =
                "UPDATE profiles SET name=?,bio=?,location=?,website=?,"+
                        "business_name=?,category=?,contact_info=?,address=?,business_hours=? "+
                        "WHERE user_id=?";

        try{
            Connection con = DbConfig.getConnection();
            PreparedStatement ps=con.prepareStatement(sql);

            ps.setString(1,p.getName());
            ps.setString(2,p.getBio());
            ps.setString(3,p.getLocation());
            ps.setString(4,p.getWebsite());
            ps.setString(5,p.getBusinessName());
            ps.setString(6,p.getCategory());
            ps.setString(7,p.getContactInfo());
            ps.setString(8,p.getAddress());
            ps.setString(9,p.getBusinessHours());
            ps.setInt(10,p.getUserId());

            ps.executeUpdate();
        }catch(Exception e){}
    }



}
