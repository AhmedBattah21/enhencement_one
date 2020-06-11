/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reportgenthree.objects;

/**
 *
 * @author Computer
 */
public class UnitsClass {

    private String unitname = "";
    private String unitcode = "";
    private String property = "";

    public UnitsClass(String uname, String code, String property) {

        this.unitname = uname;
        this.unitcode = code;
        this.property = property;

    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
    
    

}
