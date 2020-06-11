package reportgenthree.ExaminationReportsViews;

import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationFile_percourseController implements Initializable {

    @FXML
    private Label lbl_course;

    @FXML
    private Label lbl_stdyear;

    @FXML
    private Label lbl_term;

    @FXML
    private Label lbl_examyear;

    JFXTabPane parentTab = null;

    private StackPane parent_pane;

    String course;
    String std_year;
    String term;
    String exam_year;

    String style = "-fx-border-color: white;"
            + "     -fx-background-color: rgb(255.0, 255.0, 255.0);"
            + "    -fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 6.0, 0.5, 0.0, 1.5);"
            + "    -fx-padding: 5 5 5 5;"
            + "    -fx-border-radius: 5 5 5 5;"
            + "    -fx-background-radius: 5 5 5 5;";

    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
    ScrollPane spane = new ScrollPane();
    VBox vbox = new VBox();

    StackPane mypane = new StackPane();

    Tab tab = new Tab();

    private Executor exec;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        icon.setGlyphSize(245);

        spane.setFitToHeight(true);
        spane.setFitToWidth(true);

        VBox.setVgrow(vbox, Priority.ALWAYS);
        vbox.setStyle(style);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setSpacing(30);

        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

    }

    public void get_newvbox() {

        vbox = new VBox();
        VBox.setVgrow(vbox, Priority.ALWAYS);
        vbox.setStyle(style);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setSpacing(30);

    }

    @FXML
    void open_examinations(MouseEvent event) {
        String query = "";
        //settings......................
        tab = new Tab(exam_year + "  " + term + " @ Reports");
        tab.setClosable(true);

        vbox.getChildren().clear();
        get_newvbox();

        get_menu();

        HBox hbox = new HBox();
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        //-------------------------------------------------
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ExaminationFile_reportdetailsController cc = null;

        if (course.equals("All Students File")) {

            mypane.getChildren().add(vbox);
            vbox.getChildren().clear();
            searchWidgets_lists();
            spane.setContent(mypane);
            tab.setContent(spane);
            parentTab.getTabs().add(tab);
            parentTab.getSelectionModel().select(tab);

        } else {

            query = "SELECT * FROM exam_2017 WHERE"
                    + " Std_Course = '" + course + "' AND Syear = '" + std_year + "' "
                    + "AND Year = '" + exam_year + "' AND Exam_Term = '" + term + "' GROUP BY Adm_Number";

            String course = "";

            try {

                Statement st = conn.createStatement();

                ResultSet rst = st.executeQuery(query);
                int count = 0;
                int sum = 0;

                while (rst.next()) {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                    StackPane stackpane = loader.load();
                    cc = loader.getController();

                    course = rst.getString("Std_Course");
                    String unit_name = rst.getString("Unit_Name");
                    String adm = rst.getString("Adm_Number");

                    ObservableList counts = get_numberofunits(adm);
                    int units = (int) counts.get(0);

                    int mid = (int) counts.get(1);
                    int end = (int) counts.get(2);
                    String status = "T." + units + ",M." + mid + " E." + end;

                    sum = sum + units;

                    if (units == StudentsClass.get_numbunits(course)) {

                        cc.setStatus("Complete Report Form (" + status + ")");
                        icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                        icon.setGlyphSize(245);
                        icon.setGlyphStyle("-fx-fill:#7d0d82;-fx-opacity: 0.5;");
                        cc.getLbl_graphic().setGraphic(icon);
                    } else {

                        cc.setStatus("InComplete Report Form (" + status + ")");
                        icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                        icon.setGlyphSize(245);
                        icon.setGlyphStyle("-fx-fill:pink");
                        cc.getLbl_graphic().setGraphic(icon);
                    }

                    cc.setParentTab(parentTab);
                    cc.setCourse_name(course);
                    cc.setExam_year(exam_year);
                    cc.setTerm(term);

                    cc.setStd_adm(adm);
                    cc.setStd_name(AppFuctions.StudentsClass.getStdName(adm));
                    cc.setStd_year(std_year);
                    hbox.getChildren().add(stackpane);
                    count++;

                    if (count == 7) {

                        vbox.getChildren().add(hbox);
                        hbox = new HBox();
                        HBox.setHgrow(hbox, Priority.ALWAYS);
                        hbox.setSpacing(10);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        count = 0;
                    }

                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                StackPane stackpane = loader.load();
                cc = loader.getController();

                cc.setParentTab(parentTab);
                cc.setCourse_name(course);
                cc.setExam_year(exam_year);
                cc.setTerm(term);
                cc.setStd_name("All Students");
                cc.setStd_adm("All Students Reports");
                cc.setStd_year(std_year);
                cc.setStatus("Total Records " + sum);
                hbox.getChildren().add(stackpane);

                vbox.getChildren().add(hbox);
                mypane.getChildren().add(vbox);
                spane.setContent(mypane);
                tab.setContent(spane);
                parentTab.getTabs().add(tab);
                parentTab.getSelectionModel().select(tab);

                conn.close();

            } catch (SQLException | IOException exc) {

                System.out.println("Error 2 " + exc);
            }

        }

    }

    public ObservableList<Integer> get_numberofunits(String adm_number) {

        String query = "SELECT COUNT(Unit_Name) As Units FROM"
                + " (SELECT Adm_Number,Exam_Name,Exam_Score,Unit_Name,Year,Syear FROM exam_2017 "
                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                + "JOIN"
                + "(SELECT Adm_Number As Admno,Exam_Name Ex_Name,Exam_Score As Ex_Score,Unit_Name As U_Name,Year As Ex_Year,Syear As Std_Year FROM exam_2017 "
                + "WHERE Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'END TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "')"
                + "ON Adm_Number = Admno  AND Unit_Name = U_Name AND Year = Ex_Year AND Syear = Std_Year";

        String query_one = "SELECT COUNT(Adm_Number) As One FROM exam_2017 WHERE"
                + " Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' AND Exam_Name = 'MID TERM' "
                + "AND Year = '" + exam_year + "' AND Syear = '" + std_year + "'";

        String query_two = "SELECT COUNT(Adm_Number) As Two FROM exam_2017 WHERE "
                + "Adm_Number = '" + adm_number + "' AND Exam_Term = '" + term + "' "
                + "AND Exam_Name = 'END TERM' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<Integer> counts = FXCollections.observableArrayList();
        int count = 0;
        int count_one = 0;
        int count_two = 0;
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                count = rst.getInt("Units");

            }

            Statement st_one = conn.createStatement();
            ResultSet rst_one = st_one.executeQuery(query_one);

            if (rst_one.next()) {

                count_one = rst_one.getInt("One");

            }

            Statement st_two = conn.createStatement();
            ResultSet rst_two = st_two.executeQuery(query_two);

            if (rst_two.next()) {

                count_two = rst_two.getInt("Two");

            }
            conn.close();
            counts.addAll(count, count_one, count_two);

        } catch (SQLException exc) {

            System.out.println("Error 3 " + exc);
        }

        return counts;
    }

    public void refresh() {

        mypane = new StackPane();

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ExaminationFile_reportdetailsController cc = null;
        String query = "";

        vbox.getChildren().clear();
        get_newvbox();

        get_menu();

        HBox hbox = new HBox();
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        if (course.equals("All Students File")) {

            vbox.getChildren().clear();
            mypane.getChildren().add(vbox);
            searchWidgets_lists();
            spane.setContent(mypane);
            tab.setContent(spane);
            //parentTab.getTabs().add(tab);
            parentTab.getSelectionModel().select(tab);

        } else {

            query = "SELECT * FROM exam_2017 WHERE"
                    + " Std_Course = '" + course + "' AND Syear = '" + std_year + "' "
                    + "AND Year = '" + exam_year + "'  AND Exam_Term = '" + term + "' GROUP BY Adm_Number";

            String course = "";

            try {

                Statement st = conn.createStatement();

                ResultSet rst = st.executeQuery(query);
                int count = 0;
                int sum = 0;

                while (rst.next()) {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                    StackPane stackpane = loader.load();
                    cc = loader.getController();

                    course = rst.getString("Std_Course");
                    String unit_name = rst.getString("Unit_Name");
                    String adm = rst.getString("Adm_Number");

                    ObservableList counts = get_numberofunits(adm);
                    int units = (int) counts.get(0);

                    int mid = (int) counts.get(1);
                    int end = (int) counts.get(2);
                    String status = "T." + units + ",M." + mid + " E." + end;
                    sum = sum + units;

                    if (units == StudentsClass.get_numbunits(course)) {

                        cc.setStatus("Complete Report Form (" + status + ")");
                        icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                        icon.setGlyphSize(245);
                        icon.setGlyphStyle("-fx-fill:#7d0d82;-fx-opacity: 0.5;");
                        cc.getLbl_graphic().setGraphic(icon);
                    } else {

                        cc.setStatus("InComplete Report Form (" + status + ")");
                        icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                        icon.setGlyphSize(245);
                        icon.setGlyphStyle("-fx-fill:pink");
                        cc.getLbl_graphic().setGraphic(icon);
                    }

                    cc.setParentTab(parentTab);
                    cc.setCourse_name(course);
                    cc.setExam_year(exam_year);
                    cc.setTerm(term);

                    cc.setStd_adm(adm);
                    cc.setStd_name(AppFuctions.StudentsClass.getStdName(adm));
                    cc.setStd_year(std_year);
                    hbox.getChildren().add(stackpane);
                    count++;

                    if (count == 7) {

                        vbox.getChildren().add(hbox);
                        hbox = new HBox();
                        HBox.setHgrow(hbox, Priority.ALWAYS);
                        hbox.setSpacing(10);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        count = 0;
                    }

                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                StackPane stackpane = loader.load();
                cc = loader.getController();

                cc.setParentTab(parentTab);
                cc.setCourse_name(course);
                cc.setExam_year(exam_year);
                cc.setTerm(term);
                cc.setStd_name("All Students");
                cc.setStd_adm("All Students Reports");
                cc.setStd_year(std_year);
                cc.setStatus("Total Records " + sum);
                hbox.getChildren().add(stackpane);

                vbox.getChildren().add(hbox);
                mypane.getChildren().add(vbox);
                spane.setContent(mypane);
                tab.setContent(spane);

                parentTab.getSelectionModel().select(tab);

                conn.close();

            } catch (SQLException | IOException exc) {

                System.out.println("Error 5 " + exc);
            }

        }

    }

    public void search(String hint) {

        mypane = new StackPane();

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ExaminationFile_reportdetailsController cc = null;
        String query = "";

        vbox.getChildren().clear();
        get_newvbox();

        get_menu();

        HBox hbox = new HBox();
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        if (course.equals("All Students File")) {

            vbox.getChildren().clear();
            mypane.getChildren().add(vbox);
            searchWidgets_lists_onsearch(hint);
            spane.setContent(mypane);
            tab.setContent(spane);

            parentTab.getSelectionModel().select(tab);

        } else {

            query = "SELECT * FROM exam_2017 WHERE"
                    + " Std_Course = '" + course + "' AND Syear = '" + std_year + "' "
                    + "AND Year = '" + exam_year + "'  AND Exam_Term = '" + term + "' GROUP BY Adm_Number";

            String course = "";

            try {

                Statement st = conn.createStatement();

                ResultSet rst = st.executeQuery(query);
                int count = 0;
                int sum = 0;

                while (rst.next()) {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                    StackPane stackpane = loader.load();
                    cc = loader.getController();

                    course = rst.getString("Std_Course");
                    String unit_name = rst.getString("Unit_Name");
                    String adm = rst.getString("Adm_Number");

                    ObservableList counts = get_numberofunits(adm);
                    int units = (int) counts.get(0);

                    int mid = (int) counts.get(1);
                    int end = (int) counts.get(2);
                    String status = "T." + units + ",M." + mid + " E." + end;
                    sum = sum + units;

                    if (units == StudentsClass.get_numbunits(course)) {

                        cc.setStatus("Complete Report Form (" + status + ")");
                        icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                        icon.setGlyphSize(245);
                        icon.setGlyphStyle("-fx-fill:#7d0d82;-fx-opacity: 0.5;");
                        cc.getLbl_graphic().setGraphic(icon);
                    } else {

                        cc.setStatus("InComplete Report Form (" + status + ")");
                        icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                        icon.setGlyphSize(245);
                        icon.setGlyphStyle("-fx-fill:pink");
                        cc.getLbl_graphic().setGraphic(icon);
                    }

                    cc.setParentTab(parentTab);
                    cc.setCourse_name(course);
                    cc.setExam_year(exam_year);
                    cc.setTerm(term);

                    cc.setStd_adm(adm);
                    String std_name = AppFuctions.StudentsClass.getStdName(adm);
                    cc.setStd_name(std_name);
                    cc.setStd_year(std_year);

                    if (adm.contains(hint) || std_name.contains((hint.toUpperCase().trim()))) {

                        hbox.getChildren().add(stackpane);
                        count++;

                    }

                    if (count == 7) {

                        vbox.getChildren().add(hbox);
                        hbox = new HBox();
                        HBox.setHgrow(hbox, Priority.ALWAYS);
                        hbox.setSpacing(10);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        count = 0;
                    }

                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                StackPane stackpane = loader.load();
                cc = loader.getController();

                cc.setParentTab(parentTab);
                cc.setCourse_name(course);
                cc.setExam_year(exam_year);
                cc.setTerm(term);
                cc.setStd_name("All Students");
                cc.setStd_adm("All Students Reports");
                cc.setStd_year(std_year);
                cc.setStatus("Total Records " + sum);
                hbox.getChildren().add(stackpane);

                vbox.getChildren().add(hbox);
                mypane.getChildren().add(vbox);
                spane.setContent(mypane);
                tab.setContent(spane);

                parentTab.getSelectionModel().select(tab);

                conn.close();

            } catch (SQLException | IOException exc) {

                System.out.println("Error 5 " + exc);
            }

        }

    }

    public void openDialog_Searchdialog(String message) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFile_percourseController.class.getResource("ReportSearchForm.fxml"));
            StackPane pane = loader.load();
            ReportSearchFormController cc = loader.getController();
            cc.setCc(this);
            cc.getLbl_infor().setText(message);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(parent_pane, content, JFXDialog.DialogTransition.TOP);
            cc.setDialog(dlog);

            dlog.show();

        } catch (IOException ex) {

            System.out.println(ex);
        }

    }

    public void get_menu() {

        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Students Examination Reports");
        SeparatorMenuItem sp1 = new SeparatorMenuItem();
        MenuItem item2 = new MenuItem("Close This Tab");
        SeparatorMenuItem sp2 = new SeparatorMenuItem();
        MenuItem item3 = new MenuItem("Refresh Tab/Reload");

        SeparatorMenuItem sp3 = new SeparatorMenuItem();
        MenuItem item4 = new MenuItem("Search Report Form");
        menu.getItems().addAll(item1, sp1, item2, sp2, item3, sp3, item4);

        vbox.setOnContextMenuRequested((ContextMenuEvent eve) -> {
            menu.show(vbox, eve.getScreenX(), eve.getScreenY());
        });

        item2.setOnAction((ActionEvent eve) -> {

            parentTab.getTabs().remove(tab);

        });

        item3.setOnAction((ActionEvent eve) -> {

            refresh();

        });

        item4.setOnAction((ActionEvent eve) -> {

            openDialog_Searchdialog("Enter Name or Adm Number And Click Search..");

        });

    }

    public String getCourse() {
        return course;

    }

    public void setCourse(String course) {
        this.course = course;
        lbl_course.setText(course + " Report Forms");
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
        lbl_stdyear.setText(std_year);
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
        lbl_term.setText(term);
    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
        lbl_examyear.setText(exam_year);
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

    public StackPane getParent_pane() {
        return parent_pane;
    }

    public void setParent_pane(StackPane parent_pane) {
        this.parent_pane = parent_pane;
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

        exec.execute(widgetSearchTask);

    }

    @FXML
    public void searchWidgets_lists_onsearch(String hint) {

        set_processing();

        Task<List<StackPane>> widgetSearchTask = new Task<List<StackPane>>() {

            @Override
            protected List call() throws Exception {

                return get_windows_onsearch(hint);

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

            }else{
            
                
                vbox.getChildren().clear();
                openDialog_Searchdialog("No Report Form Matches Your Search Hint...");
            
            }
        });

        // run the task using a thread from the thread pool:
        exec.execute(widgetSearchTask);

    }

    public void runTask(List<HBox> items) {

        final List<HBox> hboxes = items;

        Platform.runLater(() -> {

            vbox.getChildren().clear();

        });

        for (int n = 0; n < items.size(); n++) {

            final int value = n;

            try {

                Platform.runLater(() -> {

                    vbox.getChildren().add(hboxes.get(value));

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

        vbox.getChildren().clear();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ExaminationFile_reportdetailsController.class.getResource("Processing_window.fxml"));
        StackPane pane = null;

        try {
            pane = loader.load();
            vbox.getChildren().add(pane);

        } catch (IOException exc) {

            System.out.println("" + exc);

        }

        return pane;

    }

    public List<HBox> get_windows() {

        List<HBox> items = new ArrayList<>();

        HBox hbox = new HBox();
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        String query = "SELECT * FROM exam_2017 WHERE Syear = '" + std_year + "' "
                + "AND Year = '" + exam_year + "'  AND Exam_Term = '" + term + "' GROUP BY Adm_Number";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ExaminationFile_reportdetailsController cc = null;

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);
            int count = 0;

            while (rst.next()) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                StackPane stackpane = loader.load();
                cc = loader.getController();

                String adm = rst.getString("Adm_Number");
                String std_course = StudentsClass.getStdCourse(adm);

                ObservableList counts = get_numberofunits(adm);
                int units = (int) counts.get(0);
                int mid = (int) counts.get(1);
                int end = (int) counts.get(2);
                String status = "T." + units + " M." + mid + " E." + end;

                if (units == StudentsClass.get_numbunits(std_course)) {

                    cc.setStatus("Complete Report Form (" + status + ")");
                    icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                    icon.setGlyphSize(245);
                    icon.setGlyphStyle("-fx-fill:#7d0d82;-fx-opacity: 0.5;");
                    cc.getLbl_graphic().setGraphic(icon);

                } else {

                    cc.setStatus("InComplete Report Form (" + status + ")");
                    icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                    icon.setGlyphSize(245);
                    icon.setGlyphStyle("-fx-fill:pink");
                    cc.getLbl_graphic().setGraphic(icon);
                }
                cc.setParentTab(parentTab);

                cc.setCourse_name(std_course);
                cc.setExam_year(exam_year);

                cc.setStd_adm(adm);
                cc.setStd_year(std_year);
                cc.setTerm(term);
                cc.setStd_name(AppFuctions.StudentsClass.getStdName(adm));
                cc.setExaminationFile_reportdetails_cc(cc);
                hbox.getChildren().add(stackpane);
                count++;

                if (count == 7) {

                    //vbox.getChildren().add(hbox);
                    items.add(hbox);
                    hbox = new HBox();
                    HBox.setHgrow(hbox, Priority.ALWAYS);
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    count = -1;
                }

            }

            items.add(hbox);

            conn.close();

        } catch (SQLException | IOException exc) {

            System.out.println("errror one " + exc);
        }

        return items;
    }

    public List<HBox> get_windows_onsearch(String hint) {

        List<HBox> items = new ArrayList<>();

        HBox hbox = new HBox();
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        String query = "SELECT * FROM exam_2017 WHERE Syear = '" + std_year + "' "
                + "AND Year = '" + exam_year + "'  AND Exam_Term = '" + term + "' GROUP BY Adm_Number";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ExaminationFile_reportdetailsController cc = null;

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);
            int count = 0;

            while (rst.next()) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_reportdetails.fxml"));
                StackPane stackpane = loader.load();
                cc = loader.getController();

                String adm = rst.getString("Adm_Number");
                String std_course = StudentsClass.getStdCourse(adm);

                ObservableList counts = get_numberofunits(adm);
                int units = (int) counts.get(0);
                int mid = (int) counts.get(1);
                int end = (int) counts.get(2);
                String status = "T." + units + " M." + mid + " E." + end;

                if (units == StudentsClass.get_numbunits(std_course)) {

                    cc.setStatus("Complete Report Form (" + status + ")");
                    icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                    icon.setGlyphSize(245);
                    icon.setGlyphStyle("-fx-fill:#7d0d82;-fx-opacity: 0.5;");
                    cc.getLbl_graphic().setGraphic(icon);

                } else {

                    cc.setStatus("InComplete Report Form (" + status + ")");
                    icon = new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT);
                    icon.setGlyphSize(245);
                    icon.setGlyphStyle("-fx-fill:pink");
                    cc.getLbl_graphic().setGraphic(icon);
                }
                cc.setParentTab(parentTab);

                cc.setCourse_name(std_course);
                cc.setExam_year(exam_year);

                cc.setStd_adm(adm);
                cc.setStd_year(std_year);
                cc.setTerm(term);
                String std_name = AppFuctions.StudentsClass.getStdName(adm);
                cc.setStd_name(std_name);
                cc.setExaminationFile_reportdetails_cc(cc);

                if (adm.contains(hint) || std_name.contains((hint.toUpperCase().trim()))) {

                    hbox.getChildren().add(stackpane);
                    count++;

                }

                if (count == 7) {

                    items.add(hbox);
                    hbox = new HBox();
                    HBox.setHgrow(hbox, Priority.ALWAYS);
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    count = 0;
                }

            }

            if (count >= 1) {
                
                items.add(hbox);
                
            }

            conn.close();

        } catch (SQLException | IOException exc) {

            System.out.println("errror one " + exc);
        }

        return items;
    }

}
