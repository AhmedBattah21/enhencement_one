/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import AppFuctions.CoursesClass;
import AppFuctions.FeeBookLetCreator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import Connection.sqlDataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Fee_statementsBookletController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox cmb_course;

    @FXML
    private JFXComboBox cmb_year;

    @FXML
    private JFXComboBox cmb_term;

    @FXML
    private JFXButton btn_create;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        CoursesClass.populateComboBoxClasses(cmb_course, "CourseName", "CourseTable");

        ObservableList Term = FXCollections.observableArrayList();
        Term.addAll("Term One", "Term Two", "Term Three");
        cmb_term.setItems(Term);
        cmb_term.setValue("Term One");

        cmb_course.setValue(0);

        ObservableList year = FXCollections.observableArrayList();
        year.addAll("First Year", "Second Year", "Greaduates");
        cmb_year.setItems(year);
        cmb_year.setValue("First Year");
    }

    public void createBook() {

        String main_query = "";
        
        String course = cmb_course.getSelectionModel().getSelectedItem().toString();
        String group = cmb_year.getSelectionModel().getSelectedItem().toString();
        
        String query = "SELECT * FROM Students_2017 WHERE StudentCourse = '"+course+"' AND YearOfStudy = '"+group+"' ";
        
        reportgenthree.ReportGenThree.LoadDescriptionSettingsForClassListPdf();

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage primary = (Stage) btn_create.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        String path = file.getAbsolutePath();
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
            
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            
            while(rst.next()){
            
                String adm = rst.getString("StudentRegCode");
                     
                main_query = "SELECT * FROM Fee_statements,Students_2017  WHERE State_user = '" + adm + "'  AND State_user = StudentRegCode";
                
                 if (file != null) {

                  FeeBookLetCreator.creator(main_query, path,adm);

                 }
            }
            
            conn.close();
            
            FeeBookLetCreator.mergerpdfs();
        
        }catch(SQLException exc){
        
        }

       

    }
    
    public void close(){
    
        
        Stage stage = (Stage)btn_create.getScene().getWindow();
        stage.close();
    
    
    }


}
