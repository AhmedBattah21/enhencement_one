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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;
import Connection.sqlDataBaseConnection;

public class StudentsClass {

    public static String getTimeYear() {

        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyy");

        String timetwo = time.format(format);

        return "Students_" + timetwo;

    }

    public static Boolean registerStudent(String stname, String ccode, String stRegNo, String stdyearOfStudy,
            String stdCourse, String stdCategory, String std_image, String dor, String gender) {
        boolean result = false;

        String query = "INSERT INTO  Students_2017 VALUES('" + stname + "','" + ccode + "','" + stRegNo + "','" + stdyearOfStudy + "',"
                + "'" + stdCourse + "','" + stdCategory + "','" + std_image + "','" + dor + "','" + gender + "')";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            st.execute(query);

            result = true;

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "You Have An Error E-001 Student Class \n Error " + exc);

        }

        return result;
    }
//-------------------------------------------function with error code 002 checkStudent(String stdReg)-----------------// 

    public static Boolean checkStudent(String stdReg) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM registrationnumbers WHERE Regnumber = '" + stdReg + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "You Have An Error E-002 Student Class \n Error " + exc);

        }

        return result;

    }

    public static Boolean checkStudent(String stdReg, String course) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + stdReg + "' AND StudentCourse = '" + course + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "You Have An Error E-002 Student Class \n Error " + exc);

        }

        return result;

    }

    public static Boolean checkStudent_indb(String stdReg) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + stdReg + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "You Have An Error E-002 Student Class \n Error " + exc);

        }

        return result;

    }

//-------------------------------------------function with error code 003 updateSdtFname()---------------------------------// 
    public static  int get_numbunits(String course) {

        String query = "SELECT Units FROM CourseTable WHERE CourseName = '" + course + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        int num_units = 0;

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            num_units = rst.getInt("Units");

            conn.close();

        } catch (SQLException exc) {

            System.out.println(" " + exc);
        }

        return num_units;

    }

    public static Boolean updateSdtFname(String table, String StdName, String StdRegCode) {

        String query = "UPDATE " + table + " SET STDNAME = '" + StdName + "' WHERE STDREGNO = '" + StdRegCode + "'";

        boolean result = false;

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {
            Statement st = conn.createStatement();
            int value = st.executeUpdate(query);

            if (value == 1) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "<html>You Have An Error E-003 Student Class </br> Error " + exc + "</html>");

        }

        return result;

    }

    //----------------------------------function with error code 004 updateSdtCategory()---------------------------------// 
/*
 
     *
 
     */
    public static Boolean updateSdtCategory(String table, String StdCategory, String StdRegCode) {

        String query = "UPDATE " + table + " SET STDCATEGORY = '" + StdCategory + "' WHERE STDREGNO = '" + StdRegCode + "'";

        boolean result = false;

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {
            Statement st = conn.createStatement();
            int value = st.executeUpdate(query);

            if (value == 1) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "<html>You Have An Error E-004 Student Class </br> Error " + exc + "</html>");

        }

        return result;

    }

    //----------------------------------function with error code 005 updateSdtCourse()---------------------------------// 
/*
 
     *
 
     */
    public static Boolean updateSdtCourse(String year, String StdCourse, String StdRegCode) {

        String table = "Students_".concat(year);

        String query = "UPDATE " + table + " SET StudentCourse = '" + StdCourse + "' WHERE StudentRegCode = '" + StdRegCode + "'";

        boolean result = false;

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {
            Statement st = conn.createStatement();
            int value = st.executeUpdate(query);

            if (value == 1) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "<html>You Have An Error E-005 Student Class </br> Error " + exc + "</html>");

        }

        return result;

    }

    //----------------------------------function with error code 006 updateSdtYear()---------------------------------// 
/*
 
     *
 
     */
    public static Boolean updateSdtYear(String year, String StdYear, String StdRegCode) {

        String table = "Students_".concat(year);

        String query = "UPDATE " + table + " SET YearOfStudy = '" + StdYear + "' WHERE StudentRegCode = '" + StdRegCode + "'";

        boolean result = false;

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {
            Statement st = conn.createStatement();
            int value = st.executeUpdate(query);

            if (value == 1) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "<html>You Have An Error E-005 Student Class </br> Error " + exc + "</html>");

        }

        return result;

    }

    //-------------------------------------------function with error code 002 checkStudent(String stdReg)-----------------// 
/*
 
     *
 
     */
    public static Boolean checkTable(String table) {

        boolean result = false;

        String tableone = "Students_" + table;

        String query = "SELECT * FROM " + tableone + "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            result = false;

        }

        return result;

    }

    public static void AddRegNumber(String regno, String stname) {

        String query = "INSERT INTO registrationnumbers VALUES(null," + regno + ",'Yes','" + stname + "')";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {
            Statement st = conn.createStatement();

            st.executeUpdate(query);

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "<HTML>You Have An Error E-017 Student Class <br> Error " + exc + "</HTML>");

        }

    }

    public static String getStdName(String regno) {
        String stdname = "";

        String query = "SELECT StudentName FROM Students_2017 WHERE StudentRegCode ='" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                stdname = rst.getString("StudentName");

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, exc);
        }

        return stdname;

    }

    public static String getStdregcode(String regno) {
        String Ccode = "";

        String query = "SELECT Ccode FROM Students_2017 WHERE StudentRegCode ='" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                Ccode = rst.getString("Ccode");

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, exc);
        }

        return Ccode;

    }

    public static String getStdCourse(String regno) {
        String stdname = "";

        String query = "SELECT StudentCourse FROM Students_2017 WHERE StudentRegCode ='" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                stdname = rst.getString("StudentCourse");

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, exc);
        }

        return stdname;

    }

    public static Boolean movestudentcourse(String table, String newtable, String regno) {
        boolean checker = false;

        String query = "SELECT * FROM " + table + " WHERE STDREGNO = '" + regno + "'";
        String querythree = "DELETE FROM " + table + " WHERE STDREGNO = '" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            Statement st2 = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            while (rst.next()) {

                String name = rst.getString("STDNAME");
                String category = rst.getString("STDCATEGORY");
                String year = rst.getString("STDYEAR");

                String querytwo = "INSERT INTO " + newtable + " VALUES('" + regno + "','" + name + "','" + category + "','" + year + "')";

                st2.executeUpdate(querytwo);
                st2.executeUpdate(querythree);

                checker = true;

            }
            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, exc);

        }

        return checker;
    }

    public static String getStname(String regno) {

        String query = "SELECT stname FROM registrationnumbers WHERE RegNumber = '" + regno + "'";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        String name = "";
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                name = rst.getString("stname");
            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, exc);

        }

        return name;
    }

    public static Boolean checkStudentTwo(String stdReg) {

        boolean result = false;
        String stdname = "";

        String query = "SELECT * FROM Students_2017 WHERE StudentRegCode = '" + stdReg + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                result = true;

            }

            conn.close();

        } catch (SQLException exc) {

            JOptionPane.showMessageDialog(null, "You Have An Error E-002 Student Class \n Error " + exc);

        }

        return result;

    }

}
//select 1 from information_schema.tables where table_name = @tableName
