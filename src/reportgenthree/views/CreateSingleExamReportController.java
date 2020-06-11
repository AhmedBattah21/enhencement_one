/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import AppFuctions.Functions;
import AppFuctions.Singlereportproducer;
import AppFuctions.StudentsClass;
import com.jfoenix.controls.JFXTextField;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import reportgenthree.ExaminationReportsViews.ExaminationFile_ReportFormController;
import reportgenthree.ExaminationReportsViews.InforDisplayerController;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class CreateSingleExamReportController implements Initializable {

    @FXML
    private StackPane mypane;

    @FXML
    private JFXTextField txt_regno;

    @FXML
    private Label lbl_stdname;

    @FXML
    private JFXComboBox cmb_term;

    @FXML
    private JFXComboBox cmb_year;

    @FXML
    private JFXComboBox cmb_stdyear;

    @FXML
    private Label lbl_reportname;

    @FXML
    private JFXButton btn_create;

    @FXML
    private JFXButton btn_cancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        CoursesClass.populateComboBoxClasses(cmb_term, "SemisterName", "Semisters");

        ObservableList groups = FXCollections.observableArrayList();
        groups.addAll("2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013");
        cmb_year.setItems(groups);
        String year = Integer.toString(LocalDate.now().getYear());
        cmb_year.setValue(year);

        ObservableList<String> study_year = FXCollections.observableArrayList();

        study_year.addAll("First Year", "Second Year");
        cmb_stdyear.setItems(study_year);
    }

    public void create() {

        String regno = txt_regno.getText();

        if (!cmb_year.getSelectionModel().isEmpty()) {

            String year = cmb_year.getSelectionModel().getSelectedItem().toString();

            if (!cmb_stdyear.getSelectionModel().isEmpty()) {

                String std_year = cmb_stdyear.getSelectionModel().getSelectedItem().toString();

                if (!regno.equals("")) {

                    if (!cmb_term.getSelectionModel().isEmpty()) {

                        String term = cmb_term.getSelectionModel().getSelectedItem().toString();
                        String course = AppFuctions.CoursesClass.getStudentCourse(regno);

                        String path = OpenSaveFileChooser();

                        if (get_numberofunits(regno) == StudentsClass.get_numbunits(course)) {

                            ObservableList examone_scores = FXCollections.observableArrayList();

                            String query = "SELECT * FROM"
                                    + " (SELECT Adm_Number,Exam_Name,Exam_Score,Unit_Name,Year,Syear FROM exam_2017 "
                                    + "WHERE Adm_Number = '" + regno + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' AND Year = '" + year + "' AND Syear = '" + std_year + "')"
                                    + "LEFT JOIN"
                                    + "(SELECT Adm_Number As Admno,Exam_Name Ex_Name,Exam_Score As Ex_Score,Unit_Name As U_Name,Year As Ex_Year,Syear As Std_Year FROM exam_2017 "
                                    + "WHERE Adm_Number = '" + regno + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'END TERM' AND Year = '" + year + "' AND Syear = '" + std_year + "')"
                                    + "ON Adm_Number = Admno  AND Unit_Name = U_Name AND Year = Ex_Year AND Syear = Std_Year";

                            Connection conn = sqlDataBaseConnection.sqliteconnect();
                            try {

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

                                String stdname = AppFuctions.StudentsClass.getStdName(regno);
                                boolean result = Singlereportproducer.createPdfreport(regno, stdname, course, term, examone_scores, path, year);

                                if (result) {

                                    openDialog_processcomplete();

                                } else {

                                    String message = "Soory.. Failed To Create Report Form";
                                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                    icon.setGlyphStyle("-fx-fill:orange");
                                    openDialog_three(icon, message);
                                }

                                conn.close();

                            } catch (SQLException exc) {

                                System.out.println("" + exc);
                            }

                        } else {

                            String message = "Sorry Some Marks Are Missing!!";
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                            icon.setGlyphStyle("-fx-fill:orange");
                            openDialog_three(icon, message);
                        }

                    } else {

                        String message = "Sorry.. Select Term";
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        icon.setGlyphStyle("-fx-fill:orange");
                        openDialog_three(icon, message);

                    }

                } else {

                    String message = "Enter Student Registration Number";
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    icon.setGlyphStyle("-fx-fill:orange");
                    openDialog_three(icon, message);

                }

            } else {

                String message = "Select Student Year Of Study";
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                icon.setGlyphStyle("-fx-fill:orange");
                openDialog_three(icon, message);

            }

        } else {

            String message = "Sorry.. Please Select Year";
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphStyle("-fx-fill:orange");
            openDialog_three(icon, message);

        }

    }

    public void set_name() {

        String regno = txt_regno.getText();
        lbl_stdname.setText(AppFuctions.StudentsClass.getStdName(regno));
    }

    public void set_repotname() {

        String term = cmb_term.getSelectionModel().getSelectedItem().toString();
        if (term.equals("Term Three")) {

            lbl_reportname.setText("END STAGE REPORT FORM");
        } else {

            lbl_reportname.setText(term.toUpperCase() + " REPORT FORM");

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

    public int get_numberofunits(String adm_number) {

        String term = cmb_term.getSelectionModel().getSelectedItem().toString();
        String year = cmb_year.getSelectionModel().getSelectedItem().toString();
        String std_year = cmb_stdyear.getSelectionModel().getSelectedItem().toString();

        String query = "SELECT COUNT(Unit_Name) As Units FROM"
                + " (SELECT Adm_Number,Exam_Name,Exam_Score,Unit_Name,Year,Syear FROM exam_2017 "
                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' AND Year = '" + year + "' AND Syear = '" + std_year + "')"
                + "JOIN"
                + "(SELECT Adm_Number As Admno,Exam_Name Ex_Name,Exam_Score As Ex_Score,Unit_Name As U_Name,Year As Ex_Year,Syear As Std_Year FROM exam_2017 "
                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'END TERM' AND Year = '" + year + "' AND Syear = '" + std_year + "')"
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

        Stage primary = (Stage) btn_create.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        String path = file.getAbsolutePath();
        return path;

    }

    public void openDialog(String message) {

        JFXDialogLayout content = new JFXDialogLayout();
        Label mylabel = new Label("Single Report Tool");

        content.setHeading(mylabel);
        content.setAlignment(Pos.CENTER);
        content.setBody(new Text(message));

        JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonCancel = new JFXButton("Exit");
        buttonCancel.setStyle("-fx-border-color:seagreen;-fx-border-width:1");

        buttonCancel.setOnAction(e -> dlog.close());

        content.setActions(buttonCancel);
        content.autosize();
        content.setPadding(new Insets(6, 0, 6, 0));
        content.setLayoutX(50);
        content.setLayoutY(50);

        dlog.show();

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

}
