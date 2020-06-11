/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentExam_holderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane parent;

    @FXML
    private Label exam_term;

    @FXML
    private Label Exam_Year;

    @FXML
    private Label number_units;

    @FXML
    private Label grade;

    @FXML
    private Label lbl_endfile;

    @FXML
    private Label lbl_examname;

    String adm_no;

    String stexam_term;
    String stexam_year;
    String stunits;
    String stgrade;
    String exam_name;
    String std_year;
    JFXTabPane tabpane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        
        this.std_year = std_year;
    }

    public String getStexam_term() {
        return stexam_term;

    }

    public void setStexam_term(String stexam_term) {
        this.stexam_term = stexam_term;
        exam_term.setText("Exam Term " + stexam_term);
    }

    public String getStexam_year() {
        return stexam_year;
    }

    public void setStexam_year(String stexam_year) {
        this.stexam_year = stexam_year;
        Exam_Year.setText("Examination Year " + stexam_year);
    }

    public String getStunits() {
        return stunits;
    }

    public void setStunits(String stunits) {
        this.stunits = stunits;
        number_units.setText(stunits);
    }

    public String getStgrade() {
        return stgrade;
    }

    public void setStgrade(String stgrade) {
        this.stgrade = stgrade;
        grade.setText(stgrade);
    }

    public void setAdm_no(String adm_no) {
        this.adm_no = adm_no;
        lbl_endfile.setText("End Of Exam File " + adm_no);
    }

    public void setExam_name(String exam_name) {
        this.exam_name = exam_name;
        lbl_examname.setText(exam_name);
    }

    public void setTabpane(JFXTabPane tabpane) {
        this.tabpane = tabpane;
    }

    public void ExamDetails() {

        Tab tab = new Tab(stexam_term + " " +exam_name);
        
        if(exam_name.equals("Term Final  Totals")){
            
            
             FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StudentExam_holderController.class.getResource("StprofileExamination_finaldetails.fxml"));

        try {

            StackPane pane = loader.load();
            StprofileExamination_finaldetailsController cc = loader.getController();
            
            cc.setMytab(tab);
            cc.setAdm(adm_no);
            cc.setExam_year(stexam_year);
            cc.setParentTab(tabpane);
            cc.setStd_year(std_year);
            cc.setTerm(stexam_term);

            cc.getData();

            tab.setContent(pane);
            
             } catch (IOException exc) {

            System.out.println("Error " + exc);
        }
        
        }else{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(StudentExam_holderController.class.getResource("StprofileExamination_details.fxml"));

        try {

            StackPane pane = loader.load();
            StprofileExamination_detailsController cc = loader.getController();
            cc.setExamname(exam_name);
            cc.setStd_year(std_year);
            
            
            cc.setTerm(stexam_term);
            cc.setYear(stexam_year);

            cc.setAdm_number(adm_no);
            cc.setParentTab(tabpane);
            cc.setMytab(tab);

            cc.getData();

            tab.setContent(pane);

        } catch (IOException exc) {

            System.out.println("Error " + exc);
        }
        
        }

        tabpane.getTabs().add(tab);

        tabpane.getSelectionModel().select(tab);

    }

}
