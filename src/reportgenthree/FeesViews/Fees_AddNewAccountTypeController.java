/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import AppFuctions.Functions;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import reportgenthree.objects.NeFeeAccount_object;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Fees_AddNewAccountTypeController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private StackPane mypane;

    @FXML
    private JFXTextField txt_accname;

    @FXML
    private JFXTextField txt_t1fees;

    @FXML
    private JFXTextField txt_t2fees;

    @FXML
    private JFXTextField txt_t3fees;

    @FXML
    private JFXButton btn_addAccont;

    @FXML
    private JFXButton btn_Cancel;

    @FXML
    private TableView table;

    @FXML
    private TableColumn col_accname;

    @FXML
    private TableColumn col_t1fee;

    @FXML
    private TableColumn col_id;

    @FXML
    private TableColumn col_t2fees;

    @FXML
    private TableColumn col_t3fees;

    @FXML
    private TableColumn col_totalfees;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_accname.setCellValueFactory(new PropertyValueFactory<>("accname"));
        col_t1fee.setCellValueFactory(new PropertyValueFactory<>("t1fee"));
        col_t2fees.setCellValueFactory(new PropertyValueFactory<>("t2fee"));
        col_t3fees.setCellValueFactory(new PropertyValueFactory<>("t3fee"));
        col_totalfees.setCellValueFactory(new PropertyValueFactory<>("totals"));

        addMembers();

    }

    public void addAccount() {

        try {

            if (!txt_accname.getText().trim().isEmpty()) {

                String accname = txt_accname.getText();

                if (!txt_t1fees.getText().trim().isEmpty()) {

                    String t1fee = txt_t1fees.getText();
                    int t1feeone = Integer.parseInt(txt_t1fees.getText());

                    if (!txt_t2fees.getText().trim().isEmpty()) {

                        String t2fee = txt_t2fees.getText();
                        int t2feeone = Integer.parseInt(txt_t2fees.getText());

                        if (!txt_t3fees.getText().trim().isEmpty()) {

                            String t3fee = txt_t3fees.getText();
                            int t3feeone = Integer.parseInt(txt_t3fees.getText());

                            int total = t1feeone + t2feeone + t3feeone;

                            String query = "INSERT INTO FeeAccountsTypes VALUES('" + accname + "',null,'" + t1fee + "','" + t2fee + "','" + t3fee + "','" + total + "')";

                            Connection conn = sqlDataBaseConnection.sqliteconnect();

                            if (!checkAccount(accname)) {

                                try {

                                    Statement st = conn.createStatement();
                                    st.executeUpdate(query);

                                    openDialog("Account Added....");

                                    addMembers();

                                    conn.close();
                                } catch (SQLException exc) {

                                    Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                                            "Sytem has encountered an error \n"
                                            + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

                                }

                            } else {

                                openDialog("This Account Name Alread Exist, Use Another Name");

                            }

                        } else {

                            openDialog("Please Add Valid Term Three Fee Amount");

                        }

                    } else {

                        openDialog("Please Add Valid Term Two Fee Amount");

                    }

                } else {

                    openDialog("Please Add Valid Term Three One Amount");

                }

            } else {

                openDialog("Please Add Valid Account Name");

            }

        } catch (Exception exc) {

            openDialog("Invalid Data...");

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

    public static Boolean checkAccount(String accname) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM FeeAccountsTypes WHERE AccountName = '" + accname + "' ";

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

    public void addMembers() {

        final ObservableList<NeFeeAccount_object> data = FXCollections.observableArrayList();

        String query = "SELECT * FROM FeeAccountsTypes";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USERS);

            while (rst.next()) {

                String AccountName = rst.getString("AccountName");
                String AccountId = rst.getString("AccountId");
                String AmountT1 = rst.getString("AmountT1");
                String AmountT2 = rst.getString("AmountT2");
                String AmountT3 = rst.getString("AmountT3");
                String AccountTotals = rst.getString("AccountTotals");

                data.add(new NeFeeAccount_object(AccountId, AccountName, AmountT1, AmountT2, AmountT3, AccountTotals));
            }

            table.setItems(data);
            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);
        }

    }

    public void deleteAccount() {

        if (!txt_accname.getText().trim().isEmpty()) {

            String code = txt_accname.getText();

            String query = "DELETE FROM FeeAccountsTypes WHERE AccountId = '" + code + "'";
            
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            
            try{
            
                Statement st = conn.createStatement();
                st.executeUpdate(query);
                
                 openDialog("Account Has Been Removed");
                 
                 conn.close();
                 
                 addMembers();
            
            }catch(SQLException exc){
                    
                     Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);
                     
                    }

        } else {

            openDialog("Please Enter Valid Code");

        }

    }

}
