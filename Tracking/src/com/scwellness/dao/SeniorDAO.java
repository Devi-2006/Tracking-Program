package com.scwellness.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.scwellness.bean.Senior;
import com.scwellness.util.DBUtil;

public class SeniorDAO {

    public Senior findSenior(String seniorID) {

        Senior s = null;
        String query = "SELECT * FROM SENIOR_TBL WHERE Senior_ID = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, seniorID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                s = new Senior();
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setFullName(rs.getString("Full_Name"));
                s.setAge(rs.getInt("Age"));
                s.setGender(rs.getString("Gender"));
                s.setChronicConditions(rs.getString("Chronic_Conditions"));
                s.setEmergencyContactName(rs.getString("Emergency_Contact_Name"));
                s.setEmergencyContactPhone(rs.getString("Emergency_Contact_Phone"));
                s.setStatus(rs.getString("Status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return s;
    }
    public List<Senior> viewAllSeniors() {

        List<Senior> seniors = new ArrayList<>();
        String query = "SELECT * FROM SENIOR_TBL";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Senior s = new Senior();
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setFullName(rs.getString("Full_Name"));
                s.setAge(rs.getInt("Age"));
                s.setGender(rs.getString("Gender"));
                s.setChronicConditions(rs.getString("Chronic_Conditions"));
                s.setEmergencyContactName(rs.getString("Emergency_Contact_Name"));
                s.setEmergencyContactPhone(rs.getString("Emergency_Contact_Phone"));
                s.setStatus(rs.getString("Status"));
                seniors.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seniors;
    }

    public boolean insertSenior(Senior senior) {

        String query =
            "INSERT INTO SENIOR_TBL " +
            "(Senior_ID, Full_Name, Age, Gender, Chronic_Conditions, " +
            "Emergency_Contact_Name, Emergency_Contact_Phone, Status) " +
            "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, senior.getSeniorID());
            ps.setString(2, senior.getFullName());
            ps.setInt(3, senior.getAge());
            ps.setString(4, senior.getGender());
            ps.setString(5, senior.getChronicConditions());
            ps.setString(6, senior.getEmergencyContactName());
            ps.setString(7, senior.getEmergencyContactPhone());
            ps.setString(8, senior.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSeniorStatus(String seniorID, String status) {

        String query = "UPDATE SENIOR_TBL SET Status = ? WHERE Senior_ID = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setString(2, seniorID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteSenior(String seniorID) {

        String query = "DELETE FROM SENIOR_TBL WHERE Senior_ID = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, seniorID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
