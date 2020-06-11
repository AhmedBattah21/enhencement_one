/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.DatabaseManager;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.util.Pair;
import reportgenthree.ExaminationReportsViews.ExaminationFile_ReportFormController;
import reportgenthree.ReportGenThree;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class User_loginwindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_login;

    @FXML
    private JFXButton btn_createAccount;

    @FXML
    private JFXButton btn_help;

    @FXML
    private BorderPane layout;

    @FXML
    private Circle circle;

    @FXML
    private Label infor;

    @FXML
    private StackPane mypane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        DatabaseManager();

        Image im = new Image("reportgenthree/views/System_images/12.png", false);

        circle.setFill(new ImagePattern(im));

        btn_createAccount.setOnAction(e -> Sign_up());

        startTask();

    }

    public void login() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/loginscreen.fxml"));
            AnchorPane pane = loader.load();
            LoginscreenController cc = loader.getController();

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Help() {

        reportgenthree.ReportGenThree.HelpBox();

    }

    public void Sign_up() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/UserRegistration.fxml"));
            AnchorPane pane = loader.load();
            UserRegistrationController cc = loader.getController();

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {
            Logger.getLogger(ExaminationFile_ReportFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Name_Setter() {

        //create the custom dialog
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Developer Login");
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXPEDITEDSSL);
        icon.setGlyphSize(40);
        icon.setGlyphStyle("-fx-fill:green;");

        dialog.setGraphic(icon);
        dialog.setHeaderText("Only Developers Authorised..");

        //set Button types
        ButtonType lbutton = new ButtonType("Open", ButtonData.NO);
        dialog.getDialogPane().getButtonTypes().addAll(lbutton, ButtonType.CANCEL);

        //create the username and password labels and fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 20, 10, 10));

        JFXTextField fld = new JFXTextField();
        fld.setPromptText("Developer email");
        fld.setMinWidth(200);

        JFXPasswordField pfld = new JFXPasswordField();
        pfld.setPromptText("Developer Password");

        Label infor = new Label("Enter Email and Password");

        grid.add(fld, 2, 2);
        grid.add(pfld, 2, 5);
        grid.add(infor, 4, 8);

        dialog.getDialogPane().setContent(grid);

        Optional<Pair<String, String>> result = dialog.showAndWait();

        String pass = pfld.getText();
        String uname = fld.getText();

        if (pass.equals("erickerickyaah") && uname.equals("erickerickmlz@gmail.com")) {

            reportgenthree.ReportGenThree.NameSetter();

        } else {

            infor.setText("Wrong Password..");
        }

    }

    public void runTask() {

        DateTimeFormatter fromat = DateTimeFormatter.ofPattern("'Time: 'HH:mm:ss");
        Random rand = new Random();

        for (int i = 1; i <= 10; i++) {

            int r = rand.nextInt(25) + 1;

            LocalDateTime time = LocalDateTime.now();

            String[] words = {"CREATE AWESOME REPORTS", "MANAGE RECORDS", "ACCESS RECORDS EASILY AND FASTER",
                "KEEP EXAMINATION RECORDS", "GET STUDENTS LIST EASILY", "PRINT DOCUMENTS WITH A BUTTON CLICK",
                "SEARCH STUDENTS", "REMOVE AND EDIT RECORDS EASILY", "P.E.R.M.S", "WELCOME TO P.E.R.M.S"};

            String image = "reportgenthree/AppCss/images/" + r + ".jpg";
            layout.setStyle("-fx-background-image: url(" + image + ");"
                    + "    -fx-background-repeat: no-repeat;"
                    + "    -fx-background-size: cover, auto;"
                    + "    -fx-opacity: 1;"
                    + " -fx-background-color:transparent"
            );

            if (i == 10) {

                i = 1;
            }

            try {
                // Get the Status
                final String status = (words[i]);
                final int n = 1;

                // Update the Label on the JavaFx Application Thread
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        // layout.setCenter(box);
                        infor.setText(fromat.format(time) + " \n " + status);
                    }
                });

                Thread.sleep(10000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void DatabaseManager() {

        if (!DatabaseManager.check_Database_file()) {

            DatabaseManager.createDatabase();
            DatabaseManager.create_tables();
        } else {

        }

    }

    public void startTask() {
        // Create a Runnable
        Runnable task = new Runnable() {
            public void run() {
                runTask();
            }
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

}
