/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package animalhealthtracker;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/**
 *
 * @author Shakila Kamalasena
 */
public class AdminMainFromController implements Initializable {

    @FXML
    private Circle active_icon;

    @FXML
    private Label active_label;
    
    @FXML
    private Label hiddenPwd;

    @FXML
    private Label inactiveLabel;

    @FXML
    private Circle inactive_icon;
    
    @FXML
    private TextField case_delete;

    @FXML
    private TextField case_search_top;

    @FXML
    private Button casesDelete_btn;
    
    @FXML
    private TableColumn<caseInfoData, String> case_caseId_col;

    @FXML
    private TableColumn<caseInfoData, String> case_date_col;

    @FXML
    private TableColumn<caseInfoData, String> case_follow_col;

    @FXML
    private TableColumn<caseInfoData, String> case_gender_col;

    @FXML
    private TableColumn<caseInfoData, String> case_species_col;

    @FXML
    private TableView<caseInfoData> cases_tableView;
    
    @FXML
    private BarChart<?, ?> dashboardChart;
    
    @FXML
    private Label dashboard_vetCases;

    @FXML
    private Label dashboard_vetCount;

    @FXML
    private Label dashboard_vetInactiveCases;
    
    @FXML
    private AnchorPane case_form;

    @FXML
    private Button cases_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AnchorPane dashboard_form;

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
    private Label userId_left;

    @FXML
    private Button vet_addBtn;

    @FXML
    private Button vet_clearBtn;

    @FXML
    private TextField vet_date;

    @FXML
    private TableColumn<vetData, String> vet_date_col;

    @FXML
    private Button vet_deleteBtn;

    @FXML
    private TextField vet_fname;

    @FXML
    private TableColumn<vetData, String> vet_fname_col;

    @FXML
    private ComboBox<?> vet_gender;

    @FXML
    private TableColumn<vetData, String> vet_gender_col;

    @FXML
    private TableColumn<vetData, String> vet_id_col;
    
    @FXML
    private TableColumn<vetData, String> vet_password_col;

    @FXML
    private TextField vet_lname;

    @FXML
    private TableColumn<vetData, String> vet_lname_col;

    @FXML
    private TextField vet_phone;

    @FXML
    private TableColumn<vetData, String> vet_phone_col;

    @FXML
    private ComboBox<?> vet_specialization;

    @FXML
    private TableColumn<vetData, String> vet_specialization_col;

    @FXML
    private TableView<vetData> vet_tableView;

    @FXML
    private Button vet_updateBtn;

    @FXML
    private TextField vet_vetId;

    @FXML
    private Button veterinarian_btn;

    @FXML
    private AnchorPane veterinarian_form;

    private Connection connect;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    Alert alert;
    
    
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
            veterinarian_form.setVisible(false);
            case_form.setVisible(false);
            
            dashboard_btn.setStyle("-fx-background-color: #4a5b50");
            veterinarian_btn.setStyle("-fx-background-color: transparent");
            cases_btn.setStyle("-fx-background-color: transparent");
            
            formName_top.setText("Dashboard");
            
            dashboardVeterinarianCount();
            dashboardInactiveVetCount();
            dashboardAllCaseCount();
            dashboardChart();
            
            
        } else if (event.getSource() == veterinarian_btn) {
            
            dashboard_form.setVisible(false);
            veterinarian_form.setVisible(true);
            case_form.setVisible(false);
            
            dashboard_btn.setStyle("-fx-background-color: transparent");
            veterinarian_btn.setStyle("-fx-background-color: #4a5b50");
            cases_btn.setStyle("-fx-background-color: transparent");
            
            formName_top.setText("Veterinarians List");
            
            vetGenderList();
            vetSpecializationList();
            clearStatus();
            
        } else if (event.getSource() == cases_btn) {
            dashboard_form.setVisible(false);
            veterinarian_form.setVisible(false);
            case_form.setVisible(true);
            
            dashboard_btn.setStyle("-fx-background-color: transparent");
            veterinarian_btn.setStyle("-fx-background-color: transparent");
            cases_btn.setStyle("-fx-background-color: #4a5b50");
            
            formName_top.setText("All Cases");
            
            caseInfoShowListData();
        }
    }
    
    // DISPLAY USER DATA IN NAVIGATION BARS
    public void displayUserData() {
        userId_left.setText(getData.username);
        name_top.setText(getData.username);
        
        formName_top.setText("Dashboard");
    }
    
    // LOGOUT BUTTON METHOD
    public void logout() {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> optional = alert.showAndWait();
        
        try {
            if (optional.get().equals(ButtonType.OK)) {
                logout_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Stage stage = new Stage();

                stage.setTitle("Animal Health Tracker (Admin Portal)");

                stage.setMinWidth(700);
                stage.setMinHeight(500);

                stage.setScene(new Scene(root));
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    // DASHBOARD METHODS
    public void dashboardVeterinarianCount() {
        
        String sql = "SELECT COUNT(username) FROM vet";
        
        connect = Database.connectDB();
        int countData = 0;
        
        try {
            
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {                
                countData = resultSet.getInt("COUNT(username)");
            }
            
            dashboard_vetCount.setText(String.valueOf(countData));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void dashboardInactiveVetCount() {
        
        String sql = "SELECT COUNT(username) FROM vet WHERE password = '12345678'";
        
        connect = Database.connectDB();
        int countData = 0;
        
        try {
            
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {                
                countData = resultSet.getInt("COUNT(username)");
            }
            
            dashboard_vetInactiveCases.setText(String.valueOf(countData));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    public void dashboardAllCaseCount() {
        String sql = "SELECT COUNT(caseId) FROM cases";
        
        connect = Database.connectDB();
        int count = 0;
        
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            
            while (resultSet.next()) {                
                count = resultSet.getInt("COUNT(caseId)");
            }
            dashboard_vetCases.setText(String.valueOf(count));
            
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void dashboardChart() {
        
        dashboardChart.getData().clear();
        
        String sql = "SELECT date, COUNT(caseId) FROM diagnostics GROUP BY date ORDER BY TIMESTAMP(date) DESC LIMIT 7";
        connect = Database.connectDB();
        
        try {
            
            XYChart.Series chart = new XYChart.Series();
            
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {                
                chart.getData().add(new XYChart.Data(resultSet.getString(1), resultSet.getInt(2)));
            }
            
            dashboardChart.getData().add(chart);
            connect.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    // VETERINARIAN TAB CONTROLLERS
    
    //public void statusClear() 
    
    public ObservableList<vetData> vetListData() {
        
        ObservableList<vetData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM vet";
        
        connect = Database.connectDB();
        if (connect == null) {
            System.out.println("Database connection failed.");
        }
        
        try {
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            vetData vetD;
            
            while (resultSet.next()) {                
                vetD = new vetData(resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("gender"), 
                        resultSet.getString("phoneNo"), 
                        resultSet.getString("specialization"), 
                        resultSet.getString("username"), 
                        resultSet.getDate("date"));
                listData.add(vetD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return listData;
    }
    
    private ObservableList<vetData> vetList;
    public void vetShowListData() {
        
        try {
            vetList = vetListData();
            
            vet_id_col.setCellValueFactory(new PropertyValueFactory<>("username"));
            vet_fname_col.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            vet_lname_col.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            vet_gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
            vet_phone_col.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
            vet_specialization_col.setCellValueFactory(new PropertyValueFactory<>("specialization"));
            vet_date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
                        
            vet_tableView.setItems(vetList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void vetSelect() {
        vetData vetD = vet_tableView.getSelectionModel().getSelectedItem();
        int num = vet_tableView.getSelectionModel().getFocusedIndex();
        
        if ((num - 1) < -1) {
            return;
        }
        
        vet_vetId.setText(vetD.getUsername());
        vet_fname.setText(vetD.getFirstName());
        vet_lname.setText(vetD.getLastName());
        vet_phone.setText(vetD.getPhoneNo());
        vet_date.setText(String.valueOf(vetD.getDate()));
        //hiddenPwd.setText(vetD.getPassword());
        
        connect = Database.connectDB();
        String sql = "SELECT password FROM vet WHERE username = '" + vet_vetId.getText() + "'";
        
        try {
            preparedStatement = connect.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {                
                if (resultSet.getString("password").equals("12345678")) {
                    inactiveLabel.setVisible(true);
                    inactive_icon.setVisible(true);
                    active_label.setVisible(false);
                    active_icon.setVisible(false);
                } else {
                    inactiveLabel.setVisible(false);
                    inactive_icon.setVisible(false);
                    active_label.setVisible(true);
                    active_icon.setVisible(true);
                }
            }
            
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void clearStatus() {
        inactiveLabel.setVisible(false);
        inactive_icon.setVisible(false);
        active_label.setVisible(false);
        active_icon.setVisible(false);
    }
    
    private final String[] listGender = {"Male", "Female", "Others"};
    @FXML
    public void vetGenderList() {
        List<String> listG = new ArrayList<>();

        for(String data: listGender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        vet_gender.setItems(listData);
    }
    
    private final String[] listSpecialization = {"Large/Small", "Avian & Exotic", "Wildlife", "Surgery", "Pathology", "Others"};
    @FXML
    public void vetSpecializationList() {
        List<String> listS = new ArrayList<>();

        for(String data: listSpecialization) {
            listS.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listS);
        vet_specialization.setItems(listData);
    }
    
    
    public void vetClear() {
        vet_vetId.setText("");
        vet_fname.setText("");
        vet_lname.setText("");
        vet_gender.getSelectionModel().clearSelection();
        vet_phone.setText("");
        vet_specialization.getSelectionModel().clearSelection();
        vet_date.setText("");
    }
    
    public void vetDelete() {
        
        String sql = "DELETE FROM vet WHERE username = '"
                + vet_vetId.getText() + "'";
        
        connect = Database.connectDB();
        
        try {
            
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Vet ID: " + vet_vetId.getText() + "?");
            Optional<ButtonType> optional = alert.showAndWait();
            
            if (optional.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);
                
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();
                
                vetShowListData();
                vetClear();
            }
                   
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    public void vetUpdate() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "UPDATE vet SET firstName = '"
                + vet_fname.getText() + "', lastName = '"
                + vet_lname.getText() + "', gender = '"
                + vet_gender.getSelectionModel().getSelectedItem() + "', phoneNo = '"
                + vet_phone.getText() + "', specialization = '"
                + vet_specialization.getSelectionModel().getSelectedItem() + "' WHERE username = '"
                + vet_vetId.getText() + "'";
        
        connect = Database.connectDB();
        
        try {
            
            if (vet_fname.getText().isEmpty() 
                    || vet_lname.getText().isEmpty()
                    || vet_gender.getSelectionModel().getSelectedItem() == null 
                    || vet_phone.getText().isEmpty() 
                    || vet_specialization.getSelectionModel().getSelectedItem() == null ) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the blank fields");
                alert.showAndWait();    
            } else {
                
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Vet ID: " + vet_vetId.getText() + "?");
                Optional<ButtonType> optional = alert.showAndWait();
                
                if (optional.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    vetShowListData();
                    vetClear();
                }
                
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void vetAdd() {
        try {
            
            Date date = new Date();
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            
            String insertData = "INSERT INTO vet(firstName, lastName, gender, phoneNo, specialization, username, password, date) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            
            preparedStatement = connect.prepareStatement(insertData);
            preparedStatement.setString(1, vet_fname.getText());
            preparedStatement.setString(2, vet_lname.getText());
            preparedStatement.setString(3, (String) vet_gender.getSelectionModel().getSelectedItem());
            preparedStatement.setString(4, vet_phone.getText());
            preparedStatement.setString(5, (String) vet_specialization.getSelectionModel().getSelectedItem());
            preparedStatement.setString(6, vet_vetId.getText());
            preparedStatement.setString(7, "12345678");
            preparedStatement.setString(8, String.valueOf(sqlDate));
            
            preparedStatement.executeUpdate();
            
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Successfully Added");
            alert.showAndWait();
            
            vetShowListData();
            vetClear();
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    // AVAILABLE CASES TAB CONTROLLERS
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
            
            connect.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;

    }

    private ObservableList<caseInfoData> caseInfoList;

    public void caseInfoShowListData() {

        try {

            caseInfoList = caseInfoListData();

            case_caseId_col.setCellValueFactory(new PropertyValueFactory<>("caseId"));
            case_species_col.setCellValueFactory(new PropertyValueFactory<>("species"));
            case_date_col.setCellValueFactory(new PropertyValueFactory<>("date"));
            case_gender_col.setCellValueFactory(new PropertyValueFactory<>("gender"));
            case_follow_col.setCellValueFactory(new PropertyValueFactory<>("followDate"));

            cases_tableView.setItems(caseInfoList);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void caseCaseSearch() {

        FilteredList<caseInfoData> filter = new FilteredList<>(caseInfoList, e -> true);

        case_delete.textProperty().addListener((Observable, oldValue, newValue) -> {

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

        sortedList.comparatorProperty().bind(cases_tableView.comparatorProperty());
        cases_tableView.setItems(sortedList);

    }
    
    public void caseCaseDelete() {
        
        String sql = "DELETE FROM cases WHERE caseId = '" + case_delete.getText() + "'";
        String sql1 = "DELETE FROM case_info WHERE caseId = '" + case_delete.getText() + "'";
        String sql2 = "DELETE FROM diagnostics WHERE caseId = '" + case_delete.getText() + "'";
        connect = Database.connectDB();
        
        try {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Case ID: " + case_delete.getText() + "?");
            Optional<ButtonType> optional = alert.showAndWait();
            
            if (optional.get().equals(ButtonType.OK)) {
                statement = connect.createStatement();
                statement.executeUpdate(sql);
                
                Statement st = connect.createStatement();
                st.executeUpdate(sql1);
                
                Statement st1 = connect.createStatement();
                st1.executeUpdate(sql2);
                
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Deleted!");
                alert.showAndWait();

                caseInfoShowListData();
                case_delete.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        runTime();
        vetGenderList();
        vetSpecializationList();
        displayUserData();
        dashboardVeterinarianCount();
        dashboardInactiveVetCount();
        dashboardAllCaseCount();
        clearStatus();
        dashboardChart();
        
        vetShowListData();
        
        caseInfoShowListData();
    }

}
