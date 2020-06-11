/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationViews;

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
public class Examination_titleController implements Initializable {

    @FXML
    private Label lbl_title;

    @FXML
    private Label lbl_date;
    
    String title;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        lbl_title.setText(title);
    }
    
    
}
