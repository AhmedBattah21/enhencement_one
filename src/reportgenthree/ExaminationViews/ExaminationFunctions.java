/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.ExaminationViews;

import com.jfoenix.controls.JFXTabPane;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;

/**
 *
 * @author Computer
 */
public class ExaminationFunctions {

    public static StackPane add_inforbox(String Message, FontAwesomeIconView icon, JFXTabPane tpane) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ExaminationFunctions.class.getResource("Information_window.fxml"));
        StackPane pane = null;
        icon.setGlyphSize(150);
        try {
            pane = loader.load();
            Information_windowController cc = loader.getController();
            cc.setInfor(Message);
            cc.setForm_icon(icon);
            cc.setParent_tab(tpane);

        } catch (IOException exc) {

            System.out.println("" + exc);

        }

        return pane;

    }

    
}
