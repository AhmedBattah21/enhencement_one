/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import AppFuctions.CoursesClass;
import AppFuctions.Functions;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Academics_AddNewScoreController;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Fees_CreateAccountController implements Initializable {

    @FXML
    private StackPane mypane;

    @FXML
    private TextField txt_regno;

    @FXML
    private ComboBox cmb_Accyear;

    @FXML
    private Label lbl_name;

    @FXML
    private Label lbl_course;

    @FXML
    private Label lbl_category;

    @FXML
    private JFXComboBox cmb_acctype;

    @FXML
    private Circle rect_image;

    @FXML
    private JFXButton btn_register;

    @FXML
    private JFXButton btn_Cancel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        CoursesClass.populateComboBoxClasses(cmb_acctype, "AccountName", "FeeAccountsTypes");

        ObservableList<String> study_year = FXCollections.observableArrayList();

        study_year.addAll("First Year", "Second Year");
        cmb_Accyear.setItems(study_year);

    }

    public void getStudent() {

        String regno = txt_regno.getText();

        String home = System.getProperty("user.home") + "/" + "Documents";

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

            if (rst.next()) {

                String name = rst.getString("StudentName");
                lbl_name.setText("** " + name + " **");

                cmb_Accyear.setValue(rst.getString("YearOfStudy"));
                lbl_course.setText(rst.getString("StudentCourse"));
                lbl_category.setText(rst.getString("StudentCategory"));

                String img_name = rst.getString("std_image");

                File imagepath = new File(home + "/ReportGenThree/students/" + img_name);
                Image im = new Image(imagepath.toURI().toString(), false);
                rect_image.setFill(new ImagePattern(im));

            }

            conn.close();

        } catch (SQLException exe) {

            System.out.println("" + exe);

        }

    }

    public void createAccount() {

        try {

            if (!txt_regno.getText().trim().isEmpty()) {

                if (!cmb_acctype.getSelectionModel().isEmpty()) {

                    if (!cmb_Accyear.getSelectionModel().isEmpty() && !txt_regno.getText().trim().isEmpty()) {

                        String acc_year = cmb_Accyear.getSelectionModel().getSelectedItem().toString();
                        String acc_user = txt_regno.getText();
                        String acctype = cmb_acctype.getSelectionModel().getSelectedItem().toString();

                        String grandtotal = getTermFee(acctype, "AccountTotals");

                        String t1fee = getTermFee(acctype, "AmountT1");
                        String t2fee = getTermFee(acctype, "AmountT2");
                        String t3fee = getTermFee(acctype, "AmountT3");

                        String query = "INSERT INTO Fee_Table VALUES (null,'" + acc_year + "','" + acc_user + "','" + acctype + "',0,'" + t1fee + "',0,'" + t2fee + "',0,'" + t3fee + "','" + grandtotal + "')";

                        Connection conn = sqlDataBaseConnection.sqliteconnect();

                        try {

                            Statement st = conn.createStatement();

                            if (!checkAccountUser(acc_user, acc_year)) {

                                if (acc_year.equals("Second Year")) {

                                    int gbalance = Integer.parseInt(getGrandBalance("First Year", acc_user));

                                    if (gbalance == 0 || gbalance < 0) {

                                        st.executeUpdate(query);
                                        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
                                        error_icon.setGlyphSize(150);
                                        error_icon.setGlyphStyle("-fx-fill:skyblue");
                                        String message = acc_user + " Account For " + acc_year + " Has Been Created Successfully!!";
                                        openDialog("Fee Accounts", message, error_icon);

                                    } else {

                                        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                        error_icon.setGlyphSize(150);
                                        error_icon.setGlyphStyle("-fx-fill:red");
                                        String message = "Failed To Create New Account, Student Has a Balance Of Kshs. " + gbalance + ".00 In The prev Acc";
                                        openDialog("Fee Accounts", message, error_icon);

                                    }
                                } else {

                                    st.executeUpdate(query);

                                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
                                    error_icon.setGlyphSize(150);
                                    error_icon.setGlyphStyle("-fx-fill:skyblue");
                                    String message = acc_user + " Account For " + acc_year + " Has Been Created Successfully!!";
                                    openDialog("Fee Accounts", message, error_icon);
                                }

                            } else {

                                FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                error_icon.setGlyphSize(150);
                                error_icon.setGlyphStyle("-fx-fill:red");
                                String message = "The Account Alread Exist";
                                openDialog("Fee Accounts", message, error_icon);

                            }

                            conn.close();

                        } catch (SQLException exc) {

                            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                                    "Sytem has encountered an error \n"
                                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

                        }

                    } else {

                        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        error_icon.setGlyphSize(150);
                        error_icon.setGlyphStyle("-fx-fill:red");
                        String message = "Select Acc Year..";
                        openDialog("Fee Accounts", message, error_icon);

                    }

                } else {

                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    error_icon.setGlyphSize(150);
                    error_icon.setGlyphStyle("-fx-fill:red");
                    String message = "Select Type Of Fee Account You Want To Create For This Student..";
                    openDialog("Fee Accounts", message, error_icon);

                }

            } else {

                FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                error_icon.setGlyphSize(150);
                error_icon.setGlyphStyle("-fx-fill:red");
                String message = "Enter Student Adm Number..";
                openDialog("Fee Accounts", message, error_icon);

            }

        } catch (NumberFormatException exc) {

            FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            error_icon.setGlyphSize(150);
            error_icon.setGlyphStyle("-fx-fill:red");
            String message = "Create First Year Account..";
            openDialog("Fee Accounts", message, error_icon);
        }

    }

    public static Boolean checkAccountUser(String accuser, String acc_year) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM Fee_Table WHERE Acc_user = '" + accuser + "' AND Acc_year = '" + acc_year + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return result;

    }

    public String getTermFee(String acc_type, String term) {

        String amount = "";

        String query = "SELECT * FROM FeeAccountsTypes WHERE AccountName = '" + acc_type + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amount = rst.getString(term);

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amount;

    }

    public String getGrandBalance(String acc_year, String acc_user) {

        String amount = "";

        String query = "SELECT Acc_Balance FROM Fee_Table WHERE Acc_year = '" + acc_year + "' AND Acc_user = '" + acc_user + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amount = rst.getString("Acc_Balance");

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amount;

    }

    public void openDialog(String title, String message, FontAwesomeIconView icon) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Academics_AddNewScoreController.class.getResource("Dialogs/Datasaved_dialog.fxml"));
            StackPane pane = loader.load();
            Datasaved_dialogController cc = loader.getController();
            cc.getLbl_infor().setText(message);
            cc.getLbl_icon().setGraphic(icon);
            cc.getLbl_head().setText(title);


            content.setBody(pane);

            StackPane major_mypane = ReportGenThree.getMajor_stackpane();

            JFXDialog dlog = new JFXDialog(major_mypane, content, JFXDialog.DialogTransition.TOP);

            content.autosize();

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Academics_AddNewScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
