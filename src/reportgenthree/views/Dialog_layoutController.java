/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

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
public class Dialog_layoutController implements Initializable {

    /**
     * Initializes the controller class.
     */
      @FXML
    private Label lbl_date;

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_message;

    @FXML
    private Label lbl_source;
    
    private String message_title;
    private String message;
    private String message_source;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  

    public void setMessage_title(String message_title) {
        this.message_title = message_title;
        lbl_title.setText(message_title);
    }

    public void setMessage(String message) {
        this.message = message;
        lbl_message.setText(message);
    }

    public void setMessage_source(String message_source) {
        this.message_source = message_source;
        lbl_source.setText(message_source);
    }
    
    
    
}
