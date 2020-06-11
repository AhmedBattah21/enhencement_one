package reportgenthree.ExaminationViews;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
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

    String course;
    String std_year;
    String term;
    String exam_year;

    String user_group = "";
    String user_code = "";

    String style = "-fx-border-color: white;"
            + "     -fx-background-color: rgb(255.0, 255.0, 255.0);"
            + "    -fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 6.0, 0.5, 0.0, 1.5);"
            + "    -fx-padding: 5 5 5 5;"
            + "    -fx-border-radius: 5 5 5 5;"
            + "    -fx-background-radius: 5 5 5 5;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void open_examinations(MouseEvent event) {
        String query = "";
        //settings......................
        Tab tab = new Tab(exam_year + "  " + term + " @ Units ");
        tab.setClosable(true);

        ScrollPane spane = new ScrollPane();
        spane.setFitToHeight(true);
        spane.setFitToWidth(true);

        VBox vbox = new VBox();
        VBox.setVgrow(vbox, Priority.ALWAYS);
        vbox.setStyle(style);
        vbox.setPadding(new Insets(5, 5, 5, 5));
        vbox.setSpacing(30);
        HBox hbox = new HBox();
        HBox.setHgrow(hbox, Priority.ALWAYS);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER_LEFT);

        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Units Examination Files");
        SeparatorMenuItem sp1 = new SeparatorMenuItem();
        MenuItem item2 = new MenuItem("Close Units Files Tab");
        menu.getItems().addAll(item1, sp1, item2);

        vbox.setOnContextMenuRequested((ContextMenuEvent eve) -> {
            menu.show(vbox, eve.getScreenX(), eve.getScreenY());
        });

        item2.setOnAction((ActionEvent eve) -> {

            parentTab.getTabs().remove(tab);

        });

        //-------------------------------------------------
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ExaminationFile_perUnitController cc = null;
        user_group = reportgenthree.views.LoginscreenController.getGName();
        user_code = reportgenthree.views.LoginscreenController.getRegCode();

        if (course.equals("All Students File")) {

            if (user_group.equals("Academics Admin") || user_group.equals("Finance Admin")) {
                query = "SELECT * FROM exam_2017 "
                        + "WHERE Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND  Exam_Term = '" + term + "' "
                        + "GROUP BY Unit_Name";
            } else {

                query = "SELECT * FROM exam_2017 "
                        + "WHERE Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND  Exam_Term = '" + term + "' "
                        + "AND Unit_Name IN (SELECT UnitName FROM AssignedUnits WHERE Teacher_IdNumber = '" + user_code + "') GROUP BY Unit_Name";

            }

            try {

                Statement st = conn.createStatement();

                ResultSet rst = st.executeQuery(query);
                int count = 0;

                while (rst.next()) {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_perUnit.fxml"));
                    StackPane stackpane = loader.load();
                    cc = loader.getController();

                    String unit_name = rst.getString("Unit_Name");

                    cc.setCourse("All Courses Records");
                    cc.setExam_year(exam_year);
                    cc.setStd_year(std_year);
                    cc.setTerm(term);
                    cc.setUnitname(unit_name);
                    cc.setParentTab(parentTab);
                    
                    

                    if (AppFuctions.Functions.get_unitproperty(unit_name).equals("com")) {

                        hbox.getChildren().add(stackpane);
                        count++;
                    }

                    if (count == 5) {

                        vbox.getChildren().add(hbox);
                        hbox = new HBox();
                        HBox.setHgrow(hbox, Priority.ALWAYS);
                        hbox.setSpacing(10);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        count = 0;
                    }

                }

//             
                vbox.getChildren().add(hbox);
                spane.setContent(vbox);
                tab.setContent(spane);
                parentTab.getTabs().add(tab);
                parentTab.getSelectionModel().select(tab);

                conn.close();

            } catch (SQLException | IOException exc) {

                System.out.println(" " + exc);
            }

        } else {

            if (user_group.equals("Academics Admin") || user_group.equals("Finance Admin")) {
                query = "SELECT * FROM exam_2017 "
                        + "WHERE Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND Std_Course = '" + course + "' and Exam_Term = '" + term + "' "
                        + "GROUP BY Unit_Name";
            } else {

                query = "SELECT * FROM exam_2017 "
                        + "WHERE Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND Std_Course = '" + course + "' AND Exam_Term = '" + term + "' "
                        + "AND Unit_Name IN (SELECT UnitName FROM AssignedUnits WHERE Teacher_IdNumber = '" + user_code + "')"
                        + "GROUP BY Unit_Name";

            }
            String course = "";

            try {

                Statement st = conn.createStatement();

                ResultSet rst = st.executeQuery(query);
                int count = 0;

                while (rst.next()) {

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(ExaminationFile_percourseController.class.getResource("ExaminationFile_perUnit.fxml"));
                    StackPane stackpane = loader.load();
                    cc = loader.getController();

                    course = rst.getString("Std_Course");
                    String unit_name = rst.getString("Unit_Name");

                    cc.setCourse(course);
                    cc.setExam_year(exam_year);
                    cc.setStd_year(std_year);
                    cc.setTerm(term);
                    cc.setUnitname(unit_name);
                    cc.setParentTab(parentTab);

                    if (AppFuctions.Functions.get_unitproperty(unit_name).equals("uni")) {

                        hbox.getChildren().add(stackpane);
                        count++;
                    }

                    if (count == 5) {

                        vbox.getChildren().add(hbox);
                        hbox = new HBox();
                        HBox.setHgrow(hbox, Priority.ALWAYS);
                        hbox.setSpacing(10);
                        hbox.setAlignment(Pos.CENTER_LEFT);
                        count = 0;
                    }

                }

//               
                vbox.getChildren().add(hbox);
                spane.setContent(vbox);
                tab.setContent(spane);
                parentTab.getTabs().add(tab);
                parentTab.getSelectionModel().select(tab);

                conn.close();

            } catch (SQLException | IOException exc) {

                System.out.println(" " + exc);
            }

        }

    }

    public String getCourse() {
        return course;

    }

    public void setCourse(String course) {
        this.course = course;
        lbl_course.setText(course);
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

}
