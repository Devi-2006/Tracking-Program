package com.scwellness.bean;

public class Program {
	 	private String programID;
	 	private String programName;
	    private String category;
	    private int recommendedDurationWeeks;
	    private String sessionFrequency;
	    private String status;
	    public String getProgramID() {
			return programID;
		}
		public void setProgramID(String programID) {
			this.programID = programID;
		}
		public String getProgramName() {
			return programName;
		}
		public void setProgramName(String programName) {
			this.programName = programName;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public int getRecommendedDurationWeeks() {
			return recommendedDurationWeeks;
		}
		public void setRecommendedDurationWeeks(int recommendedDurationWeeks) {
			this.recommendedDurationWeeks = recommendedDurationWeeks;
		}
		public String getSessionFrequency() {
			return sessionFrequency;
		}
		public void setSessionFrequency(String sessionFrequency) {
			this.sessionFrequency = sessionFrequency;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		
}
