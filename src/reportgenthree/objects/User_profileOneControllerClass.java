/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.objects;

public class User_profileOneControllerClass {

    private String unit_name;
    private String class_name;
    private String syear;

    public User_profileOneControllerClass(String unit_name,String class_name,String syear) {

        this.unit_name = unit_name;
        this.class_name = class_name;
        this.syear = syear;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSyear() {
        return syear;
    }

    public void setSyear(String syear) {
        this.syear = syear;
    }

}
