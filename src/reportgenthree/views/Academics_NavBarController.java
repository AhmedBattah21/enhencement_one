/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Academics_NavBarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_addScore;

    @FXML
    private JFXButton btn_editExamScore;

    @FXML
    private JFXButton btn_viewExamScore;

    @FXML
    private JFXButton btn_Delete;

    @FXML
    private JFXButton btn_CreateReports;

    @FXML
    private JFXButton btn_CreateMReports;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void open_addScoreForm() {

        ReportGenThree.NavBar_AcademicsAddScore();

    }

    public void open_EditScoreForm() {

        ReportGenThree.NavBar_AcademicsEditExamScore();

    }

   

    public void open_StudentsDeleteScore() {

        ReportGenThree.NavBar_AcademicsDeleteScore();

    }

    public void open_StudentsAcademicReport() {

        ReportGenThree.NavBar_AcademicsSingleReport();

    }

    public void open_MultipleAcademicReport_1st() {

        ReportGenThree.Examination_reportsfiles_1st();

    }
    
     public void open_MultipleAcademicReport_2nd() {

        ReportGenThree.Examination_reportsfiles_2nd();

    }

    public void open_singlestagereport() {

        ReportGenThree.NavBar_SingleEndStage();

        //
    }

    public void open_multiplestagereport() {

        ReportGenThree.NavBar_AcademicsMultipleEndStage();

        //
    }

}
