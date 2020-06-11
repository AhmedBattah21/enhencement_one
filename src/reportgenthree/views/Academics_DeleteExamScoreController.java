/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import AppFuctions.StudentsClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Connection.sqlDataBaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;


public class Academics_DeleteExamScoreController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_close;

    @FXML
    private JFXTextField txt_regno;

    @FXML
    private JFXTextField std_course;

    @FXML
    private JFXComboBox cmb_examname;

    @FXML
    private JFXComboBox cmb_term;

    @FXML
    private JFXComboBox cmb_unitname;

    @FXML
    private JFXTextField txt_score;

    @FXML
    private JFXButton btn_addscore;

    @FXML
    private JFXButton btn_clearScore;

    @FXML
    private JFXButton btn_viewScores;

    @FXML
    private Label lbl_infor;

    @FXML
    private Label lbl_infortwo;

    /**
     * they are violeting
     */
    @FXML
    private Label one;

    @FXML
    private Label two;

    @FXML
    private Label three;

    @FXML
    private Label four;

    @Override

    public void initialize(URL url, ResourceBundle rb) {
      
        lbl_infor.setVisible(false);
        CoursesClass.populateComboBoxClasses(cmb_examname, "ExaminationName", "ExamNames");
        CoursesClass.populateComboBoxClasses(cmb_term, "SemisterName", "Semisters");
        CoursesClass.populateComboBoxClasses(cmb_unitname, "UnitName", "UnitsTable");

    }

    
/**
 * deleteRecord
 */
    public void DeleteRecord() {

        try {
           
            if (!txt_regno.getText().trim().equals("")) {
                
                String regno = txt_regno.getText().trim();

                if (!std_course.getText().trim().equals("")) {
                    
                    String course = std_course.getText();

                    one.setVisible(false);
                    if (!cmb_examname.getSelectionModel().isEmpty()) {

                        String examname = cmb_examname.getSelectionModel().getSelectedItem().toString();
                        two.setVisible(false);

                        if (!cmb_term.getSelectionModel().isEmpty()) {

                            String examterm = cmb_term.getSelectionModel().getSelectedItem().toString();

                            three.setVisible(false);
                            if (!cmb_unitname.getSelectionModel().isEmpty()) {

                                String examunit = cmb_unitname.getSelectionModel().getSelectedItem().toString();
                                four.setVisible(false);

                                

                                    if(checkExamRecord()){
                                        
                                    String query = "DELETE FROM exam_2017 WHERE Exam_Name = '"+examname+"'"
                                            + " AND Exam_Term ='"+examterm+"' AND Std_Course = '"+course+"' AND "
                                            + " Unit_Name = '"+examunit+"'"
                                            + " AND  Adm_Number = '"+regno+"'";
                                    
                                    Connection conn = sqlDataBaseConnection.sqliteconnect();
                                    try{
                                        
                                        Statement st = conn.createStatement();
                                        st.executeUpdate(query);
                                        
                                        conn.close();
                                        
                                        Notifications.create()
                                            .title("Process Successful")
                                            .text("Record Has Been Deleted")
                                            .position(Pos.TOP_RIGHT)
                                            .hideAfter(Duration.seconds(5))
                                            .darkStyle()
                                            .show();
                                        
                                    }catch(SQLException exc){
                                    System.out.println(""+exc);
                                    }
                                    
                                    }else{
                                    
                                        Notifications.create()
                                            .title("Process Failed")
                                            .text("The Record Does Not Exist")
                                            .position(Pos.TOP_RIGHT)
                                            .hideAfter(Duration.seconds(5))
                                            .darkStyle()
                                            .show();
                                    }
                                    
                                    
                                
                            } else {

                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);

                                four.setGraphic(icon);
                            }

                        } else {

                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);

                            three.setGraphic(icon);

                        }

                    } else {

                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);

                        two.setGraphic(icon);
                    }
                } else {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);

                    one.setGraphic(icon);

                }

            } else {

                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                lbl_infor.setVisible(true);
                lbl_infor.setGraphic(icon);
                lbl_infor.setText("Not Found");

            }

        } catch (NumberFormatException exc) {

            Notifications.create()
                    .title("Process Failed")
                    .text("No Score")
                    .position(Pos.TOP_RIGHT)
                    .hideAfter(Duration.seconds(5))
                    .darkStyle()
                    .show();
        }

    }

    public void SetEnabled() {

        String regno = txt_regno.getText().trim();

        if (StudentsClass.checkStudentTwo(regno)) {

            std_course.setEditable(true);
            cmb_unitname.setDisable(false);
            cmb_examname.setDisable(false);
            cmb_term.setDisable(false);
            btn_addscore.setDisable(false);
            std_course.setDisable(false);
            lbl_infor.setVisible(false);
            std_course.setText(CoursesClass.getStudentCourse(regno));

        } else {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
            lbl_infor.setVisible(true);
            lbl_infor.setGraphic(icon);
            lbl_infor.setText("Not Found");
            std_course.setDisable(true);
            cmb_unitname.setDisable(true);
            cmb_examname.setDisable(true);
            cmb_term.setDisable(true);
            btn_addscore.setDisable(true);
            std_course.setText("");

           
        }

    }

    

    public Boolean checkExamRecord() {

        boolean result = false;

        String regno = txt_regno.getText().trim();
        String examname = cmb_examname.getSelectionModel().getSelectedItem().toString();
        String examterm = cmb_term.getSelectionModel().getSelectedItem().toString();
        String examunit = cmb_unitname.getSelectionModel().getSelectedItem().toString();

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + regno + "'"
                + "AND Exam_Name = '" + examname + "' AND Exam_Term = '" + examterm + "' AND"
                + " Unit_Name = '" + examunit + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();
        } catch (SQLException exc) {

            System.out.println("" + exc);

        }
        return result;

    }
    
    
    
    
    public void clear(){
    
      txt_regno.setText("");
      txt_score.setText("");
      std_course.setText("");
    
    
    }

}
