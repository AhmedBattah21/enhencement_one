/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import AppFuctions.Functions;
import static AppFuctions.Functions.getMaxScore;
import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import reportgenthree.objects.StprofileExamination_details;
import reportgenthree.views.Dialogs.Dialogs_functions;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StprofileExamination_detailsController implements Initializable {

    @FXML
    private Label lbl_schoolname;

    @FXML
    private Label lbl_boxnumber;

    @FXML
    private Label lbl_coursename;

    @FXML
    private Label lbl_examdetails;

    @FXML
    private TableView<StprofileExamination_details> table;

    @FXML
    private TableColumn col_id;

    @FXML
    private TableColumn col_no;

    @FXML
    private TableColumn col_unitname;

    @FXML
    private TableColumn col_unitscore;

    @FXML
    private TableColumn col_remark;

    @FXML
    private Button btn_save;

    @FXML
    private JFXTextField txt_oldvalue;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private Label lbl_inforone;

    @FXML
    private ComboBox cmb_unitname;

    @FXML
    private JFXTextField txt_addscore;

    @FXML
    private JFXTextField txt_recordid;

    @FXML
    private JFXTextField txt_newecord;

    @FXML
    private JFXButton btn_addnewscore;

    @FXML
    private Label lbl_infortwo;

    private TabPane parentTab;
    private Tab mytab;

    String adm_number;
    String examname;
    String term;
    String year;
    String std_year;

    final ObservableList<StprofileExamination_details> data = FXCollections.observableArrayList();
    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);

    Stage addexam_stage;

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        col_unitname.setCellValueFactory(new PropertyValueFactory<>("unitname"));
        col_no.setCellValueFactory(new PropertyValueFactory<>("count"));
        col_unitscore.setCellValueFactory(new PropertyValueFactory<>("unitscore"));
        col_remark.setCellValueFactory(new PropertyValueFactory<>("remark"));

        error.setGlyphStyle("-fx-fill:red");
        error.setGlyphSize(30);

        infor.setGlyphStyle("-fx-fill:seagreen");
        infor.setGlyphSize(30);

        lbl_schoolname.setText(SystemNameSetterController.getSname());
        lbl_boxnumber.setText(SystemNameSetterController.getbox());

        table.setOnMouseClicked(e -> {

            try {
                txt_recordid.setText("" + table.getSelectionModel().getSelectedItem().getCount());
                txt_oldvalue.setText(table.getSelectionModel().getSelectedItem().getUnitscore());
                lbl_inforone.setText("");
                lbl_inforone.setGraphic(null);
                
                lbl_infortwo.setText("");
                lbl_infortwo.setGraphic(null);

            } catch (NullPointerException exc) {

                lbl_inforone.setText("No Record...");
                lbl_inforone.setGraphic(error);
            }

        });

        CoursesClass.populateComboBoxClasses(cmb_unitname, "UnitName", "UnitsTable");

    }

    public String getAdm_number() {
        return adm_number;
    }

    public void setAdm_number(String adm_number) {
        this.adm_number = adm_number;
    }

    public void getData() {
        data.clear();
        table.getItems().clear();

        lbl_coursename.setText(AppFuctions.StudentsClass.getStdCourse(adm_number));

        String query = "SELECT * FROM exam_2017 WHERE Exam_Name = '" + examname + "'  AND Exam_Term = '" + term + "' AND Year = '" + year + "' AND "
                + "Syear = '" + std_year + "' AND  Adm_Number  = '" + adm_number + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            int count = 1;

            while (rst.next()) {

                String unitid = rst.getString("Exam_Id");
                String unitname = rst.getString("Unit_Name");
                String unitscore = rst.getString("Exam_Score");
                int score = Integer.parseInt(unitscore);
                String unitremark = getComment(score);

                data.add(new StprofileExamination_details(count, unitid, unitname, unitscore, unitremark));

                count++;

            }

            conn.close();

            table.setItems(data);

            lbl_examdetails.setText(year.toUpperCase() + "  " + term.toUpperCase() + "  " + examname.toUpperCase() + "  " + std_year.toUpperCase() + " Report");

        } catch (SQLException exc) {

        }

    }

    @FXML
    void Edit_Record(ActionEvent event) {

        if (!table.getSelectionModel().isEmpty()) {

            StprofileExamination_details details = table.getSelectionModel().getSelectedItem();
            String rec_id = details.getRecordid();
            String new_rec = txt_newecord.getText();

            try {

                lbl_inforone.setVisible(true);

                if (!rec_id.isEmpty() && !new_rec.isEmpty()) {

                    int maxscore = Functions.getMaxScore(examname);
                    int record = Integer.parseInt(new_rec);

                    if (record <= maxscore) {

                        String query = "UPDATE exam_2017 SET Exam_Score = '" + record + "' WHERE Exam_Id = '" + rec_id + "' AND Adm_Number = '" + adm_number + "'"
                                + "AND Exam_Name = '" + examname + "'  AND Exam_Term = '" + term + "' AND Year = '" + year + "' AND "
                                + "Syear = '" + std_year + "'";

                        try {

                            try (Connection conn = sqlDataBaseConnection.sqliteconnect()) {
                                Statement st = conn.createStatement();
                                int value = st.executeUpdate(query);

                                if (value >= 1) {

                                    lbl_examdetails.setText(year.toUpperCase() + "  " + term.toUpperCase() + "  "
                                            + "" + examname.toUpperCase() + "  " + std_year.toUpperCase() + " Report");

                                    lbl_inforone.setGraphic(infor);
                                    lbl_inforone.setText("Score Changed To  " + new_rec);
                                    getData();

                                } else {

                                    lbl_inforone.setText(rec_id + "  Does Not Exist In The Below Table...");
                                    lbl_inforone.setGraphic(error);

                                }
                            }

                        } catch (SQLException exc) {

                            System.out.println(" Error " + exc);
                            lbl_inforone.setGraphic(error);

                        }

                    } else {

                        lbl_inforone.setText("Score Is Invalid...Highest Score Is " + maxscore + " !!");
                        lbl_inforone.setGraphic(error);

                    }

                } else {

                    lbl_inforone.setText("Please Enter New Record and Its Record Id");
                    lbl_inforone.setGraphic(error);

                }

            } catch (NumberFormatException exc) {

                lbl_inforone.setText("Invalid Records....");
                lbl_inforone.setGraphic(error);

            }

        }

    }

    public void delete_data() {

        if (!table.getSelectionModel().isEmpty()) {

            StprofileExamination_details details = table.getSelectionModel().getSelectedItem();

            String result = Dialogs_functions.get_custom_confirm("Delete Examination Record (Can Not Be Reversed)" + details.getCount());

            if (result.equals("continue")) {

                delete_record(details.getRecordid());
                lbl_inforone.setText("Record Deleted");
                lbl_inforone.setGraphic(infor);

            } else {

                lbl_inforone.setText("Cancelled");
            }
        } else {

            lbl_inforone.setText("No record Selected..");
            lbl_inforone.setGraphic(error);

        }

    }

    public void edit_record() {

        StprofileExamination_details details = table.getSelectionModel().getSelectedItem();

        txt_recordid.setText(""+details.getCount());

    }

    public void checkScoreone() {

        try {
            int score = Integer.parseInt(txt_newecord.getText().trim());

            if (score >= 0 && score <= getMaxScore(examname)) {

                btn_edit.setDisable(false);

                lbl_inforone.setGraphic(infor);

                lbl_inforone.setText("Correct Score");

            } else {

                lbl_inforone.setGraphic(error);
                lbl_inforone.setText("Max Score Is " + getMaxScore(examname));
                btn_edit.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_inforone.setGraphic(error);
            lbl_inforone.setText("Invalid Mark");
            btn_edit.setDisable(true);

        }

    }

    public void checkScoretwo() {

        try {
            int score = Integer.parseInt(txt_addscore.getText().trim());

            if (score >= 0 && score <= getMaxScore(examname)) {

                btn_addnewscore.setDisable(false);

                lbl_infortwo.setGraphic(infor);

                lbl_infortwo.setText("Correct Score");

            } else {

                lbl_infortwo.setGraphic(error);
                lbl_infortwo.setText("Max Score Is " + getMaxScore(examname));
                btn_addnewscore.setDisable(true);

            }

        } catch (NumberFormatException exc) {

            lbl_infortwo.setGraphic(error);
            lbl_infortwo.setText("Invalid Mark");
            btn_addnewscore.setDisable(true);

        }

    }

    public Boolean checkExamRecord() {

        boolean result = false;
        String unit_name = cmb_unitname.getSelectionModel().getSelectedItem().toString();

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + adm_number + "'"
                + "AND Exam_Name = '" + examname + "' AND Exam_Term = '" + term + "' AND"
                + " Unit_Name = '" + unit_name + "' AND Syear = '" + std_year + "' AND Year = '" + year + "'";

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

    @FXML
    void close(ActionEvent event) {

        parentTab.getTabs().remove(mytab);

    }

    public void refresh() {

        getData();

    }

    public void add_examscore() {

        if (!txt_addscore.getText().isEmpty()) {

            int score = Integer.parseInt(txt_addscore.getText());
            String unit_name = cmb_unitname.getSelectionModel().getSelectedItem().toString();

            if (score <= getMaxScore(examname)) {

                if (!checkExamRecord()) {

                    String std_course = StudentsClass.getStdCourse(adm_number);

                    String query = "";

                    query = "INSERT INTO exam_2017 VALUES ('" + adm_number + "','" + examname + "',"
                            + "'" + term + "','" + score + "','" + std_course + "',null,'" + unit_name + "','" + year + "','" + std_year + "')";

                    Connection conn = sqlDataBaseConnection.sqliteconnect();

                    try {

                        Statement st = conn.createStatement();
                        st.executeUpdate(query);

                        conn.close();

                        lbl_infortwo.setGraphic(infor);
                        lbl_infortwo.setText("Score Saved");

                        getData();

                    } catch (SQLException exc) {
                        System.out.println("" + exc);
                    }

                } else {

                    lbl_infortwo.setGraphic(error);
                    lbl_infortwo.setText("Score Alread Exist");
                    lbl_infortwo.setGraphic(error);

                }

            } else {

                lbl_infortwo.setGraphic(error);
                lbl_infortwo.setText("Score Is Invalid");
                lbl_infortwo.setGraphic(error);
            }

        } else {

            lbl_infortwo.setGraphic(error);
        }

    }

    public void delete_record(String rec_id) {

        String query = "DELETE FROM exam_2017 WHERE Exam_Id = '" + rec_id + "' ";

        try {

            try (Connection conn = sqlDataBaseConnection.sqliteconnect()) {
                Statement st = conn.createStatement();
                int value = st.executeUpdate(query);

                if (value >= 1) {

                    lbl_examdetails.setText(year.toUpperCase() + "  " + term.toUpperCase() + "  "
                            + "" + examname.toUpperCase() + "  " + std_year.toUpperCase() + " Report");

                    lbl_inforone.setGraphic(infor);
                    getData();

                } else {

                    lbl_inforone.setText(rec_id + "  Could Not Delete Record...");
                    lbl_inforone.setGraphic(error);

                }
            }

        } catch (SQLException exc) {

            System.out.println(" Error " + exc);
            lbl_inforone.setGraphic(error);

        }

    }

    public String getComment(int p_score) {
        String comment = "poor";

        if (p_score >= 80) {

            comment = "Distinction";

        } else if (p_score >= 60) {

            comment = "Credit";

        } else if (p_score >= 40) {

            comment = "Pass";

        } else if (p_score >= 30) {

            comment = "Reffer";

        } else {

            comment = "Fail";

        }

        return comment;
    }

    public String getExamname() {
        return examname;
    }

    public void setExamname(String examname) {
        this.examname = examname;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
    }

    public TabPane getParentTab() {
        return parentTab;
    }

    public void setParentTab(TabPane parentTab) {
        this.parentTab = parentTab;
    }

    public Tab getMytab() {
        return mytab;
    }

    public void setMytab(Tab mytab) {
        this.mytab = mytab;
    }

}
