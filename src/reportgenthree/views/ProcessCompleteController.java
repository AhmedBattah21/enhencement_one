/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ProcessCompleteController implements Initializable {

    @FXML
    private Label lbl_infor;

    @FXML
    private JFXButton btn_openfile;

    @FXML
    private JFXButton btn_close;

    public static String path = "";

    private JFXDialog dlog =null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void openFile() {

        openDoc();
    }

    public void openDoc() {

        try {

            Desktop dk = Desktop.getDesktop();

            dk.open(new File(path));

            if (dlog != null) {
                dlog.close();
            }

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(null, "Failed To Open Document");
        }

    }

    public static void setPath(String path1) {

        path = path1;

    }

    public JFXDialog getDlog() {
        return dlog;
    }

    public void setDlog(JFXDialog dlog) {
        this.dlog = dlog;
    }

    public Label getLbl_infor() {
        return lbl_infor;
    }

    public void setLbl_infor(Label lbl_infor) {
        this.lbl_infor = lbl_infor;
    }

}
