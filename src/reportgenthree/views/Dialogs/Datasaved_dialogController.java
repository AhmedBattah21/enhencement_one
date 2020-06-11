/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views.Dialogs;

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
public class Datasaved_dialogController implements Initializable {

    @FXML
    private Label lbl_infor;
    
    @FXML
    private Label lbl_icon;
    
     @FXML
    private Label lbl_head;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }

    
    public void setLbl_icon(Label lbl_icon) {
        this.lbl_icon = lbl_icon;
    }

    public Label getLbl_head() {
        return lbl_head;
    }

    public void setLbl_head(Label lbl_head) {
        this.lbl_head = lbl_head;
    }

    public Label getLbl_icon() {
        return lbl_icon;
    }
    
    
    
}
