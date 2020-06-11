/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import AppFuctions.Functions;
import static AppFuctions.Functions.getMaxScore;
import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import reportgenthree.objects.StprofileExamination_finaldetails;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StprofileExamination_finaldetailsController implements Initializable {

    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @FXML
    private Label lbl_coursename;

    @FXML
    private Label lbl_examdetails;

    @FXML
    private TableView<StprofileExamination_finaldetails> table;

    @FXML
    private TableColumn col_no;

    @FXML
    private TableColumn col_unitname;

    @FXML
    private TableColumn col_midscore;

    @FXML
    private TableColumn col_endscore;

    @FXML
    private TableColumn col_totals;

    @FXML
    private TableColumn col_remark;

    @FXML
    private MenuItem refresh;

    @FXML
    private MenuItem close;

    @FXML
    private ComboBox<?> cmb_examnameone;

    @FXML
    private JFXTextField txt_editrecord;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private Label lbl_inforone;

    @FXML
    private ComboBox cmb_examnametwo;

    @FXML
    private ComboBox cmb_unitname;

    @FXML
    private JFXTextField txt_addscore;

    @FXML
    private JFXTextField txt_unitname;

    @FXML
    private JFXButton btn_addnewscore;

    @FXML
    private Label lbl_infortwo;

    @FXML
    private JFXButton btn_saveprint;

    private TabPane parentTab;
    private Tab mytab;

    String term;
    String adm;
    String std_year;
    String exam_year;

    final ObservableList<StprofileExamination_finaldetails> data = FXCollections.observableArrayList();
    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        error.setGlyphStyle("-fx-fill:red");
        error.setGlyphSize(30);

        infor.setGlyphStyle("-fx-fill:seagreen");
        infor.setGlyphSize(30);

        col_midscore.setCellValueFactory(new PropertyValueFactory<>("mid_score"));
        col_endscore.setCellValueFactory(new PropertyValueFactory<>("end_score"));
        col_totals.setCellValueFactory(new PropertyValueFactory<>("term_totals"));

        col_unitname.setCellValueFactory(new PropertyValueFactory<>("unitname"));
        col_no.setCellValueFactory(new PropertyValueFactory<>("count"));

        col_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));

        table.setOnMouseClicked(e -> {

            txt_unitname.setText(table.getSelectionModel().getSelectedItem().getUnitname());

        });

        CoursesClass.populateComboBoxClasses(cmb_examnameone, "ExaminationName", "ExamNames");
        CoursesClass.populateComboBoxClasses(cmb_examnametwo, "ExaminationName", "ExamNames");
        CoursesClass.populateComboBoxClasses(cmb_unitname, "UnitName", "UnitsTable");
    }

    @FXML
    void add_examscore(ActionEvent event) {

        if (!txt_addscore.getText().isEmpty() && !cmb_unitname.getSelectionModel().isEmpty() && !cmb_examnametwo.getSelectionModel().isEmpty()) {

            int score = Integer.parseInt(txt_addscore.getText());
            String unit_name = cmb_unitname.getSelectionModel().getSelectedItem().toString();
            String exam_name = cmb_examnametwo.getSelectionModel().getSelectedItem().toString();

            if (score <= getMaxScore(exam_name)) {

                if (!checkExamRecordTwo()) {

                    String std_course = StudentsClass.getStdCourse(adm);

                    String query = "";

                    query = "INSERT INTO exam_2017 VALUES ('" + adm + "','" + exam_name + "',"
                            + "'" + term + "','" + score + "','" + std_course + "',null,'" + unit_name + "','" + exam_year + "','" + std_year + "')";

                    Connection conn = sqlDataBaseConnection.sqliteconnect();

                    try {

                        Statement st = conn.createStatement();
                        st.executeUpdate(query);

                        conn.close();

                        lbl_infortwo.setGraphic(infor);
                        lbl_infortwo.setText("Score Saved");

                        getData();

                    } catch (SQLException exc) {
                        System.out.println("" + exc);
                    }

                } else {

                    lbl_infortwo.setGraphic(error);
                    lbl_infortwo.setText("Score Alread Exist");
                    lbl_infortwo.setGraphic(error);

                }

            } else {

                lbl_infortwo.setGraphic(error);
                lbl_infortwo.setText("Score Is Invalid");
                lbl_infortwo.setGraphic(error);
            }

        } else {

            lbl_infortwo.setGraphic(error);
            lbl_infortwo.setText("Can Not Add Score..");
        }

    }

    public void getData() {

        data.clear();
        table.getItems().clear();

        String query_one = "SELECT Mid,End,Exam_Term,Unit_Name,Unit,ID,Year, Mid_year,Syear,stdyear "
                + "FROM"
                + "(SELECT Exam_Name As Mid_Name,Exam_Score As Mid,Exam_Term As Term,Adm_Number As ID,Unit_Name,Year As Mid_year,Syear FROM"
                + " exam_2017 where Adm_Number = '" + adm + "' AND Exam_Name='MID TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "') "
                + "LEFT JOIN "
                + "(SELECT Exam_Name As End_Name,Exam_Score As End,Exam_Term,Adm_Number,Unit_Name As Unit,Year,Syear As stdyear FROM"
                + " exam_2017 where Adm_Number = '" + adm + "' AND Exam_Name='END TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' )"
                + "ON Adm_Number = ID AND Unit_Name = Unit AND Exam_Term = Term AND Syear = stdyear AND Mid_year = Year";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        AnchorPane pane = null;

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query_one);

            int count = 1;

            while (rst.next()) {

                String mid_score = rst.getString("Mid");
                String end_score = rst.getString("End");
                String unitname = rst.getString("Unit_Name");
                
                String end_totals = "**";
               String unitremark = "**";
                
                if (mid_score != null && end_score != null) {

                    int totals = Integer.parseInt(mid_score) + Integer.parseInt(end_score);
                    end_totals = Integer.toString(totals);
                    unitremark = getComment(totals);
                }

                if (mid_score == null) {

                    mid_score = "**";
                }

                if (end_score == null) {

                    end_score = "**";
                }
                
                if(end_totals.equals("**")){
                    
                    unitremark = "**";
                
                }
               

                data.add(new StprofileExamination_finaldetails(count, mid_score, unitname, end_score, end_totals, unitremark));

                count++;

            }

            conn.close();

            table.setItems(data);

            lbl_examdetails.setText(exam_year.toUpperCase() + "  " + term.toUpperCase() + " TOTALS  " + std_year.toUpperCase() + " REPORT");
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);

        }

    }

    @FXML
    void Edit_Record(ActionEvent event) {

        String new_rec = txt_editrecord.getText();

        try {

            if (!cmb_examnameone.getSelectionModel().isEmpty() && !new_rec.isEmpty() && !txt_unitname.getText().isEmpty()) {

                String examname = cmb_examnameone.getSelectionModel().getSelectedItem().toString();

                String unit_name = txt_unitname.getText();

                int maxscore = Functions.getMaxScore(examname);
                int record = Integer.parseInt(new_rec);

                if (record <= maxscore) {

                    String query = "UPDATE exam_2017 SET Exam_Score = '" + new_rec + "' WHERE   Adm_Number = '" + adm + "'"
                            + "AND Exam_Name = '" + examname + "'  AND Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND "
                            + "Syear = '" + std_year + "' AND Unit_Name = '" + unit_name + "'";

                    try {

                        try (Connection conn = sqlDataBaseConnection.sqliteconnect()) {
                            Statement st = conn.createStatement();
                            int value = st.executeUpdate(query);

                            if (value >= 1) {

                                lbl_examdetails.setText(exam_year.toUpperCase() + "  " + term.toUpperCase() + "  "
                                        + "" + examname.toUpperCase() + "  " + std_year.toUpperCase() + " REPORT");

                                lbl_inforone.setGraphic(infor);
                                lbl_inforone.setText("Score Changed To  " + new_rec);
                                getData();

                            } else {

                                lbl_inforone.setText("  Does Not Exist In The  Table...");
                                lbl_inforone.setGraphic(error);

                            }
                        }

                    } catch (SQLException exc) {

                        System.out.println(" Error " + exc);
                        lbl_inforone.setGraphic(error);

                    }

                } else {

                    lbl_inforone.setText("Score Is Invalid...Highest Score Is " + maxscore + " !!");
                    lbl_inforone.setGraphic(error);

                }

            } else {

                lbl_inforone.setText("Select a row to edit");
                lbl_inforone.setGraphic(error);

            }

        } catch (NumberFormatException exc) {

            lbl_inforone.setText("Invalid Records....");
            lbl_inforone.setGraphic(error);

        }

    }

    public void checkScoreone() {

        try {
            int score = Integer.parseInt(txt_editrecord.getText().trim());
            String examname = cmb_examnameone.getSelectionModel().getSelectedItem().toString();

            if (score >= 0 && score <= getMaxScore(examname)) {

                btn_edit.setDisable(false);

                lbl_inforone.setGraphic(infor);

                lbl_inforone.setText("Correct Score");

            } else {

                lbl_inforone.setGraphic(error);
                lbl_inforone.setText("Max Score Is " + getMaxScore(examname));
                btn_edit.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_inforone.setGraphic(error);
            lbl_inforone.setText("Invalid Mark");
            btn_edit.setDisable(true);

        }

    }

    public void checkScoretwo() {

        try {
            int score = Integer.parseInt(txt_addscore.getText().trim());
            String examname = cmb_examnametwo.getSelectionModel().getSelectedItem().toString();

            if (score >= 0 && score <= getMaxScore(examname)) {

                btn_addnewscore.setDisable(false);

                lbl_infortwo.setGraphic(infor);

                lbl_infortwo.setText("Correct Score");

            } else {

                lbl_infortwo.setGraphic(error);
                lbl_infortwo.setText("Max Score Is " + getMaxScore(examname));
                btn_addnewscore.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_infortwo.setGraphic(error);
            lbl_infortwo.setText("Invalid Mark");
            btn_addnewscore.setDisable(true);

        }

    }

    public Boolean checkExamRecordone() {

        boolean result = false;
        if (!txt_unitname.getText().isEmpty() && !cmb_examnameone.getSelectionModel().isEmpty()) {

            String examname = cmb_examnameone.getSelectionModel().getSelectedItem().toString();
            String unit_name = txt_unitname.getText();

            String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + adm + "'"
                    + "AND Exam_Name = '" + examname + "' AND Exam_Term = '" + term + "' AND Syear = '" + std_year + "' AND Year = '" + exam_year + "'"
                    + " AND Unit_Name = '" + unit_name + "'";

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

            lbl_inforone.setText("Select Exam Name..");
        }

        return result;

    }

    public Boolean checkExamRecordTwo() {

        boolean result = false;
        if (!cmb_unitname.getSelectionModel().isEmpty() && !cmb_examnametwo.getSelectionModel().isEmpty()) {

            String examname = cmb_examnametwo.getSelectionModel().getSelectedItem().toString();
            String unit_name = cmb_unitname.getSelectionModel().getSelectedItem().toString();
            

            String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + adm + "'"
                    + "AND Exam_Name = '" + examname + "' AND Exam_Term = '" + term + "' AND Syear = '" + std_year + "' AND Year = '" + exam_year + "'"
                    + " AND Unit_Name = '" + unit_name + "'";

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

            lbl_inforone.setText("Select Exam Name..");
        }

        return result;

    }
    
     public String getComment(int p_score) {
        String comment = "poor";

        if (p_score >= 80) {

            comment = "Distinction";

        } else if (p_score >= 60) {

            comment = "Credit";

        } else if (p_score >= 40) {

            comment = "Pass";

        } else if (p_score >= 30) {

            comment = "Reffer";

        } else {

            comment = "Fail";

        }

        return comment;
    }

    @FXML
    void close(ActionEvent event) {

        parentTab.getTabs().remove(mytab);

    }

    public void refresh() {

        getData();

    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getAdm() {
        return adm;
    }

    public void setAdm(String adm) {
        this.adm = adm;
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }

    public TabPane getParentTab() {
        return parentTab;
    }

    public void setParentTab(TabPane parentTab) {
        this.parentTab = parentTab;
    }

    public Tab getMytab() {
        return mytab;
    }

    public void setMytab(Tab mytab) {
        this.mytab = mytab;
    }

}
