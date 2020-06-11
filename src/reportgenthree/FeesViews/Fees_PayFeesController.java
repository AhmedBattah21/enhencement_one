/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import AppFuctions.CoursesClass;
import AppFuctions.Functions;
import AppFuctions.StudentsClass;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Academics_AddNewScoreController;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

public class Fees_PayFeesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane mypane;

    @FXML
    private TextField txt_accname;

    @FXML
    private JFXCheckBox check_deduct;

    @FXML
    private Label txt_fname;

    @FXML
    private JFXTextField txt_t1fees1;

    @FXML
    private JFXTextField txt_acctype;

    @FXML
    private JFXTextField txt_ttfees;

    @FXML
    private JFXTextField txt_tbalance;

    @FXML
    private JFXTextField txt_t1balace;

    @FXML
    private JFXTextField txt_t2balace;

    @FXML
    private JFXTextField txt_t3balace;

    @FXML
    private JFXTextField txt_receiptno;

    @FXML
    private JFXComboBox cmb_paymode;

    @FXML
    private ComboBox cmb_term;

    @FXML
    private JFXDatePicker dp_date;

    @FXML
    private TextField txt_amount;

    @FXML
    private JFXButton btn_pay;

    @FXML
    private JFXButton btn_Cancel;

    @FXML
    private Label lbl_stdyear;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ObservableList<String> study_year = FXCollections.observableArrayList();
        ObservableList<String> paymode = FXCollections.observableArrayList();

        paymode.addAll("By Cash", "By Check", "Bank Slip", "Over Payment");
        cmb_paymode.setItems(paymode);

        txt_accname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                txt_accname.setText(oldValue);
            }
        });

        txt_amount.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                txt_amount.setText(oldValue);
            }
        });
        CoursesClass.populateComboBoxClasses(cmb_term, "SemisterName", "Semisters");
        cmb_term.setValue(getSemister());

        cmb_paymode.setOnAction(e -> {

            String selected = cmb_paymode.getSelectionModel().getSelectedItem().toString();
            if (selected.equals("Over Payment")) {

                txt_amount.setText("(***)");
                txt_amount.setDisable(true);

            } else {

                txt_amount.setDisable(false);
                txt_amount.setText("");
            }
        });

        dp_date.setValue(LocalDate.now());

    }

    public String getSemister() {

        LocalDateTime date = LocalDateTime.now();
        int month = date.getMonthValue();
        String query = "SELECT SemisterName FROM Semisters WHERE Start >= " + month + " AND End <= '" + month + "'";

        String semister = "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {

                semister = rst.getString("SemisterName");

            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println(exc);
        }

        return semister;
    }

    public void getStudent() {

        if (!txt_accname.getText().isEmpty()) {

            String user = txt_accname.getText();

            if (StudentsClass.checkStudentTwo(user)) {

                if (CheckIfHasAccount(user)) {

                    String acc_year = AppFuctions.CoursesClass.getStudentYear(user);

                    txt_fname.setText(StudentsClass.getStname(user));

                    String acc_type = getStudentAccountType(user, acc_year);

                    lbl_stdyear.setText("** " + acc_year.toUpperCase() + " **");

                    int balance_1 = getTermFeeBalance(user, "Term One", acc_year);
                    int balance_2 = getTermFeeBalance(user, "Term Two", acc_year);
                    int balance_3 = getTermFeeBalance(user, "Term Three", acc_year);

                    txt_t1balace.setText("Kshs." + balance_1 + ".00");
                    txt_t2balace.setText("Kshs." + balance_2 + ".00");
                    txt_t3balace.setText("Kshs." + balance_3 + ".00");

                    txt_acctype.setText(acc_type);
                    txt_tbalance.setText("" + getTermTotalBalance(user, "Acc_Balance", acc_year));
                    txt_ttfees.setText(getTotalFees(acc_type));

                } else {

                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    error_icon.setGlyphSize(150);
                    error_icon.setGlyphStyle("-fx-fill:red");
                    String title = "School Fee Payment";
                    String mymessage = "Dear User, Create an Acc. For This Student First";

                    openDialog(title, mymessage, error_icon);

                }

            } else {

                txt_fname.setText("*****Student Not Found In Our Database*******");

            }

        } else {

            txt_fname.setText("*****Student Not Found In Our Database*******");

        }

    }

    public String getTotalFees(String Acc_name) {

        String amout = "";

        String query = "SELECT * FROM FeeAccountsTypes WHERE AccountName = '" + Acc_name + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amout = rst.getString("AccountTotals");

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amout;

    }

    public String getStudentAccountType(String Acc_user, String acc_year) {

        String acc_type = "";

        String query = "SELECT Acc_type FROM Fee_Table WHERE Acc_user = '" + Acc_user + "'  AND Acc_year = '" + acc_year + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                acc_type = rst.getString("Acc_type");

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return acc_type;

    }

    public Boolean CheckIfHasAccount(String Acc_user) {

        boolean hasAccount = false;

        String query = "SELECT * FROM Fee_Table WHERE Acc_user = '" + Acc_user + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            hasAccount = rst.next();

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return hasAccount;

    }

    public int getSemisterFee(String Acc_name, String term) {

        int amout = 0;

        String amount_t = "";

        if (term.equals("Term One")) {

            amount_t = "AmountT1";

        } else if (term.equals("Term Two")) {

            amount_t = "AmountT2";

        } else if (term.equals("Term Three")) {

            amount_t = "AmountT3";

        }

        String query = "SELECT  " + amount_t + "  FROM FeeAccountsTypes WHERE AccountName = '" + Acc_name + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amout = rst.getInt(amount_t);

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amout;

    }

    public void pay() {

        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        error_icon.setGlyphSize(150);
        error_icon.setGlyphStyle("-fx-fill:red");

        FontAwesomeIconView infor_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
        infor_icon.setGlyphSize(150);
        infor_icon.setGlyphStyle("-fx-fill:skyblue");
        String title = "School Fee Payment";

        try {

            if (!txt_accname.getText().isEmpty()) {

                String acc_user = txt_accname.getText();

                if (!txt_amount.getText().isEmpty()) {

                    int pay_amount = Integer.parseInt(txt_amount.getText());

                    if (!txt_receiptno.getText().isEmpty()) {

                        if (!cmb_paymode.getSelectionModel().isEmpty()) {

                            if (dp_date.getValue() != null) {

                                if (!checkReceipt(txt_receiptno.getText())) {

                                    String message = "You Are About To Add Kshs.  " + pay_amount + ".00 To  " + acc_user + " Fee Account \n "
                                            + "Payment Description (School Fees Program) \nClick Ok To Continue Otherwise to Cancel";

                                    confirm("Student Fee Payment", message, pay_amount);

                                } else {

                                    String mymessage = "Dear User, An Existing Receipt Number Alread Exits In Our Records "
                                            + "\n(Receipt Number Is Invalid \n Call Admin For any assistance)";

                                    openDialog(title, mymessage, error_icon);
                                }

                            } else {

                                String mymessage = "Dear User All The Fields Must Not Be Empty "
                                        + "\n Enter The Date or Set Your System To Pick Today's Date Automatically";

                                dp_date.requestFocus();

                                openDialog(title, mymessage, error_icon);
                            }

                        } else {

                            String mymessage = "Dear User All The Fields Must Not Be Empty "
                                    + "\n Enter The Mode Of Payment Cash,Chaque etc";

                            cmb_paymode.requestFocus();

                            openDialog(title, mymessage, error_icon);
                        }

                    } else {

                        String mymessage = "Dear User All The Fields Must Not Be Empty "
                                + "\n Enter Valid Receipt Number";

                        txt_receiptno.requestFocus();

                        openDialog(title, mymessage, error_icon);
                    }

                } else {

                    String mymessage = "Dear User All The Fields Must Not Be Empty "
                            + "\n Enter Amount In Kshs. To Save To The Student Account";

                    txt_amount.requestFocus();

                    openDialog(title, mymessage, error_icon);

                }

            } else {

                String mymessage = "Dear User All The Fields Must Not Be Empty "
                        + "\n Enter Valid Studet Registration Number!!";

                txt_accname.requestFocus();

                openDialog(title, mymessage, error_icon);
            }

        } catch (NumberFormatException exc) {

            String mymessage = "Dear User All The Fields Must Not Be Empty "
                    + "\n Invalid Information Detected!!";

            openDialog(title, mymessage, error_icon);

        }

    }

    public Boolean payFees(int pay_amount) {

        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        error_icon.setGlyphSize(150);
        error_icon.setGlyphStyle("-fx-fill:red");

        FontAwesomeIconView infor_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
        infor_icon.setGlyphSize(150);
        infor_icon.setGlyphStyle("-fx-fill:skyblue");
        String title = "School Fee Payment";

        boolean main_result = false;

        String acc_user = txt_accname.getText();
        String acc_year = AppFuctions.CoursesClass.getStudentYear(acc_user);

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd-MMM-yyy");

        String date = f.format(dp_date.getValue());

        String receiptno = txt_receiptno.getText();

        String paymode = cmb_paymode.getSelectionModel().getSelectedItem().toString();

        String term = cmb_term.getSelectionModel().getSelectedItem().toString();

        String comment = "School Fees Payment";

        int balance = getTermFeeBalance(acc_user, term, acc_year);
        int totalpaid = TotalAmountPaid(acc_user, term, acc_year);

        int grandbalance = getTermTotalBalance(acc_user, "Acc_Balance", acc_year);

        String acc_type = getStudentAccountType(acc_user, acc_year);

        int current_amount = totalpaid + pay_amount;

        int term_balance = getSemisterFee(acc_type, term) - current_amount;

        int total_termbalance = grandbalance - pay_amount;

        String cbalances = term + " Balance " + term_balance;

        if (term.equals("Term One")) {

            int prev_balance = checkPreviousBalances("Balance_t1", acc_user, acc_year);

            if (acc_year.equals("Second Year")) {

                prev_balance = checkPreviousBalances("Balance_t3", acc_user, "First Year");

                if (prev_balance == 0 && !paymode.equals("Over Payment")) {

                    main_result = updateFee(Integer.toString(current_amount), term, Integer.toString(term_balance), acc_user, acc_year,
                            Integer.toString(total_termbalance));
                    dailyUpdates(acc_user, cbalances, "" + pay_amount, "Success.. ");

                    addFeeStatement(acc_user, date, paymode, receiptno, Integer.toString(pay_amount), term, acc_year, comment);

                } else if (prev_balance < 0 && !paymode.equals("Over Payment")) {

                    String message = "Dear User, The Specified Student Has An Over payment Of Kshs." + prev_balance + ".00 \n"
                            + "Get Ride of this Over payment Before Making a new Fee Payment!! (hint) User 'Over Pay' Payment Mode";

                    openDialog("School Fees Payment", message, error_icon);

                } else if (prev_balance < 0 && paymode.equals("Over Payment")) {

                    int over_pay = prev_balance * -1;

                    totalpaid = TotalAmountPaid(acc_user, "Term One", "Second Year");
                    current_amount = totalpaid + over_pay;
                    term_balance = getSemisterFee(acc_type, "Term One") - current_amount;
                    total_termbalance = grandbalance - current_amount;

                    main_result = updateFee(Integer.toString(current_amount), term, Integer.toString(term_balance), acc_user, acc_year,
                            Integer.toString(total_termbalance));
                    dailyUpdates(acc_user, cbalances, "Over Payment " + over_pay, "Success.. ");

                    addFeeStatement(acc_user, date, paymode, "**Over Pay**", Integer.toString(over_pay), term, acc_year, comment);

                    update_onOverPay("Balance_t3", acc_user, "First Year");
                    update_onOverPay("Acc_Balance", acc_user, "First Year");

                } else if (prev_balance > 0) {

                    String message = "Dear User, The Specified Student Has a Balance Of Kshs." + prev_balance + ".00 \n"
                            + "Get Ride of this Balance Before Making a new Fee Payment To This Term!! ";

                    openDialog("School Fees Payment", message, error_icon);

                } else {

                    String message = "Dear User, We cant See Such Type Of Payment Process ";

                    openDialog("School Fees Payment", message, error_icon);

                }

            } else {

                if (prev_balance > 0 || prev_balance < 0) {

                    main_result = updateFee(Integer.toString(current_amount), term, Integer.toString(term_balance), acc_user, acc_year,
                            Integer.toString(total_termbalance));
                    dailyUpdates(acc_user, cbalances, "" + pay_amount, "Success.. ");

                    addFeeStatement(acc_user, date, paymode, receiptno, Integer.toString(pay_amount), term, acc_year, comment);

                } else {

                    String message = "Dear User,  We cant Make Such Transactions Due To The Following \n"
                            + "There Is a nill balance in This Term, we think you chose a wrong term";

                    openDialog("School Fees Payment", message, error_icon);

                }

            }

        } else if (term.equals("Term Two")) {

            int prev_balance = checkPreviousBalances("Balance_t1", acc_user, acc_year);
            //-----------------------------------------

            if (prev_balance == 0) {

                prev_balance = checkPreviousBalances("Balance_t2", acc_user, acc_year);

                if (!paymode.equals("Over Payment")) {

                    if (prev_balance > 0 || prev_balance < 0) {

                        main_result = updateFee(Integer.toString(current_amount), term, Integer.toString(term_balance), acc_user, acc_year,
                                Integer.toString(total_termbalance));
                        dailyUpdates(acc_user, cbalances, "" + pay_amount, "Success.. ");

                        addFeeStatement(acc_user, date, paymode, receiptno, Integer.toString(pay_amount), term, acc_year, comment);

                    } else {

                        String message = "Dear User,  We cant Make Such Transactions Due To The Following \n"
                                + "There Is a nill balance in This Term, we think you chose a wrong term";

                        openDialog("School Fees Payment", message, error_icon);

                    }

                } else {

                    String message = "Dear User,  We cant Make Such Transactions Due To The Following \n"
                            + "There is NO Excess Fee in the prev. Term, we think you chose a wrong term";

                    openDialog("School Fees Payment", message, error_icon);

                }

            } else if (prev_balance < 0 && !paymode.equals("Over Payment")) {

                String message = "Dear User, The Specified Student Has An Over payment Of Kshs." + prev_balance + ".00 \n"
                        + "Get Ride of this Over payment Before Making a new Fee Payment!! (hint) User 'Over Pay' Payment Mode";

                openDialog("School Fees Payment", message, error_icon);

            } else if (prev_balance < 0 && paymode.equals("Over Payment")) {

                int over_pay = prev_balance * -1;
                current_amount = totalpaid + over_pay;
                term_balance = getSemisterFee(acc_type, "Term Two") - current_amount;
                total_termbalance = grandbalance;

                main_result = updateFee(Integer.toString(current_amount), term, Integer.toString(term_balance), acc_user, acc_year,
                        Integer.toString(total_termbalance));
                dailyUpdates(acc_user, cbalances, "Over Payment " + over_pay, "Success.. ");

                addFeeStatement(acc_user, date, paymode, "**Over Pay**", Integer.toString(over_pay), term, acc_year, comment);

                update_onOverPay("Balance_t1", acc_user, acc_year);

            } else if (prev_balance > 0) {

                String message = "Dear User, The Specified Student Has a Balance Of Kshs." + prev_balance + ".00 \n"
                        + "Get Ride of this Balance Before Making a new Fee Payment To This Term!! ";

                openDialog("School Fees Payment", message, error_icon);

            }

            //------------------------------------------------------------------------------
        } else {

            int prev_balance = checkPreviousBalances("Balance_t2", acc_user, acc_year);

            if (prev_balance == 0) {

                if (!paymode.equals("Over Payment")) {

                    prev_balance = checkPreviousBalances("Balance_t3", acc_user, acc_year);

                    if (prev_balance > 0 || prev_balance < 0) {

                        main_result = updateFee(Integer.toString(current_amount), term, Integer.toString(term_balance), acc_user, acc_year,
                                Integer.toString(total_termbalance));
                        dailyUpdates(acc_user, cbalances, "" + pay_amount, "Success.. ");

                        addFeeStatement(acc_user, date, paymode, receiptno, Integer.toString(pay_amount), term, acc_year, comment);

                    } else {

                        String message = "Dear User,  We cant Make Such Transactions Due To The Following \n"
                                + "There Is a nill balance in This Term, we think you chose a wrong term \nIf This is the final term For First Year"
                                + "Then Create a second Year account for this payment..";

                        openDialog("School Fees Payment", message, error_icon);

                    }

                } else {

                    String message = "Dear User,  We cant Make Such Transactions Due To The Following \n"
                            + "There is NO Excess Fee in the prev. Term, we think you chose a wrong term";

                    openDialog("School Fees Payment", message, error_icon);
                }

            } else if (prev_balance < 0 && !paymode.equals("Over Payment")) {

                String message = "Dear User, The Specified Student Has An Over payment Of Kshs." + prev_balance + ".00 \n"
                        + "Get Ride of this Over payment Before Making a new Fee Payment!! (hint) User 'Over Pay' Payment Mode";

                openDialog("School Fees Payment", message, error_icon);

            } else if (prev_balance < 0 && paymode.equals("Over Payment")) {

                int over_pay = prev_balance * -1;
                current_amount = totalpaid + over_pay;
                term_balance = getSemisterFee(acc_type, "Term Three") - current_amount;
                total_termbalance = grandbalance;

                main_result = updateFee(Integer.toString(current_amount), term, Integer.toString(term_balance), acc_user, acc_year,
                        Integer.toString(total_termbalance));
                dailyUpdates(acc_user, cbalances, "Over Payment " + over_pay, "Success.. ");

                addFeeStatement(acc_user, date, paymode, "**Over Pay**", Integer.toString(over_pay), term, acc_year, comment);

                update_onOverPay("Balance_t2", acc_user, acc_year);

            } else if (prev_balance > 0) {

                String message = "Dear User, The Specified Student Has a Balance Of Kshs." + prev_balance + ".00 \n"
                        + "Get Ride of this Balance Before Making a new Fee Payment To This Term!! ";

                openDialog("School Fees Payment", message, error_icon);

            }

        }

        return main_result;

    }

    public void update_onOverPay(String balance_t, String acc_user, String acc_year) {

        String query = "UPDATE Fee_Table SET " + balance_t + " = 0 WHERE Acc_user = '" + acc_user + "' AND Acc_year = '" + acc_year + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public int getTermFeeBalance(String acc_name, String term, String acc_year) {

        int amount = 0;
        String amount_t = "";
        String balance_t = "";

        if (term.equals("Term One")) {

            amount_t = "Amount_t1";
            balance_t = "Balance_t1";

        } else if (term.equals("Term Two")) {

            amount_t = "Amount_t2";
            balance_t = "Balance_t2";

        } else if (term.equals("Term Three")) {

            amount_t = "Amount_t3";
            balance_t = "Balance_t3";

        }

        String query = "SELECT * FROM Fee_Table WHERE Acc_user = '" + acc_name + "' AND Acc_year = '" + acc_year + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amount = Integer.parseInt(rst.getString(balance_t));

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amount;

    }

    public int getTermTotalBalance(String acc_name, String term, String acc_year) {

        int amount = 0;

        String query = "SELECT Acc_Balance FROM Fee_Table WHERE Acc_user = '" + acc_name + "' AND Acc_year = '" + acc_year + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amount = Integer.parseInt(rst.getString("Acc_Balance"));

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amount;

    }

    public int TotalAmountPaid(String acc_name, String Term, String acc_year) {

        int amount = 0;

        String query = "SELECT SUM(State_amount) As amount FROM Fee_statements WHERE State_user = '" + acc_name + "' AND Year = '" + acc_year + "' AND Term = '" + Term + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amount = rst.getInt("amount");

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amount;

    }

    public Boolean updateFee(String amount, String term, String balance, String acc_user, String year, String grandbalance) {

        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
        error_icon.setGlyphSize(150);
        error_icon.setGlyphStyle("-fx-fill:red");

        FontAwesomeIconView infor_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
        infor_icon.setGlyphSize(150);
        infor_icon.setGlyphStyle("-fx-fill:skyblue");
        String title = "School Fee Payment";

        String amount_t = "";
        String balance_t = "";

        if (term.equals("Term One")) {

            amount_t = "Amount_t1";
            balance_t = "Balance_t1";

        } else if (term.equals("Term Two")) {

            amount_t = "Amount_t2";
            balance_t = "Balance_t2";

        } else if (term.equals("Term Three")) {

            amount_t = "Amount_t3";
            balance_t = "Balance_t3";

        }

        boolean result = false;

        String query = "UPDATE Fee_Table SET  " + amount_t + " = '" + amount + "' , " + balance_t + " = '" + balance + "',"
                + "Acc_Balance = '" + grandbalance + "'  WHERE Acc_user = '" + acc_user + "' AND Acc_year = '" + year + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            int value = st.executeUpdate(query);

            if (value >= 1) {

                result = true;
            }

            conn.close();

            getStudent();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return result;

    }

    public void dailyUpdates(String user, String cbalances, String amount, String comment) {

        String query = "INSERT INTO DailyFeeInput VALUES ('" + user + "','" + cbalances + "','" + amount + "','" + comment + "')";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public Boolean addFeeStatement(String State_user, String State_date, String State_paymode, String State_receiptno, String State_amount,
            String Term, String year, String comment) {

        String query = "INSERT INTO Fee_statements VALUES (null,'" + State_user + "','" + State_date + "','" + State_paymode + "',"
                + "'" + State_receiptno + "','" + State_amount + "','" + Term + "','" + year + "','" + comment + "')";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        boolean result = false;

        try {

            Statement st = conn.createStatement();
            int value = st.executeUpdate(query);

            if (value >= 1) {

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

    public boolean checkReceipt(String receiptno) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM Fee_statements WHERE State_receiptno = '" + receiptno + "' ";

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

    public int checkPreviousBalances(String balance_t, String user, String acc_year) {

        int balance = 0;
        String stdname = "";

        String query = "SELECT " + balance_t + " FROM Fee_Table WHERE Acc_year = '" + acc_year + "' AND Acc_user = '" + user + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                balance = rst.getInt(balance_t);

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return balance;

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

    public boolean confirm(String Programname, String message, int Amount) {

        boolean result = false;

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dlog = new JFXDialog();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Program_PaymentController.class.getResource("FeeConfirmation.fxml"));
            StackPane pane = loader.load();
            FeeConfirmationController cc = loader.getController();
            cc.getLbl_infor().setText(message);
            cc.getLbl_title().setText(Programname);

            cc.getBtn_save().setOnAction(e -> {

                boolean value = payFees(Amount);

                if (value) {

                    dlog.close();
                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
                    error_icon.setGlyphSize(150);
                    error_icon.setGlyphStyle("-fx-fill:skyblue");
                    String title = Programname;
                    String mymessage = "Great!!!! Kshs." + Amount + " Has Been Save Successifully!!!!";
                    openDialog(title, mymessage, error_icon);

                } else {

                    dlog.close();
                    FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                    error_icon.setGlyphSize(150);
                    error_icon.setGlyphStyle("-fx-fill:red");
                    String title = Programname;
                    String mymessage = "Sorry Could Not Save Payment \n Incase This case Persist, Call admin for assistance";
                    openDialog(title, mymessage, error_icon);
                }

            });

            cc.getBtn_cancel().setOnAction(e -> {

                dlog.close();

                FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                error_icon.setGlyphSize(150);
                error_icon.setGlyphStyle("-fx-fill:red");
                String title = Programname;
                String mymessage = "Payment Cancelled..";
                openDialog(title, mymessage, error_icon);

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

}
