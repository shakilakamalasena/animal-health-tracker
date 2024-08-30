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
public class caseInfoData {
    
    private String caseId;
    private String species;
    private Date date;
    private String gender;
    private Date followDate;

    public caseInfoData(String caseId, String species, Date date, String gender, Date followDate) {
        this.caseId = caseId;
        this.species = species;
        this.date = date;
        this.gender = gender;
        this.followDate = followDate;
    }

    public caseInfoData(String caseId) {
        this.caseId = caseId;
    }
    
    

    public String getCaseId() {
        return caseId;
    }

    public String getSpecies() {
        return species;
    }

    public Date getDate() {
        return date;
    }

    public String getGender() {
        return gender;
    }

    public Date getFollowDate() {
        return followDate;
    }

        
    
    
    
}
