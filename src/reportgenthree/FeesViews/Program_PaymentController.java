/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Academics_AddNewScoreController;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Program_PaymentController implements Initializable {

    @FXML
    private ComboBox cmb_pname;

    @FXML
    private Label one;

    @FXML
    private TextField txt_receiptno;

    @FXML
    private Label two;

    @FXML
    private ComboBox cmb_mode;

    @FXML
    private Label three;

    @FXML
    private JFXDatePicker datepicker;

    @FXML
    private Label four;

    @FXML
    private Label txt_comment;

    @FXML
    private TextField txt_adm;

    @FXML
    private TextField txt_amount;

    @FXML
    private Label six;

    @FXML
    private JFXButton btn_payment;

    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        AppFuctions.CoursesClass.populateComboBoxClasses(cmb_pname, "Program_Name", "Programs");

        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll("Cash Payment", "Chaque Payment", "Fee Over Payment");
        cmb_mode.setItems(data);
        icon.setGlyphSize(15);
        icon.setGlyphStyle("-fx-fill:red");

        txt_adm.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                txt_adm.setText(oldValue);
            }
        });

        txt_amount.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                txt_amount.setText(oldValue);
            }
        });

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");
        LocalDate date = LocalDate.now();
        datepicker.setValue(date);

    }

    public void add_payment() {

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

        if (!cmb_pname.getSelectionModel().isEmpty()) {

            one.setGraphic(null);

            String pname = cmb_pname.getSelectionModel().getSelectedItem().toString();
            if (!txt_receiptno.getText().isEmpty()) {
                two.setGraphic(null);
                String receiptno = txt_receiptno.getText();

                if (!cmb_mode.getSelectionModel().isEmpty()) {
                    three.setGraphic(null);
                    String mode = cmb_mode.getSelectionModel().getSelectedItem().toString();

                    if (!txt_adm.getText().isEmpty()) {

                        String adm = txt_adm.getText();

                        if (!txt_amount.getText().isEmpty()) {
                            six.setGraphic(null);
                            String amount = txt_amount.getText();

                            String std_year = AppFuctions.CoursesClass.getStudentYear(adm);
                            String date = datepicker.getValue().format(fomat);

                            try {

                                if (StudentsClass.checkStudentTwo(adm)) {

                                    if (check_receipt(receiptno)) {

                                        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                        error_icon.setGlyphSize(150);
                                        error_icon.setGlyphStyle("-fx-fill:red");
                                        String title = pname + " Payment";
                                        String message = "Sorry The Receipt No. Is Invalid \n ( A payment with the same "
                                                + "receipt Number exist in our Records)";
                                        openDialog(title, message, error_icon);

                                    } else {

                                        String message = "Save Kshs. " + amount + " To " + adm + " Account For \n"
                                                + " " + pname + "  \n" + date + " \n " + std_year;
                                        confirm(pname, message);
                                    }

                                } else {

                                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                                    error_icon.setGlyphSize(150);
                                    error_icon.setGlyphStyle("-fx-fill:red");
                                    String title = pname + " Payment";
                                    String message = "Sorry The Student Is Not In Our Records";
                                    openDialog(title, message, error_icon);

                                }

                            } catch (Exception exc) {

                                System.out.println(" " + exc);
                            }

                        } else {

                            six.setGraphic(icon);

                        }
                    } else {

                        four.setGraphic(icon);

                    }

                } else {

                    three.setGraphic(icon);

                }

            } else {

                two.setGraphic(icon);

            }

        } else {

            one.setGraphic(icon);

        }

    }

    public boolean payment() {

        DateTimeFormatter fomat = DateTimeFormatter.ofPattern("dd/MMM/yyy");

        boolean result = false;

        if (!cmb_pname.getSelectionModel().isEmpty()) {

            one.setGraphic(null);

            String pname = cmb_pname.getSelectionModel().getSelectedItem().toString();
            if (!txt_receiptno.getText().isEmpty()) {
                two.setGraphic(null);
                String receiptno = txt_receiptno.getText();

                if (!cmb_mode.getSelectionModel().isEmpty()) {
                    three.setGraphic(null);
                    String mode = cmb_mode.getSelectionModel().getSelectedItem().toString();

                    if (!txt_adm.getText().isEmpty()) {

                        String adm = txt_adm.getText();

                        if (!txt_amount.getText().isEmpty()) {
                            six.setGraphic(null);
                            String amount = txt_amount.getText();

                            String std_year = AppFuctions.CoursesClass.getStudentYear(adm);
                            String date = datepicker.getValue().format(fomat);

                            String query = "INSERT INTO Programs_Payments VALUES ('" + pname + "',null,'" + receiptno + "','" + mode + "','" + adm + "',"
                                    + "'" + std_year + "','" + date + "','" + amount + "')";
                            Connection conn = sqlDataBaseConnection.sqliteconnect();
                            try {

                                Statement st = conn.createStatement();
                                int value = st.executeUpdate(query);

                                if (value >= 1) {

                                    result = true;
                                }

                                conn.close();

                            } catch (SQLException exc) {

                                System.out.println(" " + exc);
                            }

                        } else {

                            six.setGraphic(icon);

                        }
                    } else {

                        four.setGraphic(icon);

                    }

                } else {

                    three.setGraphic(icon);

                }

            } else {

                two.setGraphic(icon);

            }

        } else {

            one.setGraphic(icon);

        }

        return result;

    }

    public boolean confirm(String Programname, String message) {

        boolean result = false;

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dlog = new JFXDialog();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Program_PaymentController.class.getResource("FeeConfirmation.fxml"));
            StackPane pane = loader.load();
            FeeConfirmationController cc = loader.getController();
            cc.getLbl_infor().setText(message);
            cc.getLbl_title().setText(Programname + " Payment");

            cc.getBtn_save().setOnAction(e -> {

                boolean value = payment();

                if (value) {

                    dlog.close();
                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
                    error_icon.setGlyphSize(150);
                    error_icon.setGlyphStyle("-fx-fill:skyblue");
                    String title = Programname + " Payment";
                    String mymessage = "Great!!!! Payment Saved Successfully!";
                    openDialog(title, mymessage, error_icon);
                } else {

                    dlog.close();
                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    error_icon.setGlyphSize(150);
                    error_icon.setGlyphStyle("-fx-fill:red");
                    String title = Programname + " Payment";
                    String mymessage = "Sorry Could Not Save Payment \n Incase This case Persist, Call admin for assistance";
                    openDialog(title, message, error_icon);
                }

            });

            cc.getBtn_cancel().setOnAction(e -> {

                dlog.close();

            });

            content.setBody(pane);

            StackPane mypane = ReportGenThree.getMajor_stackpane();
            dlog.setTransitionType(JFXDialog.DialogTransition.BOTTOM);
            dlog.setContent(content);
            dlog.setDialogContainer(mypane);

            content.autosize();

            dlog.show();
        } catch (IOException exc) {

            System.out.println(" " + exc);
        }

        return result;

    }

    public boolean check_receipt(String property) {

        boolean result = false;
        String query = "SELECT * FROM Programs_Payments WHERE Receipt_No = '" + property + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            result = rst.next();

            conn.close();

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }
        return result;
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

            StackPane mypane = ReportGenThree.getMajor_stackpane();

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            content.autosize();

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Academics_AddNewScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void set_name() {

        if (StudentsClass.checkStudentTwo(txt_adm.getText())) {

            txt_comment.setText(StudentsClass.getStdName(txt_adm.getText()));

        } else {

            txt_comment.setText("***********Name ***********");
        }

    }

}
