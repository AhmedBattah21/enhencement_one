/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationFile_EndStageReportController implements Initializable {

     @FXML
    private StackPane mypane;

    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @FXML
    private Label lbl_email;

    @FXML
    private Label lbl_phone;

    @FXML
    private Label lbl_ref;

    @FXML
    private Label lbl_box;

    @FXML
    private Label lbl_town;

    @FXML
    private Label lbl_report_title;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_stdname;

    @FXML
    private Label lbl_department;

    @FXML
    private Label lbl_stage;

    @FXML
    private Label lbl_durationfrom;

    @FXML
    private Label lbl_adm;

    @FXML
    private Label lbl_coursename;

    @FXML
    private Label lbl_percentage;

    @FXML
    private Label lbl_dueto;

    @FXML
    private TableView<?> table;

    @FXML
    private TableColumn col_sno;

    @FXML
    private TableColumn col_ppcode;

    @FXML
    private TableColumn col_subjects;

    @FXML
    private TableColumn col_score;

    @FXML
    private TableColumn col_grade;

    @FXML
    private TableColumn col_points;

    @FXML
    private TableColumn col_comments;

    @FXML
    private Label lbl_overalresults;

    @FXML
    private Button btn_print;

    @FXML
    private Button btn_close;

    @FXML
    private Label lbl_infor;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    
    
}
