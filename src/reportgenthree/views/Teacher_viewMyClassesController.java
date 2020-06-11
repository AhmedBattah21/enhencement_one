/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import reportgenthree.objects.User_profileOneControllerClass;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Teacher_viewMyClassesController implements Initializable {

    @FXML
    private TableView<User_profileOneControllerClass> table;

    @FXML
    private TableColumn col_unitname;

    @FXML
    private TableColumn col_classname;

    @FXML
    private TableColumn col_students;
    
     @FXML
    private Label lbl_infor;

    @FXML
    private Label lbl_initials;

    @FXML
    private Label lbl_code;

    @FXML
    private Label lbl_name;
    
    String id = "";
    
    String full_name;
    String initials;
    String user_code;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        col_unitname.setCellValueFactory(new PropertyValueFactory<>("unit_name"));
        col_classname.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        col_students.setCellValueFactory(new PropertyValueFactory<>("syear"));
    } 
    
    
    
     public String get_units(String id) {
        
        ObservableList<User_profileOneControllerClass> list = FXCollections.observableArrayList();
        String task = "null";
        String query = "SELECT * FROM AssignedUnits WHERE Teacher_IdNumber = '" + id + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {
            
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            
            while (rst.next()) {
                
                String UnitName = rst.getString("UnitName");
                String Student_Year = rst.getString("Student_Year");
                String Student_Class = rst.getString("Student_Class");
                
                list.add(new User_profileOneControllerClass(UnitName, Student_Class, Student_Year));
            }
            table.setItems(list);
            conn.close();
        } catch (SQLException exc) {
            
            System.out.println(exc);
        }
        return task;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public Label getLbl_initials() {
        return lbl_initials;
    }

    public void setLbl_initials(Label lbl_initials) {
        this.lbl_initials = lbl_initials;
    }

    public Label getLbl_code() {
        return lbl_code;
    }

    public void setLbl_code(Label lbl_code) {
        this.lbl_code = lbl_code;
    }

    public Label getLbl_name() {
        return lbl_name;
    }

    public void setLbl_name(Label lbl_name) {
        this.lbl_name = lbl_name;
    }
     
     
    
}
