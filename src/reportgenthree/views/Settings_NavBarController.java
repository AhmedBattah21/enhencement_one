/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import AppFuctions.Functions;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Settings_NavBarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane navbar;

    @FXML
    private JFXButton btn_createupdate;

    @FXML
    private JFXButton btn_updatedtb;

    @FXML
    private JFXButton btn_checkupdates;

    @FXML
    private JFXButton btn_createbp;

    @FXML
    private StackPane mypane;

    private String SelectedArea = "";
    private String image_name = "";
    private File selected_image = null;
    private File selected_db = null;
    private static String selected_db_path = "";
    private String new_filename = "";
    private static Connection conn = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void createUpdater() {
        
        SelectedArea = "";

        openFileChooser();
        
        if(!SelectedArea.equals("")){

             sqliteCreate();
             
             
        }

    }
    
    public void BackUpForm(){
    
        
        reportgenthree.ReportGenThree.BackUp();
    
    
    }

    public void openFileChooser() {

        Stage stage = (Stage) btn_updatedtb.getScene().getWindow();

        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("M.E.R.M.S");

        File file = directoryChooser.showDialog(stage);

        if (file != null) {

            SelectedArea = file.getAbsolutePath();

        }

    }

    public void sqliteCreate() {

        reportgenthree.ReportGenThree.LoadProcessor();

        Random rand = new Random();

        String dbname = "reportgen.db";

        int r = rand.nextInt(50000) + 1;

        String extension = "";

        int i = dbname.lastIndexOf(".");
        if (i >= 0) {

            extension = dbname.substring(i + 1);

        }

        new_filename = "updater-" + r + "sql-".concat("." + extension);

        File srcfile = new File(System.getProperty("user.home") + "/" + "Documents" + "/" + "ReportGenThree" + "/" + "reportgen.db");

        String home = System.getProperty("user.home") + "/" + "Documents";

        File dest = new File(SelectedArea + "/" + new_filename);

        try {

            Files.copy(srcfile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

            //reportgenthree.ReportGenThree.closeProcessor();
            reportgenthree.ReportGenThree.LoadProcessorResult();

        } catch (IOException ex) {

            System.out.println("" + ex);

        }

    }

    public void updateMyDatabase() {

        //get the update path
        chooseDb();

        Connection conn = getConnetcion();

        String query = "SELECT * FROM users";

        try {

            Statement st = conn.createStatement();

            if (st.execute(query)) {

                reportgenthree.ReportGenThree.NavBar_RecordsUpaderForm();
                
                conn.close();

            } else {

               Functions.showAlert(Alert.AlertType.ERROR, "M.E.RM.S", "Invalid Database",
                       "The File Choosen As Database Updater Is Invalid \n Coose Another Databse");

            }

        } catch (SQLException ex) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.RM.S", "Invalid Database",
                       "The File Choosen As Database Updater Is Invalid \n Choose Another Databse");
        }

    }

    public void chooseDb() {

        Stage stage = (Stage) btn_updatedtb.getScene().getWindow();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("P.E.R.M.S  Select Your Updater Database File updater-****_sql");

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Database File", "*.db");
        chooser.getExtensionFilters().add(filter);

        File file = chooser.showOpenDialog(stage);

        if (file != null) {

            selected_db_path = file.getAbsolutePath();
            image_name = file.getName();
            selected_db = file.getAbsoluteFile();

        }

    }

    public static Connection getConnetcion() {

        

        try {

            Class.forName("org.sqlite.JDBC");

            String home = System.getProperty("user.home") + "/" + "Documents" + "/" + "ReportGenThree" + "/" + "reportgen.db";

            conn = DriverManager.getConnection("jdbc:sqlite:" + selected_db_path, null, null);

        } catch (ClassNotFoundException | SQLException exc) {

            JOptionPane.showMessageDialog(null, exc, null, JOptionPane.OK_OPTION);
        }

        return conn;

    }

}
