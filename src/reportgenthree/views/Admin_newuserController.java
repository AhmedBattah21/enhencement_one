/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import Connection.sqlDataBaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import reportgenthree.objects.users_object;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Admin_newuserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField txt_code;

    @FXML
    private JFXComboBox<?> cmb_groups;

    @FXML
    private JFXButton btn_add;

    @FXML
    private Label lbl_infor;

    @FXML
    private TableView table;

    @FXML
    private TableColumn cl_username;

    @FXML
    private TableColumn col_regcode;

    @FXML
    private TableColumn col_status;

    @FXML
    private JFXTextField txt_regcodeTwo;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private JFXButton btn_block;
    
    @FXML
    private JFXButton btn_close;

    @FXML
    private Label lbl_inforTwo;
    
    @FXML
    private JFXButton btn_unblock;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lbl_infor.setText("");
        ObservableList groups = FXCollections.observableArrayList();
         groups.addAll("Academics Admin","Finance Admin","Teacher");
        cmb_groups.setItems(groups);
        
        btn_block.setOnAction(e -> removeUser(btn_block.getAccessibleText()));
        btn_remove.setOnAction(e -> removeUser(btn_remove.getAccessibleText()));
        btn_unblock.setOnAction(e -> removeUser(btn_unblock.getAccessibleText()));
        
        col_regcode.setCellValueFactory(new PropertyValueFactory<>("regcode"));
        col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        cl_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        
        addMembers();
        
    }

    public void add() {

        if (!txt_code.getText().equals("")) {

            String code = txt_code.getText();

            if (!cmb_groups.getSelectionModel().isEmpty()) {

                String group = cmb_groups.getSelectionModel().getSelectedItem().toString();

                String query = "INSERT INTO users VALUES ('','','','" + code + "','" + group + "','','')";

                if(!checkCodes(code)){
                    
                    try {

                    Connection conn = sqlDataBaseConnection.sqliteconnect();
                    Statement st = conn.createStatement();
                    st.executeUpdate(query);
                    lbl_infor.setText("Space Have Been Created!!");
                    conn.close();

                } catch (SQLException exc) {

                    System.out.println("" + exc);

                }
                
                }else{
                
                lbl_infor.setText("Registration Code Exist...F");
                }

            } else {
                lbl_infor.setText("Select User Group");

            }

        } else {
            lbl_infor.setText("Insert Code");

        }

    }

    public void removeUser(String text) {

        if (!txt_regcodeTwo.getText().equals("")) {

            String code = txt_regcodeTwo.getText();

            if (checkCodes(code)) {

                String query = "";

                if (text.equals("remove")) {

                    query = "DELETE FROM users WHERE RegCode = '" + code + "'";

                } else if (text.equals("block")) {

                    query = "UPDATE users SET status = 'blocked' WHERE RegCode = '" + code + "'";

                }else if(text.equals("unblock")){
                
                    
                    query = "UPDATE users SET status = 'ok' WHERE RegCode = '" + code + "'";
                
                }

                if (!query.equals("")) {

                    Connection conn = sqlDataBaseConnection.sqliteconnect();

                    try {

                        Statement st = conn.createStatement();
                        st.executeUpdate(query);
                        conn.close();
                        
                        lbl_inforTwo.setText("Action "+text+" Done!!.");

                    } catch (SQLException exc) {

                        System.out.println("" + exc);
                    }

                } else {

                    lbl_inforTwo.setText("Nothing Will Be Done..");
                }

            } else {

                lbl_inforTwo.setText("Code Does Not Exist..");

            }

        } else {

            lbl_inforTwo.setText("Please Insert Reg Code..");

        }

    }

    public Boolean checkCodes(String regcode) {

        boolean checker = false;

        String query = "SELECT * FROM users WHERE RegCode = '" + regcode + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                checker = true;
            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);

        }

        return checker;

    }
    
    public void addMembers(){
        
    final ObservableList<users_object> data = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM users";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USERS);
            
            while(rst.next()){
            
                String username = rst.getString("UserName");
                String code = rst.getString("RegCode");
                String status = rst.getString("status");
                
            
                data.add(new users_object(username,code,status));
            }
        
            table.setItems(data);
            conn.close();
            
        }catch(SQLException exc){
        
        System.out.println(""+exc);
        }
    
    
    
    }
    
    public void close(){
    
    Stage stage = (Stage)btn_close.getScene().getWindow();
    stage.close();
    
    }

}
