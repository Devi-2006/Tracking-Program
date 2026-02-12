package com.scwellness.bean;

import java.math.BigDecimal;
import java.sql.Date;

public class Session {
	private int sessionID;
    private String seniorID;
    private String programID;
    private Date sessionDate;
    private String timeSlot;
    private String clinicianName;
    private String bloodPressure;
    private BigDecimal sugarLevel;
    private String sessionNotes;
    private String status;
	public int getSessionID() {
		return sessionID;
	}
	public void setSessionID(int sessionID) {
		this.sessionID = sessionID;
	}
	public String getSeniorID() {
		return seniorID;
	}
	public void setSeniorID(String seniorID) {
		this.seniorID = seniorID;
	}
	public String getProgramID() {
		return programID;
	}
	public void setProgramID(String programID) {
		this.programID = programID;
	}
	public Date getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(Date sessionDate) {
		this.sessionDate = sessionDate;
	}
	public String getTimeSlot() {
		return timeSlot;
	}
	public void setTimeSlot(String timeSlot) {
		this.timeSlot = timeSlot;
	}
	public String getClinicianName() {
		return clinicianName;
	}
	public void setClinicianName(String clinicianName) {
		this.clinicianName = clinicianName;
	}
	public String getBloodPressure() {
		return bloodPressure;
	}
	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}
	public BigDecimal getSugarLevel() {
		return sugarLevel;
	}
	public void setSugarLevel(BigDecimal sugarLevel) {
		this.sugarLevel = sugarLevel;
	}
	public String getSessionNotes() {
		return sessionNotes;
	}
	public void setSessionNotes(String sessionNotes) {
		this.sessionNotes = sessionNotes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
