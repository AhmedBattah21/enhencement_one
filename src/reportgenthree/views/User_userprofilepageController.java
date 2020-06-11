/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class User_userprofilepageController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Circle imgcircle;

    @FXML
    private Label lbl_username;

    @FXML
    private Label lbldate;

    @FXML
    private Label lbltime;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem logout;

    @FXML
    private JFXButton btn_files;

    @FXML
    private StackPane mypane;

    @FXML
    private Label lbl_group;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        String imagename = LoginscreenController.getImage();

        String home = System.getProperty("user.home") + "/" + "Documents" + "/" + "ReportGenThree" + "/" + "users" + "/" + imagename;

        if (!imagename.equals("")) {

            File imagepath = new File(home);
            if (imagepath.exists()) {

                Image im = new Image(imagepath.toURI().toString(), false);
                imgcircle.setFill(new ImagePattern(im));

            } else {

                Image im = new Image("reportgenthree/AppCss/images/1.jpg", false);

                imgcircle.setFill(new ImagePattern(im));

            }

        } else {

            Image im = new Image("reportgenthree/AppCss/images/1.jpg", false);

            imgcircle.setFill(new ImagePattern(im));

        }

        String uname = LoginscreenController.getUserName();
        String gname = LoginscreenController.getGName();
        lbl_username.setText(uname);
        lbl_group.setText("Group: " + gname);

        setDate();

    }

    public void setDate() {

        try {
            DateTimeFormatter fromata = DateTimeFormatter.ofPattern("'Date: 'dd-MMM-yyy");
            LocalDate date = LocalDate.now();
            lbldate.setText(fromata.format(date));

        } catch (Exception exc) {

            System.out.println("" + exc);
        }

    }

    public void openFileSystem_1st() {

        try {

            ReportGenThree.Examination_reportsfiles_1st();

        } catch (Exception exc) {

            System.out.println("" + exc);
        }
    }
    
    public void openFileSystem_2nd() {

        try {

            ReportGenThree.Examination_reportsfiles_2nd();

        } catch (Exception exc) {

            System.out.println("" + exc);
        }
    }

    public void examinationfiles_secondyear() {

        ReportGenThree.Examination_Files_2ndyear();

    }

    public void examinationfiles_fstyear() {

        ReportGenThree.Examination_Files_1styear();

    }

    public void openStudentsFinder() {

        ReportGenThree.NavBar_StudentsFindData();

    }

    public void reportMakerM() {

        reportgenthree.ReportGenThree.NavBar_AcademicsMultipleReport();

    }

    public void reportMakerS() {

        reportgenthree.ReportGenThree.NavBar_AcademicsSingleReport();

    }

    public void FindResults() {

        reportgenthree.ReportGenThree.Student_profile();

    }

}
