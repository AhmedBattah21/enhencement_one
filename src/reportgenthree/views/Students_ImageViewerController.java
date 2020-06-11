/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.views;

import Connection.sqlDataBaseConnection;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Computer
 */
public class Students_ImageViewerController implements Initializable {
    
    @FXML
    private Label lbl_details;
    
    @FXML
    private ImageView imageview;
    
    @FXML
    private Button btn_editpic;
    
     @FXML
    private Button btn_opencam;
    
    @FXML
    private Button btn_newpic;
    
    private File image_file;
    
    private String details;
    
    private String std_admno;
    
    private VBox image_holder;
    
    Webcam webcam;
    
    String home = System.getProperty("user.home") + "/" + "Documents";
    Boolean isRunning = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        webcam = Webcam.getDefault();
        
        if (webcam != null) {
            
            Image im = new Image("reportgenthree/views/System_images/default.png", false);
            imageview.setImage(im);
            
        } else {
            
            lbl_details.setText("Camera Not Supported");
            btn_newpic.setDisable(true);
            
        }
        
    }
    
    public void setImage(File image) {
        this.image_file = image;
        
        Image im = new Image(image.toURI().toString(), false);
        imageview.setImage(im);
    }
    
    public void setDetails(String details) {
        this.details = details;
        lbl_details.setText(details);
    }
    
    public void setStd_admno(String std_admno) {
        this.std_admno = std_admno;
    }
    
    public void new_pic() {
        
        if (webcam != null) {
            if (btn_newpic.getText().equals("Save")) {
                
                change_imageName( std_admno + ".jpg");
                
                try {
                    
                    btn_newpic.setText("New Pic");
                    
                } catch (Exception ex) {
                    
                    System.out.println("Error " + ex);
                }
                
            } else {
                try {
                    
                    if (isRunning == false) {
                        
                        isRunning = true;
                        btn_newpic.setText("Capture Image");
                        
                        webcam.setViewSize(new Dimension(320, 240));
                        
                        webcam.open();
                        
                        ImageIO.write(webcam.getImage(), "JPG", new File(home + "/ReportGenThree/students/temp.jpg"));
                        
                        new videoFeedTaker().start();
                        
                    } else if (isRunning == true) {
                        
                        ImageIO.write(webcam.getImage(), "JPG", new File(home + "/ReportGenThree/students/" + std_admno + ".jpg"));
                        isRunning = false;
                        File image_path = new File(home + "/ReportGenThree/students/" + std_admno + ".jpg");
                        Image im = new Image(image_path.toURI().toString(), false);
                        imageview.setImage(im);
                        
                        btn_newpic.setText("Save");
                        webcam.close();
                        
                    }

                    //webcam.close();
                } catch (IOException ex) {
                    
                    Logger.getLogger(Students_ImageViewerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        } else {
            
        }
        
    }
    
    public void open_webcam() {
        if (webcam != null) {
            if (!webcam.isOpen()) {
                
                isRunning = true;
                btn_newpic.setText("Capture Image");
                
                webcam.setViewSize(new Dimension(320, 240));
                
                webcam.open();
                new videoFeedTaker().start();
                
            }
            
        }
        
    }
    
    public void edit_pic() {
        
        if (webcam != null) {
            
            if (btn_editpic.getText().equals("Save")) {
                
                try {
                    
                    change_imageName(std_admno + ".jpg");
                    
                    btn_editpic.setText("Edit Pic");
                    
                } catch (Exception ex) {
                    
                    System.out.println("Error " + ex);
                }
                
            } else {
                try {
                    
                    if (isRunning == false) {
                        
                        isRunning = true;
                        btn_editpic.setText("Capture Image");
                        
                        webcam.setViewSize(new Dimension(640, 480));
                        
                        webcam.open();
                        
                        ImageIO.write(webcam.getImage(), "JPG", new File(home + "/ReportGenThree/students/temp.jpg"));
                        
                        new videoFeedTaker().start();
                        
                    } else if (isRunning == true) {
                        
                        ImageIO.write(webcam.getImage(), "JPG", new File(home + "/ReportGenThree/students/" + std_admno + ".jpg"));
                        isRunning = false;
                        File image_path = new File(home + "/ReportGenThree/students/" + std_admno + ".jpg");
                        Image im = new Image(image_path.toURI().toString(), false);
                        imageview.setImage(im);
                        
                        btn_editpic.setText("Save");
                        webcam.close();
                        
                    }

                    //webcam.close();
                } catch (IOException ex) {
                    
                    Logger.getLogger(Students_ImageViewerController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
            
        } else {
            
        }
        
    }
    
    class videoFeedTaker extends Thread {
        
        @Override
        public void run() {
            
            while (isRunning) {
                try {
                    
                    if (webcam.isOpen()) {
                        
                        ImageIO.write(webcam.getImage(), "JPG", new File(home + "/ReportGenThree/students/temp.jpg"));
                        
                        File image_path = new File(home + "/ReportGenThree/students/temp.jpg");
                        
                        Image im = new Image(image_path.toURI().toString(), false);
                        
                        imageview.setImage(im);
                        Thread.sleep(50);
                    } else {
                        
                        System.out.println("Webcam Closed");
                    }
                    
                } catch (IOException | InterruptedException ex) {
                    
                    System.out.println("Error " + ex);
                }
            }
        }
    }
    
    public void change_imageName(String new_imagename) {
        
        String query = "UPDATE students_2017 SET std_image ='" + new_imagename + "' WHERE StudentRegCode = '" + std_admno + "'";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        try {
            
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
            
        } catch (SQLException exc) {
            
            System.out.println("Error " + exc);
            
        }
        
    }
    
    public Button getBtn_editpic() {
        return btn_editpic;
    }
    
    public void setBtn_editpic(Button btn_editpic) {
        this.btn_editpic = btn_editpic;
    }
    
    public Button getBtn_newpic() {
        return btn_newpic;
    }
    
    public void setBtn_newpic(Button btn_newpic) {
        this.btn_newpic = btn_newpic;
    }

    public Button getBtn_opencam() {
        return btn_opencam;
    }

    public void setBtn_opencam(Button btn_opencam) {
        this.btn_opencam = btn_opencam;
    }
    
    
    
}
