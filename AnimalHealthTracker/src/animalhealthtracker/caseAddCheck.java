/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalhealthtracker;

/**
 *
 * @author Shakila Kamalasena
 */
public class caseAddCheck {
    
    private String caseId;
    private String species;
    private String date;
    private String gender;
    private String followDate;

    public caseAddCheck(String caseId, String species, String date, String gender, String followDate) {
        this.caseId = caseId;
        this.species = species;
        this.date = date;
        this.gender = gender;
        this.followDate = followDate;
    }

    public String getCaseId() {
        return caseId;
    }

    public String getSpecies() {
        return species;
    }

    public String getDate() {
        return date;
    }

    public String getGender() {
        return gender;
    }

    public String getFollowDate() {
        return followDate;
    }
    
    
    
}
