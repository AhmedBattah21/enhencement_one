/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import AppFuctions.StudentsClass;
import AppFuctions.CoursesClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Student_RegistrationFormController implements Initializable {
    
    @FXML
    private JFXTextField txt_fname;
    
    @FXML
    private JFXTextField txt_lname;
    
    @FXML
    private JFXComboBox<?> cmb_course;
    
    @FXML
    private JFXComboBox<?> cmb_category;
    
    @FXML
    private JFXComboBox cmb_year;
    
    @FXML
    private JFXTextField txt_regno;
    
    @FXML
    private JFXDatePicker dp_date;
    
    @FXML
    private JFXButton btn_chooseImage;
    
    @FXML
    private JFXButton btn_useCamera;
    
    @FXML
    private Label lbl_imgname;
    
    @FXML
    private JFXButton btn_register;
    
    @FXML
    private JFXComboBox cmb_gender;
    
    @FXML
    private JFXButton btn_Cancel;
    
    @FXML
    private Rectangle rect_image;
    
    @FXML
    private JFXTextField txt_Ccode;
    
    @FXML
    private StackPane mypane;

    /**
     * Initializes the controller class.
     */
    private String image_path = "";
    private String image_name = "";
    private File selected_image = null;
    private String new_filename = "";
    String home = System.getProperty("user.home") + "/" + "Documents";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        CoursesClass.populateComboBoxClasses(cmb_course, "CourseName", "CourseTable");
        CoursesClass.populateComboBoxClasses(cmb_category, "category_name", "categories");
        
        Image im = new Image("reportgenthree/views/System_images/default.png", false);
        
        rect_image.setFill(new ImagePattern(im));
        //lbl_imgname.setText("Default Image");

        ObservableList groups = FXCollections.observableArrayList();
        groups.addAll("Female", "Male");
        cmb_gender.setItems(groups);
        lbl_imgname.setText("");
        
        ObservableList<String> study_year = FXCollections.observableArrayList();
        
        study_year.addAll("First Year", "Second Year");
        cmb_year.setItems(study_year);
        cmb_year.setValue("First Year");
        
    }
    
    @FXML
    public void keylistener(KeyEvent evt) {
        
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
                
                cmb_category.requestFocus();
                
            }
        } else {
            
            evt.consume();
            
        }
        
    }
    
    @FXML
    public void keylistener_category(KeyEvent evt) {
        
        if (!cmb_category.getSelectionModel().getSelectedItem().toString().trim().isEmpty()) {
            
            if (evt.getCode() == KeyCode.SPACE || evt.getCode() == KeyCode.ENTER) {
                
                cmb_course.requestFocus();
                
            }
        } else {
            
            evt.consume();
            
        }
        
    }
    
    @FXML
    public void keylistener_course(KeyEvent evt) {
        
        if (!cmb_course.getSelectionModel().getSelectedItem().toString().trim().isEmpty()) {
            
            if (evt.getCode() == KeyCode.SPACE || evt.getCode() == KeyCode.ENTER) {
                
                txt_regno.requestFocus();
                
            }
        } else {
            
            evt.consume();
            
        }
        
    }
    
    @FXML
    public void keylistener_regno(KeyEvent evt) {
        
        if (!txt_regno.getText().trim().isEmpty()) {
            
            if (evt.getCode() == KeyCode.SPACE || evt.getCode() == KeyCode.ENTER) {
                
                dp_date.requestFocus();
                
            }
        } else {
            
            evt.consume();
            
        }
        
    }
    
    @FXML
    public void registerStudent() {
        
        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();
        
        int n = rand.nextInt(50000) + 1;
        
        try {
            String s1 = cmb_category.getSelectionModel().getSelectedItem().toString();
            String s2 = cmb_course.getSelectionModel().getSelectedItem().toString();
            
            String year = cmb_year.getSelectionModel().getSelectedItem().toString();
            
            String fname = txt_fname.getText().toUpperCase().trim();
            String lname = txt_lname.getText().toUpperCase().trim();
            String n1 = fname + " " + lname;
            
            if (!lbl_imgname.getText().trim().isEmpty()) {
                
                if (!cmb_gender.getSelectionModel().isEmpty()) {
                    
                    String gender = cmb_gender.getSelectionModel().getSelectedItem().toString();
                    
                    if (!dp_date.getValue().toString().trim().isEmpty()) {
                        
                        String dor = dp_date.getValue().format(fomat);
                        
                        if (!s1.equals("Select Category") && !s2.equals("Select Year Of Study")) {

                            //-----------------------------
                            if (!txt_regno.getText().trim().equals("") && !n1.trim().equals("") && !txt_Ccode.getText().trim().isEmpty()) {
                                
                                String regno = txt_regno.getText();
                                String Ccode = txt_Ccode.getText();
                                
                                if (StudentsClass.checkStudent(regno).equals(false) && !lbl_imgname.getText().equals("")) {
                                    
                                    if (lbl_imgname.getText().equals("Default Image")) {
                                        
                                    } else {
                                        
                                        copyImage(regno);
                                    }
                                    
                                    boolean result = StudentsClass.registerStudent(n1, Ccode, regno, year, s2, s1, new_filename, dor, gender);
                                    
                                    StudentsClass.AddRegNumber(regno, n1);
                                    
                                    if (result) {
                                        
                                        openDialog(" Registration Successfull  Welcome " + n1 + " \nTo Muyeye Vocational Trainning Centre");
                                        
                                        String desc = "Registration Of Student \nRegCode " + regno + ""
                                                + "->Course " + Ccode + " ->Year ->" + year + "\n"
                                                + "Name " + n1;
                                        sqlDataBaseConnection.add_activity("Activity:" + n, "Registration", desc, dor, LoginscreenController.getUserName());
                                        
                                        new_filename = "";
                                        lbl_imgname.setText("");
                                        
                                    } else {
                                        
                                        openDialog(" Registration Of " + n1 + " Has Failed ");
                                        
                                    }
                                    
                                } else {
                                    
                                    openDialog(txt_regno.getText() + " Alread Exist \n"
                                            + " Belong To Student " + StudentsClass.getStname(regno) + "\n Please Change and Proceed Next Step \n"
                                            + "Two Students Cant Own One Registration Code");
                                    
                                }
                            } else {
                                
                                openDialog("Incomplete Information.. \n Some Information Is Missing Please Recheck The Form and Add More Infor Where"
                                        + "Necessary");
                                
                            }

                            //-----------------------------
                        }
                    } else {
                        
                        openDialog("Enter Date Of Registration \n"
                                + "Please Select Date Of Registration Click The Calendar Icon To View and Select From Calendar");
                        
                    }
                    
                } else {
                    
                    openDialog("Select Gender\n This Field Is Required To Complete Student Registraton To Merms");
                }
                
            } else {
                
                openDialog(" No Image Selected \n->Either Select Image From Computer \n->Or Click Use Default  To Use Default Image \n->Or"
                        + "Click Take Pic To open Camera");
                
            }
        } catch (java.lang.NullPointerException exc) {
            
            openDialog("Student Registration Failed \n->Please Make Sure You Leave No Blank.. "+exc);
            
        }
        
    }
    
    @FXML
    public void openFileChooser() {
        
        Stage stage = (Stage) btn_chooseImage.getScene().getWindow();
        
        FileChooser chooser = new FileChooser();
        chooser.setTitle("P.E.R.M.S");
        
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images Only", "*.jpg", "*.png", "*.JPEG", "*.TIFF");
        chooser.getExtensionFilters().add(filter);
        
        File file = chooser.showOpenDialog(stage);
        
        if (file != null) {
            
            image_path = file.getAbsolutePath();
            image_name = file.getName();
            selected_image = file.getAbsoluteFile();
            lbl_imgname.setText(image_name);
            
            Image im = new Image(selected_image.toURI().toString());
            
            rect_image.setFill(new ImagePattern(im));
            
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
    
    @FXML
    public void useDefaultImage() {
        
        if (!cmb_gender.getSelectionModel().isEmpty()) {
            
            String gender = cmb_gender.getSelectionModel().getSelectedItem().toString();
            
            if (gender.equals("Female")) {
                
                new_filename = "defaultf.png";
                lbl_imgname.setText("Default Image");
                Image im = new Image("reportgenthree/views/System_images/defaultf.png", false);
                
                rect_image.setFill(new ImagePattern(im));
            } else {
                
                new_filename = "defaultm.png";
                lbl_imgname.setText("Default Image");
                Image im = new Image("reportgenthree/views/System_images/defaultm.png", false);
                
                rect_image.setFill(new ImagePattern(im));
                
            }
            
        }
        
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
            
            JFXButton buttonCancel = new JFXButton("Ok");
            buttonCancel.setStyle("-fx-border-color:seagreen;-fx-border-width:1");
            
            buttonCancel.setOnAction(e -> dlog.close());
            
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
    
    public static String getCourseName(String cname) {
        String cabr = "";
        
        String query = "SELECT CourseAbreviation FROM CourseTable WHERE CourseName = '" + cname + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {
            
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            
            if (rst.next()) {
                
                cabr = rst.getString("CourseAbreviation");
            }
            conn.close();
        } catch (SQLException exc) {
            
            JOptionPane.showMessageDialog(null, "<HTML>Error Number 007 Source Course Functions <br>" + exc + "</HTML>");
            
        }
        
        return cabr;
        
    }
    
    public void setAbriviation() {
        
        if (!cmb_course.getSelectionModel().isEmpty()) {
            
            String course = cmb_course.getSelectionModel().getSelectedItem().toString();
            
            txt_Ccode.setText(getCourseName(course));
            
        }
        
    }
    
    public void clear() {
        
        reportgenthree.ReportGenThree.NavBar_StudentsRegistration();
        
    }
    
    public void take_pic() {
        
        if (!txt_regno.getText().isEmpty()) {
            
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(Students_datadisplayerController.class.getResource("Students_ImageViewer.fxml"));
            try {
                
                StackPane pane = loader.load();
                Students_ImageViewerController cc = loader.getController();
                
                cc.setStd_admno(txt_regno.getText());
                File file = new File("reportgenthree/views/System_images/defaultm.png");
                //File file_one = new File(Student_RegistrationFormController.class.getResource("views/System_images/defaultm.png").getFile());
                cc.setImage(file);
                cc.setDetails("Image For" + txt_regno.getText());
                
                Scene scene = new Scene(pane, 420, 485);
                Stage stage = new Stage();
                stage.setOnCloseRequest((WindowEvent event) -> {
                    Platform.runLater(() -> {
                        
                        new_filename = txt_regno.getText() + ".jpg";
                        File image = new File(home + "/ReportGenThree/students/" + new_filename);
                        lbl_imgname.setText(new_filename);
                        Image im = new Image(image.getAbsoluteFile().toURI().toString(), false);
                        rect_image.setFill(new ImagePattern(im));
                        
                    });
                });
                
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                
            } catch (IOException exc) {
                
            }
            
        } else {
            
            openDialog("Enter Registration Number First....");
        }
        
    }
    
}
