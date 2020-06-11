/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class TeachersPanelController implements Initializable {

    @FXML
    private BorderPane parent;

    @FXML
    private JFXButton btn_addteacher;

    @FXML
    private JFXButton btn_availableteachers;

    @FXML
    private JFXButton btn_assign;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_addtask;

    @FXML
    private StackPane stackpane;

    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btn_addteacher.setOnAction(e -> {
            open_addteachers();
        });
        btn_availableteachers.setOnAction(e -> {
            open_Availableteachers();
        });

        btn_assign.setOnAction(e -> {
            open_Assignteacher();
        });

        btn_edit.setOnAction(e -> {

            open_AssignedteachersView();
        });

        btn_addtask.setOnAction(e -> {

            add_task();
        });

        lbl_schoolname.setText(SystemNameSetterController.getSname());
        lbl_boxnumber.setText(SystemNameSetterController.getbox());

    }

    public void open_addteachers() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TeachersPanelController.class.getResource("Teachers_AddnewTeacher.fxml"));

            StackPane spane = loader.load();
            Teachers_AddnewTeacherController cc = loader.getController();
            cc.setParent_stackpane(stackpane);
            cc.setTp_cc(this);

            parent.setCenter(spane);

        } catch (IOException ex) {
            Logger.getLogger(TeachersPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void open_Availableteachers() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TeachersPanelController.class.getResource("Teachers_Availableteachers.fxml"));

            StackPane spane = loader.load();
            Teachers_AvailableteachersController cc = loader.getController();
            cc.setParent_stackpane(stackpane);

            parent.setCenter(spane);

        } catch (IOException ex) {
            Logger.getLogger(TeachersPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void open_Assignteacher() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TeachersPanelController.class.getResource("Teachers_Assignunits.fxml"));

            StackPane spane = loader.load();
            Teachers_AssignunitsController cc = loader.getController();
            cc.setParent_stackpane(stackpane);
            cc.setTp_cc(this);

            parent.setCenter(spane);

        } catch (IOException ex) {
            Logger.getLogger(TeachersPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void open_AssignedteachersView() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TeachersPanelController.class.getResource("Teachers_AssignedUnitsViewer.fxml"));

            StackPane spane = loader.load();

            parent.setCenter(spane);

        } catch (IOException ex) {
            Logger.getLogger(TeachersPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void add_task() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TeachersPanelController.class.getResource("Teacher_Tasks.fxml"));
            StackPane pane = loader.load();
            Teacher_TasksController cc = loader.getController();

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {

            System.out.println(ex);
        }

    }
}
