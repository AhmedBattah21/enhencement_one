/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.CoursesClass;
import AppFuctions.Functions;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import Connection.sqlDataBaseConnection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Computer
 */
public class Student_DeleteDataFormController implements Initializable {

    @FXML
    private JFXTextField txt_regno;

    @FXML
    private JFXTextField txt_fname;

    @FXML
    private JFXTextField txt_lname;

    //@FXML
    public JFXComboBox cmb_category;

    //@FXML
    public JFXComboBox cmb_course;

    @FXML
    private JFXDatePicker dp_date;

    @FXML
    private Rectangle rect_image;

    @FXML
    private JFXButton btn_chooseImage;

    @FXML
    private JFXButton btn_useCamera;

    @FXML
    private Label lbl_imgname;

    @FXML
    private JFXButton btn_register;

    @FXML
    private JFXButton btn_Cancel;

    public AnchorPane DelPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

       
         CoursesClass.populateComboBoxClasses(cmb_course, "CourseName", "CourseTable");
        CoursesClass.populateComboBoxClasses(cmb_category, "category_name", "categories");

    }

    public void getStudent() {

        String regno = txt_regno.getText();

        String home = System.getProperty("user.home") + "/" + "Documents";

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            
            DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

            if (rst.next()) {

                String[] name = rst.getString("StudentName").trim().split("\\s+");
                txt_fname.setText(name[0]);
                txt_lname.setText(name[1]);
                cmb_category.setValue(rst.getString("StudentCategory"));
                cmb_course.setValue(rst.getString("StudentCourse"));

                String img_name = rst.getString("std_image");
                LocalDate date =LocalDate.parse(rst.getString("DOR"),fomat);

                lbl_imgname.setText(img_name);
                
                dp_date.setValue(date);

                File imagepath = new File(home + "/ReportGenThree/students/" + img_name);
                Image im = new Image(imagepath.toURI().toString(), false);
                rect_image.setFill(new ImagePattern(im));

            }

            conn.close();

        } catch (SQLException exe) {

            System.out.println("" + exe);

        }

    }

    public void DeleteData() {

        String fname = txt_fname.getText().trim();
        String lname = txt_lname.getText().trim();

        String regno = txt_regno.getText();

        if (!fname.isEmpty() && !lname.isEmpty()) {

            String category = cmb_category.getSelectionModel().getSelectedItem().toString().trim();

            if (!category.isEmpty()) {

                String course = cmb_course.getSelectionModel().getSelectedItem().toString().trim();

                if (!course.isEmpty()) {

                    if (!lbl_imgname.getText().trim().isEmpty()) {

                        String query = "DELETE FROM Students_2017  WHERE StudentRegCode = '" + regno + "'";
                        
                        String querytwo = "DELETE FROM registrationnumbers WHERE RegNumber = '" + regno + "'";

                        Connection conn = sqlDataBaseConnection.sqliteconnect();

                        try {

                            Statement st = conn.createStatement();
                            Statement sttwo = conn.createStatement();
                            st.executeUpdate(query);
                            sttwo.executeUpdate(querytwo);

                            if (lbl_imgname.getText().equals("defaultm.png") || lbl_imgname.getText().equals("defaultf.png")) {

                            } else {

                                DeleteImage();
                            }

                            notification("P.E.R.M.S", "Thank You");
                            conn.close();
                            
                            reportgenthree.ReportGenThree.NavBar_StudentsDeleteData();

                        } catch (SQLException exc) {

                            System.out.println("" + exc);

                        }

                    } else {

                        notification("Students Data Editing", "Profile Photo Is Empty!");
                    }

                } else {

                    notification("Students Data Editing", "Course Is Empty!");
                }
            } else {

                notification("Students Data Editing", "Category Is Empty!");
            }

        } else {

            notification("Students Data Editing", "Please Enter Student Full Name");

        }

    }

    public void notification(String title, String data) {

        Notifications.create()
                .title(title)
                .text(data)
                .position(Pos.TOP_LEFT)
                .hideAfter(Duration.seconds(5))
                .darkStyle()
                .show();

    }

    public void DeleteImage() {

        String home = System.getProperty("user.home") + "/" + "Documents";

        File dest = new File(home + "/ReportGenThree/students/" + lbl_imgname.getText());

        try {

            Files.deleteIfExists(dest.toPath());

        } catch (IOException ex) {

            System.out.println("" + ex);

        }

    }

    public void delete_confirm() {

        double x = DelPane.getLayoutX();
        double y = DelPane.getLayoutY();
        String std_name = txt_fname.getText() + " " + txt_lname.getText();

        if (!std_name.trim().isEmpty()) {

            String regno = txt_regno.getText();

            String cname = cmb_course.getSelectionModel().getSelectedItem().toString();

            Optional<ButtonType> result = Functions.showAlertTwo(Alert.AlertType.CONFIRMATION, "P.E.R.MS", "Delete Confirmation",
                    "Student Name: " + std_name + " \n Admission Number: " + regno + " "
                    + "\n Class Name :" + cname + " \n Will Be Removed Completely From Our Sytem", x, y);

            if (!result.isPresent()) {

                Notifications.create()
                        .title("P.E.R.M.S")
                        .text("Deleting Data Has Been Cancelled..")
                        .position(Pos.TOP_LEFT)
                        .hideAfter(Duration.seconds(5))
                        .darkStyle()
                        .show();

            } else if (result.get() == ButtonType.OK) {

                DeleteData();

            } else {

                Notifications.create()
                        .title("P.E.R.M.S")
                        .text("Deleting Data Has Been Cancelled..")
                        .position(Pos.TOP_LEFT)
                        .hideAfter(Duration.seconds(5))
                        .darkStyle()
                        .show();

            }

        } else {

            Notifications.create()
                    .title("P.E.R.M.S")
                    .text("Enter Registration Number...")
                    .position(Pos.TOP_LEFT)
                    .hideAfter(Duration.seconds(5))
                    .darkStyle()
                    .show();

        }

    }

}
