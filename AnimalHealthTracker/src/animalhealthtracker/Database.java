/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalhealthtracker;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Shakila Kamalasena
 */
public class Database {
    public static Connection connectDB(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection connect = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/animalhealthtracker", "root", "");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
