/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
public class Information_windowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Label lbl_icon;
    
      @FXML
    private Label lbl_infor;
      
      @FXML
    private FontAwesomeIconView form_icon;
      
    
    private String infor;
    
    FontAwesomeIcon icon;
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   

    public void setInfor(String infor) {
        this.infor = infor;
        lbl_infor.setText(infor);
    }

    public void setForm_icon(FontAwesomeIconView form_icon) {
       
        lbl_icon.setGraphic(form_icon);
    }
    
    
    
    
    
}
