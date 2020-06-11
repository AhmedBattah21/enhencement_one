/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Computer
 */
public class Student_dataholder {

    // In real life, you might want a connection pool here, though for
    // desktop applications a single connection often suffices:
    private Connection conn;
    String home = System.getProperty("user.home") + "/" + "Documents";
    private List<StackPane> items;

    public Student_dataholder() {

        conn = sqlDataBaseConnection.sqliteconnect();

    }

    public VBox getWidows(String hint) {

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode LIKE '%" + hint + "%' OR StudentName LIKE '%" + hint + "%' "
                + "OR YearOfStudy LIKE '%" + hint + "%' OR StudentCourse LIKE '%" + hint + "%' OR StudentCategory LIKE '%" + hint + "%'";
        items = new ArrayList<>();

        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setSpacing(5);

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                //rectangle for holding images
                File imagepath = new File(home + "/ReportGenThree/students/" + rst.getString("std_image"));
                Image im = new Image(imagepath.toURI().toString(), false);

                // results++;
                // lbl_count.setText("Found " + results + " Results");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Student_dataholder.class.getResource("Students_datadisplayer.fxml"));
                try {

                    StackPane stpane_one = loader.load();
                    Students_datadisplayerController controller = loader.getController();

                    controller.setAdm_number(rst.getString("Ccode") + "/" + rst.getString("StudentRegCode"));
                    controller.setStd_course(rst.getString("StudentCourse"));
                    controller.setStd_name(rst.getString("StudentName"));
                    controller.setStd_dor(rst.getString("DOR"));
                    controller.setStd_year(rst.getString("YearOfStudy"));
                    controller.setStd_category(rst.getString("StudentCategory"));
                    controller.setStd_image(imagepath);
                    controller.setStd_code(rst.getString("StudentRegCode"));

                    box.getChildren().add(stpane_one);

                    System.out.println("Added..");

                } catch (IOException ex) {

                    System.out.println("" + ex);
                }

            }

        } catch (SQLException exc) {

            System.out.println("" + exc);
        }

        return box;
    }

    public void shutdown() {
        try {
            conn.close();
        } catch (SQLException ex) {
            System.out.println("" + ex);
        }
    }

    public List<StackPane> getItems() {
        return items;
    }

    public void set_items(VBox box) {

        int num_items = items.size();

        for (int n = 0; n <= num_items; n++) {

            box.getChildren().add(n, items.get(n));
        }

    }

    public List getWidows_lists(String hint) {

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode LIKE '%" + hint + "%' OR StudentName LIKE '%" + hint + "%' "
                + "OR YearOfStudy LIKE '%" + hint + "%' OR StudentCourse LIKE '%" + hint + "%' OR StudentCategory LIKE '%" + hint + "%'";
        items = new ArrayList<>();

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                //rectangle for holding images
                File imagepath = new File(home + "/ReportGenThree/students/" + rst.getString("std_image"));
                Image im = new Image(imagepath.toURI().toString(), false);

                // results++;
                // lbl_count.setText("Found " + results + " Results");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Student_dataholder.class.getResource("Students_datadisplayer.fxml"));
                try {

                    StackPane stpane_one = loader.load();
                    Students_datadisplayerController controller = loader.getController();

                    controller.setAdm_number(rst.getString("Ccode") + "/" + rst.getString("StudentRegCode"));
                    controller.setStd_course(rst.getString("StudentCourse"));
                    controller.setStd_name(rst.getString("StudentName"));
                    controller.setStd_dor(rst.getString("DOR"));
                    controller.setStd_year(rst.getString("YearOfStudy"));
                    controller.setStd_category(rst.getString("StudentCategory"));
                    controller.setStd_image(imagepath);
                    controller.setStd_code(rst.getString("StudentRegCode"));

                    items.add(stpane_one);

                } catch (IOException ex) {

                    System.out.println("" + ex);
                }

            }

        } catch (SQLException exc) {

            System.out.println("" + exc);
        }

        return items;
    }

}
