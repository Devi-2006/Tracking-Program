package com.scwellness.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.scwellness.bean.Program;
import com.scwellness.util.DBUtil;

public class ProgramDAO {

    public Program findProgram(String programID) {
        Program p = null;
        String query = "SELECT * FROM PROGRAM_TBL WHERE Program_ID = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, programID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Program();
                p.setProgramID(rs.getString("Program_ID"));
                p.setProgramName(rs.getString("Program_Name"));
                p.setCategory(rs.getString("Category"));
                p.setRecommendedDurationWeeks(
                        rs.getInt("Recommended_Duration_Weeks"));
                p.setSessionFrequency(rs.getString("Session_Frequency"));
                p.setStatus(rs.getString("Status"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<Program> viewAllPrograms() {
        List<Program> programs = new ArrayList<>();
        String query = "SELECT * FROM PROGRAM_TBL";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Program p = new Program();
                p.setProgramID(rs.getString("Program_ID"));
                p.setProgramName(rs.getString("Program_Name"));
                p.setCategory(rs.getString("Category"));
                p.setRecommendedDurationWeeks(
                        rs.getInt("Recommended_Duration_Weeks"));
                p.setSessionFrequency(rs.getString("Session_Frequency"));
                p.setStatus(rs.getString("Status"));
                programs.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return programs;
    }

    public boolean insertProgram(Program program) {
        String query =
            "INSERT INTO PROGRAM_TBL " +
            "(Program_ID, Program_Name, Category, Recommended_Duration_Weeks, " +
            "Session_Frequency, Status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, program.getProgramID());
            ps.setString(2, program.getProgramName());
            ps.setString(3, program.getCategory());
            ps.setInt(4, program.getRecommendedDurationWeeks());
            ps.setString(5, program.getSessionFrequency());
            ps.setString(6, program.getStatus());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProgramStatus(String programID, String status) {
        String query =
            "UPDATE PROGRAM_TBL SET Status = ? WHERE Program_ID = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setString(2, programID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteProgram(String programID) {
        String query =
            "DELETE FROM PROGRAM_TBL WHERE Program_ID = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, programID);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
