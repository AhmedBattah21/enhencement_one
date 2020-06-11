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

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ExaminationReportFile_titleController implements Initializable {

   ExaminationFile_reportdetailsController cc = null;
   @FXML
    private JFXButton btn_print;

    @FXML
    private JFXButton btn_close;
    
    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    public ExaminationFile_reportdetailsController getCc() {
        return cc;
    }

    public void setCc(ExaminationFile_reportdetailsController cc) {
        this.cc = cc;
    }

    public JFXButton getBtn_print() {
        return btn_print;
    }

    public void setBtn_print(JFXButton btn_print) {
        this.btn_print = btn_print;
    }
    
    
    
    public void close(){
    
        
        cc.remove_tab();
    
    }
    
}
