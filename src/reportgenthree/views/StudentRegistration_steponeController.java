/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;
import static reportgenthree.ReportGenThree.get_std_registrationcontroller;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentRegistration_steponeController implements Initializable {

    @FXML
    private TextField txt_fname;

    @FXML
    private Label infor1;

    @FXML
    private TextField txt_lname;

    @FXML
    private Label infor2;

    @FXML
    private ComboBox cmb_gender;

    @FXML
    private Label infor3;

    @FXML
    private ComboBox cmb_stdyear;

    @FXML
    private Label infor4;

    @FXML
    private Button btn_nextone;

    private BorderPane parent = new BorderPane();

    FontAwesomeIconView error = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
    FontAwesomeIconView infor = new FontAwesomeIconView(FontAwesomeIcon.CHECK_SQUARE_ALT);
    FontAwesomeIconView tick = new FontAwesomeIconView(FontAwesomeIcon.CHECK);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            ReportGenThree.get_std_registrationcontroller().getBtn_stepone().setGraphic(null);
            ReportGenThree.get_std_registrationcontroller().getBtn_steptwo().setGraphic(null);
            ReportGenThree.get_std_registrationcontroller().getBtn_stepthree().setGraphic(null);
            ReportGenThree.get_std_registrationcontroller().getBtn_stepfour().setGraphic(null);
            error.setGlyphStyle("-fx-fill:red");
            error.setGlyphSize(15);

            tick.setGlyphStyle("-fx-fill:white");
            tick.setGlyphSize(30);

            infor.setGlyphStyle("-fx-fill:seagreen");
            infor.setGlyphSize(15);

            btn_nextone.setOnAction(e -> next_step());

            ObservableList groups = FXCollections.observableArrayList();
            groups.addAll("Female", "Male");
            cmb_gender.setItems(groups);

            ObservableList<String> study_year = FXCollections.observableArrayList();

            study_year.addAll("First Year", "Second Year");
            cmb_stdyear.setItems(study_year);
            cmb_stdyear.setValue("First Year");

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentRegistration_steponeController.class.getResource("StudentRegistrationPanel.fxml"));
            StackPane spane = loader.load();
            StudentRegistrationPanelController cc = loader.getController();
            parent = cc.getParent();

        } catch (IOException ex) {
            System.out.println(" Error " + ex);
        }

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

    public void next_step() {

        if (!txt_fname.getText().equals("")) {

            infor1.setGraphic(infor);
            if (!txt_lname.getText().equals("")) {

                infor2.setGraphic(infor);
                if (!cmb_gender.getSelectionModel().isEmpty()) {

                    infor3.setGraphic(infor);
                    if (!cmb_stdyear.getSelectionModel().isEmpty()) {
                        infor4.setGraphic(infor);

                        try {

                            String fname = txt_fname.getText().toUpperCase();
                            String lname = txt_lname.getText().toUpperCase();
                            String gender = cmb_gender.getSelectionModel().getSelectedItem().toString();
                            String std_year = cmb_stdyear.getSelectionModel().getSelectedItem().toString();

                            StudentRegistration_stepfourController cc = null;
                            StackPane spane = null;

                            if (get_std_registrationcontroller().getSpane_stepfour() == null) {

                                FXMLLoader loader_stepfour = new FXMLLoader();
                                loader_stepfour.setLocation(StudentRegistration_steponeController.class.getResource("StudentRegistration_stepfour.fxml"));

                                spane = loader_stepfour.load();
                                cc = loader_stepfour.getController();
                                cc.setStd_fname(fname);
                                cc.setStd_lname(lname);
                                cc.setStd_gender(gender);
                                cc.setStd_year(std_year);

                                get_std_registrationcontroller().setSpane_stepfour(spane);
                                get_std_registrationcontroller().setCc_stepfour(cc);
                                
                            } else {

                                spane = get_std_registrationcontroller().getSpane_stepfour();
                                cc = get_std_registrationcontroller().getCc_stepfour();
                                cc.setStd_fname(fname);
                                cc.setStd_lname(lname);
                                cc.setStd_gender(gender);
                                cc.setStd_year(std_year);

                            }

                            if (get_std_registrationcontroller().getSpane_steptwo() == null) {

                                FXMLLoader loader_steptwo = new FXMLLoader();
                                loader_steptwo.setLocation(StudentRegistration_steponeController.class.getResource("StudentRegistration_steptwo.fxml"));

                                StackPane step_two = loader_steptwo.load();
                                StudentRegistration_steptwoController cc_two = loader_steptwo.getController();
                                cc_two.setCc_stepfour(cc);
                                cc_two.setParent(parent);
                                cc_two.setSpane(spane);
                                cc_two.setGender(gender);
                                parent.setCenter(step_two);

                                get_std_registrationcontroller().setSpane_steptwo(step_two);
                                get_std_registrationcontroller().setCc_steptwo(cc_two);

                            } else {

                                StackPane step_two = get_std_registrationcontroller().getSpane_steptwo();
                                StudentRegistration_steptwoController cc_two = get_std_registrationcontroller().getCc_steptwo();
                                cc_two.setCc_stepfour(cc);
                                cc_two.setParent(parent);
                                cc_two.setSpane(spane);
                                cc_two.setGender(gender);
                                parent.setCenter(step_two);
                            }

                            get_std_registrationcontroller().getBtn_stepone().setGraphic(tick);

                        } catch (IOException ex) {

                            Logger.getLogger(StudentRegistration_steponeController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    } else {

                        infor4.setGraphic(error);
                    }
                } else {

                    infor3.setGraphic(error);
                }
            } else {

                infor2.setGraphic(error);
            }
        } else {

            infor1.setGraphic(error);
        }

    }

    @FXML
    public void keylistener_fname(KeyEvent evt) {

        if (!txt_fname.getText().trim().isEmpty()) {

            if (evt.getCode() == KeyCode.SPACE || evt.getCode() == KeyCode.ENTER) {

                txt_lname.requestFocus();

            }
        } else {

            evt.consume();

        }

    }

    @FXML
    public void keylistener_lname(KeyEvent evt) {

        if (!txt_lname.getText().trim().isEmpty()) {

            if (evt.getCode() == KeyCode.ENTER) {

                cmb_gender.requestFocus();

            }
        } else {

            evt.consume();

        }

    }

    @FXML
    public void keylistener_gender(KeyEvent evt) {

        if (!cmb_gender.getSelectionModel().isEmpty()) {

            if (evt.getCode() == KeyCode.ENTER) {

                cmb_stdyear.requestFocus();

            }
        } else {

            evt.consume();

        }

    }

    @FXML
    public void keylistener_stdyear(KeyEvent evt) {

        if (!cmb_stdyear.getSelectionModel().isEmpty()) {

            if (evt.getCode() == KeyCode.SPACE || evt.getCode() == KeyCode.ENTER) {

                btn_nextone.requestFocus();

            }
        } else {

            evt.consume();

        }

    }

    @FXML
    public void keylistener_button(KeyEvent evt) {

        if (evt.getCode() == KeyCode.ENTER || evt.getCode() == KeyCode.N) {

            next_step();

        }

    }

    public BorderPane getParent() {
        return parent;
    }

    public void setParent(BorderPane parent) {
        this.parent = parent;
    }

}
