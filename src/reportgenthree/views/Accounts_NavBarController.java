/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;


public class Accounts_NavBarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    String gname = "";

    @FXML
    private AnchorPane navbar;

    @FXML
    private StackPane mypane;

    @FXML
    private JFXButton btn_edit;

    @FXML
    private JFXButton btn_techers;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        gname = LoginscreenController.getGName();
        btn_edit.setOnAction(e -> openEditForm());
        btn_techers.setOnAction(e -> Teachers());

    }

    public void openUserProfile() {

        ReportGenThree.user_userProfile();

    }

    public void openEditForm() {

        ReportGenThree.Accounts_EditInfor();

    }

    public void AddNewUser() {

        if (gname.equals("Academics Admin")) {

            ReportGenThree.addNewUser();

        } else {

             reportgenthree.ReportGenThree.open_WarningDialog("Sorry Only Admin is permitted to Open This Page, Please Consult Admin");

        }

    }

    public void AddNewUnit() {

        if (gname.equals("Academics Admin")) {

            ReportGenThree.addNewUnit();

        } else {

             reportgenthree.ReportGenThree.open_WarningDialog("Sorry Only Admin is permitted to Open This Page, Please Consult Admin");

        }

    }

    public void AddNewCourses() {

        if (gname.equals("Academics Admin")) {

            ReportGenThree.Accounts_addCourses();

        } else {

             reportgenthree.ReportGenThree.open_WarningDialog("Sorry Only Admin is permitted to Open This Page, Please Consult Admin");

        }

    }

    public void Teachers() {

        if (gname.equals("Academics Admin")) {

            ReportGenThree.Accounts_Teachers();

        } else {

            reportgenthree.ReportGenThree.open_WarningDialog("Sorry Only Admin is permitted to Open This Page, Please Consult Admin");

        }

    }

}
