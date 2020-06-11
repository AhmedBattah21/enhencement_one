
package reportgenthree.views;
/**
 * 
 */
import AppFuctions.CoursesClass;
import AppFuctions.StudentsClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Academics_AddNewScoreController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane mypane;

    @FXML
    private JFXButton btn_close;

    @FXML
    private TextField txt_regno;

    @FXML
    private Label std_name;

    @FXML
    private Label std_course;

    @FXML
    private ComboBox cmb_examname;

    @FXML
    private JFXComboBox cmb_year;

    @FXML
    private ComboBox cmb_syear;

    @FXML
    private ComboBox cmb_term;

    @FXML
    private ComboBox cmb_unitname;

    @FXML
    private TextField txt_score;

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

    @FXML
    private Label two;

    @FXML
    private Label three;

    @FXML
    private Label four;

    @FXML
    private Label five;

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        //btn_clearScore.setOnAction(e -> clear());
        lbl_infor.setVisible(false);
        CoursesClass.populateComboBoxClasses(cmb_examname, "ExaminationName", "ExamNames");
        CoursesClass.populateComboBoxClasses(cmb_term, "SemisterName", "Semisters");
        CoursesClass.populateComboBoxClasses(cmb_unitname, "UnitName", "UnitsTable");

        ObservableList groups = FXCollections.observableArrayList();
        groups.addAll("2020", "2019", "2018", "2017", "2016", "2015", "2014", "2013");
        cmb_year.setItems(groups);

        String year = Integer.toString(LocalDate.now().getYear());
        cmb_year.setValue(year);

        ObservableList<String> study_year = FXCollections.observableArrayList();

        study_year.addAll("First Year", "Second Year");
        cmb_syear.setItems(study_year);

        txt_score.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,2}([\\.]\\d{0,5})?")) {
                txt_score.setText(oldValue);
            }
        });

        txt_regno.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                txt_regno.setText(oldValue);
            }
        });

    }

    /**
     * 
     */
    public void addRecord() {
        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;
        try {

            
            if (!txt_regno.getText().isEmpty()) {

                String regno = txt_regno.getText().trim();

                int score = Integer.parseInt(txt_score.getText().trim());

                String course = StudentsClass.getStdCourse(regno);

                if (!cmb_syear.getSelectionModel().isEmpty()) {

                    if (!cmb_year.getSelectionModel().isEmpty()) {

                        if (!regno.isEmpty()) {

                            if (!cmb_examname.getSelectionModel().isEmpty()) {

                                String examname = cmb_examname.getSelectionModel().getSelectedItem().toString();
                                two.setVisible(false);

                                if (!cmb_term.getSelectionModel().isEmpty()) {

                                    String examterm = cmb_term.getSelectionModel().getSelectedItem().toString();

                                    three.setVisible(false);
                                    if (!cmb_unitname.getSelectionModel().isEmpty()) {

                                        String examunit = cmb_unitname.getSelectionModel().getSelectedItem().toString();
                                        four.setVisible(false);

                                        String year = cmb_year.getSelectionModel().getSelectedItem().toString();

                                        String syear = cmb_syear.getSelectionModel().getSelectedItem().toString();

                                        if (!txt_score.getText().isEmpty()) {

                                            if (score <= getMaxScore(examname)) {

                                                if (!checkExamRecord()) {

                                                    String query = "INSERT INTO exam_2017 VALUES ('" + regno + "','" + examname + "',"
                                                            + "'" + examterm + "','" + score + "','" + course + "',null,'" + examunit + "','" + year + "','" + syear + "')";

                                                    Connection conn = sqlDataBaseConnection.sqliteconnect();
                                                    try {

                                                        Statement st = conn.createStatement();
                                                        st.executeUpdate(query);

                                                        conn.close();

                                                        String desc = "New Score Added For " + regno + " Examination " + year + " \n " + syear + " " + examunit + " " + examterm + " " + score;

                                                        sqlDataBaseConnection.add_activity("Activity:" + n, "Registration", desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                                                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
                                                        icon.setGlyphSize(150);
                                                        icon.setGlyphStyle("-fx-fill:#1e70c3");

                                                        openDialog("Student Score Has Been Saved",
                                                                "Great!!!! Score has Been Saved...safely ", regno, icon);

                                                    } catch (SQLException exc) {
                                                        System.out.println("" + exc);
                                                    }

                                                } else {

                                                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                                    icon.setGlyphSize(150);
                                                    icon.setGlyphStyle("-fx-fill:red");

                                                    openDialog("We cant Save The Score Due To The Following..",
                                                            "The Same record for the examination specification is alread in our Database"
                                                            + ", You Can Only Delete the record or edit it..", regno, icon);

                                                }

                                            } else {

                                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                                icon.setGlyphSize(150);
                                                icon.setGlyphStyle("-fx-fill:red");

                                                openDialog("We cant Save The Score Due To The Following..",
                                                        "This examination Selected Is Out Of " + getMaxScore(examname) + ", Your Score"
                                                        + "Must be either less than or equal to this Max Score (" + getMaxScore(examname) + ")", regno, icon);

                                            }
                                        } else {
                                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                            icon.setGlyphSize(150);
                                            icon.setGlyphStyle("-fx-fill:red");

                                            openDialog("No! No! We cant Save The Score Due To The Following..", "We cant Find the student Score and Some other Related fields"
                                                    + "are Empty too", regno,icon);
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
                            lbl_infor.setVisible(true);
                            lbl_infor.setGraphic(icon);
                            lbl_infor.setText("Not Found");

                        }

                    } else {

                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        icon.setGlyphSize(150);
                        icon.setGlyphStyle("-fx-fill:red");
                        openDialog("We cant Save The Score Due To The Following..", "There is no  Year /n "
                                + "Select Year..2018,2017,2019... at the top ", regno, icon);

                    }

                } else {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    icon.setGlyphSize(150);
                    icon.setGlyphStyle("-fx-fill:red");
                    openDialog("We cant Save The Score Due To The Following..", "There is no student Year /n "
                            + "Is Your Student a 1st Year or 2nd Year ? ", regno, icon);

                }
            } else {

                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                icon.setGlyphSize(150);
                icon.setGlyphStyle("-fx-fill:red");
                openDialog("We cant Save The Score Due To The Following..", "There is no student Registration Number ", "???", icon);
            }

        } catch (NumberFormatException exc) {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphSize(150);
            icon.setGlyphStyle("-fx-fill:red");
            openDialog("Examination Records", "Add Exam Score ", " ", icon);
        }

    }

    /**
     * 
     */
    public void SetEnabled() {

        String regno = txt_regno.getText().trim();

        if (StudentsClass.checkStudentTwo(regno)) {

            cmb_unitname.setDisable(false);
            cmb_examname.setDisable(false);
            cmb_term.setDisable(false);
            btn_addscore.setDisable(false);

            lbl_infor.setVisible(false);
            std_course.setText(CoursesClass.getStudentCourse(regno));
            std_name.setText(StudentsClass.getStdName(regno));

        } else {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
            lbl_infor.setVisible(true);
            lbl_infor.setGraphic(icon);
            lbl_infor.setText("Not Found");

            cmb_unitname.setDisable(true);
            cmb_examname.setDisable(true);
            cmb_term.setDisable(true);
            btn_addscore.setDisable(true);
            std_course.setText("****** Not Found    *******");
            std_name.setText("******  Not Found      *******");

        }

    }

    public void checkScore() {

        if (!cmb_examname.getSelectionModel().isEmpty()) {

            two.setGraphic(null);
            if (!cmb_term.getSelectionModel().isEmpty()) {
                three.setGraphic(null);

                if (!cmb_unitname.getSelectionModel().isEmpty()) {
                    four.setGraphic(null);

                    if (!cmb_syear.getSelectionModel().isEmpty()) {

                        if (!txt_score.getText().isEmpty()) {

                            int score = Integer.parseInt(txt_score.getText().trim());

                            String exam_name = cmb_examname.getSelectionModel().getSelectedItem().toString();

                            if (score >= 0 && score <= 100 && score <= getMaxScore(exam_name)) {

                                btn_addscore.setDisable(false);

                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
                                icon.setGlyphSize(30);
                                icon.setGlyphStyle("-fx-fill:skyblue");
                                lbl_infortwo.setText("Correct Score..");
                                lbl_infortwo.setGraphic(icon);

                            } else {

                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                icon.setGlyphSize(30);
                                icon.setGlyphStyle("-fx-fill:red");

                                lbl_infortwo.setGraphic(icon);
                                lbl_infortwo.setText("Invalid Score");
                                btn_addscore.setDisable(true);

                            }

                        } else {

                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                            icon.setGlyphSize(30);
                            icon.setGlyphStyle("-fx-fill:red");

                            lbl_infortwo.setGraphic(icon);
                            lbl_infortwo.setText("Invalid Score");
                            btn_addscore.setDisable(true);
                        }

                    } else {

                        cmb_syear.requestFocus();
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        icon.setGlyphStyle("-fx-fill:red");
                        five.setGraphic(icon);
                        txt_score.setText("");
                    }

                } else {

                    cmb_unitname.requestFocus();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    icon.setGlyphStyle("-fx-fill:red");
                    four.setGraphic(icon);
                    txt_score.setText("");
                }

            } else {

                cmb_term.requestFocus();
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                icon.setGlyphStyle("-fx-fill:red");
                three.setGraphic(icon);
                txt_score.setText("");
            }

        } else {

            cmb_examname.requestFocus();
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphStyle("-fx-fill:red");
            two.setGraphic(icon);
            txt_score.setText("");

        }
    }

    public Boolean checkExamRecord() {

        boolean result = false;

        String regno = txt_regno.getText().trim();
        String examname = cmb_examname.getSelectionModel().getSelectedItem().toString();
        String examterm = cmb_term.getSelectionModel().getSelectedItem().toString();
        String examunit = cmb_unitname.getSelectionModel().getSelectedItem().toString();
        String syear = cmb_syear.getSelectionModel().getSelectedItem().toString();
        String exyear = cmb_year.getSelectionModel().getSelectedItem().toString();

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + regno + "'"
                + "AND Exam_Name = '" + examname + "' AND Exam_Term = '" + examterm + "' AND"
                + " Unit_Name = '" + examunit + "' AND Syear = '" + syear + "' AND Year = '" + exyear + "'";

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

    public int getMaxScore(String examname) {

        String query = "SELECT MaxScore FROM ExamNames WHERE ExaminationName = '" + examname + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        int maxscore = 0;
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                maxscore = rst.getInt("MaxScore");
            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);

        }

        return maxscore;

    }

    public void clear() {

        txt_regno.setText(null);
        txt_score.setText(null);
        std_course.setText(null);

    }

    public static String getYear(String regcode) {

        String query = "SELECT YearOfStudy FROM students_2017 WHERE StudentRegCode = '" + regcode + "'";
        String ccode = "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            ccode = rst.getString("YearOfStudy");
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return ccode;
    }

    public void openDialog(String title, String message, String adm, FontAwesomeIconView icon) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Academics_AddNewScoreController.class.getResource("Dialogs/Datasaved_dialog.fxml"));
            StackPane pane = loader.load();
            Datasaved_dialogController cc = loader.getController();
            cc.getLbl_infor().setText(message);
            cc.getLbl_icon().setGraphic(icon);
            cc.getLbl_head().setText(title + " Adm No. " + adm);

            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            content.autosize();

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Academics_AddNewScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
