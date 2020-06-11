/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views.Dialogs;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Studentpropert_texteditorController implements Initializable {

    @FXML
    private Label lbl_oldvalue;
    
     @FXML
    private Label lbl_infor;

    @FXML
    private HBox hbox_properttype;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_cancel;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    public void add_control(JFXComboBox control){
    
        hbox_properttype.getChildren().add(control);
       
    }
    
    public void add_control(TextField control,String hint){
    
        hbox_properttype.getChildren().add(control);
        control.setPromptText(hint);
    
    }

    public Label getLbl_oldvalue() {
        return lbl_oldvalue;
    }

    public void setLbl_oldvalue(Label lbl_oldvalue) {
        this.lbl_oldvalue = lbl_oldvalue;
    }

    public HBox getHbox_properttype() {
        return hbox_properttype;
    }

    public void setHbox_properttype(HBox hbox_properttype) {
        this.hbox_properttype = hbox_properttype;
    }

    public JFXButton getBtn_save() {
        return btn_save;
    }

    public void setBtn_save(JFXButton btn_save) {
        this.btn_save = btn_save;
    }

    public JFXButton getBtn_cancel() {
        return btn_cancel;
    }

    public void setBtn_cancel(JFXButton btn_cancel) {
        this.btn_cancel = btn_cancel;
    }

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }
    
    
}
