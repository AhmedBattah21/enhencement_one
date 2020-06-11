/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AppFuctions;

import Connection.sqlDataBaseConnection;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javax.swing.JOptionPane;
import reportgenthree.views.SystemNameSetterController;

/**
 *
 * @author Computer
 */
public class Singlereportproducer {

    private static List<InputStream> list = new ArrayList<>();
    private static String pathmain = "";
    private static String singlepathmain = "";
    private static String stdcourseone = "";
    private static StackPane stackpane = new StackPane();
    private static boolean created = false;

    //createPdfreport(regno,stdname,coursename,termname,unitcodescore,path)  
    public static boolean createPdfreport(String regno, String stdname, String stdcourse, String term, ObservableList Scores, String path, String YearOfStudy) {

        Document doc = new Document(PageSize.A4);
        doc.setMargins(40, 40, 30, 20);
        pathmain = path;

        stdcourseone = stdcourse;
        singlepathmain = path + "/" + regno + ".pdf";

        try {

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path + "//" + getCcode(regno) + "-" + regno + ".pdf"));
            String slash = "\\";

            reportgenthree.views.ProcessCompleteController.setPath(path + "\\" + getCcode(regno) + "-" + regno + ".pdf");

            list.add(new FileInputStream(path + "//" + getCcode(regno) + "-" + regno + ".pdf"));
            String src = path + "/" + getCcode(regno) + "-" + regno + ".pdf";

            doc.open();

            Rectangle layout = new Rectangle(PageSize.A4);

            layout.setBorderColor(BaseColor.BLACK);  //Border color
            layout.setBorderWidth(4);      //Border width  
            layout.setBorder(Rectangle.BOX);  //Border on 4 sides
            doc.add(layout);

            String home = System.getProperty("user.home") + "/" + "Documents";

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

            Chunk title6 = new Chunk("Email: " + SystemNameSetterController.getEmail());
            Chunk title7 = new Chunk("CELL:" + SystemNameSetterController.getContacts());
            Chunk title8 = new Chunk("Our Ref: " + SystemNameSetterController.getRef() + " " + year);
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
            //-----------------------------table of student name and Title...........................................................
            PdfPTable tablethree = new PdfPTable(3);
            tablethree.setWidthPercentage(100);
            tablethree.setWidths(new int[]{5, 3, 5});

            Chunk examname = new Chunk("END OF THE TERM REPORT FORM");
            Chunk NameHeadr = new Chunk("NAME OF STUDENT:...." + StudentsClass.getStdName(regno).toUpperCase() + "......");
            Chunk AdmNumber = new Chunk("ADM NO:......" + getCcode(regno).toUpperCase() + "/" + regno.toUpperCase() + ".....");

            Chunk courseheader = new Chunk("COURSE...." + stdcourse.toUpperCase());
            Chunk termheader = new Chunk(term.toUpperCase() + ".......");

            PdfPCell cellt9 = new PdfPCell();
            cellt9.addElement(addtitle(examname));
            cellt9.setColspan(3);
            tablethree.addCell(cellt9).setBorder(Rectangle.NO_BORDER);
            tablethree.setSpacingBefore(10);

            PdfPCell cellt10 = new PdfPCell(addtitletwo(NameHeadr));
            cellt10.setColspan(2);
            tablethree.addCell(cellt10).setBorder(Rectangle.NO_BORDER);

            PdfPCell cellt11 = new PdfPCell(addtitletwo(AdmNumber));
            tablethree.addCell(cellt11).setBorder(Rectangle.NO_BORDER);

            PdfPCell cellt12 = new PdfPCell(addtitletwo(courseheader));
            cellt12.setColspan(2);
            tablethree.addCell(cellt12).setBorder(Rectangle.NO_BORDER);

            PdfPCell cellt13 = new PdfPCell(addtitletwo(termheader));
            tablethree.addCell(cellt13).setBorder(Rectangle.NO_BORDER);

            int numberofcols = 7;
            //cells to create the headers
            PdfPTable tablecontents = new PdfPTable(numberofcols);
            tablecontents.setWidthPercentage(100);
            tablecontents.setWidths(new int[]{1, 4, 2, 2, 2, 2, 3});
            tablecontents.setSpacingBefore(5);

            PdfPCell c1 = new PdfPCell();
            c1.addElement(addtitleFour(new Chunk("S/NO")));

            tablecontents.addCell(c1);

            PdfPCell c2 = new PdfPCell();
            c2.addElement(addtitleFour(new Chunk("COURSE   MODULE")));
            tablecontents.addCell(c2);

            PdfPCell c3 = new PdfPCell();
            c3.addElement(addtitleFour(new Chunk("MID-TERM \n OUT OF 30")));
            tablecontents.addCell(c3);

            PdfPCell c4 = new PdfPCell();
            c4.addElement(addtitleFour(new Chunk("END TERM \nOUT OF 70")));
            tablecontents.addCell(c4);

            PdfPCell c5 = new PdfPCell();
            c5.addElement(addtitleFour(new Chunk("AVERAGE \n MARKS%")));
            tablecontents.addCell(c5);

            PdfPCell c6 = new PdfPCell();
            c6.addElement(addtitleFour(new Chunk("COMMENTS")));
            tablecontents.addCell(c6);

            PdfPCell c7 = new PdfPCell();
            c7.addElement(addtitleFour(new Chunk("SIGN/INITIAL")));
            tablecontents.addCell(c7);

            int numberofunits = Scores.size();
            String scores = Scores.toString().substring(1, Scores.size() - 1);
            ObservableList<String> my_comments = FXCollections.observableArrayList();
            int total_marks = 0;

            int count = 1;
            for (int value = 0; value < Scores.size(); value++) {

                String new_score = Scores.get(value).toString();
                String[] score = new_score.split("-");

                PdfPCell c8 = new PdfPCell();
                c8.addElement(addtitletwo(new Chunk(Integer.toString(count))));//FIRST CELL
                tablecontents.addCell(c8);

                PdfPCell c9 = new PdfPCell();
                c9.addElement(addtitletwo(new Chunk(score[0])));//SECOND CELL
                tablecontents.addCell(c9);

                PdfPCell c10 = new PdfPCell();
                c10.addElement(addtitletwo(new Chunk(score[1])));
                tablecontents.addCell(c10);

                PdfPCell c101 = new PdfPCell();
                c101.addElement(addtitletwo(new Chunk(score[2])));
                tablecontents.addCell(c101);

                int totalscore = 0;

                if (score[1].equals("-") && score[2].equals("-")) {

                    totalscore = 0;

                } else if (score[1].equals("-") && !score[2].equals("-")) {

                    totalscore = Integer.parseInt(score[2]);
                } else if (score[2].equals("-") && !score[1].equals("-")) {

                    totalscore = Integer.parseInt(score[1]);
                } else if (!score[1].equals("-") && !score[2].equals("-")) {

                    int score1 = Integer.parseInt(score[2]);
                    int score2 = Integer.parseInt(score[1]);
                    totalscore = score1 + score2;
                    total_marks = total_marks + totalscore;
                }

                PdfPCell c102 = new PdfPCell();
                c102.addElement(addtitletwo(new Chunk(Integer.toString(totalscore))));
                tablecontents.addCell(c102);

                PdfPCell c11 = new PdfPCell();
                c11.addElement(addtitletwo(new Chunk(getComment(totalscore))));
                tablecontents.addCell(c11);
                my_comments.add(getComment(totalscore));

                PdfPCell c12 = new PdfPCell();
                c12.addElement(addtitletwo(new Chunk(score[4])));
                tablecontents.addCell(c12);

                count++;

            }

            //table to hold overarl results
            PdfPTable overalresults = new PdfPTable(1);
            overalresults.setSpacingBefore(10);
            overalresults.setWidthPercentage(100);
            PdfPCell cellresults1 = new PdfPCell();
            PdfPCell cellresults2 = new PdfPCell();

            cellresults1.addElement(addtitleFour(new Chunk("OVERALL RESULTS")));
            removeborders(cellresults1);
            overalresults.addCell(cellresults1);

            cellresults2.addElement(new Chunk(getAgregate(my_comments, total_marks, stdcourse)));

            getLine(cellresults2);
            overalresults.addCell(cellresults2);

            //table to hold overarl results
            PdfPTable Tremarks = new PdfPTable(6);
            Tremarks.setSpacingBefore(5);
            Tremarks.setWidthPercentage(100);

            PdfPCell r1 = new PdfPCell();
            r1.setColspan(6);
            r1.addElement(addtitleFour(new Chunk("INSTRUCTORS REMARKS")));
            removeborders(r1);
            Tremarks.addCell(r1);

            PdfPCell r2 = new PdfPCell();
            r2.setColspan(6);
            r2.addElement(addtitletwo(new Chunk("_")));
            Tremarks.addCell(getLine(r2));

            PdfPCell r3 = new PdfPCell();
            r3.setColspan(5);
            r3.addElement(addtitletwo(new Chunk("_")));
            Tremarks.addCell(getLine(r3));

            PdfPCell r4 = new PdfPCell();
            r4.addElement(addtitleFour(new Chunk("SIGN")));
            Tremarks.addCell(getLine(r4));

            //STATING OF P TABLE HERE
            PdfPTable Premarks = new PdfPTable(6);
            Premarks.setSpacingBefore(5);
            Premarks.setWidthPercentage(100);

            PdfPCell r5 = new PdfPCell();
            r5.setColspan(6);
            r5.addElement(addtitleFour(new Chunk("PRINCIPAL'S REMARKS")));
            removeborders(r5);
            Premarks.addCell(r5);

            PdfPCell r6 = new PdfPCell();
            r6.setColspan(6);
            r6.addElement(addtitletwo(new Chunk("_")));
            Premarks.addCell(getLine(r6));

            PdfPCell r8 = new PdfPCell();
            r8.setColspan(5);
            r8.addElement(addtitletwo(new Chunk("_")));
            Premarks.addCell(getLine(r8));

            PdfPCell r9 = new PdfPCell();
            r9.addElement(addtitleFour(new Chunk("SIGN")));
            Premarks.addCell(getLine(r9));

            //
            //Footer
            PdfPTable Fremarks = new PdfPTable(3);
            Fremarks.setSpacingBefore(5);
            Fremarks.setWidthPercentage(100);

            PdfPCell r10 = new PdfPCell();
            r10.addElement(addtitleFour(new Chunk("FEE BALANCE")));
            Fremarks.addCell(getLine(r10));

            PdfPCell r11 = new PdfPCell();
            r11.addElement(addtitleFour(new Chunk("NEXT TERM FEE")));
            Fremarks.addCell(getLine(r11));

            PdfPCell r12 = new PdfPCell();
            r12.addElement(addtitleFour(new Chunk("OPENING DATE")));
            Fremarks.addCell(getLine(r12));

            PdfPCell r13 = new PdfPCell();
            r13.addElement(addtitletwo(new Chunk("Kshs.")));
            //r13.addElement(addtitletwo(new Chunk("Kshs." + getFeeBalance(term, regno) + ".00")));
            Fremarks.addCell(r13);

            PdfPCell r14 = new PdfPCell();
            r14.addElement(addtitletwo(new Chunk("Kshs.")));
            //r14.addElement(addtitletwo(new Chunk("Kshs." + getFeeBalanceTwo(term, regno) + ".00")));
            Fremarks.addCell(r14);

            PdfPCell r15 = new PdfPCell();
            r15.addElement(addtitletwo(new Chunk("..")));
            Fremarks.addCell(r15);

            PdfPTable Footer = new PdfPTable(1);
            Footer.setSpacingBefore(2);
            Footer.setWidthPercentage(100);

            PdfPCell r16 = new PdfPCell();
            r16.addElement(addtitle(new Chunk("THIS REPORT FORM HAS BEEN ISSUED WITH NO ALTERATION")));
            r16.setBorder(5);
            r16.setBorder(Rectangle.NO_BORDER);
            r16.setBorderWidthTop(5);
            Footer.addCell(r16);

            doc.add(table);
            doc.add(tabletwo);
            doc.add(tablethree);
            doc.add(tablecontents);
            doc.add(overalresults);

            doc.add(Tremarks);
            doc.add(Premarks);
            doc.add(Fremarks);
            doc.add(Footer);

            doc.close();
            writer.close();
            
            add_watermark(src);

            created = true;
            total_marks = 0;
            my_comments.clear();
            

        } catch (DocumentException exc) {

            JOptionPane.showConfirmDialog(null, exc);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Singlereportproducer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Singlereportproducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return created;
    }

    public static void add_watermark(String src) {

        try {

            PdfReader reader = new PdfReader(src);
            int n = reader.getNumberOfPages();
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(src));
            // text watermark
            Font f = new Font(FontFamily.HELVETICA, 30);
            Phrase p = new Phrase("My watermark (text)", f);
            // image watermark
            String home = System.getProperty("user.home") + "/" + "Documents";

            Image img = Image.getInstance(home + "/ReportGenThree/school/two.png");
            float w = img.getScaledWidth();
            float h = img.getScaledHeight();
            // transparency
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.5f);
            // properties
            PdfContentByte over;
            Rectangle pagesize;
            float x, y;
            // loop over every page
            for (int i = 1; i <= n; i++) {
                pagesize = reader.getPageSizeWithRotation(i);
                x = (pagesize.getLeft() + pagesize.getRight()) / 2;
                y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                over = stamper.getOverContent(i);
                over.saveState();
                over.setGState(gs1);
                if (i % 2 == 1) {
                    ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 0);
                } else {
                    over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                }
                over.restoreState();
            }
            stamper.close();
            reader.close();

        } catch (IOException | DocumentException exc) {

            System.out.println("Error " + exc);

        }
    }

    public static Paragraph addtitle(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 11);
        myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_CENTER);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }

    public static Paragraph addtitletwo(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        //myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_LEFT);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }

    public static Paragraph addtitleFour(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_LEFT);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }

    public static Paragraph addtitlethree(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        header.setAlignment(Element.ALIGN_CENTER);
        header.setFont(myfnt);

        header.add(title);

        return header;
    }

    public static PdfPCell getLine(PdfPCell cell) {

        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);

        return cell;
    }

    public static PdfPCell getLine_withdata(PdfPCell cell, String data) {

        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.addElement(new Chunk());
        cell.addElement(new Chunk(data));

        return cell;
    }

    public static PdfPCell removeborders(PdfPCell cell) {
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);

        return cell;
    }

    public static String getComment(int score) {
        String comment = "poor";

        if (score >= 80) {

            comment = "Distinction";

        } else if (score >= 60) {

            comment = "Credit";

        } else if (score >= 40) {

            comment = "Pass";

        } else if (score >= 30) {

            comment = "Reffer";

        } else {

            comment = "Fail";

        }

        return comment;
    }

    public static void mergerpdfs() {

        try {

            OutputStream out = new FileOutputStream(new File(pathmain + "/" + stdcourseone + ".pdf"));

            doMerge(list, out);

        } catch (FileNotFoundException e) {

            JOptionPane.showMessageDialog(null, e);

        } catch (IOException e) {

            JOptionPane.showMessageDialog(null, e);

        } catch (DocumentException ex) {

            JOptionPane.showMessageDialog(null, ex);
        }

    }

    public static void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }

        outputStream.flush();
        document.close();
        outputStream.close();
    }

    public static void showProcessComplete() {

        reportgenthree.ReportGenThree.LoadProcessCompleteBox();

    }

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

    public static int getFeeBalance(String term, String acc_user) {

        String bname = "";
        String year = getStYear(acc_user);

        if (term.equals("Term One")) {

            bname = "Balance_t1";

        } else if (term.equals("Term Two")) {

            bname = "Balance_t2";

        }

        if (year.equals("First Year")) {

            year = "First Year";

        } else if (year.equals("Second Year")) {

            year = "Second Year";

        }

        int balance = getTermFeeBalance(acc_user, bname, year);

        return balance;

    }

    public static int getFeeBalanceTwo(String term, String acc_user) {

        String bname = "";

        String year = getStYear(acc_user);

        if (term.equals("Term One")) {

            bname = "Balance_t2";

        } else if (term.equals("Term Two")) {

            bname = "Balance_t3";

        }

        if (year.equals("First Year")) {

            year = "First Year";

        } else if (year.equals("Second Year")) {

            year = "Second Year";

        }

        int balance = getTermFeeBalance(acc_user, bname, year);

        return balance;

    }

    public static int getTermFeeBalance(String acc_name, String term, String acc_year) {

        int amount = 0;

        String query = "SELECT * FROM Fee_Table WHERE Acc_user = '" + acc_name + "' AND Acc_year = '" + acc_year + "' ";

        Connection conn = sqlDataBaseConnection.sqliteconnect();

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                amount = Integer.parseInt(rst.getString(term));

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return amount;

    }

    public static String getStYear(String regno) {

        String query = "SELECT YearOfStudy FROM Students_2017 WHERE StudentRegCode = '" + regno + "'";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        String year = "";

        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);

            if (rst.next()) {

                year = rst.getString("YearOfStudy");

            }

            conn.close();

        } catch (SQLException exc) {

            Functions.showAlert(Alert.AlertType.ERROR, "M.E.R.M.S", "Action Failed",
                    "Sytem has encountered an error \n"
                    + "This is An FXL Error Please Cntact Developer If It Persist \n Error  " + exc);

        }

        return year;

    }

    public static StackPane getStackpane() {
        return stackpane;
    }

    public static void setStackpane(StackPane stackpane) {
        Singlereportproducer.stackpane = stackpane;
    }

    public static String getAgregate(List<String> comments, int total, String course) {

        String agregate = "";

        int countr = 0;
        int countf = 0;
        int devider = StudentsClass.get_numbunits(course);

        int count = 0;
        for (int i = 0; i < comments.size(); i++) {

            if (comments.get(i).equals("Reffer")) {

                countr++;

            }

            if (comments.get(i).equals("Fail")) {

                countf++;
            }
        }

        if (countf <= 3 && countf > 0) {

            agregate = "REFFER";

        } else if (countf > 3) {

            agregate = "FAIL";

        } else if (countr > 3) {

            agregate = "FAIL";

        } else if (countr <= 3 && countr > 0) {

            agregate = "REFFER";

        } else {

            double average = total / devider;
            agregate = getComment(average);

        }

        return agregate;
    }

    public static String getComment(double score) {
        String comment = "poor";

        if (score >= 80) {

            comment = "DISTINCTION";

        } else if (score >= 75) {

            comment = "DISTINCTION";

        } else if (score >= 70) {

            comment = "CREDIT";

        } else if (score >= 65) {

            comment = "CREDIT";

        } else if (score >= 60) {

            comment = "CREDIT";

        } else if (score >= 55) {

            comment = "PASS";

        } else if (score >= 50) {

            comment = "PASS";

        } else if (score >= 45) {

            comment = "PASS";

        } else if (score >= 40) {

            comment = "PASS";

        } else if (score >= 35) {

            comment = "REFFER";

        } else if (score >= 30) {

            comment = "REFFER";

        } else if (score >= 20) {

            comment = "REFFER";

        } else {

            comment = "FAIL";

        }

        return comment;
    }

}
