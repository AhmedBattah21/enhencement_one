/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
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
public class ProcessCompleteMultipleEndStageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lbl_numberofreports;

    @FXML
    private Label lbl_missingmarks;

    @FXML
    private JFXButton btn_view;

    @FXML
    private JFXButton btn_marge;

    @FXML
    private JFXButton btn_exit;
    
    @FXML
    private JFXButton btn_openfolder;

    static int missingmarks = 0;
    static int num_reports = 0;

    static String path = "";
    static String mainpath = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        lbl_missingmarks.setText("Students With Missing Marks " + retunMissingMarks());
        lbl_numberofreports.setText("Number Of Reports Produced " + retunReportsMarks());

        
    }

    public void close() {

        Stage stage = (Stage) btn_exit.getScene().getWindow();
        stage.close();

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
    
    public static void setPath(String path1){
    
        path = path1;
    
    }
    
     public static void setMainPath(String path1){
    
        mainpath = path1;
    
    }
    
    public  void openDoc() {
        
        try {
            
            AppFuctions.MultipleEndStageproducer.mergerpdfs();

            Desktop dk = Desktop.getDesktop();

           
            dk.open(new File(path));
            
           Stage stage = (Stage) btn_exit.getScene().getWindow();
           stage.close();
          

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(null, "Failed To Open Document \n"+ex);
        }

    }
    
    
    public void OpenFolder(){
        
        
    
    try{
        
        
        Runtime.getRuntime().exec("explorer.exe"+mainpath);
        
    
    }catch(IOException exc){
    
    System.out.println("Error "+exc);
    }
    
    
    }
    
    

}
