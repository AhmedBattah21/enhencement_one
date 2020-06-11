/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;
import static reportgenthree.ReportGenThree.get_std_registrationcontroller;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentRegistration_steptwoController implements Initializable {

    @FXML
    private ComboBox cmb_course;

    @FXML
    private Label infor1;

    @FXML
    private TextField txt_regcode;

    @FXML
    private Label infor2;

    @FXML
    private ComboBox cmb_ategory;

    @FXML
    private Label infor3;
    @FXML
    private Label lbl_code;

    @FXML
    private Button btn_nexttwo;

    private BorderPane parent = new BorderPane();
    StackPane spane = null;

    String gender = "Male";

    StudentRegistration_stepfourController cc_stepfour = null;

    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
    FontAwesomeIconView tick = new FontAwesomeIconView(FontAwesomeIcon.CHECK);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setGlyphStyle("-fx-fill:red");
        error.setGlyphSize(15);

        infor.setGlyphStyle("-fx-fill:seagreen");
        infor.setGlyphSize(15);

        tick.setGlyphStyle("-fx-fill:white");
        tick.setGlyphSize(30);

        CoursesClass.populateComboBoxClasses(cmb_course, "CourseName", "CourseTable");
        CoursesClass.populateComboBoxClasses(cmb_ategory, "category_name", "categories");

        cmb_course.setOnAction(e -> {

            lbl_code.setText(getCourseName(cmb_course.getSelectionModel().getSelectedItem().toString()));

        });

        txt_regcode.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                txt_regcode.setText(oldValue);
            }
        });

        btn_nexttwo.setOnAction(e -> step_three());
    }

    public void step_three() {

        if (!cmb_course.getSelectionModel().isEmpty()) {

            infor1.setGraphic(infor);
            if (!txt_regcode.getText().isEmpty() && !StudentsClass.checkStudent(txt_regcode.getText())) {

                infor2.setGraphic(infor);
                if (!cmb_ategory.getSelectionModel().isEmpty()) {

                    try {
                        infor3.setGraphic(infor);

                        cc_stepfour.setStd_course(cmb_course.getSelectionModel().getSelectedItem().toString());
                        cc_stepfour.setStd_category(cmb_ategory.getSelectionModel().getSelectedItem().toString());
                        cc_stepfour.setStd_adm(txt_regcode.getText());
                        cc_stepfour.setStd_regcode(lbl_code.getText().toUpperCase());
                        cc_stepfour.setStd_complete_adm(lbl_code.getText().toUpperCase() + "/" + txt_regcode.getText().toUpperCase());

                        if (get_std_registrationcontroller().getSpane_stepthree() == null && get_std_registrationcontroller().getCc_stepthree() == null) {

                            FXMLLoader loader_stepthree = new FXMLLoader();

                            loader_stepthree.setLocation(StudentRegistration_steptwoController.class.getResource("StudentRegistration_stepthree.fxml"));
                            StackPane step_three = loader_stepthree.load();
                            StudentRegistration_stepthreeController cc_three = loader_stepthree.getController();
                            cc_three.setCc_stepfour(cc_stepfour);
                            cc_three.setGender(gender);
                            cc_three.setAdm_number(txt_regcode.getText());
                            cc_three.setSpane(spane);
                            cc_three.setParent(parent);
                            parent.setCenter(step_three);

                            ReportGenThree.get_std_registrationcontroller().getBtn_steptwo().setGraphic(tick);
                            ReportGenThree.get_std_registrationcontroller().setSpane_stepthree(step_three);
                            ReportGenThree.get_std_registrationcontroller().setCc_stepthree(cc_three);
                            
                        } else {

                            StackPane step_three = get_std_registrationcontroller().getSpane_stepthree();
                            StudentRegistration_stepthreeController cc_three = get_std_registrationcontroller().getCc_stepthree();
                            cc_three.setCc_stepfour(cc_stepfour);
                            cc_three.setGender(gender);
                            cc_three.setAdm_number(txt_regcode.getText());
                            cc_three.setSpane(spane);
                            cc_three.setParent(parent);
                            parent.setCenter(step_three);
                            
                            ReportGenThree.get_std_registrationcontroller().getBtn_steptwo().setGraphic(tick);

                        }

                    } catch (IOException ex) {

                        Logger.getLogger(StudentRegistration_steptwoController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    infor3.setGraphic(error);
                }
            } else {

                infor2.setGraphic(error);
            }

        } else {

            infor1.setGraphic(error);
        }

    }

    public StudentRegistration_stepfourController getCc_stepfour() {
        return cc_stepfour;
    }

    public void setCc_stepfour(StudentRegistration_stepfourController cc_stepfour) {
        this.cc_stepfour = cc_stepfour;
    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCourseName(String cname) {
        String cabr = "";

        String query = "SELECT CourseAbreviation FROM CourseTable WHERE CourseName = '" + cname + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                cabr = rst.getString("CourseAbreviation");
            }
            conn.close();
        } catch (SQLException exc) {

            System.out.println(exc);

        }

        return cabr;

    }

    public StackPane getSpane() {
        return spane;
    }

    public void setSpane(StackPane spane) {
        this.spane = spane;
    }

}
