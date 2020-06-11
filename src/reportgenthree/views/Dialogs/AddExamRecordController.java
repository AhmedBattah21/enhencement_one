/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views.Dialogs;

import AppFuctions.CoursesClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class AddExamRecordController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private ComboBox<?> cmb_unit;

    @FXML
    private TextField txt_score;

    @FXML
    private Label lbl_icon;

    @FXML
    private JFXButton btn_add;

    @FXML
    private Label lbl_result;

    @FXML
    private StackPane mypane;

    String term;
    String exam_year;
    String std_year;
    String exam_name;
    String unit_name;
    String adm_number;
    String course;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CoursesClass.populateComboBoxClasses(cmb_unit, "UnitName", "UnitsTable");
    }

    @FXML
    void save_data(ActionEvent event) {

        if (!txt_score.getText().isEmpty() && !cmb_unit.getSelectionModel().isEmpty()) {

            int score = Integer.parseInt(txt_score.getText());

            unit_name = cmb_unit.getSelectionModel().getSelectedItem().toString();

            if (score <= getMaxScore(exam_name)) {

                if (!checkExamRecord()) {

                    String query = "INSERT INTO exam_2017 VALUES ('" + adm_number + "','" + exam_name + "',"
                            + "'" + term + "','" + score + "','" + course + "',null,'" + unit_name + "','" + exam_year + "','" + std_year + "')";

                    Connection conn = sqlDataBaseConnection.sqliteconnect();

                    System.out.println(" Courses " + course);
                    try {

                        Statement st = conn.createStatement();
                        st.executeUpdate(query);

                        conn.close();

                        openDialog("Student Score Has Been Saved",
                                "Thank You Click View To Check The Record ", adm_number);

                    } catch (SQLException exc) {
                        System.out.println("" + exc);
                    }

                } else {

                    openDialog("We cant Save The Score Due To The Following..",
                            "Score Alread Exist, You Can Delete or Edit..", adm_number);

                }

            } else {

                openDialog("We cant Save The Score Due To The Following..",
                        "Score Is Invalid.", adm_number);
            }

        } else {

            openDialog("Select Unit and Enter Its Score..",
                    "Score Is Invalid.", adm_number);

        }

    }

    public void checkScore() {

        try {
            int score = Integer.parseInt(txt_score.getText().trim());

            if (score >= 0 && score <= 100) {

                btn_add.setDisable(false);
                lbl_icon.setVisible(false);
            } else {

                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                lbl_icon.setVisible(true);
                lbl_icon.setGraphic(icon);
                lbl_result.setText("Invalid Mark");
                btn_add.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
            lbl_icon.setVisible(true);
            lbl_icon.setGraphic(icon);
            lbl_result.setText("Invalid Mark");
            btn_add.setDisable(true);

        }

    }

    public int getMaxScore(String examname) {

        String query = "SELECT MaxScore FROM ExamNames WHERE ExaminationName = '" + examname + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        int maxscore = 0;
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                maxscore = rst.getInt("MaxScore");
            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);

        }

        return maxscore;

    }

    public Boolean checkExamRecord() {

        boolean result = false;

        String examunit = cmb_unit.getSelectionModel().getSelectedItem().toString();

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + adm_number + "'"
                + "AND Exam_Name = '" + exam_name + "' AND Exam_Term = '" + term + "' AND"
                + " Unit_Name = '" + examunit + "' AND Syear = '" + std_year + "' AND Year = '" + exam_year + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();
        } catch (SQLException exc) {

            System.out.println("" + exc);

        }
        return result;

    }

    public void openDialog(String title, String message, String adm) {

        JFXDialogLayout content = new JFXDialogLayout();
        Label mylabel = new Label(title + " Adm No. " + adm);

        content.setHeading(mylabel);
        content.setAlignment(Pos.BASELINE_LEFT);
        content.setBody(new Text(message));

        JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonCancel = new JFXButton("Exit");
        buttonCancel.setStyle("-fx-border-color:seagreen;"
                + "-fx-border-width:1;"
                + "");

        buttonCancel.setOnAction(e -> dlog.close());

        content.setActions(buttonCancel);
        content.autosize();
        content.setPadding(new Insets(6, 0, 6, 0));
        content.setLayoutX(50);
        content.setLayoutY(50);

        dlog.show();

    }

    public TextField getTxt_score() {
        return txt_score;
    }

    public void setTxt_score(TextField txt_score) {
        this.txt_score = txt_score;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getAdm_number() {
        return adm_number;
    }

    public void setAdm_number(String adm_number) {
        this.adm_number = adm_number;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

}
