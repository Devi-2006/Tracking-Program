package com.scwellness.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.scwellness.bean.Session;
import com.scwellness.util.DBUtil;

public class SessionDAO {

    public int generateSessionID() {
        Connection connection = DBUtil.getDBConnection();
        String query = "SELECT SESSION_SEQ.NEXTVAL FROM DUAL";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean insertSession(Session session) {
        Connection connection = DBUtil.getDBConnection();
        String query =
            "INSERT INTO SESSION_TBL (Session_ID, Senior_ID, Program_ID, Session_Date, Time_Slot, Clinician_Name, Status) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, session.getSessionID());
            ps.setString(2, session.getSeniorID());
            ps.setString(3, session.getProgramID());
            ps.setDate(4, session.getSessionDate());
            ps.setString(5, session.getTimeSlot());
            ps.setString(6, session.getClinicianName());
            ps.setString(7, "SCHEDULED");

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateSessionOutcome(int sessionID, String status,
            String bloodPressure, BigDecimal sugarLevel, String sessionNotes) {

        String query =
            "UPDATE SESSION_TBL SET STATUS = ?, Blood_Pressure = ?, Sugar_Level = ?, Session_Notes = ? " +
            "WHERE Session_ID = ?";

        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement(query);

            ps.setString(1, status);
            ps.setString(2, bloodPressure);
            ps.setBigDecimal(3, sugarLevel);
            ps.setString(4, sessionNotes);
            ps.setInt(5, sessionID);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Session findSession(int sessionID) {
        Session session = null;
        String query = "SELECT * FROM SESSION_TBL WHERE Session_ID = ?";

        try {
            Connection connection = DBUtil.getDBConnection();
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, sessionID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                session = new Session();
                session.setSessionID(rs.getInt("Session_ID"));
                session.setSeniorID(rs.getString("Senior_ID"));
                session.setProgramID(rs.getString("Program_ID"));
                session.setSessionDate(rs.getDate("Session_Date"));
                session.setTimeSlot(rs.getString("Time_Slot"));
                session.setClinicianName(rs.getString("Clinician_Name"));
                session.setBloodPressure(rs.getString("Blood_Pressure"));
                session.setSugarLevel(rs.getBigDecimal("Sugar_Level"));
                session.setSessionNotes(rs.getString("Session_Notes"));
                session.setStatus(rs.getString("Status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return session;
    }

    public List<Session> findSessionsBySenior(String seniorID) {
        List<Session> list = new ArrayList<>();
        String query = "SELECT * FROM SESSION_TBL WHERE Senior_ID = ? ORDER BY Session_Date";

        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, seniorID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Session s = new Session();
                s.setSessionID(rs.getInt("Session_ID"));
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setProgramID(rs.getString("Program_ID"));
                s.setSessionDate(rs.getDate("Session_Date"));
                s.setTimeSlot(rs.getString("Time_Slot"));
                s.setClinicianName(rs.getString("Clinician_Name"));
                s.setBloodPressure(rs.getString("Blood_Pressure"));
                s.setSugarLevel(rs.getBigDecimal("Sugar_Level"));
                s.setSessionNotes(rs.getString("Session_Notes"));
                s.setStatus(rs.getString("Status"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<Session> findSessionsByProgram(String programID) {
        List<Session> list = new ArrayList<>();
        String query = "SELECT * FROM SESSION_TBL WHERE Program_ID = ? ORDER BY Session_Date";

        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, programID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Session s = new Session();
                s.setSessionID(rs.getInt("Session_ID"));
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setProgramID(rs.getString("Program_ID"));
                s.setSessionDate(rs.getDate("Session_Date"));
                s.setTimeSlot(rs.getString("Time_Slot"));
                s.setClinicianName(rs.getString("Clinician_Name"));
                s.setBloodPressure(rs.getString("Blood_Pressure"));
                s.setSugarLevel(rs.getBigDecimal("Sugar_Level"));
                s.setSessionNotes(rs.getString("Session_Notes"));
                s.setStatus(rs.getString("Status"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public java.util.List<Session> findSessionsByDate(java.sql.Date sessionDate) {
        java.util.List<Session> list = new java.util.ArrayList<>();

        String query = "SELECT * FROM SESSION_TBL WHERE Session_Date = ? ORDER BY Time_Slot";

        try {
            Connection con = DBUtil.getDBConnection();
           PreparedStatement ps = con.prepareStatement(query);
            ps.setDate(1, sessionDate);

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Session s = new Session();
                s.setSessionID(rs.getInt("Session_ID"));
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setProgramID(rs.getString("Program_ID"));
                s.setSessionDate(rs.getDate("Session_Date"));
                s.setTimeSlot(rs.getString("Time_Slot"));
                s.setClinicianName(rs.getString("Clinician_Name"));
                s.setStatus(rs.getString("Status"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    public List<Session> findConflictingSessions(String seniorID, Date sessionDate, String timeSlot) {
        List<Session> list = new ArrayList<>();
        String query =
            "SELECT * FROM SESSION_TBL WHERE Senior_ID = ? AND Session_Date = ? AND Time_Slot = ?";

        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, seniorID);
            ps.setDate(2, sessionDate);
            ps.setString(3, timeSlot);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Session s = new Session();
                s.setSessionID(rs.getInt("Session_ID"));
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setProgramID(rs.getString("Program_ID"));
                s.setSessionDate(rs.getDate("Session_Date"));
                s.setTimeSlot(rs.getString("Time_Slot"));
                s.setClinicianName(rs.getString("Clinician_Name"));
                s.setStatus(rs.getString("Status"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public java.util.List<Session> findActiveSessionsForSenior(String seniorID) {
        java.util.List<Session> list = new java.util.ArrayList<>();

        String query = "SELECT * FROM SESSION_TBL WHERE Senior_ID = ? AND Status = 'SCHEDULED'";

        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, seniorID);

            java.sql.ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Session s = new Session();
                s.setSessionID(rs.getInt("Session_ID"));
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setProgramID(rs.getString("Program_ID"));
                s.setSessionDate(rs.getDate("Session_Date"));
                s.setTimeSlot(rs.getString("Time_Slot"));
                s.setClinicianName(rs.getString("Clinician_Name"));
                s.setStatus(rs.getString("Status"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public java.util.List<Session> findActiveSessionsForProgram(String programID) {
        java.util.List<Session> list = new java.util.ArrayList<>();

        String query = "SELECT * FROM SESSION_TBL WHERE Program_ID = ? AND Status = 'SCHEDULED'";

        try {
            Connection con = DBUtil.getDBConnection();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, programID);

           ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Session s = new Session();
                s.setSessionID(rs.getInt("Session_ID"));
                s.setSeniorID(rs.getString("Senior_ID"));
                s.setProgramID(rs.getString("Program_ID"));
                s.setSessionDate(rs.getDate("Session_Date"));
                s.setTimeSlot(rs.getString("Time_Slot"));
                s.setClinicianName(rs.getString("Clinician_Name"));
                s.setStatus(rs.getString("Status"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


}
