/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentRegistrationPanelController implements Initializable {

    @FXML
    private BorderPane parent;

    @FXML
    private JFXButton btn_stepone;

    @FXML
    private JFXButton btn_steptwo;

    @FXML
    private JFXButton btn_stepthree;

    @FXML
    private JFXButton btn_stepfour;
    
    @FXML
    private JFXButton btn_start;

    StudentRegistration_steponeController cc_stepone = null;
    StudentRegistration_steptwoController cc_steptwo = null;
    StudentRegistration_stepthreeController cc_stepthree = null;
    StudentRegistration_stepfourController cc_stepfour = null;

    StackPane spane_stepone = null;
    StackPane spane_steptwo = null;
    StackPane spane_stepthree = null;
    StackPane spane_stepfour = null;
    
    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        lbl_schoolname.setText(SystemNameSetterController.getSname());
        lbl_boxnumber.setText(SystemNameSetterController.getbox());
        
        btn_start.setOnAction(e->{
        
            step_one();
        });
        
        btn_stepone.setOnAction(e -> {
        
             if (spane_stepone != null) {

                parent.setCenter(spane_stepone);
                btn_stepone.setGraphic(null);
            }
        
        });

        btn_steptwo.setOnAction(e -> {

            if (spane_steptwo != null) {

                parent.setCenter(spane_steptwo);
                 btn_steptwo.setGraphic(null);
            }

        });

        btn_stepthree.setOnAction(e -> {

            if (spane_stepthree != null) {

                parent.setCenter(spane_stepthree);
                 btn_stepthree.setGraphic(null);
            }

        });

        btn_stepfour.setOnAction(e -> {

            if (spane_stepfour != null) {

                parent.setCenter(spane_stepfour);
                 btn_stepfour.setGraphic(null);
            }

        });

    }

    public void step_one() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentRegistrationPanelController.class.getResource("StudentRegistration_stepone.fxml"));
            StackPane pane = loader.load();
            StudentRegistration_steponeController cc = loader.getController();
            cc.setParent(parent);
            parent.setCenter(pane);

            btn_stepone.setGraphic(null);
            btn_steptwo.setGraphic(null);
            btn_stepthree.setGraphic(null);
            btn_stepfour.setGraphic(null);

            spane_stepone = pane;
             spane_steptwo = null;
            spane_stepthree = null;
            spane_stepfour = null;

        } catch (IOException ex) {

            System.out.println("Error " + ex);
        }

    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public JFXButton getBtn_stepone() {
        return btn_stepone;
    }

    public void setBtn_stepone(JFXButton btn_stepone) {
        this.btn_stepone = btn_stepone;
    }

    public JFXButton getBtn_steptwo() {
        return btn_steptwo;
    }

    public void setBtn_steptwo(JFXButton btn_steptwo) {
        this.btn_steptwo = btn_steptwo;
    }

    public JFXButton getBtn_stepthree() {
        return btn_stepthree;
    }

    public void setBtn_stepthree(JFXButton btn_stepthree) {
        this.btn_stepthree = btn_stepthree;
    }

    public JFXButton getBtn_stepfour() {
        return btn_stepfour;
    }

    public void setBtn_stepfour(JFXButton btn_stepfour) {
        this.btn_stepfour = btn_stepfour;
    }

    public JFXButton getBtn_start() {
        return btn_start;
    }

    public void setBtn_start(JFXButton btn_start) {
        this.btn_start = btn_start;
    }

    public StudentRegistration_steponeController getCc_stepone() {
        return cc_stepone;
    }

    public void setCc_stepone(StudentRegistration_steponeController cc_stepone) {
        this.cc_stepone = cc_stepone;
    }

    public StudentRegistration_steptwoController getCc_steptwo() {
        return cc_steptwo;
    }

    public void setCc_steptwo(StudentRegistration_steptwoController cc_steptwo) {
        this.cc_steptwo = cc_steptwo;
    }

    public StudentRegistration_stepthreeController getCc_stepthree() {
        return cc_stepthree;
    }

    public void setCc_stepthree(StudentRegistration_stepthreeController cc_stepthree) {
        this.cc_stepthree = cc_stepthree;
    }

    public StudentRegistration_stepfourController getCc_stepfour() {
        return cc_stepfour;
    }

    public void setCc_stepfour(StudentRegistration_stepfourController cc_stepfour) {
        this.cc_stepfour = cc_stepfour;
    }

    public StackPane getSpane_stepone() {
        return spane_stepone;
    }

    public void setSpane_stepone(StackPane spane_stepone) {
        this.spane_stepone = spane_stepone;
    }

    public StackPane getSpane_steptwo() {
        return spane_steptwo;
    }

    public void setSpane_steptwo(StackPane spane_steptwo) {
        this.spane_steptwo = spane_steptwo;
    }

    public StackPane getSpane_stepthree() {
        return spane_stepthree;
    }

    public void setSpane_stepthree(StackPane spane_stepthree) {
        this.spane_stepthree = spane_stepthree;
    }

    public StackPane getSpane_stepfour() {
        return spane_stepfour;
    }

    public void setSpane_stepfour(StackPane spane_stepfour) {
        this.spane_stepfour = spane_stepfour;
    }

    public Label getLbl_schoolname() {
        return lbl_schoolname;
    }

    public void setLbl_schoolname(Label lbl_schoolname) {
        this.lbl_schoolname = lbl_schoolname;
    }

    public Label getLbl_boxnumber() {
        return lbl_boxnumber;
    }

    public void setLbl_boxnumber(Label lbl_boxnumber) {
        this.lbl_boxnumber = lbl_boxnumber;
    }

   
}
