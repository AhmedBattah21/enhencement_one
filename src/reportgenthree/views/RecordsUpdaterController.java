/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.Functions;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXProgressBar;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class RecordsUpdaterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_updatestdetails;

    @FXML
    private JFXProgressBar processor_one;

    @FXML
    private JFXButton btn_updateexams;

    @FXML
    private JFXProgressBar processor_two;

    @FXML
    private JFXButton btn_updatefee;

    @FXML
    private JFXProgressBar processor_three;

    @FXML
    private JFXButton btn_close;
    
    @FXML
    private JFXButton btn_systemrecords;

    @FXML
    private JFXProgressBar processor_four;

    @FXML
    private Label lbl_one;

    @FXML
    private Label lbl_two;
    @FXML
    private Label lbl_three;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        processor_one.setVisible(false);
        processor_two.setVisible(false);
        processor_three.setVisible(false);
    }

    public void close() {

        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();

    }
    
    
    public void update_systemdetails(){
    
        
    
    
    
    }

    public void updateStdetails() {

        Platform.runLater(() -> {
            lbl_one.setText("");
        });

        processor_one.setVisible(true);

        Connection conn_updater = Settings_NavBarController.getConnetcion();
        Connection conn_master = sqlDataBaseConnection.sqliteconnect();

        String query = "SELECT * FROM Students_2017";

        try {

            Statement st_master = conn_master.createStatement();
            Statement st_updater = conn_updater.createStatement();

            ResultSet rst_updater = st_updater.executeQuery(query);

            int count = 0;

            while (rst_updater.next()) {

                String stname = rst_updater.getString("StudentName");
                String Ccode = rst_updater.getString("Ccode");
                String StudentRegCode = rst_updater.getString("StudentRegCode");
                String YearOfStudy = rst_updater.getString("YearOfStudy");
                String StudentCourse = rst_updater.getString("StudentCourse");
                String StudentCategory = rst_updater.getString("StudentCategory");
                String std_image = rst_updater.getString("std_image");
                String DOR = rst_updater.getString("DOR");
                String Gender = rst_updater.getString("Gender");

                String querytwo = "INSERT INTO Students_2017 VALUES('" + stname + "','" + Ccode + "','" + StudentRegCode + "','" + YearOfStudy + "',"
                        + "'" + StudentCourse + "','" + StudentCategory + "','" + std_image + "','" + DOR + "','" + Gender + "')";

                String querythree = "INSERT INTO registrationnumbers VALUES(null," + StudentRegCode + ",'Yes','" + stname + "')";

                if (checkStudent(StudentRegCode)) {

                } else {

                    count++;

                    st_master.executeUpdate(querytwo);
                    st_master.executeUpdate(querythree);

                    final int counter = count;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            lbl_one.setText("  Updating..." + counter);
                        }
                    });

                }

            }

            processor_one.setVisible(false);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    lbl_one.setText("Process Complete.");
                }
            });

            conn_updater.close();
            conn_master.close();

        } catch (SQLException exc) {

           Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static Boolean checkStudent(String stdReg) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM registrationnumbers WHERE Regnumber = '" + stdReg + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return result;

    }

    public void updateExams() {

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                lbl_two.setText("");
            }
        });

        processor_two.setVisible(true);

        Connection conn_updater = Settings_NavBarController.getConnetcion();
        Connection conn_master = sqlDataBaseConnection.sqliteconnect();

        String query = "SELECT * FROM exam_2017";

        try {

            Statement st_master = conn_master.createStatement();
            Statement st_updater = conn_updater.createStatement();

            ResultSet rst_updater = st_updater.executeQuery(query);

            int count = 0;

            while (rst_updater.next()) {

                String Adm_Number = rst_updater.getString("Adm_Number");
                String Exam_Name = rst_updater.getString("Exam_Name");
                String Exam_Term = rst_updater.getString("Exam_Term");
                String Exam_Score = rst_updater.getString("Exam_Score");
                String Std_Course = rst_updater.getString("Std_Course");
                String Exam_Id = rst_updater.getString("Exam_Id");
                String Unit_Name = rst_updater.getString("Unit_Name");
                String Year = rst_updater.getString("Year");
                String Syear = rst_updater.getString("Syear");

                String querytwo = "INSERT INTO exam_2017 VALUES ('" + Adm_Number + "','" + Exam_Name + "',"
                        + "'" + Exam_Term + "','" + Exam_Score + "','" + Std_Course + "',null,'" + Unit_Name + "','" + Year + "','" + Syear + "')";

                if (checkExamRecord(Adm_Number, Exam_Name, Exam_Term, Unit_Name, Syear, Year)) {

                } else {

                    count++;

                    final int counter = count;

                    st_master.executeUpdate(querytwo);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            lbl_two.setText("  Updating..." + counter);
                        }
                    });

                }

            }

            processor_two.setVisible(false);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    lbl_two.setText("Process Complete.");
                }
            });
            conn_master.close();
            conn_updater.close();

        } catch (SQLException exc) {

           Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

    }

    public Boolean checkExamRecord(String regno, String examname, String examterm, String examunit, String syear, String year) {

        boolean result = false;

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + regno + "'"
                + "AND Exam_Name = '" + examname + "' AND Exam_Term = '" + examterm + "' AND"
                + " Unit_Name = '" + examunit + "' AND Syear = '" + syear + "' AND Year = '" + year + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();
        } catch (SQLException exc) {

           Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }
        return result;

    }

    public void startTaskOne() {
        // Create a Runnable
        Runnable task = new Runnable() {
            public void run() {

                updateStdetails();

            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    public void startTaskTwo() {
        // Create a Runnable
        Runnable task = new Runnable() {
            public void run() {

                updateExams();

            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }
    
    
    public void startTaskThree() {
        // Create a Runnable
        Runnable task = new Runnable() {
            public void run() {

                feeUpdater();

            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    
    
    public void feeUpdater(){
        
        processor_three.setVisible(true);
        
        deleteFeesDetails();
        
        String Updaterquery = "SELECT * FROM Fee_Table";
        
        String Majorquery = "";
        
        Connection conn_updater = Settings_NavBarController.getConnetcion();
        Connection conn_master = sqlDataBaseConnection.sqliteconnect();
        
        try{
            
            Statement st = conn_updater.createStatement();
            ResultSet rst = st.executeQuery(Updaterquery);
            
            Statement master_st = conn_master.createStatement();
            
        int count = 0;    
        
        while(rst.next()){
            
            count++;
           
            String Acc_year = rst.getString("Acc_year");
            String Acc_user = rst.getString("Acc_user");
            String Acc_type = rst.getString("Acc_type");
            String Amount_t1 = rst.getString("Amount_t1");
            String Balance_t1 = rst.getString("Balance_t1");
            String Amount_t2 = rst.getString("Amount_t2");
            String Balance_t2 = rst.getString("Balance_t2");
            String Amount_t3 = rst.getString("Amount_t3");
            String Balance_t3 = rst.getString("Balance_t3");
            String Acc_Balance = rst.getString("Acc_Balance");
            
            Majorquery = "INSERT INTO Fee_Table VALUES(null,'"+Acc_year+"','"+Acc_user+"','"+Acc_type+"','"+Amount_t1+"','"+Balance_t1+"',"
                    + "'"+Amount_t2+"','"+Balance_t2+"','"+Amount_t3+"','"+Balance_t3+"','"+Acc_Balance+"')";
            
            master_st.executeUpdate(Majorquery);
            
            final int countthree = count;
            
            Platform.runLater(new Runnable() {
            @Override
            public void run() {

                lbl_three.setText("Updating..."+countthree);
            }
        });
        
        }
        
        conn_master.close();
        conn_updater.close();
        
            Platform.runLater(new Runnable() {
            @Override
            public void run() {

               lbl_three.setText("Updating...Finished.");
            }
        });
        
        
        }catch(SQLException exc){
        
            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        
        }
        
         processor_three.setVisible(false); 
    
    }
    
    public void deleteFeesDetails(){
    
    Connection conn_master = sqlDataBaseConnection.sqliteconnect();
    String query = "DELETE FROM Fee_Table";
    
    Platform.runLater(new Runnable() {
            @Override
            public void run() {

                lbl_three.setText("Deleting older files..");
            }
        });
    
    try{
        
        Statement st = conn_master.createStatement();
        
        st.executeUpdate(query);
        
        conn_master.close();
        
    
    }catch(SQLException exc){
        
        Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
    
    }
    
    }
}
