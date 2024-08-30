/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalhealthtracker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javax.tools.DiagnosticListener;

/**
 *
 * @author Shakila Kamalasena
 */
public class VetMainFormController implements Initializable {

    @FXML
    private TableColumn<caseInfoData, String> todayCases_col;

    @FXML
    private TableView<caseInfoData> todayCases_tableView;
    
    @FXML
    private TextField showCurrentPassword;

    @FXML
    private TextField showNewPassword;

    @FXML
    private TextField showReNewPassword;
    
    @FXML
    private PasswordField currentPassword;
    
    @FXML
    private PasswordField newPassword;

    @FXML
    private PasswordField reNewPassword;
    
    @FXML
    private Circle activeIcon;
    
    @FXML
    private Label hiddenPwd;

    @FXML
    private Label activeLabel;
    
    @FXML
    private Circle inactiveIcon;

    @FXML
    private Label inactiveLabel;
    
    @FXML
    private Button addCase_add_btn;
    
    @FXML
    private AnchorPane settings_form;
    
    @FXML
    private Button setting_btn;

    @FXML
    private TextField addCase_age;

    @FXML
    private TextField addCase_caseId;

    @FXML
    private TableColumn<caseInfoData, String> addCase_caseId_col;

    @FXML
    private Button addCase_clear_btn;

    @FXML
    private TextField addCase_date;

    @FXML
    private TableColumn<caseInfoData, String> addCase_examDate_col;

    @FXML
    private TextField addCase_followDate;

    @FXML
    private TableColumn<caseInfoData, String> addCase_followDate_col;

    @FXML
    private AnchorPane addCase_form;

    @FXML
    private ComboBox<?> addCase_gender;

    @FXML
    private TableColumn<caseInfoData, String> addCase_gender_col;

    @FXML
    private TextField addCase_heartRate;

    @FXML
    private TextField addCase_location;

    @FXML
    private TextField addCase_search;

    @FXML
    private TextField addCase_species;

    @FXML
    private TableColumn<caseInfoData, String> addCase_species_col;

    @FXML
    private TableView<caseInfoData> addCase_tableView;

    @FXML
    private TextField addCase_temp;

    @FXML
    private TextField addCase_tests;

    @FXML
    private TextField addCase_treatments;

    @FXML
    private Button addcase_btn;

    @FXML
    private Label dashboard_allCases;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_inactiveCases;

    @FXML
    private Label dashboard_todayCount;

    @FXML
    private Label date_time;

    @FXML
    private Label formName_top;

    @FXML
    private Button logout_btn;

    @FXML
    private Label name_left;

    @FXML
    private Label name_top;

    @FXML
    private AnchorPane updateCase_form;

    @FXML
    private TextField update_caseId;

    @FXML
    private Button update_clear_btn;

    @FXML
    private TextField update_date;

    @FXML
    private TableColumn<diagnosticData, String> update_date_col;

    @FXML
    private TableColumn<diagnosticData, String> update_caseId_col;

    @FXML
    private TextField update_followDate;

    @FXML
    private Button update_search;

    @FXML
    private TableView<diagnosticData> update_tableView;

    @FXML
    private TextField update_tests;

    @FXML
    private TableColumn<diagnosticData, String> update_tests_col;

    @FXML
    private TextField update_treatments;

    @FXML
    private TableColumn<diagnosticData, String> update_treatments_col;

    @FXML
    private Button update_update_btn;

    @FXML
    private Button updatecase_btn;

    @FXML
    private Label userId_left;
    
    @FXML
    private CheckBox setting_checkbox;
    

    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    Alert alert;

    // ================ NAVIGATION BAR METHODS ================
    // DATE UPDATE FUNCTION
    public void runTime() {
        new Thread() {
            public void run() {
                SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        date_time.setText(format.format(new Date()));
                    });
                }
            }
        }.start();
    }

    // SWITCH FORM FUNCTION
    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addCase_form.setVisible(false);
            updateCase_form.setVisible(false);
            settings_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: #738d9e");
            addcase_btn.setStyle("-fx-background-color: transparent");
            updatecase_btn.setStyle("-fx-background-color: transparent");
            setting_btn.setStyle("-fx-background-color: transparent");

            formName_top.setText("Dashboard");
            
            caseInfoShowListDates();
            todayCount();
            caseCount();
        } else if (event.getSource() == addcase_btn) {
            dashboard_form.setVisible(false);
            addCase_form.setVisible(true);
            updateCase_form.setVisible(false);
            settings_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            addcase_btn.setStyle("-fx-background-color: #738d9e");
            updatecase_btn.setStyle("-fx-background-color: transparent");
            setting_btn.setStyle("-fx-background-color: transparent");

            formName_top.setText("Add Case Form");

            //addCaseClear();
            addCaseGenderList();
            caseInfoShowListData();
        } else if (event.getSource() == updatecase_btn) {
            dashboard_form.setVisible(false);
            addCase_form.setVisible(false);
            updateCase_form.setVisible(true);
            settings_form.setVisible(false);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            addcase_btn.setStyle("-fx-background-color: transparent");
            updatecase_btn.setStyle("-fx-background-color: #738d9e");
            setting_btn.setStyle("-fx-background-color: transparent");

            formName_top.setText("Update Case Form");

            diagnosticsShowListData();
        } else if (event.getSource() == setting_btn) {
            dashboard_form.setVisible(false);
            addCase_form.setVisible(false);
            updateCase_form.setVisible(false);
            settings_form.setVisible(true);

            dashboard_btn.setStyle("-fx-background-color: transparent");
            addcase_btn.setStyle("-fx-background-color: transparent");
            updatecase_btn.setStyle("-fx-background-color: transparent");
            setting_btn.setStyle("-fx-background-color: #738d9e");

            formName_top.setText("Profile Setting");
            
            activeStatus();
        }
    }

    // DISPLAY USER DATA IN NAVIGATION BARS
    public void displayUserData() {
        String sql = "SELECT firstName, lastName FROM vet WHERE username = '" + getData.username + "'";
        
        connect = Database.connectDB();
        try {
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            vetData vetD;
            
            while (resultSet.next()) {                
                vetD = new vetData(resultSet.getString("firstName"), resultSet.getString("lastName"));
                name_top.setText(vetD.getFirstName());
                name_left.setText(vetD.getFirstName() + " " + vetD.getLastName());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

        formName_top.setText("Dashboard");

        userId_left.setText(getData.username);

    }
    
    // DASHBOARD CARD (COUNTS)
    public void caseCount() {
        String sql = "SELECT COUNT(caseId) FROM cases";
        
        connect = Database.connectDB();
        int count = 0;
        
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {                
                count = resultSet.getInt("COUNT(caseId)");
            }
            dashboard_allCases.setText(String.valueOf(count));
            
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void todayCount() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT COUNT(caseId) FROM cases WHERE followDate = '" + sqlDate + "'";
        
        connect = Database.connectDB();
        int count = 0;
        
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {                
                count = resultSet.getInt("COUNT(caseId)");
            }
            dashboard_todayCount.setText(String.valueOf(count));
            
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // DASHBOARD TABLE FUNCTIONS
    public ObservableList<caseInfoData> caseInfoListDates() {
        
        ObservableList<caseInfoData> listData = FXCollections.observableArrayList();
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT caseId FROM cases WHERE followDate = '" + sqlDate + "'";
        connect = Database.connectDB();
        
        try {
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            caseInfoData caseInfoD;
            
            while (resultSet.next()) {                
                caseInfoD = new caseInfoData(resultSet.getString("caseId"));
                listData.add(caseInfoD);
            }
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listData;
        
    }
    
    private ObservableList<caseInfoData> caseList;
    
    public void caseInfoShowListDates() {
        try {
            caseList = caseInfoListDates();
            
            todayCases_col.setCellValueFactory(new PropertyValueFactory<>("caseId"));
            
            todayCases_tableView.setItems(caseList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // LOGOUT BUTTON METHOD
    public void logout() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> optional = alert.showAndWait();

        try {
            if (optional.get().equals(ButtonType.OK)) {
                logout_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("VetPage.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Animal Health Tracker (Veterinarian Portal)");

                stage.setMinWidth(700);
                stage.setMinHeight(500);

                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // ACTIVE STATUS FUNCTION
    public void activeStatus() {
        
        final String DEFAULT_PASSWORD = "12345678";
        String sql = "SELECT password FROM vet WHERE username = '" + getData.username + "'";
        connect = Database.connectDB();
        
        try {
            
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {                
                hiddenPwd.setText(resultSet.getString("password"));
                
                String REAL_PASSWORD = hiddenPwd.getText();

                if (DEFAULT_PASSWORD.equals(REAL_PASSWORD)) {
                    
                    activeIcon.setVisible(false);
                    inactiveIcon.setVisible(true);

                    activeLabel.setVisible(false);
                    inactiveLabel.setVisible(true);

                } else {

                    activeIcon.setVisible(true);
                    inactiveIcon.setVisible(false);

                    activeLabel.setVisible(true);
                    inactiveLabel.setVisible(false);

                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    // ================ ADD CASE PAGE METHODS ================
    // GENDER SELECT COMBO BOX IN ADD CASE PAGE
    private final String[] listGender = {"Male", "Female", "Others"};

    @FXML
    public void addCaseGenderList() {
        List<String> listG = new ArrayList<>();

        for (String data : listGender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        addCase_gender.setItems(listData);
    }

    // CLEAR BUTTON IN ADD CASE PAGE
    public void addCaseClear() {
        addCase_caseId.setText("");
        addCase_species.setText("");
        addCase_gender.getSelectionModel().clearSelection();
        addCase_age.setText("");
        addCase_location.setText("");
        addCase_date.setText("");
        addCase_heartRate.setText("");
        addCase_temp.setText("");
        addCase_tests.setText("");
        addCase_treatments.setText("");
        addCase_followDate.setText("");
    }

    // ADD CASE BUTTON FUNCTIONALITY (CASE WAS ADDED INITIALLY BY HERE)
    public void addCaseAdd() {
        try {

            String sql1 = "INSERT INTO cases(caseId, species, gender, age, location, date, heartRate, bodyTemp, tests, treatments, followDate) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sql2 = "INSERT INTO case_info(caseId, species, date, gender, followDate) VALUES(?, ?, ?, ?, ?)";
            String sql3 = "INSERT INTO diagnostics(caseId, date, tests, treatments, followDate) VALUES(?, ?, ?, ?, ?)";

            connect = Database.connectDB();

            if (addCase_caseId.getText().isEmpty()
                    || addCase_species.getText().isEmpty()
                    || addCase_gender.getSelectionModel().getSelectedItem() == null
                    || addCase_age.getText().isEmpty()
                    || addCase_location.getText().isEmpty()
                    || addCase_date.getText().isEmpty()
                    || addCase_heartRate.getText().isEmpty()
                    || addCase_temp.getText().isEmpty()
                    || addCase_tests.getText().isEmpty()
                    || addCase_treatments.getText().isEmpty()
                    || addCase_followDate.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();

            } else {

                String check = "SELECT caseId FROM cases WHERE caseId = '" + addCase_caseId.getText() + "'";

                statement = connect.createStatement();
                resultSet = statement.executeQuery(check);

                if (resultSet.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Case ID: " + addCase_caseId.getText() + " was already exists!");
                    alert.showAndWait();
                } else if(inactiveLabel.isVisible()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("You are inactive. Please change your password to be active!");
                    alert.showAndWait();
                }else {

                    // INSERT INTO cases DATABASE TABLE                
                    preparedStatement = connect.prepareStatement(sql1);
                    preparedStatement.setString(1, addCase_caseId.getText());
                    preparedStatement.setString(2, addCase_species.getText());
                    preparedStatement.setString(3, (String) addCase_gender.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(4, addCase_age.getText());
                    preparedStatement.setString(5, addCase_location.getText());
                    preparedStatement.setString(6, addCase_date.getText());
                    preparedStatement.setString(7, addCase_heartRate.getText());
                    preparedStatement.setString(8, addCase_temp.getText());
                    preparedStatement.setString(9, addCase_tests.getText());
                    preparedStatement.setString(10, addCase_treatments.getText());
                    preparedStatement.setString(11, addCase_followDate.getText());
                    preparedStatement.executeUpdate();

                    // INSERT INTO case_info DATABASE TABLE
                    preparedStatement = connect.prepareStatement(sql2);
                    preparedStatement.setString(1, addCase_caseId.getText());
                    preparedStatement.setString(2, addCase_species.getText());
                    preparedStatement.setString(3, addCase_date.getText());
                    preparedStatement.setString(4, (String) addCase_gender.getSelectionModel().getSelectedItem());
                    preparedStatement.setString(5, addCase_followDate.getText());
                    preparedStatement.executeUpdate();

                    // INSERT INTO diagnostics DATABASE TABLE
                    preparedStatement = connect.prepareStatement(sql3);
                    preparedStatement.setString(1, addCase_caseId.getText());
                    preparedStatement.setString(2, addCase_date.getText());
                    preparedStatement.setString(3, addCase_tests.getText());
                    preparedStatement.setString(4, addCase_treatments.getText());
                    preparedStatement.setString(5, addCase_followDate.getText());
                    preparedStatement.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added");
                    alert.showAndWait();

                    addCaseClear();

                }

                caseInfoShowListData();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ADD CASE TABLE FUNCTIONS
    public ObservableList<caseInfoData> caseInfoListData() {

        ObservableList<caseInfoData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM case_info";

        connect = Database.connectDB();

        try {

            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            caseInfoData caseInfoD;

            while (resultSet.next()) {
                caseInfoD = new caseInfoData(resultSet.getString("caseId"),
                        resultSet.getString("species"),
                        resultSet.getDate("date"),
                        resultSet.getString("gender"),
                        resultSet.getDate("followDate"));
                listData.add(caseInfoD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;

    }

    private ObservableList<caseInfoData> caseInfoList;

    public void caseInfoShowListData() {

        try {

            caseInfoList = caseInfoListData();

            addCase_caseId_col.setCellValueFactory(new PropertyValueFactory<>("caseId"));
            addCase_species_col.setCellValueFactory(new PropertyValueFactory<>("species"));
            addCase_examDate_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            addCase_gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
            addCase_followDate_col.setCellValueFactory(new PropertyValueFactory<>("followDate"));

            addCase_tableView.setItems(caseInfoList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void caseSelect() {
        caseInfoData caseD = addCase_tableView.getSelectionModel().getSelectedItem();
        //String selectedId = addCase_search.getText();
        int num = addCase_tableView.getSelectionModel().getFocusedIndex();

        if ((num - 1) < -1) {
            return;
        }

        String selectedId = caseD.getCaseId();

        String sql = "SELECT * FROM cases WHERE caseId = '" + selectedId + "'";
        connect = Database.connectDB();
        try {

            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                addCase_caseId.setText(resultSet.getString("caseId"));
                addCase_species.setText(resultSet.getString("species"));
                //addCase_gender.getSelectionModel().setSelectedItem(resultSet.getString(1));
                addCase_age.setText(String.valueOf(resultSet.getInt("age")));
                addCase_location.setText(resultSet.getString("location"));
                addCase_date.setText(String.valueOf(resultSet.getString("date")));
                addCase_heartRate.setText(String.valueOf(resultSet.getInt("heartRate")));
                addCase_temp.setText(String.valueOf(resultSet.getInt("bodyTemp")));
                addCase_tests.setText(resultSet.getString("tests"));
                addCase_treatments.setText(resultSet.getString("treatments"));
                addCase_followDate.setText(String.valueOf(resultSet.getString("followDate")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // ADD CASE FORM SEARCH FUNCTION
    public void addCaseSearch() {

        FilteredList<caseInfoData> filter = new FilteredList<>(caseInfoList, e -> true);

        addCase_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateCaseInfoData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateCaseInfoData.getCaseId().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCaseInfoData.getSpecies().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCaseInfoData.getDate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateCaseInfoData.getGender().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateCaseInfoData.getFollowDate().toString().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }

            });

        });

        SortedList<caseInfoData> sortedList = new SortedList<>(filter);

        sortedList.comparatorProperty().bind(addCase_tableView.comparatorProperty());
        addCase_tableView.setItems(sortedList);

    }

    // ================ UPDATE CASE PAGE METHODS ================
    // BUTTON FOR CLEAR THE FORM
    public void updateClear() {
        update_caseId.setText("");
        update_date.setText("");
        update_tests.setText("");
        update_treatments.setText("");
        update_followDate.setText("");
    }

    // BUTTON FOR UPDATE BUTTON
    public void updateUpdate() {

        try {

            if (update_caseId.getText().isEmpty()
                    || update_date.getText().isEmpty()
                    || update_tests.getText().isEmpty()
                    || update_treatments.getText().isEmpty()
                    || update_followDate.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Case ID: " + update_caseId.getText() + "?");
                Optional<ButtonType> optional = alert.showAndWait();

                if (optional.get().equals(ButtonType.OK)) {

                    String sql = "INSERT INTO diagnostics(caseId, date, tests, treatments, followDate) VALUES (?, ?, ?, ?, ?)";

                    connect = Database.connectDB();
                    preparedStatement = connect.prepareStatement(sql);
                    preparedStatement.setString(1, update_caseId.getText());
                    preparedStatement.setString(2, update_date.getText());
                    preparedStatement.setString(3, update_tests.getText());
                    preparedStatement.setString(4, update_treatments.getText());
                    preparedStatement.setString(5, update_followDate.getText());
                    preparedStatement.executeUpdate();
                    
                    String update = "UPDATE cases SET followDate = '" + update_followDate.getText() + "' WHERE caseId = '" + update_caseId.getText() + "'";
                    Connection con = Database.connectDB();
                    Statement st = con.createStatement();
                    st.executeUpdate(update);

                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    updateClear();
                    diagnosticsShowListData();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // UPDATE CASE TABLE FUNCTIONS
    public ObservableList<diagnosticData> diagnosticListData() {

        ObservableList<diagnosticData> listData = FXCollections.observableArrayList();
        String sql = "SELECT caseId, date, tests, treatments FROM diagnostics";

        try {

            connect = Database.connectDB();
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            diagnosticData diagnosticD;

            while (resultSet.next()) {
                diagnosticD = new diagnosticData(resultSet.getString("caseId"),
                        resultSet.getDate("date"),
                        resultSet.getString("tests"),
                        resultSet.getString("treatments"));
                listData.add(diagnosticD);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;

    }

    private ObservableList<diagnosticData> diagnosticList;

    public void diagnosticsShowListData() {

        try {

            diagnosticList = diagnosticListData();

            update_date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            update_tests_col.setCellValueFactory(new PropertyValueFactory<>("tests"));
            update_treatments_col.setCellValueFactory(new PropertyValueFactory<>("treatments"));
            update_caseId_col.setCellValueFactory(new PropertyValueFactory<>("caseId"));

            update_tableView.setItems(diagnosticList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // UPDATE CASE SEARCH OPTION
    public void updateCaseSearch() {

        FilteredList<diagnosticData> filter = new FilteredList<>(diagnosticList, e -> true);

        update_caseId.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateDiagnosticData -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

                if (predicateDiagnosticData.getCaseId().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDiagnosticData.getDate().toString().contains(searchKey)) {
                    return true;
                } else if (predicateDiagnosticData.getTests().toLowerCase().contains(searchKey)) {
                    return true;
                } else if (predicateDiagnosticData.getTreatments().toLowerCase().contains(searchKey)) {
                    return true;
                } else {
                    return false;
                }

            });

        });

        SortedList<diagnosticData> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(update_tableView.comparatorProperty());
        update_tableView.setItems(sortList);

    }
    
    
    // ================ SETTING FORM METHODS ================
    // HIDE/SHOW PASSWORD CHECKBOX FUNCTION
    public void showPassword() {
        if (setting_checkbox.isSelected()) {
            showCurrentPassword.setText(currentPassword.getText());
            showNewPassword.setText(newPassword.getText());
            showReNewPassword.setText(reNewPassword.getText());
            
            showCurrentPassword.setVisible(true);
            currentPassword.setVisible(false);
            showNewPassword.setVisible(true);
            newPassword.setVisible(false);
            showReNewPassword.setVisible(true);
            reNewPassword.setVisible(false);
        } else {
            currentPassword.setText(showCurrentPassword.getText());
            newPassword.setText(showNewPassword.getText());
            reNewPassword.setText(showReNewPassword.getText());
            
            showCurrentPassword.setVisible(false);
            currentPassword.setVisible(true);
            showNewPassword.setVisible(false);
            newPassword.setVisible(true);
            showReNewPassword.setVisible(false);
            reNewPassword.setVisible(true);
        }
    }
    
    // FILLS THE ALL 6 PASSWORD TEXTFIELDS
    public void settingTexts() { 
        if (!setting_checkbox.isSelected()) {
            showCurrentPassword.setText(currentPassword.getText());
            showNewPassword.setText(newPassword.getText());
            showReNewPassword.setText(reNewPassword.getText());

        } else {
            currentPassword.setText(showCurrentPassword.getText());
            newPassword.setText(showNewPassword.getText());
            reNewPassword.setText(showReNewPassword.getText());

        }
    }
    
    
    public void changePassword() {
        
        String pwd;
        if (setting_checkbox.isSelected()) {
            pwd = showNewPassword.getText();
        } else {
            pwd = newPassword.getText();
        }
        
        String sql = "UPDATE vet SET password = '" + pwd + "' WHERE username = '" + getData.username + "'";
        
        try {

            settingTexts();

            connect = Database.connectDB();
            
            if (showCurrentPassword.getText().isEmpty() 
                    || currentPassword.getText().isEmpty() 
                    || showNewPassword.getText().isEmpty()
                    || newPassword.getText().isEmpty() 
                    || showReNewPassword.getText().isEmpty() 
                    || reNewPassword.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();
            } else {
                
                if (!hiddenPwd.getText().equals(currentPassword.getText())) { 
                    // CHECK DATABASE WHETHER THE OLD PASSWORD MATCHES THE ENTERED OLD PASSWORD
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Entered current password is wrong!");
                    alert.showAndWait();
                } else if (!reNewPassword.getText().equals(newPassword.getText()) || !showReNewPassword.getText().equals(showNewPassword.getText())) { 
                    // CHECK WHETHER PASSWORDS MATCHES
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Passwords does not matches!");
                    alert.showAndWait();
                } else if (newPassword.getText().length() < 8) {
                    // CHECK THE LENGTH OF THE PASSWORDS
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Password must be atleast 8 characters");
                    alert.showAndWait();
                } else {
                    // PASSWORD CHANGE
                    alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Are you sure you want to update the password?");
                    Optional<ButtonType> optional = alert.showAndWait();
                    
                    if (optional.get().equals(ButtonType.OK)) {
                        statement = connect.createStatement();
                        statement.executeUpdate(sql);
                        
                        activeStatus();
                        
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Password changed!");
                        alert.showAndWait();
                        
                        showCurrentPassword.setText("");
                        showNewPassword.setText("");
                        showReNewPassword.setText("");
                        currentPassword.setText("");
                        newPassword.setText("");
                        reNewPassword.setText("");
                    }
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    

    // ================ FORM CONTROLLER ================
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runTime();
        displayUserData();
        activeStatus();
        
        todayCount();
        caseCount();
        caseInfoShowListDates();

        caseInfoShowListData();
        addCaseGenderList();

        diagnosticsShowListData();
    }

}
