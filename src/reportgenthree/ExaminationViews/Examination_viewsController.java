/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationViews;

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
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Examination_viewsController implements Initializable {

    @FXML
    private JFXTabPane Parent_tab;

    @FXML
    private Tab default_tab;

    @FXML
    private VBox default_fileholder;

    private String syear = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //get_files();
    }

    @FXML
    void close(ActionEvent event) {

        Parent_tab.getTabs().remove(default_tab);
        place_inforbox();

    }

    public void get_files_1styear() {

        String query = "SELECT * FROM exam_2017 WHERE Syear = 'First Year'  GROUP BY Year,Syear,Exam_Term ORDER BY Year,Syear";

        syear = "First Year";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        HBox box = new HBox();
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(30);

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            int count = 0;

            while (rst.next()) {

                String exam_year = rst.getString("Year");
                String exam_term = rst.getString("Exam_Term");
                String std_year = rst.getString("Syear");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Examination_viewsController.class.getResource("Examination_dialogone.fxml"));

                StackPane pane = loader.load();
                Examination_dialogoneController cc = loader.getController();
                cc.setExam_term(exam_term);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setParentTab(Parent_tab);

                box.getChildren().add(pane);
                count++;

                if (count == 5) {

                    default_fileholder.getChildren().add(box);
                    box = new HBox();
                    HBox.setHgrow(box, Priority.ALWAYS);
                    box.setSpacing(30);
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

    public void get_files_2ndyear() {

        String query = "SELECT * FROM exam_2017 WHERE Syear = 'Second Year' GROUP BY Year,Syear,Exam_Term ORDER BY Year,Syear";

        syear = "Second Year";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        HBox box = new HBox();
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(30);

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            int count = 0;

            while (rst.next()) {

                String exam_year = rst.getString("Year");
                String exam_term = rst.getString("Exam_Term");
                String std_year = rst.getString("Syear");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Examination_viewsController.class.getResource("Examination_dialogone.fxml"));

                StackPane pane = loader.load();
                Examination_dialogoneController cc = loader.getController();
                cc.setExam_term(exam_term);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setParentTab(Parent_tab);

                box.getChildren().add(pane);
                count++;

                if (count == 5) {

                    default_fileholder.getChildren().add(box);
                    box = new HBox();
                    HBox.setHgrow(box, Priority.ALWAYS);
                    box.setSpacing(30);
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
        StackPane pane = add_inforbox("Welcome To Student Examination Files " + syear, icon, Parent_tab);
        Tab tab = new Tab("Default Tab");
        tab.setContent(pane);
        Parent_tab.getTabs().add(tab);
        Parent_tab.getSelectionModel().select(tab);

    }

    public StackPane add_inforbox(String Message, FontAwesomeIconView icon, JFXTabPane tpane) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Examination_viewsController.class.getResource("Information_window.fxml"));
        StackPane pane = null;
        icon.setGlyphSize(150);
        try {
            pane = loader.load();
            Information_windowController cc = loader.getController();
            cc.setInfor(Message);
            cc.setForm_icon(icon);
            cc.setParent_tab(tpane);

            cc.getBtn_openfiles_one().setOnAction(e -> {
                ReportGenThree.Examination_Files_1styear();

            });

            cc.getBtn_openfiles_two().setOnAction(e -> {
                ReportGenThree.Examination_Files_2ndyear();
            });
           

        } catch (IOException exc) {

            System.out.println("" + exc);

        }

        return pane;

    }

    public void calculate() {

        System.out.println("Calculated...");

    }

}
