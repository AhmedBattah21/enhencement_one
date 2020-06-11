/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import java.io.File;
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
public class Student_informationpageController implements Initializable {

    @FXML
    private Label Adm_number;
    @FXML
    private Label Std_course;
    @FXML
    private Label Std_name;
    @FXML
    private Label Std_dor;
    @FXML
    private Label Std_year;
    @FXML
    private Label Std_category;
    @FXML
    private Label Std_code;
    
    String image_name;
    
    File imagepath;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public Label getAdm_number() {
        return Adm_number;
    }

    public Label getStd_course() {
        return Std_course;
    }

    public Label getStd_name() {
        return Std_name;
    }

    public Label getStd_dor() {
        return Std_dor;
    }

    public Label getStd_year() {
        return Std_year;
    }

    public Label getStd_category() {
        return Std_category;
    }

    public Label getStd_code() {
        return Std_code;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public File getImagepath() {
        return imagepath;
    }

    public void setImagepath(File imagepath) {
        this.imagepath = imagepath;
    }
    
    

}
