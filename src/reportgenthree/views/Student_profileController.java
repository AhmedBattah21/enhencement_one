/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Student_profileController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Circle image_holder;

    @FXML
    private TabPane tabpane;

    @FXML
    private Button btn_search;

    @FXML
    private JFXButton btn_fstatements;

    @FXML
    private VBox gridpane;

    @FXML
    private TextField txt_hint;

    @FXML
    private Tab default_tab;

    int count = 1;

    HBox box;

    ObservableList<Integer> tabs = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        btn_search.setOnAction(e -> get_data());

        place_inforbox("Welcome To Student Profile \n Enter Correct Adm No To Search");
        tabs.add(0);
        default_tab.setClosable(false);

    }

    public void get_data() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Student_profileController.class.getResource("StudentProfile_dataholder.fxml"));

        try {

            if (AppFuctions.StudentsClass.checkStudentTwo(txt_hint.getText())) {

                int tab_index = Integer.parseInt(txt_hint.getText());

                StackPane pane = loader.load();
                StudentProfile_dataholderController cc = loader.getController();
                cc.setAdmno(txt_hint.getText());

                cc.funnyMethod();
                Tab tab = new Tab("Student " + txt_hint.getText());

                tab.setContent(pane);

                tabpane.getTabs().add(tab);
                tabpane.getSelectionModel().select(tab);

            } else {

                place_inforbox("Sorry Student Not Found..");
                tabpane.getSelectionModel().select(default_tab);

            }

        } catch (IOException exc) {

        }

    }

    public void place_inforbox(String Message) {

        FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.BINOCULARS);
        icon.setGlyphSize(100);
        icon.setGlyphStyle("-fx-fill:skyblue");
        StackPane pane = add_inforbox(Message, icon);
        default_tab.setContent(pane);

    }

    public static StackPane add_inforbox(String Message, FontAwesomeIconView icon) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Student_profileController.class.getResource("Information_window.fxml"));
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

}
