/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXDatePicker;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentRegistration_stepthreeController implements Initializable {

    @FXML
    private Rectangle rect_image;

    @FXML
    private Button btn_default;

    @FXML
    private Button btn_new;

    @FXML
    private Button btn_browse;

    @FXML
    private Label lbl_imagename;

    @FXML
    private JFXDatePicker dp_date;

    @FXML
    private Button btn_nextthree;

    private BorderPane parent = new BorderPane();

    StudentRegistration_stepfourController cc_stepfour = null;

    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
    FontAwesomeIconView tick = new FontAwesomeIconView(FontAwesomeIcon.CHECK);

    StackPane spane = null;

    private String image_path = "";
    private String image_name = "";
    private File selected_image = null;
    private String new_filename = "";
    private String adm_number = "";

    private String copy_signal = "";

    String gender = "Male";
    String home = System.getProperty("user.home") + "/" + "Documents";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        error.setGlyphStyle("-fx-fill:red");
        error.setGlyphSize(15);

        infor.setGlyphStyle("-fx-fill:seagreen");
        infor.setGlyphSize(15);

        tick.setGlyphStyle("-fx-fill:white");
        tick.setGlyphSize(30);

        Image im = new Image("reportgenthree/views/System_images/default.png", false);
        rect_image.setFill(new ImagePattern(im));

        btn_browse.setOnAction(e -> openFileChooser());
        btn_default.setOnAction(e -> useDefaultImage());
        btn_new.setOnAction(e -> take_pic());

        LocalDate date = LocalDate.now();

        
        dp_date.setValue(date);

        btn_nextthree.setOnAction(e -> step_four());

    }

    public void step_four() {
        
        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

        if (!new_filename.equals("") || selected_image != null) {

            if (!dp_date.getValue().toString().isEmpty()) {

                cc_stepfour.setDate(fomat.format(dp_date.getValue()));
                cc_stepfour.setNew_filename(new_filename);
                cc_stepfour.setParent(parent);
                cc_stepfour.setCopy_signal(copy_signal);

                if (image_name.equals("defaultf.png") || image_name.equals("defaultm.png")) {

                    cc_stepfour.setImage_location(image_path);
                    ReportGenThree.get_std_registrationcontroller().getBtn_stepthree().setGraphic(tick);

                } else {

                    cc_stepfour.setSelected_image(selected_image);
                    cc_stepfour.setImage_name(image_name);

                    ReportGenThree.get_std_registrationcontroller().getBtn_stepthree().setGraphic(tick);

                }

                parent.setCenter(spane);

            } else {

                lbl_imagename.setText("Select Date");
            }

        } else {

            lbl_imagename.setText("Student Image Can'nt Be Empty");

        }

    }

    @FXML
    public void openFileChooser() {

        Stage stage = (Stage) btn_browse.getScene().getWindow();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("M.E.R.M.S");

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images Only", "*.jpg", "*.png", "*.JPEG", "*.TIFF");
        chooser.getExtensionFilters().add(filter);

        File file = chooser.showOpenDialog(stage);

        if (file != null) {

            image_path = file.getAbsolutePath();

            image_name = file.getName();

            selected_image = file.getAbsoluteFile();
            lbl_imagename.setText(image_name);
            copy_signal = "copy";
            Image im = new Image(selected_image.toURI().toString());

            rect_image.setFill(new ImagePattern(im));

        }

    }

    public void take_pic() {

        FXMLLoader loader = new FXMLLoader();

        loader.setLocation(Students_datadisplayerController.class.getResource("Students_ImageViewer.fxml"));
        try {

            StackPane pane = loader.load();
            Students_ImageViewerController cc = loader.getController();

            cc.setStd_admno(adm_number);

            File file = new File("reportgenthree/views/System_images/defaultm.png");

            cc.setImage(file);
            cc.setDetails("Image For  " + adm_number);

            cc.open_webcam();

            Scene scene = new Scene(pane, 340, 370);
            Stage stage = new Stage();
            stage.setOnCloseRequest((WindowEvent event) -> {
                Platform.runLater(() -> {

                    new_filename = adm_number + ".jpg";
                    image_name = new_filename;
                    File image = new File(home + "/ReportGenThree/students/" + new_filename);
                    selected_image = image;
                    image_path = home + "/ReportGenThree/students/" + new_filename;
                    lbl_imagename.setText(new_filename);
                    Image im = new Image(image.getAbsoluteFile().toURI().toString(), false);
                    rect_image.setFill(new ImagePattern(im));
                    copy_signal = "dont copy";

                });
            });

            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.showAndWait();

        } catch (IOException exc) {

        }

    }

    @FXML
    public void useDefaultImage() {

        if (gender.equals("Female")) {

            new_filename = "defaultf.png";
            image_name = new_filename;
            lbl_imagename.setText("Default Image");
            Image im = new Image("reportgenthree/views/System_images/defaultf.png", false);

            image_path = "reportgenthree/views/System_images/defaultf.png";

            rect_image.setFill(new ImagePattern(im));

        } else {

            new_filename = "defaultm.png";
            image_name = new_filename;
            lbl_imagename.setText("Default Image");
            image_path = "reportgenthree/views/System_images/defaultf.png";
            Image im = new Image("reportgenthree/views/System_images/defaultm.png", false);

            rect_image.setFill(new ImagePattern(im));

        }

        copy_signal = "dont copy";

    }

    public StudentRegistration_stepfourController getCc_stepfour() {
        return cc_stepfour;
    }

    public void setCc_stepfour(StudentRegistration_stepfourController cc_stepfour) {
        this.cc_stepfour = cc_stepfour;
    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAdm_number() {
        return adm_number;
    }

    public void setAdm_number(String adm_number) {
        this.adm_number = adm_number;
    }

    public StackPane getSpane() {
        return spane;
    }

    public void setSpane(StackPane spane) {
        this.spane = spane;
    }

}
