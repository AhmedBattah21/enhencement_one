/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import reportgenthree.views.Dialogs.Dialogs_functions;

/**
 *
 * @author Computer
 */
public class Student_EditDataFormController implements Initializable {

    @FXML
    private StackPane mypane;

    @FXML
    private TextField txt_fname;

    @FXML
    private TextField txt_lname;

    @FXML
    private ComboBox cmb_category;

    @FXML
    private ComboBox cmb_gender;

    @FXML
    private ComboBox cmb_yearofstudy;

    @FXML
    private ComboBox cmb_course;

    @FXML
    private JFXDatePicker dp_date;

    @FXML
    private JFXButton btn_register;

    @FXML
    private JFXButton btn_Cancel;

    String regno = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        CoursesClass.populateComboBoxClasses(cmb_course, "CourseName", "CourseTable");
        CoursesClass.populateComboBoxClasses(cmb_category, "category_name", "categories");

        ObservableList groups = FXCollections.observableArrayList();
        groups.addAll("Female", "Male");
        cmb_gender.setItems(groups);

        ObservableList<String> study_year = FXCollections.observableArrayList();

        study_year.addAll("First Year", "Second Year");
        cmb_yearofstudy.setItems(study_year);

        txt_fname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txt_fname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

            }
        });

        txt_lname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txt_lname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

            }
        });

    }

    public void getStudent() {

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

            if (rst.next()) {

                String[] name = rst.getString("StudentName").trim().split("\\s+");

                if (name.length == 2) {
                    txt_fname.setText(name[0]);
                    txt_lname.setText(name[1]);

                } else if (name.length == 3) {

                    txt_fname.setText(name[0]);
                    txt_lname.setText(name[1] + " " + name[2]);
                }
                cmb_category.setValue(rst.getString("StudentCategory"));
                cmb_course.setValue(rst.getString("StudentCourse"));
                cmb_gender.setValue(rst.getString("Gender"));
                cmb_yearofstudy.setValue(rst.getString("YearOfStudy"));
                LocalDate date = LocalDate.parse(rst.getString("DOR"), fomat);
                dp_date.setValue(date);

            }

            conn.close();

        } catch (SQLException exe) {

            System.out.println("" + exe);

        }

    }

//    @FXML
    public void saveData() {

        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;

        String fname = txt_fname.getText().trim();
        String lname = txt_lname.getText().trim();

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

        if (!cmb_yearofstudy.getSelectionModel().isEmpty()) {

            String std_year = cmb_yearofstudy.getSelectionModel().getSelectedItem().toString();

            if (!cmb_gender.getSelectionModel().isEmpty()) {

                String gender = cmb_gender.getSelectionModel().getSelectedItem().toString();

                if (!dp_date.getValue().toString().isEmpty()) {

                    String dor = dp_date.getValue().format(fomat);

                    if (!fname.isEmpty() && !lname.isEmpty()) {

                        String category = cmb_category.getSelectionModel().getSelectedItem().toString().trim();

                        if (!category.isEmpty()) {

                            String course = cmb_course.getSelectionModel().getSelectedItem().toString().trim();
                            String course_code = CoursesClass.getCourseAbr(course);

                            if (!course.isEmpty()) {

                                String stname = fname + " " + lname;

                                String query = "UPDATE Students_2017 SET StudentName = '" + stname + "',"
                                        + "StudentCourse = '" + course + "',StudentCategory = '" + category + "',"
                                        + " YearOfStudy = '" + std_year + "',DOR = '" + dor + "',Ccode = '" + course_code + "'"
                                        + "WHERE StudentRegCode = '" + regno + "'";

                                Connection conn = sqlDataBaseConnection.sqliteconnect();

                                try {

                                    Statement st = conn.createStatement();
                                    st.executeUpdate(query);
                                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
                                    icon.setGlyphSize(150);
                                    icon.setGlyphStyle("-fx-fill:skyblue");

                                    openDialog("Great !!! Data Has Been Saved", icon);
                                    String desc = "Details For " + regno + "Change \n Name " + stname + " ->Course " + course + " ->Category " + category + "\n"
                                            + "->Year " + std_year + " \n"
                                            + "Search Student To See The Results";

                                    sqlDataBaseConnection.add_activity("Activity:" + n, "Std Editor", desc, dor, LoginscreenController.getUserName());
                                    conn.close();

                                } catch (SQLException exc) {

                                    System.out.println("" + exc);

                                }

                            } else {

                                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_DOWN);
                                icon.setGlyphSize(150);
                                icon.setGlyphStyle("-fx-fill:RED");

                                openDialog("No! Select Course Of Your Student..", icon);
                            }
                        } else {

                            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_DOWN);
                            icon.setGlyphSize(150);
                            icon.setGlyphStyle("-fx-fill:RED");

                            openDialog("No! Please Insert Student Category", icon);
                        }

                    } else {
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_DOWN);
                        icon.setGlyphSize(150);
                        icon.setGlyphStyle("-fx-fill:RED");

                        openDialog("Please Enter Student Full Name", icon);

                    }

                } else {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_DOWN);
                    icon.setGlyphSize(150);
                    icon.setGlyphStyle("-fx-fill:RED");
                    openDialog("Enter Date Of Registration", icon);

                }

            } else {

                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_DOWN);
                icon.setGlyphSize(150);
                icon.setGlyphStyle("-fx-fill:RED");
                openDialog("Enter Gender", icon);

            }

        } else {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_DOWN);
            icon.setGlyphSize(150);
            icon.setGlyphStyle("-fx-fill:RED");

            openDialog("Select Year Of Study", icon);

        }

    }

    public void openDialog(String message, FontAwesomeIconView icon) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            Label mylabel = new Label("Student Data Editor ");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Student_EditDataFormController.class.getResource("Dialogs/Datasaved_dialog.fxml"));
            StackPane pane = loader.load();
            Datasaved_dialogController cc = loader.getController();
            cc.getLbl_infor().setText(message);
            cc.getLbl_icon().setGraphic(icon);

            content.setHeading(mylabel);

            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            content.autosize();

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Academics_AddNewScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

}
