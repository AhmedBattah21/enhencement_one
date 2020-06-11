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
public class CoursesClass {
    
   private String coursename = "";
   private String Abriviation = "";
   private String units = "";
   
   
   public CoursesClass(String cname,String abr,String units){
   
   
       coursename = cname;
       Abriviation = abr;
       this.units = units;
      
   }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
   
   

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getAbriviation() {
        return Abriviation;
    }

    public void setAbriviation(String Abriviation) {
        this.Abriviation = Abriviation;
    }
   
    
}
