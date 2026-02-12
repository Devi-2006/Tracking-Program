package com.scwellness.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.scwellness.bean.Program;
import com.scwellness.bean.Senior;
import com.scwellness.bean.Session;
import com.scwellness.dao.ProgramDAO;
import com.scwellness.dao.SeniorDAO;
import com.scwellness.dao.SessionDAO;
import com.scwellness.util.ActiveSessionsExistException;
import com.scwellness.util.DBUtil;
import com.scwellness.util.SessionConflictException;
import com.scwellness.util.ValidationException;

public class WellnessService {

    public Senior viewSeniorDetails(String seniorID) {
        if (seniorID == null )
            return null;
        SeniorDAO seniorDAO = new SeniorDAO();
        Senior senior = seniorDAO.findSenior(seniorID);
        return senior;
    }
    
    public List<Senior> viewAllSeniors() {
        SeniorDAO seniorDAO = new SeniorDAO();
        return seniorDAO.viewAllSeniors();
    }

    public String registerNewSenior(Senior senior) {
        SeniorDAO seniorDAO = new SeniorDAO();
        try {
            if (senior == null) {
                throw new ValidationException("Senior details cannot be null");
            }
            if (senior.getSeniorID() == null|| senior.getFullName() == null
                    || senior.getPrimaryPhone() == null 
                    || senior.getEmergencyContactPhone() == null) {
                throw new ValidationException("Required senior details are missing");
            }
            if (senior.getAge() < 55) {
                throw new ValidationException("Senior age must be 55 or above");
            }
            if (seniorDAO.findSenior(senior.getSeniorID()) != null) {
                throw new ValidationException("Senior ID already exists");
            }
            senior.setStatus("ACTIVE");
            boolean inserted = seniorDAO.insertSenior(senior);

            if (inserted) {
                return "SUCCESS";
            } else {
                return "FAILED";
            }

        } catch (ValidationException ve) {
            return ve.toString();   
        } catch (Exception e) {
            return "ERROR: Unable to register senior";
        }
    }
    
    public Program viewProgramDetails(String programID) {
        if (programID == null || programID.trim().isEmpty()) {
            return null;
        }
        ProgramDAO programDAO = new ProgramDAO();
        Program program = programDAO.findProgram(programID);
        return program;
    }


    public List<Program> viewAllPrograms() {
        return new ProgramDAO().viewAllPrograms();
    }

    public String createProgram(Program program) {
        ProgramDAO programDAO = new ProgramDAO();
        try {
            if (program == null) {
                throw new ValidationException("Program details cannot be null");
            }
            if (program.getProgramID() == null 
                    || program.getProgramName() == null 
                    || program.getCategory() == null ) {

                throw new ValidationException("Required program details are missing");
            }

            if (program.getRecommendedDurationWeeks() <= 0) {
                throw new ValidationException("Program duration must be greater than zero");
            }
            if (programDAO.findProgram(program.getProgramID()) != null) {
                throw new ValidationException("Program ID already exists");
            }
            program.setStatus("ACTIVE");
            boolean inserted = programDAO.insertProgram(program);
            if (inserted) {
                return "SUCCESS";
            } else {
                return "FAILED";
            }

        } catch (ValidationException ve) {
            return ve.toString();  
        } catch (Exception e) {
            return "ERROR: Unable to create program";
        }
    }
    public String scheduleSession(String seniorID, String programID,Date sessionDate, String timeSlot, String clinicianName) {
        SessionDAO sessionDAO = new SessionDAO();
        SeniorDAO seniorDAO = new SeniorDAO();
        ProgramDAO programDAO = new ProgramDAO();
        try {
            if (seniorID == null || seniorID.trim().isEmpty()
                    || programID == null || programID.trim().isEmpty()
                    || timeSlot == null || timeSlot.trim().isEmpty()
                    || clinicianName == null || clinicianName.trim().isEmpty()
                    || sessionDate == null) {

                throw new ValidationException("Invalid session details");
            }
            if (seniorDAO.findSenior(seniorID) == null) {
                throw new ValidationException("Senior does not exist");
            }
            if (programDAO.findProgram(programID) == null) {
                throw new ValidationException("Program does not exist");
            }
            if (!sessionDAO.findConflictingSessions(seniorID, sessionDate, timeSlot).isEmpty()) {
                throw new SessionConflictException("Session Confilts");
            }
            Session session = new Session();
            session.setSessionID(sessionDAO.generateSessionID());
            session.setSeniorID(seniorID);
            session.setProgramID(programID);
            session.setSessionDate(sessionDate);
            session.setTimeSlot(timeSlot);
            session.setClinicianName(clinicianName);
            session.setStatus("SCHEDULED");
            boolean inserted = sessionDAO.insertSession(session);

            if (inserted) {
                return "SUCCESS";
            } else {
                return "FAILED";
            }
        } catch (ValidationException ve) {
            return ve.toString();

        } catch (SessionConflictException se) {
            return se.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: Unable to schedule session";
        }
    }
    
    public boolean recordSessionOutcome(int sessionID, String status,
            String bloodPressure, java.math.BigDecimal sugarLevel, String notes) {
        SessionDAO sessionDAO = new SessionDAO();
        try {
            if (sessionID <= 0) {
                throw new ValidationException("Invalid session ID");
            }
            if (status == null ||
               (!status.equalsIgnoreCase("COMPLETED") && !status.equalsIgnoreCase("MISSED"))) {
                throw new ValidationException("Invalid session status");
            }
            Session session = sessionDAO.findSession(sessionID);
            if (session == null) {
                return false;
            }
            if (!"SCHEDULED".equalsIgnoreCase(session.getStatus())) {
                return false;
            }
            return sessionDAO.updateSessionOutcome(
                    sessionID, status, bloodPressure, sugarLevel, notes);

        } catch (ValidationException ve) {
            System.out.println(ve.toString());
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Session> listSessionsBySenior(String seniorID) {
        SessionDAO sessionDAO = new SessionDAO();
        return sessionDAO.findSessionsBySenior(seniorID);
    }
    
    public List<Session> listSessionsByProgram(String programID) {
        return new SessionDAO().findSessionsByProgram(programID);
    }

    public List<Session> listSessionsByDate(java.sql.Date sessionDate) {
        return new SessionDAO().findSessionsByDate(sessionDate);
    }

    public boolean deactivateSenior(String seniorID) {

        SeniorDAO seniorDAO = new SeniorDAO();
        SessionDAO sessionDAO = new SessionDAO();

        try {
            if (seniorID == null ) {
                throw new ValidationException("Senior ID cannot be blank");
            }
            Senior senior = seniorDAO.findSenior(seniorID);
            if (senior == null) {
                return false;
            }
            if (!sessionDAO.findActiveSessionsForSenior(seniorID).isEmpty()) {
                throw new ActiveSessionsExistException("No active session");
            }
            return seniorDAO.updateSeniorStatus(seniorID, "INACTIVE");

        } catch (ValidationException ve) {
            System.out.println(ve.toString());
            return false;

        } catch (ActiveSessionsExistException ae) {
            System.out.println(ae.toString());
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deactivateProgram(String programID) {
        ProgramDAO programDAO = new ProgramDAO();
        SessionDAO sessionDAO = new SessionDAO();
        try {
            if (programID == null) {
                throw new ValidationException("Program ID cannot be blank");
            }
            Program program = programDAO.findProgram(programID);
            if (program == null) {
                return false;
            }
            if (!sessionDAO.findActiveSessionsForProgram(programID).isEmpty()) {
                throw new ActiveSessionsExistException(" No Session active");
            }
            return programDAO.updateProgramStatus(programID, "INACTIVE");

        } catch (ValidationException ve) {
            System.out.println(ve.toString());
            return false;
        } catch (ActiveSessionsExistException ae) {
            System.out.println(ae.toString());
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean removeSenior(String seniorID) {
        SeniorDAO seniorDAO = new SeniorDAO();
        SessionDAO sessionDAO = new SessionDAO();
        try {
            if (seniorID == null) {
                throw new ValidationException("Senior ID cannot be blank");
            }
            if (!sessionDAO.findActiveSessionsForSenior(seniorID).isEmpty()) {
                throw new ActiveSessionsExistException("NO Session active");
            }
            return seniorDAO.deleteSenior(seniorID);
        } catch (ValidationException ve) {
            System.out.println(ve.toString());
            return false;

        } catch (ActiveSessionsExistException ae) {
            System.out.println(ae.toString());
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeProgram(String programID) {
        ProgramDAO programDAO = new ProgramDAO();
        SessionDAO sessionDAO = new SessionDAO();
        try {
            if (programID == null || programID.trim().isEmpty()) {
                throw new ValidationException("Program ID cannot be blank");
            }
            if (!sessionDAO.findActiveSessionsForProgram(programID).isEmpty()) {
                throw new ActiveSessionsExistException("No session active");
            }
            return programDAO.deleteProgram(programID);

        } catch (ValidationException ve) {
            System.out.println(ve.toString());
            return false;

        } catch (ActiveSessionsExistException ae) {
            System.out.println(ae.toString());
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
