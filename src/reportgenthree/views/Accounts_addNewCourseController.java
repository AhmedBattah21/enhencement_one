/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import reportgenthree.objects.CoursesClass;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Connection.sqlDataBaseConnection;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

public class Accounts_addNewCourseController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXButton btn_close;

    @FXML
    private JFXTextField txt_cname;

    @FXML
    private JFXButton btn_addcourse;

    @FXML
    private JFXButton btn_remove;

    @FXML
    private TableView table;

    @FXML
    private TableColumn column_id;

    @FXML
    private TableColumn column_cname;

    @FXML
    private TableColumn column_units;

    @FXML
    private StackPane mypane;

    @FXML
    private JFXTextField txt_abriviation;

    @FXML
    private JFXTextField txt_units;

    final ObservableList<CoursesClass> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        column_cname.setCellValueFactory(new PropertyValueFactory<>("coursename"));
        column_id.setCellValueFactory(new PropertyValueFactory<>("Abriviation"));
        column_units.setCellValueFactory(new PropertyValueFactory<>("units"));
        addCoursesInTable();

        txt_units.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,2}([\\.]\\d{0,5})?")) {
                    txt_units.setText(oldValue);
                    Toolkit.getDefaultToolkit().beep();
                }
            }
        });

        txt_abriviation.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    txt_abriviation.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

                }
            }
        });

        txt_cname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txt_cname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

            }
        });

    }

    public void close() {

        Stage stage = (Stage) btn_close.getScene().getWindow();
        stage.close();

    }

    public void addCourse() {

        if (!txt_cname.getText().trim().isEmpty()) {

            String coursename = txt_cname.getText();

            if (!txt_abriviation.getText().trim().isEmpty()) {

                String coursehead = txt_abriviation.getText();

                if (!txt_units.getText().isEmpty()) {

                    String units = txt_units.getText();

                    String query = "INSERT INTO CourseTable VALUES ('" + coursename + "','" + coursehead + "','" + units + "')";

                    Connection conn = sqlDataBaseConnection.sqliteconnect();

                    if (!checkCourse(coursename)) {

                        try {

                            Statement st = conn.createStatement();
                            st.executeUpdate(query);

                            openDialog("Course Module Saved.. \n " + coursename + " " + coursehead);

                            data.clear();
                            addCoursesInTable();

                            conn.close();

                        } catch (SQLException exc) {

                            openDialog("Sorry The Is An Error " + exc);
                        }

                    } else {

                        openDialog("Course Alread Exist");
                    }

                } else {

                    openDialog("Enter Number Of Units..");

                }

            } else {

                openDialog("Enter Head Of The Course..");

            }

        } else {

            openDialog("Enter Course Name..");

        }

    }

    public void edit_numunits() {

        String units = txt_units.getText();
        String abrv = txt_abriviation.getText();
        
        if(!txt_abriviation.getText().isEmpty() && !txt_abriviation.getText().isEmpty() && checkCourseAbr(abrv)){

        String query = "UPDATE CourseTable SET Units ='" + units + "' WHERE CourseAbreviation = '" + abrv + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
            addCoursesInTable();

        } catch (SQLException exc) {

            System.out.println("Error "+exc);
        }
        
        }

    }

    public void remove() {

        if (!txt_abriviation.getText().trim().isEmpty()) {
            String abr = txt_abriviation.getText();

            if (checkCourseAbr(abr)) {

                String query = "DELETE FROM CourseTable WHERE CourseAbreviation = '" + abr + "' ";

                Connection conn = sqlDataBaseConnection.sqliteconnect();

                try {

                    Statement st = conn.createStatement();
                    st.executeUpdate(query);
                    conn.close();
                    openDialog("Course Deleted..");

                    addCoursesInTable();

                } catch (SQLException exc) {

                    openDialog("Sorry There Is An Error .." + exc);

                }

            } else {

                openDialog("No Search Course Found..");
            }

        } else {

            openDialog("Enter Course Abriviation...");

        }

    }

    public void openDialog(String message) {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Label("P.E.R.M.S"));
        content.setBody(new Text(message));

        JFXDialog dlog = new JFXDialog(mypane, content, JFXDialog.DialogTransition.CENTER);

        JFXButton buttonCancel = new JFXButton("Exit");

        buttonCancel.setOnAction(e -> dlog.close());

        content.setActions(buttonCancel);

        dlog.show();

    }

    public Boolean checkCourse(String course) {

        boolean checker = false;

        String query = "SELECT * FROM CourseTable WHERE CourseName = '" + course + "'";

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

    public Boolean checkCourseAbr(String abr) {

        boolean checker = false;

        String query = "SELECT * FROM CourseTable WHERE CourseAbreviation = '" + abr + "'";

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

    public void addCoursesInTable() {

        data.clear();

        String query = "SELECT * FROM CourseTable";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USERS);

            while (rst.next()) {

                String coursename = rst.getString("CourseName");
                String abriviation = rst.getString("CourseAbreviation");
                String units = rst.getString("Units");

                data.add(new CoursesClass(coursename, abriviation, units));
            }

            table.setItems(data);
            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);
        }

    }
}
