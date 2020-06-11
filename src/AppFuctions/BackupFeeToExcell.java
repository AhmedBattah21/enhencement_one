/**
 * This class is responsible for creating back up of data into an excell
 * document
 */
package AppFuctions;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import Connection.sqlDataBaseConnection;
import java.io.FileInputStream;
import reportgenthree.views.SystemNameSetterController;

/**
 *
 * @author Computer
 */
public class BackupFeeToExcell {

    private static FileOutputStream fileout = null;
    private static String filepath = "";

    public static void createSheet(String path) {

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        String query = "SELECT * FROM Fee_Table ";
        HSSFWorkbook workbook = new HSSFWorkbook();

        try {

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            fileout = new FileOutputStream(path.concat("/FeeStatementsBackup.xls"));

            HSSFSheet sheetone = workbook.createSheet("Fee Accounts");

//sheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);    
            sheetone.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(2, 2, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(3, 3, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(4, 4, 0, 6));
            sheetone.addMergedRegion(new CellRangeAddress(4, 4, 7, 12));
            sheetone.addMergedRegion(new CellRangeAddress(5, 5, 0, 12));

            //Creating styles One
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 20);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            font.setColor(HSSFColor.BLUE.index);
            style.setFont(font);
            //======================================

            //Creating styles Two
            HSSFCellStyle styletwo = workbook.createCellStyle();
            HSSFFont fontwo = workbook.createFont();
            fontwo.setFontName(HSSFFont.FONT_ARIAL);
            fontwo.setFontHeightInPoints((short) 16);

            fontwo.setColor(HSSFColor.BLUE.index);
            styletwo.setFont(fontwo);
            styletwo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //======================================

            //Creating styles three
            HSSFCellStyle style3 = workbook.createCellStyle();
            HSSFFont font3 = workbook.createFont();
            font3.setFontName(HSSFFont.FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);

            style3.setFont(font3);
            //style3.setWrapText(true);
            style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            //======================================

            //Creating styles three
            HSSFCellStyle style6 = workbook.createCellStyle();
            HSSFFont font6 = workbook.createFont();
            font6.setFontName(HSSFFont.FONT_ARIAL);
            font6.setFontHeightInPoints((short) 12);
            font6.setColor(HSSFColor.RED.index);

            style6.setFont(font6);
            //style3.setWrapText(true);
            style6.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            //======================================

            //Creating styles Four
            HSSFCellStyle style4 = workbook.createCellStyle();
            HSSFFont font4 = workbook.createFont();
            font4.setFontName(HSSFFont.FONT_ARIAL);
            font4.setFontHeightInPoints((short) 13);

            font4.setColor(HSSFColor.RED.index);
            style4.setFont(font4);
            style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style4.setBorderBottom((short) 2);

            HSSFCellStyle style5 = workbook.createCellStyle();
            HSSFFont font5 = workbook.createFont();
            font5.setFontName(HSSFFont.FONT_ARIAL);
            font5.setFontHeightInPoints((short) 14);

            font5.setColor(HSSFColor.BLUE.index);
            style5.setFont(font5);
            style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //======================================

            Row row = sheetone.createRow((short) 0);
            Cell ct1 = row.createCell(0);

            ct1.setCellValue(SystemNameSetterController.getSname());
            ct1.setCellStyle(style);

            Row row2 = sheetone.createRow((short) 1);
            Cell ct2 = row2.createCell(0);

            ct2.setCellValue(SystemNameSetterController.getbox()+" "+SystemNameSetterController.getPlace());//"The Following Books Were Issued  To "+tcode
            ct2.setCellStyle(styletwo);

            Row row3 = sheetone.createRow((short) 2);
            Cell ct3 = row3.createCell(0);
            ct3.setCellValue("Fee Statements Back Up");
            ct3.setCellStyle(style5);

            Row row6 = sheetone.createRow((short) 3);
            Cell ct6 = row6.createCell(0);
            ct6.setCellValue("Students Fee Syayements Back Up");
            ct6.setCellStyle(style5);

            Row row8 = sheetone.createRow((short) 6);
            Cell ct8 = row8.createCell(0);
            ct8.setCellValue("Acc Year");
            ct8.setCellStyle(style3);

            Cell ct8b = row8.createCell(1);
            ct8b.setCellValue("Account User");
            ct8b.setCellStyle(style3);

            Cell ct8c = row8.createCell(2);
            ct8c.setCellValue("Acc Type");
            ct8c.setCellStyle(style3);

            Cell ct8d = row8.createCell(3);
            ct8d.setCellValue("Amount Term One");
            ct8d.setCellStyle(style3);

            Cell ct8e = row8.createCell(4);
            ct8e.setCellValue("Balance Term One");
            ct8e.setCellStyle(style3);

            Cell ct8f = row8.createCell(5);
            ct8f.setCellValue("Paid Amount Term 2");
            ct8f.setCellStyle(style3);

            Cell ct8g = row8.createCell(6);
            ct8g.setCellValue("Balance Term 2");
            ct8g.setCellStyle(style3);

            Cell ct8h = row8.createCell(7);
            ct8h.setCellValue("Amount Paid Term 3");
            ct8h.setCellStyle(style3);

            Cell ct8i = row8.createCell(8);
            ct8i.setCellValue("Balance Term 3");
            ct8i.setCellStyle(style3);

            Cell ct8j = row8.createCell(9);
            ct8j.setCellValue("Total Balance");
            ct8j.setCellStyle(style3);

            int value = 8;

            while (rst.next()) {

                Row row9 = sheetone.createRow((short) value);
                Cell ct91 = row9.createCell(0);
                ct91.setCellValue(rst.getString("Acc_year"));
                ct91.setCellStyle(style3);

                Cell ct9b = row9.createCell(1);
                String admnumber = rst.getString("Acc_user");
                ct9b.setCellValue(getCcode(admnumber) + "/" + admnumber);
                ct9b.setCellStyle(style3);

                Cell ct9c = row9.createCell(2);
                ct9c.setCellValue(rst.getString("Acc_type"));
                ct9c.setCellStyle(style3);

                Cell ct9d = row9.createCell(3);
                ct9d.setCellValue(rst.getInt("Amount_t1"));
                ct9d.setCellStyle(style3);

                Cell ct9e = row9.createCell(4);
                ct9e.setCellValue(rst.getInt("Balance_t1"));
                ct9e.setCellStyle(style3);

                Cell ct9f = row9.createCell(5);
                ct9f.setCellValue(rst.getInt("Amount_t2"));
                ct9f.setCellStyle(style3);

                Cell ct9g = row9.createCell(6);
                ct9g.setCellValue(rst.getInt("Balance_t2"));
                ct9g.setCellStyle(style3);

                Cell ct9h = row9.createCell(7);
                ct9h.setCellValue(rst.getInt("Amount_t3"));
                ct9h.setCellStyle(style3);

                Cell ct9i = row9.createCell(8);
                ct9i.setCellValue(rst.getInt("Balance_t3"));
                ct9i.setCellStyle(style3);

                Cell ct9j = row9.createCell(9);
                ct9j.setCellValue(rst.getInt("Acc_Balance"));
                ct9j.setCellStyle(style3);

                value++;
            }

            sheetone.addMergedRegion(new CellRangeAddress(value + 2, value + 2, 0, 4));
            sheetone.addMergedRegion(new CellRangeAddress(value + 2, value + 2, 6, 9));

            for (int i = 0; i < 400; i++) {

                sheetone.autoSizeColumn(i);

            }
            workbook.write(fileout);

            conn.close();

        } catch (FileNotFoundException exe) {

            JOptionPane.showMessageDialog(null, exe);

        } catch (SQLException | IOException exe) {

            JOptionPane.showMessageDialog(null, exe);

        }

       
    }

    
/**
 * 
 * @param path 
 */
    public static void CreateBackups(String path) {

        createSheet(path);
        createFeeStatementsUpdate(path);

    }
/**
 * 
 * @param regcode
 * @return 
 */
    public static String getCcode(String regcode) {

        String query = "SELECT Ccode FROM students_2017 WHERE StudentRegCode = '" + regcode + "'";
        String ccode = "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            ccode = rst.getString("Ccode");
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return ccode;
    }

    public static void createFeeStatementsUpdate(String path) {

        FileInputStream file = null;

        FileOutputStream output = null;

        String query = "SELECT * FROM Fee_statements ORDER BY State_user";
        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            file = new FileInputStream(path + "/FeeStatementsBackup.xls");

            HSSFWorkbook workbook = new HSSFWorkbook(file);

            Statement st = conn.createStatement();

            ResultSet rst = st.executeQuery(query);

            HSSFSheet sheetone = workbook.createSheet("Fee Statements");

//sheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);    
            sheetone.addMergedRegion(new CellRangeAddress(0, 0, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(1, 1, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(2, 2, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(3, 3, 0, 12));
            sheetone.addMergedRegion(new CellRangeAddress(4, 4, 0, 6));
            sheetone.addMergedRegion(new CellRangeAddress(4, 4, 7, 12));
            sheetone.addMergedRegion(new CellRangeAddress(5, 5, 0, 12));

            //Creating styles One
            HSSFCellStyle style = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setFontName(HSSFFont.FONT_ARIAL);
            font.setFontHeightInPoints((short) 20);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

            font.setColor(HSSFColor.BLUE.index);
            style.setFont(font);
            //======================================

            //Creating styles Two
            HSSFCellStyle styletwo = workbook.createCellStyle();
            HSSFFont fontwo = workbook.createFont();
            fontwo.setFontName(HSSFFont.FONT_ARIAL);
            fontwo.setFontHeightInPoints((short) 16);

            fontwo.setColor(HSSFColor.BLUE.index);
            styletwo.setFont(fontwo);
            styletwo.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //======================================

            //Creating styles three
            HSSFCellStyle style3 = workbook.createCellStyle();
            HSSFFont font3 = workbook.createFont();
            font3.setFontName(HSSFFont.FONT_ARIAL);
            font3.setFontHeightInPoints((short) 10);

            style3.setFont(font3);
            //style3.setWrapText(true);
            style3.setAlignment(HSSFCellStyle.ALIGN_LEFT);
            //======================================

            //Creating styles three
            HSSFCellStyle style6 = workbook.createCellStyle();
            HSSFFont font6 = workbook.createFont();
            font6.setFontName(HSSFFont.FONT_ARIAL);
            font6.setFontHeightInPoints((short) 12);
            font6.setColor(HSSFColor.RED.index);

            style6.setFont(font6);
            //style3.setWrapText(true);
            style6.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            //======================================

            //Creating styles Four
            HSSFCellStyle style4 = workbook.createCellStyle();
            HSSFFont font4 = workbook.createFont();
            font4.setFontName(HSSFFont.FONT_ARIAL);
            font4.setFontHeightInPoints((short) 13);

            font4.setColor(HSSFColor.RED.index);
            style4.setFont(font4);
            style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style4.setBorderBottom((short) 2);

            HSSFCellStyle style5 = workbook.createCellStyle();
            HSSFFont font5 = workbook.createFont();
            font5.setFontName(HSSFFont.FONT_ARIAL);
            font5.setFontHeightInPoints((short) 14);

            font5.setColor(HSSFColor.BLUE.index);
            style5.setFont(font5);
            style5.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            //======================================

            Row row = sheetone.createRow((short) 0);
            Cell ct1 = row.createCell(0);

            ct1.setCellValue(SystemNameSetterController.getSname());
            ct1.setCellStyle(style);

            Row row2 = sheetone.createRow((short) 1);
            Cell ct2 = row2.createCell(0);

            ct2.setCellValue(SystemNameSetterController.getbox()+" "+SystemNameSetterController.getPlace());//"The Following Books Were Issued  To "+tcode
            ct2.setCellStyle(styletwo);

            Row row3 = sheetone.createRow((short) 2);
            Cell ct3 = row3.createCell(0);
            ct3.setCellValue("Fee Statements Back Up");
            ct3.setCellStyle(style5);

            Row row6 = sheetone.createRow((short) 3);
            Cell ct6 = row6.createCell(0);
            ct6.setCellValue("Students Fee Syayements Back Up");
            ct6.setCellStyle(style5);

            Row row8 = sheetone.createRow((short) 6);
            Cell ct8 = row8.createCell(0);
            ct8.setCellValue("Acc User");
            ct8.setCellStyle(style3);

            Cell ct8b = row8.createCell(1);
            ct8b.setCellValue("Payment Date");
            ct8b.setCellStyle(style3);

            Cell ct8c = row8.createCell(2);
            ct8c.setCellValue("Payment Mode");
            ct8c.setCellStyle(style3);

            Cell ct8d = row8.createCell(3);
            ct8d.setCellValue("Receipt No");
            ct8d.setCellStyle(style3);

            Cell ct8e = row8.createCell(4);
            ct8e.setCellValue("Amount");
            ct8e.setCellStyle(style3);

            Cell ct8f = row8.createCell(5);
            ct8f.setCellValue("Balance");
            ct8f.setCellStyle(style3);

            Cell ct8g = row8.createCell(6);
            ct8g.setCellValue("Term");
            ct8g.setCellStyle(style3);

            Cell ct8h = row8.createCell(7);
            ct8h.setCellValue("Year");
            ct8h.setCellStyle(style3);

            Cell ct8i = row8.createCell(8);
            ct8i.setCellValue("Comment");
            ct8i.setCellStyle(style3);

            int value = 8;

            while (rst.next()) {

                Row row9 = sheetone.createRow((short) value);
                Cell ct91 = row9.createCell(0);
                ct91.setCellValue(rst.getString("State_date"));
                ct91.setCellStyle(style3);

                Cell ct9b = row9.createCell(1);
                String admnumber = rst.getString("State_user");
                ct9b.setCellValue(getCcode(admnumber) + "/" + admnumber);
                ct9b.setCellStyle(style3);

                Cell ct9c = row9.createCell(2);
                ct9c.setCellValue(rst.getString("State_paymode"));
                ct9c.setCellStyle(style3);

                Cell ct9d = row9.createCell(3);
                ct9d.setCellValue("R/" + rst.getString("State_receiptno"));
                ct9d.setCellStyle(style3);

                Cell ct9e = row9.createCell(4);
                ct9e.setCellValue(rst.getInt("State_amount"));
                ct9e.setCellStyle(style3);

                Cell ct9f = row9.createCell(5);
                ct9f.setCellValue(rst.getInt("State_balance"));
                ct9f.setCellStyle(style3);

                Cell ct9g = row9.createCell(6);
                ct9g.setCellValue(rst.getString("Term"));
                ct9g.setCellStyle(style3);

                Cell ct9h = row9.createCell(7);
                ct9h.setCellValue(rst.getString("Year"));
                ct9h.setCellStyle(style3);

                Cell ct9i = row9.createCell(8);
                ct9i.setCellValue(rst.getString("Comment"));
                ct9i.setCellStyle(style3);

                value++;
            }

            sheetone.addMergedRegion(new CellRangeAddress(value + 2, value + 2, 0, 4));
            sheetone.addMergedRegion(new CellRangeAddress(value + 2, value + 2, 6, 9));

            for (int i = 0; i < 400; i++) {

                sheetone.autoSizeColumn(i);

            }
            output = new FileOutputStream(path + "/FeeStatementsBackup.xls");

            workbook.write(output);

            conn.close();

        } catch (FileNotFoundException exe) {

            JOptionPane.showMessageDialog(null, exe);

        } catch (SQLException | IOException exe) {

            JOptionPane.showMessageDialog(null, exe);

        }

      

    }

}
