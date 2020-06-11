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
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class ProcessCompleteMultipleReportsController implements Initializable {

    @FXML
    private Label lbl_numberofreports;

    @FXML
    private Label lbl_missingmarks;

    @FXML
    private JFXButton btn_marge;

    JFXDialog dlog = new JFXDialog();

    @FXML
    private JFXButton btn_exit;

    static int missingmarks = 0;
    static int num_reports = 0;

    static String path = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        lbl_missingmarks.setText("Students With Missing Marks " + retunMissingMarks());
        lbl_numberofreports.setText("Number Of Reports Produced " + retunReportsMarks());

    }

    public void close() {

        dlog.close();

    }

    public static void setMissingMarks(int value) {

        missingmarks = value;

    }

    public static void setReportsMarks(int value) {

        num_reports = value;

    }

    public int retunMissingMarks() {

        return missingmarks;

    }

    public int retunReportsMarks() {

        return num_reports;

    }

    public static void setPath(String path1) {

        path = path1;

    }

    public void openDoc() {

        try {

            AppFuctions.MutlipleReportproducer.mergerpdfs();

            Desktop dk = Desktop.getDesktop();

            dk.open(new File(path));

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(null, "Failed To Open Document");
        }

    }

    public Label getLbl_numberofreports() {
        return lbl_numberofreports;
    }

    public void setLbl_numberofreports(Label lbl_numberofreports) {
        this.lbl_numberofreports = lbl_numberofreports;
    }

    public Label getLbl_missingmarks() {
        return lbl_missingmarks;
    }

    public void setLbl_missingmarks(Label lbl_missingmarks) {
        this.lbl_missingmarks = lbl_missingmarks;
    }

    public JFXDialog getDlog() {
        return dlog;
    }

    public void setDlog(JFXDialog dlog) {
        this.dlog = dlog;
    }

}
