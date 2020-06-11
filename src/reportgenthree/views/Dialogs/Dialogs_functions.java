/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views.Dialogs;

import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Computer
 */
public class Dialogs_functions {

    private static String Result;
    private static Stage Dialog_stage = new Stage();

    public Dialogs_functions() {

    }

    public static String get_custom_confirm(String infor) {

        StackPane pane = null;
        Dialog_stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Dialogs_functions.class.getResource("ConfirmationDialog.fxml"));
            pane = loader.load();
            ConfirmationDialogController cc = loader.getController();
            cc.setInfor(infor);

            Scene scene = new Scene(pane, 420, 170);

            Dialog_stage.setScene(scene);

            Dialog_stage.initModality(Modality.APPLICATION_MODAL);
            Dialog_stage.setResizable(false);
            Dialog_stage.showAndWait();

            Result = cc.getRESULT();

        } catch (IOException ex) {
            System.out.println(ex);
        }

        return Result;
    }
    
    public static void Data_saveddialog(StackPane stackpane,String message){
    
        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Dialogs_functions.class.getResource("Datasaved_dialog.fxml"));
            StackPane pane = loader.load();
            Datasaved_dialogController cc = loader.getController();
            cc.getLbl_infor().setText(message);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {

            System.out.println(ex);
        }
    
    
    
    }

}
