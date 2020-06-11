/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import AppFuctions.Functions;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import reportgenthree.objects.FeeStatements_Object;
import AppFuctions.FeeSingleFeeStatemets;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Academics_AddNewScoreController;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Fees_FeeStatementsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane mypane;

    @FXML
    private AnchorPane pane;

    @FXML
    private VBox container;

    @FXML
    private HBox infor_container_three;

    @FXML
    private Circle rect_image;

    @FXML
    private Label lbl_fname;

    @FXML
    private Label lbl_course;

    @FXML
    private Label lbl_t1balance;

    @FXML
    private Label lbl_t2balance;

    @FXML
    private Label lbl_t3balance;

    @FXML
    private Label lbl_TotalBalance;

    @FXML
    private Label lbl_acctype;

    @FXML
    private TableView<FeeStatements_Object> tble_table;

    @FXML
    private TableColumn col_date;

    @FXML
    private TableColumn col_mode;

    @FXML
    private TableColumn col_receiptno;

    @FXML
    private TableColumn col_description;

    @FXML
    private TableColumn col_amount;

    @FXML
    private TableColumn col_balance;

    @FXML
    private TableColumn col_term;

    @FXML
    private HBox infor_container_four;

    @FXML
    private JFXButton btn_create;

    @FXML
    private Label lbl_count;

    @FXML
    private ComboBox cmb_year;

    @FXML
    private TextField txt_hint;

    @FXML
    private JFXButton btn_search;
    private String main_query;
    private String adm_number;

    String home = System.getProperty("user.home") + "/" + "Documents";
    final ObservableList<FeeStatements_Object> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        ObservableList<String> study_year = FXCollections.observableArrayList();

        study_year.addAll("First Year", "Second Year");
        cmb_year.setItems(study_year);

        col_date.setCellValueFactory(new PropertyValueFactory<>("State_date"));
        col_term.setCellValueFactory(new PropertyValueFactory<>("Term"));
        col_amount.setCellValueFactory(new PropertyValueFactory<>("State_amount"));
        col_balance.setCellValueFactory(new PropertyValueFactory<>("State_balance"));
        col_receiptno.setCellValueFactory(new PropertyValueFactory<>("State_receiptno"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("State_comment"));

        col_mode.setCellValueFactory(new PropertyValueFactory<>("State_paymode"));

        txt_hint.requestFocus();

        lbl_count.setText("");

    }

    public void funnyMethod() {

        data.clear();
        tble_table.getItems().clear();

        String hint = txt_hint.getText().trim();

        adm_number = hint;

        if (!cmb_year.getSelectionModel().isEmpty()) {

            String syear = cmb_year.getSelectionModel().getSelectedItem().toString();

            if (hint.equals("")) {

                FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                error_icon.setGlyphSize(150);
                error_icon.setGlyphStyle("-fx-fill:red");
                String message = "Enter Student Admission Number";

                openDialog("Fee Statements", message, error_icon);
            } else {

                Connection conn = sqlDataBaseConnection.sqliteconnect();

                hint = hint.trim();

                String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + hint + "'";
                String query_two = "SELECT * FROM Fee_statements WHERE State_user = '" + hint + "' AND Year = '" + syear + "'";

                main_query = "SELECT * FROM Fee_statements,Students_2017  WHERE State_user = '" + hint + "' AND Year = '" + syear + "' AND State_user = StudentRegCode ";

                try {

                    Statement st = conn.createStatement();
                    Statement st_two = conn.createStatement();

                    ResultSet rst = st.executeQuery(query);

                    int results = 0;
                    int result_two = 0;

                    if (rst.next()) {

                        File imagepath = new File(home + "/ReportGenThree/students/" + rst.getString("std_image"));
                        Image im = new Image(imagepath.toURI().toString(), false);
                        rect_image.setFill(new ImagePattern(im));

                        lbl_fname.setText("Full Name : " + rst.getString("StudentName"));
                        lbl_course.setText("Course:" + rst.getString("StudentCourse"));

                        String accname = getAccName(hint, syear);

                        lbl_acctype.setText("Acc Type : " + accname + " Kshs." + getTotalFees(accname, "AccountTotals") + " Per Year");

                        lbl_t1balance.setText("1)T I Balance Kshs." + getTermFeeBalance(hint, "Balance_t1", syear) + " Total  Kshs." + getTotalFees(accname, "AmountT1"));
                        lbl_t2balance.setText("2)T II Balance Kshs. " + getTermFeeBalance(hint, "Balance_t2", syear) + " Total  Kshs." + getTotalFees(accname, "AmountT2"));
                        lbl_t3balance.setText("3)T III Balance Kshs." + getTermFeeBalance(hint, "Balance_t3", syear) + " Total  Kshs." + getTotalFees(accname, "AmountT3"));

                        lbl_TotalBalance.setText("Total Balance For The Year Kshs. " + getTermFeeBalance(hint, "Acc_Balance", syear));

                        results++;

                        lbl_count.setText("Found " + results + " Result(s)");

                        ResultSet rst_two = st_two.executeQuery(query_two);

                        int totalpaid = 0;

                        String original_term = "Term One";
                        int semisterfee = getSemisterFee(accname, original_term);
                        int State_balance = semisterfee;

                        

                            while (rst_two.next()) {

                                String State_date = rst_two.getString("State_date");
                                String State_paymode = rst_two.getString("State_paymode");
                                String State_receiptno = rst_two.getString("State_receiptno");
                                String State_amount = rst_two.getString("State_amount");

                                String Term = rst_two.getString("Term");

                                String comment = rst_two.getString("Comment");

                                if (original_term.equals(Term)) {

                                    totalpaid = totalpaid + Integer.parseInt(State_amount);
                                    State_balance = semisterfee - totalpaid;
                                    

                                } else {
                                    
                                     data.add(new FeeStatements_Object("***", "***", 
                                             "***", "new term", "***", "***", "CLEARED"));

                                    totalpaid = 0;
                                    totalpaid = totalpaid + Integer.parseInt(State_amount);
                                    original_term = Term;
                                    semisterfee = getSemisterFee(accname, original_term);
                                    State_balance = semisterfee - totalpaid;

                                }

                                adm_number = hint;

                                result_two++;

                                data.add(new FeeStatements_Object(State_date, State_paymode, State_receiptno, "Kshs. " + State_amount + ".00", "Kshs. " + State_balance + ".00", Term, comment));
                            }

                            tble_table.setItems(FXCollections.observableArrayList(data));

                      if (result_two == 0) {

                            FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                            error_icon.setGlyphSize(150);
                            error_icon.setGlyphStyle("-fx-fill:red");
                            String message = "No Payments Statements Found For This Student,";

                            openDialog("Fee Statements", message, error_icon);

                        }

                    } else {

                        FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
                        error_icon.setGlyphSize(150);
                        error_icon.setGlyphStyle("-fx-fill:red");
                        String message = "Student Not Found..";

                        openDialog("Fee Statements", message, error_icon);
                    }

                    if (result_two == 0) {

                    }

                    conn.close();

                } catch (SQLException exc) {

                    System.out.println("" + exc);

                }

            }
        } else {
            FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            error_icon.setGlyphSize(150);
            error_icon.setGlyphStyle("-fx-fill:red");
            String message = "Enter Student Year Of Study i.e First Year or Second Year!!";

            openDialog("Fee Statements", message, error_icon);
        }

    }

    public void placeErrorBox() {

        VBox error = new VBox();
        error.setAlignment(Pos.CENTER);
        error.setMinHeight(100);
        error.setMinWidth(100);

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
        Label lbl_error = new Label("", icon);

        lbl_error.setId("lbl_error");

        error.getChildren().add(lbl_error);

        Label lbl_infor = new Label("Sorry No Examination Records \n For The Above Student");
        lbl_infor.setId("lbl_error");

        error.getChildren().add(lbl_infor);

        infor_container_three.getChildren().clear();
        infor_container_three.getChildren().add(error);

    }

    public static String getCcode(String regcode) {

        String query = "SELECT Ccode FROM students_2017 WHERE StudentRegCode = '" + regcode + "'";
        String ccode = "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            ccode = rst.getString("Ccode");
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return ccode;
    }

    public int getTermFeeBalance(String acc_name, String term, String acc_year) {

        int amount = 0;

        String query = "SELECT * FROM Fee_Table WHERE Acc_user = '" + acc_name + "' AND Acc_year = '" + acc_year + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amount = Integer.parseInt(rst.getString(term));

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amount;

    }

    public String getTotalFees(String Acc_name, String column) {

        String amout = "";

        String query = "SELECT * FROM FeeAccountsTypes WHERE AccountName = '" + Acc_name + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amout = rst.getString(column);

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amout;

    }

    public String getAccName(String Acc_user, String year) {

        String amout = "";

        String query = "SELECT Acc_type  FROM Fee_Table WHERE Acc_user = '" + Acc_user + "' AND Acc_year = '" + year + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amout = rst.getString("Acc_type");

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amout;

    }

    public void create() {

        reportgenthree.ReportGenThree.LoadDescriptionSettingsForClassListPdf();

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage primary = (Stage) btn_create.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        String path = file.getAbsolutePath();

        if (file != null) {

            FeeSingleFeeStatemets.creator(main_query, path, adm_number);

        }

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

}
