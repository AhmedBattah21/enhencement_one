/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.Connection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import reportgenthree.ExaminationViews.Information_windowController;
import reportgenthree.ReportGenThree;

public class Examination_ReportsViewController implements Initializable {

    @FXML
    private JFXTabPane Parent_tab;
    
     @FXML
    private StackPane ParentPane;

    @FXML
    private Tab default_tab;

    @FXML
    private VBox default_fileholder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void close(ActionEvent event) {

        Parent_tab.getTabs().remove(default_tab);
        place_inforbox();

    }

    public void get_files_1st() {

        default_tab.setText("Examination Report Forms");

        String query = "SELECT * FROM exam_2017 WHERE Syear = 'First Year'  GROUP BY Year,Syear,Exam_Term ORDER BY Year,Syear";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        HBox box = new HBox();
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(40);

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            int count = 0;

            while (rst.next()) {

                String exam_year = rst.getString("Year");
                String exam_term = rst.getString("Exam_Term");
                String std_year = rst.getString("Syear");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Examination_ReportsViewController.class.getResource("Examination_dialogone.fxml"));

                StackPane pane = loader.load();
                Examination_dialogoneController cc = loader.getController();
                cc.setExam_term(exam_term);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setParentTab(Parent_tab);
                cc.setParent_pane(ParentPane);

                box.getChildren().add(pane);
                count++;

                if (count == 5) {

                    default_fileholder.getChildren().add(box);
                    box = new HBox();
                    HBox.setHgrow(box, Priority.ALWAYS);
                    box.setSpacing(40);
                    box.setAlignment(Pos.CENTER_LEFT);
                    count = 0;
                }

            }

            default_fileholder.getChildren().add(box);

            conn.close();
        } catch (SQLException exc) {

        } catch (IOException ex) {

            System.out.println(" Error" + ex);
        }

    }

    public void get_files_2nd() {

        default_tab.setText("Examination Report Forms");

        String query = "SELECT * FROM exam_2017 WHERE Syear = 'Second Year' GROUP BY Year,Syear,Exam_Term ORDER BY Year,Syear";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        HBox box = new HBox();
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(40);

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            int count = 0;

            while (rst.next()) {

                String exam_year = rst.getString("Year");
                String exam_term = rst.getString("Exam_Term");
                String std_year = rst.getString("Syear");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Examination_ReportsViewController.class.getResource("Examination_dialogone.fxml"));

                StackPane pane = loader.load();
                Examination_dialogoneController cc = loader.getController();
                cc.setExam_term(exam_term);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setParentTab(Parent_tab);
                 cc.setParent_pane(ParentPane);

                box.getChildren().add(pane);
                count++;

                if (count == 5) {

                    default_fileholder.getChildren().add(box);
                    box = new HBox();
                    HBox.setHgrow(box, Priority.ALWAYS);
                    box.setSpacing(40);
                    box.setAlignment(Pos.CENTER_LEFT);
                    count = 0;
                }

            }

            default_fileholder.getChildren().add(box);

            conn.close();
        } catch (SQLException exc) {

        } catch (IOException ex) {

            System.out.println(" Error" + ex);
        }

    }

    public void place_inforbox() {

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BINOCULARS);
        icon.setGlyphSize(100);
        icon.setGlyphStyle("-fx-fill:skyblue");
        StackPane pane = add_inforbox("Welcome To Student Examination Files", icon, Parent_tab);
        Tab tab = new Tab("Default Tab");
        tab.setContent(pane);
        Parent_tab.getTabs().add(tab);
        Parent_tab.getSelectionModel().select(tab);

    }

    public StackPane add_inforbox(String Message, FontAwesomeIconView icon, JFXTabPane tpane) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("ExaminationViews/Information_window.fxml"));
        StackPane pane = null;
        icon.setGlyphSize(150);
        try {
            pane = loader.load();
            Information_windowController cc = loader.getController();
            cc.setInfor(Message);
            cc.setForm_icon(icon);
            cc.setParent_tab(tpane);

            cc.getBtn_openfiles_one().setOnAction(e -> {
                ReportGenThree.Examination_reportsfiles_1st();

            });

            cc.getBtn_openfiles_two().setOnAction(e -> {
                ReportGenThree.Examination_reportsfiles_2nd();
            });

        } catch (IOException exc) {

            System.out.println("" + exc);

        }

        return pane;

    }

}
