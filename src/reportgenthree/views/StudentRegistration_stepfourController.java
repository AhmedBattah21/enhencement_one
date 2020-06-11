/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentRegistration_stepfourController implements Initializable {

    @FXML
    private Rectangle rect_image;

    @FXML
    private StackPane mypane;

    @FXML
    private Label lbl_names;

    @FXML
    private Label lbl_course;

    @FXML
    private Label lbl_stdyear;

    @FXML
    private Label lbl_category;

    @FXML
    private Label lbl_gender;

    @FXML
    private Label lbl_adm;

    @FXML
    private Label lbl_date;

    @FXML
    private Button btn_save;

    @FXML
    private Label lbl_names1;

    private String std_fname;
    private String std_lname;
    private String std_gender;
    private String std_year;
    private String std_regcode;
    private String std_complete_adm;

    private String std_course;
    private String std_adm;
    private String std_category;

    private String image_name;
    private String image_location;
    private String date;

    private String copy_signal = "copy";

    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);

    private String new_filename = "";
    String home = System.getProperty("user.home") + "/" + "Documents";
    private File selected_image = null;

    private BorderPane parent = new BorderPane();
    FontAwesomeIconView tick = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
    FontAwesomeIconView times = new FontAwesomeIconView(FontAwesomeIcon.TIMES);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setGlyphStyle("-fx-fill:red");
        error.setGlyphSize(15);

        infor.setGlyphStyle("-fx-fill:seagreen");
        infor.setGlyphSize(15);

        tick.setGlyphStyle("-fx-fill:white");
        tick.setGlyphSize(30);
        
        times.setGlyphStyle("-fx-fill:red");
        times.setGlyphSize(30);

        btn_save.setOnAction(e -> save_data());

    }

    public void save_data() {

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;

        if (!StudentsClass.checkStudent(std_adm)) {

            System.out.println("File Name " + new_filename);

            if (copy_signal.equals("copy")) {

                copyImage(std_adm);
            }

            String std_name = std_fname + " " + std_lname;
            boolean result = StudentsClass.registerStudent(std_name, std_regcode, std_adm, std_year, std_course, std_category, new_filename, date, std_gender);

            StudentsClass.AddRegNumber(std_adm, std_name);

            if (result) {

                ReportGenThree.get_std_registrationcontroller().getBtn_stepfour().setGraphic(tick);

                openDialog(" Registration Successfull  Welcome " + std_name + " \nTo Muyeye Vocational Trainning Centre");

                String desc = "Registration Of Student \nRegCode " + std_adm + ""
                        + "->Course " + std_regcode + " ->Year ->" + std_year + "\n"
                        + "Name " + std_name;

                sqlDataBaseConnection.add_activity("Activity:" + n, "Registration", desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

            } else {

                openDialog(" Registration Of " + std_name + " Has Failed ");
                ReportGenThree.get_std_registrationcontroller().getBtn_stepfour().setGraphic(times);

            }

        } else {

            openDialog(" Registration Of " + std_adm + " Has Failed \n  Registration Number In Use");
            ReportGenThree.get_std_registrationcontroller().getBtn_stepfour().setGraphic(times);

        }

    }

    public void copyImage(String regno) {

        String extension = "";

        int i = image_name.lastIndexOf(".");
        if (i >= 0) {

            extension = image_name.substring(i + 1);

        }

        new_filename = regno.concat("." + extension);

        File dest = new File(home + "/ReportGenThree/students/" + new_filename);
        File srcfile = selected_image;

        try {

            Files.copy(srcfile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {

            System.out.println("" + ex);

        }

    }

    public String getStd_fname() {
        return std_fname;
    }

    public void setStd_fname(String std_fname) {
        this.std_fname = std_fname;
        lbl_names.setText("First Name:" + std_fname);
    }

    public String getStd_lname() {
        return std_lname;
    }

    public void setStd_lname(String std_lname) {
        this.std_lname = std_lname;
        lbl_names1.setText("Last Name(s) " + std_lname);
    }

    public String getStd_gender() {
        return std_gender;
    }

    public void setStd_gender(String std_gender) {
        this.std_gender = std_gender;
        lbl_gender.setText("Gender " + std_gender);
    }

    public String getStd_year() {
        return std_year;
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
        lbl_stdyear.setText(std_year);
    }

    public String getStd_course() {
        return std_course;
    }

    public void setStd_course(String std_course) {
        this.std_course = std_course;
        lbl_course.setText("Course: " + std_course);
    }

    public String getStd_adm() {
        return std_adm;
    }

    public void setStd_adm(String std_adm) {
        this.std_adm = std_adm;

    }

    public String getStd_category() {
        return std_category;
    }

    public void setStd_category(String std_category) {
        this.std_category = std_category;
        lbl_category.setText("Category " + std_category);
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;

    }

    public String getImage_location() {
        return image_location;

    }

    public void setImage_location(String image_location) {
        this.image_location = image_location;

        Image im = new Image(image_location, false);
        rect_image.setFill(new ImagePattern(im));

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        lbl_date.setText("Date " + date);
    }

    public String getNew_filename() {
        return new_filename;
    }

    public void setNew_filename(String new_filename) {
        this.new_filename = new_filename;
    }

    public File getSelected_image() {
        return selected_image;
    }

    public void setSelected_image(File selected_image) {
        this.selected_image = selected_image;
        if (selected_image != null) {

            Image im = new Image(selected_image.toURI().toString(), false);
            rect_image.setFill(new ImagePattern(im));

        }
    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public String getStd_regcode() {
        return std_regcode;
    }

    public void setStd_regcode(String std_regcode) {
        this.std_regcode = std_regcode;
    }

    public String getStd_complete_adm() {
        return std_complete_adm;
    }

    public void setStd_complete_adm(String std_complete_adm) {
        this.std_complete_adm = std_complete_adm;
        lbl_adm.setText("Adm No. " + std_complete_adm);
    }

    public String getCopy_signal() {
        return copy_signal;
    }

    public void setCopy_signal(String copy_signal) {
        this.copy_signal = copy_signal;
    }

    public void openDialog(String message) {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Student_RegistrationFormController.class.getResource("Dialog_layout.fxml"));
            FlowPane layout = loader.load();
            Dialog_layoutController cc = loader.getController();
            cc.setMessage(message);
            cc.setMessage_source("Students Registration");
            cc.setMessage_title("Student Registration");

            JFXDialogLayout content = new JFXDialogLayout();

            content.setAlignment(Pos.CENTER);
            content.setBody(layout);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

            JFXButton buttonCancel = new JFXButton("New Student");
            buttonCancel.setStyle("-fx-border-color:seagreen;-fx-border-width:1");

            buttonCancel.setOnAction(e -> {
                dlog.close();
                new_student();

            });

            content.setActions(buttonCancel);
            content.autosize();
            content.setPadding(new Insets(1, 0, 1, 0));
            content.setLayoutX(50);
            content.setLayoutY(50);

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Student_RegistrationFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void new_student() {

        try {

            ReportGenThree.get_std_registrationcontroller().setSpane_stepone(null);
            ReportGenThree.get_std_registrationcontroller().setSpane_steptwo(null);
            ReportGenThree.get_std_registrationcontroller().setSpane_stepthree(null);
            ReportGenThree.get_std_registrationcontroller().setSpane_stepfour(null);

            ReportGenThree.get_std_registrationcontroller().setCc_stepone(null);
            ReportGenThree.get_std_registrationcontroller().setCc_steptwo(null);
            ReportGenThree.get_std_registrationcontroller().setCc_stepthree(null);
            ReportGenThree.get_std_registrationcontroller().setCc_stepfour(null);
            
            ReportGenThree.get_std_registrationcontroller().step_one();

        } catch (Exception ex) {
            Logger.getLogger(StudentRegistration_stepfourController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
