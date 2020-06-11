/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationViews;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Information_windowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lbl_icon;

    @FXML
    private Label lbl_infor;

    @FXML
    private FontAwesomeIconView form_icon;

    private JFXTabPane Parent_tab;

    private String infor;
    
     @FXML
    private Button btn_openfiles_one;

    @FXML
    private Button btn_openfiles_two;

    FontAwesomeIcon icon;

    String style = "-fx-border-color: white;"
            + "     -fx-background-color: rgb(255.0, 255.0, 255.0);"
            + "    -fx-effect: dropshadow(gaussian, rgb(0.0, 0.0, 0.0, 0.15), 6.0, 0.5, 0.0, 1.5);"
            + "    -fx-padding: 5 5 5 5;"
            + "    -fx-border-radius: 5 5 5 5;"
            + "    -fx-background-radius: 5 5 5 5;";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setInfor(String infor) {
        this.infor = infor;
        lbl_infor.setText(infor);
    }

    public void setForm_icon(FontAwesomeIconView form_icon) {

        lbl_icon.setGraphic(form_icon);
    }

    @FXML
    void Open_files(ActionEvent event) {

        VBox default_fileholder = new VBox();
        default_fileholder.setStyle(style);
        Tab tab = new Tab("Examination Files");

        String query = "SELECT * FROM exam_2017 GROUP BY Year,Syear,Exam_Term ORDER BY Year,Syear";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        HBox box = new HBox();
        HBox.setHgrow(box, Priority.ALWAYS);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setSpacing(40);
        
        ContextMenu menu = new ContextMenu();
        MenuItem item1 = new MenuItem("Examination Files");
        SeparatorMenuItem sp1 = new SeparatorMenuItem();
        MenuItem item2 = new MenuItem("Close Examination Files Tab");
        menu.getItems().addAll(item1,sp1,item2);
        
        default_fileholder.setOnContextMenuRequested((ContextMenuEvent eve) -> {
            menu.show(default_fileholder, eve.getScreenX(), eve.getScreenY());
        });
        
         item2.setOnAction((ActionEvent eve) -> {

            Parent_tab.getTabs().remove(tab);
            
        });
        
        

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            int count = 1;

            while (rst.next()) {

                String exam_year = rst.getString("Year");
                String exam_term = rst.getString("Exam_Term");
                String std_year = rst.getString("Syear");

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Examination_viewsController.class.getResource("Examination_dialogone.fxml"));

                StackPane pane = loader.load();
                Examination_dialogoneController cc = loader.getController();
                cc.setExam_term(exam_term);
                cc.setExam_year(exam_year);
                cc.setStd_year(std_year);
                cc.setParentTab(Parent_tab);

                box.getChildren().add(pane);

                if (count == 4) {

                    default_fileholder.getChildren().add(box);
                    box = new HBox();
                    HBox.setHgrow(box, Priority.ALWAYS);
                    box.setSpacing(40);
                    box.setAlignment(Pos.CENTER_LEFT);
                    count = 1;
                }

                count++;
            }

            default_fileholder.getChildren().add(box);
            ScrollPane spane = new ScrollPane(default_fileholder);
            spane.setFitToHeight(true);
            spane.setFitToWidth(true);
            
            tab.setContent(spane);
            Parent_tab.getTabs().add(tab);
            Parent_tab.getSelectionModel().select(tab);

            conn.close();
        } catch (SQLException exc) {

        } catch (IOException ex) {

            System.out.println(" Error" + ex);
        }

    }

    public JFXTabPane getParent_tab() {
        return Parent_tab;
    }

    public void setParent_tab(JFXTabPane Parent_tab) {
        this.Parent_tab = Parent_tab;
    }

    public Button getBtn_openfiles_one() {
        return btn_openfiles_one;
    }

    public void setBtn_openfiles_one(Button btn_openfiles_one) {
        this.btn_openfiles_one = btn_openfiles_one;
    }

    public Button getBtn_openfiles_two() {
        return btn_openfiles_two;
    }

    public void setBtn_openfiles_two(Button btn_openfiles_two) {
        this.btn_openfiles_two = btn_openfiles_two;
    }

    
    

}
