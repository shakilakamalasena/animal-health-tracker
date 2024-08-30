/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalhealthtracker;

import java.util.Date;

/**
 *
 * @author Shakila Kamalasena
 */
public class diagnosticData {
    
    private String caseId;
    private Date date;
    private String tests;
    private String treatments;
    private Date followDate;

    public diagnosticData(String caseId, Date date, String tests, String treatments, Date followDate) {
        this.caseId = caseId;
        this.date = date;
        this.tests = tests;
        this.treatments = treatments;
        this.followDate = followDate;
    }

    public diagnosticData(Date date, String tests, String treatments) {
        this.date = date;
        this.tests = tests;
        this.treatments = treatments;
    }

    public diagnosticData(String caseId, Date date, String tests, String treatments) {
        this.caseId = caseId;
        this.date = date;
        this.tests = tests;
        this.treatments = treatments;
    }
    
    

    public String getCaseId() {
        return caseId;
    }

    public Date getDate() {
        return date;
    }

    public String getTests() {
        return tests;
    }

    public String getTreatments() {
        return treatments;
    }

    public Date getFollowDate() {
        return followDate;
    }
    
    
    
}
