/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import AppFuctions.BackupFeeToExcell;
import com.jfoenix.controls.JFXProgressBar;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Random;
import javafx.application.Platform;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class CreateBackUpController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_choosepath;

    @FXML
    private JFXTextField txt_path;

    @FXML
    private JFXButton btn_create;

    @FXML
    private JFXProgressBar progressbar;

    @FXML
    private Label lbl_infor;

    @FXML
    private JFXButton btn_close;

    private String selectedPath = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbl_infor.setText("");
        lbl_infor.setVisible(false);

        progressbar.setVisible(false);

    }

    public void choosePath() {

        Stage stage = (Stage) btn_close.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("M.E.R.M.S");

        File file = directoryChooser.showDialog(stage);

        if (file != null) {

            selectedPath = file.getAbsolutePath();

            txt_path.setText(selectedPath);

        }

    }

    public void runTaskBackUp() {

        progressbar.setVisible(true);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                lbl_infor.setVisible(true);

                lbl_infor.setText("Creating Wait...");
            }
        });

        Runnable task = new Runnable() {
            public void run() {

                BackupFeeToExcell.CreateBackups(selectedPath);

                sqliteCreate();

            }
        };

        //Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();

    }

    public void close() {

        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();

    }

    public void sqliteCreate() {

        String dbname = "reportgen.db";

        File srcfile = new File(System.getProperty("user.home") + "/" + "Documents" + "/" + "ReportGenThree" + "/" + "reportgen.db");

        String home = System.getProperty("user.home") + "/" + "Documents";

        File dest = new File(selectedPath + "/" + dbname);

        try {

            Files.copy(srcfile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {

            System.out.println("" + ex);

        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                reportgenthree.ReportGenThree.LoadProcessorResult();

                lbl_infor.setText("Finished Task...");
            }
        });

        progressbar.setVisible(false);

    }

}
