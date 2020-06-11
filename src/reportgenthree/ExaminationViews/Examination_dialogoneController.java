/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class Examination_dialogoneController implements Initializable {

    @FXML
    private Label lbl_filetitle;

    @FXML
    private Label lbl_file_stdyear;

    @FXML
    private Label file_examterm;

    String std_year;
    String exam_term;
    String exam_year;

    JFXTabPane parentTab = null;

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
    void Open_records(MouseEvent event) {

        user_group = reportgenthree.views.LoginscreenController.getGName();
        user_code = reportgenthree.views.LoginscreenController.getRegCode();

        String query = "";
        if (user_group.equals("Academics Admin") || user_group.equals("Finance Admin")) {

            query = "SELECT * FROM exam_2017 WHERE Year = '" + exam_year + "' AND Syear = '" + std_year + "' AND Exam_Term = '" + exam_term + "'"
                    + " GROUP BY Year,Syear,Exam_Term,Std_Course ORDER BY Year,Syear";
        } else {

            query = "SELECT * FROM exam_2017 WHERE Year = '" + exam_year + "' AND Syear = '" + std_year + "' AND Exam_Term = '" + exam_term + "' "
                    + "AND  Std_Course IN (SELECT Teacher_Department FROM Teacher WHERE Teacher_IdNumber = '" + user_code + "') "
                    + " GROUP BY Year,Syear,Exam_Term,Std_Course ORDER BY Year,Syear";
        }

        Tab tab = new Tab(exam_year + "   " + exam_term + " @ Course");

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
        MenuItem item1 = new MenuItem("Courses Examination Files");
        SeparatorMenuItem sp1 = new SeparatorMenuItem();
        MenuItem item2 = new MenuItem("Close Course Files Tab");
        menu.getItems().addAll(item1, sp1, item2);

        vbox.setOnContextMenuRequested((ContextMenuEvent eve) -> {
            menu.show(vbox, eve.getScreenX(), eve.getScreenY());
        });

        item2.setOnAction((ActionEvent eve) -> {

            parentTab.getTabs().remove(tab);

        });

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ExaminationFile_percourseController cc = null;

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);
            int count = 0;

            while (rst.next()) {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Examination_dialogoneController.class.getResource("ExaminationFile_percourse.fxml"));
                StackPane stackpane = loader.load();
                cc = loader.getController();

                String course = rst.getString("Std_Course");

                cc.setCourse(course);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setTerm(exam_term);
                cc.setParentTab(parentTab);

                hbox.getChildren().add(stackpane);
                count++;

                if (count == 5) {

                    vbox.getChildren().add(hbox);
                    hbox = new HBox();
                    HBox.setHgrow(hbox, Priority.ALWAYS);
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER_LEFT);
                    count = 0;
                }

            }

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Examination_dialogoneController.class.getResource("ExaminationFile_percourse.fxml"));
            StackPane stackpane = loader.load();
            cc = loader.getController();

            cc.setCourse("All Students File");
            cc.setExam_year(exam_year);
            cc.setStd_year(std_year);
            cc.setTerm(exam_term);
            cc.setParentTab(parentTab);
            hbox.getChildren().add(stackpane);

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

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
        lbl_file_stdyear.setText(std_year);
    }

    public String getExam_term() {
        return exam_term;
    }

    public void setExam_term(String exam_term) {
        this.exam_term = exam_term;
        file_examterm.setText(exam_term);

    }

    public String getExam_year() {
        return exam_year;
    }

    public void setExam_year(String exam_year) {
        this.exam_year = exam_year;
        lbl_filetitle.setText("Examination File For " + exam_year);
    }

    public void setParentTab(JFXTabPane parentTab) {
        this.parentTab = parentTab;
    }

}
