/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree;

import AppFuctions.Functions;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import reportgenthree.ExaminationViews.Examination_viewsController;
import reportgenthree.ExaminationReportsViews.Examination_ReportsViewController;
import reportgenthree.views.Accounts_NavBarController;
import reportgenthree.views.MainpanelController;
import reportgenthree.views.StudentRegistrationPanelController;
import reportgenthree.views.WarningBoxController;

/**
 *
 * @author Computer
 */
public class ReportGenThree extends Application {

    public static Stage primary = new Stage();

    public static Stage processorStage = new Stage();

    public static Stage SplashScreen = new Stage();
    private static final Screen SCREEN = Screen.getPrimary();
    private static final Rectangle2D BOUNDS = SCREEN.getVisualBounds();
    private static StackPane major_pane;
    private static StackPane major_stackpane;
    private static BorderPane mainlyout;
    private static AnchorPane mainlyouttwo;
    public static StudentRegistrationPanelController cc_std_regcontroller = null;
    public static TabPane main_tabpane = new TabPane();
    public static Tab user_profiletab = null;
    public static Tab Student_registration = null;

    public static Tab Student_ShowListForm = null;
    public static Tab TeachersPanel = null;
    public static Tab Examination_views_1year = null;
    public static Tab Examination_views_2ndyear = null;
    public static Tab Student_FindStudents = null;

    public static Tab Examination_report_1styear = null;
    public static Tab Examination_report_2nd = null;

    public static Tab Student_profile = null;
    public static Tab Accounts_EditInfor = null;
    public static Tab Academics_AddNewScore = null;
    public static Tab Academics_EdiExamScore = null;
    public static Tab Fees_PayFees = null;
    public static Tab NewProgramForm = null;
    public static Tab AvailablePrograms = null;
    public static Tab Program_Payment = null;
    public static Tab Fees_FeeStatements = null;
    public static Tab CreateSingleExamReport = null;
    public static Tab Fees_CreateAccount = null;
    public static Tab SemisterDurationSetter = null;
    public static Tab Fees_feebalances = null;
    public static Tab DeleteExamScore = null;

    public static FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.PAPER_PLANE_ALT);

    @Override
    public void start(Stage primaryStage) {

        try {

            SplashScreen = primaryStage;

            SplashScreen.setTitle("M.E.R.M.S   v1.0.0 2018");
            SplashScreen.centerOnScreen();
            SplashScreen.setResizable(true);

            SplashScreen.getIcons().add(new Image(ReportGenThree.class.getResourceAsStream("/company/iconthree.png")));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/user_loginwindow.fxml"));

            mainlyout = loader.load();
            Scene myscene = new Scene(mainlyout, 900, 600);
            SplashScreen.setScene(myscene);
            SplashScreen.show();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed", "" + exc);

        }

    }

    public static void LoadMainScreen() {

        try {

            Stage primaryStage = new Stage();
            SplashScreen = primaryStage;

            SplashScreen.setTitle("M.E.R.M.S   v1.0.0 2018");
            SplashScreen.centerOnScreen();
            SplashScreen.setResizable(true);

            SplashScreen.getIcons().add(new Image(ReportGenThree.class.getResourceAsStream("/company/iconthree.png")));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/user_loginwindow.fxml"));

            mainlyout = loader.load();
            Scene myscene = new Scene(mainlyout, 900, 600);
            SplashScreen.setScene(myscene);
            SplashScreen.show();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed", "" + exc);

        }

    }

    public static void closeSplash() {

        SplashScreen.close();
        Application();

    }

    public static void logoutApp() {

        user_profiletab = null;
        Student_registration = null;
        Student_ShowListForm = null;
        TeachersPanel = null;
        Examination_views_1year = null;
        Examination_views_2ndyear = null;
        Student_FindStudents = null;
        Examination_report_1styear = null;
        Examination_report_2nd = null;
        Student_profile = null;
        Accounts_EditInfor = null;
        Academics_AddNewScore = null;
        Academics_EdiExamScore = null;
        Fees_PayFees = null;
        NewProgramForm = null;
        AvailablePrograms = null;
        Program_Payment = null;
        CreateSingleExamReport = null;
        Fees_FeeStatements = null;
        Fees_CreateAccount = null;
        SemisterDurationSetter = null;
        Fees_feebalances = null;
        DeleteExamScore = null;
        main_tabpane = new TabPane();

        primary.close();

        LoadMainScreen();

    }

    /**
     * 
     */
    public static void Application() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/Mainpanel.fxml"));

            major_pane = loader.load();
            MainpanelController cc = loader.getController();

            Scene myscene = new Scene(major_pane, BOUNDS.getWidth() - 10, BOUNDS.getHeight() - 10);
            primary.setScene(myscene);
            primary.setTitle("M.E.R.M.S   v1.0.0 2018");

            primary.getIcons().add(new Image(ReportGenThree.class.getResourceAsStream("/company/iconthree.png")));

            primary.centerOnScreen();

            mainlyout = cc.getBorderpane();
            major_stackpane = cc.getMajor_stackpane();

            user_userProfile();
            ActivityWindow_NavBar();

            primary.show();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed", "" + exc);

        }

    }

    /**
     * 
     */
    public static void addStudents_NavBar() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Students_NavBar.fxml"));

        try {

            AnchorPane pane = loader.load();

            mainlyout.setLeft(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    
    /**
     * 
     */
    public static void ActivityWindow_NavBar() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Activity_Window.fxml"));

        try {

            AnchorPane pane = loader.load();

            mainlyout.setRight(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void hideRightPane() {

        try {

            mainlyout.setRight(null);

        } catch (Exception exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Student_profile() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/Student_profile.fxml"));
            StackPane pane = loader.load();

            if (Student_profile == null) {
                Student_profile = new Tab("Students Profiles");
                Student_profile.setGraphic(icon);
                Student_profile.setContent(pane);
                main_tabpane.getTabs().add(Student_profile);
                main_tabpane.getSelectionModel().select(Student_profile);

            } else {

                main_tabpane.getSelectionModel().select(Student_profile);
                Student_profile.setGraphic(icon);
            }
            Student_profile.setOnCloseRequest(e -> {

                Student_profile = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Examination_Files_1styear() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("ExaminationViews/Examination_views.fxml"));
            StackPane pane = loader.load();
            Examination_viewsController cc = loader.getController();

            if (Examination_views_1year == null) {

                Examination_views_1year = new Tab("Exam Files 1 st Year");
                Examination_views_1year.setContent(pane);
                Examination_views_1year.setGraphic(icon);
                cc.get_files_1styear();
                main_tabpane.getTabs().add(Examination_views_1year);
                main_tabpane.getSelectionModel().select(Examination_views_1year);
            } else {

                main_tabpane.getSelectionModel().select(Examination_views_1year);
                Examination_views_1year.setGraphic(icon);
            }
            Examination_views_1year.setOnCloseRequest(e -> {

                Examination_views_1year = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Examination_Files_2ndyear() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("ExaminationViews/Examination_views.fxml"));
            StackPane pane = loader.load();
            Examination_viewsController cc = loader.getController();

            if (Examination_views_2ndyear == null) {

                Examination_views_2ndyear = new Tab(" 2nd Year Exam Files");
                Examination_views_2ndyear.setContent(pane);
                Examination_views_2ndyear.setGraphic(icon);
                cc.get_files_2ndyear();
                main_tabpane.getTabs().add(Examination_views_2ndyear);
                main_tabpane.getSelectionModel().select(Examination_views_2ndyear);

            } else {

                main_tabpane.getSelectionModel().select(Examination_views_2ndyear);
                Examination_views_2ndyear.setGraphic(icon);
            }

            Examination_views_2ndyear.setOnCloseRequest(e -> {

                Examination_views_2ndyear = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Examination_reportsfiles_1st() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("ExaminationReportsViews/Examination_views.fxml"));
            StackPane pane = loader.load();
            Examination_ReportsViewController cc = loader.getController();

            if (Examination_report_1styear == null) {

                Examination_report_1styear = new Tab("1st Report Forms");
                Examination_report_1styear.setContent(pane);
                cc.get_files_1st();
                main_tabpane.getTabs().add(Examination_report_1styear);
                Examination_report_1styear.setGraphic(icon);
                main_tabpane.getSelectionModel().select(Examination_report_1styear);
            } else {

                main_tabpane.getSelectionModel().select(Examination_report_1styear);
                Examination_report_1styear.setGraphic(icon);
            }

            Examination_report_1styear.setOnCloseRequest(e -> {

                Examination_report_1styear = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Examination_reportsfiles_2nd() {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("ExaminationReportsViews/Examination_views.fxml"));
            StackPane pane = loader.load();
            Examination_ReportsViewController cc = loader.getController();

            if (Examination_report_2nd == null) {

                Examination_report_2nd = new Tab("2nd Year Report Forms");
                Examination_report_2nd.setContent(pane);
                Examination_report_2nd.setGraphic(icon);
                cc.get_files_2nd();
                main_tabpane.getTabs().add(Examination_report_2nd);
                main_tabpane.getSelectionModel().select(Examination_report_2nd);
            } else {

                main_tabpane.getSelectionModel().select(Examination_report_2nd);
                Examination_report_2nd.setGraphic(icon);
            }

            Examination_report_2nd.setOnCloseRequest(e -> {

                Examination_report_2nd = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Accounts_EditInfor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Accounts_EditInfor.fxml"));

        try {

            StackPane pane = loader.load();

            if (Accounts_EditInfor == null) {

                Accounts_EditInfor = new Tab("Edit Account Infor");
                Accounts_EditInfor.setContent(pane);
                main_tabpane.getTabs().add(Accounts_EditInfor);
                Accounts_EditInfor.setGraphic(icon);
                main_tabpane.getSelectionModel().select(Accounts_EditInfor);

            } else {

                main_tabpane.getSelectionModel().select(Accounts_EditInfor);
                Accounts_EditInfor.setGraphic(icon);
            }

            Accounts_EditInfor.setOnCloseRequest(e -> {

                Accounts_EditInfor = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Accounts_Teachers() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/TeachersPanel.fxml"));

        try {

            StackPane pane = loader.load();
            mainlyout.setRight(null);

            if (TeachersPanel == null) {

                TeachersPanel = new Tab("Teacher's Panel");
                TeachersPanel.setContent(pane);
                main_tabpane.getTabs().add(TeachersPanel);
                TeachersPanel.setGraphic(icon);
                main_tabpane.getSelectionModel().select(TeachersPanel);
            } else {

                main_tabpane.getSelectionModel().select(TeachersPanel);
                TeachersPanel.setGraphic(icon);
            }

            TeachersPanel.setOnCloseRequest(e -> {

                TeachersPanel = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Settings_Form() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("SystemSettings/MainSettingWindow.fxml"));

        try {

            StackPane pane = loader.load();

            mainlyout.setCenter(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Semister_DurationSetter() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/SemisterDurationSetter.fxml"));

        try {

            StackPane pane = loader.load();

            if (SemisterDurationSetter == null) {
                SemisterDurationSetter = new Tab("Semister Durations");
                SemisterDurationSetter.setGraphic(icon);
                SemisterDurationSetter.setContent(pane);
                main_tabpane.getTabs().add(SemisterDurationSetter);
                main_tabpane.getSelectionModel().select(SemisterDurationSetter);

            } else {

                main_tabpane.getSelectionModel().select(SemisterDurationSetter);
                SemisterDurationSetter.setGraphic(icon);
            }
            SemisterDurationSetter.setOnCloseRequest(e -> {

                SemisterDurationSetter = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void addAcademic_NavBar() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Academics_NavBar.fxml"));

        try {

            AnchorPane pane = loader.load();

            mainlyout.setLeft(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void addAccounts_NavBar() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Accounts_NavBar.fxml"));

        try {

            AnchorPane pane = loader.load();

            mainlyout.setLeft(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_StudentsRegistration() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/StudentRegistrationPanel.fxml"));

        try {

            StackPane pane = loader.load();
            cc_std_regcontroller = loader.getController();

            if (Student_registration == null) {

                Student_registration = new Tab("Student Registration");
                Student_registration.setGraphic(icon);
                Student_registration.setContent(pane);
                main_tabpane.getTabs().add(Student_registration);
                main_tabpane.getSelectionModel().select(Student_registration);
            } else {

                main_tabpane.getSelectionModel().select(Student_registration);
                Student_registration.setGraphic(icon);

            }

            Student_registration.setOnCloseRequest(e -> {

                Student_registration = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static StudentRegistrationPanelController get_std_registrationcontroller() {

        return cc_std_regcontroller;

    }

    public static void NavBar_StudentsDeleteData() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Student_DeleteDataForm.fxml"));

        try {

            AnchorPane pane = loader.load();

            mainlyout.setRight(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_StudentsFindData() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Student_FindStudents.fxml"));

        try {

            StackPane pane = loader.load();
            if (Student_FindStudents == null) {

                Student_FindStudents = new Tab("Student's Finder");
                Student_FindStudents.setGraphic(icon);
                Student_FindStudents.setContent(pane);
                main_tabpane.getTabs().add(Student_FindStudents);
                main_tabpane.getSelectionModel().select(Student_FindStudents);

            } else {

                main_tabpane.getSelectionModel().select(Student_FindStudents);
                Student_FindStudents.setGraphic(icon);
            }

            Student_FindStudents.setOnCloseRequest(e -> {

                Student_FindStudents = null;

            });
        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void user_userProfile() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/user_userprofilepage.fxml"));
        main_tabpane.setSide(Side.BOTTOM);

        try {

            StackPane pane = loader.load();

            if (user_profiletab == null) {
                mainlyout.setCenter(main_tabpane);

                user_profiletab = new Tab();
                user_profiletab.setClosable(false);
                user_profiletab.setGraphic(icon);
                user_profiletab.setContent(pane);
                user_profiletab.setText("Home");
                main_tabpane.getTabs().add(user_profiletab);

            } else {

                main_tabpane.getSelectionModel().select(user_profiletab);
                user_profiletab.setGraphic(icon);
            }

            user_profiletab.setOnCloseRequest(e -> {

                user_profiletab = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_StudentsViewListForm() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Student_ShowListForm.fxml"));

        try {

            StackPane pane = loader.load();
            mainlyout.setRight(null);

            if (Student_ShowListForm == null) {

                Student_ShowListForm = new Tab("All Students");
                Student_ShowListForm.setContent(pane);
                Student_ShowListForm.setGraphic(icon);
                main_tabpane.getTabs().add(Student_ShowListForm);
                main_tabpane.getSelectionModel().select(Student_ShowListForm);
            } else {

                main_tabpane.getSelectionModel().select(Student_ShowListForm);
                Student_ShowListForm.setGraphic(icon);
            }

            Student_ShowListForm.setOnCloseRequest(e -> {

                Student_ShowListForm = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_AcademicsEditExamScore() {
        //NavBar_AcademicsStudentsScoreSheets
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Academics_EdiExamScore.fxml"));

        try {

            StackPane pane = loader.load();
            if (Academics_EdiExamScore == null) {

                Academics_EdiExamScore = new Tab("Edit Exam Score");
                Academics_EdiExamScore.setContent(pane);
                main_tabpane.getTabs().add(Academics_EdiExamScore);
                main_tabpane.getSelectionModel().select(Academics_EdiExamScore);
            } else {

                main_tabpane.getSelectionModel().select(Academics_EdiExamScore);
                Academics_EdiExamScore.setGraphic(icon);

            }

            Academics_EdiExamScore.setOnCloseRequest(e -> {

                Academics_EdiExamScore = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_AcademicsStudentsScoreSheets() {
        //NavBar_AcademicsStudentsScoreSheets
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Academics_StudentsScoreSheets.fxml"));

        try {

            AnchorPane pane = loader.load();

            mainlyout.setCenter(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void removeLeftNode() {
        //NavBar_AcademicsFindResultsForm

        try {

            mainlyout.setLeft(null);

        } catch (Exception exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void removeRightNode() {

        try {

            mainlyout.setRight(null);

        } catch (Exception exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_AcademicsAddScore() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Academics_AddNewScore.fxml"));

        try {

            StackPane pane = loader.load();
            if (Academics_AddNewScore == null) {
                Academics_AddNewScore = new Tab("Add Exam Score");
                Academics_AddNewScore.setContent(pane);
                main_tabpane.getTabs().add(Academics_AddNewScore);
                Academics_AddNewScore.setGraphic(icon);
                main_tabpane.getSelectionModel().select(Academics_AddNewScore);
            } else {

                main_tabpane.getSelectionModel().select(Academics_AddNewScore);
                Academics_AddNewScore.setGraphic(icon);
            }
            Academics_AddNewScore.setOnCloseRequest(e -> {

                Academics_AddNewScore = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Accounts_addCourses() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Accounts_addNewCourse.fxml"));

        try {

            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.setResizable(false);
            Scene myscene = new Scene(pane, 385, 470);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void addNewUser() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Admin_newuser.fxml"));

        try {

            Stage new_stage = new Stage();
            AnchorPane pane = loader.load();
            new_stage.setResizable(false);
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void addNewUnit() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Accounts_addNewUnit.fxml"));

        try {

            Stage new_stage = new Stage();
            StackPane pane = loader.load();

            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.setResizable(false);
            Scene myscene = new Scene(pane, 450, 535);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Sign_Up() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/UserRegistration.fxml"));

        try {

            Stage new_stage = new Stage();
            AnchorPane pane = loader.load();
            new_stage.setResizable(false);
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void HelpBox() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("UserHelp/MainhelpWindow.fxml"));

        try {

            Stage new_stage = new Stage();
            AnchorPane pane = loader.load();

            Scene myscene = new Scene(pane, 900, 600);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void LogInWindow() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/loginscreen.fxml"));

        try {

            Stage new_stage = new Stage();
            AnchorPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_AcademicsDeleteScore() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Academics_DeleteExamScore.fxml"));

        try {

            StackPane  pane = loader.load();

            if (DeleteExamScore == null) {
                DeleteExamScore = new Tab("Delete Exam Score");
                DeleteExamScore.setContent(pane);
                main_tabpane.getTabs().add(DeleteExamScore);
                DeleteExamScore.setGraphic(icon);
                main_tabpane.getSelectionModel().select(DeleteExamScore);
            } else {

                main_tabpane.getSelectionModel().select(DeleteExamScore);
                DeleteExamScore.setGraphic(icon);
            }
           DeleteExamScore.setOnCloseRequest(e -> {

                DeleteExamScore = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_AcademicsSingleReport() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/CreateSingleExamReport.fxml"));

        try {

            StackPane pane = loader.load();

            if (CreateSingleExamReport == null) {

                CreateSingleExamReport = new Tab("Exam Files 1 st Year");
                CreateSingleExamReport.setContent(pane);
                CreateSingleExamReport.setGraphic(icon);

                main_tabpane.getTabs().add(CreateSingleExamReport);
                main_tabpane.getSelectionModel().select(CreateSingleExamReport);
            } else {

                main_tabpane.getSelectionModel().select(CreateSingleExamReport);
                CreateSingleExamReport.setGraphic(icon);
            }
            CreateSingleExamReport.setOnCloseRequest(e -> {

                CreateSingleExamReport = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NameSetter() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/SystemNameSetter.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_RecordsUpaderForm() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/RecordsUpdater.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_SingleEndStage() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/CreateSingleEndStage.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_Endstagedetailstaker() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/EndstageDetailsTaker.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_AcademicsMultipleReport() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/CreateMultipleExamReport.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void NavBar_AcademicsMultipleEndStage() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/CreateMultipleEndStageReport.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void MissingMarksErrorWindow() {

        //NavBar_AcademicsSingleReport()
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/ReportProductionMissingMarks.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            AnchorPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void LoadDescriptionSettingsForClassListPdf() {

        Stage newstage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/DescriptionTaker.fxml"));

        try {

            newstage.setTitle("Pdf Document Settings");

            newstage.initModality(Modality.APPLICATION_MODAL);

            newstage.initStyle(StageStyle.UNDECORATED);

            AnchorPane pane = loader.load();

            Scene myscene = new Scene(pane, 400, 540);

            newstage.setScene(myscene);
            newstage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void LoadProcessCompleteBox() {

        Stage newstage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/ProcessComplete.fxml"));

        try {

            newstage.setTitle("Pdf Document Settings");

            newstage.initModality(Modality.APPLICATION_MODAL);

            newstage.setResizable(true);

            AnchorPane pane = loader.load();

            Scene myscene = new Scene(pane, 300, 300);

            newstage.setScene(myscene);
            newstage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void LoadProcessor() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/Proessor.fxml"));

        try {

            processorStage.setTitle("Processing....");

            processorStage.initModality(Modality.APPLICATION_MODAL);

            processorStage.initStyle(StageStyle.UNDECORATED);

            AnchorPane pane = loader.load();

            Scene myscene = new Scene(pane, 260, 210);

            processorStage.setScene(myscene);
            processorStage.show();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void LoadProcessorResult() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/ProessorResult.fxml"));

        try {

            processorStage.setTitle("Processing Complete");

            AnchorPane pane = loader.load();

            Scene myscene = new Scene(pane, 260, 210);

            processorStage.setScene(myscene);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void closeProcessor() {

        processorStage.close();

        processorStage = new Stage();

    }

    public static void ProcessCompleteMultipleReports() {

        Stage newstage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/ProcessCompleteMultipleReports.fxml"));

        try {

            newstage.setTitle("Pdf Document Settings");

            newstage.initModality(Modality.APPLICATION_MODAL);

            newstage.initStyle(StageStyle.UNDECORATED);

            AnchorPane pane = loader.load();

            Scene myscene = new Scene(pane, 350, 500);

            newstage.setScene(myscene);
            newstage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void ProcessCompleteMultipleEndstage() {

        Stage newstage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/ProcessCompleteMultipleEndStageReports.fxml"));

        try {

            newstage.setTitle("Pdf Document Settings");

            newstage.initModality(Modality.APPLICATION_MODAL);

            newstage.initStyle(StageStyle.UNDECORATED);

            AnchorPane pane = loader.load();

            Scene myscene = new Scene(pane, 350, 500);

            newstage.setScene(myscene);
            newstage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    //-----------------fees forms
    public static void Fees_CreateAccount() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/Fees_CreateAccount.fxml"));

        try {

            StackPane pane = loader.load();

            if (Fees_CreateAccount == null) {

                Fees_CreateAccount = new Tab("Create Students Fee Accounts");
                Fees_CreateAccount.setContent(pane);
                Fees_CreateAccount.setGraphic(icon);

                main_tabpane.getTabs().add(Fees_CreateAccount);
                main_tabpane.getSelectionModel().select(Fees_CreateAccount);
            } else {

                main_tabpane.getSelectionModel().select(Fees_CreateAccount);
                Fees_CreateAccount.setGraphic(icon);
            }
            Fees_CreateAccount.setOnCloseRequest(e -> {

                Fees_CreateAccount = null;

            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }
    }

    public static void Fees_CreateAccountType() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/Fees_AddNewAccountType.fxml"));

        try {

            StackPane pane = loader.load();

            mainlyout.setRight(pane);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Fees_PayFees() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/Fees_PayFees.fxml"));

        try {

            StackPane pane = loader.load();
            if (Fees_PayFees == null) {

                Fees_PayFees = new Tab("Fee Payment Tab");
                Fees_PayFees.setContent(pane);
                main_tabpane.getTabs().add(Fees_PayFees);
                main_tabpane.getSelectionModel().select(Fees_PayFees);
            } else {

                main_tabpane.getSelectionModel().select(Fees_PayFees);
                Fees_PayFees.setGraphic(icon);
            }

            Fees_PayFees.setOnCloseRequest(e -> {

                Fees_PayFees = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Fee_NewProgramForm() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/NewProgramForm.fxml"));

        try {

            StackPane pane = loader.load();
            if (NewProgramForm == null) {

                NewProgramForm = new Tab("New Program");
                NewProgramForm.setContent(pane);
                main_tabpane.getTabs().add(NewProgramForm);
                main_tabpane.getSelectionModel().select(NewProgramForm);
            } else {

                main_tabpane.getSelectionModel().select(NewProgramForm);
                NewProgramForm.setGraphic(icon);
            }

            NewProgramForm.setOnCloseRequest(e -> {

                NewProgramForm = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Fee_OtherPayments() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/Program_Payment.fxml"));

        try {

            StackPane pane = loader.load();
            if (Program_Payment == null) {

                Program_Payment = new Tab("Other Program Payments");
                Program_Payment.setContent(pane);
                main_tabpane.getTabs().add(Program_Payment);
                main_tabpane.getSelectionModel().select(Program_Payment);
            } else {

                main_tabpane.getSelectionModel().select(Program_Payment);
                Program_Payment.setGraphic(icon);
            }

            Program_Payment.setOnCloseRequest(e -> {

                Program_Payment = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Fee_AvailablePrograms() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/AvailablePrograms.fxml"));

        try {

            StackPane pane = loader.load();
            if (AvailablePrograms == null) {

                AvailablePrograms = new Tab("Available Programs");
                AvailablePrograms.setContent(pane);
                main_tabpane.getTabs().add(AvailablePrograms);
                main_tabpane.getSelectionModel().select(AvailablePrograms);
            } else {

                main_tabpane.getSelectionModel().select(AvailablePrograms);
                AvailablePrograms.setGraphic(icon);
            }

            AvailablePrograms.setOnCloseRequest(e -> {

                AvailablePrograms = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Fees_FeesStatements() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/Fees_FeeStatements.fxml"));

        try {

            StackPane pane = loader.load();

            if (Fees_FeeStatements == null) {

                Fees_FeeStatements = new Tab("View FeeStatements");
                Fees_FeeStatements.setContent(pane);
                main_tabpane.getTabs().add(Fees_FeeStatements);
                main_tabpane.getSelectionModel().select(Fees_FeeStatements);
            } else {

                main_tabpane.getSelectionModel().select(Fees_FeeStatements);
                Fees_FeeStatements.setGraphic(icon);
            }

            Fees_FeeStatements.setOnCloseRequest(e -> {

                Fees_FeeStatements = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Fees_FeesBalances() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/Fees_feebalances.fxml"));

        try {

            StackPane pane = loader.load();

            if (Fees_feebalances == null) {

                Fees_feebalances = new Tab("Students Fee Balances");
                Fees_feebalances.setContent(pane);
                main_tabpane.getTabs().add(Fees_feebalances);
                main_tabpane.getSelectionModel().select(Fees_feebalances);
            } else {

                main_tabpane.getSelectionModel().select(Fees_feebalances);
                Fees_feebalances.setGraphic(icon);
            }

            Fees_feebalances.setOnCloseRequest(e -> {

                Fees_feebalances = null;
            });

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void Fees_booklet() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("FeesViews/Fee_statementsBooklet.fxml"));

        try {

            //AnchorPane pane = loader.load();
            //mainlyout.setLeft(pane);
            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.initStyle(StageStyle.UNDECORATED);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void BackUp() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ReportGenThree.class.getResource("views/CreateBackUp.fxml"));

        try {

            Stage new_stage = new Stage();
            StackPane pane = loader.load();
            new_stage.initModality(Modality.APPLICATION_MODAL);
            new_stage.setResizable(false);
            Scene myscene = new Scene(pane);
            new_stage.setScene(myscene);
            new_stage.showAndWait();

        } catch (LoadException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public static void open_WarningDialog(String infor) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(ReportGenThree.class.getResource("views/WarningBox.fxml"));
            StackPane spane = loader.load();
            WarningBoxController cc = loader.getController();
            cc.getBtn_help().setOnAction(e -> HelpBox());
            cc.getLbl_infor().setText(infor);
            content.setBody(spane);

            StackPane pane = getMajor_stackpane();

            JFXDialog dlog = new JFXDialog(pane, content, JFXDialog.DialogTransition.BOTTOM);

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Accounts_NavBarController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static StackPane getMajor_stackpane() {
        return major_stackpane;
    }

    public static void setMajor_stackpane(StackPane major_stackpane) {
        ReportGenThree.major_stackpane = major_stackpane;
    }

    public static TabPane getMain_tabpane() {
        return main_tabpane;
    }

    public static void setMain_tabpane(TabPane main_tabpane) {
        
        ReportGenThree.main_tabpane = main_tabpane;
    }

}
