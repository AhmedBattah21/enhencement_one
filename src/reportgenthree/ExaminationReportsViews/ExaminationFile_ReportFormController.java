/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;

import AppFuctions.Functions;
import AppFuctions.Singlereportproducer;
import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;
import reportgenthree.views.ProcessCompleteController;
import reportgenthree.views.SystemNameSetterController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationFile_ReportFormController implements Initializable {

    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @FXML
    private Label lbl_email;

    @FXML
    private Label lbl_phone;

    @FXML
    private Label lbl_ref;

    @FXML
    private Label lbl_box;

    @FXML
    private Label lbl_town;

    @FXML
    private Label lbl_report_title;

    @FXML
    private Label lbl_stdname;

    @FXML
    private Label lbl_coursename;

    @FXML
    private Label lbl_adm;

    @FXML
    private Label lbl_term;

    @FXML
    private Label lbl_infor;

    @FXML
    private TableView<ExaminationFile_ReportFormClass> table;

    @FXML
    private TableColumn col_sno;

    @FXML
    private TableColumn col_course;

    @FXML
    private TableColumn col_mid;

    @FXML
    private TableColumn col_end;

    @FXML
    private TableColumn col_total;

    @FXML
    private TableColumn col_comment;

    @FXML
    private TableColumn col_initials;

    @FXML
    private Label lbl_overalresults;

    @FXML
    private StackPane mypane;

    @FXML
    private Button btn_print;

    @FXML
    private Button btn_close;

    private String std_name;
    private String std_year;
    private String adm_number;
    private String course;
    private String term;
    private String exam_year;
    private String overal_results;
    private ResultSet resultset = null;
    private JFXTabPane parent;
    private Tab tab;

    private ExaminationFile_ReportFormController exam_file_report_cc = null;
    private ExaminationFile_reportdetailsController examreportdtails = null;

    ObservableList exam_scores = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        col_sno.setCellValueFactory(new PropertyValueFactory<>("sno"));
        col_course.setCellValueFactory(new PropertyValueFactory<>("course"));
        col_mid.setCellValueFactory(new PropertyValueFactory<>("mid"));
        col_end.setCellValueFactory(new PropertyValueFactory<>("end"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("average"));
        col_comment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        col_initials.setCellValueFactory(new PropertyValueFactory<>("initials"));

        lbl_schoolname.setText(SystemNameSetterController.getSname());
        lbl_boxnumber.setText(SystemNameSetterController.getbox());
        lbl_email.setText("Email " + SystemNameSetterController.getEmail());
        lbl_phone.setText("Contacts " + SystemNameSetterController.getContacts());
        lbl_ref.setText("OUR REF:" + SystemNameSetterController.getRef());
        lbl_town.setText(SystemNameSetterController.getPlace());

        btn_close.setOnAction(e -> close());
        btn_print.setOnAction(e -> print_report());

    }

    public void print_report() {

        ObservableList examone_scores = FXCollections.observableArrayList();

        if (get_numberofunits(adm_number) == StudentsClass.get_numbunits(course)) {

            String path = OpenSaveFileChooser();

            if (!path.equals("null")) {

                try {
                    String query = "SELECT * FROM"
                            + " (SELECT Adm_Number,Exam_Name,Exam_Score,Unit_Name,Year,Syear FROM exam_2017 "
                            + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                            + "LEFT JOIN"
                            + "(SELECT Adm_Number As Admno,Exam_Name Ex_Name,Exam_Score As Ex_Score,Unit_Name As U_Name,Year As Ex_Year,Syear As Std_Year FROM exam_2017 "
                            + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'END TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                            + "ON Adm_Number = Admno  AND Unit_Name = U_Name AND Year = Ex_Year AND Syear = Std_Year";

                    Connection conn = sqlDataBaseConnection.sqliteconnect();
                    Statement st = conn.createStatement();
                    ResultSet rst = st.executeQuery(query);

                    int count = 1;
                    while (rst.next()) {

                        String course_module = rst.getString("Unit_Name");
                        String mid = rst.getString("Exam_Score");
                        String end = rst.getString("Ex_Score");
                        String end_totals = "**";
                        String unitremark = "**";
                        String initials = "**";

                        if (mid != null && end != null) {

                            int totals = Integer.parseInt(mid) + Integer.parseInt(end);
                            end_totals = Integer.toString(totals);
                            unitremark = getComment(totals);
                            initials = Functions.get_initials(course_module, std_year, course);

                            examone_scores.add(rst.getString("Unit_Name") + "-" + mid
                                    + "-" + end + "-" + totals + "-" + initials);
                        }

                        if (mid == null) {

                            mid = "**";
                        }

                        if (end == null) {

                            end = "**";
                        }

                        count++;
                    }

                    boolean result = Singlereportproducer.createPdfreport(adm_number, std_name, course, term, examone_scores, path, exam_year);

                    if (result) {

                        openDialog_processcomplete();

                    } else {

                        String message = "Sorry.. Failed To Create Report Form";
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        icon.setGlyphStyle("-fx-fill:orange");
                        openDialog_three(icon, message);
                    }

                    conn.close();
                } catch (SQLException ex) {

                    System.out.println("Error " + ex);
                }

            } else {

                String message = "Location Was Not Set, Process Cancelled";
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                icon.setGlyphStyle("-fx-fill:orange");
                openDialog_three(icon, message);

            }

        } else {

            String message = "Sorry The Report Is Incomplete, \n"
                    + " Have Missing Marks!!!";
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphStyle("-fx-fill:orange");
            openDialog_three(icon, message);
        }

    }

    public void close() {

        parent.getTabs().remove(tab);

    }

    public void getData() {

        ObservableList<ExaminationFile_ReportFormClass> data = FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM"
                    + " (SELECT Adm_Number,Exam_Name,Exam_Score,Unit_Name,Year,Syear FROM exam_2017 "
                    + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                    + "LEFT JOIN"
                    + "(SELECT Adm_Number As Admno,Exam_Name Ex_Name,Exam_Score As Ex_Score,Unit_Name As U_Name,Year As Ex_Year,Syear As Std_Year FROM exam_2017 "
                    + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'END TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                    + "ON Adm_Number = Admno  AND Unit_Name = U_Name AND Year = Ex_Year AND Syear = Std_Year";

            Connection conn = sqlDataBaseConnection.sqliteconnect();
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            int count = 1;
            while (rst.next()) {

                String course_module = rst.getString("Unit_Name");
                String mid = rst.getString("Exam_Score");
                String end = rst.getString("Ex_Score");
                String end_totals = "**";
                String unitremark = "**";
                String initials = "**";

                if (mid != null && end != null) {

                    int totals = Integer.parseInt(mid) + Integer.parseInt(end);
                    end_totals = Integer.toString(totals);
                    unitremark = getComment(totals);
                    initials = Functions.get_initials(course_module, std_year, course);
                }

                if (mid == null) {

                    mid = "**";
                }

                if (end == null) {

                    end = "**";
                }

                data.add(new ExaminationFile_ReportFormClass(count, course_module, mid, end, end_totals, unitremark, initials));
                count++;
            }

            table.setItems(data);
            conn.close();

        } catch (SQLException ex) {

            System.out.println("Error " + ex);
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

    public void openDialog() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFile_ReportFormController.class.getResource("ReportUpdaterForm.fxml"));
            StackPane pane = loader.load();
            ReportUpdaterFormController cc = loader.getController();
            cc.setLbl_infor1(lbl_infor);
            cc.setAdm(adm_number);
            cc.setCourse(course);
            cc.setExam_year(exam_year);
            cc.setStd_year(std_year);
            cc.setTerm(term);
            cc.setCc(exam_file_report_cc);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void openDialog_two() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFile_ReportFormController.class.getResource("ReportUpdaterEditForm.fxml"));
            StackPane pane = loader.load();
            ReportUpdateEditFormController cc = loader.getController();
            cc.setLbl_infor1(lbl_infor);
            cc.setAdm(adm_number);
            cc.setCourse(course);
            cc.setExam_year(exam_year);
            cc.setStd_year(std_year);
            cc.setTerm(term);
            cc.setUnit_name(table.getSelectionModel().getSelectedItem().getCourse());
            cc.setCc(exam_file_report_cc);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void openDialog_three(FontAwesomeIconView icon, String message) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            icon.setGlyphSize(250);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFile_ReportFormController.class.getResource("InforDisplayer.fxml"));
            StackPane pane = loader.load();
            InforDisplayerController cc = loader.getController();
            cc.getLbl_iconholder().setGraphic(icon);
            cc.getLbl_infor().setText(message);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
        lbl_stdname.setText(std_name);
    }

    public String getAdm_number() {
        return adm_number;
    }

    public void setAdm_number(String adm_number) {
        this.adm_number = adm_number;
        String regcode = StudentsClass.getStdregcode(adm_number);
        lbl_adm.setText(regcode + "/" + adm_number);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
        lbl_coursename.setText(course);
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
        lbl_term.setText(term);
        lbl_report_title.setText("END OF " + term.toUpperCase() + " REPORT FORM");
    }

    public String getOveral_results() {
        return overal_results;
    }

    public void setOveral_results(String overal_results) {
        this.overal_results = overal_results;
    }

    public ResultSet getResultset() {
        return resultset;
    }

    public void setResultset(ResultSet resultset) {
        this.resultset = resultset;
    }

    public JFXTabPane getParent() {
        return parent;
    }

    public void setParent(JFXTabPane parent) {
        this.parent = parent;
    }

    public Tab getTab() {
        return tab;
    }

    public void setTab(Tab tab) {
        this.tab = tab;
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
    }

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
    }

    public ExaminationFile_ReportFormController getExam_file_report_cc() {
        return exam_file_report_cc;
    }

    public void setExam_file_report_cc(ExaminationFile_ReportFormController exam_file_report_cc) {
        this.exam_file_report_cc = exam_file_report_cc;
    }

    public Button getBtn_print() {
        return btn_print;
    }

    public void setBtn_print(Button btn_print) {
        this.btn_print = btn_print;
    }

    public Button getBtn_close() {
        return btn_close;
    }

    public void setBtn_close(Button btn_close) {
        this.btn_close = btn_close;
    }

    public ExaminationFile_reportdetailsController getExamreportdtails() {
        return examreportdtails;
    }

    public void setExamreportdtails(ExaminationFile_reportdetailsController examreportdtails) {
        this.examreportdtails = examreportdtails;
    }

    public int get_numberofunits(String adm_number) {

        String query = "SELECT COUNT(Unit_Name) As Units FROM"
                + " (SELECT Adm_Number,Exam_Name,Exam_Score,Unit_Name,Year,Syear FROM exam_2017 "
                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                + "JOIN"
                + "(SELECT Adm_Number As Admno,Exam_Name Ex_Name,Exam_Score As Ex_Score,Unit_Name As U_Name,Year As Ex_Year,Syear As Std_Year FROM exam_2017 "
                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'END TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                + "ON Adm_Number = Admno  AND Unit_Name = U_Name AND Year = Ex_Year AND Syear = Std_Year";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        int count = 0;
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {

                count = rst.getInt("Units");
            }
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return count;
    }

    public String OpenSaveFileChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        String path = "null";
        Stage primary = (Stage) btn_close.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        if (file != null) {

            path = file.getAbsolutePath();

        }
        return path;

    }

}
