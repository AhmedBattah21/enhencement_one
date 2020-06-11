/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationReportsViews;

import com.jfoenix.controls.JFXDialog;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ReportSearchFormController implements Initializable {

   @FXML
    private TextField txt_hint;

    @FXML
    private Button btn_search;
    
    @FXML
    private Label lbl_infor;
    
    private JFXDialog dialog;
    
    ExaminationFile_percourseController cc;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        btn_search.setOnAction(e->search());
        
    }  
    
    public void search(){
    
    if(cc != null && !txt_hint.getText().isEmpty()){
        String hint = txt_hint.getText().trim();
    
        cc.search(hint);
        
        if(dialog != null){
        
            dialog.close();
        }
    }
    
    }

    public ExaminationFile_percourseController getCc() {
        return cc;
    }

    public void setCc(ExaminationFile_percourseController cc) {
        this.cc = cc;
    }

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }

    public JFXDialog getDialog() {
        return dialog;
    }

    public void setDialog(JFXDialog dialog) {
        this.dialog = dialog;
    }
    
    
    
    
}
