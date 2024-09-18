package animalhealthtracker;

import com.mysql.jdbc.Connection;
import java.net.URL;
//import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VetPageController implements Initializable {

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
    private TextField register_fname;

    @FXML
    private AnchorPane register_form;

    @FXML
    private ComboBox<?> register_gender;

    @FXML
    private TextField register_lname;

    @FXML
    private Hyperlink register_loginHere;

    @FXML
    private PasswordField register_password;

    @FXML
    private TextField register_phone;

    @FXML
    private TextField register_showPassword;

    @FXML
    private Button register_signupBtn;

    @FXML
    private ComboBox<?> register_specialization;

    @FXML
    private TextField register_username;

    // DATABASE CONNECTIVITY
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private final AlertMessage alert = new AlertMessage();

    private final String[] listGender = {"Male", "Female", "Others"};

    @FXML
    public void registerGenderList() {
        List<String> listG = new ArrayList<>();

        for (String data : listGender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        register_gender.setItems(listData);
    }

    private final String[] listSpecialization = {"Large/Small", "Avian & Exotic", "Wildlife", "Surgery", "Pathology", "Others"};

    @FXML
    public void registerSpecializationList() {
        List<String> listS = new ArrayList<>();

        for (String data : listSpecialization) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        register_specialization.setItems(listData);
    }
    
    // FILLS THE ALL PASSWORD TEXTFIELDS
    public void settingTexts() { 
        if (!login_checkBtn.isSelected()) {
            login_showPassword.setText(login_password.getText());
        } else {
            login_password.setText(login_showPassword.getText());
        }
    }

    @FXML
    public void loginAccount() {
        
        settingTexts();

        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/ Password empty");
        } else {
            String sql = "SELECT * FROM vet WHERE username = ? AND password = ?";

            connect = Database.connectDB();

            try {
                
//                settingTexts();
                
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
                prepare.setString(2, login_password.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    getData.username = login_username.getText();
                    //IF THE ENTERED USERNAME AND PASSWORD ARE CORRECT
                    alert.successMessage("Login Successfully!");

                    //GO TO VET MAIN FORM IF THE CREDINTIALS ARE OK
                    Parent root = FXMLLoader.load(getClass().getResource("VetMainForm.fxml"));
                    Stage stage = new Stage();

                    stage.setTitle("Animal Health Tracker | Veterinarian portal");

                    stage.setMinWidth(1400);
                    stage.setMinHeight(750);

                    stage.setScene(new Scene(root));
                    stage.show();

                    //TO CLOSE THE VET LOGIN PAGE
                    login_loginBtn.getScene().getWindow().hide();

                } else {
                    //IF THE ENTERED USERNAME OR PASSWORD IS INCORRECT
                    alert.errorMessage("Incorrect Username/ Password");
                }

                connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
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

    @FXML
    public void registerAccount() {

        if (register_fname.getText().isEmpty()
                || register_lname.getText().isEmpty()
                || register_gender.getSelectionModel().getSelectedItem() == null
                || register_phone.getText().isEmpty()
                || register_specialization.getSelectionModel().getSelectedItem() == null
                || register_username.getText().isEmpty()
                || register_password.getText().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            //CHECK WHETHER THE ENTERED USERNAME ALREADY EXISTS
            String checkUsername = "SELECT * FROM vet WHERE username = '" + register_username.getText() + "'";

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
                    //INSERTING USERDATA TO THE DATABASE
                    String insertData = "INSERT INTO vet(firstName, lastName, gender, phoneNo, specialization, username, password, date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, register_fname.getText());
                    prepare.setString(2, register_lname.getText());
                    prepare.setString(3, (String) register_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(4, register_phone.getText());
                    prepare.setString(5, (String) register_specialization.getSelectionModel().getSelectedItem());
                    prepare.setString(6, register_username.getText());
                    prepare.setString(7, register_password.getText());
                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert.successMessage("Registered Successfully!");
                    registerClear();

                    //SWITCHING TO LOGIN FORM AFTER SUCCESSFUL REGISTRATION
                    login_form.setVisible(true);
                    register_form.setVisible(false);

                    connect.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void registerClear() {
        register_fname.clear();
        register_lname.clear();
        register_gender.getSelectionModel().clearSelection();
        register_phone.clear();
        register_specialization.getSelectionModel().clearSelection();
        register_username.clear();
        register_password.clear();
        register_showPassword.clear();
    }

    @FXML
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
        if (login_user.getSelectionModel().getSelectedItem() == "Veterinary Portal") {

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

        } else if (login_user.getSelectionModel().getSelectedItem() == "Admin Portal") {

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

        }

        // CLOSE SELECTED USER TYPE WINDOW
        login_user.getScene().getWindow().hide();

    }

    @FXML
    //Switching login form and register form
    public void switchForm(ActionEvent event) {
        try {
            System.out.println("Switching form...");
            if (event.getSource() == register_loginHere) {
                System.out.println("Switching to Login Form...");
                login_form.setVisible(true);
                register_form.setVisible(false);
            } else if (event.getSource() == login_registerHere) {
                System.out.println("Switching to Register Form...");
                login_form.setVisible(false);
                register_form.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL locatio, ResourceBundle resources) {
        userList();
        registerGenderList();
        registerSpecializationList();
    }

}
