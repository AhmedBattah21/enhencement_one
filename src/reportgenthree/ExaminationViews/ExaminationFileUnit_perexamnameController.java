/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationViews;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationFileUnit_perexamnameController implements Initializable {

    @FXML
    private Label lbl_unitname;

    @FXML
    private Label lbl_coursename;

    @FXML
    private Label lbl_stdyear;

    @FXML
    private Label lbl_term;

    @FXML
    private Label lbl_examname;

    @FXML
    private Label lbl_examyear;

    String unit_name;
    String course;
    String std_year;
    String term;
    String exam_year;
    String exam_name;
    JFXTabPane parentTab;
    Tab tab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    void opentable() {

        if (!exam_name.equals("Calculated Totals")) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFileUnit_perexamnameController.class.getResource("ExaminationScoreSheetViewer.fxml"));
            try {

                StackPane pane = loader.load();
                ExaminationScoreSheetViewerController cc = loader.getController();
                tab = new Tab(unit_name + " Score Sheet");
                cc.setCourse(course);
                cc.setExam_name(exam_name);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setTerm(term);
                cc.setUnit_name(unit_name);
                cc.setParentTab(parentTab);
                cc.setTab(tab);
                cc.populate_table();

                tab.setContent(pane);

                parentTab.getTabs().add(tab);

                parentTab.getSelectionModel().select(tab);

            } catch (IOException exc) {

                System.out.println(exc);
            }

        } else {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ExaminationFileUnit_perexamnameController.class.getResource("ExaminationFile_CalculatedTotals.fxml"));
            try {

                StackPane pane = loader.load();
                ExaminationFile_CalculatedTotalsController cc = loader.getController();
                tab = new Tab(unit_name + " Score Sheet");
                cc.setCourse(course);
                cc.setExam_name(exam_name);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setTerm(term);
                cc.setUnit_name(unit_name);
                cc.setParentTab(parentTab);
                cc.setTab(tab);
                cc.getData();

                tab.setContent(pane);

                parentTab.getTabs().add(tab);

                parentTab.getSelectionModel().select(tab);

            } catch (IOException exc) {

                System.out.println(exc);
            }
        }
    }

    public String getUnit_name() {
        return unit_name;

    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
        lbl_unitname.setText(unit_name);
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
        lbl_coursename.setText(course);
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

    public String getExam_name() {
        return exam_name;
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
        lbl_examname.setText(exam_name);
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
