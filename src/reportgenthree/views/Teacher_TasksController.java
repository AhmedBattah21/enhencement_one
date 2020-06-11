package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import reportgenthree.objects.Teacher_Tasks;

public class Teacher_TasksController implements Initializable {

    @FXML
    private TextField txt_taskname;

    @FXML
    private Label lbl_infor;

    @FXML
    private JFXButton btn_add;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private TableColumn col_code;

    @FXML
    private TableColumn col_taskname;
    
     @FXML
    private JFXCheckBox check_character;

    @FXML
    private TableView<Teacher_Tasks> table;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        col_taskname.setCellValueFactory(new PropertyValueFactory<>("task_name"));
        col_code.setCellValueFactory(new PropertyValueFactory<>("task_code"));

        get_tasks();

        table.setOnMouseClicked(e -> {

            if (!table.getSelectionModel().isEmpty()) {
                String code = table.getSelectionModel().getSelectedItem().getTask_code();
                txt_taskname.setText(code);
            }

        });
    }

    @FXML
    void add_task(ActionEvent event) {

        if (!txt_taskname.getText().isEmpty()) {

            String query  = "";
            if(check_character.isSelected()){
                
            query = "INSERT INTO TeacherTasks VALUES (null,'" + txt_taskname.getText() + "','com')";
            
            }else{
            
            query = "INSERT INTO TeacherTasks VALUES (null,'" + txt_taskname.getText() + "','uni')";
            }
            
            
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            try {

                Statement st = conn.createStatement();

                int n = 0;
                if (!check_task(txt_taskname.getText())) {

                    n = st.executeUpdate(query);

                }

                if (n >= 1) {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE);
                    icon.setGlyphSize(20);
                    icon.setGlyphStyle("-fx-fill:seagreen");
                    lbl_infor.setGraphic(icon);
                    get_tasks();
                } else {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                    icon.setGlyphSize(20);
                    icon.setGlyphStyle("-fx-fill:red");
                    lbl_infor.setGraphic(icon);

                }
                conn.close();

            } catch (SQLException exc) {

                System.out.println("Error " + exc);
            }

        } else {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphSize(20);
            icon.setGlyphStyle("-fx-fill:red");
            lbl_infor.setGraphic(icon);
        }
    }

    @FXML
    void remove_task(ActionEvent event) {

        if (!txt_taskname.getText().isEmpty()) {

            String query = "DELETE FROM TeacherTasks WHERE Task_Id = '" + txt_taskname.getText() + "'";
            Connection conn = sqlDataBaseConnection.sqliteconnect();
            try {

                Statement st = conn.createStatement();
                int n = st.executeUpdate(query);
                if (n >= 1) {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE);
                    icon.setGlyphSize(20);
                    icon.setGlyphStyle("-fx-fill:seagreen");
                    lbl_infor.setGraphic(icon);
                    get_tasks();
                } else {

                    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
                    icon.setGlyphSize(20);
                    icon.setGlyphStyle("-fx-fill:red");
                    lbl_infor.setGraphic(icon);

                }
                conn.close();

            } catch (SQLException exc) {

                System.out.println("Error " + exc);
            }

        } else {

            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
            icon.setGlyphSize(20);
            icon.setGlyphStyle("-fx-fill:red");
            lbl_infor.setGraphic(icon);
        }
    }

    public void get_tasks() {

        String query = "SELECT * FROM TeacherTasks";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<Teacher_Tasks> data = FXCollections.observableArrayList();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                String code = rst.getString("Task_Id");
                String name = rst.getString("TaskName");

                data.add(new Teacher_Tasks(code, name));

            }

            table.setItems(data);
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

    }

    public Boolean check_task(String task) {

        boolean result = false;
        String query = "SELECT * FROM TeacherTasks WHERE TaskName = '" + task + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            result = rst.next();
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return result;
    }

}
