package com.scwellness.app;

import java.sql.Date;

import com.scwellness.bean.Program;
import com.scwellness.bean.Senior;
import com.scwellness.service.WellnessService;

public class WellnessMain {

    public static void main(String[] args) {

        WellnessService service = new WellnessService();

        Senior s = new Senior();
        s.setSeniorID("SR2005");
        s.setFullName("Ramesh Iyer");
        s.setAge(69);
        s.setGender("MALE");
        s.setChronicConditions("Cardiac History");
        s.setEmergencyContactName("Kavitha Iyer");
        s.setEmergencyContactPhone("9123456780");

        String seniorResult = service.registerNewSenior(s);
        System.out.println("Register Senior Result: " + seniorResult);

        Program program = new Program();
        program.setProgramID("PRGCC02");
        program.setProgramName("Cardiac Care – Level 2");
        program.setCategory("CARDIAC_CARE");
        program.setRecommendedDurationWeeks(6);
        program.setSessionFrequency("Weekly monitoring session");

        String programResult = service.createProgram(program);
        System.out.println("Create Program Result: " + programResult);

        Date sessionDate = Date.valueOf("2026-02-07");

        String sessionResult = service.scheduleSession(
                "SR2005",
                "PRGCC02",
                sessionDate,
                "10:30-11:00",
                "Dr. Anitha Rao"
        );

        System.out.println("Schedule Session Result: " + sessionResult);
    }
}
