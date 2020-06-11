/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.FeesViews;

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
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class NewProgramFormController implements Initializable {

    @FXML
    private Label lbl_title;
    @FXML
    private TextField txt_programname;

    @FXML
    private Label one;

    @FXML
    private TextField termone_fee;

    @FXML
    private Label two;

    @FXML
    private TextField termtwo_fee;

    @FXML
    private Label three;

    @FXML
    private TextField termthree_fee;

    @FXML
    private Label four;

    @FXML
    private JFXCheckBox check_one;

    @FXML
    private JFXCheckBox check_two;

    @FXML
    private JFXButton btn_addprogram;

    FontAwesomeIconView icon_error = new FontAwesomeIconView(FontAwesomeIcon.TIMES);
    FontAwesomeIconView icon_infor = new FontAwesomeIconView(FontAwesomeIcon.THUMBS_UP);
    FontAwesomeIconView icon_tick = new FontAwesomeIconView(FontAwesomeIcon.CHECK);
    FontAwesomeIconView icon_times = new FontAwesomeIconView(FontAwesomeIcon.TIMES);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        icon_error.setGlyphStyle("-fx-fill:red");
        icon_error.setGlyphSize(15);

        icon_infor.setGlyphStyle("-fx-fill:skyblue");
        icon_infor.setGlyphSize(15);

        icon_tick.setGlyphStyle("-fx-fill:orange");
        icon_tick.setGlyphSize(50);

        icon_times.setGlyphStyle("-fx-fill:red");
        icon_times.setGlyphSize(50);

        check_one.setSelected(true);

        check_one.setOnMouseClicked(e -> {

            if (check_two.isSelected()) {

                check_two.setSelected(false);
            }

            if (!check_one.isSelected()) {

                check_two.setSelected(true);
            }

        });

        check_two.setOnMouseClicked(e -> {

            if (check_one.isSelected()) {

                check_one.setSelected(false);
            }

            if (!check_two.isSelected()) {

                check_one.setSelected(true);
            }

        });

        txt_programname.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txt_programname.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

            }
        });

        termone_fee.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                termone_fee.setText(oldValue);
            }
        });

        termtwo_fee.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                termtwo_fee.setText(oldValue);
            }
        });

        termthree_fee.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d{0,7}([\\.]\\d{0,5})?")) {
                termthree_fee.setText(oldValue);
            }
        });

    }

    public void add_program() {

        if (!txt_programname.getText().isEmpty()) {
            one.setGraphic(null);
            String program = txt_programname.getText().toUpperCase();

            if (!termone_fee.getText().isEmpty()) {
                two.setGraphic(null);
                String t1 = termone_fee.getText().toUpperCase();

                if (!termtwo_fee.getText().isEmpty()) {
                    three.setGraphic(null);
                    String t2 = termtwo_fee.getText().toUpperCase();

                    if (!termthree_fee.getText().isEmpty()) {
                        four.setGraphic(null);
                        String t3 = termthree_fee.getText().toUpperCase();

                        String query = "";

                        if (check_one.isSelected()) {

                            query = "INSERT INTO Programs VALUES(null,'" + program + "','" + t1 + "','" + t2 + "','" + t3 + "','onetime')";

                        } else if (check_two.isSelected()) {

                            query = "INSERT INTO Programs VALUES(null,'" + program + "','" + t1 + "','" + t2 + "','" + t3 + "','threetime')";
                        }
                        Connection conn = sqlDataBaseConnection.sqliteconnect();

                        try {

                            if (check_program(program)) {

                                lbl_title.setGraphic(icon_times);

                            } else {
                                Statement st = conn.createStatement();
                                int value = st.executeUpdate(query);

                                if (value >= 1) {

                                    lbl_title.setGraphic(icon_tick);
                                }

                            }
                            conn.close();

                        } catch (SQLException exc) {

                            System.out.println("Error " + exc);
                        }

                    } else {

                        four.setGraphic(icon_error);
                        termthree_fee.requestFocus();

                    }
                } else {

                    three.setGraphic(icon_error);
                    termtwo_fee.requestFocus();

                }

            } else {

                two.setGraphic(icon_error);
                termone_fee.requestFocus();

            }

        } else {

            one.setGraphic(icon_error);
            txt_programname.requestFocus();

        }

    }

    public Boolean update_program(String program_id) {

        boolean result = false;
        if (!txt_programname.getText().isEmpty()) {
            one.setGraphic(null);
            String program = txt_programname.getText().toUpperCase();

            if (!termone_fee.getText().isEmpty()) {
                two.setGraphic(null);
                String t1 = termone_fee.getText().toUpperCase();

                if (!termtwo_fee.getText().isEmpty()) {
                    three.setGraphic(null);
                    String t2 = termtwo_fee.getText().toUpperCase();

                    if (!termthree_fee.getText().isEmpty()) {
                        four.setGraphic(null);
                        String t3 = termthree_fee.getText().toUpperCase();

                        String query = "";

                        if (check_one.isSelected()) {

                            query = "UPDATE Programs SET Program_Name = '" + program + "',T1_Amount = '" + t1 + "',T2_Amount = '" + t2 + "',"
                                    + "T3_Amount = '" + t3 + "',Program_plan = 'onetime' WHERE "
                                    + "Program_Id = '" + program_id + "'";
                        } else {

                            query = "UPDATE Programs SET Program_Name = '" + program + "',T1_Amount = '" + t1 + "',T2_Amount = '" + t2 + "',"
                                    + "T3_Amount = '" + t3 + "',Program_plan = 'threetime' WHERE "
                                    + "Program_Id = '" + program_id + "'";
                        }

                        Connection conn = sqlDataBaseConnection.sqliteconnect();

                        try {

                            Statement st = conn.createStatement();
                            int value = st.executeUpdate(query);

                            if (value >= 1) {

                                result = true;
                            }

                            conn.close();

                        } catch (SQLException exc) {

                            System.out.println("Error " + exc);
                        }

                    } else {

                        four.setGraphic(icon_error);
                        termthree_fee.requestFocus();

                    }
                } else {

                    three.setGraphic(icon_error);
                    termtwo_fee.requestFocus();

                }

            } else {

                two.setGraphic(icon_error);
                termone_fee.requestFocus();

            }

        } else {

            one.setGraphic(icon_error);
            txt_programname.requestFocus();

        }

        return result;

    }

    public Boolean check_program(String Program_Name) {

        boolean result = false;

        String query = "SELECT * FROM Programs WHERE Program_Name = '" + Program_Name + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            result = rst.next();

            conn.close();
        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }
        return result;
    }

    public JFXButton getBtn_addprogram() {
        return btn_addprogram;
    }

    public void setBtn_addprogram(JFXButton btn_addprogram) {
        this.btn_addprogram = btn_addprogram;
    }

    public TextField getTxt_programname() {
        return txt_programname;
    }

    public void setTxt_programname(TextField txt_programname) {
        this.txt_programname = txt_programname;
    }

    public TextField getTermone_fee() {
        return termone_fee;
    }

    public void setTermone_fee(TextField termone_fee) {
        this.termone_fee = termone_fee;
    }

    public TextField getTermtwo_fee() {
        return termtwo_fee;
    }

    public void setTermtwo_fee(TextField termtwo_fee) {
        this.termtwo_fee = termtwo_fee;
    }

    public TextField getTermthree_fee() {
        return termthree_fee;
    }

    public void setTermthree_fee(TextField termthree_fee) {
        this.termthree_fee = termthree_fee;
    }

}
