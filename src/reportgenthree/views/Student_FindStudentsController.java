/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Student_FindStudentsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private VBox container;

    @FXML
    private HBox infor_container;

    @FXML
    private Rectangle image;

    @FXML
    private JFXTextField txt_hint;

    JFXButton btn;

    public AnchorPane pane;

    private Student_dataholder widgetAccessor;

    // java.util.concurrent.Executor typically provides a pool of threads...
    private Executor exec;

    String home = System.getProperty("user.home") + "/" + "Documents";
    Image img = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt_hint.requestFocus();

        placeInfpBox();

        widgetAccessor = new Student_dataholder();

        img = new Image(getClass().getResourceAsStream("System_images/loader2.gif"));

        // create executor that uses daemon threads:
        exec = Executors.newCachedThreadPool(runnable -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });

    }

    public void funnyMethod() {

        container.getChildren().clear();

        String hint = txt_hint.getText().trim();

        if (hint.equals("")) {

            placeInfpBox();

        } else {

            Connection conn = sqlDataBaseConnection.sqliteconnect();

            hint = hint.trim();

            String query = "SELECT * FROM Students_2017 WHERE StudentRegCode LIKE '%" + hint + "%' OR StudentName LIKE '%" + hint + "%' "
                    + "OR YearOfStudy LIKE '%" + hint + "%' OR StudentCourse LIKE '%" + hint + "%' OR StudentCategory LIKE '%" + hint + "%'";
            Platform.runLater(() -> {
                try {

                    Statement st = conn.createStatement();

                    ResultSet rst = st.executeQuery(query);

                    int results = 0;

                    while (rst.next()) {

                        //rectangle for holding images
                        File imagepath = new File(home + "/ReportGenThree/students/" + rst.getString("std_image"));
                        Image im = new Image(imagepath.toURI().toString(), false);

                        results++;

                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Student_FindStudentsController.class.getResource("Students_datadisplayer.fxml"));
                        try {

                            StackPane stpane_one = loader.load();
                            Students_datadisplayerController controller = loader.getController();

                            controller.setAdm_number(rst.getString("Ccode") + "/" + rst.getString("StudentRegCode"));
                            controller.setStd_course(rst.getString("StudentCourse"));
                            controller.setStd_name(rst.getString("StudentName"));
                            controller.setStd_dor(rst.getString("DOR"));
                            controller.setStd_year(rst.getString("YearOfStudy"));
                            controller.setStd_category(rst.getString("StudentCategory"));
                            controller.setStd_image(imagepath);
                            controller.setStd_code(rst.getString("StudentRegCode"));

                            container.getChildren().add(stpane_one);

                        } catch (IOException ex) {

                            System.out.println("" + ex);
                        }

                    }

                    if (results == 0) {

                        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                        icon.setStyle("-fx-fill:orange");
                        container.getChildren().add(add_inforbox("Sorry No Information Matching Your Search!", icon));

                    }

                } catch (SQLException exc) {

                    System.out.println("" + exc);

                }

            });

        }

        //container.getChildren().add(pane);
    }

   

    public void stdDeleteForm() {

        reportgenthree.ReportGenThree.NavBar_StudentsDeleteData();

    }

    

    public void placeInfpBox() {

        container.getChildren().clear();

        String infor = "Welcome, Enter A Hint In The Above Text Box "
                + " To Search Student Details";
        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BINOCULARS);
        icon.setStyle("-fx-fill:seagreen");

        container.getChildren().add(add_inforbox(infor, icon));

    }

    public void FadeInTrans(HBox box) {

        FadeTransition fadeOutTransition = new FadeTransition();
        fadeOutTransition.setDuration(Duration.seconds(1));
        fadeOutTransition.setNode(box);
        fadeOutTransition.setFromValue(0.5);
        fadeOutTransition.setToValue(1);

        fadeOutTransition.play();

    }

    public StackPane add_inforbox(String Message, FontAwesomeIconView icon) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Student_FindStudentsController.class.getResource("Information_window.fxml"));
        StackPane pane = null;
        icon.setGlyphSize(150);
        try {
            pane = loader.load();
            Information_windowController cc = loader.getController();
            cc.setInfor(Message);
            cc.setForm_icon(icon);

        } catch (IOException exc) {

            System.out.println("" + exc);

        }

        return pane;

    }

    public StackPane set_processing() {

        container.getChildren().clear();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Student_FindStudentsController.class.getResource("Processing_window.fxml"));
        StackPane pane = null;

        try {
            pane = loader.load();
            container.getChildren().add(pane);

        } catch (IOException exc) {

            System.out.println("" + exc);

        }

        return pane;

    }

    // handle search button:
    @FXML
    public void searchWidgets() {

        set_processing();

        Task<VBox> widgetSearchTask = new Task<VBox>() {

            @Override
            protected VBox call() throws Exception {

                return widgetAccessor.getWidows(txt_hint.getText());

            }
        };

        widgetSearchTask.setOnFailed(e -> {

            widgetSearchTask.getException().printStackTrace();
            // inform user of error...
        });

        widgetSearchTask.setOnSucceeded(e -> {

            if (!widgetSearchTask.getValue().getChildren().isEmpty()) {

                Platform.runLater(() -> {
                    container.getChildren().add(widgetSearchTask.getValue());
                });

            } else {

                container.getChildren().clear();
                FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                icon.setStyle("-fx-fill:orange");
                container.getChildren().add(add_inforbox("Sorry No Information Matching Your Search!", icon));

            }

        });

        // run the task using a thread from the thread pool:
        exec.execute(widgetSearchTask);
    }

    @FXML
    public void searchWidgets_lists() {

        if (!txt_hint.getText().isEmpty()) {

            set_processing();

            Task<List<StackPane>> widgetSearchTask = new Task<List<StackPane>>() {

                @Override
                protected List call() throws Exception {

                    return widgetAccessor.getWidows_lists(txt_hint.getText());

                }
            };

            widgetSearchTask.setOnFailed(e -> {

                widgetSearchTask.getException().printStackTrace();
                // inform user of error...
            });

            widgetSearchTask.setOnSucceeded(e -> {

                if (!widgetSearchTask.getValue().isEmpty()) {

                    Platform.runLater(() -> {
                        startTask(widgetSearchTask.getValue());
                        //container.getChildren().add(widgetSearchTask.getValue());
                    });

                } else {

                    container.getChildren().clear();
                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                    icon.setStyle("-fx-fill:orange");
                    container.getChildren().add(add_inforbox("Sorry No Information Matching Your Search!", icon));

                }

            });

            // run the task using a thread from the thread pool:
            exec.execute(widgetSearchTask);

        } else {

            placeInfpBox();
        }
    }

    public void runTask(List<StackPane> items) {

        final List<StackPane> stackpanes = items;

        Platform.runLater(() -> {

            container.getChildren().clear();

        });

        double m = 0;

        for (int n = 0; n < items.size(); n++) {

            final int value = n;

            m = n;

            update(m, (items.size()));

            try {

                Platform.runLater(() -> {

                    container.getChildren().add(stackpanes.get(value));

                });

                Thread.sleep(100);

            } catch (InterruptedException exc) {

                System.out.println("" + exc);
            }
        }

    }

    public void startTask(List items) {
        // Create a Runnable
        Runnable task = () -> {
            runTask(items);
        };

        // Run the task in a background thread
        Thread backgroundThread = new Thread(task);
        // Terminate the running thread if the application exits
        backgroundThread.setDaemon(true);
        // Start the thread
        backgroundThread.start();
    }

    public void update(double newvalue, double size) {

        double progress = newvalue / size;

        
    }

}
