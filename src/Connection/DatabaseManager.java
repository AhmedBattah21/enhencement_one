
package Connection;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class DatabaseManager {

    final static String APP_DATA_PATH = System.getenv("APPDATA");
    final static String APP_FOLDER = "Merms3";
    final static String DB_FOLDER = "Database";
    final static String DB_NAME = "reportgen.db";
    final static String DB_HOME = APP_DATA_PATH + "/" + APP_FOLDER + "/" + DB_FOLDER;
    public final static String DRIVER = "org.sqlite.JDBC";

    public static Connection Mainconnection() {

        Connection ConnMain = null;

        try {

            Class.forName(DRIVER);

            ConnMain = DriverManager.getConnection("jdbc:sqlite:" + DB_HOME + "/" + DB_NAME, null, null);

        } catch (SQLException | ClassNotFoundException exc) {

            JOptionPane.showMessageDialog(null, exc, null, JOptionPane.OK_OPTION);

        }

        return ConnMain;
    }

    public static Boolean check_Database_existance() {

        boolean result = true;

        try {

            File file = new File(DB_HOME);

            if (file.exists()) {

                result = true;

            } else {
                file.mkdirs();

                result = false;
            }
        } catch (Exception exc) {

            System.out.println("" + exc);

        }

        return result;

    }

    public static Boolean check_Database_file() {

        boolean result = false;

        try {

            File file = new File(DB_HOME + "/" + DB_NAME);

            result = file.exists();

        } catch (Exception exc) {

            System.out.println("" + exc);

        }

        return result;

    }

    public static void createDatabase() {

        if (check_Database_existance()) {

        } else {

            Mainconnection();

        }

    }

    public static Boolean table_checker(String tname) {

        boolean results = true;

        Connection conn = Mainconnection();

        try {

            DatabaseMetaData dbmd = conn.getMetaData();

            ResultSet rs = dbmd.getTables(null, null, tname.toUpperCase(), null);

            if (rs.next()) {

                results = true;

            } else {

                results = false;

            }

            conn.close();

        } catch (SQLException exc) {

            System.out.println("Table_checker" + exc);

        }

        return results;
    }
    
    /**
     * 
     * @return 
     */

    public static boolean create_tables() {

        create_Actitvities();
        create_categories();
        create_course_table();
        createdGroupsTable();
        create_DailyFeeInput();
        create_ExamNames();
        create_ExaminationType();
        create_FeeAccountsTypes();
        create_Fee_Table();
        create_Fee_statements();
        create_Semisters();
        create_StudentsClasses();
        create_Students_2017();
        create_UnitsTable();
        create_exam_2017();
        create_registrationnumbers();
        create_users();
        create_Teacher();
        create_AssignedUnits();
        create_TaskTable();
        create_programPayment();
        create_programnames();

        return false;

    }

    public static void create_Actitvities() {

        String query = "CREATE TABLE Activities ("
                + "    Activity_id   VARCHAR (100),"
                + "    Activity_name VARCHAR (100),"
                + "    Activity_desc VARCHAR (100),"
                + "    Activity_date VARCHAR (100),"
                + "    Activity_User VARCHAR (100) "
                + ");";

        if (!table_checker("Activities")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_Activities_table " + exc);

            }
        }

    }

    public static void create_categories() {

        String query = "CREATE TABLE categories ("
                + "    category_id   INTEGER PRIMARY KEY AUTOINCREMENT"
                + "                          NOT NULL,"
                + "    category_name VARCHAR NOT NULL"
                + ");";

        if (!table_checker("categories")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_categories_table " + exc);

            }
        }

    }

    public static void create_course_table() {

        String query = "CREATE TABLE CourseTable ("
                + "    CourseName        VARCHAR (100) NOT NULL"
                + "                                    PRIMARY KEY,"
                + "    CourseAbreviation VARCHAR (100) NOT NULL,"
                + "    Units             VARCHAR (100) "
                + ");";

        if (!table_checker("CourseTable")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_CourseTable_table " + exc);

            }
        }

    }

    public static void createdGroupsTable() {

        String query = "CREATE TABLE CreatedGroups ("
                + "    CohoteName     VARCHAR (100) NOT NULL PRIMARY KEY,"
                + "    CourseName     VARCHAR (100) NOT NULL,"
                + "    DateOfCreation VARCHAR (100) NOT NULL"
                + ");";

        if (!table_checker("CreatedGroups")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_CreatedGroups_table " + exc);

            }
        }

    }

    public static void create_DailyFeeInput() {

        String query = "CREATE TABLE DailyFeeInput ("
                + "    regno            VARCHAR (100) NOT NULL,"
                + "    current_balances VARCHAR       NOT NULL,"
                + "    Amountpaid       VARCHAR       NOT NULL,"
                + "    Comments         VARCHAR       NOT NULL"
                + ");";

        if (!table_checker("DailyFeeInput")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_DailyFeeInput_table " + exc);

            }
        }

    }

    public static void create_exam_2017() {

        String query = "CREATE TABLE exam_2017 ("
                + "    Adm_Number VARCHAR       NOT NULL,"
                + "    Exam_Name  VARCHAR       NOT NULL,"
                + "    Exam_Term  VARCHAR       NOT NULL,"
                + "    Exam_Score INTEGER       NOT NULL,"
                + "    Std_Course VARCHAR       NOT NULL,"
                + "    Exam_Id    INTEGER       PRIMARY KEY AUTOINCREMENT,"
                + "    Unit_Name  VARCHAR       NOT NULL,"
                + "    Year       VARCHAR (100),"
                + "    Syear      VARCHAR (100) "
                + ");";

        if (!table_checker("exam_2017")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_exam_2017 " + exc);

            }
        }

    }

    public static void create_ExaminationType() {

        String query = "CREATE TABLE ExaminationType ("
                + "    ExamId   VARCHAR (100) PRIMARY KEY"
                + "                           NOT NULL,"
                + "    ExamName VARCHAR (100) NOT NULL,"
                + "    Students VARCHAR (100) NOT NULL"
                + ");";

        if (!table_checker("ExaminationType")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_ExaminationType " + exc);

            }
        }

    }

    public static void create_ExamNames() {

        String query = "CREATE TABLE ExamNames ("
                + "    ExaminationName VARCHAR (100) PRIMARY KEY"
                + "                                  NOT NULL,"
                + "    Status          VARCHAR (100) NOT NULL,"
                + "    MaxScore        VARCHAR (100) "
                + ");";

        if (!table_checker("ExamNames")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_ExamNames " + exc);

            }
        }

    }

    public static void create_Fee_statements() {

        String query = "CREATE TABLE Fee_statements ("
                + "    State_id        INTEGER       NOT NULL"
                + "                                  PRIMARY KEY AUTOINCREMENT,"
                + "    State_user      VARCHAR       NOT NULL,"
                + "    State_date      VARCHAR       NOT NULL,"
                + "    State_paymode   VARCHAR       NOT NULL,"
                + "    State_receiptno VARCHAR       NOT NULL,"
                + "    State_amount    VARCHAR       NOT NULL,"
                + "    Term            VARCHAR       NOT NULL,"
                + "    Year            VARCHAR (100),"
                + "    Comment         VARCHAR"
                + ");";

        if (!table_checker("Fee_statements")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_Fee_statements " + exc);

            }
        }

    }

    public static void create_Fee_Table() {

        String query = "CREATE TABLE Fee_Table ("
                + "    Acc_id      INTEGER       PRIMARY KEY AUTOINCREMENT"
                + "                              NOT NULL,"
                + "    Acc_year    VARCHAR (100) NOT NULL,"
                + "    Acc_user    VARCHAR (100) NOT NULL,"
                + "    Acc_type    VARCHAR       NOT NULL,"
                + "    Amount_t1   VARCHAR       NOT NULL,"
                + "    Balance_t1  VARCHAR       NOT NULL,"
                + "    Amount_t2   VARCHAR (100) NOT NULL,"
                + "    Balance_t2  VARCHAR       NOT NULL,"
                + "    Amount_t3   VARCHAR       NOT NULL,"
                + "    Balance_t3  VARCHAR       NOT NULL,"
                + "    Acc_Balance VARCHAR       NOT NULL"
                + ");";

        if (!table_checker("Fee_Table")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_Fee_Table " + exc);

            }
        }

    }

    public static void create_FeeAccountsTypes() {

        String query = "CREATE TABLE FeeAccountsTypes ("
                + "    AccountName   VARCHAR NOT NULL,"
                + "    AccountId     INTEGER PRIMARY KEY AUTOINCREMENT"
                + "                          NOT NULL,"
                + "    AmountT1      VARCHAR NOT NULL,"
                + "    AmountT2      VARCHAR NOT NULL,"
                + "    AmountT3      VARCHAR NOT NULL,"
                + "    AccountTotals VARCHAR NOT NULL"
                + ");";

        if (!table_checker("FeeAccountsTypes")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_FeeAccountsTypes " + exc);

            }
        }

    }

    public static void create_registrationnumbers() {

        String query = "CREATE TABLE registrationnumbers ("
                + "    Reg_Id    INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "    RegNumber VARCHAR NOT NULL,"
                + "    Status    VARCHAR NOT NULL,"
                + "    stname    VARCHAR"
                + ");";

        if (!table_checker("registrationnumbers")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_registrationnumbers " + exc);

            }
        }

    }

    public static void create_Semisters() {

        String query = "CREATE TABLE Semisters ("
                + "    SemisterId   INTEGER       PRIMARY KEY AUTOINCREMENT"
                + "                               NOT NULL,"
                + "    SemisterName VARCHAR       NOT NULL,"
                + "    Start        VARCHAR (100),"
                + "    [End]        VARCHAR (100) "
                + ");";

        if (!table_checker("Semisters")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_Semisters " + exc);

            }
        }

    }

    public static void create_Students_2017() {

        String query = "CREATE TABLE Students_2017 ("
                + "    StudentName     VARCHAR (100) NOT NULL,"
                + "    Ccode           VARCHAR (100),"
                + "    StudentRegCode  VARCHAR (100) NOT NULL,"
                + "    YearOfStudy     VARCHAR (100) NOT NULL,"
                + "    StudentCourse   VARCHAR (100) NOT NULL,"
                + "    StudentCategory VARCHAR (100) NOT NULL,"
                + "    std_image       VARCHAR,"
                + "    DOR             VARCHAR (100),"
                + "    Gender          VARCHAR (100) "
                + ");";

        if (!table_checker("Students_2017")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_Students_2017 " + exc);

            }
        }

    }

    public static void create_StudentsClasses() {

        String query = "CREATE TABLE StudentsClasses ("
                + "    Class_Id   INTEGER PRIMARY KEY AUTOINCREMENT"
                + "                       NOT NULL,"
                + "    Class_Name VARCHAR NOT NULL"
                + ");";

        if (!table_checker("StudentsClasses")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_StudentsClasses " + exc);

            }
        }

    }

    public static void create_UnitsTable() {

        String query = "CREATE TABLE UnitsTable ("
                + "    UnitName VARCHAR (100) NOT NULL,"
                + "    UnitCode VARCHAR (100) PRIMARY KEY"
                + "                           NOT NULL,"
                + "    Property VARCHAR (100) NOT NULL"
                + ");";

        if (!table_checker("UnitsTable")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_UnitsTable " + exc);

            }
        }

    }

    public static void create_users() {

        String query = "CREATE TABLE users ("
                + "    UserName  VARCHAR (100) NOT NULL,"
                + "    password  VARCHAR (100) NOT NULL,"
                + "    IdNumber  VARCHAR (100) NOT NULL,"
                + "    RegCode   VARCHAR (100) NOT NULL,"
                + "    UserGroup VARCHAR       NOT NULL,"
                + "    status    VARCHAR,"
                + "    userimage VARCHAR (100) "
                + ");";

        if (!table_checker("users")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_users " + exc);

            }
        }

    }

    public static void create_Teacher() {

        String query = "CREATE TABLE Teacher ("
                + "    Teacher_Name       VARCHAR (100) NOT NULL,"
                + "    Teacher_IdNumber   VARCHAR (100) NOT NULL,"
                + "    Teacher_Department VARCHAR (100) NOT NULL,"
                + "    Teacher_Initials   VARCHAR (100) NOT NULL,"
                + "    Teacher_Task       VARCHAR (100) NOT NULL,"
                + "    Teacher_Id         INTEGER       PRIMARY KEY AUTOINCREMENT,"
                + "    Password           VARCHAR (100) NOT NULL,"
                + "    Image              VARCHAR (100),"
                + "    Status             VARCHAR (100) "
                + ");";

        if (!table_checker("Teacher")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_Teacher " + exc);

            }
        }

    }

    /*
    
    
);

    
     */
    public static void create_AssignedUnits() {

        String query = "CREATE TABLE AssignedUnits ("
                + "    UnitName         VARCHAR (100) NOT NULL,"
                + "    Teacher_IdNumber VARCHAR (100) NOT NULL,"
                + "    Student_Year     VARCHAR (100) NOT NULL,"
                + "    Student_Class    VARCHAR (100) NOT NULL,"
                + "    Teacher_Initials VARCHAR (100) "
                + ");";

        if (!table_checker("AssignedUnits")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_AssignedUnits " + exc);

            }
        }

    }

    public static void create_TaskTable() {

        String query = "CREATE TABLE TeacherTasks ("
                + "    Task_Id  INTEGER       PRIMARY KEY AUTOINCREMENT,"
                + "    TaskName VARCHAR (100) NOT NULL,"
                + "    Property VARCHAR (100) "
                + ");";

        if (!table_checker("TeacherTasks")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_ TeacherTasks" + exc);

            }
        }

    }

    public static void create_programPayment() {

        String query = "CREATE TABLE Programs_Payments ("
                + "    Program    VARCHAR (100) NOT NULL,"
                + "    Program_Id INTEGER       PRIMARY KEY AUTOINCREMENT,"
                + "    Receipt_No VARCHAR (100) NOT NULL,"
                + "    Mode       VARCHAR (100) NOT NULL,"
                + "    Adm_Number VARCHAR (100) NOT NULL,"
                + "    syear      VARCHAR (100) NOT NULL,"
                + "    DOP        VARCHAR (100) NOT NULL,"
                + "    Amount     VARCHAR (100) NOT NULL"
                + ");";

        if (!table_checker("Programs_Payments")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_ Programs_Payments" + exc);

            }
        }

    }

    public static void create_programnames() {

        String query = "CREATE TABLE Programs ("
                + "    Program_Id   INTEGER       PRIMARY KEY AUTOINCREMENT,"
                + "    Program_Name VARCHAR (100) NOT NULL,"
                + "    T1_Amount    VARCHAR (100) NOT NULL,"
                + "    T2_Amount    VARCHAR (100) NOT NULL,"
                + "    T3_Amount    VARCHAR (100) NOT NULL,"
                + "    Program_plan VARCHAR (100) NOT NULL"
                + ");";

        if (!table_checker("Programs")) {

            Connection conn = Mainconnection();

            try {
                Statement st = conn.createStatement();
                st.execute(query);
                conn.close();

            } catch (SQLException exc) {

                System.out.println(" Create_ Programs" + exc);

            }
        }

    }

}
