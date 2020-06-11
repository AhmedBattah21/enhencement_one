
package reportgenthree.views;

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
import com.jfoenix.controls.JFXCheckBox;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import reportgenthree.objects.UnitsClass;


public class Accounts_addNewUnitController implements Initializable {

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
    private StackPane mypane;

    @FXML
    private JFXCheckBox check_box;

    @FXML
    private TableColumn column_property;

    @FXML
    private JFXTextField txt_abriviation;

    final ObservableList<UnitsClass> data = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        column_cname.setCellValueFactory(new PropertyValueFactory<>("unitname"));
        column_property.setCellValueFactory(new PropertyValueFactory<>("property"));
        column_id.setCellValueFactory(new PropertyValueFactory<>("unitcode"));
        addCoursesInTable();

    }

    

    public void addCourse() {

        if (!txt_cname.getText().trim().isEmpty()) {

            String coursename = txt_cname.getText();

            if (!txt_abriviation.getText().trim().isEmpty()) {

                String coursehead = txt_abriviation.getText();
                String query = "";

                if (check_box.isSelected()) {

                    query = "INSERT INTO UnitsTable VALUES ('" + coursename + "','" + coursehead + "','com')";

                } else {

                    query = "INSERT INTO UnitsTable VALUES ('" + coursename + "','" + coursehead + "','uni')";

                }

                Connection conn = sqlDataBaseConnection.sqliteconnect();

                if (!checkCourse(coursename)) {

                    try {

                        Statement st = conn.createStatement();
                        st.executeUpdate(query);

                        openDialog("Unit  Saved.. \n " + coursename + " " + coursehead);

                        data.clear();
                        addCoursesInTable();

                        conn.close();

                    } catch (SQLException exc) {

                        openDialog("Sorry The Is An Error " + exc);
                    }

                } else {

                    openDialog("Unit Alread Exist");
                }

            } else {

                openDialog("Enter Code Of The Unit..");

            }

        } else {

            openDialog("Enter Unit Name..");

        }

    }

    public void remove() {

        if (!txt_abriviation.getText().trim().isEmpty()) {
            String abr = txt_abriviation.getText();

            if (checkCourseAbr(abr)) {

                String query = "DELETE FROM UnitsTable WHERE UnitCode = '" + abr + "' ";

                Connection conn = sqlDataBaseConnection.sqliteconnect();

                try {

                    Statement st = conn.createStatement();
                    st.executeUpdate(query);
                    conn.close();
                    openDialog("Unit Deleted..");

                    addCoursesInTable();

                } catch (SQLException exc) {

                    openDialog("Sorry There Is An Error .." + exc);

                }

            } else {

                openDialog("No Search Unit Found..");
            }

        } else {

            openDialog("Enter Unit Code...");

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

        String query = "SELECT * FROM UnitsTable WHERE UnitName = '" + course + "'";

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

        String query = "SELECT * FROM UnitsTable WHERE UnitCode = '" + abr + "'";

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

        String query = "SELECT * FROM UnitsTable";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.USERS);

            while (rst.next()) {

                String coursename = rst.getString("UnitName");
                String abriviation = rst.getString("UnitCode");
                String property = rst.getString("Property");

                data.add(new UnitsClass(coursename, abriviation,property));
            }

            table.setItems(data);
            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);
        }

    }
}
