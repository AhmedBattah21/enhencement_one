/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import static reportgenthree.ReportGenThree.getMajor_stackpane;

public class User_profileOneController implements Initializable {

    @FXML
    private Label lbl_fullname;

    @FXML
    private Label lbl_dept;

    @FXML
    private Label lbl_mainask;

    private String id_number = "";
    String imagename = "";

    String gname = "";
    String code = "";

    @FXML
    private Circle image_holder;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        get_userdata();
    }

    public void get_userdata() {

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        gname = LoginscreenController.getGName();
        code = LoginscreenController.getRegCode();

        if (gname.equals("Academics Admin") || gname.equals("Finance Admin")) {

            try {
                String query = "SELECT * FROM users WHERE RegCode = '" + code + "'";

                Statement st = conn.createStatement();
                ResultSet rst = st.executeQuery(query);

                if (rst.next()) {

                    String full_name = rst.getString("UserName");
                    id_number = rst.getString("RegCode");
                    String dept = get_dept("Teacher_Department", "Teacher", id_number);

                    lbl_fullname.setText("User Name/ Names " + full_name);
                    lbl_dept.setText(dept);
                    lbl_mainask.setText(gname);

                    if (!get_task(id_number).equals("null")) {

                        lbl_mainask.setText("(" + gname + " ) " + get_task(id_number));
                    }

                }

                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(User_profileOneController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            String query = "SELECT * FROM Teacher WHERE Teacher_IdNumber ='" + code + "'";

            try {

                Statement st = conn.createStatement();
                ResultSet rst = st.executeQuery(query);

                if (rst.next()) {

                    String full_name = rst.getString("Teacher_Name");
                    id_number = rst.getString("Teacher_IdNumber");
                    String dept = get_dept("Teacher_Department", "Teacher", id_number);

                    lbl_fullname.setText("User Name " + full_name);
                    lbl_dept.setText(dept);
                    lbl_mainask.setText(gname);

                    if (!get_task(id_number).equals("null")) {

                        lbl_mainask.setText("(" + gname + " ) " + get_task(id_number));
                    }

                }

                conn.close();

            } catch (SQLException ex) {
                Logger.getLogger(User_profileOneController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void set_image() {

        imagename = LoginscreenController.getImage();
        String home = System.getProperty("user.home") + "/" + "Documents" + "/" + "ReportGenThree" + "/" + "users" + "/" + imagename;
        

        if (!imagename.equals("")) {

            File imagepath = new File(home);

            if (imagepath.exists()) {

                File file = imagepath.getAbsoluteFile();
                Image im = new Image(file.toURI().toString(), false);
                image_holder.setFill(new ImagePattern(im));

            } else {

                Image im = new Image("reportgenthree/AppCss/images/1.jpg", false);

                image_holder.setFill(new ImagePattern(im));

            }

        } else {

            Image im = new Image("reportgenthree/AppCss/images/1.jpg", false);

            image_holder.setFill(new ImagePattern(im));

        }

    }

    public String get_dept(String col, String table, String condition) {

        String dept = "";
        String query = "SELECT " + col + " FROM " + table + " WHERE Teacher_IdNumber = '" + condition + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                dept = rst.getString(col);
            }
            conn.close();
        } catch (SQLException exc) {

            System.out.println(exc);
        }
        return dept;
    }

    public String get_task(String id) {

        String task = "null";
        String query = "SELECT Teacher_Task FROM Teacher WHERE Teacher_IdNumber = '" + id + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                task = rst.getString("Teacher_Task");
            }
            conn.close();
        } catch (SQLException exc) {

            System.out.println(exc);
        }
        return task;
    }

    public void view_myclasses() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(User_profileOneController.class.getResource("Teacher_viewMyClasses.fxml"));
            StackPane spane = loader.load();
            Teacher_viewMyClassesController cc = loader.getController();
            cc.setId(id_number);
            cc.get_units(id_number);
            cc.getLbl_code().setText("Your ID Code:" + code);
            cc.getLbl_initials().setText("Your Initials:" + get_dept("Teacher_Initials", "Teacher", id_number));
            cc.getLbl_name().setText("Your Full Name:" + get_dept("Teacher_Name", "Teacher", id_number));

            content.setBody(spane);

            StackPane pane = getMajor_stackpane();

            JFXDialog dlog = new JFXDialog(pane, content, JFXDialog.DialogTransition.BOTTOM);

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Accounts_NavBarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
