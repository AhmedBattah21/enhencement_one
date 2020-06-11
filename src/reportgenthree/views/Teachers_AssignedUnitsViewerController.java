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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import reportgenthree.objects.Teachers_AssignedUnitsViewerClass;
import reportgenthree.views.Dialogs.Dialogs_functions;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Teachers_AssignedUnitsViewerController implements Initializable {

    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @FXML
    private Label lbl_title;

    @FXML
    private TableView<Teachers_AssignedUnitsViewerClass> table;

    @FXML
    private TableColumn col_number;

    @FXML
    private TableColumn col_name;

    @FXML
    private TableColumn col_unitname;

    @FXML
    private TableColumn col_classname;

    @FXML
    private TableColumn col_stdyear;

    @FXML
    private Button btn_save;

    @FXML
    private TextField txt_hint;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        col_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_classname.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        col_stdyear.setCellValueFactory(new PropertyValueFactory<>("std_year"));
        col_unitname.setCellValueFactory(new PropertyValueFactory<>("unitname"));

        lbl_schoolname.setText(SystemNameSetterController.getSname());
        lbl_boxnumber.setText(SystemNameSetterController.getbox());
        lbl_title.setText("Teachers & Their Respective Units");

        get_data();

    }

    public void get_data() {

        ObservableList<Teachers_AssignedUnitsViewerClass> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM AssignedUnits";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            int count = 0;
            while (rst.next()) {

                String UnitName = rst.getString("UnitName");
                String Teacher_IdNumber = rst.getString("Teacher_IdNumber");
                String Student_Year = rst.getString("Student_Year");
                String Student_Class = rst.getString("Student_Class");
                String name = get_tname(Teacher_IdNumber);
                count++;
                data.add(new Teachers_AssignedUnitsViewerClass(name, UnitName, Student_Year, Student_Class, count, Teacher_IdNumber));
            }

            table.setItems(data);
            conn.close();

        } catch (SQLException exc) {

        }

    }

    public void delete_assignment() {

        String t_id = table.getSelectionModel().getSelectedItem().getTeacher_idnumber();
        String query = "DELETE FROM AssignedUnits WHERE AssignedUnits.Teacher_IdNumber = '" + t_id + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            String result = Dialogs_functions.get_custom_confirm("Remove " + t_id + " And All Related Information?");


            if (result.equals("continue")) {
                Statement st = conn.createStatement();
                Statement st_two = conn.createStatement();

                int rows = st.executeUpdate(query);

                get_data();

            }

            conn.close();
        } catch (SQLException exc) {

            System.out.println(exc);
        }

    }

    public void get_data_serach() {

        table.getItems().clear();

        ObservableList<Teachers_AssignedUnitsViewerClass> data = FXCollections.observableArrayList();
        String hint = txt_hint.getText();

        String query = "SELECT * FROM AssignedUnits WHERE UnitName LIKE '%" + hint + "%' OR Teacher_IdNumber LIKE '%" + hint + "%' OR"
                + " Student_Class LIKE  '%" + hint + "%' OR Student_Year LIKE '%" + hint + "%'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            int count = 0;
            while (rst.next()) {

                String UnitName = rst.getString("UnitName");
                String Teacher_IdNumber = rst.getString("Teacher_IdNumber");
                String Student_Year = rst.getString("Student_Year");
                String Student_Class = rst.getString("Student_Class");
                String name = get_tname(Teacher_IdNumber);
                count++;
                data.add(new Teachers_AssignedUnitsViewerClass(name, UnitName, Student_Year, Student_Class, count,Teacher_IdNumber));
            }

            table.setItems(data);
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

    }

    public String get_tname(String idnumber) {

        String query = "SELECT Teacher_Name FROM Teacher WHERE Teacher_IdNumber = '" + idnumber + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        String name = "Null";
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {

                name = rst.getString("Teacher_Name");
            }
            conn.close();

        } catch (SQLException exc) {

        }
        return name;
    }

}
