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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Dialogs.Dialogs_functions;
import reportgenthree.views.LoginscreenController;
import reportgenthree.views.ProcessCompleteController;
import reportgenthree.views.SystemNameSetterController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationScoreSheetViewerController implements Initializable {

    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @FXML
    private TableView<ExaminationScoreSheetViewerClass> table;

    @FXML
    private TableColumn col_count;

    @FXML
    private TableColumn col_adm;

    @FXML
    private TableColumn col_score;

    @FXML
    private TableColumn col_stdname;

    @FXML
    private TableColumn col_remark;

    @FXML
    private Label lbl_coursename;

    @FXML
    private TableColumn col_recordid;

    @FXML
    private Label lbl_examdetails;

    @FXML
    private Label lbl_infor;

    @FXML
    private JFXTextField txt_adm;

    @FXML
    private JFXTextField txt_score;

    @FXML
    private JFXButton btn_addscore;

    @FXML
    private JFXTextField txt_recordid;

    @FXML
    private JFXTextField txt_oldscore;

    @FXML
    private JFXTextField txt_newscore;

    @FXML
    private JFXButton btn_editscore;

    @FXML
    private JFXButton btn_createpdf;

    @FXML
    private StackPane mypane;

    String unit_name;
    String course;
    String std_year;
    String term;
    String exam_year;
    String exam_name;
    JFXTabPane parentTab;
    Tab tab;

    String main_query = "";

    final ObservableList<ExaminationScoreSheetViewerClass> data = FXCollections.observableArrayList();
    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
    FontAwesomeIconView trash = new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        error.setGlyphSize(20);
        error.setStyle("-fx-fill:red");

        trash.setGlyphSize(20);
        trash.setStyle("-fx-fill:red");

        infor.setGlyphSize(20);
        infor.setStyle("-fx-fill:skyblue");

        col_count.setCellValueFactory(new PropertyValueFactory<>("count"));
        col_adm.setCellValueFactory(new PropertyValueFactory<>("adm_number"));
        col_score.setCellValueFactory(new PropertyValueFactory<>("score"));
        col_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));

        col_stdname.setCellValueFactory(new PropertyValueFactory<>("std_name"));

        lbl_schoolname.setText(SystemNameSetterController.getSname());
        lbl_boxnumber.setText(SystemNameSetterController.getbox());

        table.setOnMouseClicked(e -> {

            if (!table.getSelectionModel().isEmpty()) {
                String record_id = table.getSelectionModel().getSelectedItem().getAdm_number();
                txt_recordid.setText(record_id);
                String old_value = table.getSelectionModel().getSelectedItem().getScore();
                txt_oldscore.setText(old_value);
                txt_newscore.setText("");
            }

        });

        btn_createpdf.setOnAction(e -> createPdfDocument());

    }

    public void populate_table() {

        lbl_coursename.setText(course);

        if (course.equals("All Courses Records Sheets")) {

            main_query = "SELECT * FROM exam_2017 "
                    + "WHERE"
                    + " Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND  Exam_Term = '" + term + "' "
                    + "AND Unit_Name = '" + unit_name + "' AND  Exam_Name = '" + exam_name + "'";
        } else {

            main_query = "SELECT * FROM exam_2017 "
                    + "WHERE"
                    + " Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND Std_Course = '" + course + "' and Exam_Term = '" + term + "' "
                    + "AND Unit_Name = '" + unit_name + "' AND  Exam_Name = '" + exam_name + "'";

        }

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(main_query);

            int count = 1;

            while (rst.next()) {

                String adm = rst.getString("Adm_Number");
                String recordid = rst.getString("Exam_Id");
                String unitscore = rst.getString("Exam_Score");

                String unitremark = getComment(unitscore, exam_name);
                String std_name = StudentsClass.getStdName(adm);

                data.add(new ExaminationScoreSheetViewerClass(count, adm, unitscore, unitremark, recordid, std_name));
                count++;

            }

            conn.close();

            table.setItems(data);

            lbl_examdetails.setText(std_year.toUpperCase() + " " + exam_year.toUpperCase() + "  " + term.toUpperCase() + "  " + exam_name.toUpperCase() + ""
                    + "   " + unit_name.toUpperCase() + " Score Sheet ");
        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

    }

    public void refresh() {

        lbl_coursename.setText(course);

        data.clear();
        table.getItems().clear();

        if (course.equals("All Courses Records Sheets")) {

            main_query = "SELECT * FROM exam_2017 "
                    + "WHERE"
                    + " Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND  Exam_Term = '" + term + "' "
                    + "AND Unit_Name = '" + unit_name + "' AND  Exam_Name = '" + exam_name + "'";
        } else {

            main_query = "SELECT * FROM exam_2017 "
                    + "WHERE"
                    + " Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND Std_Course = '" + course + "' and Exam_Term = '" + term + "' "
                    + "AND Unit_Name = '" + unit_name + "' AND  Exam_Name = '" + exam_name + "'";

        }

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(main_query);

            int count = 1;

            while (rst.next()) {

                String adm = rst.getString("Adm_Number");
                String recordid = rst.getString("Exam_Id");
                String unitscore = rst.getString("Exam_Score");
                String unitremark = getComment(unitscore, exam_name);

                String std_name = StudentsClass.getStdName(adm);

                data.add(new ExaminationScoreSheetViewerClass(count, adm, unitscore, unitremark, recordid, std_name));

                count++;

            }

            table.setItems(data);

            conn.close();

            lbl_examdetails.setText(std_year.toUpperCase() + " " + exam_year.toUpperCase() + "  " + term.toUpperCase() + "  " + exam_name.toUpperCase() + ""
                    + "   " + unit_name.toUpperCase() + " Score Sheet ");

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

    }

    @FXML
    void Delete_Record(ActionEvent event) {

        Random rand = new Random();

        LocalDateTime dor = LocalDateTime.now();

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

        int n = rand.nextInt(50000) + 1;

        String record_id = table.getSelectionModel().getSelectedItem().getRecordid();
        String adm = table.getSelectionModel().getSelectedItem().getAdm_number();
        String score = table.getSelectionModel().getSelectedItem().getScore();
        String result = Dialogs_functions.get_custom_confirm("Delete Record (" + record_id + ")  " + score + " For " + adm);

        if (result.equals("continue")) {

            String query = "DELETE FROM exam_2017 WHERE Exam_Id = '" + record_id + "'";
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            try {
                Statement st = conn.createStatement();
                int rows = st.executeUpdate(query);

                if (rows >= 1) {

                    lbl_infor.setText("Record " + record_id + " Deleted ");
                    lbl_infor.setGraphic(infor);

                    String desc = "Score For Student " + adm + " Deleted \n Unit " + unit_name + " Score " + score + "\n ("
                            + " " + exam_name + " - " + exam_year + "-" + term + ")";

                    table.getItems().clear();
                    data.clear();

                    refresh();
                    sqlDataBaseConnection.add_activity("Activity:" + n, "Std Editor", desc, fomat.format(dor), LoginscreenController.getUserName());

                } else {

                    lbl_infor.setText("Sorry!! Could Not Delete Record " + record_id + ".... ");
                    lbl_infor.setGraphic(error);

                }
            } catch (SQLException exc) {

                System.out.println(" " + exc);
            }

        } else {

            lbl_infor.setText("You Cancelled...");
            lbl_infor.setGraphic(infor);

        }

    }

    @FXML
    void Edit_Record(ActionEvent event) {

        String record_id = table.getSelectionModel().getSelectedItem().getRecordid();
        txt_recordid.setText(record_id);
        String old_value = table.getSelectionModel().getSelectedItem().getScore();
        txt_oldscore.setText(old_value);

    }

    public boolean save_newscore() {

        Random rand = new Random();

        LocalDateTime dor = LocalDateTime.now();

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

        int n = rand.nextInt(50000) + 1;

        boolean result = false;
        if (!txt_newscore.getText().isEmpty() && !table.getSelectionModel().isEmpty()) {

            String score = txt_newscore.getText();
            String record_id = table.getSelectionModel().getSelectedItem().getRecordid();
            String adm = table.getSelectionModel().getSelectedItem().getAdm_number();

            String query = "UPDATE  exam_2017 SET Exam_Score = '" + score + "' WHERE Exam_Id = '" + record_id + "'";

            int score_two = Integer.parseInt(txt_newscore.getText());

            if (score_two <= getMaxScore(exam_name)) {

                try {
                    Connection conn = sqlDataBaseConnection.sqliteconnect();
                    Statement st = conn.createStatement();

                    int count = st.executeUpdate(query);

                    if (count >= 1) {

                        result = true;
                        lbl_infor.setGraphic(infor);
                        lbl_infor.setText("Great!! Score Edited..");

                        refresh();

                        String desc = "New Score For " + adm + " Added ("
                                + "" + exam_year + " - " + exam_name + "-" + unit_name + "-" + term + ")";

                        sqlDataBaseConnection.add_activity("Activity:" + n, "Std Editor", desc, fomat.format(dor), LoginscreenController.getUserName());

                    }
                    conn.close();

                } catch (SQLException exc) {

                    System.out.println(" " + exc);
                }

            } else {

                lbl_infor.setGraphic(error);
                lbl_infor.setText("No..That Is Invalid Mark");
            }

        } else {

            lbl_infor.setGraphic(error);
            lbl_infor.setText("Hay!! Enter new score...or Select a row to edit");

        }

        return result;
    }

    @FXML
    void Refresh_table(ActionEvent event) {

        refresh();
    }

    @FXML
    void close(ActionEvent event) {

        parentTab.getTabs().remove(tab);

    }

    @FXML
    void save_data(ActionEvent event) {
        Random rand = new Random();

        LocalDateTime dor = LocalDateTime.now();

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

        int n = rand.nextInt(50000) + 1;

        if (!txt_adm.getText().isEmpty()) {

            String adm = txt_adm.getText();

            if (!txt_score.getText().isEmpty()) {

                int score = Integer.parseInt(txt_score.getText());

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

                            lbl_infor.setGraphic(infor);
                            lbl_infor.setText("Score Saved");

                            String desc = "New Score For " + adm + " Added ("
                                    + "" + exam_year + " - " + exam_name + "-" + unit_name + "-" + term + ")";

                            sqlDataBaseConnection.add_activity("Activity:" + n, "Std Editor", desc, fomat.format(dor), LoginscreenController.getUserName());

                            refresh();

                        } catch (SQLException exc) {
                            System.out.println("" + exc);
                        }

                    } else {

                        lbl_infor.setGraphic(error);
                        lbl_infor.setText("Score Alread Exist");
                        lbl_infor.setGraphic(error);

                    }

                } else {

                    lbl_infor.setGraphic(error);
                    lbl_infor.setText("Score Is Invalid");
                    lbl_infor.setGraphic(error);
                }

            } else {

                lbl_infor.setGraphic(error);
            }

        } else {

            lbl_infor.setGraphic(error);

        }

    }

    public void checkScore() {

        try {
            int score = Integer.parseInt(txt_score.getText().trim());

            if (score >= 0 && score <= getMaxScore(exam_name)) {

                btn_addscore.setDisable(false);

                lbl_infor.setGraphic(infor);

                lbl_infor.setText("Correct Score");

            } else {

                lbl_infor.setGraphic(error);
                lbl_infor.setText("Max Score Is " + getMaxScore(exam_name));
                btn_addscore.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_infor.setGraphic(error);
            lbl_infor.setText("Invalid Mark");
            btn_addscore.setDisable(true);

        }

    }

    public void checkScore_edit() {

        try {
            int score = Integer.parseInt(txt_newscore.getText().trim());

            if (score >= 0 && score <= getMaxScore(exam_name)) {

                btn_addscore.setDisable(false);

                lbl_infor.setGraphic(infor);

                lbl_infor.setText("Correct Score");

            } else {

                lbl_infor.setGraphic(error);
                lbl_infor.setText("Max Score Is " + getMaxScore(exam_name));
                btn_addscore.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_infor.setGraphic(error);
            lbl_infor.setText("Invalid Mark");
            btn_addscore.setDisable(true);

        }

    }

    public Boolean checkExamRecord() {

        boolean result = false;

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + txt_adm.getText() + "'"
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

    public void check_student() {

        if (!course.equals("All Courses Records Sheets")) {

            if (StudentsClass.checkStudent(txt_adm.getText(), course)) {

                lbl_infor.setGraphic(infor);
                lbl_infor.setText(StudentsClass.getStdName(txt_adm.getText()));
            } else {

                lbl_infor.setGraphic(error);
                lbl_infor.setText("Student Not Found...");

            }

        } else {

            if (StudentsClass.checkStudentTwo(txt_adm.getText())) {

                lbl_infor.setGraphic(infor);
                lbl_infor.setText(StudentsClass.getStdName(txt_adm.getText()));
            } else {

                lbl_infor.setGraphic(error);
                lbl_infor.setText("Student Not Found...");

            }

        }

    }

    public String getComment(String score, String examname) {
        String comment = "poor";

        int maxscore = Functions.getMaxScore(examname);

        double d_score = Double.parseDouble(score);

        double p_score = ((d_score / maxscore)) * 100;

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

    public void createPdfDocument() {

        try {
            String path = OpenSaveFileChooser();

            if (!path.equals("")) {

                ReportGenThree.LoadDescriptionSettingsForClassListPdf();

                boolean result = AppFuctions.ScoreSheetPdfCreator_v2.creator(main_query, path);

                if (result) {

                    openDialog_processcomplete();
                }

            }

        } catch (Exception exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "E.R.M.S", "Exception Found", "Erck Error " + exc);

        }

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

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
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

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
    }

    public TabPane getParentTab() {
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
