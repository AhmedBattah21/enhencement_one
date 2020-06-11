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
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
import reportgenthree.views.EndstageDetailsTakerController;
import reportgenthree.views.SystemNameSetterController;

/**
 *
 * @author Computer
 */
public class MultipleEndStageproducer {

    private static List<InputStream> list = new ArrayList<>();
    private static String pathmain = "";
    private static String singlepathmain = "";
    private static String stdcourseone = "";

    //createPdfreport(regno,stdname,coursename,termname,unitcodescore,path)  
    public static void createPdfreport(String regno, String stdname, String stdcourse, String term, ObservableList Scores, String path, String stage, String dpt) {

        Document doc = new Document(PageSize.A4);
        doc.setMargins(40, 40, 30, 20);
        pathmain = path;

        stdcourseone = stdcourse;
        singlepathmain = path + "/" + regno + ".pdf";

        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter fmat = DateTimeFormatter.ofPattern("dd-MMM-yyy");

        try {

            

            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(path + "//" + getCcode(regno) + "-" + regno + "-Transcript.pdf"));
            String slash = "\\";

            //reportgenthree.views.ProcessCompleteMultipleEndStageController.setPath(path + "\\" + getCcode(regno) + "-" + regno + "-Transcript.pdf");

            list.add(new FileInputStream(path + "//" + getCcode(regno) + "-" + regno + "-Transcript.pdf"));

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

            PdfPTable table1 = new PdfPTable(3);
            table1.setWidthPercentage(100);
            table1.setWidths(new int[]{2, 8, 2});

            PdfPCell cell = new PdfPCell(img, true);

            table1.addCell(cell);//.setBorder(Rectangle.NO_BORDER);//first cell to hold image

            PdfPCell celltwo = new PdfPCell();
            celltwo.addElement(addtitle(titleone));
            celltwo.addElement(addtitle(title2));
            celltwo.addElement(addtitle(title3));
            celltwo.addElement(addtitle(title4));

            table1.addCell(celltwo).setBorder(Rectangle.NO_BORDER);;//fsecond cell to hold titles

            PdfPCell cellthree = new PdfPCell(imgtwo, true);
            table1.addCell(cellthree);//.setBorder(Rectangle.NO_BORDER);;//fsecond cell to hold titles

            //------------------------------------------------the second values.................................................
            PdfPTable table2 = new PdfPTable(3);
            table2.setWidthPercentage(100);
            table2.setSpacingAfter(10);
            table2.setWidths(new int[]{5, 3, 5});

            String year = Integer.toString(LocalDate.now().getYear());

            Chunk title6 = new Chunk("Email: "+SystemNameSetterController.getEmail());
            Chunk title7 = new Chunk("CELL:"+SystemNameSetterController.getContacts());
            Chunk title8 = new Chunk("Our Ref: "+SystemNameSetterController.getRef() +" "+ year);
            Chunk title9 = new Chunk(SystemNameSetterController.getbox());
            Chunk title91 = new Chunk(SystemNameSetterController.getPlace());

            PdfPCell cellt6 = new PdfPCell();
            cellt6.addElement(addtitletwo(title6));
            cellt6.addElement(addtitletwo(title7));
            cellt6.addElement(addtitletwo(title8));
            table2.addCell(cellt6).setBorder(Rectangle.NO_BORDER);

            PdfPCell cellt7 = new PdfPCell();
            table2.addCell(cellt7).setBorder(Rectangle.NO_BORDER);

            PdfPCell cellt8 = new PdfPCell();
            cellt8.addElement(addtitletwo(title9));
            cellt8.addElement(addtitletwo(title91));
            table2.addCell(cellt8).setBorder(Rectangle.NO_BORDER);

            //------------------------------end of section one---------------------------------------
            //.........................section two.............................
            PdfPTable table3 = new PdfPTable(1);
            table3.setWidthPercentage(100);
            table3.setWidths(new int[]{10});

            Chunk t1 = new Chunk("THIS IS TO CERTIFY THAT THE CANDIDATE NAMED BELOW SAT FOR STAGE " + stage + " EXAM IN "
                    + "THE SUBJECTS NAMED BELOW AND QUALIFIED FOR THE AWARD OF PROVINSIONAL EXAMINATION ");

            PdfPCell ct1 = new PdfPCell();
            ct1.addElement(addtitletwo(t1));

            table3.addCell(ct1).setBorder(Rectangle.NO_BORDER);

            //end of section two.............................................
            //.......................................start of section three............................
            PdfPTable table13 = new PdfPTable(1);
            table13.setWidthPercentage(100);
            table13.setSpacingAfter(10);
            table13.setWidths(new int[]{10});

            Chunk tt1 = new Chunk("TRANSCRIPT");
            tt1.setUnderline(0.1f, -2f);
            Chunk tt2 = new Chunk("END OF STAGE NAVSET LEVEL ONE EXAMINATION");
            tt2.setUnderline(0.1f, -2f);

            PdfPCell ctt1 = new PdfPCell();
            ctt1.addElement(addtitlefive(tt1));

            PdfPCell ctt2 = new PdfPCell();
            ctt2.addElement(addtitlefive(tt2));

            table13.addCell(ctt1).setBorder(Rectangle.NO_BORDER);
            table13.addCell(ctt2).setBorder(Rectangle.NO_BORDER);

            //end of section three
            //-----------------------------table1 of student name and Title...........................................................
            PdfPTable table4 = new PdfPTable(2);
            table4.setWidthPercentage(100);
            table4.setSpacingBefore(10);
            table4.setWidths(new int[]{5, 5});

            Chunk NameHeadr = new Chunk("NAME : " + stdname.toUpperCase());

            Chunk AdmNumber = new Chunk("ADMISSION NO:  " + getCcode(regno).toUpperCase() + "/" + regno.toUpperCase());

            Chunk department = new Chunk("DEPARTMENT :" + EndstageDetailsTakerController.getDepartment().toUpperCase());

            Chunk cstage = new Chunk("STAGE: " + stage);

            Chunk attendance = new Chunk("% OF ATTENDANCE:" + EndstageDetailsTakerController.getAttendance() + "%");

            Chunk courseheader = new Chunk("COURSE/TRADE:" + stdcourse.toUpperCase());

            Chunk dfrom = new Chunk("DURATION: FROM: " + DOR(regno));
            Chunk dto = new Chunk("TO: " + EndstageDetailsTakerController.getDateto());

            PdfPCell ct41 = new PdfPCell();
            ct41.addElement(addtitleFour(NameHeadr));
            table4.addCell(ct41).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct42 = new PdfPCell(addtitleFour(AdmNumber));
            table4.addCell(ct42).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct43 = new PdfPCell(addtitleFour(department));
            table4.addCell(ct43).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct44 = new PdfPCell(addtitleFour(courseheader));
            table4.addCell(ct44).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct45 = new PdfPCell(addtitleFour(cstage));
            table4.addCell(ct45).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct46 = new PdfPCell(addtitleFour(attendance));
            table4.addCell(ct46).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct47 = new PdfPCell(addtitleFour(dfrom));
            table4.addCell(ct47).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct48 = new PdfPCell(addtitleFour(dto));
            table4.addCell(ct48).setBorder(Rectangle.NO_BORDER);

            //section two
            int numberofcols = 7;
            //cells to create the headers
            PdfPTable table5 = new PdfPTable(numberofcols);
            table5.setWidthPercentage(100);
            table5.setWidths(new int[]{1, 4, 2, 2, 2, 2, 3});
            table5.setSpacingBefore(10);

            PdfPCell c1 = new PdfPCell();
            c1.addElement(addtitleFour(new Chunk("S/NO")));

            table5.addCell(c1);

            PdfPCell c2 = new PdfPCell();
            c2.addElement(addtitleFour(new Chunk("PAPAER CODE")));
            table5.addCell(c2);

            PdfPCell c3 = new PdfPCell();
            c3.addElement(addtitleFour(new Chunk("SUBJECTS")));
            table5.addCell(c3);

            PdfPCell c4 = new PdfPCell();
            c4.addElement(addtitleFour(new Chunk("%SCORE")));
            table5.addCell(c4);

            PdfPCell c5 = new PdfPCell();
            c5.addElement(addtitleFour(new Chunk("GRADE")));
            table5.addCell(c5);

            PdfPCell c6 = new PdfPCell();
            c6.addElement(addtitleFour(new Chunk("POINTS")));
            table5.addCell(c6);

            PdfPCell c7 = new PdfPCell();
            c7.addElement(addtitleFour(new Chunk("COMMENTS")));
            table5.addCell(c7);

            int numberofunits = Scores.size();
            String scores = Scores.toString().substring(1, Scores.size() - 1);
            
            ArrayList<String> records = new ArrayList<>();
            

            int count = 1;
            records.clear();
            
            int totalscore = 0;
            
            for (int value = 0; value < Scores.size(); value++) {

                String new_score = Scores.get(value).toString();
                String[] score = new_score.split("-");

                PdfPCell c8 = new PdfPCell();
                c8.addElement(addtitletwo(new Chunk(Integer.toString(count))));//FIRST CELL
                table5.addCell(c8);

                PdfPCell c9 = new PdfPCell();
                c9.addElement(addtitletwo(new Chunk(score[2])));//SECOND CELL
                table5.addCell(c9);

                PdfPCell c10 = new PdfPCell();
                c10.addElement(addtitletwo(new Chunk(score[0])));
                table5.addCell(c10);

                PdfPCell c101 = new PdfPCell();
                c101.addElement(addtitletwo(new Chunk(score[1])));
                table5.addCell(c101);

                PdfPCell c102 = new PdfPCell();
                c102.addElement(addtitletwo(new Chunk(getGrading(Integer.parseInt(score[1])))));
                table5.addCell(c102);

                PdfPCell c11 = new PdfPCell();
                c11.addElement(addtitletwo(new Chunk(getPoints(Integer.parseInt(score[1])))));
                table5.addCell(c11);

                PdfPCell c12 = new PdfPCell();
                c12.addElement(addtitletwo(new Chunk(getComment(Integer.parseInt(score[1])))));
                table5.addCell(c12);
                
                
                records.add(getComment(Integer.parseInt(score[1])));
                
                totalscore = totalscore+Integer.parseInt(score[1]);
                
                
                count++;

            }
            
           

            //.....................table 51 ----------------------------------------------
            Chunk agregate = new Chunk("AGGREGATE: "+getAgregate(records,totalscore));

            PdfPTable table51 = new PdfPTable(1);
            table51.setSpacingBefore(5);
            table51.setWidthPercentage(100);

            PdfPCell c51 = new PdfPCell();
            c51.addElement(addtitletwo(agregate));
            table51.addCell(c51).setBorder(Rectangle.NO_BORDER);

            //----------------------end of table 51.....................................
            //................................table1 6.....................................
            PdfPTable table6 = new PdfPTable(2);
            table6.setSpacingBefore(10);
            table6.setWidthPercentage(100);

            Chunk ch0 = new Chunk("GRADING");

            Chunk ch1 = new Chunk("80-100  A     [12]   -DISTINCTION   1");
            Chunk ch2 = new Chunk("75-79    A-    [11]   -DISTINCTION   2");
            Chunk ch3 = new Chunk("70-74    B+    [10]   -CREDIT          3");
            Chunk ch4 = new Chunk("65-69    B      [9]     -CREDIT          4");
            Chunk ch5 = new Chunk("60-64    B-     [8]     -CREDIT          5");
            Chunk ch6 = new Chunk("55-59    C+    [7]     -PASS             6");
            Chunk ch7 = new Chunk("50-54    C      [6]     -PASS             6");
            Chunk ch8 = new Chunk("45-49    C-     [5]     -PASS             7");
            Chunk ch9 = new Chunk("40-44    C-     [4]     -PASS             7");
            Chunk ch10 = new Chunk("35-39   D+    [4]     -REFFER         8");
            Chunk ch11 = new Chunk("30-34   D     [3]     -REFFER          8");
            Chunk ch12 = new Chunk("20-29   D-    [2]     -REFFER          8");
            Chunk ch13 = new Chunk("0-19     E     [1]     -FAIL               9");
            Chunk ch14 = new Chunk("ABS ABSENT");
            Chunk ch15 = new Chunk("CRNM - COURSE NOT MET");
            Chunk ch16 = new Chunk("CNC -COURSE NOT COMPLETED");
            Chunk ch17 = new Chunk("X - PASS AFTER RESULT");

            PdfPCell ct60 = new PdfPCell(addtitletwo(ch0));
            ct60.setColspan(2);
            table6.addCell(ct60).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct61 = new PdfPCell(addtitletwo(ch1));
            table6.addCell(ct61).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct62 = new PdfPCell(addtitletwo(ch10));
            table6.addCell(ct62).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct63 = new PdfPCell(addtitletwo(ch2));
            table6.addCell(ct63).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct64 = new PdfPCell(addtitletwo(ch11));
            table6.addCell(ct64).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct65 = new PdfPCell(addtitletwo(ch3));
            table6.addCell(ct65).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct66 = new PdfPCell(addtitletwo(ch12));
            table6.addCell(ct66).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct67 = new PdfPCell(addtitletwo(ch4));
            table6.addCell(ct67).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct68 = new PdfPCell(addtitletwo(ch13));
            table6.addCell(ct68).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct69 = new PdfPCell(addtitletwo(ch5));
            table6.addCell(ct69).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct610 = new PdfPCell(addtitletwo(ch14));
            table6.addCell(ct610).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct611 = new PdfPCell(addtitletwo(ch6));
            table6.addCell(ct611).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct612 = new PdfPCell(addtitletwo(ch15));
            table6.addCell(ct612).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct613 = new PdfPCell(addtitletwo(ch7));
            table6.addCell(ct613).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct614 = new PdfPCell(addtitletwo(ch16));
            table6.addCell(ct614).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct615 = new PdfPCell(addtitletwo(ch8));
            table6.addCell(ct615).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct616 = new PdfPCell(addtitletwo(ch17));
            table6.addCell(ct616).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct617 = new PdfPCell(addtitletwo(ch9));
            table6.addCell(ct617).setBorder(Rectangle.NO_BORDER);

            //------------------------------------------------------------------------------
            //table1 to hold overarl results
            PdfPTable table7 = new PdfPTable(2);
            table7.setSpacingBefore(10);
            table7.setWidthPercentage(100);

            Chunk chunk1 = new Chunk("PRINCIPAL: " + EndstageDetailsTakerController.getPname().toUpperCase());
            Chunk chunk2 = new Chunk("HEAD OF DEPARTMENT: " + EndstageDetailsTakerController.getHDepartment().toUpperCase());

            Chunk chunk3 = new Chunk("SIGNATURE:........................................");
            Chunk chunk4 = new Chunk("SIGNATURE:........................................");

            Chunk chunk5 = new Chunk("DATE:.............................................");
            Chunk chunk6 = new Chunk("DATE:.............................................");

            PdfPCell ct71 = new PdfPCell(addtitleFour(chunk1));
            table7.addCell(ct71).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct72 = new PdfPCell(addtitleFour(chunk2));
            table7.addCell(ct72).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct73 = new PdfPCell(addtitleFour(chunk3));
            table7.addCell(ct73).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct74 = new PdfPCell(addtitleFour(chunk4));
            table7.addCell(ct74).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct75 = new PdfPCell(addtitleFour(chunk5));
            table7.addCell(ct75).setBorder(Rectangle.NO_BORDER);

            PdfPCell ct76 = new PdfPCell(addtitleFour(chunk6));
            table7.addCell(ct76).setBorder(Rectangle.NO_BORDER);

            PdfPTable Footer = new PdfPTable(1);
            Footer.setSpacingBefore(10);
            Footer.setWidthPercentage(100);

            PdfPCell r16 = new PdfPCell();
            r16.addElement(addtitleFour(new Chunk("THIS PROVISIONAL TRANSCRIPT IS ISSUED WITHOUT ANY ALTERATION OR ERASURE"
                    + " WHATSOEVER AND IS NOT A CERTIFICATE . THE ACADEMIC BOARD OF MUYEYE VOCATIONAL TRAINING CENTRE RESERVES "
                    + "THE RIGHT TO CORRECT THE INFORMATION GIVEN ON THE TRANSCRIPT")));
            r16.setBorder(5);
            r16.setBorder(Rectangle.NO_BORDER);
            r16.setBorderWidthTop(5);
            Footer.addCell(r16);

            doc.add(table1);
            doc.add(table2);
            doc.add(table3);
            doc.add(table13);
            doc.add(table4);
            doc.add(table5);
            doc.add(table51);
            doc.add(table6);
            doc.add(table7);

            doc.add(Footer);

            doc.close();
            writer.flush();
            writer.close();

        } catch (DocumentException exc) {

            JOptionPane.showConfirmDialog(null, exc);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(MultipleEndStageproducer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(MultipleEndStageproducer.class.getName()).log(Level.SEVERE, null, ex);
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

    public static Paragraph addtitlefive(Chunk title) {

        Paragraph header = new Paragraph();

        Font myfnt = new Font(Font.FontFamily.TIMES_ROMAN, 10);
        //myfnt.setStyle(Font.BOLD);

        header.setAlignment(Element.ALIGN_CENTER);
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

    public static PdfPCell removeborders(PdfPCell cell) {
        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);

        return cell;
    }

    public static void mergerpdfs() {

        Random rand = new Random();

        int r = rand.nextInt(500) + 1;

        doMerge(list);

    }

    public static void doMerge(List<InputStream> list) {

        OutputStream outputStream = null;

        Random rand = new Random();
        int r = rand.nextInt(500) + 1;

        System.out.println("RandomOne " + r);

        try {

            outputStream = new FileOutputStream(new File(pathmain + "/" + stdcourseone + "-" + r + ".pdf"));

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

            reportgenthree.views.ProcessCompleteMultipleEndStageController.setPath(pathmain + "/" + stdcourseone + "-" + r + ".pdf");
            
            reportgenthree.views.ProcessCompleteMultipleEndStageController.setMainPath(pathmain);
            
            list.clear();

        } catch (FileNotFoundException | DocumentException ex) {

            System.out.println(" " + ex);

        } catch (IOException ex) {

            System.out.println("Here " + ex);

        } finally {

            try {
                outputStream.close();

            } catch (IOException ex) {

                System.out.println(" " + ex);
            }
        }

    }

    public static void cleanup(OutputStream out) {

        try {
            out.close();
        } catch (IOException ex) {

            System.out.print("error " + ex);
        }

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

    public static String DOR(String regcode) {

        String query = "SELECT DOR FROM students_2017 WHERE StudentRegCode = '" + regcode + "'";
        String ccode = "";

        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {

            Statement st = conn.createStatement();
            ResultSet rst = st.executeQuery(query);
            ccode = rst.getString("DOR");
            conn.close();

        } catch (SQLException exc) {

            System.out.println("Error " + exc);
        }

        return ccode;
    }

    public static String getGrade(int score) {
        String comment = "poor";

        if (score >= 80) {

            comment = "A";

        } else if (score >= 75) {

            comment = "A-";

        } else if (score >= 70) {

            comment = "B+";

        } else if (score >= 65) {

            comment = "B";

        } else if (score >= 60) {

            comment = "B-";

        } else if (score >= 55) {

            comment = "C+";

        } else if (score >= 50) {

            comment = "C";

        } else if (score >= 45) {

            comment = "C-";

        } else if (score >= 40) {

            comment = "C-";

        } else if (score >= 35) {

            comment = "D+";

        } else if (score >= 30) {

            comment = "D";

        } else if (score >= 20) {

            comment = "D-";

        } else {

            comment = "E";

        }

        return comment;
    }

    public static String getPoints(int score) {

        String comment = "poor";

        if (score >= 80) {

            comment = "12";

        } else if (score >= 75) {

            comment = "11";

        } else if (score >= 70) {

            comment = "10";

        } else if (score >= 65) {

            comment = "9";

        } else if (score >= 60) {

            comment = "8";

        } else if (score >= 55) {

            comment = "7";

        } else if (score >= 50) {

            comment = "6";

        } else if (score >= 45) {

            comment = "5";

        } else if (score >= 40) {

            comment = "4";

        } else if (score >= 35) {

            comment = "4";

        } else if (score >= 30) {

            comment = "3";

        } else if (score >= 20) {

            comment = "2";

        } else {

            comment = "1";

        }

        return comment;
    }

    public static String getComment(int score) {
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

    public static String getGrading(int score) {

        String comment = "poor";

        if (score >= 80) {

            comment = "TWELVE (12)";

        } else if (score >= 75) {

            comment = "ELEVEN (11)";

        } else if (score >= 70) {

            comment = "TEN (10)";

        } else if (score >= 65) {

            comment = "NINE (9)";

        } else if (score >= 60) {

            comment = "EIGHT (8)";

        } else if (score >= 55) {

            comment = "SEVEN (7)";

        } else if (score >= 50) {

            comment = "SIX (6)";

        } else if (score >= 45) {

            comment = "FIVE (5)";

        } else if (score >= 40) {

            comment = "FOUR (4)";

        } else if (score >= 35) {

            comment = "FOUR (4)";

        } else if (score >= 30) {

            comment = "THREE (3)";

        } else if (score >= 20) {

            comment = "TWO (2)";

        } else {

            comment = "ONE (1)";

        }

        return comment;
    }
    
    
    public static String getAgregate(List<String> comments,int total) {

        String agregate = "";
        Iterator<String> it = comments.iterator();

        int countr = 0;
        int countf = 0;

        int count = 0;
        for (int i = 0; i < comments.size(); i++) {

            if (comments.get(i).equals("REFFER")) {

                countr++;
            }

            if (comments.get(i).equals("FAIL")) {

                countf++;
            }
        }

        if (countf == 3) {

            agregate = "FAIL";

        } else if (countf == 2) {

            agregate = "REFFER";

        } else if (countr == 3) {

            agregate = "REFFER";

        } else if (countr == 2) {

            agregate = "REFFER";

        } else if (countr == 1) {

            agregate = "REFFER";

        } else if (countf == 1) {

            agregate = "REFFER";

        }else{
        
            double average = total/3;
             agregate = getComment(average);
            
        
        }

        
        return agregate;
    }


}
