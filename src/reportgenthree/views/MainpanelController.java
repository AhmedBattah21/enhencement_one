/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class MainpanelController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private BorderPane borderpane;

    @FXML
    private StackPane major_stackpane;

    @FXML
    private JFXButton btn_students;

    @FXML
    private JFXButton btn_academics;

    @FXML
    private JFXButton btn_Accounts;

    @FXML
    private JFXButton btn_fees;

    @FXML
    private JFXButton btn_datamanager;

    @FXML
    private JFXButton btn_Settings;

    @FXML
    private JFXButton btn_Home;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private MenuItem close;

    @FXML
    private MenuItem logout;

    @FXML
    private MenuItem hide;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        HamburgerBasicCloseTransition transition = new HamburgerBasicCloseTransition(hamburger);

        transition.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            //transition.play();
            drawerplay();
        });

      

        try {

            AnchorPane pane = FXMLLoader.load(ReportGenThree.class.getResource("views/Students_NavBar.fxml"));

            drawer.setSidePane(pane);

            drawer.requestFocus();

        } catch (IOException exc) {

            System.out.println("here" + exc);

        }

    }

    public void drawerplay() {

        if (drawer.isShown()) {

            drawer.close();

        } else {

            drawer.open();
        }

    }

    public void loadStudents_NavBar() {

        try {
            AnchorPane pane = FXMLLoader.load(ReportGenThree.class.getResource("views/Students_NavBar.fxml"));
            //drawer.setMinWidth(200);

            if (drawer.isShown()) {
                drawer.setSidePane(pane);
            } else {

                drawer.setSidePane(pane);
                drawer.open();
            }

        } catch (IOException exc) {

        }

    }

    public void loadAcademics_NavBar() {

        try {
            AnchorPane pane = FXMLLoader.load(ReportGenThree.class.getResource("views/Academics_NavBar.fxml"));
            //drawer.setMinWidth(200);

            if (drawer.isShown()) {
                drawer.setSidePane(pane);
            } else {

                drawer.setSidePane(pane);
                drawer.open();
            }

        } catch (IOException exc) {

        }

    }

    public void loadProfile_NavBar() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/User_profileOne.fxml"));
            AnchorPane pane = loader.load();
            
            User_profileOneController cc = loader.getController();
            cc.get_userdata();
            cc.set_image();

            if (drawer.isShown()) {
                drawer.setSidePane(pane);
            } else {

                drawer.setSidePane(pane);
                drawer.open();
            }

        } catch (IOException exc) {

        }

    }

    public void loadAccounts_NavBar() {

        try {
            AnchorPane pane = FXMLLoader.load(ReportGenThree.class.getResource("views/Accounts_NavBar.fxml"));
            //drawer.setMinWidth(200);

            if (drawer.isShown()) {
                drawer.setSidePane(pane);
            } else {

                drawer.setSidePane(pane);
                drawer.open();
            }
        } catch (IOException exc) {

        }
    }

    public void loadSettings_NavBar() {

        try {
            AnchorPane pane = FXMLLoader.load(ReportGenThree.class.getResource("views/Settings_NavBar.fxml"));
          

            if (drawer.isShown()) {
                drawer.setSidePane(pane);
            } else {

                drawer.setSidePane(pane);
                drawer.open();
            }

        } catch (IOException exc) {

            System.out.println(" " + exc);

        }
    }
    
     public void loadSystemSettings() {

        try {
            AnchorPane pane = FXMLLoader.load(ReportGenThree.class.getResource("views/SystemSettings_NavBar.fxml"));
          

            if (drawer.isShown()) {
                drawer.setSidePane(pane);
            } else {

                drawer.setSidePane(pane);
                drawer.open();
            }

        } catch (IOException exc) {

            System.out.println(" " + exc);

        }
    }

    public void loadFees_NavBar() {

        String user = reportgenthree.views.LoginscreenController.getGName();

        if (user.equals("Finance Admin")) {

            try {
                AnchorPane pane = FXMLLoader.load(ReportGenThree.class.getResource("FeesViews/Fees_NavBar.fxml"));
                pane.setMinWidth(200);

                if (drawer.isShown()) {
                    drawer.setSidePane(pane);
                } else {

                    drawer.setSidePane(pane);
                    drawer.open();
                }
            } catch (IOException exc) {

                System.out.println(" " + exc);

            }

        } else {

            reportgenthree.ReportGenThree.open_WarningDialog("You Have No Permission To Access This Site...");

        }
    }

    public void notification(String title, String data) {

        Notifications.create()
                .title(title)
                .text(data)
                .position(Pos.TOP_LEFT)
                .hideAfter(Duration.seconds(5))
                .darkStyle()
                .show();

    }

    public void minimize() {

        Stage stage = (Stage) btn_Accounts.getScene().getWindow();
        stage.setIconified(true);

    }

    public void closeApp() {

        Stage stage = (Stage) btn_Accounts.getScene().getWindow();
        stage.close();

    }

    public void hideRight() {

        reportgenthree.ReportGenThree.hideRightPane();

    }

    public void logOut() {

        reportgenthree.ReportGenThree.logoutApp();

    }

    public void Add_Mainprofile() {

        reportgenthree.ReportGenThree.user_userProfile();

    }

    public void Add_ActivityWindow() {

        reportgenthree.ReportGenThree.ActivityWindow_NavBar();

    }

    public BorderPane getBorderpane() {
        return borderpane;
    }

    public void setBorderpane(BorderPane borderpane) {
        this.borderpane = borderpane;
    }

    public StackPane getMajor_stackpane() {
        return major_stackpane;
    }

    public void setMajor_stackpane(StackPane major_stackpane) {
        this.major_stackpane = major_stackpane;
    }

}
