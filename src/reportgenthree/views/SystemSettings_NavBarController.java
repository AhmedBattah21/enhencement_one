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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class SystemSettings_NavBarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane navbar;

    @FXML
    private JFXButton btn_createupdate;

    @FXML
    private JFXButton btn_updatedtb;

    @FXML
    private JFXButton btn_checkupdates;

    @FXML
    private JFXButton btn_createbp;

    @FXML
    private StackPane mypane;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void set_systemdates(){
    
    
       ReportGenThree.Semister_DurationSetter();
       
    
    }
}
