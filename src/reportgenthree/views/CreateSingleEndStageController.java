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
import AppFuctions.SingleEndStageproducer;
import com.jfoenix.controls.JFXTextField;
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

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class CreateSingleEndStageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXComboBox cmb_term;

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
    private JFXTextField txt_regno;

    @FXML
    private StackPane mypane;

    @FXML
    private JFXCheckBox check_all;

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
        lbl_error.setVisible(false);
        lbl_error1.setVisible(false);

        lst_examNames.setOnMousePressed(e -> {
            lbl_error.setVisible(false);
        });

        ObservableList groups = FXCollections.observableArrayList();
        groups.addAll("2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013");
        cmb_year.setItems(groups);
        String year = Integer.toString(LocalDate.now().getYear());
        cmb_year.setValue(year);

    }

    public void close() {

        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();

    }

    public void selectAll() {

        if (check_all.isSelected()) {

            lst_units.getCheckModel().checkAll();

        } else {

            lst_units.getCheckModel().clearChecks();

        }

    }

    public void create() {
        
        ReportGenThree.NavBar_Endstagedetailstaker();

        String regno = txt_regno.getText();

        String stage = "";

        if (!cmb_syear.getSelectionModel().isEmpty()) {

            if (!cmb_year.getSelectionModel().isEmpty()) {

                if (!regno.equals("")) {

                    if (!cmb_term.getSelectionModel().isEmpty()) {

                        String term = cmb_term.getSelectionModel().getSelectedItem().toString();
                        String syear = cmb_syear.getSelectionModel().getSelectedItem().toString();

                        int examsnumber = lst_examNames.getCheckModel().getCheckedItems().size();
                        if (examsnumber == 1 && examsnumber != 0) {
                            lbl_error.setVisible(false);

                            int unitsnumber = lst_units.getCheckModel().getCheckedItems().size();

                            String path = OpenSaveFileChooser();

                            if (unitsnumber != 0) {

                                lbl_error1.setVisible(false);
                                String examone = lst_examNames.getCheckModel().getCheckedItems().get(0).toString();

                                //System.out.println("Exam One " + examone + " Exam Two " + examtwo);
                                ObservableList units_toSelect = FXCollections.observableArrayList();
                                ObservableList checkedUnits = lst_units.getCheckModel().getCheckedItems();

                                ObservableList examone_scores = FXCollections.observableArrayList();

                                String r = "'";
                                for (int n = 0; n < checkedUnits.size(); n++) {

                                    units_toSelect.add(r + checkedUnits.get(n).toString().trim() + r);

                                }

                                String toSelectUnits = units_toSelect.toString();
                                toSelectUnits = toSelectUnits.substring(1, toSelectUnits.length() - 1);

                                String year = cmb_year.getSelectionModel().getSelectedItem().toString();

                                if (syear.equals("First Year")) {

                                    stage = "I";
                                    
                                }else{
                                
                                    stage = "II";
                                }

                                //System.out.println(toSelectUnits);
                                String query = "SELECT * FROM exam_2017 WHERE Adm_Number = '" + regno + "' "
                                        + "AND Exam_Term = '" + term + "' AND Exam_Name = '" + examone + "' AND Year = '" + year + "' "
                                        + "AND Unit_Name IN (" + toSelectUnits + ") AND Syear = '" + syear + "'";

                                Connection conn = sqlDataBaseConnection.sqliteconnect();
                                try {

                                    Statement st = conn.createStatement();

                                    ResultSet rst = st.executeQuery(query);
                                    int total = 0;

                                    while (rst.next()) {

                                        //System.out.println(rst.getString("Unit_Name") + " Score " + rst.getString("Exam_Score"));
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

                                    conn.close();

                                    if (examone_scores.size() >= 3) {

                                        String stdCourse = CoursesClass.getStudentCourse(regno);
                                        String stdname = AppFuctions.StudentsClass.getStdName(regno);

                                        SingleEndStageproducer.createPdfreport(regno, stdname, stdCourse, term, examone_scores, path,stage, "");

                                    } else {

                                        ReportGenThree.MissingMarksErrorWindow();

                                    }

                                } catch (SQLException exc) {

                                    System.out.println("" + exc);
                                }

                                //System.out.println(query);
                            } else {

                                lbl_error1.setVisible(true);
                                openDialog("Select units to include \n in exam Report Form");
                            }

                        } else {

                            lbl_error.setVisible(true);

                            openDialog("Select Examination");
                        }

                    } else {

                        openDialog("Term Is Missing..");

                    }

                } else {

                    openDialog("Enter Student Registration Number..");

                }

            } else {

                openDialog("Please Select Year");
            }

        } else {

            openDialog("Please Select Year Of Study");
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
        Label mylabel = new Label("Single Report Tool");

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

    public static String getUnitcode(String unitname) {

        String query = "SELECT UnitCode FROM UnitsTable WHERE UnitName = '" + unitname + "'";
        String ccode = "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            ccode = rst.getString("UnitCode");
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return ccode;
    }

}
