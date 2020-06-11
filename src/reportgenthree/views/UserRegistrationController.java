/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class UserRegistrationController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField txt_username;

    @FXML
    private JFXPasswordField txt_ps1;

    @FXML
    private JFXPasswordField txt_ps2;

    @FXML
    private JFXTextField txt_egcode;

    @FXML
    private JFXButton btn_sign;

    @FXML
    private Label lbl_infor;
    
    @FXML
    private JFXButton btn_close;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbl_infor.setVisible(false);
       
    }

    public void signUp() {

        if (!txt_username.getText().equals("")) {

            String uname = txt_username.getText().trim();

            if (!txt_ps1.getText().equals("")) {

                String ps1 = txt_ps1.getText().trim();

                if (!txt_ps2.getText().equals("")) {

                    String ps2 = txt_ps2.getText().trim();

                    if (txt_ps2.getText().equals(txt_ps1.getText())) {

                        if (!txt_egcode.getText().equals("")) {
                            String regcode = txt_egcode.getText();

                            if (checkUser(regcode).equals(true)) {

                                String query = "UPDATE users SET UserName = '" + uname + "',password = '" + ps1 + "'"
                                        + "WHERE RegCode = '"+regcode+"'";
                                Connection conn = sqlDataBaseConnection.sqliteconnect();
                                try {

                                    Statement st = conn.createStatement();
                                    st.executeUpdate(query);
                                    
                                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_CIRCLE);

                                    lbl_infor.setText("Thank You, You Have Been Registered!!");
                                    lbl_infor.setGraphic(icon);
                                    lbl_infor.setVisible(true);
                                    
                                    conn.close();

                                } catch (SQLException exc) {

                                    System.out.println("" + exc);
                                }

                            } else {

                                lbl_infor.setVisible(true);

                                lbl_infor.setText("Incorrect Registration Code");

                            }

                        } else {

                            lbl_infor.setVisible(true);

                            lbl_infor.setText("Insert Registration Code");

                        }

                    } else {

                        lbl_infor.setVisible(true);

                        lbl_infor.setText("Password Do Not Match!!");

                    }

                } else {

                    lbl_infor.setVisible(true);

                    lbl_infor.setText("Your Password");

                }

            } else {

                lbl_infor.setVisible(true);

                lbl_infor.setText("Your Password");

            }

        } else {

            lbl_infor.setVisible(true);
            lbl_infor.setText("Your User Name!!");

        }

    }

    public Boolean checkUser(String regcode) {
        
        String name = "";

        String query = "SELECT * FROM users WHERE RegCode = '" + regcode + "' AND UserName = '"+name+"'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        boolean checker = false;

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                checker = true;

            }

            conn.close();
        } catch (SQLException exc) {

            System.out.println(""+exc);
        }
        return checker;

    }
    
    
    public void close(){
    
    Stage stage = (Stage)btn_close.getScene().getWindow();
    stage.close();
    
    }

}
