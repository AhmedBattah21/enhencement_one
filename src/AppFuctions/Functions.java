/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppFuctions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javax.swing.JOptionPane;
import org.controlsfx.control.CheckListView;
import Connection.sqlDataBaseConnection;

/**
 *
 * @author Computer
 */
public class Functions {

    /**
     * 
     * @param type
     * @param title
     * @param header
     * @param content 
     */
    public static void showAlert(Alert.AlertType type, String title, String header, String content) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setX(0);
        alert.setY(50);
        alert.showAndWait();

    }

    /**
     * 
     * @param type
     * @param title
     * @param header
     * @param content
     * @param x
     * @param y
     * @return 
     */
    public static Optional<ButtonType> showAlertTwo(Alert.AlertType type, String title, String header, String content, double x, double y) {

        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.setX(x + 15);
        alert.setY(y);
        Optional<ButtonType> result = alert.showAndWait();

        return result;

    }

    
    /**
     * 
     * @param exc
     * @param x
     * @param y 
     */
    public static void getSqlExceptionMessage(SQLException exc, double x, double y) {

        SQLException error = exc;

        showAlertTwo(Alert.AlertType.ERROR, "ERMS", "Database Not Working", ""
                + "Our Database Has Encoutered The Below Error " + exc
                + " Try Restartint The Application \n If It Persist Please Contact Developer", x, y);

    }

    /**
     * 
     * @param hex
     * @return 
     */
    public static int getRedColor(String hex) {

        int r = Integer.valueOf(hex.substring(1, 3), 16);

        return r;
    }

    /**
     * 
     * @param hex
     * @return 
     */
    public static int getGreenColor(String hex) {

        int g = Integer.valueOf(hex.substring(3, 5), 16);

        return g;
    }

    /**
     * 
     * @param hex
     * @return 
     */
    public static int getBlackColor(String hex) {

        int b = Integer.valueOf(hex.substring(5, 7), 16);

        return b;
    }

    
    /**
     * 
     * @param listView
     * @param tableName
     * @param ColumnName 
     */
    public static void populateCheckListView(CheckListView listView, String tableName, String ColumnName) {

        String query = "SELECT " + ColumnName + " FROM " + tableName + "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<String> rows = FXCollections.observableArrayList();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            while (rst.next()) {

                rows.add(rst.getString(ColumnName));

            }

            listView.getItems().addAll(rows);

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "" + exc);

        }

    }

    
    /**
     * 
     * @param score
     * @return 
     */
    public static String getGrade(double score) {

        String Grade = "G";

        if (score >= 80) {

            Grade = "A";

        } else if (score >= 75) {

            Grade = "A-";

        } else if (score >= 70) {

            Grade = "B+";

        } else if (score >= 65) {

            Grade = "B";

        } else if (score >= 60) {

            Grade = "B-";

        } else if (score >= 54) {

            Grade = "C+";

        } else if (score >= 50) {

            Grade = "C";

        } else if (score >= 45) {

            Grade = "C-";

        } else if (score >= 40) {

            Grade = "D+";

        } else if (score >= 35) {

            Grade = "D";

        } else if (score >= 30) {

            Grade = "D-";

        } else if (score <= 29 && score >= 0) {

            Grade = "E";

        } else {

            Grade = "F";
        }

        return Grade;

    }

    
    /**
     * 
     * @param score
     * @return 
     */
    public static String getRemarks(double score) {

        String Grade = "G";

        if (score >= 80) {

            Grade = "Excellent";

        } else if (score >= 75) {

            Grade = "Excellent";

        } else if (score >= 70) {

            Grade = "Very Good";

        } else if (score >= 65) {

            Grade = "Very Good";

        } else if (score >= 60) {

            Grade = "Good";

        } else if (score >= 54) {

            Grade = "Good";

        } else if (score >= 50) {

            Grade = "Good";

        } else if (score >= 45) {

            Grade = "Average";

        } else if (score >= 40) {

            Grade = "Trial";

        } else if (score >= 35) {

            Grade = "Tried";

        } else if (score >= 30) {

            Grade = "More Effort";

        } else if (score <= 29 && score >= 0) {

            Grade = "Poor";

        } else {

            Grade = "Poor";
        }

        return Grade;

    }

    
    /**
     * 
     * @param examname
     * @return 
     */
    public static int getMaxScore(String examname) {

        String query = "SELECT MaxScore FROM ExamNames WHERE ExaminationName = '" + examname + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        int maxscore = 0;
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                maxscore = rst.getInt("MaxScore");
            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println("" + exc);

        }

        return maxscore;

    }

    /**
     * 
     * @param unitname
     * @return 
     */
    public static String get_unitproperty(String unitname) {

        String query = "SELECT Property FROM UnitsTable WHERE UnitName = '" + unitname + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        String property = "";
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {

                property = rst.getString("Property");
                
            }
            conn.close();
        } catch (SQLException exc) {

            System.out.println(" "+exc);
        }
        
        return property;

    }
    
    
    /**
     * 
     * @param unit_name
     * @param std_year
     * @param course
     * @return 
     */
    public static String get_initials(String unit_name, String std_year, String course) {

        String unit_property = get_unitproperty(unit_name);
        String query = "Null";
        String initials = "Null";

        if (unit_property.equals("uni")) {

            query = "SELECT Teacher_Initials FROM AssignedUnits "
                    + "WHERE UnitName = '" + unit_name + "' AND Student_Class = '" + course + "' AND Student_Year = '" + std_year + "'";
        } else {

            query = "SELECT Teacher_Initials FROM AssignedUnits "
                    + "WHERE UnitName = '" + unit_name + "' AND Student_Class = 'All Classes' AND Student_Year = '" + std_year + "'";

        }
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            if (rst.next()) {

                initials = rst.getString("Teacher_Initials");
            }
            conn.close();
        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }
        return initials;
    }


}
