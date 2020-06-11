/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import Connection.sqlDataBaseConnection;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;


public class Accounts_EditInforController implements Initializable {

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
    private Label lbl_group;

    @FXML
    private StackPane mypane;

    @FXML
    private JFXButton btn_getPhoto;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXButton btn_changeuname;

    @FXML
    private JFXPasswordField f_password;

    @FXML
    private JFXPasswordField s_password;

    @FXML
    private JFXButton btn_changepass;

    @FXML
    private JFXButton btn_Exit;

    @FXML
    private JFXButton btn_files;

    @FXML
    private JFXTextField txt_path;

    @FXML
    private JFXButton btn_savePhoto;

    String regcode = "";

    private String image_path = "";
    private String image_name = "";
    private File selected_image = null;
    private String new_filename = "";
    String uname = "";
    String gname = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        String imagename = LoginscreenController.getImage();

        String home = System.getProperty("user.home") + "/" + "Documents" + "/" + "ReportGenThree" + "/" + "users" + "/" + imagename;

        if (!imagename.equals("")) {

            File imagepath = new File(home);
            Image im = new Image(imagepath.toURI().toString(), false);
            imgcircle.setFill(new ImagePattern(im));

        } else {

            Image im = new Image("reportgenthree/AppCss/images/1.jpg", false);

            imgcircle.setFill(new ImagePattern(im));

        }

        uname = LoginscreenController.getUserName();
        gname = LoginscreenController.getGName();
        lbl_username.setText(uname);
        lbl_group.setText("Group: " + gname);

        regcode = LoginscreenController.getRegCode();
        

    }

    public void changeName() {

        String name = username.getText();
        String query = "";
        String query_activities = "";
        String query_assignedunits = "";
        boolean result;

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        if (name.trim().length() >= 6) {

            if (gname.equals("Academics Admin") || gname.equals("Finance Admin")) {

                query = "UPDATE users SET UserName = '" + name + "' WHERE RegCode = '" + regcode + "' ";

                result = check_teacher("users", "UserName", name);

                try {

                    if (!result) {
                        Statement st = conn.createStatement();
                        int n_1 = st.executeUpdate(query);

                        conn.close();


                        if (n_1 >= 1) {

                            openDialog("Admin Name Has Been Changed To \n " + name + " \n Restart Application To View Changes");

                            String desc = regcode + " Changed User Name To " + name;

                            sqlDataBaseConnection.add_activity("Activity:" + n, "Admin Infor",
                                    desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());
                        } else {
                            openDialog("Sorry Could Not Change Your User Name \n " + name + " ");
                        }

                    } else {

                        openDialog("User Name Is In Use..  (" + name + ") \nUse Another Name");
                    }

                } catch (SQLException exc) {

                    openDialogTwo("Error " + exc);
                }

            } else {

                query = "UPDATE Teacher SET Teacher_Name = '" + name + "' WHERE Teacher_IdNumber = '" + regcode + "' ";
                
                
                result = check_teacher("Teacher", "Teacher_IdNumber", name);

                try {

                    if (!result) {
                        Statement st = conn.createStatement();
                        int n_1 = st.executeUpdate(query);
                        
                        
                        conn.close();

                       

                        if (n_1 >= 1) {

                            openDialog("User Name Has Been Changed To \n " + name + " \n Restart Application To View Changes");

                            String desc = regcode + " Changed User Name To " + name;

                            sqlDataBaseConnection.add_activity("Activity:" + n, "User Infor",
                                    desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());
                            
                            
                        } else {
                            openDialog("Sorry Could Not Change Your User Name \n " + name + " ");
                        }

                    } else {

                        openDialog("User Name Is In Use..  (" + name + ") \nUse Another Name");
                    }

                } catch (SQLException exc) {

                    openDialogTwo("Error " + exc);
                }

            }

        } else {

            openDialogTwo("User Name Should Have Atleast 6 Characters");
        }
    }

    public void changePass() {
        
        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        Random rand = new Random();

        int n = rand.nextInt(50000) + 1;

        String p1 = f_password.getText();
        String p2 = s_password.getText();

        if (p1.trim().length() >= 6) {

            if (p1.equals(p2)) {

                String query_main = "";

                if (gname.equals("Academics Admin") || gname.equals("Finance Admin")) {

                    query_main = "UPDATE users SET password = '" + p1 + "' WHERE RegCode = '" + regcode + "' ";

                } else {
                    query_main = "UPDATE Teacher SET Password = '" + p1 + "' WHERE Teacher_IdNumber = '" + regcode + "' ";

                }
                Connection conn = sqlDataBaseConnection.sqliteconnect();

                try {

                    Statement st = conn.createStatement();
                    st.executeUpdate(query_main);
                    conn.close();

                    openDialog("Password Has Been Changed \n Restart Application To Viw Changes");
                    
                    String desc = regcode + " Changed Password To ****";

                            sqlDataBaseConnection.add_activity("Activity:" + n, "User/Admin Infor",
                                    desc, fomat.format(LocalDateTime.now()), LoginscreenController.getUserName());

                } catch (SQLException exc) {

                    openDialogTwo("Error " + exc);
                }

            } else {

                openDialogTwo("Passwords Do Not Match");

            }

        } else {

            openDialogTwo("Password Should Have Atleast 6 Characters");
        }
    }

    public void openDialog(String message) {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label("M.E.R.M.S"));
        content.setBody(new Text(message));

        JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonCancel = new JFXButton("Exit");
        JFXButton Restart = new JFXButton("Restart");
        Restart.setOnAction(e -> ReportGenThree.logoutApp());

        buttonCancel.setOnAction((ActionEvent event) -> {
            ReportGenThree.Accounts_EditInfor();
            dlog.close();
        });

        content.setActions(buttonCancel, Restart);

        dlog.show();

    }

    public void openDialogTwo(String message) {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label("M.E.R.M.S"));
        content.setBody(new Text(message));

        JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonCancel = new JFXButton("Exit");

        buttonCancel.setOnAction((ActionEvent event) -> {
            ReportGenThree.Accounts_EditInfor();
            dlog.close();
        });

        content.setActions(buttonCancel);

        dlog.show();

    }

    public void openFileChooser() {

        Stage stage = (Stage) btn_changepass.getScene().getWindow();

        FileChooser chooser = new FileChooser();
        chooser.setTitle("M.E.R.M.S");

        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Images Only", "*.jpg", "*.png", "*.JPEG", "*.TIFF");
        chooser.getExtensionFilters().add(filter);

        File file = chooser.showOpenDialog(stage);

        if (file != null) {

            image_path = file.getAbsolutePath();
            image_name = file.getName();
            selected_image = file.getAbsoluteFile();
            txt_path.setText(image_name);

            Image im = new Image(selected_image.toURI().toString());

            imgcircle.setFill(new ImagePattern(im));

        }
    }

    public void saveImage() {

        String query = "";
        if (!txt_path.getText().trim().isEmpty()) {

            copyImage(regcode);

            if (gname.equals("Academics Admin") || gname.equals("Finance Admin")) {

                query = "UPDATE users SET userimage = '" + new_filename + "' WHERE RegCode = '" + regcode + "' ";
            } else {

                query = "UPDATE Teacher SET Image = '" + new_filename + "' WHERE Teacher_IdNumber = '" + regcode + "' ";

            }

            Connection conn = sqlDataBaseConnection.sqliteconnect();

            try {

                Statement st = conn.createStatement();
                st.executeUpdate(query);
                conn.close();
                openDialog("Image Has Been Saved");

            } catch (SQLException exc) {

                openDialogTwo("Sory, There Is An Error " + exc);
            }
        } else {

            openDialogTwo("Soory, Choose Image First ");

        }

    }

    public void copyImage(String regcode) {

        String extension = "";

        int i = image_name.lastIndexOf(".");

        if (i >= 0) {

            extension = image_name.substring(i + 1);

        }

        new_filename = regcode.concat("." + extension);

        String home = System.getProperty("user.home") + "/" + "Documents";

        File dest = new File(home + "/ReportGenThree/users/" + new_filename);
        File srcfile = selected_image;

        try {

            Files.copy(srcfile.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException ex) {

            System.out.println("" + ex);

        }

    }

    public boolean check_teacher(String table, String teacher_field, String teacher_data) {

        boolean results = false;

        String query = "SELECT * FROM " + table + " WHERE " + teacher_field + " = '" + teacher_data + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                results = true;
            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return results;
    }

}
