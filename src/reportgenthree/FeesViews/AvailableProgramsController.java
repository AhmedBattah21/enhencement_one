/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

import Connection.sqlDataBaseConnection;
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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Dialogs.Dialogs_functions;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class AvailableProgramsController implements Initializable {

    @FXML
    private TableView<AvailableProgramsClass> table;

    @FXML
    private TableColumn col_pname;

    @FXML
    private TableColumn col_t1;

    @FXML
    private TableColumn col_t2;

    @FXML
    private TableColumn col_t3;

    @FXML
    private TableColumn col_total;
    
     @FXML
    private TableColumn col_plan;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        col_pname.setCellValueFactory(new PropertyValueFactory<>("pname"));
        col_t1.setCellValueFactory(new PropertyValueFactory<>("t1"));
        col_t2.setCellValueFactory(new PropertyValueFactory<>("t2"));
        col_t3.setCellValueFactory(new PropertyValueFactory<>("t3"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("total"));
        col_plan.setCellValueFactory(new PropertyValueFactory<>("pplan"));

        getData();

        ContextMenu menu = new ContextMenu();

        MenuItem item = new MenuItem("Available Programs Menu");
        MenuItem item_one = new MenuItem("Remove Program");
        MenuItem item_two = new MenuItem("Edit Program");
        MenuItem item_three = new MenuItem("Refresh Programs");

        SeparatorMenuItem sp1 = new SeparatorMenuItem();
        SeparatorMenuItem sp2 = new SeparatorMenuItem();
        SeparatorMenuItem sp3 = new SeparatorMenuItem();

        menu.getItems().addAll(item, sp1, item_one, sp2, item_two, sp3, item_three);

        table.setOnContextMenuRequested((event) -> {

            menu.show(table, event.getScreenX(), event.getScreenY());

        });

        item_one.setOnAction(e -> {

            String value = Dialogs_functions.get_custom_confirm("Are You Sure You Want To Remove This Program?");

            if (value.equals("continue")) {

                remove();
            }

        });

        item_three.setOnAction(e -> {

            getData();

        });

        item_two.setOnAction(e -> {

            if (!table.getSelectionModel().isEmpty()) {

                AvailableProgramsClass data = table.getSelectionModel().getSelectedItem();
                Edit_Program(data);
            }

        });

    }

    public void getData() {

        ObservableList<AvailableProgramsClass> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM Programs";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                String pname = rst.getString("Program_Name");
                String t1 = rst.getString("T1_Amount");
                String t2 = rst.getString("T2_Amount");
                String t3 = rst.getString("T3_Amount");
                String pid = rst.getString("Program_Id");
                String pplan = rst.getString("Program_plan");

                if (pplan.equals("onetime")) {

                    pplan = "One Time Program";
                } else {

                    pplan = "Three Time Program";

                }

                int total = (Integer.parseInt(t1) + Integer.parseInt(t2) + Integer.parseInt(t3));

                data.add(new AvailableProgramsClass(pname, t1, t2, t3, total, pid, pplan));

            }

            table.setItems(data);
            conn.close();
        } catch (SQLException exc) {

        }

    }

    public void remove() {

        if (!table.getSelectionModel().isEmpty()) {

            String id = table.getSelectionModel().getSelectedItem().getPid();
            String query = "DELETE FROM Programs WHERE Program_id =  '" + id + "'";
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            try {

                Statement st = conn.createStatement();
                st.executeQuery(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" " + exc);
            }

        }
    }

    public void Edit_Program(AvailableProgramsClass data) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            JFXDialog dlog = new JFXDialog();
            Label mylabel = new Label("School Programs");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(AvailableProgramsController.class.getResource("NewProgramForm.fxml"));
            StackPane pane = loader.load();
            NewProgramFormController cc = loader.getController();

            cc.getBtn_addprogram().setText("Update Program");
            cc.getTxt_programname().setText(data.getPname());
            cc.getTermone_fee().setText(data.getT1());
            cc.getTermtwo_fee().setText(data.getT2());
            cc.getTermthree_fee().setText(data.getT3());
            cc.getBtn_addprogram().setOnAction(e -> {

                boolean result = cc.update_program(data.getPid());

                if (result) {

                    getData();
                    dlog.close();

                }

            });

            content.setHeading(mylabel);

            content.setBody(pane);

            StackPane mypane = ReportGenThree.getMajor_stackpane();

            dlog.setDialogContainer(mypane);
            dlog.setContent(content);
            dlog.setTransitionType(JFXDialog.DialogTransition.TOP);

            content.autosize();

            dlog.show();
        } catch (IOException exc) {

            System.out.println(" " + exc);
        }

    }
    
    
    public void add_program(){
    
       ReportGenThree.Fee_NewProgramForm();
    
    }

}
