/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import AppFuctions.Functions;
import Connection.sqlDataBaseConnection;
import reportgenthree.objects.SearchedStudent;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import reportgenthree.ReportGenThree;
import reportgenthree.views.Dialogs.Dialogs_functions;
import reportgenthree.views.Dialogs.Studentpropert_texteditorController;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class StudentsListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public JFXTextField hint;
    @FXML
    private TableView<SearchedStudent> table;

    @FXML
    private TableColumn number;

    @FXML
    private TableColumn name;

    @FXML
    private TableColumn admnumber;

    @FXML
    private TableColumn col_course;

    @FXML
    private TableColumn col_category;

    @FXML
    private TableColumn stream;

    @FXML
    private TableColumn col_gender;

    @FXML
    private TableColumn col_date;

    @FXML
    private StackPane stackpane;

    @FXML
    private TableColumn col_image;
    public Label infor;
    public JFXButton pdfDoc;
    private String query = "";

    private final static int rowsPerPage = 25;
    public BorderPane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        name.setCellValueFactory(new PropertyValueFactory<>("stname"));
        admnumber.setCellValueFactory(new PropertyValueFactory<>("admnumber"));
        stream.setCellValueFactory(new PropertyValueFactory<>("classname"));
        number.setCellValueFactory(new PropertyValueFactory<>("Count"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("std_category"));
        col_course.setCellValueFactory(new PropertyValueFactory<>("std_course"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("std_date"));
        col_gender.setCellValueFactory(new PropertyValueFactory<>("std_gender"));
        col_image.setCellValueFactory(new PropertyValueFactory<>("std_image"));

        search(); //this rows to the data hence ready for creating a pegination that has data

        Pagination pagination = new Pagination((data.size() / rowsPerPage + 1), 0);
        pagination.getStylesheets().add("reportgenthree/AppCss/peginationStyle.css");

        pagination.setPageFactory(this::createPage);

        pane.setCenter(pagination);

    }

    final ObservableList<SearchedStudent> data = FXCollections.observableArrayList();

    private Node createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        table.setItems(FXCollections.observableArrayList(data.subList(fromIndex, toIndex)));

        return new BorderPane(table);
    }

    public void search() {
        get_menu();

        data.clear();//Before searching clear the data to remove available items

        String searchHint = hint.getText();

        query = "SELECT * FROM Students_2017 WHERE StudentRegCode LIKE '%" + searchHint + "%' OR "
                + "StudentCategory LIKE '%" + searchHint + "%' OR StudentName LIKE '%" + searchHint + "%'"
                + " OR StudentCourse LIKE '%" + searchHint + "%'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        int count = 0;

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                count++;

                String course_name = rst.getString("YearOfStudy");
                String admnumber = rst.getString("StudentRegCode");
                String ccode = rst.getString("Ccode");
                String stname = rst.getString("StudentName");
                String std_image = rst.getString("std_image");
                String std_course = rst.getString("StudentCourse");
                String std_category = rst.getString("StudentCategory");
                String std_date = rst.getString("DOR");
                String std_gender = rst.getString("Gender");
                String std_admcode = rst.getString("StudentRegCode");
                JFXButton btn = new JFXButton(std_image);

                infor.setText("Resuts  " + count);

                data.add(new SearchedStudent(stname, course_name, ccode + "/" + admnumber, count, std_gender, btn, std_course, std_category, std_date, std_admcode));//add teh data afresh to the data list

            }

            conn.close();

            //create the pegination and add it in the center pane
            Pagination pagination = new Pagination((data.size() / rowsPerPage + 1), 0);
            pagination.getStylesheets().add("reportgenthree/AppCss/peginationStyle.css");
            pagination.setPageFactory(this::createPage);

            pane.setCenter(pagination);

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "ERMS", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public void search_two(String searchHint) {
        get_menu();

        data.clear();//Before searching clear the data to remove available items

        query = "SELECT * FROM Students_2017 WHERE StudentRegCode LIKE '%" + searchHint + "%' OR "
                + "StudentCategory LIKE '%" + searchHint + "%' OR StudentName LIKE '%" + searchHint + "%'"
                + " OR StudentCourse LIKE '%" + searchHint + "%'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        int count = 0;

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                count++;

                String course_name = rst.getString("YearOfStudy");
                String admnumber = rst.getString("StudentRegCode");
                String ccode = rst.getString("Ccode");
                String stname = rst.getString("StudentName");
                String std_image = rst.getString("std_image");
                String std_course = rst.getString("StudentCourse");
                String std_category = rst.getString("StudentCategory");
                String std_date = rst.getString("DOR");
                String std_gender = rst.getString("Gender");
                String std_admcode = rst.getString("StudentRegCode");
                JFXButton btn = new JFXButton(std_image);

                infor.setText("Resuts  " + count);

                data.add(new SearchedStudent(stname, course_name, ccode + "/" + admnumber, count, std_gender, btn, std_course, std_category, std_date, std_admcode));//add teh data afresh to the data list

            }

            conn.close();

            //create the pegination and add it in the center pane
            Pagination pagination = new Pagination((data.size() / rowsPerPage + 1), 0);
            pagination.getStylesheets().add("reportgenthree/AppCss/peginationStyle.css");
            pagination.setPageFactory(this::createPage);

            pane.setCenter(pagination);

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "ERMS", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is A Database Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

    }

    public void createPdfDocument() {

        try {
            String path = OpenSaveFileChooser();

            ReportGenThree.LoadDescriptionSettingsForClassListPdf();

            AppFuctions.StudentsListPdfCreator.creator(query, path);

        } catch (Exception exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "E.R.M.S", "Exception Found", "Erck Error " + exc);

        }

    }

    public String OpenSaveFileChooser() {

        DirectoryChooser directoryChooser = new DirectoryChooser();

        Stage primary = (Stage) pdfDoc.getScene().getWindow();

        File file = directoryChooser.showDialog(primary);

        String path = file.getAbsolutePath();
        return path;

    }

    public void std_property_stdTable(String new_value, String propertname, String std_id) {

        String query = "UPDATE Students_2017 SET " + propertname + " = '" + new_value + "' WHERE StudentRegCode = '" + std_id + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

    }

    public void std_property_examtable(String new_value, String propertname, String std_id) {

        String query = "UPDATE exam_2017 SET " + propertname + " = '" + new_value + "' WHERE Adm_Number = '" + std_id + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

    }

    public void std_delete(String std_id) {

        String query = "DELETE FROM Students_2017 WHERE StudentRegCode = '" + std_id + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

    }

    public void get_menu() {

        ContextMenu menu = new ContextMenu();

        table.setOnContextMenuRequested((event) -> {
            menu.show(table, event.getScreenX(), event.getScreenY());
        });

        MenuItem edit_stdame = new MenuItem("Edit Student's Name.");
        SeparatorMenuItem sp1 = new SeparatorMenuItem();

        MenuItem edit_stdcourse = new MenuItem("Edit Student's Course");
        SeparatorMenuItem sp2 = new SeparatorMenuItem();

        MenuItem edit_stdcategory = new MenuItem("Edit Student's Category");
        SeparatorMenuItem sp3 = new SeparatorMenuItem();

        MenuItem edit_stdstream = new MenuItem("Edit Student's Stream/Class");
        SeparatorMenuItem sp4 = new SeparatorMenuItem();

        MenuItem edit_stdgender = new MenuItem("Edit Student's gender");
        SeparatorMenuItem sp5 = new SeparatorMenuItem();

        MenuItem edit_stdprofile = new MenuItem("Edit Student's Profile");
        SeparatorMenuItem sp6 = new SeparatorMenuItem();

        MenuItem delete_data = new MenuItem("Remove Selected Data");
        SeparatorMenuItem sp7 = new SeparatorMenuItem();

        menu.getItems().addAll(edit_stdame, sp1, edit_stdcourse, sp2, edit_stdcategory, sp3, edit_stdstream, sp4,
                edit_stdgender, sp5, edit_stdprofile, sp6, delete_data, sp7);

        edit_stdcourse.setOnAction(e -> {

            JFXComboBox box = new JFXComboBox();
            box.setItems(AppFuctions.CoursesClass.getTableItems("CourseName", "CourseTable"));
            String original = table.getSelectionModel().getSelectedItem().getStd_course();
            student_propertyEditor(box, original, "std_course");

        });

        edit_stdstream.setOnAction(e -> {

            JFXComboBox box = new JFXComboBox();
            ObservableList<String> items = FXCollections.observableArrayList();
            items.addAll("First Year", "Second Year");
            box.setItems(items);
            String original = table.getSelectionModel().getSelectedItem().getClassname();
            student_propertyEditor(box, original, "YearOfStudy");

        });

        edit_stdcategory.setOnAction(e -> {

            JFXComboBox box = new JFXComboBox();
            box.setItems(AppFuctions.CoursesClass.getTableItems("category_name", "categories"));
            String original = table.getSelectionModel().getSelectedItem().getStd_category();
            student_propertyEditor(box, original, "std_category");

        });

        delete_data.setOnAction(e -> {

            String adm = table.getSelectionModel().getSelectedItem().getAdmnumber();
            String result = Dialogs_functions.get_custom_confirm("Erase this student and all related files?");
            if(result.equals("continue")){
            
                std_delete(adm);
            }

        });

        edit_stdgender.setOnAction(e -> {

            JFXComboBox box = new JFXComboBox();
            ObservableList<String> items = FXCollections.observableArrayList();
            items.addAll("Male", "Female");
            box.setItems(items);
            String original = table.getSelectionModel().getSelectedItem().getStd_gender();
            student_propertyEditor(box, original, "std_gender");

        });

        edit_stdame.setOnAction(e -> {

            JFXTextField box = new JFXTextField();
            box.setPrefWidth(200);
            box.setLabelFloat(true);
            box.setStyle("-fx-font-size:15;");
            box.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
                if (!newValue.matches("\\sa-zA-Z*")) {
                    box.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));

                }

            });

            String original = table.getSelectionModel().getSelectedItem().getStname();
            student_propertyEditor(box, original, "StudentName", "New Student Name");

        });

    }

    public void student_propertyEditor(JFXComboBox box, String original, String property) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentsListController.class.getResource("Dialogs/Studentpropert_texteditor.fxml"));
            StackPane pane = loader.load();
            Studentpropert_texteditorController cc = loader.getController();
            cc.add_control(box);
            cc.getLbl_oldvalue().setText(original);
            String adm = table.getSelectionModel().getSelectedItem().getStd_admcode();

            if (property.equals("std_course")) {

                cc.getBtn_save().setOnAction(e -> {

                    if (!box.getSelectionModel().isEmpty()) {

                        String new_value = box.getSelectionModel().getSelectedItem().toString();
                        String course_code = AppFuctions.CoursesClass.getCourseAbr(new_value);
                        std_property_stdTable(new_value, "StudentCourse", adm);
                        std_property_stdTable(course_code, "Ccode", adm);
                        std_property_examtable(new_value, "Std_Course", adm);

                        cc.getLbl_infor().setText("Great!! Course Changed in All Related Field Files..");

                        search_two(adm);

                    } else {

                        cc.getLbl_infor().setText("Choose New Course");
                    }
                });
            } else if (property.equals("std_category")) {

                cc.getBtn_save().setOnAction(e -> {

                    if (!box.getSelectionModel().isEmpty()) {

                        String new_value = box.getSelectionModel().getSelectedItem().toString();

                        std_property_stdTable(new_value, "StudentCategory", adm);

                        cc.getLbl_infor().setText("Great!! Student Category Changed..");

                        search_two(adm);

                    } else {

                        cc.getLbl_infor().setText("Choose New Category");
                    }
                });

            } else if (property.equals("std_gender")) {

                cc.getBtn_save().setOnAction(e -> {

                    if (!box.getSelectionModel().isEmpty()) {

                        String new_value = box.getSelectionModel().getSelectedItem().toString();

                        std_property_stdTable(new_value, "Gender", adm);

                        cc.getLbl_infor().setText("Great!! Student Gender Changed..");

                        search_two(adm);

                    } else {

                        cc.getLbl_infor().setText("Choose New Gender");
                    }
                });

            } else if (property.equals("YearOfStudy")) {

                cc.getBtn_save().setOnAction(e -> {

                    if (!box.getSelectionModel().isEmpty()) {

                        String new_value = box.getSelectionModel().getSelectedItem().toString();

                        std_property_stdTable(new_value, "YearOfStudy", adm);

                        cc.getLbl_infor().setText("Great!! Student Stream Changed..");

                        search_two(adm);

                    } else {

                        cc.getLbl_infor().setText("Choose New Gender");
                    }
                });

            }

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {

            System.out.println(ex);
        }

    }

    public void student_propertyEditor(JFXTextField txt_field, String original, String property, String prompt_text) {

        try {
            JFXDialogLayout content = new JFXDialogLayout();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(StudentsListController.class.getResource("Dialogs/Studentpropert_texteditor.fxml"));
            StackPane pane = loader.load();
            Studentpropert_texteditorController cc = loader.getController();
            cc.add_control(txt_field, prompt_text);
            cc.getLbl_oldvalue().setText(original);
            String adm = table.getSelectionModel().getSelectedItem().getStd_admcode();

            if (property.equals("StudentName")) {

                cc.getBtn_save().setOnAction(e -> {

                    if (!txt_field.getText().isEmpty()) {

                        String new_value = txt_field.getText().toUpperCase();

                        std_property_stdTable(new_value, "StudentName", adm);

                        cc.getLbl_infor().setText("Great!! Student Name Changed in All Related Field Files..");

                        search_two(adm);

                    } else {

                        cc.getLbl_infor().setText("Enter Name Of Student..");
                    }
                });
            }

            content.setAlignment(Pos.CENTER);
            content.setBody(pane);

            JFXDialog dlog = new JFXDialog(stackpane, content, JFXDialog.DialogTransition.TOP);

            dlog.show();

        } catch (IOException ex) {

            System.out.println(ex);
        }

    }

}
