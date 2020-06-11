/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class EndstageDetailsTakerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField Pname;

    @FXML
    private JFXTextField dpt;

    @FXML
    private JFXTextField hdpt;

    @FXML
    private JFXTextField attendance;

    @FXML
    private JFXTextField dto;
    
    @FXML
    private JFXButton btnsave;

    private static String dapartment = "";
    private static String prname = "";
    private static String hdepartment = "";
    private static String attendanceone = "";
    private static String dateto = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void saveMethod() {

        if (!dpt.getText().isEmpty()) {

            dapartment = dpt.getText();
        }

        if (!Pname.getText().isEmpty()) {
            prname = Pname.getText();

        }

        if (!dto.getText().isEmpty()) {

            dateto = dto.getText();
        }

        if (!attendance.getText().isEmpty()) {
            attendanceone = attendance.getText();
        }

        if (!hdpt.getText().isEmpty()) {
            
            hdepartment = hdpt.getText();
        }
     
        Stage stage = (Stage)btnsave.getScene().getWindow();
        stage.close();
        
        
        
    }

    public static String getDepartment() {

        return dapartment;
    }

    public static String getHDepartment() {

        return hdepartment;
    }

    public static String getAttendance() {

        return attendanceone;
    }

    public static String getDateto() {

        return dateto;
    }

    public static String getPname() {

        return prname;
    }

}
