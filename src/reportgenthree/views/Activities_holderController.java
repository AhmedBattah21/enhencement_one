
package reportgenthree.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Activities_holderController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Label lbl_activityid;

    @FXML
    private Label lbl_date;

    @FXML
    private Button btn_close;

    @FXML
    private TextArea lbl_description;
    
    @FXML
    private StackPane parent;
    
    private String activity_id;
    private String activity_name;
    private String activity_date;
    private String activity_details;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbl_description.setEditable(false);
    }   
    
     public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
        lbl_activityid.setText(activity_id);
    }
     
     public void set_area(){
     
         System.out.println("Area Has Been Set....");
     
     }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
        
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
        lbl_date.setText(activity_date);
    }

    public void setActivity_details(String activity_details) {
        this.activity_details = activity_details;
        lbl_description.setText(activity_details);
    }
    
    public void close_window(){
    
        ((VBox)parent.getParent()).getChildren().remove(parent);
    
    }
    
}
