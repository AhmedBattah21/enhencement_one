/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Dialogs.Datasaved_dialogController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class SemisterDurationSetterController implements Initializable {

    @FXML
    private Label lbl_day;

    @FXML
    private Label lbl_month;

    @FXML
    private Label lbl_year;

    @FXML
    private JFXDatePicker dp_t1start;

    @FXML
    private JFXDatePicker dp_t1end;

    @FXML
    private JFXDatePicker dp_t2start;

    @FXML
    private JFXDatePicker dp_t2end;

    @FXML
    private JFXDatePicker dp_t3start;

    @FXML
    private JFXDatePicker dp_t3end;

    @FXML
    private JFXButton btn_save;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        LocalDateTime date = LocalDateTime.now();

        lbl_day.setText("" + date.getDayOfMonth());
        lbl_month.setText(date.getMonth().toString());
        lbl_year.setText("" + date.getYear());

        dp_t1start.setValue(LocalDate.of(date.getYear(), Month.JANUARY, 1));

        dp_t1end.setValue(LocalDate.of(date.getYear(), Month.APRIL, Month.APRIL.maxLength()));

        dp_t2start.setValue(LocalDate.of(date.getYear(), Month.MAY, 1));

        dp_t2end.setValue(LocalDate.of(date.getYear(), Month.AUGUST, Month.AUGUST.maxLength()));

        dp_t3start.setValue(LocalDate.of(date.getYear(), Month.SEPTEMBER, 1));

        dp_t3end.setValue(LocalDate.of(date.getYear(), Month.DECEMBER, Month.DECEMBER.maxLength()));

    }

    public void set_dates() {

        int t1start = dp_t1start.getValue().getMonthValue();
        int t2start = dp_t2start.getValue().getMonthValue();
        int t3start = dp_t3start.getValue().getMonthValue();

        int t1end = dp_t1end.getValue().getMonthValue();
        int t2end = dp_t2end.getValue().getMonthValue();
        int t3end = dp_t3end.getValue().getMonthValue();

        String query_one = "UPDATE Semisters SET Start = '" + t1start + "' ,End = '" + t1end + "' WHERE SemisterId = 1";
        String query_two = "UPDATE Semisters SET Start = '" + t2start + "' ,End = '" + t2end + "' WHERE SemisterId = 2";
        String query_three = "UPDATE Semisters SET Start = '" + t3start + "' ,End = '" + t3end + "' WHERE SemisterId = 3";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st_1 = conn.createStatement();
            Statement st_2 = conn.createStatement();
            Statement st_3 = conn.createStatement();

            st_1.executeUpdate(query_one);
            st_2.executeUpdate(query_two);
            st_3.executeUpdate(query_three);

            FontAwesomeIconView error_icon = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
            error_icon.setGlyphSize(150);
            error_icon.setGlyphStyle("-fx-fill:skyblue");
            String title = "School Semister Duration Settings";
            String mymessage = "Great!!!! Dates Saved Successifully!!!!";
            openDialog(title, mymessage, error_icon);

            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);
        }

    }

    public void openDialog(String title, String message, FontAwesomeIconView icon) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();
            Label mylabel = new Label(title);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Academics_AddNewScoreController.class.getResource("Dialogs/Datasaved_dialog.fxml"));
            StackPane pane = loader.load();
            Datasaved_dialogController cc = loader.getController();
            cc.getLbl_infor().setText(message);
            cc.getLbl_icon().setGraphic(icon);

            content.setHeading(mylabel);

            content.setBody(pane);

            StackPane major_mypane = ReportGenThree.getMajor_stackpane();

            JFXDialog dlog = new JFXDialog(major_mypane, content, JFXDialog.DialogTransition.TOP);

            content.autosize();

            dlog.show();
        } catch (IOException ex) {
            Logger.getLogger(Academics_AddNewScoreController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
