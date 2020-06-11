/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;

import AppFuctions.Functions;
import AppFuctions.MutlipleReportproducer;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;
import reportgenthree.views.ProcessCompleteMultipleReportsController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationFile_reportdetailsController implements Initializable {

    @FXML
    private Label lbl_rname;

    @FXML
    private Label lbl_stdname;

    @FXML
    private Label lbl_coursename;

    @FXML
    private Label lbl_examyear;

    @FXML
    private Label lbl_stdyear;

    @FXML
    private Label lbl_status;

    @FXML
    private Label lbl_graphic;

    @FXML
    private FontAwesomeIconView background_image;
    private ExaminationFile_reportdetailsController ExaminationFile_reportdetails_cc = null;

    JFXTabPane parentTab = null;

    private String exam_year;
    private String std_name;
    private String course_name;
    private String std_adm;
    private String std_year;
    private String term;
    private String status;

    private Tab main_tab = null;

    @FXML
    private StackPane mypane;

    @FXML
    private StackPane parent_stackpane;

    VBox box = new VBox();
    InforDisplayerController cc_inford = null;

    private Executor exec;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        VBox.setVgrow(box, Priority.ALWAYS);
        box.setSpacing(1);

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

    }

    public void get_data() {

        parent_stackpane = new StackPane();
        get_menu();

        try {

            if (!std_adm.equals("All Students Reports")) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ExaminationFile_reportdetailsController.class.getResource("ExaminationFile_ReportForm.fxml"));
                StackPane pane = loader.load();

                ExaminationFile_ReportFormController cc = loader.getController();

                cc.setAdm_number(std_adm);
                cc.setCourse(course_name);
                cc.setStd_name(std_name);
                cc.setTerm(term);
                cc.setStd_year(std_year);
                cc.setExam_year(exam_year);
                cc.setParent(parentTab);
                cc.setExam_file_report_cc(cc);

                cc.getData();

                Tab tab = new Tab("Report Form " + std_adm);
                ScrollPane spane = new ScrollPane(pane);
                spane.setFitToHeight(true);
                spane.setFitToWidth(true);
                spane.setPannable(true);
                tab.setContent(spane);
                cc.setTab(tab);
                parentTab.getTabs().add(tab);
                parentTab.getSelectionModel().select(tab);

            } else {

                String query = "SELECT * FROM exam_2017 WHERE"
                        + " Std_Course = '" + course_name + "' AND Syear = '" + std_year + "' "
                        + "AND Year = '" + exam_year + "' AND Exam_Term = '" + term + "' GROUP BY Adm_Number";
                Connection conn = sqlDataBaseConnection.sqliteconnect();

                try {

//                  
                    searchWidgets_lists();

                    Tab tab = new Tab("Report Form " + std_adm);

                    ScrollPane spane = new ScrollPane(box);
                    spane.setFitToHeight(true);
                    spane.setFitToWidth(true);
                    spane.setPannable(true);
                    parent_stackpane.getChildren().add(spane);

                    tab.setContent(parent_stackpane);

                    parentTab.getTabs().add(tab);
                    setMain_tab(tab);
                    parentTab.getSelectionModel().select(tab);
                } catch (Exception exc) {

                    System.out.println("Error " + exc);
                }

            }

        } catch (IOException ex) {

            System.out.println("Error " + ex);

        }

    }

    @FXML
    public void searchWidgets_lists() {

        set_processing();

        Task<List<StackPane>> widgetSearchTask = new Task<List<StackPane>>() {

            @Override
            protected List call() throws Exception {

                return get_windows();

            }
        };

        widgetSearchTask.setOnFailed(e -> {

            widgetSearchTask.getException().printStackTrace();

        });

        widgetSearchTask.setOnSucceeded(e -> {

            if (!widgetSearchTask.getValue().isEmpty()) {

                Platform.runLater(() -> {
                    startTask(widgetSearchTask.getValue());

                });

            }
        });

        // run the task using a thread from the thread pool:
        exec.execute(widgetSearchTask);

    }

    public void runTask(List<StackPane> items) {

        final List<StackPane> stackpanes = items;

        Platform.runLater(() -> {

            box.getChildren().clear();

            box.getChildren().add(get_title());

        });

        for (int n = 0; n < items.size(); n++) {

            final int value = n;

            try {

                Platform.runLater(() -> {

                    box.getChildren().add(stackpanes.get(value));

                });

                Thread.sleep(100);

            } catch (InterruptedException exc) {

                System.out.println("" + exc);
            }
        }

    }

    public void startTask(List items) {
        // Create a Runnable
        Runnable task = () -> {
            runTask(items);
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    public StackPane set_processing() {

        box.getChildren().clear();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ExaminationFile_reportdetailsController.class.getResource("Processing_window.fxml"));
        StackPane pane = null;

        try {
            pane = loader.load();
            box.getChildren().add(pane);

        } catch (IOException exc) {

            System.out.println("" + exc);

        }

        return pane;

    }

    public List<StackPane> get_windows() {

        List<StackPane> items = new ArrayList<>();

        String query = "SELECT * FROM exam_2017 WHERE"
                + " Std_Course = '" + course_name + "' AND Syear = '" + std_year + "' "
                + "AND Year = '" + exam_year + "' AND Exam_Term = '" + term + "' GROUP BY Adm_Number";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (term.equals("Term Three")) {

            } else {

                while (rst.next()) {

                    String course = rst.getString("Std_Course");
                    String unit_name = rst.getString("Unit_Name");
                    String adm = rst.getString("Adm_Number");
                    String std_name = AppFuctions.StudentsClass.getStdName(adm);

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ExaminationFile_reportdetailsController.class.getResource("ExaminationFile_ReportForm.fxml"));
                    StackPane pane = loader.load();

                    ExaminationFile_ReportFormController cc = loader.getController();

                    cc.setAdm_number(adm);
                    cc.setCourse(course);
                    cc.setStd_name(std_name);
                    cc.setTerm(term);
                    cc.setStd_year(std_year);
                    cc.setExam_year(exam_year);
                    cc.setParent(parentTab);
                    cc.setExam_file_report_cc(cc);
                    cc.getBtn_close().setOnAction(e -> remove_form(pane));
                    cc.getData();
                    items.add(pane);

                }

            }
        } catch (SQLException | IOException exc) {

            System.out.println("Error " + exc);

        }
        return items;
    }

    public void remove_tab() {

        parentTab.getTabs().remove(main_tab);

    }

    public void remove_form(StackPane pane) {

        box.getChildren().remove(pane);

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
        lbl_examyear.setText(exam_year);
    }

    public String getStd_name() {
        return std_name;
    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
        lbl_stdname.setText(std_name);
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
        lbl_coursename.setText(course_name);
    }

    public String getStd_adm() {
        return std_adm;
    }

    public void setStd_adm(String std_adm) {
        this.std_adm = std_adm;
        lbl_rname.setText(std_adm);
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
        lbl_stdyear.setText(std_year);
    }

    public JFXTabPane getParentTab() {
        return parentTab;
    }

    public void setParentTab(JFXTabPane parentTab) {
        this.parentTab = parentTab;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        lbl_status.setText(status);
    }

    public FontAwesomeIconView getBackground_image() {
        return background_image;
    }

    public void setBackground_image(FontAwesomeIconView background_image) {
        this.background_image = background_image;
    }

    public StackPane getMypane() {
        return mypane;
    }

    public void setMypane(StackPane mypane) {
        this.mypane = mypane;

    }

    public Label getLbl_graphic() {
        return lbl_graphic;
    }

    public void setLbl_graphic(Label lbl_graphic) {
        this.lbl_graphic = lbl_graphic;
    }

    public Tab getMain_tab() {
        return main_tab;
    }

    public void setMain_tab(Tab main_tab) {
        this.main_tab = main_tab;
    }

    public ExaminationFile_reportdetailsController getExaminationFile_reportdetails_cc() {
        return ExaminationFile_reportdetails_cc;
    }

    public void setExaminationFile_reportdetails_cc(ExaminationFile_reportdetailsController ExaminationFile_reportdetails_cc) {
        this.ExaminationFile_reportdetails_cc = ExaminationFile_reportdetails_cc;
    }

    public AnchorPane get_title() {

        AnchorPane pane = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFile_reportdetailsController.class.getResource("ExaminationReportFile_title.fxml"));
            pane = loader.load();
            ExaminationReportFile_titleController cc = loader.getController();

            cc.setCc(this);

            cc.getBtn_print().setOnAction(e -> multiple_report());

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_reportdetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return pane;

    }

    public void get_menu() {

        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Students Academic Report Forms");
        SeparatorMenuItem sp1 = new SeparatorMenuItem();
        MenuItem item2 = new MenuItem("Print All Report Forms");
        SeparatorMenuItem sp2 = new SeparatorMenuItem();
        MenuItem item3 = new MenuItem("Refresh Tab/Reload");
        SeparatorMenuItem sp3 = new SeparatorMenuItem();
        MenuItem item4 = new MenuItem("Close All Reports");
        menu.getItems().addAll(item1, sp1, item2, sp2, item3, sp3, item4);

        box.setOnContextMenuRequested((ContextMenuEvent eve) -> {
            menu.show(box, eve.getScreenX(), eve.getScreenY());
        });

        item2.setOnAction((ActionEvent eve) -> {

            multiple_report();

        });

        item4.setOnAction((ActionEvent eve) -> {

            parentTab.getTabs().remove(main_tab);

        });

    }

    public void multiple_report() {

        String path = OpenSaveFileChooser();

        if (!path.equals("null")) {

            String query = "SELECT * FROM exam_2017 WHERE"
                    + " Std_Course = '" + course_name + "' AND Syear = '" + std_year + "' "
                    + "AND Year = '" + exam_year + "' AND Exam_Term = '" + term + "' GROUP BY Adm_Number";

            Connection conn = sqlDataBaseConnection.sqliteconnect();

            try {

                Statement st = conn.createStatement();
                ResultSet rst = st.executeQuery(query);

                int numberofreports = 0;
                int incomplete_reports = 0;

                while (rst.next()) {

                    String adm_number = rst.getString("Adm_Number");
                    ObservableList examone_scores = FXCollections.observableArrayList();

                    if (get_numberofunits(adm_number) == StudentsClass.get_numbunits(course_name)) {

                        String query_two = "SELECT * FROM"
                                + " (SELECT Adm_Number,Exam_Name,Exam_Score,Unit_Name,Year,Syear FROM exam_2017 "
                                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                                + "LEFT JOIN"
                                + "(SELECT Adm_Number As Admno,Exam_Name Ex_Name,Exam_Score As Ex_Score,Unit_Name As U_Name,Year As Ex_Year,Syear As Std_Year FROM exam_2017 "
                                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'END TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                                + "ON Adm_Number = Admno  AND Unit_Name = U_Name AND Year = Ex_Year AND Syear = Std_Year";

                        Statement st_2 = conn.createStatement();
                        ResultSet rst_2 = st_2.executeQuery(query_two);

                        int count = 1;
                        while (rst_2.next()) {

                            String course_module = rst_2.getString("Unit_Name");
                            String mid = rst_2.getString("Exam_Score");
                            String end = rst_2.getString("Ex_Score");
                            String end_totals = "**";
                            String unitremark = "**";
                            String initials = "**";

                            if (mid != null && end != null) {

                                int totals = Integer.parseInt(mid) + Integer.parseInt(end);
                                end_totals = Integer.toString(totals);
                                unitremark = getComment(totals);
                                initials = Functions.get_initials(course_module, std_year, course_name);

                                examone_scores.add(rst_2.getString("Unit_Name") + "-" + mid
                                        + "-" + end + "-" + end_totals + "-" + initials);
                            }

                            if (mid == null) {

                                mid = "**";
                            }

                            if (end == null) {

                                end = "**";
                            }

                            count++;

                        }

                        MutlipleReportproducer.createPdfreport(adm_number, std_name, course_name, term, examone_scores, path, exam_year);

                        numberofreports++;

                    } else {

                        incomplete_reports++;
                    }

                }

                String created = "Report Forms Created  " + numberofreports;
                String missing = "Report Forms With Missing Marks " + incomplete_reports;

                openDialog_processcomplete(missing, created);

                conn.close();

            } catch (SQLException exc) {

                System.out.println("Error " + exc);
            }

        } else {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            String message = "Location Is Not Set \nMake Sure You Select a destination folder for the reports";
            icon.setGlyphStyle("-fx-fill:red");

            openDialog_three(icon, message);

        }

    }

    public void openDialog_processcomplete(String missing, String created) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/ProcessCompleteMultipleReports.fxml"));
            AnchorPane pane = loader.load();
            ProcessCompleteMultipleReportsController cc = loader.getController();
            cc.getLbl_missingmarks().setText(missing);
            cc.getLbl_numberofreports().setText(created);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(parent_stackpane, content, JFXDialog.DialogTransition.TOP);

            cc.setDlog(dlog);
            dlog.show();

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String openDialog_three(FontAwesomeIconView icon, String message) {

        String word = "erick";
        try {
            JFXDialogLayout content = new JFXDialogLayout();
            icon.setGlyphSize(250);

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFile_reportdetailsController.class.getResource("InforDisplayer.fxml"));
            StackPane pane = loader.load();
            cc_inford = loader.getController();
            cc_inford.getLbl_iconholder().setGraphic(icon);
            cc_inford.getLbl_infor().setText(message);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(parent_stackpane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return word;
    }

    public String OpenSaveFileChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();
        String path = "null";
        Stage primary = (Stage) lbl_stdname.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        if (file != null) {

            path = file.getAbsolutePath();

        }
        return path;

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

}
