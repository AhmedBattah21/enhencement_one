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
import javafx.scene.control.Label;


public class WarningBoxController implements Initializable {
@FXML
    private JFXButton btn_help;
 @FXML
    private Label lbl_infor;

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 

    public JFXButton getBtn_help() {
        return btn_help;
    }

    public void setBtn_help(JFXButton btn_help) {
        this.btn_help = btn_help;
    }

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }
    
    
    
}
