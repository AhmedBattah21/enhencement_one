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
public class Students_NavBarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXButton btn_newStudent;

    @FXML
    private JFXButton btn_Edit;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_delete;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void open_studentRegForm(){
    
      ReportGenThree.NavBar_StudentsRegistration();  
    
    }
    
    
    
    public void open_studentFindForm(){
        
        ReportGenThree.NavBar_StudentsFindData();
    }
    
    public void open_studentDeleteDataForm(){
        
        ReportGenThree.NavBar_StudentsDeleteData();
    }
    
    public void open_studentViewListDataForm(){
        
        ReportGenThree.NavBar_StudentsViewListForm();
    }
    
    
    //NavBar_StudentsDeleteData
    
}
