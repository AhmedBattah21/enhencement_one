/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;

import AppFuctions.CoursesClass;
import static AppFuctions.Functions.getMaxScore;
import AppFuctions.StudentsClass;
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
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ReportUpdaterFormController implements Initializable {

    @FXML
    private ComboBox cmb_unitname;

    @FXML
    private Label infor_1;

    @FXML
    private ComboBox cmb_examname;

    @FXML
    private Label infor_2;

    @FXML
    private TextField txt_score;

    @FXML
    private Label infor_3;

    @FXML
    private Button btn_addscore;

    private Label lbl_infor1;
    String adm = "";
    String term;
    String std_year;
    String course;
    String exam_year;
    
    ExaminationFile_ReportFormController cc = null;

    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
    
    FontAwesomeIconView error_b = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor_b = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        error.setGlyphSize(20);
        error.setStyle("-fx-fill:red");

        infor.setGlyphSize(20);
        infor.setStyle("-fx-fill:skyblue");
        
        
        error_b.setGlyphSize(30);
        error_b.setStyle("-fx-fill:red");

        infor_b.setGlyphSize(30);
        infor_b.setStyle("-fx-fill:skyblue");
        

        ObservableList<String> exams = FXCollections.observableArrayList();
        exams.addAll("MID TERM", "END TERM");
        cmb_examname.setItems(exams);
        
        CoursesClass.populateComboBoxClasses(cmb_unitname, "UnitName", "UnitsTable");
        
    }

    @FXML
    void save_newdata(ActionEvent event) {

        if (!cmb_unitname.getSelectionModel().isEmpty()) {
            String unitname = cmb_unitname.getSelectionModel().getSelectedItem().toString();

            if (!cmb_examname.getSelectionModel().isEmpty()) {

                String exam_name = cmb_examname.getSelectionModel().getSelectedItem().toString();

                if (!txt_score.getText().isEmpty()) {

                    int score = Integer.parseInt(txt_score.getText());

                    if (score <= getMaxScore(exam_name)) {

                        if (!checkExamRecord()) {

                            String std_course = StudentsClass.getStdCourse(adm);

                            String query = "";

                            query = "INSERT INTO exam_2017 VALUES ('" + adm + "','" + exam_name + "',"
                                    + "'" + term + "','" + score + "','" + course + "',null,'" + unitname + "','" + exam_year + "','" + std_year + "')";

                            Connection conn = sqlDataBaseConnection.sqliteconnect();

                            try {

                                Statement st = conn.createStatement();
                                st.executeUpdate(query);
                                
                                cc.getData();

                                conn.close();

                                lbl_infor1.setGraphic(infor_b);
                                lbl_infor1.setText("Great!! New Score  ("+ score+")  Saved....");

                                
                            } catch (SQLException exc) {
                                System.out.println("" + exc);
                            }

                        } else {

                            lbl_infor1.setGraphic(error_b);
                            lbl_infor1.setText("No No..No.!! .That Score Alread Exist, Sorry Cant Change It..Try Editor");
                            lbl_infor1.setGraphic(error_b);

                        }

                    } else {

                        lbl_infor1.setGraphic(error_b);
                        lbl_infor1.setText("Whaat!! The Score Is Invalid ");
                        lbl_infor1.setGraphic(error_b);
                    }

                } else {

                    lbl_infor1.setGraphic(error_b);
                }

            } else {

                infor_2.setGraphic(error);

            }
        } else {

            infor_1.setGraphic(error);

        }

    }

    public Boolean checkExamRecord() {

        boolean result = false;
        if (!cmb_unitname.getSelectionModel().isEmpty()) {
            String unit_name = cmb_unitname.getSelectionModel().getSelectedItem().toString();

            if (!cmb_examname.getSelectionModel().isEmpty()) {
                String exam_name = cmb_examname.getSelectionModel().getSelectedItem().toString();

                String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + adm + "'"
                        + "AND Exam_Name = '" + exam_name + "' AND Exam_Term = '" + term + "' AND"
                        + " Unit_Name = '" + unit_name + "' AND Syear = '" + std_year + "' AND Year = '" + exam_year + "'";
                
              

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

            } else {

                infor_2.setGraphic(error);
            }
        } else {

            infor_1.setGraphic(error);
        }

        return result;

    }

    public void checkScore() {
        if (!cmb_examname.getSelectionModel().isEmpty()) {
            try {
                int score = Integer.parseInt(txt_score.getText().trim());
                String exam_name = cmb_examname.getSelectionModel().getSelectedItem().toString();

                if (score >= 0 && score <= getMaxScore(exam_name)) {

                    btn_addscore.setDisable(false);

                    lbl_infor1.setGraphic(infor_b);

                    lbl_infor1.setText("Wow!! That is a Correct Score..");

                } else {

                    lbl_infor1.setGraphic(error_b);
                    lbl_infor1.setText("Hay Max Score Is " + getMaxScore(exam_name));
                    btn_addscore.setDisable(true);

                }

            } catch (NumberFormatException exc) {

                lbl_infor1.setGraphic(error_b);
                lbl_infor1.setText("Sorry..We Cant Have such scores !! Invalid Mark");
                btn_addscore.setDisable(true);

            }

        } else {

            infor_2.setGraphic(error);

        }

    }

    public Label getLbl_infor1() {
        return lbl_infor1;
    }

    public void setLbl_infor1(Label lbl_infor1) {
        this.lbl_infor1 = lbl_infor1;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }

    public ExaminationFile_ReportFormController getCc() {
        return cc;
    }

    public void setCc(ExaminationFile_ReportFormController cc) {
        this.cc = cc;
    }

}
