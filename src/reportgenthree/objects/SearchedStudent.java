/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.objects;

import com.jfoenix.controls.JFXButton;

/**
 *
 * @author Computer
 */
public class SearchedStudent {

    private String stname;
    private String classname;

    private String admnumber;

    private String std_gender;
    private JFXButton std_image;
    private String std_course;
    private String std_category;
    private String std_date;
     private String std_admcode;
    private int Count;

    public int getCount() {
        return Count;
    }

    public void setCount(int Count) {
        this.Count = Count;
    }

    public SearchedStudent(String stname1, String classname1, String admnumber1, int count, String std_gender,
            JFXButton std_image, String std_course, String std_category, String std_date,String std_admcode) {

        this.classname = classname1;
        this.admnumber = admnumber1;
        this.stname = stname1;
        this.Count = count;
        this.std_gender = std_gender;
        this.std_image = std_image;
        this.std_course = std_course;
        this.std_category = std_category;
        this.std_date = std_date;
        this.std_admcode = std_admcode;

    }

    public String getStd_admcode() {
        return std_admcode;
    }

    public void setStd_admcode(String std_admcode) {
        this.std_admcode = std_admcode;
    }
    
    

    public String getStname() {
        return stname;
    }

    public void setStname(String stname) {
        this.stname = stname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getAdmnumber() {
        return admnumber;
    }

    public void setAdmnumber(String admnumber) {
        this.admnumber = admnumber;
    }

    public String getStd_gender() {
        return std_gender;
    }

    public void setStd_gender(String std_gender) {
        this.std_gender = std_gender;
    }

    public JFXButton getStd_image() {
        return std_image;
    }

    public void setStd_image(JFXButton std_image) {
        this.std_image = std_image;
    }

    public String getStd_course() {
        return std_course;
    }

    public void setStd_course(String std_course) {
        this.std_course = std_course;
    }

    public String getStd_category() {
        return std_category;
    }

    public void setStd_category(String std_category) {
        this.std_category = std_category;
    }

    public String getStd_date() {
        return std_date;
    }

    public void setStd_date(String std_date) {
        this.std_date = std_date;
    }
    
    

}
