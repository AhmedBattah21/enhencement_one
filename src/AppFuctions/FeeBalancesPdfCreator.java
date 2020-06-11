/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppFuctions;

import static AppFuctions.Singlereportproducer.addtitle;
import static AppFuctions.Singlereportproducer.addtitletwo;
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
public class FeeBalancesPdfCreator {

    private static String descriptionColor = null;

    private static String row1 = "";
    private static String row2 = "";
    private static String BackGroundHeaderColor = null;
    private static String HeaderFontColor = null;

    private static DescriptionTakerController desp = null;

    public static void creator(String query, String path, String term) {

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

            FileOutputStream out = new FileOutputStream(path + "/Student Fee Statements -" + formatter.format(date) + ".pdf");
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            reportgenthree.views.ProcessCompleteController.setPath(path + "/Student Fee Statements -" + formatter.format(date) + ".pdf");

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
            PdfPTable ContentSection = new PdfPTable(4);
            ContentSection.setWidthPercentage(100);
            ContentSection.setSpacingBefore(5);
            ContentSection.setWidths(new int[]{4, 10, 10, 10});
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

                        setTableTitle("Full Name", "Admission Number", "Fee Balance", ContentSection, "S/No");

                    }

                    String classname = rst.getString("YearOfStudy");
                    String admnumber = rst.getString("StudentRegCode");
                    String stname = rst.getString("StudentName");
                    String ccode = rst.getString("Ccode");

                    String feebalance = rst.getString(term);

                    totalbalance = totalbalance + Integer.parseInt(feebalance);

                    if (Integer.parseInt(feebalance) > 0) {

                        setNamesToCell(stname, ccode + "/" + admnumber, "Kshs. " + feebalance + ".00", ContentSection, count);
                        count++;

                    }

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

                Chunk total_balance = new Chunk("Kshs."+Integer.toString(totalbalance)+".00");
                Chunk total_title = new Chunk("Total Outstanding Balance");

                PdfPCell c0 = new PdfPCell();
                c0.setColspan(3);
                c0.addElement(styleone(total_title));
                PdfPCell c1 = new PdfPCell();
                c1.addElement(styleTwo(total_balance));

                doc.add(table);
                doc.add(tabletwo);
                doc.add(DescriptionSection);
                ContentSection.addCell(c0);
                ContentSection.addCell(c1);
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

    public static Paragraph styleone(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 13);
        myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_RIGHT);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }
    
    public static Paragraph styleTwo(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 13);
        myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_LEFT);
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
        myfnt.setSize((float) DescriptionTakerController.getTableHeaderFontSize());

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

    public static void setNamesToCell(String stname, String adm, String classname, PdfPTable table, int count) {

        Chunk count_1 = new Chunk(Integer.toString(count));
        Chunk stname_1 = new Chunk(stname);
        Chunk adm_1 = new Chunk(adm);
        Chunk classname_1 = new Chunk(classname);

        PdfPCell c0 = new PdfPCell();

        PdfPCell c1 = new PdfPCell();
        PdfPCell c2 = new PdfPCell();
        PdfPCell c3 = new PdfPCell();

        if (count % 2 != 0) {

            c0.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c1.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c2.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));
            c3.setBackgroundColor(new BaseColor(Functions.getRedColor(row1), Functions.getGreenColor(row1), Functions.getBlackColor(row1), 1));

        } else {

            c0.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c1.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c2.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
            c3.setBackgroundColor(new BaseColor(Functions.getRedColor(row2), Functions.getGreenColor(row2), Functions.getBlackColor(row2), 1));
        }

        c0.addElement(addtitletwo(count_1));

        c1.addElement(addtitletwo(stname_1));

        c2.addElement(addtitletwo(adm_1));

        c3.addElement(addtitletwo(classname_1));

        table.addCell(c0);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
    }

    public static void setTableTitle(String stname, String adm, String classname, PdfPTable table, String count) {

        Chunk count_1 = new Chunk(count);
        Chunk stname_1 = new Chunk(stname);
        Chunk adm_1 = new Chunk(adm);
        Chunk classname_1 = new Chunk(classname);

        PdfPCell c0 = new PdfPCell();
        PdfPCell c1 = new PdfPCell();
        PdfPCell c2 = new PdfPCell();
        PdfPCell c3 = new PdfPCell();

        c0.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c1.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c2.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));
        c3.setBackgroundColor(new BaseColor(getRedColor(HeaderFontColor), getGreenColor(HeaderFontColor), getBlackColor(HeaderFontColor), 1));

        c0.addElement(FormatTableColumnHeaders(count_1));

        c1.addElement(FormatTableColumnHeaders(stname_1));

        c2.addElement(FormatTableColumnHeaders(adm_1));

        c3.addElement(FormatTableColumnHeaders(classname_1));

        table.addCell(c0);
        table.addCell(c1);
        table.addCell(c2);
        table.addCell(c3);
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
}
