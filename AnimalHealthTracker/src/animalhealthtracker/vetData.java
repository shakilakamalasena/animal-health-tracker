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
public class vetData {
    
    private String firstName;
    private String lastName;
    private String gender;
    private String phoneNo;
    private String specialization;
    private String username;
    private String password;
    private Date date;

    public vetData(String firstName, String lastName, String gender, String phoneNo, String specialization, String username, String password, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.specialization = specialization;
        this.username = username;
        this.password = password;
        this.date = date;
    }

    public vetData(String firstName, String lastName, String gender, String phoneNo, String specialization, String username, Date date) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNo = phoneNo;
        this.specialization = specialization;
        this.username = username;
        this.date = date;
    }
    
    

    public vetData(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Date getDate() {
        return date;
    }
    
}
