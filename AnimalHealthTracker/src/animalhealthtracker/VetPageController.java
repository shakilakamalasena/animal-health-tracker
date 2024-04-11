package animalhealthtracker;

import Components.Users;
import Components.Database;
import Components.AlertMessage;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static javax.management.remote.JMXConnectorFactory.connect;

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
    private TextField register_email;

    @FXML
    private AnchorPane register_form;

    @FXML
    private TextField register_fullName;

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

    // DATABASE CONNECTIVITY
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private AlertMessage alert = new AlertMessage();

    @FXML
    public void loginAccount() {

        if (login_username.getText().isEmpty() || login_password.getText().isEmpty()) {
            alert.errorMessage("Incorrect Username/ Password");
        } else {
            String sql = "SELECT * FROM vet WHERE username = ? AND password = ?";

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
                prepare.setString(2, login_password.getText());
                result = prepare.executeQuery();

                if (result.next()) {
                    //IF THE ENTERED USERNAME AND PASSWORD ARE CORRECT
                    alert.successMessage("Login Successfully!");
                } else {
                    //IF THE ENTERED USERNAME OR PASSWORD IS INCORRECT
                    alert.errorMessage("Incorrect Username/ Password");
                }
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

        if (register_fullName.getText().isEmpty() || register_email.getText().isEmpty() || register_username.getText().isEmpty() || register_password.getText().isEmpty()) {
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
                    String insertData = "INSERT INTO vet(fullName, email, username, password) VALUES(?, ?, ?, ?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, register_fullName.getText());
                    prepare.setString(2, register_email.getText());
                    prepare.setString(3, register_username.getText());
                    prepare.setString(4, register_password.getText());

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
                Parent root = FXMLLoader.load(getClass().getResource("AdminLoginPage.fxml"));
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
        if (event.getSource() == register_loginHere) {
            login_form.setVisible(true);
            register_form.setVisible(false);
        } else if (event.getSource() == login_registerHere) {
            login_form.setVisible(false);
            register_form.setVisible(true);
        }
    }

    @Override
    public void initialize(URL locatio, ResourceBundle resources) {
        userList();
    }

}
