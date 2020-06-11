/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.UserHelp;

import com.jfoenix.controls.JFXTreeView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.octicons.OctIcon;
import de.jensd.fx.glyphs.octicons.OctIconView;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class MainhelpWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTreeView<String> MainTreeView;

    @FXML
    private BorderPane pane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        addItems();
        MainTreeView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> getItems(newValue.getValue()));
    }

    public void addItems() {

        TreeItem<String> itemone = new TreeItem<>("Choose Topic");

        TreeItem<String> t1 = new TreeItem<>("Students");
        TreeItem<String> t2 = new TreeItem<>("Academics");
        TreeItem<String> t3 = new TreeItem<>("Users");
        TreeItem<String> t4 = new TreeItem<>("Settings");
        TreeItem<String> t5 = new TreeItem<>("Registration");

        itemone.getChildren().addAll(t1, t2, t3, t4, t5);

        //Students Subitems
        TreeItem<String> st1 = new TreeItem<>("Students Registration");
        st1.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.USER_PLUS));

        TreeItem<String> st2 = new TreeItem<>("Students Edit Records");
        st2.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));

        TreeItem<String> st3 = new TreeItem<>("Students Delete");
        st3.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT));

        TreeItem<String> st4 = new TreeItem<>("Students Find Students");
        st4.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BINOCULARS));

        TreeItem<String> st5 = new TreeItem<>("Students View Records");
        st5.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.DATABASE));

        t1.getChildren().addAll(st1, st2, st3, st4, st5);
        t1.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.QUESTION_CIRCLE));

        //Academics SubItesm
        TreeItem<String> at1 = new TreeItem<>("Add Score");
        at1.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BOOK));

        TreeItem<String> at2 = new TreeItem<>("Edit Exam Score");
        at2.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));

        TreeItem<String> at3 = new TreeItem<>("View Results");
        at3.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.BINOCULARS));

        TreeItem<String> at4 = new TreeItem<>("Delete Exam Record");
        at4.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TRASH_ALT));

        TreeItem<String> at5 = new TreeItem<>("Score Sheet Viewer");
        at5.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TABLE));

        TreeItem<String> at6 = new TreeItem<>("Single Academic Report Creator");
        at6.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.FILE_PDF_ALT));

        TreeItem<String> at7 = new TreeItem<>("Multiple Academic Report Viewer");
        at7.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.SERVER));

        t2.getChildren().addAll(at1, at2, at3, at4, at5, at6, at7);
        t2.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.QUESTION));

        //USERS SUBCLASSES
        TreeItem<String> ut1 = new TreeItem<>("User Profile");
        ut1.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.USER));

        TreeItem<String> ut2 = new TreeItem<>("Edit User Infor");
        ut2.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));

        TreeItem<String> ut3 = new TreeItem<>("New User");
        ut3.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.USER_PLUS));

        TreeItem<String> ut4 = new TreeItem<>("New Group");
        ut4.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.USERS));

        TreeItem<String> ut5 = new TreeItem<>("New Course");
        ut5.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.TRELLO));

        TreeItem<String> ut6 = new TreeItem<>("New Unit");
        ut6.setGraphic(new OctIconView(OctIcon.DIFF));

        TreeItem<String> ut7 = new TreeItem<>("New Examination Type");
        ut7.setGraphic(new OctIconView(OctIcon.BOOK));

        t3.getChildren().addAll(ut1, ut2, ut3, ut4, ut5, ut6, ut7);
        t3.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.QUESTION));

        itemone.setExpanded(true);
        MainTreeView = new JFXTreeView<>(itemone);
        MainTreeView.setPrefWidth(300);

        pane.setLeft(MainTreeView);

    }

    public void std_registration() {

        String message = "The system mejors on <b>managing students records</b> \n"
                + "As one of the core objectives.\n"
                + "This window helps you add the most needed details about your student.\n"
                + "All the fields must be field and incase there is no defined photo \n"
                + "For the user being register, the alternative is to use the default \n"
                + "Photo.\n\n"
                + "After confirmation, the records will be stored in the application \n"
                + "After editing do not forget to hit save button to save the records";

        String title = "Students Registration";

        getVbox(title, message, "/HelpFiles/reghelp.png");

    }
    
    public void std_editing() {

        String message = "Incase Errors Occur like typing errors and other \n"
                + "In a way that the details of the students stored do not realy \n"
                + "conform to the actual students data, then this form will help you \n"
                + "Edit the records\n\n"
                + "To do so, Enter the regcode of the student, if He or She exist, then \n"
                + "the records will apear in the respective boxes,just Edit where necessary \n\n"
                + "After editing do not forget to hit save button to save the records";

        String title = "Students Registration";

        getVbox(title, message, "/HelpFiles/std_edithelp.png");

    }
    
    public void std_Remove() {

        String message = "Delete Student Record \n\n"
                + "Removes Student records From system database \n"
                + "But this is not recommended \n"
                + "To delete student,Enter Adm Number and Hit Delete \n"
                + "Remember you can not un dothis action, \n"
                + "Therefore think twice before deleting";

        String title = "Delete Student Record";

        getVbox(title, message, "/HelpFiles/RemoveStudent.png");

    }
    
    public void std_Finder() {

        String message = "Use This Tool to find students Records \n\n"
                + "Gives Full Details \n \n"
                + "Of The Student \n \n"
                + "Good tool to use when you require specific students \n"
                + "Details or confirming Students registration";

        String title = "Student Finder Tool";

        getVbox(title, message, "/HelpFiles/std_finder.png");

    }

    public void getVbox(String title, String message, String imgname) {

        Text txt = new Text();

        txt.setFont(new Font(15));
        
        ImageView imgv = new ImageView();
        InputStream in = MainhelpWindowController.class.getClass().getResourceAsStream(imgname);

        Image img = new Image(in);
        imgv.setImage(img);
       
        try {
            in.close();
        } catch (IOException ex) {
            System.out.println(""+ex);
        }

        txt.setTextAlignment(TextAlignment.CENTER);
        txt.setText(message);
        
        
        Label label = new Label(title);

        VBox box = new VBox();
        box.alignmentProperty().set(Pos.TOP_CENTER);
        box.setSpacing(20);
        box.setPadding(new Insets(20,20,20,20));
        box.setMargin(box, new Insets(20,20,20,20));

        box.getChildren().addAll(label, imgv,txt);
        ScrollPane spane = new ScrollPane(box);
        spane.setFitToHeight(true);
        spane.setFitToWidth(true);
        
        pane.setCenter(spane);

    }

    public void getItems(String helpname) {

        if (helpname.equals("Students Registration")) {

            std_registration();
        }else if(helpname.equals("Students Edit Records")){
        
        std_editing();
        
        }else if(helpname.equals("Students Delete")){
        
            std_Remove();
        
        
        }else if(helpname.equals("Students Find Students")){
        
            std_Finder();
        
        }
//
    }

}
