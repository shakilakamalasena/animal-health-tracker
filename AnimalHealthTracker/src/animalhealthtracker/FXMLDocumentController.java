/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package animalhealthtracker;

import com.mysql.jdbc.Connection;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Shakila Kamalasena
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private CheckBox login_checkBtn;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Button login_loginBtn;

    @FXML
    private PasswordField login_password;

    @FXML
    private Hyperlink login_registerHere;

    @FXML
    private TextField login_showPassword;

    @FXML
    private ComboBox<?> login_user;

    @FXML
    private TextField login_username;

    @FXML
    private AnchorPane main_form;

    @FXML
    private CheckBox register_checkBox;

    @FXML
    private TextField register_email;

    @FXML
    private AnchorPane register_form;

    @FXML
    private Hyperlink register_loginHere;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_showPassword;

    @FXML
    private Button register_signupBtn;

    @FXML
    private TextField register_username;

    //Database Tools
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();

    // FILLS THE ALL PASSWORD TEXTFIELDS
    public void settingTexts() {
        if (!login_checkBtn.isSelected()) {
            login_showPassword.setText(login_password.getText());
        } else {
            login_password.setText(login_showPassword.getText());
        }
    }

    public void loginAccount() {
        
        settingTexts();
        
        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/ Password");
        } else {
            String sql = "SELECT * FROM admin WHERE username = ?";

            connect = Database.connectDB();

            try {
                if (!login_showPassword.isVisible()) {
                    if (!login_showPassword.getText().equals(login_password.getText())) {
                        login_showPassword.setText(login_password.getText());
                    }
                } else {
                    if (!login_showPassword.getText().equals(login_password.getText())) {
                        login_password.setText(login_showPassword.getText());
                    }
                }

                prepare = connect.prepareStatement(sql);
                prepare.setString(1, login_username.getText());
                result = prepare.executeQuery();

                if (result.next()) {

                    String storedHashedPassword = result.getString("password"); // GET THE HASHED PASSWORD FROM DB

                    // VERIFY THE ENTERED PASSWORD WITH THE HASHED PASSWORD
                    if (BCrypt.checkpw(login_password.getText(), storedHashedPassword)) {
                        getData.username = login_username.getText();

                        // IF THE ENTERD CREDENTIALS ARE CORRECT
                        alert.successMessage("Login Successfully!");

                        // GO TO ADMIN MAIN FORM
                        Parent root = FXMLLoader.load(getClass().getResource("AdminMainForm.fxml"));
                        Stage stage = new Stage();
                        stage.setTitle("Animal Health Tracker | Admin portal");
                        stage.setScene(new Scene(root));
                        stage.show();

                        // TO CLOSE THE ADMIN LOGIN PAGE
                        login_loginBtn.getScene().getWindow().hide();
                    } else {
                        // IF THE PASSWORD IS CORRECT
                        alert.errorMessage("Incorrect Username/ Password");
                    }
                } else {
                    //IF THE ENTERED USERNAME OR PASSWORD IS INCORRECT
                    alert.errorMessage("Incorrect Username/ Password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loginShowPassword() {
        if (login_checkBtn.isSelected()) {
            login_showPassword.setText(login_password.getText());
            login_showPassword.setVisible(true);
            login_password.setVisible(false);
        } else {
            login_password.setText(login_showPassword.getText());
            login_showPassword.setVisible(false);
            login_password.setVisible(true);
        }
    }
    
    // FILLS THE ALL PASSWORD TEXTFIELDS IN REGISTER WINDOW
    public void settingTextsReg() { 
        if (!register_checkBox.isSelected()) {
            register_showPassword.setText(register_password.getText());
        } else {
            register_password.setText(register_showPassword.getText());
        }
    }

    public void registerAccount() {
        
        settingTextsReg();
        
        if (register_email.getText().isEmpty() || register_username.getText().isEmpty() || register_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            //CHECK WHETHER THE ENTERED USERNAME ALREADY EXISTS
            String checkUsername = "SELECT * FROM admin WHERE username = '" + register_username.getText() + "'";

            connect = Database.connectDB();

            try {
                if (!register_showPassword.isVisible()) {
                    if (!register_showPassword.getText().equals(register_password.getText())) {
                        register_showPassword.setText(register_password.getText());
                    }
                } else {
                    if (!register_showPassword.getText().equals(register_password.getText())) {
                        register_password.setText(register_showPassword.getText());
                    }
                }

                prepare = connect.prepareStatement(checkUsername);
                result = prepare.executeQuery();

                if (result.next()) {
                    alert.errorMessage(register_username.getText() + " is already exists!");
                } else if (register_password.getText().length() < 8) {
                    alert.errorMessage("Password must be atleast 8 characters");
                } else {

                    // HASH PASSWORD USING BCRYPT
                    String plainPassword = register_password.getText();
                    String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());

                    //INSERTING USERDATA TO THE DATABASE
                    String insertData = "INSERT INTO admin(email, username, password) VALUES(?, ?, ?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, register_email.getText());
                    prepare.setString(2, register_username.getText());
                    prepare.setString(3, hashedPassword);

                    prepare.executeUpdate();

                    alert.successMessage("Registered Successfully!");
                    registerClear();

                    //SWITCHING TO LOGIN FORM AFTER SUCCESSFUL REGISTRATION
                    login_form.setVisible(true);
                    register_form.setVisible(false);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void registerClear() {
        register_email.clear();
        register_username.clear();
        register_password.clear();
        register_showPassword.clear();
    }

    public void registerShowPassword() {
        if (register_checkBox.isSelected()) {
            register_showPassword.setText(register_password.getText());
            register_showPassword.setVisible(true);
            register_password.setVisible(false);
        } else {
            register_password.setText(register_showPassword.getText());
            register_showPassword.setVisible(false);
            register_password.setVisible(true);
        }
    }

    public void userList() {
        List<String> listU = new ArrayList<>();

        for (String data : Users.user) {
            listU.add(data);
        }

        ObservableList listData = FXCollections.observableList(listU);
        login_user.setItems(listData);
    }

    // SWITCH PAGES WHEN THE USER TYPE CHANGES
    public void switchPage() {
        if (login_user.getSelectionModel().getSelectedItem() == "Admin Portal") {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Animal Health Tracker (Admin Portal)");

                stage.setMinWidth(700);
                stage.setMinHeight(500);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (login_user.getSelectionModel().getSelectedItem() == "Veterinary Portal") {

            try {
                Parent root = FXMLLoader.load(getClass().getResource("VetPage.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Animal Health Tracker (Veterinary Portal)");

                stage.setMinWidth(700);
                stage.setMinHeight(500);

                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        // CLOSE SELECTED USER TYPE WINDOW
        login_user.getScene().getWindow().hide();

    }

    //Switching login form and register form
    public void switchForm(ActionEvent event) {

        if (event.getSource() == login_registerHere) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        } else if (event.getSource() == register_loginHere) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        userList();
    }

}
