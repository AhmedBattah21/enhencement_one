/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import reportgenthree.objects.Teachers_AvailableteachersClass;
import reportgenthree.views.Dialogs.Dialogs_functions;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Teachers_AvailableteachersController implements Initializable {

    @FXML
    private Label lbl_school;

    @FXML
    private Label lbl_title;

    @FXML
    private TableView<Teachers_AvailableteachersClass> table;

    @FXML
    private TableColumn cl_number;

    @FXML
    private TableColumn col_name;

    @FXML
    private TableColumn col_idnumber;

    @FXML
    private TableColumn col_task;

    @FXML
    private TableColumn cal_dept;

    @FXML
    private TableColumn col_initials;

    @FXML
    private JFXButton btn_print;

    @FXML
    private StackPane stackpane;
    private StackPane   parent_stackpane;

    final ObservableList<Teachers_AvailableteachersClass> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cl_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("stname"));
        col_initials.setCellValueFactory(new PropertyValueFactory<>("initials"));
        col_task.setCellValueFactory(new PropertyValueFactory<>("task"));
        cal_dept.setCellValueFactory(new PropertyValueFactory<>("dept"));
        col_idnumber.setCellValueFactory(new PropertyValueFactory<>("IdNumber"));

        lbl_school.setText(SystemNameSetterController.getSname());
        lbl_title.setText(SystemNameSetterController.getbox());

        get_data();
    }

    public void delete() {

        String t_id = table.getSelectionModel().getSelectedItem().getIdNumber();
        String query = "DELETE FROM AssignedUnits WHERE AssignedUnits.Teacher_IdNumber = '" + t_id + "' ";

        String query_two = "DELETE FROM Teacher WHERE Teacher.Teacher_IdNumber ='" + t_id + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            String result = Dialogs_functions.get_custom_confirm("Remove " + t_id + " And All Related Information?");
            
           

            if (result.equals("continue")) {
                Statement st = conn.createStatement();
                Statement st_two = conn.createStatement();

                int rows = st.executeUpdate(query);
                int rows_two = st.executeUpdate(query_two);

                get_data();

            }

            conn.close();
        } catch (SQLException exc) {

            System.out.println(exc);
        }

    }

    public void edit() {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Teachers_AvailableteachersController.class.getResource("Teachers_AddnewTeacher.fxml"));
            StackPane pane = loader.load();

            Teachers_AddnewTeacherController cc = loader.getController();
            Teachers_AvailableteachersClass data = table.getSelectionModel().getSelectedItem();
            cc.getCmb_department().setValue(data.getDept());
            cc.getCmb_task().setValue(data.getTask());
            cc.getTxt_initials().setText(data.getInitials());
            cc.getTxt_name().setText(data.getStname());
            cc.getTxt_number().setText(data.getIdNumber());
            cc.set_btnsave_toedit(data.getIdNumber());
            cc.setCc(this);

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(parent_stackpane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {

            System.out.println(ex);
        }

    }

    public void get_data() {

        String query = "SELECT * FROM Teacher";
        data.clear();
        table.getItems().clear();

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            int number = 0;
            while (rst.next()) {

                String name = rst.getString("Teacher_Name");
                String phone = rst.getString("Teacher_IdNumber");
                String dept = rst.getString("Teacher_Department");
                String initials = rst.getString("Teacher_Initials");
                String task = rst.getString("Teacher_Task");
                number++;

                data.add(new Teachers_AvailableteachersClass(name, phone, initials, dept, number, task));
            }
            table.setItems(data);

            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error Erick " + exc);
        }

    }

    public StackPane getParent_stackpane() {
        return parent_stackpane;
    }

    public void setParent_stackpane(StackPane parent_stackpane) {
        this.parent_stackpane = parent_stackpane;
    }

   

}
