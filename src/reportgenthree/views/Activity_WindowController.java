/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Activity_WindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private VBox container;
    
     @FXML
    private TextField txt_hint;

    @FXML
    private Button btn_search;

    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        add_activities();
    }
    
    

    public void add_activities() {

        container.getChildren().clear();
        String query = "";
        String gname = LoginscreenController.getGName();
        String user = LoginscreenController.getUserName();
        
        try {
            
            if(gname.equals("Academics Admin")){
                
             query = "SELECT * FROM Activities ORDER BY Activity_date";
            
            }else{
            
                query = "SELECT * FROM Activities WHERE Activity_user = '"+user+"' ORDER BY Activity_date";
            
            }
            
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            
            while (rst.next()) {
              
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Activity_WindowController.class.getResource("Activities_holder.fxml"));
                     StackPane pane = loader.load();
                    Activities_holderController cc = loader.getController();
                   
                   
                    cc.setActivity_date(rst.getString("Activity_date"));
                    cc.setActivity_details(rst.getString("Activity_desc"));
                    cc.setActivity_id(rst.getString("Activity_id"));
                    cc.setActivity_name(rst.getString("Activity_name"));
                   
                    container.getChildren().add(pane);
                    
               
            }
            
            
        } catch (SQLException | IOException exc) {
            
          System.out.println("Error "+exc);
        }

    }
    
    
 
    @FXML
    void check_activity() {

        container.getChildren().clear();
        String hint = txt_hint.getText();
        
        try {
            String query = "SELECT * FROM Activities WHERE Activity_user = '"+LoginscreenController.getUserName()+"' AND "
                    + "Activity_name LIKE '%"+hint+"%' OR Activity_desc LIKE '%"+hint+"%' OR Activity_date LIKE '%"+hint+"%'";
            
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {
              
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Activity_WindowController.class.getResource("Activities_holder.fxml"));
                     StackPane pane = loader.load();
                    Activities_holderController cc = loader.getController();
                   
                   
                    cc.setActivity_date(rst.getString("Activity_date"));
                    cc.setActivity_details(rst.getString("Activity_desc"));
                    cc.setActivity_id(rst.getString("Activity_id"));
                    cc.setActivity_name(rst.getString("Activity_name"));
                   
                    container.getChildren().add(pane);
                    
               
            }
        } catch (SQLException | IOException exc) {
            
          System.out.println("Error "+exc);
        }

        
    }

    @FXML
    void search_activity() {

    }

   
    

}
