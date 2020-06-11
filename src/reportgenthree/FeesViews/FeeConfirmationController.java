/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FeeConfirmationController implements Initializable {

    @FXML
    private Label lbl_title;

    @FXML
    private JFXButton btn_save;

    @FXML
    private JFXButton btn_cancel;

    @FXML
    private Label lbl_infor;

    public boolean result = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btn_cancel.setOnAction(e -> {

            result = false;

        });

        btn_save.setOnAction(e -> {

            result = true;

        });

    }

    public Label getLbl_title() {
        return lbl_title;
    }

    public void setLbl_title(Label lbl_title) {
        this.lbl_title = lbl_title;
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

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }
    
    

}
