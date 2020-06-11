/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views.Dialogs;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ConfirmationDialogController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lbl_infor;

    @FXML
    private JFXButton btn_continue;

    @FXML
    private JFXButton btn_cancel;

    private String RESULT = "null";

    private String infor;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    public void continue_button() {

        RESULT = "continue";

        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();

    }

    public void cancel_button() {

        RESULT = "cancel";
        Stage stage = (Stage) btn_cancel.getScene().getWindow();
        stage.close();

    }

    public String getRESULT() {
        return RESULT;
    }

    public void setRESULT(String RESULT) {
        this.RESULT = RESULT;
    }

    public void setInfor(String infor) {
        this.infor = infor;
        lbl_infor.setText(infor);
    }

}
