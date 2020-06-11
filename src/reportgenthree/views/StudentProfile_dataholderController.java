/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentProfile_dataholderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Circle image_holder;
    
    @FXML
    private JFXButton btn_examination;
    
    @FXML
    private JFXButton btn_fstatements;
    
    @FXML
    private JFXTabPane parenttab;
    
    @FXML
    private Tab default_pane;
    
    @FXML
    private StackPane mypane;
    
    Tab examFileTab = null;
    Tab edit_form = null;
    Tab edit_image = null;
    
    String stdImageName = "";
    File selectedImage = null;
    String image_path = "";
    String new_filename = "";
    String gender = "";
    
    String home = System.getProperty("user.home") + "/" + "Documents";
    
    String admno;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn_examination.setOnAction(e -> get_data());
        
        default_pane.setText("Student Profile..");
        
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.WECHAT);
        
        placeInfpBox(default_pane, "Welcome To Student Profile here", icon);
        
    }
    
    public void get_data() {
        
        if (examFileTab == null) {
            
            examFileTab = new Tab("Examinations Details " + admno);
            
            ContextMenu menu = new ContextMenu();
            
            VBox gridpane = new VBox();
            gridpane.setAlignment(Pos.TOP_CENTER);
            
            gridpane.setOnContextMenuRequested((ContextMenuEvent event) -> {
                menu.show(gridpane, event.getScreenX(), event.getScreenY());
            });
            
            MenuItem item1 = new MenuItem("Examination Details " + admno);
            menu.getItems().add(item1);
            
            MenuItem item2 = new MenuItem("Close Tab " + admno);
            item2.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
            menu.getItems().add(item2);
            
            item2.setOnAction((ActionEvent event) -> {
                
                parenttab.getTabs().remove(examFileTab);
                examFileTab = null;
            });
            
            HBox box = new HBox();
            box.getChildren().clear();
            HBox.setHgrow(box, Priority.ALWAYS);
            VBox main_holder = new VBox();
            AnchorPane title = null;
            
            String hint = admno;
            
            String query = "SELECT * FROM exam_2017 WHERE Adm_Number = '" + hint + "' "
                    + "GROUP BY Exam_Name,Exam_Term,Year,Syear ORDER BY Year,Exam_Term ASC";
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            
            try {
                
                Statement st = conn.createStatement();
                ResultSet rst = st.executeQuery(query);
                
                int count = 0;
                
                int exams = 0;
                
                ScrollPane spane = new ScrollPane();
                
                while (rst.next()) {
                    
                    HBox.setHgrow(box, Priority.ALWAYS);
                    box.setAlignment(Pos.CENTER_LEFT);
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(StudentProfile_dataholderController.class.getResource("StudentExam_holder.fxml"));
                    AnchorPane pane = loader.load();
                    StudentExam_holderController cc = loader.getController();
                    
                    FXMLLoader loader_title = new FXMLLoader();
                    loader_title.setLocation(StudentProfile_dataholderController.class.getResource("StudentExam_holdertitle.fxml"));
                    title = loader_title.load();
                    StudentExam_holdertitleController cc_title = loader_title.getController();
                    cc_title.setExam_title(rst.getString("Year") + "  " + rst.getString("Exam_Term"));
                    
                    String term = rst.getString("Exam_Term");
                    String exam_year = rst.getString("Year");
                    String adm_number = hint;
                    String std_year = rst.getString("Syear");
                    String exam_name = rst.getString("Exam_Name");
                    
                    String num_units = count_units(term, std_year, exam_name, adm_number, exam_year);
                    
                    cc.setStexam_term(term);
                    cc.setStexam_year(exam_year);
                    cc.setStgrade("Overall Remark :St Grade");
                    cc.setStunits("Number Of Units : " + num_units);
                    cc.setAdm_no(adm_number);
                    cc.setStd_year(std_year);
                    cc.setExam_name(exam_name);
                    
                    cc.setTabpane(parenttab);
                    count = count + 1;
                    
                    box.getChildren().add(pane);
                    
                    if (exam_name.equals("END STAGE")) {
                        
                        count = 2;
                        
                    }
                    
                    if (count == 2) {
                        
                        if (!exam_name.equals("END STAGE")) {
                            
                            box.getChildren().add(getfinals(term, adm_number, std_year, exam_year));
                            
                        }
                        
                        main_holder.getChildren().add(title);
                        main_holder.getChildren().add(box);
                        gridpane.getChildren().add(main_holder);
                        
                        box = new HBox();
                        main_holder = new VBox();
                        
                        count = 0;
                    }
                    
                    exams++;
                    
                }
                
                if (!box.getChildren().isEmpty()) {
                    
                    main_holder.getChildren().add(title);
                    main_holder.getChildren().add(box);
                    gridpane.getChildren().add(main_holder);
                    
                }
                
                if (exams >= 1) {
                    
                    ScrollPane pane = new ScrollPane();
                    pane.setFitToWidth(true);
                    pane.setContent(gridpane);
                    examFileTab.setContent(pane);
                    parenttab.getTabs().add(examFileTab);
                    parenttab.getSelectionModel().select(examFileTab);
                    
                } else {
                    String message = "No Examinations Files/Details Found For This Student...";
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.INFO);
                    icon.setGlyphSize(150);
                    icon.setGlyphStyle("-fx-fill:skyblue");
                    gridpane.setAlignment(Pos.CENTER);
                    
                    StackPane pane = setNoDataPane(message, icon);
                    
                    pane.setMaxSize(250, 250);
                    gridpane.getChildren().add(pane);
                    examFileTab.setContent(gridpane);
                    parenttab.getTabs().add(examFileTab);
                    parenttab.getSelectionModel().select(examFileTab);
                    
                }
                
                conn.close();
                
            } catch (SQLException | IOException exc) {
                
                System.out.println(" " + exc);
                
            }
            
        } else {
            
            parenttab.getSelectionModel().select(examFileTab);
            
        }
        
    }
    
    public AnchorPane getfinals(String term, String adm, String std_year, String exam_year) {
        
        String query_one = "SELECT Mid,End,Exam_Term,Unit_Name,Unit,ID,Year, Mid_year,Syear,stdyear "
                + "FROM"
                + "(SELECT Exam_Name As Mid_Name,Exam_Score As Mid,Exam_Term As Term,Adm_Number As ID,Unit_Name,Year As Mid_year,Syear FROM"
                + " exam_2017 where Adm_Number = '" + adm + "' AND Exam_Name='MID TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "') "
                + "JOIN "
                + "(SELECT Exam_Name As End_Name,Exam_Score As End,Exam_Term,Adm_Number,Unit_Name As Unit,Year,Syear As stdyear FROM"
                + " exam_2017 where Adm_Number = '" + adm + "' AND Exam_Name='END TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' )"
                + "ON Adm_Number = ID AND Unit_Name = Unit AND Exam_Term = Term AND Syear = stdyear AND Mid_year = Year";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        AnchorPane pane = null;
        
        try {
            
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query_one);
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentProfile_dataholderController.class.getResource("StudentExam_holder.fxml"));
            pane = loader.load();
            StudentExam_holderController cc = loader.getController();
            
            int count = getCount_two(term, adm, std_year, exam_year);
            
            if (rst.next()) {
                
                cc.setStexam_term(term);
                cc.setStexam_year(exam_year);
                cc.setStgrade("Overall Remark :St Grade");
                cc.setStunits("Number Of Units : " + count);
                cc.setAdm_no(adm);
                cc.setStd_year(std_year);
                cc.setExam_name("Term Final  Totals");
                
                cc.setTabpane(parenttab);
                
            } else {
                
                cc.setStexam_term(term);
                cc.setStexam_year(exam_year);
                cc.setStgrade("Overall Remark :No Records Yet");
                cc.setStunits("Number Of Units : No Records Yet");
                cc.setAdm_no(adm);
                cc.setStd_year(std_year);
                cc.setExam_name("No Records Yet");
                
                cc.setTabpane(parenttab);
                
            }
            conn.close();
            
        } catch (SQLException exc) {
            
            System.out.println("Error " + exc);
            
        } catch (IOException ex) {
            
            Logger.getLogger(StudentProfile_dataholderController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pane;
        
    }
    
    public int getCount_two(String term, String adm, String std_year, String exam_year) {
        
        String query_one = "SELECT COUNT(Mid) AS count "
                + "FROM"
                + "(SELECT Exam_Name As Mid_Name,Exam_Score As Mid,Exam_Term As Term,Adm_Number As ID,Unit_Name,Year As Mid_year,Syear FROM"
                + " exam_2017 where Adm_Number = '" + adm + "' AND Exam_Name='MID TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "') "
                + "JOIN "
                + "(SELECT Exam_Name As End_Name,Exam_Score As End,Exam_Term,Adm_Number,Unit_Name As Unit,Year,Syear As stdyear FROM"
                + " exam_2017 where Adm_Number = '" + adm + "' AND Exam_Name='END TERM' and Exam_Term = '" + term + "' AND Year = '" + exam_year + "' AND Syear = '" + std_year + "' )"
                + "ON Adm_Number = ID AND Unit_Name = Unit AND Exam_Term = Term AND Syear = stdyear AND Mid_year = Year";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        int count = 0;
        
        try {
            
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query_one);
            
            if (rst.next()) {
                
                count = Integer.parseInt(rst.getString("count"));
                
            }
            conn.close();
            
        } catch (SQLException exc) {
            
            System.out.println("Error " + exc);
            
        }
        return count;
        
    }
    
    public void setAdmno(String admno) {
        this.admno = admno;
    }
    
    public void funnyMethod() {
        
        String hint = admno;
        
        final StackPane stackpaneholder = new StackPane();
        
        StackPane stpane_one = new StackPane();
        
        ContextMenu menu = new ContextMenu();
        
        stackpaneholder.setOnContextMenuRequested((ContextMenuEvent event) -> {
            menu.show(stackpaneholder, event.getScreenX(), event.getScreenY());
        });
        
        MenuItem item1 = new MenuItem("Student Details " + admno);
        menu.getItems().add(item1);
        
        MenuItem item2 = new MenuItem("Close Tab " + admno);
        item2.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TIMES));
        menu.getItems().add(item2);
        
        MenuItem item3 = new MenuItem("Refresh Tab " + admno);
        item3.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.REFRESH));
        menu.getItems().add(item3);
        
        MenuItem item4 = new MenuItem("Edit " + admno);
        item4.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
        menu.getItems().add(item4);
        
        item4.setOnAction((ActionEvent event) -> {
            
            edit_data();
        });
        
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BINOCULARS);
        
        if (hint.equals("")) {
            
            placeInfpBox(default_pane, "No Student Match To Your Search \n Kindly Enter New Hint", icon);
            
        } else {
            
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            
            hint = hint.trim();
            
            String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + hint + "' ";
            
            try {
                
                Statement st = conn.createStatement();
                
                ResultSet rst = st.executeQuery(query);
                
                int results = 0;
                
                if (rst.next()) {
                    
                    stdImageName = rst.getString("std_image");
                    
                    File imagepath = new File(home + "/ReportGenThree/students/" + stdImageName);
                    Image im = new Image(imagepath.toURI().toString(), false);
                    image_holder.setFill(new ImagePattern(im));
                    
                    results++;
                    
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Student_FindStudentsController.class.getResource("Student_informationpage.fxml"));
                    try {
                        
                        stpane_one = loader.load();
                        Student_informationpageController controller = loader.getController();
                        
                        controller.getAdm_number().setText(rst.getString("Ccode") + "/" + rst.getString("StudentRegCode"));
                        controller.getStd_course().setText(rst.getString("StudentCourse"));
                        controller.getStd_name().setText(rst.getString("StudentName"));
                        controller.getStd_dor().setText(rst.getString("DOR"));
                        controller.getStd_year().setText(rst.getString("YearOfStudy"));
                        controller.getStd_category().setText(rst.getString("StudentCategory"));
                        controller.setImagepath(imagepath);
                        gender = rst.getString("Gender");
                        
                        stackpaneholder.getChildren().add(stpane_one);
                        
                        default_pane.setText("Student Information");
                        ScrollPane spane = new ScrollPane();
                        spane.setFitToHeight(true);
                        spane.setFitToWidth(true);
                        spane.setContent(stackpaneholder);
                        default_pane.setContent(spane);
                        parenttab.getSelectionModel().select(default_pane);
                        
                    } catch (IOException ex) {
                        
                        System.out.println("" + ex);
                    }
                    
                }
                
                conn.close();
                
            } catch (SQLException exc) {
                
                System.out.println("" + exc);
                
            }
            
        }
        
    }
    
    public void edit_data() {
        
        if (edit_form == null) {
            
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(StudentProfile_dataholderController.class.getResource("Student_EditDataForm.fxml"));
                StackPane pane = loader.load();
                Student_EditDataFormController cc = loader.getController();
                cc.setRegno(admno);
                cc.getStudent();
                
                edit_form = new Tab("Edit Details " + admno);
                edit_form.setContent(pane);
                parenttab.getTabs().add(edit_form);
                parenttab.getSelectionModel().select(edit_form);
                
                edit_form.setOnCloseRequest(e -> {
                    
                    edit_form = null;
                });
                
            } catch (IOException ex) {
                
                Logger.getLogger(StudentProfile_dataholderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            
            parenttab.getSelectionModel().select(edit_form);
            
        }
        
    }
    
    public void edit_image() {
        
        if (edit_image == null) {
            
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(StudentProfile_dataholderController.class.getResource("Students_ImageViewer.fxml"));
                StackPane pane = loader.load();
                Students_ImageViewerController cc = loader.getController();
                
                File imagepath = new File(home + "/ReportGenThree/students/" + stdImageName);
                cc.setImage(imagepath);
                cc.setStd_admno(admno);
                cc.setDetails("Student Image " + admno);
                
                cc.getBtn_editpic().setText("Choose From File");
                cc.getBtn_editpic().setOnAction(e -> {
                    
                    if (openFileChooser()) {
                        
                        File image = new File(home + "/ReportGenThree/students/" + new_filename);
                        
                        copyImage();
                        cc.setImage(image);
                        change_imageName(new_filename);
                        
                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_ALT_UP);
                        icon.setGlyphSize(150);
                        icon.setGlyphStyle("-fx-fill:skyblue");
                        openDialog("Image Changed!!", icon);
                        
                    }
                });
                
                cc.getBtn_opencam().setText("Use Default");
                cc.getBtn_opencam().setOnAction(e -> {
                    
                    cc.setImage(useDefaultImage());
                    change_imageName(new_filename);
                });
                
                edit_image = new Tab("Edit Image " + admno);
                edit_image.setContent(pane);
                parenttab.getTabs().add(edit_image);
                parenttab.getSelectionModel().select(edit_image);
                
                edit_image.setOnCloseRequest(e -> {
                    
                    edit_image = null;
                });
                
            } catch (IOException ex) {
                
                Logger.getLogger(StudentProfile_dataholderController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            
            parenttab.getSelectionModel().select(edit_image);
            
        }
        
    }
    
    public StackPane add_inforbox(String Message, FontAwesomeIconView icon) {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Student_FindStudentsController.class.getResource("Information_window.fxml"));
        StackPane pane = null;
        icon.setGlyphSize(150);
        try {
            pane = loader.load();
            Information_windowController cc = loader.getController();
            cc.setInfor(Message);
            cc.setForm_icon(icon);
            
        } catch (IOException exc) {
            
            System.out.println("" + exc);
            
        }
        
        return pane;
        
    }
    
    public void placeInfpBox(Tab tab, String message, FontAwesomeIconView icon) {
        
        icon.setStyle("-fx-fill:seagreen");
        
        tab.setContent(add_inforbox(message, icon));
        
    }
    
    public void change_imageName(String new_imagename) {
        
        String query = "UPDATE students_2017 SET std_image ='" + new_imagename + "' WHERE StudentRegCode = '" + admno + "'";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {
            
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
            
        } catch (SQLException exc) {
            
            System.out.println("Error " + exc);
            
        }
        
    }
    
    public void addStudentInformation() {
        
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Student_profileController.class.getResource("Student_informationpage.fxml"));
            StackPane pane = loader.load();
            default_pane.setContent(pane);
            
        } catch (IOException ex) {
            Logger.getLogger(Student_profileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public String count_units(String term, String std_year, String exam_name, String adm_number, String exam_year) {
        
        String query = "select COUNT(Exam_Id) As Units from exam_2017 WHERE Exam_Term = '" + term + "' AND Exam_Name = '" + exam_name + "'"
                + " AND Syear = '" + std_year + "' AND Year = '" + exam_year + "' AND Adm_Number = '" + adm_number + "'";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        String count = "";
        try {
            
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {
                
                count = rst.getString("Units");
            }
            conn.close();
            
        } catch (SQLException exc) {
            
            System.out.println("Error " + exc);
            
        }
        
        return count;
        
    }
    
    public StackPane setNoDataPane(String message, FontAwesomeIconView icon) {
        
        StackPane pane = new StackPane();
        try {
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentProfile_dataholderController.class.getResource("Dialogs/Datasaved_dialog.fxml"));
            pane = loader.load();
            Datasaved_dialogController cc = loader.getController();
            cc.getLbl_infor().setText(message);
            cc.getLbl_icon().setGraphic(icon);
            
        } catch (IOException ex) {
            
            Logger.getLogger(Academics_AddNewScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pane;
        
    }
    
    public Boolean openFileChooser() {
        
        Stage stage = (Stage) btn_examination.getScene().getWindow();
        
        FileChooser chooser = new FileChooser();
        chooser.setTitle("M.E.R.M.S");
        
        boolean result = false;
        
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images Only", "*.jpg", "*.png", "*.JPEG");
        chooser.getExtensionFilters().add(filter);
        
        File file = chooser.showOpenDialog(stage);
        
        if (file != null) {
            
            image_path = file.getAbsolutePath();
            stdImageName = file.getName();
            selectedImage = file.getAbsoluteFile();
            
            Image im = new Image(selectedImage.toURI().toString());
            
            String extension = "";
            
            int i = stdImageName.lastIndexOf(".");
            if (i >= 0) {
                
                extension = stdImageName.substring(i + 1);
                
            }
            
            new_filename = admno.concat("." + extension);
            
            result = true;
        }
        
        return result;
        
    }
    
    public void copyImage() {
        
        String home = System.getProperty("user.home") + "/" + "Documents";
        
        File dest = new File(home + "/ReportGenThree/students/" + new_filename);
        File srcfile = selectedImage;
        
        try {
            
            Files.copy(srcfile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException ex) {
            
            System.out.println("" + ex);
            
        }
        
    }
    
    public void openDialog(String message, FontAwesomeIconView icon) {
        
        try {
            JFXDialogLayout content = new JFXDialogLayout();
            Label mylabel = new Label("Student Image Editor ");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentProfile_dataholderController.class.getResource("Dialogs/Datasaved_dialog.fxml"));
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
    
    public File useDefaultImage() {
        
        File im = null;
        
        switch (gender) {
            
            case "Female":
                new_filename = "defaultf.png";
                im = new File(home + "/ReportGenThree/students/" + new_filename);
                break;
            case "Male":
                new_filename = "defaultm.png";
                im = new File(home + "/ReportGenThree/students/" + new_filename);
                break;
            default:
                new_filename = "default.png";
                im = new File(home + "/ReportGenThree/students/" + new_filename);
                break;
        }
        
        return im;
        
    }
    
}
