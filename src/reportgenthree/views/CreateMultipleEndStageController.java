/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.controlsfx.control.CheckListView;
import AppFuctions.Functions;
import AppFuctions.MultipleEndStageproducer;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import reportgenthree.ReportGenThree;
import static reportgenthree.views.CreateSingleEndStageController.getUnitcode;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class CreateMultipleEndStageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox cmb_term;
    
    @FXML
    private JFXCheckBox check_all;
    
     @FXML
    private JFXComboBox cmb_year;
     
      @FXML
    private JFXComboBox cmb_syear;

    @FXML
    private CheckListView lst_examNames;

    @FXML
    private CheckListView lst_units;

    @FXML
    private JFXButton btn_create;

    @FXML
    private JFXButton btn_cancel;

    @FXML
    private JFXButton btn_close;

    @FXML
    private Label lbl_error;

    @FXML
    private Label lbl_error1;

    @FXML
    private JFXComboBox cmb_group;
    
    @FXML
    private StackPane mypane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ObservableList<String> rows = FXCollections.observableArrayList();

        rows.add("END STAGE");
        lst_examNames.setItems(rows);
        
        ObservableList<String> study_year = FXCollections.observableArrayList();

        study_year.addAll("First Year", "Second Year");
        cmb_syear.setItems(study_year);

        
        Functions.populateCheckListView(lst_units, "UnitsTable", "UnitName");
        CoursesClass.populateComboBoxClasses(cmb_term, "SemisterName", "Semisters");
       
        CoursesClass.populateComboBoxClasses(cmb_group, "CourseName", "CourseTable");
        lbl_error.setVisible(false);
        lbl_error1.setVisible(false);

        lst_examNames.setOnMousePressed(e -> {
            lbl_error.setVisible(false);
        });
        
        ObservableList groups = FXCollections.observableArrayList();
        groups.addAll("2020", "2019","2018", "2017", "2016", "2015", "2014", "2013");
        cmb_year.setItems(groups);
        String year =Integer.toString(LocalDate.now().getYear());
        cmb_year.setValue(year);
        
    }

    public void close() {

        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();

    }
    
    public void selectAll(){
    
        if(check_all.isSelected()){
        
            lst_units.getCheckModel().checkAll();
        
        }else{
            
             lst_units.getCheckModel().clearChecks();
        
        }
    
    }

    public void create() {

         String stage = "";
          ReportGenThree.NavBar_Endstagedetailstaker();
        
        if(!cmb_year.getSelectionModel().isEmpty()){
            
            String year = cmb_year.getSelectionModel().getSelectedItem().toString();
        
        if (!cmb_group.getSelectionModel().isEmpty()) {

            String group = cmb_group.getSelectionModel().getSelectedItem().toString();

            if (!cmb_term.getSelectionModel().isEmpty()) {

                String term = cmb_term.getSelectionModel().getSelectedItem().toString();

                int examsnumber = lst_examNames.getCheckModel().getCheckedItems().size();
                
                String path = OpenSaveFileChooser();
                
                if (examsnumber == 1 && examsnumber != 0) {
                    lbl_error.setVisible(false);

                    int unitsnumber = lst_units.getCheckModel().getCheckedItems().size();

                    if (unitsnumber != 0) {

                        lbl_error1.setVisible(false);
                        String examone = lst_examNames.getCheckModel().getCheckedItems().get(0).toString();
                       
                        //System.out.println("Exam One " + examone + " Exam Two " + examtwo);

                        ObservableList units_toSelect = FXCollections.observableArrayList();
                        ObservableList checkedUnits = lst_units.getCheckModel().getCheckedItems();

                        ObservableList examone_scores = FXCollections.observableArrayList();
                        ObservableList regnos_missingreports = FXCollections.observableArrayList();

                        int numberofreports = 0;
                        int numberofmissingmarks = 0;

                        String r = "'";
                        for (int n = 0; n < checkedUnits.size(); n++) {

                            units_toSelect.add(r + checkedUnits.get(n).toString().trim() + r);

                        }

                        String toSelectUnits = units_toSelect.toString();
                        toSelectUnits = toSelectUnits.substring(1, toSelectUnits.length() - 1);

                        //System.out.println(toSelectUnits);
                        Connection conn = sqlDataBaseConnection.sqliteconnect();

                        String queryone = "SELECT DISTINCT Adm_Number FROM exam_2017 WHERE Std_Course = '" + group + "'";
                        try {

                            Statement stone = conn.createStatement();
                            ResultSet rstone = stone.executeQuery(queryone);
                            
                            

                            while (rstone.next()) {

                                examone_scores.clear();
                                numberofreports++;

                                String regno = rstone.getString("Adm_Number");

                                String syear = cmb_syear.getSelectionModel().getSelectedItem().toString();

                                if (syear.equals("First Year")) {

                                    stage = "I";
                                }else{
                                
                                    stage = "II";
                                }

                                //System.out.println(toSelectUnits);
                                String query = "SELECT * FROM exam_2017 WHERE Adm_Number = '" + regno + "' "
                                        + "AND Exam_Term = '" + term + "' AND Exam_Name = '" + examone + "' AND Year = '" + year + "' "
                                        + "AND Unit_Name IN (" + toSelectUnits + ") AND Syear = '" + syear + "'";

                                Statement st = conn.createStatement();

                                ResultSet rst = st.executeQuery(query);

                                int total = 0;
                                
                                while (rst.next()) {
 
                              
                                        if (rst.getString("Unit_Name").equals("Trade Theory") || rst.getString("Unit_Name").equals("Trade Practise")) {

                                            String code = getUnitcode(rst.getString("Unit_Name"));

                                            examone_scores.add(rst.getString("Unit_Name") + "-" + rst.getString("Exam_Score")
                                                    + "-" + code);

                                        } else {

                                            int value = Integer.parseInt(rst.getString("Exam_Score"));

                                            total = total + value;

                                        }

                                }
                                
                                 int finalscore = total / 6;

                                    examone_scores.add("General Paper" + "-" + finalscore
                                            + "-" + "101");

                                
                                if (examone_scores.size() >= 3) {

                                    String stdCourse = CoursesClass.getStudentCourse(regno);
                                    String stdname = AppFuctions.StudentsClass.getStdName(regno);
                                    
                                    

                                    MultipleEndStageproducer.createPdfreport(regno, stdname, stdCourse, term, examone_scores, path,"","");

                                } else {

                                    regnos_missingreports.add(regno);

                                }

                            }

                            if (regnos_missingreports.size() != 0) {

                                //ReportGenThree.MissingMarksErrorWindow();
                            }

                            ProcessCompleteMultipleEndStageController.setMissingMarks(regnos_missingreports.size());
                            ProcessCompleteMultipleEndStageController.
                                    setReportsMarks(numberofreports-regnos_missingreports.size());

                            ReportGenThree.ProcessCompleteMultipleEndstage();

                            conn.close();

                        } catch (SQLException exc) {

                            System.out.println("" + exc);
                        }

                        //System.out.println(query);
                    } else {

                        lbl_error1.setVisible(true);
                        openDialog("Please Select Units To Be Included In The Report");
                    }

                } else {

                    lbl_error.setVisible(true);
                    openDialog("Please Select Exams \n Maximum Of Two \n 1) Out of 30 \n 2) Out Of 70");
                }

            } else {
                
                
                openDialog("Please Select Term Of The Year.. ");

            }

        } else {
            
             openDialog("Please Select The Target Group/Class \n Of Students ");

        }
        
        }else{
        
         openDialog("Please Select Year \n ");
        }

    }

    public String OpenSaveFileChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage primary = (Stage) btn_create.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        String path = file.getAbsolutePath();
        return path;

    }
    
    
     public void openDialog(String message) {

        JFXDialogLayout content = new JFXDialogLayout();
        Label mylabel = new Label("Multiple Report Tool");

        content.setHeading(mylabel);
        content.setAlignment(Pos.CENTER);
        content.setBody(new Text(message));

        JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonCancel = new JFXButton("Exit");
        buttonCancel.setStyle("-fx-border-color:seagreen;-fx-border-width:1");

        buttonCancel.setOnAction(e -> dlog.close());

        content.setActions(buttonCancel);
        content.autosize();
        content.setPadding(new Insets(6, 0, 6, 0));
        content.setLayoutX(50);
        content.setLayoutY(50);

        dlog.show();

    }
    
   

}
