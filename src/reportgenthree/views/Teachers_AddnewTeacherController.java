/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Teachers_AddnewTeacherController implements Initializable {

    @FXML
    private TextField txt_name;

    @FXML
    private Label inforone;

    @FXML
    private TextField txt_number;

    @FXML
    private Label infor2;

    @FXML
    private ComboBox cmb_department;

    @FXML
    private Label inforone3;

    @FXML
    private TextField txt_initials;

    @FXML
    private Label inforone4;

    @FXML
    private ComboBox cmb_task;

    @FXML
    private Label inforone5;

    @FXML
    private Button btn_save;
    
     private StackPane   parent_stackpane;

    Teachers_AvailableteachersController cc = null;
    TeachersPanelController tp_cc = null;

    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setGlyphStyle("-fx-fill:red");
        error.setGlyphSize(15);

        infor.setGlyphStyle("-fx-fill:seagreen");
        infor.setGlyphSize(15);

        AppFuctions.CoursesClass.populateComboBoxClasses(cmb_department, "CourseName", "CourseTable");

        AppFuctions.CoursesClass.populateComboBoxClasses(cmb_task, "TaskName", "TeacherTasks");
        cmb_task.setValue("No Task");

    }

    public void add_teacher() {

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;

        if (!txt_name.getText().isEmpty()) {

            String tname = txt_name.getText();
            inforone.setGraphic(null);

            if (!txt_number.getText().isEmpty()) {
                String number = txt_number.getText();
                infor2.setGraphic(null);

                if (!cmb_department.getSelectionModel().isEmpty()) {
                    String dept = cmb_department.getSelectionModel().getSelectedItem().toString();
                    inforone3.setGraphic(null);

                    if (!txt_initials.getText().isEmpty()) {
                        String initials = txt_initials.getText();
                        inforone4.setGraphic(null);

                        if (!cmb_task.getSelectionModel().isEmpty()) {
                            String task = cmb_task.getSelectionModel().getSelectedItem().toString();
                            inforone5.setGraphic(null);

                            String query = "INSERT INTO Teacher VALUES ('" + tname + "','" + number + "','" + dept + "','" + initials + "','" + task + "',null,'12345','defaultf.png','Active')";

                            Connection conn = sqlDataBaseConnection.sqliteconnect();

                            try {

                                if (!check_teacher("Teacher_Name", tname)) {
                                    inforone.setGraphic(null);
                                    if (!check_teacher("Teacher_IdNumber", number)) {

                                        infor2.setGraphic(null);
                                        if (!check_teacher("Teacher_Initials", initials)) {
                                            inforone4.setGraphic(null);

                                            if (get_taskproperty(task).equals("uni")) {

                                                if (!check_teacher("Teacher_Task", task)) {

                                                    inforone5.setGraphic(null);
                                                    Statement st = conn.createStatement();
                                                    int result = st.executeUpdate(query);

                                                    if (result >= 1) {

                                                        reportgenthree.views.Dialogs.Dialogs_functions.Data_saveddialog(parent_stackpane, "Great!!! Teacher Registered!!!");
                                                        
                                                        String desc = "A new Teacher Registered  " + number + " Name " + tname + " \n "
                                                                + "Department " + dept + " Initials " + initials + " Task " + task;
                                                        sqlDataBaseConnection.add_activity("Activity:" + n, "Registration",
                                                                desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());
                                                        
                                                        tp_cc.open_addteachers();
                                                        

                                                    } else {

                                                        btn_save.setGraphic(error);
                                                    }

                                                } else {

                                                    inforone5.setGraphic(error);

                                                }

                                            } else {

                                                inforone5.setGraphic(null);
                                                Statement st = conn.createStatement();
                                                int result = st.executeUpdate(query);

                                                if (result >= 1) {

                                                    reportgenthree.views.Dialogs.Dialogs_functions.Data_saveddialog(parent_stackpane, "Great!!! Teacher Registered!!!");

                                                    String desc = "A new Teacher Registered  " + number + " Name " + tname + " \n "
                                                            + "Department " + dept + " Initials " + initials + " Task " + task;
                                                    sqlDataBaseConnection.add_activity("Activity:" + n, "Registration",
                                                            desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());
                                                    
                                                     tp_cc.open_addteachers();

                                                } else {

                                                    btn_save.setGraphic(error);
                                                }

                                            }

                                        } else {

                                            inforone4.setGraphic(error);
                                        }

                                    } else {

                                        infor2.setGraphic(error);
                                    }

                                } else {

                                    inforone.setGraphic(error);

                                }
                                conn.close();

                            } catch (SQLException exc) {

                                System.out.println("Error Erick" + exc);
                            }

                        } else {

                            inforone5.setGraphic(error);

                        }

                    } else {

                        inforone4.setGraphic(error);

                    }

                } else {

                    inforone3.setGraphic(error);

                }

            } else {

                infor2.setGraphic(error);

            }

        } else {

            inforone.setGraphic(error);

        }

    }

    public void edit_teacher(String t_id) {

        try {

            DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
            Random rand = new Random();

            int n = rand.nextInt(50000) + 1;

            Connection conn = sqlDataBaseConnection.sqliteconnect();

            if (!txt_name.getText().isEmpty()) {

                error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
                infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
                error.setGlyphStyle("-fx-fill:red");
                error.setGlyphSize(15);
                infor.setGlyphStyle("-fx-fill:seagreen");
                infor.setGlyphSize(15);

                String tname = txt_name.getText();
                inforone.setGraphic(null);

                String query = "UPDATE Teacher SET Teacher_Name ='" + tname + "' WHERE Teacher_IdNumber = '" + t_id + "'";

                Statement st = conn.createStatement();
                int result = st.executeUpdate(query);

                if (result >= 1) {

                    inforone.setGraphic(infor);
                    String desc = "Teachers Infor Edited  Name Changed To.. " + tname + " (" + t_id + ")";
                    sqlDataBaseConnection.add_activity("Activity:" + n, "Instractor Information",
                            desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                    cc.get_data();

                }

            } else {

                inforone.setGraphic(error);
            }

            if (!txt_number.getText().isEmpty()) {

                error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
                infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
                error.setGlyphStyle("-fx-fill:red");
                error.setGlyphSize(15);
                infor.setGlyphStyle("-fx-fill:seagreen");
                infor.setGlyphSize(15);

                String number = txt_number.getText();
                infor2.setGraphic(null);

                if (!check_teacher("Teacher_IdNumber", number)) {

                    String query = "UPDATE Teacher SET Teacher_IdNumber ='" + number + "' WHERE Teacher_IdNumber = '" + t_id + "'";

                    Statement st = conn.createStatement();
                    int result = st.executeUpdate(query);

                    if (result >= 1) {

                        infor2.setGraphic(infor);
                        String desc = "Teachers Infor Edited  Code Changed To.. " + number + " (" + t_id + ")";
                        sqlDataBaseConnection.add_activity("Activity:" + n, "Instractor Information",
                                desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                        cc.get_data();

                    }

                } else {

                    infor2.setGraphic(error);
                }

            } else {

                infor2.setGraphic(error);

            }

            //--------------------chage department-------------------------
            if (!cmb_department.getSelectionModel().isEmpty()) {
                error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
                infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
                error.setGlyphStyle("-fx-fill:red");
                error.setGlyphSize(15);
                infor.setGlyphStyle("-fx-fill:seagreen");
                infor.setGlyphSize(15);

                String dept = cmb_department.getSelectionModel().getSelectedItem().toString();
                inforone3.setGraphic(null);

                String query_dept = "UPDATE Teacher SET Teacher_Department ='" + dept + "' WHERE Teacher_IdNumber = '" + t_id + "'";

                Statement st_dept = conn.createStatement();
                int result_dept = st_dept.executeUpdate(query_dept);

                if (result_dept >= 1) {

                    inforone3.setGraphic(infor);
                    String desc = "Teachers Infor Edited  Department Changed To.. " + dept + " (" + t_id + ")";
                    sqlDataBaseConnection.add_activity("Activity:" + n, "Instractor Information",
                            desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                    cc.get_data();

                }

            } else {

                inforone3.setGraphic(error);
            }

            //--------------------end -------------------------
            /*-------------------change initials----------------------------------------------*/
            if (!txt_initials.getText().isEmpty()) {

                error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
                infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
                error.setGlyphStyle("-fx-fill:red");
                error.setGlyphSize(15);
                infor.setGlyphStyle("-fx-fill:seagreen");
                infor.setGlyphSize(15);

                String initials = txt_initials.getText();
                inforone4.setGraphic(null);

                if (!check_teacher("Teacher_Initials", initials)) {

                    String query = "UPDATE Teacher SET Teacher_Initials ='" + initials + "' WHERE Teacher_IdNumber = '" + t_id + "'";

                    Statement st = conn.createStatement();
                    int result = st.executeUpdate(query);

                    if (result >= 1) {

                        inforone4.setGraphic(infor);
                        String desc = "Teachers Infor Edited  Initials Changed To.. " + initials + " (" + t_id + ")";
                        sqlDataBaseConnection.add_activity("Activity:" + n, "Instractor Information",
                                desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                        cc.get_data();

                    }

                } else {

                    inforone4.setGraphic(error);
                }

            } else {

                inforone4.setGraphic(error);

            }

            /*-------------------end change initials----------------------------------------------*/
 /*...............................change tasks......................................................................*/
            if (!cmb_task.getSelectionModel().isEmpty()) {

                error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
                infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
                error.setGlyphStyle("-fx-fill:red");
                error.setGlyphSize(15);
                infor.setGlyphStyle("-fx-fill:seagreen");
                infor.setGlyphSize(15);

                String task = cmb_task.getSelectionModel().getSelectedItem().toString();
                inforone5.setGraphic(null);
                String query = "UPDATE Teacher SET Teacher_Task ='" + task + "' WHERE Teacher_IdNumber = '" + t_id + "' ";

                if (get_taskproperty(task).equals("com")) {

                    Statement st = conn.createStatement();
                    int result = st.executeUpdate(query);

                    if (result >= 1) {

                        inforone5.setGraphic(infor);
                        String desc = "Teachers Infor Edited  Task Changed To.. " + task + " (" + t_id + ")";
                        sqlDataBaseConnection.add_activity("Activity:" + n, "Instractor Information",
                                desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                        cc.get_data();

                    }

                } else if (!check_teacher("Teacher_Task", task)) {

                    Statement st = conn.createStatement();
                    int result = st.executeUpdate(query);

                    if (result >= 1) {

                        inforone5.setGraphic(infor);
                        String desc = "Teachers Infor Edited  Task Changed To.. " + task + " (" + t_id + ")";
                        sqlDataBaseConnection.add_activity("Activity:" + n, "Instractor Information",
                                desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                        cc.get_data();

                    }

                } else {

                    inforone5.setGraphic(error);
                }

            } else {

                inforone5.setGraphic(error);

            }

            conn.close();
        } catch (SQLException ex) {

            System.out.println("Erick  " + ex);
        }

    }

    public void set_btnsave_toedit(String t_id) {

        btn_save.setOnAction(e -> edit_teacher(t_id));
    }

    public boolean check_teacher(String teacher_field, String teacher_data) {

        boolean results = false;

        String query = "SELECT * FROM Teacher WHERE " + teacher_field + " = '" + teacher_data + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            results = rst.next();

            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error Erick One" + exc);
        }

        return results;
    }

    public String get_taskproperty(String taskname) {

        String property = "";

        String query = "SELECT Property FROM TeacherTasks WHERE TaskName = '" + taskname + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                property = rst.getString("Property");

            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error Erick One" + exc);
        }

        return property;
    }

    public TextField getTxt_name() {
        return txt_name;
    }

    public void setTxt_name(TextField txt_name) {
        this.txt_name = txt_name;
    }

    public TextField getTxt_number() {
        return txt_number;
    }

    public void setTxt_number(TextField txt_number) {
        this.txt_number = txt_number;
    }

    public ComboBox getCmb_department() {
        return cmb_department;
    }

    public void setCmb_department(ComboBox cmb_department) {
        this.cmb_department = cmb_department;
    }

    public TextField getTxt_initials() {
        return txt_initials;
    }

    public void setTxt_initials(TextField txt_initials) {
        this.txt_initials = txt_initials;
    }

    public ComboBox getCmb_task() {
        return cmb_task;
    }

    public void setCmb_task(ComboBox cmb_task) {
        this.cmb_task = cmb_task;
    }

    public Button getBtn_save() {
        return btn_save;
    }

    public void setBtn_save(Button btn_save) {
        this.btn_save = btn_save;
    }

    public Teachers_AvailableteachersController getCc() {
        return cc;
    }

    public void setCc(Teachers_AvailableteachersController cc) {
        this.cc = cc;
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
