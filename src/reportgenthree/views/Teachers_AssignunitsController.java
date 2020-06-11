/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.Functions;
import Connection.sqlDataBaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Teachers_AssignunitsController implements Initializable {

    @FXML
    private ComboBox cmb_tname;

    @FXML
    private ComboBox cmb_unitname;

    @FXML
    private ComboBox cmb_coursename;

    @FXML
    private ComboBox cmb_year;

    @FXML
    private Button btn_save;

    @FXML
    private Label infor1;

    @FXML
    private Label infor2;

    @FXML
    private Label infor3;

    @FXML
    private Label infor4;

    @FXML
    private Label lbl_details;

    private StackPane parent_stackpane;

    TeachersPanelController tp_cc = null;

    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        populate_cmb_teachers();

        populate_cmb_teachers();
        populateComboBoxClasses(cmb_unitname, "UnitName", "UnitsTable");

        ObservableList<String> study_year = FXCollections.observableArrayList();
        study_year.addAll("First Year", "Second Year");
        cmb_year.setItems(study_year);
        cmb_year.setValue("First Year");

        error.setGlyphStyle("-fx-fill:red");
        error.setGlyphSize(15);

        infor.setGlyphStyle("-fx-fill:seagreen");
        infor.setGlyphSize(15);

        cmb_unitname.setOnAction(e -> {

            String unit_name = cmb_unitname.getSelectionModel().getSelectedItem().toString();

            if (Functions.get_unitproperty(unit_name).equals("uni")) {

                cmb_coursename.getItems().clear();
                populateComboBoxClasses(cmb_coursename, "CourseName", "CourseTable");

            } else {

                cmb_coursename.getItems().clear();
                cmb_coursename.getItems().add("All Classes");

            }

        });
    }

    @FXML
    void assign_unit(ActionEvent event) {

        if (!cmb_tname.getSelectionModel().isEmpty()) {

            String[] tnames = cmb_tname.getSelectionModel().getSelectedItem().toString().split(" ");
            String t_name = tnames[0];
            infor1.setGraphic(null);

            if (!cmb_unitname.getSelectionModel().isEmpty()) {
                String unitname = cmb_unitname.getSelectionModel().getSelectedItem().toString();
                infor2.setGraphic(null);

                if (!cmb_coursename.getSelectionModel().isEmpty()) {
                    infor3.setGraphic(null);

                    String coursename = cmb_coursename.getSelectionModel().getSelectedItem().toString();

                    if (!cmb_year.getSelectionModel().isEmpty()) {
                        infor4.setGraphic(null);

                        String year = cmb_year.getSelectionModel().getSelectedItem().toString();

                        if (!check_unit(unitname, year, coursename)) {

                            String initials = get_tinitials(t_name);

                            String query = "INSERT INTO AssignedUnits VALUES ('" + unitname + "','" + t_name + "','" + year + "','" + coursename + "','" + initials + "')";
                            Connection conn = sqlDataBaseConnection.sqliteconnect();
                            try {

                                Statement st = conn.createStatement();
                                int resut = st.executeUpdate(query);
                                if (resut >= 1) {

                                    reportgenthree.views.Dialogs.Dialogs_functions.Data_saveddialog(parent_stackpane, "Great!!! Information Saved..!!!");
                                    tp_cc.open_Assignteacher();

                                } else {

                                    btn_save.setGraphic(error);
                                    lbl_details.setText("Failed...");
                                }
                                conn.close();
                            } catch (SQLException exc) {

                                System.out.println("Error " + exc);
                            }
                        } else {

                            infor2.setGraphic(error);
                            lbl_details.setText("Unit Alread Taken...");
                        }

                    } else {

                        infor4.setGraphic(error);
                        lbl_details.setText("Null Field");
                    }

                } else {

                    infor3.setGraphic(error);
                    lbl_details.setText("Null Field");
                }

            } else {

                infor2.setGraphic(error);
                lbl_details.setText("Null Field");
            }

        } else {

            infor1.setGraphic(error);
            lbl_details.setText("Null Field");
        }

    }

    public void populate_cmb_teachers() {

        ObservableList<String> teacher = FXCollections.observableArrayList();
        String query = "SELECT * FROM Teacher";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                String teacher_name = rst.getString("Teacher_IdNumber") + " " + rst.getString("Teacher_Name");
                teacher.add(teacher_name);
            }
            cmb_tname.setItems(teacher);
            conn.close();
        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

    }

    public static void populateComboBoxClasses(ComboBox box, String column, String table) {

        String query = "SELECT " + column + " FROM " + table + "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<String> rows = FXCollections.observableArrayList();

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                box.getItems().add(rst.getString(column));

            }

            conn.close();
        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

    }

    public Boolean check_unit(String unitname, String std_year, String std_class) {

        boolean result = false;
        String query = "SELECT * FROM AssignedUnits WHERE UnitName = '" + unitname + "' AND Student_Year = '" + std_year + "' AND Student_Class = '" + std_class + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {

                result = true;
            }
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);

        }
        return result;
    }

    public String get_tinitials(String idnumber) {

        String query = "SELECT Teacher_Initials FROM Teacher WHERE Teacher_IdNumber = '" + idnumber + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        String name = "Null";
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {

                name = rst.getString("Teacher_Initials");
            }
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }
        return name;
    }

    public StackPane getParent_stackpane() {
        return parent_stackpane;
    }

    public void setParent_stackpane(StackPane parent_stackpane) {
        this.parent_stackpane = parent_stackpane;
    }

    public TeachersPanelController getTp_cc() {
        return tp_cc;
    }

    public void setTp_cc(TeachersPanelController tp_cc) {
        this.tp_cc = tp_cc;
    }
    
    

}
