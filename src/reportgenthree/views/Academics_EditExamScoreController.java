
package reportgenthree.views;

import AppFuctions.CoursesClass;
import AppFuctions.StudentsClass;
import com.jfoenix.controls.JFXButton;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

public class Academics_EditExamScoreController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane mypane;

    @FXML
    private TextField txt_regno;

    @FXML
    private Label one;

    @FXML
    private Label lbl_infor;

    @FXML
    private TextField txt_eyear;

    @FXML
    private Label std_name;

    @FXML
    private Label std_course;

    @FXML
    private ComboBox<?> cmb_examname;

    @FXML
    private Label two;

    @FXML
    private ComboBox<?> cmb_term;

    @FXML
    private Label three;

    @FXML
    private ComboBox cmb_unitname;

    @FXML
    private Label four;

    @FXML
    private TextField txt_score;

    @FXML
    private Label lbl_infortwo;

    @FXML
    private JFXButton btn_addscore;

    @FXML
    private JFXButton btn_clearScore;

    @Override

    public void initialize(URL url, ResourceBundle rb) {

        CoursesClass.populateComboBoxClasses(cmb_examname, "ExaminationName", "ExamNames");
        CoursesClass.populateComboBoxClasses(cmb_term, "SemisterName", "Semisters");
        CoursesClass.populateComboBoxClasses(cmb_unitname, "UnitName", "UnitsTable");
        btn_addscore.setDisable(true);

        txt_score.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,2}([\\.]\\d{0,5})?")) {
                txt_score.setText(oldValue);
            }
        });

        txt_eyear.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,4}([\\.]\\d{0,5})?")) {
                txt_eyear.setText(oldValue);
            }
        });

        txt_regno.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,5}([\\.]\\d{0,5})?")) {
                txt_regno.setText(oldValue);
            }
        });

    }

    //violet method rules
    public void EditRecord() {

        try {

            if (!txt_regno.getText().isEmpty()) {

                String regno = txt_regno.getText().trim();

                String course = AppFuctions.StudentsClass.getStdCourse(regno);

                if (!txt_eyear.getText().isEmpty()) {

                    String eyear = txt_eyear.getText();

                    if (!cmb_examname.getSelectionModel().isEmpty()) {

                        String examname = cmb_examname.getSelectionModel().getSelectedItem().toString();
                        two.setVisible(false);

                        if (!cmb_term.getSelectionModel().isEmpty()) {

                            String examterm = cmb_term.getSelectionModel().getSelectedItem().toString();

                            three.setVisible(false);
                            if (!cmb_unitname.getSelectionModel().isEmpty()) {

                                String examunit = cmb_unitname.getSelectionModel().getSelectedItem().toString();
                                four.setVisible(false);

                                if (!txt_score.getText().isEmpty()) {

                                    int score = Integer.parseInt(txt_score.getText().trim());

                                    if (score <= getMaxScore(examname)) {

                                        if (checkExamRecord()) {

                                            String query = "UPDATE exam_2017 SET Exam_Score = '" + score + "' WHERE"
                                                    + " Exam_Name = '" + examname + "' AND"
                                                    + " Exam_Term ='" + examterm + "' AND"
                                                    + " Std_Course = '" + course + "' AND Unit_Name = '" + examunit + "'"
                                                    + " AND Adm_Number = '" + regno + "' AND Year = '" + eyear + "' ";

                                            Connection conn = sqlDataBaseConnection.sqliteconnect();
                                            try {

                                                Statement st = conn.createStatement();
                                                st.executeUpdate(query);

                                                conn.close();

                                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
                                                icon.setGlyphSize(150);
                                                icon.setGlyphStyle("-fx-fill:skyblue");
                                                openDialog("Examination Editor Infor", "Greet!!! Score Changed...Edited..Saved.", regno, icon);

                                            } catch (SQLException exc) {
                                                
                                                //
                                                
                                            }

                                        } else {

                                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                            icon.setGlyphSize(150);
                                            icon.setGlyphStyle("-fx-fill:red");
                                            openDialog("Examination Editor Infor", "Could Not Edit The Record, Its Unknown", regno, icon);
                                        }

                                    } else {

                                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                        icon.setGlyphSize(150);
                                        icon.setGlyphStyle("-fx-fill:red");
                                        openDialog("Examination Editor Infor", "Examination Score Is Invalid!!", regno, icon);

                                    }
                                } else {

                                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                    icon.setGlyphSize(150);
                                    icon.setGlyphStyle("-fx-fill:red");
                                    openDialog("Examination Editor Infor", "Please Enter Examination Score!!!!", regno, icon);
                                }

                            } else {

                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                icon.setGlyphSize(150);
                                icon.setGlyphStyle("-fx-fill:red");
                                openDialog("Examination Editor Infor", "No No! Please Select Unit Name", regno, icon);
                            }

                        } else {

                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                            icon.setGlyphSize(150);
                            icon.setGlyphStyle("-fx-fill:red");
                            openDialog("Examination Editor Infor", "Select Examination Term...e.g Term One", regno, icon);

                        }

                    } else {

                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        icon.setGlyphSize(150);
                        icon.setGlyphStyle("-fx-fill:red");
                        openDialog("Examination Editor Infor", "Select Examination Name...e.g Mid Term", regno, icon);
                    }

                } else {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    icon.setGlyphSize(150);
                    icon.setGlyphStyle("-fx-fill:red");
                    openDialog("Examination Editor Infor", "Enter Examination Year e.g 2018", regno, icon);

                }

            } else {

                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                lbl_infor.setVisible(true);
                lbl_infor.setGraphic(icon);
                lbl_infor.setText("Not Found");

            }

        } catch (NumberFormatException exc) {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphSize(150);
            icon.setGlyphStyle("-fx-fill:red");
            openDialog("Examination Editor Infor", "Process Failed", "??", icon);
        }

    }

    //violeting method rules
    public void SetEnabled() {

        String regno = txt_regno.getText().trim();

        if (StudentsClass.checkStudentTwo(regno)) {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
            lbl_infor.setGraphic(icon);
            lbl_infor.setText("Found..");
            cmb_unitname.setDisable(false);
            cmb_examname.setDisable(false);
            cmb_term.setDisable(false);

            std_course.setDisable(false);
            std_course.setText(CoursesClass.getStudentCourse(regno));
            std_name.setText(StudentsClass.getStdName(regno));

        } else {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
            lbl_infor.setGraphic(icon);
            lbl_infor.setText("Not Found");
            cmb_unitname.setDisable(true);
            cmb_examname.setDisable(true);
            cmb_term.setDisable(true);

            std_course.setText("*************Not Found********");
            std_name.setText("*************Not Found********");

        }

    }

    public void check_eyear() {

        if (!txt_eyear.getText().isEmpty()) {

            one.setGraphic(null);
        }
        
        //

    }

    public void check_examname() {

        if (!cmb_examname.getSelectionModel().isEmpty()) {

            two.setGraphic(null);
        }

        //
    }

    public void check_term() {

        if (!cmb_term.getSelectionModel().isEmpty()) {

            three.setGraphic(null);
        }

        
        //
    }

    public void check_unitname() {

        if (!cmb_unitname.getSelectionModel().isEmpty()) {

            four.setGraphic(null);
        }

        
        //
    }

    public void checkScore() {

        if (!txt_regno.getText().isEmpty()) {

            if (!txt_eyear.getText().isEmpty()) {

                one.setGraphic(null);
                if (!cmb_examname.getSelectionModel().isEmpty()) {

                    two.setGraphic(null);
                    String exam_name = cmb_examname.getSelectionModel().getSelectedItem().toString();
                    if (!cmb_term.getSelectionModel().isEmpty()) {

                        three.setGraphic(null);

                        if (!cmb_unitname.getSelectionModel().isEmpty()) {

                            four.setGraphic(null);

                            if (!txt_score.getText().isEmpty()) {

                                int score = Integer.parseInt(txt_score.getText().trim());

                                if (score >= 0 && score <= getMaxScore(exam_name)) {

                                    btn_addscore.setDisable(false);
                                    lbl_infortwo.setText("Correct Score");
                                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
                                    icon.setGlyphStyle("-fx-fill:skyblue");
                                    lbl_infortwo.setGraphic(icon);

                                } else {

                                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);

                                    lbl_infortwo.setGraphic(icon);
                                    lbl_infortwo.setText("Invalid Mark");
                                    btn_addscore.setDisable(true);

                                }
                            } else {

                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                icon.setGlyphStyle("-fx-fill:red");
                                icon.setGlyphSize(20);
                                lbl_infortwo.setGraphic(icon);
                                lbl_infortwo.setText("Null Value !!");
                                btn_addscore.setDisable(true);

                            }

                        } else {

                            txt_score.setText("");
                            cmb_unitname.requestFocus();
                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                            icon.setGlyphStyle("-fx-fill:red");
                            icon.setGlyphSize(15);
                            four.setGraphic(icon);

                        }

                    } else {

                        txt_score.setText("");
                        cmb_term.requestFocus();
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        icon.setGlyphStyle("-fx-fill:red");
                        icon.setGlyphSize(15);
                        three.setGraphic(icon);

                    }

                } else {

                    txt_score.setText("");
                    cmb_examname.requestFocus();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    icon.setGlyphStyle("-fx-fill:red");
                    icon.setGlyphSize(15);
                    two.setGraphic(icon);

                }

            } else {

                txt_score.setText("");
                txt_eyear.requestFocus();
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                icon.setGlyphStyle("-fx-fill:red");
                icon.setGlyphSize(15);
                one.setGraphic(icon);

            }

        } else {

            txt_score.setText("");
            txt_regno.requestFocus();
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphStyle("-fx-fill:red");
            icon.setGlyphSize(15);
            lbl_infor.setGraphic(icon);
            lbl_infor.setText("Adm No ??");

        }

    }

    public Boolean checkExamRecord() {

        boolean result = false;

        String regno = txt_regno.getText().trim();
        String examname = cmb_examname.getSelectionModel().getSelectedItem().toString();
        String examterm = cmb_term.getSelectionModel().getSelectedItem().toString();
        String examunit = cmb_unitname.getSelectionModel().getSelectedItem().toString();
        String exam_year = txt_eyear.getText();
        String course = AppFuctions.StudentsClass.getStdCourse(regno);

        String query = "SELECT * FROM exam_2017 WHERE Adm_Number ='" + regno + "'"
                + "AND Exam_Name = '" + examname + "' AND Exam_Term = '" + examterm + "' AND"
                + " Unit_Name = '" + examunit + "' AND Year = '" + exam_year + "' AND Std_Course = '" + course + "'";

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

        txt_regno.setText("");
        txt_score.setText("");
        std_course.setText("");

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
