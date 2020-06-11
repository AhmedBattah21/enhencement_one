/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class InforDisplayerController implements Initializable {

   @FXML
    private Label lbl_iconholder;

    @FXML
    private Label lbl_infor;
    
    @FXML
    private JFXButton btn_allpurpose;
    
    boolean result = false;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn_allpurpose.setOnAction(e->get_results());
    }  

    public Label getLbl_iconholder() {
        return lbl_iconholder;
    }

    public void setLbl_iconholder(Label lbl_iconholder) {
        this.lbl_iconholder = lbl_iconholder;
    }

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }

    public JFXButton getBtn_allpurpose() {
        return btn_allpurpose;
    }

    public void setBtn_allpurpose(JFXButton btn_allpurpose) {
        this.btn_allpurpose = btn_allpurpose;
    }
    
    public void get_results(){
    
        setResult(true);
    
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
    
    
}
