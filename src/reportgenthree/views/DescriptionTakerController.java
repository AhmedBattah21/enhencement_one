/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import  org.controlsfx.dialog.FontSelectorDialog;


/**
 * FXML Controller class
 *
 * @author Computer
 */
public class DescriptionTakerController implements Initializable {

    /**
     * Initializes the controller class.
     */
    public JFXColorPicker descrptionFontColor;
    public JFXColorPicker headerBackColor;
    public JFXColorPicker headerFontColor;
    public JFXColorPicker rowColor_1;
    public JFXColorPicker rowColor_2;
    public JFXSlider descriptionFontSize;
    public JFXSlider TableContentsFontSize;
    public JFXCheckBox alternate;
    
    
    public JFXTextArea ttle;

    public TextField descriFontSize;
    public TextField tbleFontSize;
    public TextField tblContentsMaxRows;

    public Button useDefaults;
    public Button savenew;
    public static String title = "";
    
    public static double descriptionFontSize_1 =12.0;
    public static String descriptionFontFamily_1 ="Arial";
    public static String descriptionFontStyle_1 ="Reguler";
    public static Font descFont = new Font("Times New Roman",12);
    
    
     public static double TableHeaderFontSize_1 =12.0;
    public static String TableHeaderFontFamily_1 ="Times New Roman";
    public static String TableHeaderFontStyle_1 ="Reguler";
   
    
    public static double TableContentsFontSize_1 =12.0;
    public static String TableContentsFontFamily_1 ="Times New Roman";
    public static String TableContentsFontStyle_1 ="Reguler";
    
    public static String tableRowOneColor;
    public static String tableRowTwoColor;
    public static String headerFontColor_1;
    public static String color_descriptionFontColor;
   
    public static String backgroundHeaderColor;
    
    public JFXButton button;
    
    private static int maxrowintable = 25;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    tableRowOneColor =  "#"+Integer.toHexString(rowColor_1.getValue().hashCode()).substring(0, 6).toUpperCase();
    tableRowTwoColor = "#"+Integer.toHexString(rowColor_2.getValue().hashCode()).substring(0, 6).toUpperCase();
    
    backgroundHeaderColor ="#"+ Integer.toHexString(headerBackColor.getValue().hashCode()).substring(0, 6).toUpperCase();
    headerFontColor_1 = "#"+Integer.toHexString(headerFontColor.getValue().hashCode()).substring(0, 6).toUpperCase();
    color_descriptionFontColor ="#"+ Integer.toHexString(descrptionFontColor.getValue().hashCode()).substring(0, 6).toUpperCase();
    

        rowColor_2.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            tableRowTwoColor ="#"+Integer.toHexString(rowColor_2.getValue().hashCode()).substring(0, 6).toUpperCase(); 
            
            
        }
    });
        
        rowColor_1.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            
            tableRowOneColor ="#"+ Integer.toHexString(rowColor_1.getValue().hashCode()).substring(0, 6).toUpperCase(); 
            
            
        }
    });
        
        
         headerBackColor.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            
            backgroundHeaderColor ="#"+ Integer.toHexString(headerBackColor.getValue().hashCode()).substring(0, 6).toUpperCase(); 
            
            
        }
    });
        
         
         descrptionFontColor.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            
            color_descriptionFontColor="#"+ Integer.toHexString(descrptionFontColor.getValue().hashCode()).substring(0, 6).toUpperCase(); 
            
            
        }
    });
         
         
         //headerFontColor_1 = Integer.toHexString(headerFontColor.getValue().hashCode()).substring(0, 6).toUpperCase();
        
         
         headerFontColor.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            
            headerFontColor_1="#"+ Integer.toHexString(headerFontColor.getValue().hashCode()).substring(0, 6).toUpperCase(); 
            
            
        }
    });
         
         
         
         
         headerFontColor.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            
            headerFontColor_1="#"+ Integer.toHexString(headerFontColor.getValue().hashCode()).substring(0, 6).toUpperCase(); 
            
            
        }
    });
         
        

    }
    
/*
    
    Table Contents get Methods
    
    
    */   
    
    public static double getTableContentsFontSize(){
        
        return TableContentsFontSize_1;
    
    
    }
    
    public static String getTableContentsFontFamily(){
        
        return TableContentsFontFamily_1;
    
    
    }
    
    public static String getTableContentsFontStyle(){
        
        return TableContentsFontStyle_1;
    
    
    }
    
    /*
         -------------End Of Table Contents---------------------
    */
    
    
    
    
     /*
         -------------Description get methods settings---------------------
    */
    
    public static double getDescriptionFontSize(){
    
        //descriptionFontSize_1
        return descriptionFontSize_1;
    
    }
    
    
     public static Font getDescriptionFont(){
    
        //descriptionFontSize_1
        return descFont;
    
    }
    
    
    
     public static String getDescriptionFontFamily(){
    
        //descriptionFontSize_1
        return descriptionFontFamily_1;
    
    }
     
     
      public static String getDescriptionFontStyle(){
    
        //descriptionFontSize_1
        return descriptionFontStyle_1;
    
    }
      
      
      public static String getDescriptionFontColor(){
    
         return color_descriptionFontColor;
    
    }
    
    
    
    public static double getTableHeaderFontSize(){
    
        //descriptionFontSize_1
        return TableHeaderFontSize_1;
    
    }
    
    
   
    
    public static String getTableHeaderFontFamily(){
    
        //descriptionFontSize_1
        return TableHeaderFontFamily_1;
    
    }
    
    
 /*
    
   =============end of description settings  get method ===================== 
    
    */   
    
   
 
    
    /*
    
   =============Start Description settings  get method ===================== 
    
    */   
    
    
    
   
    
    
    public static String getTableHeaderFontStyle(){
    
        //descriptionFontSize_1
        return TableHeaderFontStyle_1;
    
    }
    
    public static String getTableRow_1Color(){
    
         return tableRowOneColor;
    
    }
    
    
    
    public static String getTableRow_2Color(){
    
         return tableRowTwoColor;
    
    }
    
    
    
    public static String getBackgroundHeaderColor(){
    
         return backgroundHeaderColor;
    
    }
    
    
    
    
   
    
    public static String getHeaderFontColor(){
    
         return headerFontColor_1;
    
    }
    
/*
    
   =============end Description settings  get method ===================== 
    
    */    
    
    
    public static String getReportTitle(){
    
    return title;
    
    }
    
    
    public  void setMaxNumbeOfRows(){
    
       maxrowintable = Integer.parseInt(tblContentsMaxRows.getText());
       
       System.out.println("Max Rows Detected "+maxrowintable);
       
    }
    
    public  static int getMaxNumbeOfRows(){
    
       return maxrowintable;
       
    }
    
    public void setReportTitle(){
    
    title = ttle.getText();
    
    
    
    }
    
    
    
    
    //headerFontColor_1 = headerFontColor.getValue();
    
    public void CloseApp(){
    
    
        
     Stage stage = (Stage) savenew.getScene().getWindow();
    stage.close();
    
    
    }
    
    
    public void showDescriptionFontWindow(){
    
       FontSelectorDialog dlg = new FontSelectorDialog(null);
       dlg.setHeaderText("Format Your Description");
       dlg.setTitle("Description Style Setting");
       dlg.showAndWait();
       Font myfont = dlg.getResult();
       descFont = dlg.getResult();
       
       
     descriptionFontSize_1 =myfont.getSize();
     descriptionFontFamily_1 =myfont.getFamily();
     descriptionFontStyle_1 =myfont.getStyle();
       
    }
    
    
    public void showTableHeaderFontWindow(){
    
       FontSelectorDialog dlg = new FontSelectorDialog(null);
       dlg.setHeaderText("Format Your Table Headers");
       dlg.setTitle("Table Header Font Stles");
       dlg.showAndWait();
       Font myfont = dlg.getResult();
       
     TableHeaderFontSize_1 =myfont.getSize();
     TableHeaderFontFamily_1 =myfont.getFamily();
     TableHeaderFontStyle_1 =myfont.getStyle();
       
    }
    
     public void showTableContentsFontWindow(){
    
       FontSelectorDialog dlg = new FontSelectorDialog(null);
       dlg.setHeaderText("Format Your Table Contents");
       dlg.setTitle("Table Contents Font Stles");
       dlg.showAndWait();
       Font myfont = dlg.getResult();
       
     TableContentsFontSize_1 =myfont.getSize();
     TableContentsFontFamily_1 =myfont.getFamily();
     TableContentsFontStyle_1 =myfont.getStyle();
       
    }
    
    
    
    
}
