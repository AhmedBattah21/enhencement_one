/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentExam_holdertitleController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane parent;

    @FXML
    private Label lbl_title;

    @FXML
    private Button btn_close;

    private VBox parent_vbox;

    String exam_title;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        btn_close.setOnAction(e -> Close());
    }

    public void setExam_title(String exam_title) {
        this.exam_title = exam_title;
        lbl_title.setText(exam_title);
    }

    public void setParent_vbox(VBox parent_vbox) {
        this.parent_vbox = parent_vbox;
    }

    public void Close() {

        VBox box = ((VBox) parent.getParent());

        ((VBox)box.getParent()).getChildren().remove(box);
    }

}
