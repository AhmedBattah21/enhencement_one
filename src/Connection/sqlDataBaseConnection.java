
package Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class sqlDataBaseConnection {

    private static Connection Conn = null;

    public static Connection sqliteconnect() {

        // Class.forName("org.sqlite.JDBC");
        
        // String home = System.getProperty("user.home") + "/" + "Documents" + "/" + "ReportGenThree" + "/" + "reportgen.db";
        
        Conn = DatabaseManager.Mainconnection();

        return Conn;

    }

    public static void add_activity(String Act_id, String Act_name, String Act_desc, String Act_date, String Act_user) {
        String query = "INSERT INTO Activities VALUES ('" + Act_id + "','" + Act_name + "','" + Act_desc + "','" + Act_date + "','" + Act_user + "')";

        Connection conn = sqliteconnect();
        try {

            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error Erick " + exc);
        }

    }

}
