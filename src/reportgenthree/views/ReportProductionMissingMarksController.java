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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ReportProductionMissingMarksController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private JFXButton btn_close;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    public void close(){
    
        Stage stage = (Stage)btn_close.getScene().getWindow();
        stage.close();
    
    }
    
}
