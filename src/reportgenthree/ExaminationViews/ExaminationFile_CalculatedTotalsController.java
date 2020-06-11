/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationViews;

import AppFuctions.Functions;
import static AppFuctions.Functions.getMaxScore;
import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import reportgenthree.ReportGenThree;
import reportgenthree.views.LoginscreenController;
import reportgenthree.views.ProcessCompleteController;
import reportgenthree.views.SystemNameSetterController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationFile_CalculatedTotalsController implements Initializable {

    @FXML
    private Label lbl_schoolname;
    
    @FXML
    private StackPane mypane;

    @FXML
    private Label lbl_boxnumber;

    @FXML
    private Label lbl_coursename;

    @FXML
    private Label lbl_examdetails;

    @FXML
    private TableView<ExaminationFile_CalculatedTotalsClass> table;

    @FXML
    private TableColumn col_no;

    @FXML
    private TableColumn col_stdname;

    @FXML
    private TableColumn col_adm;

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
    private TextField txt_recordid;

    @FXML
    private TextField txt_newecord;

    @FXML
    private Label lbl_infor;

    @FXML
    private Button btn_save;

    @FXML
    private JFXTextField txt_admOne;

    @FXML
    private ComboBox cmb_examone;

    @FXML
    private JFXTextField txt_score;

    @FXML
    private JFXButton btn_addscore;

    @FXML
    private Label lbl_infor1;

    @FXML
    private JFXTextField txt_admtwo;

    @FXML
    private ComboBox cmb_examtwo;

    @FXML
    private JFXTextField txt_scoretwo;

    @FXML
    private JFXButton btn_editscore;

    @FXML
    private Label lbl_infor2;

    @FXML
    private TextField txt_search;
    
     @FXML
    private JFXButton btn_print;

    String unit_name;
    String course;
    String std_year;
    String term;
    String exam_year;
    String exam_name;
    JFXTabPane parentTab;
    Tab tab;
    
    String main_query = "";
    
    

    final ObservableList<ExaminationFile_CalculatedTotalsClass> data = FXCollections.observableArrayList();
    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        error.setGlyphSize(20);
        error.setStyle("-fx-fill:red");

        infor.setGlyphSize(20);
        infor.setStyle("-fx-fill:skyblue");

        col_midscore.setCellValueFactory(new PropertyValueFactory<>("mid_score"));
        col_endscore.setCellValueFactory(new PropertyValueFactory<>("end_score"));
        col_totals.setCellValueFactory(new PropertyValueFactory<>("term_totals"));
        col_adm.setCellValueFactory(new PropertyValueFactory<>("Admnumber"));
        col_stdname.setCellValueFactory(new PropertyValueFactory<>("std_name"));
        col_no.setCellValueFactory(new PropertyValueFactory<>("count"));

        col_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));

        lbl_schoolname.setText(SystemNameSetterController.getSname());
        lbl_boxnumber.setText(SystemNameSetterController.getbox());

        ObservableList<String> exams = FXCollections.observableArrayList();
        exams.addAll("MID TERM", "END TERM");
        cmb_examone.setItems(exams);
        cmb_examtwo.setItems(exams);

        table.setOnMouseClicked(e -> {

            try {

                String adm = table.getSelectionModel().getSelectedItem().getAdmnumber();
                txt_admtwo.setText(adm);

            } catch (NullPointerException exc) {

            }

        });
        
       btn_print.setOnAction(e->createPdfDocument());
    }

    public void getData() {

        lbl_coursename.setText(course);
        data.clear();
        table.getItems().clear();
        //String query = "";

        if (!txt_search.getText().isEmpty()) {
            getData_Search();
        } else {

            if (course.equals("All Courses Records Sheets")) {

                main_query = "SELECT Mid,End,Exam_Term,Unit_Name,Unit,Adm_Number,Year, Mid_year,Syear,stdyear "
                        + "FROM"
                        + "(SELECT Exam_Name As Mid_Name,Exam_Score As Mid,Exam_Term As Term,Adm_Number,Unit_Name,Year As Mid_year,Syear,Std_Course FROM"
                        + " exam_2017 where  Exam_Name='MID TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "'"
                        + "AND  Unit_Name = '" + unit_name + "') "
                        + "LEFT JOIN "
                        + "(SELECT Exam_Name As End_Name,Exam_Score As End,Exam_Term,Adm_Number  As ID,Unit_Name As Unit,Year,Syear As stdyear,Std_Course As  Course FROM"
                        + " exam_2017 where  Exam_Name='END TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' "
                        + "AND  Unit_Name = '" + unit_name + "')"
                        + "ON Adm_Number = ID AND Unit_Name = Unit AND Exam_Term = Term AND Syear = stdyear AND Mid_year = Year  AND Std_Course = Course";

            } else {

                main_query = "SELECT Mid,End,Exam_Term,Unit_Name,Unit,Adm_Number,Year, Mid_year,Syear,stdyear "
                        + "FROM"
                        + "(SELECT Exam_Name As Mid_Name,Exam_Score As Mid,Exam_Term As Term,Adm_Number,Unit_Name,Year As Mid_year,Syear,Std_Course FROM"
                        + " exam_2017 where  Exam_Name='MID TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "'"
                        + "AND Std_Course = '" + course + "' AND Unit_Name = '" + unit_name + "') "
                        + "LEFT JOIN "
                        + "(SELECT Exam_Name As End_Name,Exam_Score As End,Exam_Term,Adm_Number  As ID,Unit_Name As Unit,Year,Syear As stdyear,Std_Course As  Course FROM"
                        + " exam_2017 where  Exam_Name='END TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' "
                        + "AND Std_Course = '" + course + "' AND Unit_Name = '" + unit_name + "')"
                        + "ON Adm_Number = ID AND Unit_Name = Unit AND Exam_Term = Term AND Syear = stdyear AND Mid_year = Year  AND Std_Course = Course";
            }
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            AnchorPane pane = null;

            try {

                Statement st = conn.createStatement();
                ResultSet rst = st.executeQuery(main_query);

                int count = 1;

                while (rst.next()) {

                    String mid_score = rst.getString("Mid");
                    String end_score = rst.getString("End");

                    String adm = rst.getString("Adm_Number");
                    String std_name = AppFuctions.StudentsClass.getStdName(adm);
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

                    data.add(new ExaminationFile_CalculatedTotalsClass(count, mid_score, std_name, end_score, end_totals, unitremark, adm));

                    count++;

                }

                conn.close();

                table.setItems(data);

                lbl_examdetails.setText(exam_year.toUpperCase() + "  " + term.toUpperCase() + " TOTALS  " + std_year.toUpperCase() + " REPORT");
                conn.close();

            } catch (SQLException | NumberFormatException exc) {

                System.out.println("Error " + exc);

            }

        }

    }

    public void getData_Search() {

        lbl_coursename.setText(course);
        String hint = txt_search.getText();
        data.clear();
        table.getItems().clear();
        String query = "";

        if (course.equals("All Courses Records Sheets")) {

            query = "SELECT Mid,End,Exam_Term,Unit_Name,Unit,ID,Year, Mid_year,Syear,stdyear "
                    + "FROM"
                    + "(SELECT Exam_Name As Mid_Name,Exam_Score As Mid,Exam_Term As Term,Adm_Number As ID,Unit_Name,Year As Mid_year,Syear,Std_Course FROM"
                    + " exam_2017 where  Exam_Name='MID TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "'"
                    + "AND  Unit_Name = '" + unit_name + "'  AND Adm_Number LIKE '%" + hint + "%' ) "
                    + "LEFT JOIN "
                    + "(SELECT Exam_Name As End_Name,Exam_Score As End,Exam_Term,Adm_Number,Unit_Name As Unit,Year,Syear As stdyear,Std_Course As  Course FROM"
                    + " exam_2017 where  Exam_Name='END TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' "
                    + "AND  Unit_Name = '" + unit_name + "' AND Adm_Number LIKE '%" + hint + "%')"
                    + "ON Adm_Number = ID AND Unit_Name = Unit AND Exam_Term = Term AND Syear = stdyear AND Mid_year = Year  AND Std_Course = Course";

        } else {

            query = "SELECT Mid,End,Exam_Term,Unit_Name,Unit,ID,Year, Mid_year,Syear,stdyear "
                    + "FROM"
                    + "(SELECT Exam_Name As Mid_Name,Exam_Score As Mid,Exam_Term As Term,Adm_Number As ID,Unit_Name,Year As Mid_year,Syear,Std_Course FROM"
                    + " exam_2017 where  Exam_Name='MID TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "'"
                    + "AND Std_Course = '" + course + "' AND Unit_Name = '" + unit_name + "') "
                    + "LEFT JOIN "
                    + "(SELECT Exam_Name As End_Name,Exam_Score As End,Exam_Term,Adm_Number,Unit_Name As Unit,Year,Syear As stdyear,Std_Course As  Course FROM"
                    + " exam_2017 where  Exam_Name='END TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' "
                    + "AND Std_Course = '" + course + "' AND Unit_Name = '" + unit_name + "')"
                    + "ON Adm_Number = ID AND Unit_Name = Unit AND Exam_Term = Term AND Syear = stdyear AND Mid_year = Year  AND Std_Course = Course";
        }
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        AnchorPane pane = null;

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            int count = 1;

            while (rst.next()) {

                String mid_score = rst.getString("Mid");
                String end_score = rst.getString("End");

                String adm = rst.getString("ID");
                String std_name = AppFuctions.StudentsClass.getStdName(adm);
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

                data.add(new ExaminationFile_CalculatedTotalsClass(count, mid_score, std_name, end_score, end_totals, unitremark, adm));

                count++;

            }

            conn.close();

            table.setItems(data);

            lbl_examdetails.setText(exam_year.toUpperCase() + "  " + term.toUpperCase() + " TOTALS  " + std_year.toUpperCase() + " REPORT");
            conn.close();

        } catch (SQLException | NumberFormatException exc) {

            System.out.println("Error " + exc);

        }

    }

    @FXML
    void close(ActionEvent event) {

        parentTab.getTabs().remove(tab);

    }

    public void refresh() {

        getData();

    }

    public boolean save_newscore() {

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;

        boolean result = false;
        if (!txt_scoretwo.getText().isEmpty()) {

            String score = txt_scoretwo.getText();
            String exam_name = cmb_examtwo.getSelectionModel().getSelectedItem().toString();
            String adm = txt_admtwo.getText();

            String query = "UPDATE  exam_2017 SET Exam_Score = '" + score + "' WHERE Exam_Name = '" + exam_name + "' AND Exam_Term = '" + term + "' AND"
                    + " Unit_Name = '" + unit_name + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' AND Adm_Number = '" + adm + "'";

            int score_two = Integer.parseInt(score);

            if (checkExamRecord_two()) {

                if (score_two <= getMaxScore(exam_name)) {

                    try {
                        Connection conn = sqlDataBaseConnection.sqliteconnect();
                        Statement st = conn.createStatement();

                        int count = st.executeUpdate(query);

                        if (count >= 1) {

                            result = true;
                            lbl_infor2.setGraphic(infor);
                            lbl_infor2.setText("Score Edited..");

                            String desc = "Score For (Edited)" + adm + " Examination " + std_year + " - " + exam_year + " - \n " + term + " - " + unit_name + " -" + score;
                            sqlDataBaseConnection.add_activity("Activity:" + n, "Student Examination Scores",
                                    desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                            refresh();

                        } else {

                            result = true;
                            lbl_infor2.setGraphic(error);
                            lbl_infor2.setText("Action Failed");

                        }
                        conn.close();

                    } catch (SQLException exc) {

                        System.out.println(" " + exc);
                    }

                } else {

                    lbl_infor2.setGraphic(error);
                    lbl_infor2.setText("Invalid Mark");
                }

            } else {

                lbl_infor2.setGraphic(error);
                lbl_infor2.setText("Record Not Found");

            }

        }

        return result;
    }

    @FXML
    void save_newdata(ActionEvent event) {

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;

        if (!txt_admOne.getText().isEmpty()) {

            String adm = txt_admOne.getText();

            if (!txt_score.getText().isEmpty()) {

                int score = Integer.parseInt(txt_score.getText());
                String exam_name = cmb_examone.getSelectionModel().getSelectedItem().toString();

                if (score <= getMaxScore(exam_name)) {

                    if (!checkExamRecord()) {

                        String std_course = StudentsClass.getStdCourse(adm);

                        String query = "";

                        if (course.equals("All Courses Records Sheets")) {

                            query = "INSERT INTO exam_2017 VALUES ('" + adm + "','" + exam_name + "',"
                                    + "'" + term + "','" + score + "','" + std_course + "',null,'" + unit_name + "','" + exam_year + "','" + std_year + "')";
                        } else {

                            query = "INSERT INTO exam_2017 VALUES ('" + adm + "','" + exam_name + "',"
                                    + "'" + term + "','" + score + "','" + course + "',null,'" + unit_name + "','" + exam_year + "','" + std_year + "')";
                        }

                        Connection conn = sqlDataBaseConnection.sqliteconnect();

                        try {

                            Statement st = conn.createStatement();
                            st.executeUpdate(query);

                            conn.close();

                            lbl_infor1.setGraphic(infor);
                            lbl_infor1.setText("Score Saved");

                            String desc = "Score For (Added)" + adm + " Examination " + std_year + " - " + exam_year + " - \n " + term + " - " + unit_name + " -" + score;
                            sqlDataBaseConnection.add_activity("Activity:" + n, "Student Examination Scores",
                                    desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                            getData();

                        } catch (SQLException exc) {
                            System.out.println("" + exc);
                        }

                    } else {

                        lbl_infor1.setGraphic(error);
                        lbl_infor1.setText("Score Alread Exist");
                        lbl_infor1.setGraphic(error);

                    }

                } else {

                    lbl_infor1.setGraphic(error);
                    lbl_infor1.setText("Score Is Invalid");
                    lbl_infor1.setGraphic(error);
                }

            } else {

                lbl_infor1.setGraphic(error);
            }

        } else {

            lbl_infor1.setGraphic(error);

        }

    }

    public void checkScore() {

        try {
            int score = Integer.parseInt(txt_score.getText().trim());
            String exam_name = cmb_examone.getSelectionModel().getSelectedItem().toString();

            if (score >= 0 && score <= getMaxScore(exam_name)) {

                btn_addscore.setDisable(false);

                lbl_infor1.setGraphic(infor);

                lbl_infor1.setText("Correct Score");

            } else {

                lbl_infor1.setGraphic(error);
                lbl_infor1.setText("Max Score Is " + getMaxScore(exam_name));
                btn_addscore.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_infor1.setGraphic(error);
            lbl_infor1.setText("Invalid Mark");
            btn_addscore.setDisable(true);

        }

    }

    public void checkScore_edit() {

        try {
            int score = Integer.parseInt(txt_scoretwo.getText().trim());
            String exam_name = cmb_examtwo.getSelectionModel().getSelectedItem().toString();

            if (score >= 0 && score <= getMaxScore(exam_name)) {

                btn_editscore.setDisable(false);

                lbl_infor2.setGraphic(infor);

                lbl_infor2.setText("Correct Score");

            } else {

                lbl_infor2.setGraphic(error);
                lbl_infor2.setText("Max Score Is " + getMaxScore(exam_name));
                btn_editscore.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_infor2.setGraphic(error);
            lbl_infor2.setText("Invalid Mark");
            btn_editscore.setDisable(true);

        }

    }

    public Boolean checkExamRecord() {

        boolean result = false;
        String exam_name = cmb_examone.getSelectionModel().getSelectedItem().toString();

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + txt_admOne.getText() + "'"
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
        return result;

    }

    public Boolean checkExamRecord_two() {

        boolean result = false;
        String exam_name = cmb_examtwo.getSelectionModel().getSelectedItem().toString();

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + txt_admtwo.getText() + "'"
                + "AND Exam_Name = '" + exam_name + "' AND Exam_Term = '" + term + "' AND"
                + " Unit_Name = '" + unit_name + "' AND Syear = '" + std_year + "' AND Year = '" + exam_year + "'";

        System.out.println("checkExamRecord_two() " + query);

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

    public void check_student() {

        if (!course.equals("All Courses Records Sheets")) {

            if (StudentsClass.checkStudent(txt_admOne.getText(), course)) {

                lbl_infor1.setGraphic(infor);
                lbl_infor1.setText(StudentsClass.getStdName(txt_admOne.getText()));
            } else {

                lbl_infor1.setGraphic(error);
                lbl_infor1.setText("Student Not Found...");

            }

        } else {

            if (StudentsClass.checkStudent_indb(txt_admOne.getText())) {

                lbl_infor1.setGraphic(infor);
                lbl_infor1.setText(StudentsClass.getStdName(txt_admOne.getText()));
            } else {

                lbl_infor1.setGraphic(error);
                lbl_infor1.setText("Student Not Found...");

            }

        }

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
    
    public void createPdfDocument() {

        try {
            String path = OpenSaveFileChooser();

            ReportGenThree.LoadDescriptionSettingsForClassListPdf();

            boolean result = AppFuctions.ScoreEDTSheetPdfCreator_v2.creator(main_query,path);
            if(result){
            
                openDialog_processcomplete();
            }

        } catch (Exception exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Exception Found", "Erck Error " + exc);

        }

    }
    
    public void openDialog_processcomplete() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/ProcessComplete.fxml"));
            AnchorPane pane = loader.load();
            ProcessCompleteController cc = loader.getController();

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            cc.setDlog(dlog);
            dlog.show();

        } catch (IOException ex) {
            
            System.out.println(ex);
        }

    }
    
     public String OpenSaveFileChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage primary = (Stage) btn_addscore.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        String path = file.getAbsolutePath();
        return path;

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

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public JFXTabPane getParentTab() {
        return parentTab;
    }

    public void setParentTab(JFXTabPane parentTab) {
        this.parentTab = parentTab;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

}
