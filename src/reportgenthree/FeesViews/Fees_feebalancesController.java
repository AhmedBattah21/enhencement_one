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
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;
import reportgenthree.objects.FeeBalances_object;
import AppFuctions.FeeBalancesPdfCreator;
import AppFuctions.StudentsClass;
import com.jfoenix.controls.JFXCheckBox;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Fees_feebalancesController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane mypane;

    @FXML
    private BorderPane pane;

    @FXML
    private TableView table;

    @FXML
    private TableColumn number;

    @FXML
    private TableColumn name;

    @FXML
    private TableColumn admnumber;

    @FXML
    private TableColumn col_coursename;

    @FXML
    private TableColumn col_amountpaid;

    @FXML
    private TableColumn col_totalfee;

    @FXML
    private TableColumn feebalance;

    @FXML
    private TableColumn stream;

    @FXML
    private ComboBox cmb_year;

    @FXML
    private ComboBox cmb_group;

    @FXML
    private ComboBox cmb_term;

    @FXML
    private JFXButton btn_get;

    @FXML
    private JFXTextField hint;

    @FXML
    private JFXCheckBox check_allbalances;

    @FXML
    private JFXButton pdfDoc;

    private String query = "";
    private String termone = "";

    private final static int rowsPerPage = 50;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        name.setCellValueFactory(new PropertyValueFactory<>("stname"));
        admnumber.setCellValueFactory(new PropertyValueFactory<>("admnumber"));
        stream.setCellValueFactory(new PropertyValueFactory<>("classname"));
        number.setCellValueFactory(new PropertyValueFactory<>("Count"));
        feebalance.setCellValueFactory(new PropertyValueFactory<>("feebalance"));
        col_coursename.setCellValueFactory(new PropertyValueFactory<>("coursename"));
        col_totalfee.setCellValueFactory(new PropertyValueFactory<>("totalfee"));
        col_amountpaid.setCellValueFactory(new PropertyValueFactory<>("amount_paid"));

        ObservableList<String> study_year = FXCollections.observableArrayList();

        study_year.addAll("First Year", "Second Year");
        cmb_year.setItems(study_year);

        CoursesClass.populateComboBoxClasses(cmb_group, "CourseName", "CourseTable");
        cmb_group.getItems().add("All Available Groups");

        CoursesClass.populateComboBoxClasses(cmb_term, "SemisterName", "Semisters");

        //search(); //this rows to the data hence ready for creating a pegination that has data
        Pagination pagination = new Pagination((data.size() / rowsPerPage + 1), 0);
        pagination.getStylesheets().add("reportgenthree/AppCss/peginationStyle.css");

        pagination.setPageFactory(this::createPage);

        pane.setCenter(pagination);

    }

    final ObservableList<FeeBalances_object> data = FXCollections.observableArrayList();

    private Node createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

        return new BorderPane(table);
    }

    public void search() {

        if (!cmb_term.getSelectionModel().isEmpty()) {

            if (!cmb_group.getSelectionModel().isEmpty()) {

                if (!cmb_year.getSelectionModel().isEmpty()) {

                    data.clear();//Before searching clear the data to remove available items

                    String searchHint = hint.getText();

                    String term = cmb_term.getSelectionModel().getSelectedItem().toString();
                    int total_fee = 0;
                    String coursename = "";
                    String amount_t = "";

                    if (term.equals("Term One")) {

                        termone = "Balance_t1";
                        amount_t = "Amount_t1";

                    } else if (term.equals("Term Two")) {

                        termone = "Balance_t2";
                        amount_t = "Amount_t2";

                    } else if (term.equals("Term Three")) {

                        termone = "Balance_t3";
                        amount_t = "Amount_t3";

                    }

                    String acc_year = cmb_year.getSelectionModel().getSelectedItem().toString();
                    String group = cmb_group.getSelectionModel().getSelectedItem().toString();

                    query = "SELECT * FROM Students_2017,Fee_Table WHERE StudentRegCode LIKE '%" + searchHint + "%' AND StudentRegCode = Acc_user AND "
                            + "Acc_year = '" + acc_year + "' AND StudentCourse = '" + group + "'";

                    Connection conn = sqlDataBaseConnection.sqliteconnect();
                    int count = 0;

                    try {

                        Statement st = conn.createStatement();

                        ResultSet rst = st.executeQuery(query);

                        int totalbalance = 0;

                        while (rst.next()) {

                            count++;

                            String std_year = rst.getString("Acc_year");
                            String admnumber = rst.getString("StudentRegCode");
                            String ccode = rst.getString("Ccode");
                            String stname = rst.getString("StudentName");
                            String feebalance = rst.getString(termone);
                            String std_acctype = getStudentAccountType(admnumber, acc_year);
                            total_fee = getSemisterFee(std_acctype, term);

                            coursename = StudentsClass.getStdCourse(admnumber);

                            totalbalance = totalbalance + Integer.parseInt(feebalance);

                            int amount_paid = AmountPaid(amount_t, admnumber, acc_year);

                            data.add(new FeeBalances_object(stname, std_year, ccode + "/" + admnumber, count, "Kshs. " + feebalance + ".00",
                                    coursename, "Kshs." + total_fee + ".00", "Kshs." + amount_paid + ".00"));

                        }

                        data.add(new FeeBalances_object("**", "**", "**", count + 1, "**", "**", "Total Balance", "Kshs." + totalbalance + ".00"));

                        conn.close();

                        Pagination pagination = new Pagination((data.size() / rowsPerPage + 1), 0);
                        pagination.getStylesheets().add("reportgenthree/AppCss/peginationStyle.css");
                        pagination.setPageFactory(this::createPage);

                        pane.setCenter(pagination);

                    } catch (SQLException exc) {

                        Functions.showAlert(Alert.AlertType.ERROR, "ERMS", "Action Failed",
                                "Sytem has encountered an error \n"
                                + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

                    }

                } else {

                    openDialog("Select Year Of Study");

                }

            } else {

                openDialog("Choose Group e.g Building Technology");
            }
        } else {

            openDialog("Choose Term e.g Term One");
        }

    }

    public void getData() {

        if (!cmb_term.getSelectionModel().isEmpty()) {

            if (!cmb_group.getSelectionModel().isEmpty()) {

                if (!cmb_year.getSelectionModel().isEmpty()) {

                    data.clear();//Before searching clear the data to remove available items

                    String searchHint = hint.getText();

                    String term = cmb_term.getSelectionModel().getSelectedItem().toString();

                    String amount_t = "";

                    if (term.equals("Term One")) {

                        termone = "Balance_t1";
                        amount_t = "Amount_t1";

                    } else if (term.equals("Term Two")) {

                        termone = "Balance_t2";
                        amount_t = "Amount_t2";

                    } else if (term.equals("Term Three")) {

                        termone = "Balance_t3";
                        amount_t = "Amount_t3";

                    }

                    String acc_year = cmb_year.getSelectionModel().getSelectedItem().toString();
                    String group = cmb_group.getSelectionModel().getSelectedItem().toString();

                    if (group.equals("All Available Groups")) {

                        query = "SELECT * FROM Students_2017,Fee_Table WHERE   StudentRegCode = Acc_user AND "
                                + "Acc_year = '" + acc_year + "' ";

                    } else {

                        query = "SELECT * FROM Students_2017,Fee_Table WHERE   StudentRegCode = Acc_user AND "
                                + "Acc_year = '" + acc_year + "' AND StudentCourse = '" + group + "'";

                    }

                    Connection conn = sqlDataBaseConnection.sqliteconnect();
                    int count = 0;

                    try {

                        Statement st = conn.createStatement();

                        ResultSet rst = st.executeQuery(query);

                        int totalbalance = 0;
                        String course = "";

                        while (rst.next()) {

                            String course_name = rst.getString("YearOfStudy");
                            String admnumber = rst.getString("StudentRegCode");
                            String ccode = rst.getString("Ccode");
                            String stname = rst.getString("StudentName");
                            int feebalance = rst.getInt(termone);

                            String std_acctype = getStudentAccountType(admnumber, acc_year);
                            int total_fee = getSemisterFee(std_acctype, term);

                            String coursename = StudentsClass.getStdCourse(admnumber);

                            if (feebalance > 0) {

                                totalbalance = totalbalance + feebalance;
                                int amount_paid = AmountPaid(amount_t, admnumber, acc_year);

                                if (check_allbalances.isSelected()) {

                                    if (term.equals("Term Two")) {

                                        feebalance = feebalance + rst.getInt("Balance_t1");

                                    } else if (term.equals("Term Three")) {

                                        feebalance = feebalance + rst.getInt("Balance_t1");
                                        feebalance = feebalance + rst.getInt("Balance_t2");
                                    }

                                }

                                count++;

                                data.add(new FeeBalances_object(stname, course_name, ccode + "/" + admnumber, count, "Kshs. " + feebalance + ".00",
                                        coursename, "Kshs." + total_fee + ".00", "Kshs." + amount_paid + ".00"));

                            }
                        }

                        if (totalbalance > 0) {

                            data.add(new FeeBalances_object("**", "**", "**", count + 1, "Kshs." + totalbalance + ".00", "**", "**", "Total Balance"));

                        }

                        conn.close();

                        //create the pegination and add it in the center pane
                        Pagination pagination = new Pagination((data.size() / rowsPerPage + 1), 0);
                        pagination.getStylesheets().add("reportgenthree/AppCss/peginationStyle.css");
                        pagination.setPageFactory(this::createPage);

                        pane.setCenter(pagination);

                    } catch (SQLException exc) {

                        Functions.showAlert(Alert.AlertType.ERROR, "ERMS", "Action Failed",
                                "Sytem has encountered an error \n"
                                + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

                    }

                } else {

                    openDialog("Select Year Of Study");

                }

            } else {

                openDialog("Choose Group e.g Building Technology");
            }
        } else {

            openDialog("Choose Term e.g Term One");
        }

    }

    public void createPdfDocument() {

        try {
            String path = OpenSaveFileChooser();

            ReportGenThree.LoadDescriptionSettingsForClassListPdf();

            FeeBalancesPdfCreator.creator(query, path, termone);

        } catch (Exception exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "E.R.M.S", "Exception Found", "Erck Error " + exc);

        }

    }

    public String OpenSaveFileChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage primary = (Stage) pdfDoc.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        String path = file.getAbsolutePath();
        return path;

    }

    public void openDialog(String message) {

        JFXDialogLayout content = new JFXDialogLayout();
        Label mylabel = new Label("Students Registration");

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

    public int AmountPaid(String amount_t, String user, String acc_year) {

        int balance = 0;
        String stdname = "";

        String query = "SELECT " + amount_t + " FROM Fee_Table WHERE Acc_year = '" + acc_year + "' AND Acc_user = '" + user + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                balance = rst.getInt(amount_t);

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return balance;

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
