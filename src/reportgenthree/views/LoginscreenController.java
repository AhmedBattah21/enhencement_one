/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Connection.sqlDataBaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class LoginscreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lbl_infor;

    @FXML
    private JFXComboBox<?> cmb_group;

    @FXML
    private Label lbl_group;

    @FXML
    private JFXTextField txt_name;

    @FXML
    private Label lbl_name;

    @FXML
    private JFXPasswordField txt_pass;

    @FXML
    private Label lbl_pass;

    @FXML
    private JFXButton btn_login;

    @FXML
    private JFXButton btn_fgpass;

    @FXML
    private JFXButton btn_createacc;

    Stage mystage;

    private static String Publicusername;
    private static String PublicGname;
    private static String regcode;
    private static String image;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //mystage = (Stage)btn_login.getScene().getWindow();

        lbl_group.setVisible(false);
        lbl_pass.setVisible(false);
        lbl_name.setVisible(false);
        lbl_infor.setText("Log In");
        ObservableList groups = FXCollections.observableArrayList();
        groups.addAll("Academics Admin", "Finance Admin", "Instractor");
        cmb_group.setItems(groups);

    }

    public void login() {

        if (!cmb_group.getSelectionModel().isEmpty()) {

            String group = cmb_group.getSelectionModel().getSelectedItem().toString();

            if (!txt_name.getText().trim().equals("")) {

                String uname = txt_name.getText();

                if (!txt_pass.getText().trim().equals("")) {

                    String pass = txt_pass.getText();

                    String query = "";

                    if (group.equals("Academics Admin") || group.equals("Finance Admin")) {

                        query = "SELECT * FROM users WHERE UserName = '" + uname + "' AND password = '" + pass + "' AND UserGroup = '" + group + "'";

                    } else {

                        query = "SELECT * FROM Teacher WHERE Teacher_IdNumber = '" + uname + "' AND Password = '" + pass + "'";
                    }

                    Connection conn = sqlDataBaseConnection.sqliteconnect();

                    try {

                        Statement st = conn.createStatement();

                        ResultSet rst = st.executeQuery(query);

                        if (rst.next()) {

                            String status = "";
                            if (group.equals("Academics Admin") || group.equals("Finance Admin")) {

                                Publicusername = uname;
                                PublicGname = group;
                                status = rst.getString("status");
                                regcode = rst.getString("RegCode");
                                image = rst.getString("userimage").trim();

                            } else {

                                Publicusername =rst.getString("Teacher_IdNumber");
                                PublicGname = group;
                                status = rst.getString("Status");
                                regcode = rst.getString("Teacher_IdNumber");
                                image = rst.getString("Image").trim();

                            }

                            if (!status.equals("blocked")) {

                                ReportGenThree.closeSplash();

                            } else {

                                lbl_infor.setText("Account Is Blocked");

                            }

                        } else {

                            lbl_infor.setText("Sorry Wrong Password!!!");

                        }

                        conn.close();

                    } catch (SQLException exc) {

                        System.out.println("" + exc);
                    }

                } else {

                    lbl_pass.setVisible(true);

                }

            } else {

                lbl_name.setVisible(true);

            }
        } else {

            lbl_group.setVisible(true);

        }

    }

    @FXML
    public void keylistener_group(KeyEvent evt) {

        if (!cmb_group.getSelectionModel().isEmpty()) {

            if (evt.getCode() == KeyCode.ENTER) {

                txt_name.requestFocus();
                lbl_group.setVisible(false);

            }
        } else {

            evt.consume();
            lbl_group.setVisible(true);
        }

    }

    @FXML
    public void keylistener_username(KeyEvent evt) {

        if (!txt_name.getText().trim().equals("")) {

            if (evt.getCode() == KeyCode.ENTER) {

                txt_pass.requestFocus();
                lbl_name.setVisible(false);

            }
        } else {

            evt.consume();
            lbl_name.setVisible(true);

        }

    }

    @FXML
    public void keylistener_pass(KeyEvent evt) {

        if (!txt_pass.getText().trim().equals("")) {

            if (evt.getCode() == KeyCode.ENTER) {

                btn_login.requestFocus();
                lbl_pass.setVisible(false);

            }
        } else {

            evt.consume();
            lbl_pass.setVisible(true);

        }

    }

    public static String getUserName() {

        return Publicusername;

    }

    public static String getGName() {

        return PublicGname;

    }

    public static String getRegCode() {

        return regcode;

    }

    public static String getImage() {

        return image;

    }

    public static void setPublicusername(String Publicusername) {
        LoginscreenController.Publicusername = Publicusername;
    }

    public static void setPublicGname(String PublicGname) {
        LoginscreenController.PublicGname = PublicGname;
    }
    
    

}
