/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Students_datadisplayerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane Parent;

    @FXML
    private Label lbl_std_name;

    @FXML
    private Label lbl_course;

    @FXML
    private Label lbl_year;

    @FXML
    private Label lbl_adm;

    @FXML
    private Label lbl_exam;

    @FXML
    private Label lbl_dor;

    @FXML
    private Label lbl_dof;

    @FXML
    private Label lbl_tname;

    @FXML
    private Label lbl_debt;

    @FXML
    private Label lbl_category;

    @FXML
    private Button btn_close;

    @FXML
    private Circle circle_image;

    private String std_name;
    private String adm_number;
    private String std_course;
    private String std_code;
    private String std_year;
    private String std_dor;
    private File std_image;
    private String std_category;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setData() {

    }

    public void setStd_name(String std_name) {
        this.std_name = std_name;
        lbl_std_name.setText(std_name);
    }

    public void setAdm_number(String adm_number) {
        this.adm_number = adm_number;
        lbl_adm.setText(adm_number);
    }

    public void setStd_course(String std_course) {
        this.std_course = std_course;
        lbl_course.setText(std_course);
    }

    public void setStd_year(String std_year) {
        this.std_year = std_year;
        lbl_year.setText(std_year);
    }

    public void setStd_dor(String std_dor) {
        this.std_dor = std_dor;
        lbl_dor.setText(std_dor);
    }

    public void setStd_image(File std_image) {
        this.std_image = std_image;
        Image im = new Image(std_image.toURI().toString(), false);
        circle_image.setFill(new ImagePattern(im));

    }

    public void setStd_category(String std_category) {
        this.std_category = std_category;
        lbl_category.setText(std_category);
    }

    public void setStd_code(String std_code) {
        this.std_code = std_code;
    }

    public void close_window() {
        try {

            ((VBox) Parent.getParent()).getChildren().remove(Parent);

        } catch (Exception exc) {

        }

    }

    public void view_image() {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Students_datadisplayerController.class.getResource("Students_ImageViewer.fxml"));
        try {

            StackPane pane = loader.load();
            Students_ImageViewerController cc = loader.getController();

            cc.setImage(std_image);
            cc.setDetails("Name " + std_name + " Adm No " + adm_number);
            cc.setStd_admno(std_code);

            Stage stage = new Stage();
            Scene scene = new Scene(pane, 345, 370);

            stage.setScene(scene);

            stage.showAndWait();

        } catch (IOException exc) {

            System.out.println("Error " + exc);
        }

    }

    public void student_profile() {

        ReportGenThree.Student_profile();

    }

    public void more_infor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Students_datadisplayerController.class.getResource("StudentProfile_dataholder.fxml"));
        TabPane tabpane = ReportGenThree.getMain_tabpane();

        try {

            StackPane pane = loader.load();
            StudentProfile_dataholderController cc = loader.getController();
            cc.setAdmno(std_code);

            cc.funnyMethod();
            Tab tab = new Tab("Student " + std_code);

            tab.setContent(pane);

            tabpane.getTabs().add(tab);
            tabpane.getSelectionModel().select(tab);

        } catch (IOException exc) {

            System.out.println("" + exc);
        }

    }

}
