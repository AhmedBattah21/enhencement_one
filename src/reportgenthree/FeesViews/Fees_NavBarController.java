/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Academics_AddNewScoreController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Fees_NavBarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane navbar;

    @FXML
    private JFXButton btn_newfaccount;

    @FXML
    private JFXButton btn_createfaccount;

    @FXML
    private JFXButton btn_payfees;

    @FXML
    private JFXButton btn_stdaccount;

    @FXML
    private JFXButton btn_checkbalances;

    @FXML
    private StackPane mypane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void createStudentAccount() {

        reportgenthree.ReportGenThree.Fees_CreateAccount();

    }

    public void createAccountType() {

        reportgenthree.ReportGenThree.Fees_CreateAccountType();

    }

    public void PayFees() {

        reportgenthree.ReportGenThree.Fees_PayFees();

    }

    public void StudentFeestatements() {

        reportgenthree.ReportGenThree.Fees_FeesStatements();

    }

    public void StudentFeebalances() {

        reportgenthree.ReportGenThree.Fees_FeesBalances();

    }

    public void FeesBooklet() {

        reportgenthree.ReportGenThree.Fees_booklet();

    }

    public void Add_Program() {

        ReportGenThree.Fee_NewProgramForm();
    }

    public void available_programs() {

        ReportGenThree.Fee_AvailablePrograms();
    }
    
    public void otherPayments() {

        ReportGenThree.Fee_OtherPayments();
    }

    public void openDialog() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            Label mylabel = new Label("School Programs");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Fees_NavBarController.class.getResource("NewProgramForm.fxml"));
            StackPane pane = loader.load();
            NewProgramFormController cc = loader.getController();

            content.setHeading(mylabel);

            content.setBody(pane);

            StackPane mypane = ReportGenThree.getMajor_stackpane();

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            content.autosize();

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Academics_AddNewScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
