/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppFuctions;

import static AppFuctions.Singlereportproducer.addtitle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import Connection.sqlDataBaseConnection;
import com.itextpdf.text.Image;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.Alert;
import java.sql.Statement;
import reportgenthree.views.DescriptionTakerController;
import java.io.IOException;
import reportgenthree.views.SystemNameSetterController;

//import javafx.scene.text.Font;
/**
 *
 * @author Computer
 */
public class FeeSingleFeeStatemets {

    private static String descriptionColor = null;

    private static String row1 = "";
    private static String row2 = "";
    private static String BackGroundHeaderColor = null;
    private static String HeaderFontColor = null;

    private static DescriptionTakerController desp = null;

    public static void creator(String query, String path,String admno) {

        //lets create the document fast
        desp = new DescriptionTakerController();

        descriptionColor = DescriptionTakerController.getDescriptionFontColor();

        row1 = DescriptionTakerController.getTableRow_1Color();

        row2 = DescriptionTakerController.getTableRow_2Color();

        BackGroundHeaderColor = DescriptionTakerController.getBackgroundHeaderColor();

        HeaderFontColor = DescriptionTakerController.getHeaderFontColor();

        try {

            Document doc = new Document(PageSize.A4);
            doc.setMargins(40, 40, 30, 20);
            String home = System.getProperty("user.home") + "/" + "Documents";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyy");
            LocalDate date = LocalDate.now();

            FileOutputStream out = new FileOutputStream(path + "/Student Fee Statemenst -" +admno+"-"+ formatter.format(date) + ".pdf");
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            reportgenthree.views.ProcessCompleteController.setPath(path + "/Student Fee Statemenst -" +admno+"-"+ formatter.format(date) + ".pdf");

            doc.open();

            //doc.add(Detailstable);
            Rectangle layout = new Rectangle(PageSize.A4);

            layout.setBorderColor(BaseColor.BLACK);  //Border color
            layout.setBorderWidth(4);      //Border width  
            layout.setBorder(Rectangle.BOX);  //Border on 4 sides
            doc.add(layout);

            Image img = Image.getInstance(home + "/ReportGenThree/school/two.png");
            img.scaleAbsolute(30f, 30f);

            Image imgtwo = Image.getInstance(home + "/ReportGenThree/school/one.png");
            imgtwo.scaleAbsolute(30f, 30f);

            Chunk titleone = new Chunk("THE COUNTY GORVERNMENT OF KILIFI");
            Chunk title2 = new Chunk("MINISTRY OF EDUCATION YOUTH AFFAIRS AND SPORTS");
            Chunk title3 = new Chunk("DEPARTMENT OF YOUTH TRAINING");

            Chunk title4 = new Chunk(SystemNameSetterController.getSname());

            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{2, 8, 2});

            PdfPCell cell = new PdfPCell(img, true);

            table.addCell(cell);//.setBorder(Rectangle.NO_BORDER);//first cell to hold image

            PdfPCell celltwo = new PdfPCell();
            celltwo.addElement(addtitle(titleone));
            celltwo.addElement(addtitle(title2));
            celltwo.addElement(addtitle(title3));
            celltwo.addElement(addtitle(title4));

            table.addCell(celltwo).setBorder(Rectangle.NO_BORDER);;//fsecond cell to hold titles

            PdfPCell cellthree = new PdfPCell(imgtwo, true);
            table.addCell(cellthree);//.setBorder(Rectangle.NO_BORDER);;//fsecond cell to hold titles

            //------------------------------------------------the second values.................................................
            PdfPTable tabletwo = new PdfPTable(3);
            tabletwo.setWidthPercentage(100);
            tabletwo.setWidths(new int[]{5, 3, 5});

            String year = Integer.toString(LocalDate.now().getYear());

            Chunk title6 = new Chunk("Email:"+SystemNameSetterController.getEmail());
            Chunk title7 = new Chunk("CELL:"+SystemNameSetterController.getContacts());
            Chunk title8 = new Chunk("Our Ref: "+SystemNameSetterController.getRef() +" "+ year);
            Chunk title9 = new Chunk(SystemNameSetterController.getbox());
            Chunk title91 = new Chunk(SystemNameSetterController.getPlace());

            PdfPCell cellt6 = new PdfPCell();
            cellt6.addElement(addtitletwo(title6));
            cellt6.addElement(addtitletwo(title7));
            cellt6.addElement(addtitletwo(title8));
            tabletwo.addCell(cellt6).setBorder(Rectangle.NO_BORDER);

            PdfPCell cellt7 = new PdfPCell();
            tabletwo.addCell(cellt7).setBorder(Rectangle.NO_BORDER);

            PdfPCell cellt8 = new PdfPCell();
            cellt8.addElement(addtitletwo(title9));
            cellt8.addElement(addtitletwo(title91));
            tabletwo.addCell(cellt8).setBorder(Rectangle.NO_BORDER);

            //--------------------------------------------------------------------------------end---------------------------------------
            //description section
            PdfPTable DescriptionSection = new PdfPTable(1);
            DescriptionSection.setWidthPercentage(100);
            DescriptionSection.setSpacingBefore(5);

            Chunk descp_a = new Chunk(DescriptionTakerController.getReportTitle());

            PdfPCell Descp1 = new PdfPCell();
            Descp1.setMinimumHeight(50);
            Descp1.setVerticalAlignment(Element.ALIGN_MIDDLE);
            Descp1.addElement(DescriptionSection(descp_a));
            Descp1.setBackgroundColor(new BaseColor(getRedColor(descriptionColor), getGreenColor(descriptionColor), getBlackColor(descriptionColor)));
            DescriptionSection.addCell(Descp1);
            //doc.add(DescriptionSection);

            //content section
            PdfPTable ContentSection = new PdfPTable(9);
            ContentSection.setWidthPercentage(100);
            ContentSection.setSpacingBefore(5);
            ContentSection.setWidths(new int[]{4, 10, 10, 10,10,10,10,10,10});
            Connection conn = sqlDataBaseConnection.sqliteconnect();

            // setTableTitle("Full Name", "Admission Number", "Stream", ContentSection, "Serial Number");
            try {
                Statement st = conn.createStatement();
                ResultSet rst = st.executeQuery(query);
                int count = 1;
                int numstudents = 1;

                int totalbalance = 0;
                while (rst.next()) {

                    if (numstudents == 1) {

                        setTableTitle("Date", "Mode", "Receipt No", "Description", "Amount", "Balance", "Term", "Year", ContentSection, "S/No");

                    }

                    String State_date = rst.getString("State_date");
                    String State_paymode = rst.getString("State_paymode");
                    String State_receiptno = rst.getString("State_receiptno");
                    String Comment = rst.getString("Comment");
                    String State_amount = rst.getString("State_amount");
                    String State_balance = rst.getString("State_balance");
                    String Term = rst.getString("Term");
                    String Year = rst.getString("Year");

                    setNamesToCell(State_date, State_paymode,State_receiptno, Comment,"Kshs. " + State_amount + ".00","Kshs. " +State_balance + ".00",Term,Year,ContentSection, count);
                    
                    count++;

                    if (numstudents == DescriptionTakerController.getMaxNumbeOfRows()) {

                        doc.add(table);
                        doc.add(tabletwo);
                        doc.add(DescriptionSection);

                        doc.add(ContentSection);
                        ContentSection.flushContent();
                        numstudents = 0;

                        doc.newPage();

                    }

                    numstudents++;

                }

                doc.add(table);
                doc.add(tabletwo);
                doc.add(DescriptionSection);
                doc.add(ContentSection);

                conn.close();

            } catch (SQLException exc) {

                Functions.getSqlExceptionMessage(exc, 0, 0);

            }

            doc.close();
            out.close();
            writer.close();
            showProcessComplete();

        } catch (DocumentException | FileNotFoundException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "E.R.M.S", "Exception Found", " here Error " + exc);
        } catch (IOException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "E.R.M.S", "Exception Found", " here Error " + exc);
        }

    }

    public static Paragraph MajorTitle(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 30);
        myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_CENTER);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }

    public static Paragraph OtherTitles(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 14);
        myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_LEFT);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }

    public static Paragraph DescriptionSection(Chunk title) {

        Paragraph header = new Paragraph();
        title.setUnderline(0.1f, 0f);

        Font myfnt = new Font();

        myfnt.setStyle(DescriptionTakerController.getDescriptionFontStyle());
        myfnt.setFamily(DescriptionTakerController.getDescriptionFontFamily());
        myfnt.setSize((float) DescriptionTakerController.getDescriptionFontSize());

        header.setAlignment(Element.ALIGN_CENTER);

        header.setFont(myfnt);

        header.add(title);

        return header;
    }

    public static Paragraph FormatDataInTable(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font();

        myfnt.setStyle(DescriptionTakerController.getTableContentsFontStyle());
        myfnt.setFamily(DescriptionTakerController.getTableContentsFontFamily());
        myfnt.setSize((float) DescriptionTakerController.getTableContentsFontSize());

        header.setAlignment(Element.ALIGN_LEFT);
        header.setFont(myfnt);
        header.add(title);

        return header;
    }

    public static Paragraph FormatTableColumnHeaders(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font();

        myfnt.setStyle(DescriptionTakerController.getTableHeaderFontStyle());
        myfnt.setFamily(DescriptionTakerController.getTableHeaderFontFamily());
       myfnt.setSize(13);
       myfnt.setStyle(Font.BOLD);
        header.setAlignment(Element.ALIGN_LEFT);
        header.setFont(myfnt);
        header.add(title);

        return header;
    }

    public static PdfPCell CellSetCenter(PdfPCell cell, float c) {

        cell.setMinimumHeight(c);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        return cell;

    }

    public static void setNamesToCell(String date, String mode, String ReceiptNo, String desc, String amount, String balance, String term, String Year,
            PdfPTable table, int count) {

        Chunk count_1 = new Chunk("" + count);
        Chunk date_1 = new Chunk(date);
        Chunk mode_1 = new Chunk(mode);
        Chunk ReceiptNo_1 = new Chunk(ReceiptNo);
        Chunk desc_1 = new Chunk(desc);
        Chunk amount_1 = new Chunk(amount);
        Chunk balance_1 = new Chunk(balance);
        Chunk term_1 = new Chunk(term);
        Chunk Year_1 = new Chunk(Year);

        PdfPCell c0 = new PdfPCell();
        PdfPCell c1 = new PdfPCell();
        PdfPCell c2 = new PdfPCell();
        PdfPCell c3 = new PdfPCell();
        PdfPCell c4 = new PdfPCell();
        PdfPCell c5 = new PdfPCell();
        PdfPCell c6 = new PdfPCell();
        PdfPCell c7 = new PdfPCell();
        PdfPCell c8 = new PdfPCell();

        if (count % 2 != 0) {

            c0.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c1.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c2.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c3.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c4.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c5.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c6.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c7.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c8.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));

        } else {

            c0.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c1.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c2.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c3.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c4.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c5.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c6.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c7.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c8.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
        }

        c0.addElement(addtitletwo(count_1));

        c1.addElement(addtitletwo(date_1));

        c2.addElement(addtitletwo(mode_1));

        c3.addElement(addtitletwo(ReceiptNo_1));

        c4.addElement(addtitletwo(desc_1));

        c5.addElement(addtitletwo(amount_1));

        c6.addElement(addtitletwo(balance_1));

        c7.addElement(addtitletwo(term_1));

        c8.addElement(addtitletwo(Year_1));

        table.addCell(c0);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);
    }

    public static void setTableTitle(String date, String mode, String ReceiptNo, String desc, String amount, String balance, String term, String Year,
            PdfPTable table, String count) {

        //setTableTitle("Date", "Mode", "Receipt No","Description","Amount", "Balance","Term","Year",ContentSection, "S/No");
        Chunk count_1 = new Chunk(count);
        Chunk date_1 = new Chunk(date);
        Chunk mode_1 = new Chunk(mode);
        Chunk ReceiptNo_1 = new Chunk(ReceiptNo);
        Chunk desc_1 = new Chunk(desc);
        Chunk amount_1 = new Chunk(amount);
        Chunk balance_1 = new Chunk(balance);
        Chunk term_1 = new Chunk(term);
        Chunk Year_1 = new Chunk(Year);

        PdfPCell c0 = new PdfPCell();
        PdfPCell c1 = new PdfPCell();
        PdfPCell c2 = new PdfPCell();
        PdfPCell c3 = new PdfPCell();
        PdfPCell c4 = new PdfPCell();
        PdfPCell c5 = new PdfPCell();
        PdfPCell c6 = new PdfPCell();
        PdfPCell c7 = new PdfPCell();
        PdfPCell c8 = new PdfPCell();

        c0.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c1.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c2.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c3.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c4.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c5.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c6.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c7.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c8.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));

        c0.addElement(FormatTableColumnHeaders(count_1));

        c1.addElement(FormatTableColumnHeaders(date_1));

        c2.addElement(FormatTableColumnHeaders(mode_1));

        c3.addElement(FormatTableColumnHeaders(ReceiptNo_1));

        c4.addElement(FormatTableColumnHeaders(desc_1));

        c5.addElement(FormatTableColumnHeaders(amount_1));

        c6.addElement(FormatTableColumnHeaders(balance_1));

        c7.addElement(FormatTableColumnHeaders(term_1));

        c8.addElement(FormatTableColumnHeaders(Year_1));

        table.addCell(c0);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
        table.addCell(c4);
        table.addCell(c5);
        table.addCell(c6);
        table.addCell(c7);
        table.addCell(c8);
    }

    public static int getRedColor(String hex) {

        int r = Integer.valueOf(hex.substring(1, 3), 16);

        return r;
    }

    public static int getGreenColor(String hex) {

        int g = Integer.valueOf(hex.substring(3, 5), 16);

        return g;
    }

    public static int getBlackColor(String hex) {

        int b = Integer.valueOf(hex.substring(5, 7), 16);

        return b;
    }

    public static void showProcessComplete() {

        reportgenthree.ReportGenThree.LoadProcessCompleteBox();

    }
    
    
    public static Paragraph addtitletwo(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 9);
        //myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_LEFT);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }
}
