/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.Functions;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class SystemNameSetterController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane mypane;

    @FXML
    private TextField txt_sname;

    @FXML
    private TextField txt_box;

    @FXML
    private TextField txt_place;

    @FXML
    private TextField txt_imail;

    @FXML
    private TextField txt_contacts;

    @FXML
    private TextField txt_ref;

    @FXML
    private TextField txt_appname;

    @FXML
    private Button btn_save;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setNames() {

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        String sname = txt_sname.getText().toUpperCase();
        String box = txt_box.getText().toUpperCase();
        String place = txt_place.getText().toUpperCase();
        String email = txt_imail.getText().toLowerCase();
        String contacts = txt_contacts.getText().toUpperCase();
        String ref = txt_ref.getText().toUpperCase();
        String appname = txt_appname.getText().toUpperCase();

        if (!sname.equals("")) {

            if (!box.equals("")) {

                if (!place.equals("")) {

                    if (!email.equals("")) {

                        if (!contacts.equals("")) {

                            if (!ref.equals("")) {

                                if (!appname.equals("")) {

                                    String query = "UPDATE NameSetter SET sname ='" + sname + "', box ='" + box + "', place = '" + place + "',email = '" + email + "',"
                                            + "contacts = '" + contacts + "', ref = '" + ref + "', appname = '" + appname + "' WHERE appid = 1";

                                    try {

                                        Statement st = conn.createStatement();
                                        st.executeUpdate(query);
                                        conn.close();
                                        openDialog("Data Has Been Saved");

                                    } catch (SQLException exc) {

                                        Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                                                "Sytem has encountered an error \n"
                                                + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
                                    }

                                } else {

                                    openDialog("Enter Appname \n i.e Merms ");

                                }

                            } else {

                                openDialog("Enter ref \n i.e MPS/STR/VOL (01)2018");

                            }

                        } else {

                            openDialog("Enter Contacts \n i.e 07********/07********");

                        }

                    } else {

                        openDialog("Enter email adress \n i.e erickerickmlz@gmail.com");

                    }

                } else {

                    openDialog("Enter Town Name \n i.e Malindi kenya");

                }

            } else {

                openDialog("Enter Box Number \n i.e P.O.BOX 1919-80200");

            }

        } else {

            openDialog("Enter School Name");

        }

    }

    public void openDialog(String message) {

        JFXDialogLayout content = new JFXDialogLayout();
        Label mylabel = new Label("Fee Accounts");

        content.setHeading(mylabel);
        content.setAlignment(Pos.CENTER);
        content.setBody(new Text(message));

        JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonCancel = new JFXButton("Exit");
        buttonCancel.setStyle("-fx-border-color:seagreen;-fx-border-width:1");

        buttonCancel.setOnAction(e -> dlog.close());

        content.setActions(buttonCancel);
        content.autosize();
        content.setPadding(new Insets(6, 0, 6, 0));
        content.setLayoutX(50);
        content.setLayoutY(50);

        dlog.show();

    }

    public void close() {

        Stage stage = (Stage) btn_save.getScene().getWindow();
        stage.close();

    }

    public static String getSname() {

        String sname = "";

        String query = "SELECT sname FROM NameSetter WHERE appid = 1";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                sname = rst.getString("sname");
            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return sname;
    }

    public static String getbox() {

        String sname = "";

        String query = "SELECT Box FROM NameSetter WHERE appid = 1";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                sname = rst.getString("Box");
            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return sname;
    }

    public static String getPlace() {

        String sname = "";

        String query = "SELECT place FROM NameSetter WHERE appid = 1";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                sname = rst.getString("place");
            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return sname;
    }

    public static String getEmail() {

        String sname = "";

        String query = "SELECT email FROM NameSetter WHERE appid = 1";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                sname = rst.getString("email");
            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return sname;
    }

    public static String getContacts() {

        String sname = "";

        String query = "SELECT contacts FROM NameSetter WHERE appid = 1";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                sname = rst.getString("contacts");
            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return sname;
    }

    public static String getRef() {

        String sname = "";

        String query = "SELECT ref FROM NameSetter WHERE appid = 1";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                sname = rst.getString("ref");
            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return sname;
    }

    public static String getAppname() {

        String sname = "";

        String query = "SELECT appname FROM NameSetter WHERE appid = 1";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                sname = rst.getString("appname");
            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

        return sname;
    }

    public  void createTable() {

        String query = "CREATE TABLE NameSetter ("
                + "    Sname    VARCHAR NOT NULL,"
                + "    Box      VARCHAR,"
                + "    place    VARCHAR NOT NULL,"
                + "    email    VARCHAR NOT NULL,"
                + "    contacts VARCHAR NOT NULL,"
                + "    ref      VARCHAR NOT NULL,"
                + "    appname  VARCHAR NOT NULL,"
                + "    Appid    INTEGER PRIMARY KEY AUTOINCREMENT"
                + "                     NOT NULL"
                + ");";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
            
            PopulateData();
            openDialog("Table Created..");

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        }

    }
    
    
    
    public static void PopulateData(){
    
        String query = "INSERT INTO NameSetter VALUES('Ericksofts.com.ke','erickerickmlz@gmail.com','Malindi-Kenya','erickerickmlz@gmail.com',"
                + "'0706293707/0706038555','erickerickmlz@gmail.com','ReportCreator',null)";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try{
            
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
        
        }catch(SQLException exc){
        
            
            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
        
        }
    
    
    }
}
