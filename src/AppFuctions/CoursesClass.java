
package AppFuctions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import Connection.sqlDataBaseConnection;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

/**
 *
 * @author Computer
 */
public class CoursesClass {
    
    private static List<String> names = new ArrayList<>();
    private static List<String> regs = new ArrayList<>();
    private static List<String> scores = new ArrayList<>();
    
    public static Boolean checkcourse(String coursename,String abr){
    boolean checker = false;
    
    String query = "SELECT * FROM coursetable WHERE CourseName = '"+coursename+"' OR CourseAbreviation ='"+abr+"' ";
    
    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    try{
        
        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);
        
        if(rst.next()){
            
        checker = true;
        
        }
    
    conn.close();
    }catch(SQLException exc){
    
        JOptionPane.showMessageDialog(null, exc);
    
    }
    
    
    return checker;
    }
    
    /**
     * 
     * @param cname
     * @param hname
     * @param cabr
     * @return 
     */
    public static Boolean RegisterCourse(String cname,String hname,String cabr){
    Boolean result = false;
    
    String query = "INSERT INTO coursetable VALUES('"+cname+"','"+hname+"','"+cabr+"')";
    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    
    try{
    Statement st = conn.createStatement();
    st.execute(query);
    
    result = true;
    
    conn.close();
    }catch(SQLException exc){
    
    JOptionPane.showMessageDialog(null,"<HTML>Error Number 005 Source UserFunctions <br>"+exc+"</HTML>");
    
    }
    
   return result; 
    
    }
    
    /**
     * 
     * @param table 
     */
    public static void populateTableCourses(JTable table){
        
        String query = "SELECT CourseName As Course,CourseAbreviation As Abr , CourseHead As Chair FROM CourseTable";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 006 Source Course Functions <br>"+exc+"</HTML>");
        }
    
    }
    /**
     * 
     * @param table
     * @param hint 
     */
     public static void populateTableCoursestWO(JTable table,String hint){
        
        String query = "SELECT CourseName As Course,CourseAbreviation As Abr , CourseHead As Chair FROM CourseTable WHERE CourseName LIKE '%"+hint+"%' "
                + " OR CourseAbreviation LIKE '%"+hint+"%' OR  CourseHead  LIKE '%"+hint+"%'  ";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           if(rst.next()){
                   
           
           }
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 006 Source Course Functions <br>"+exc+"</HTML>");
        }
    
    }
     
     /**
      * 
      * @param box 
      */
     
      public static void populateComboBoxCourses(JComboBox box){
        
        String query = "SELECT CourseName FROM CourseTable";
        box.removeAllItems();
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           while(rst.next()){
                   
                   box.addItem(rst.getString("CourseName"));
           
           }
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 006 Source Course Functions <br>"+exc+"</HTML>");
        }
    
    }
      
      
      /**
       * 
       * @param cname
       * @return 
       */
      public static String getCourseAbr(String cname){
      String cabr = "";
      
       String query = "SELECT CourseAbreviation FROM CourseTable WHERE CourseName = '"+cname+"'";
       Connection conn = sqlDataBaseConnection.sqliteconnect();
       try{
       
           Statement st = conn.createStatement();
           ResultSet rst = st.executeQuery(query);
           
           if(rst.next()){
           
               cabr = rst.getString("CourseAbreviation");
           }
       conn.close();
       }catch(SQLException exc){
       
           JOptionPane.showMessageDialog(null,"<HTML>Error Number 007 Source Course Functions <br>"+exc+"</HTML>");
       
       }
      
      return cabr;
      
      }
      
      
      /**
       * 
       * @param tablename
       * @return 
       */
      public static Boolean checkTable(String tablename){
      Connection conn = sqlDataBaseConnection.sqliteconnect();
      boolean checker = false;
          try{
              
              DatabaseMetaData meta = conn.getMetaData();
              ResultSet rst = meta.getTables(null, null, tablename, null);
              
              if(rst.next()){
                  
                checker = true;
                
              }
          
             
          conn.close();
          }catch(SQLException exc){
          
                JOptionPane.showMessageDialog(null,"<HTML>Error Number 008 Source Course Functions <br>"+exc+"</HTML>");
          
          }
      
      return checker;
      }
      
      
      public static Boolean createGroupTable(String table){
      boolean checker = false;
      
          String query = "CREATE TABLE "+table+" (\n" +
                                              "    STDREGNO    VARCHAR (100),\n" +
                                               "    STDNAME     VARCHAR (100),\n" +
                                               "    STDCATEGORY VARCHAR (100),\n" +
                                               "    STDYEAR     VARCHAR (100) \n" +
                                               ");";
      
      Connection conn = sqlDataBaseConnection.sqliteconnect();
      
      try{
          
          Statement st = conn.createStatement();
          
          st.executeUpdate(query);
          
          checker = true;
          
          conn.close();
      }catch(SQLException exc){
      
      JOptionPane.showMessageDialog(null,"<HTML>Error Number 009 Source Course Functions <br>"+exc+"</HTML>");
      checker = false;
      
      }
      return checker;
      
      }
      
      
      /**
       * 
       * @param gname
       * @param course
       * @param date 
       */
      public static void registerGroup(String gname,String course,String date){
      
      String query ="INSERT INTO CreatedGroups VALUES('"+gname+"','"+course+"','"+date+"')";
      
      Connection conn = sqlDataBaseConnection.sqliteconnect();
      
      try{
      
          Statement st = conn.createStatement();
          st.executeUpdate(query);
      
      }catch(SQLException exc){
          
          JOptionPane.showMessageDialog(null,"<HTML>Error Number 010 Source Course Functions <br>"+exc+"</HTML>");
      
      }
      
      
      }
      
      
      /**
       * 
       * @param value
       * @return 
       */
      public static Boolean checkifInteger(String value){
          
          
      boolean checker = false;
      
     
      
      try{
       int value3 = Integer.parseInt(value);
       
          int mine = value3/20;
          
          if(value3 >= 2005 && value3 <= 2030){
              
               checker = true;
          
          }
         
      
      }catch(NumberFormatException exc){
          
          checker = false;
      
      }
      
      
      return checker;
      }
  
      
      /**
       * 
       * @param table 
       */
 public static void populateGroupTable(JTable table){
     
 String query = "SELECT CohoteName As Cohote,CourseName As Course,DateOfCreation As Date FROM CreatedGroups";
 Connection conn = sqlDataBaseConnection.sqliteconnect();
 try{
 
 Statement st = conn.createStatement();
 ResultSet rst = st.executeQuery(query);
 
 
 conn.close();
 
 }catch(SQLException exc){
 
 JOptionPane.showMessageDialog(null,"<HTML>Error Number 011 Source Course Functions <br>"+exc+"</HTML>");
 
 }
 
 
 }
    
 
      
 public static void populateGroupTableTwo(JTable table,String hint){
     
 String query = "SELECT CohoteName As Cohote,CourseName As Course,DateOfCreation As Date FROM CreatedGroups WHERE CohoteName LIKE  '%"+hint+"%' "
         + " OR CourseName LIKE  '%"+hint+"%' OR  DateOfCreation LIKE  '%"+hint+"%' ";
 Connection conn = sqlDataBaseConnection.sqliteconnect();
 try{
 
 Statement st = conn.createStatement();
 ResultSet rst = st.executeQuery(query);
 
 
 conn.close();
 
 }catch(SQLException exc){
 
 JOptionPane.showMessageDialog(null,"<HTML>Error Number 011 Source Course Functions <br>"+exc+"</HTML>");
 
 }
 
 
 }
 
 public static String getExamnumberCode(String examnumber,String Term,String target,String year,String Examname){
 //Exam One, Exam Two, Exam Three, Exam Four
     String examc = "";
     String targetname = "";
     String term = "";
     
     if(examnumber.equals("Exam One")){
      
          examc ="1";
         
     }else if(examnumber.equals("Exam Two")){
     
         examc ="2";
     }else if(examnumber.equals("Exam Three")){
     
         examc ="3";
     }else if(examnumber.equals("Exam Four")){
     
         examc ="4";
     }
 
     //First Year, Second Year, Part Time, Special Case
     if(target.equals("First Year")){
      
          targetname ="1";
         
     }else if(target.equals("Second Year")){
     
        targetname ="2";
        
     }else if(target.equals("Part Time")){
     
        targetname ="3";
        
     }else if(target.equals("Special Case")){
     
        targetname ="4";
        
     }
  //Term One, Term Two, Term Three
     
 if(Term.equals("Term One")){
      
          term ="1";
         
     }else if(Term.equals("Term Two")){
     
        term ="2";
        
     } else if(Term.equals("Term Three")){
     
        term ="3";
        
     }    
 //Mid_Term
//End_Term 
 String table = "";
 
 if(Examname.equals("Mid_Term") || Examname.equals("End_Term")){
     
     table = Examname+"_"+term+"_"+targetname+"_"+year;
 
 }else{
     
 table = Examname+"_"+examc+"_"+term+"_"+targetname+"_"+year;
 
 }
 return table;
 
 }
 
 public static void createExamTable(String tablename){
 
     String query = "CREATE TABLE  "+tablename+" ( STDREG    VARCHAR (100),STDCOURSE VARCHAR (100));";
     
  Connection conn = sqlDataBaseConnection.sqliteconnect();
  
     try{
     Statement st = conn.createStatement();
     
     st.executeUpdate(query);
         
     conn.close();
     
     }catch(SQLException exc){
         
  JOptionPane.showMessageDialog(null,"<HTML>Error Number 012 Source Course Functions <br>"+exc+"</HTML>");    
     
     }
 
 }
 
 public static void updateExamTable(String[] units,String table){
 
 String query = "";
 
 Connection conn = sqlDataBaseConnection.sqliteconnect();
 
 try{
     
     Statement st = conn.createStatement();
     
     for(int value = 0; value < units.length;value++){
         
     String unitcode = units[value];
     
     query = "ALTER TABLE "+table+" ADD "+units[value]+" VARCHAR(100)";
     st.executeUpdate(query);
     
     }
 conn.close();
 
 }catch(SQLException exc){
 
  JOptionPane.showMessageDialog(null,"<HTML>Error Number 013 Source Course Functions <br>"+exc+"</HTML>");  
  
 }
 
 }
 
 public static void addExmtable(String examname,String maxscore){
     
 String query = "INSERT INTO createtexaminationtables VALUES('"+examname+"','yes','"+maxscore+"') " ;
 Connection conn = sqlDataBaseConnection.sqliteconnect();
 
 try{
 Statement st = conn.createStatement();
 st.executeUpdate(query);
 
 conn.close();
 
 }catch(SQLException exc){
 
JOptionPane.showMessageDialog(null,"<HTML>Error Number 014 Source Course Functions <br>"+exc+"</HTML>");   
 }
 
 }
 
 public static Boolean checkifIntegerOnly(String value){
          
          
      boolean checker = false;
      
     
      
      try{
       int value3 = Integer.parseInt(value);
       
          int mine = value3/20;
            
               checker = true;
          
      }catch(NumberFormatException exc){
          
          checker = false;
      
      }
      
      
      return checker;
      }
 
 public static void populateCMBwithItems(JComboBox cmb,String Column,String table){
 
     String query = "SELECT * FROM "+table+"";
     Connection conn = sqlDataBaseConnection.sqliteconnect();
     try{
         
         Statement st = conn.createStatement();
         ResultSet rst = st.executeQuery(query);
         
         while(rst.next()){
             
             cmb.addItem(rst.getString(Column));
         
         }
         
     
     }catch(SQLException exc){
     
    JOptionPane.showMessageDialog(null,"<HTML>Error Number 015 Source Course Functions <br>"+exc+"</HTML>"); 
     }
 
 
 
 }
 
 
 public static Boolean checkCourseInExamT(String tname,String cname){
 //cname inform of abr
  boolean checker = false;
  String query = "SELECT * FROM "+tname+" WHERE STDCOURSE = '"+cname+"'";
   
 Connection conn = sqlDataBaseConnection.sqliteconnect();
 try{
     
     Statement st = conn.createStatement();
     
     ResultSet rst = st.executeQuery(query);
     if(rst.next()){
     
         checker = true;
     }
 
 conn.close();
 
 }catch(SQLException exc){
     
JOptionPane.showMessageDialog(null,"<HTML>Error Number 016 Source Course Functions <br>"+exc+"</HTML>"); 
 
 }
 
 
 
 return checker;
 }
 
 public static int getNumberOfUnitsInExamT(String examt){
 int count = 0;
 
 Connection conn = sqlDataBaseConnection.sqliteconnect();
 
 try{
 
     DatabaseMetaData meta = conn.getMetaData();
     ResultSet rst = meta.getColumns(null, null, examt,null);
     
     while(rst.next()){
         
        
       count++;
     }
 conn.close();
 }catch(SQLException exc){
 
JOptionPane.showMessageDialog(null,"<HTML>Error Number 016 Source Course Functions <br>"+exc+"</HTML>");  
 }
 
 return count;
 
 }
 
 public static void addStudentsToExamT(String examt,String stdt,int unumber){
 
     String querygetst = "SELECT STDREGNO FROM  "+stdt+"";
     Connection conn = sqlDataBaseConnection.sqliteconnect();
     String querytwo = "";
     
     String[]  stdcourse = stdt.split("-");
     String courseabr = stdcourse[0];
     
     try{
     
         Statement st = conn.createStatement();
         Statement st2 = conn.createStatement();
         
         ResultSet rst = st.executeQuery(querygetst);
        while(rst.next()){
         String stdreg = rst.getString("STDREGNO");
         
         
         
        querytwo = getquery(unumber,examt,stdreg,courseabr);
        
        
        st2.executeUpdate(querytwo);
        
        } 
        conn.close();
        
     }catch(SQLException exc){
     
         JOptionPane.showMessageDialog(null,"<HTML>Error Number 016 Source Course Functions <br>"+exc+"</HTML>");  
     
     }
     
     
 
 
 
 }



public static String  getquery(int number,String examt,String stdr,String stdcourse){

String query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-')";

if(number == 3){

    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-')";

}else if(number == 4){

query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-')";

}else if(number == 5){
    
    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-'')";


}else if(number == 6){
    
    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-')";


}else if(number == 7){
    
    
    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-')";


}else if(number == 8){
    
    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-','-')";


}else if(number == 9){
    
    
    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-','-','-')";


}else if(number == 10){
    
    
    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-','-','-','-')";


}else if(number == 11){
    
    
    query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-','-','-','-','-')";


}else if(number == 12){
    
    
     query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-','-','-','-','-','-')";


}else if(number == 13){

     query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-','-','-','-','-','-','-')";

}else {

query = "INSERT INTO "+examt+" VALUES('"+stdr+"','"+stdcourse+"','-','-','-','-','-','-','-','-','-','-','-')";

}


return query;
}

public static Boolean checkstInExamT(String examt,String regno,String course){
boolean checker = false;

    String query = "SELECT * FROM "+examt+" WHERE STDREG = '"+regno+"' AND STDCOURSE = '"+course+"' ";
    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    try{
 Statement st = conn.createStatement();
 ResultSet rst = st.executeQuery(query);
 
 if(rst.next()){
     
 checker = true;
 }
        
 conn.close();
 
  }catch(SQLException exc){

      
      JOptionPane.showMessageDialog(null,"<HTML>Error Number 016 Source Course Functions <br>"+exc+"</HTML>");  
}
    
return checker;

}


public static Boolean checkunitInExamT(String examt,String unitname){
    
boolean checker = false;

    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    try{
        
        DatabaseMetaData meta = conn.getMetaData();
        
        ResultSet rst = meta.getColumns(null, null, examt, unitname);
        
        if(rst.next()){
        
             checker = true;
        }
    conn.close();
    
    }catch(SQLException exc){
    
    JOptionPane.showMessageDialog(null,"<HTML>Error Number 017 Source Course Functions <br>"+exc+"</HTML>");
    
    }

return checker;
}


public static Boolean checkif_has_amark(String examt,String unitname,String regno ){
boolean checker = false;

    String query = "SELECT "+unitname+" FROM "+examt+" WHERE STDREG = '"+regno+" '";
    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    try{
        
        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);
        
        if(rst.next()){
        
            String unit = rst.getString(unitname);
            
            if(unit.equals("-")){
                
                checker = false;
            
            }else{
                
                checker = true;
            
            }
        }
    conn.close();
    
    }catch(SQLException exc){
        
     JOptionPane.showMessageDialog(null,"<HTML>Error Number 018 Source Course Functions <br>"+exc+"</HTML>");   
    
    }

return checker;
}


public static Boolean checkMackInrange(String examt,String mark){
boolean checker = false;

    String query = "SELECT MaxScore FROM createtexaminationtables WHERE ExaminationName = '"+examt+"'";
    
    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    int unitcore = Integer.parseInt(mark);
    
    try{
        
        Statement st = conn.createStatement();
        ResultSet rst = st.executeQuery(query);
        
        if(rst.next()){
        
            int maxscore =Integer.parseInt(rst.getString("MaxScore"));
            
            if(unitcore <= maxscore && unitcore >=0){
                
                checker = true;
            
            }
           
        }
        
        
    conn.close();
    
    }catch(SQLException exc){
        
     JOptionPane.showMessageDialog(null,"<HTML>Error Number 019 Source Course Functions <br>"+exc+"</HTML>");   
    
    }
    

return checker;

}


public static Boolean addScore(String examtt,String unitname,String regno, String stdcourse,String score){
  boolean checker = false;  
    
    String query = "UPDATE  "+examtt+" SET "+unitname+"='"+score+"' WHERE  STDREG ='"+regno+"' AND STDCOURSE ='"+stdcourse+"' ";
    
    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    try{
        
    Statement st = conn.createStatement();
        
       st.executeUpdate(query);
       checker = true;
       
      conn.close();
    
    }catch(SQLException exc){
        
     JOptionPane.showMessageDialog(null,"<HTML>Error Number 20 Source Course Functions <br>"+exc+"</HTML>");  
     checker = false;
    
    }
    

return checker;

}


public static void getandpopulateunits_reports(JComboBox cmb){
        
    cmb.removeAllItems();
   Connection conn = sqlDataBaseConnection.sqliteconnect();
   
   String query = "SELECT UnitName,UnitCode FROM unitstable";
   
   try{
   
       Statement st = conn.createStatement();
       ResultSet rst = st.executeQuery(query);
       
      while(rst.next()){
      
          String cname = rst.getString("UnitName");
          String ccode = rst.getString("UnitCode");
          String name = cname+"-"+ccode;
          
          cmb.addItem(name);
          
      
      }
   
      conn.close();
      
   }catch(SQLException exc){
   
       
       JOptionPane.showMessageDialog(null, "Code Error 2 In Reports Functions"+exc);
   
   }
}


public static void getandpopulateexams_reports(JComboBox cmb){
        
   cmb.removeAllItems();
   Connection conn = sqlDataBaseConnection.sqliteconnect();
   
   String query = "SELECT ExaminationName FROM createtexaminationtables";
   
   try{
   
       Statement st = conn.createStatement();
       ResultSet rst = st.executeQuery(query);
       
      while(rst.next()){
      
          String cname = rst.getString("ExaminationName");
          
          cmb.addItem(cname);
          
          
      
      }
   
      conn.close();
      
   }catch(SQLException exc){
   
       
       JOptionPane.showMessageDialog(null, "Code Error 3 In Reports Functions"+exc);
   
   }
}


public static Boolean checkiftablehasdata_reports(String tname){
 boolean checker = false;       
   
   Connection conn = sqlDataBaseConnection.sqliteconnect();
   
   String query = "SELECT  *FROM "+tname+"";
   
   try{
   
       Statement st = conn.createStatement();
       ResultSet rst = st.executeQuery(query);
       
      while(rst.next()){
      
         checker = true;
      }
   
      conn.close();
      
   }catch(SQLException exc){
   
       
       JOptionPane.showMessageDialog(null, "Code Error 3 In Reports Functions"+exc);
   
   }
   
   return checker;
}


public static void getNamesAndRegNosPlusScores(String tnames,String texam,String unitcode){

names.clear();
regs.clear();
scores.clear();
    
    String colnames = tnames+".STDNAME";
    String clnregs = tnames+".STDREGNO";
    String clnregs2 = texam+".STDREG";
    String colscore = texam+"."+unitcode;
    
    String query = "SELECT "+colnames+","+clnregs+","+colscore+"  FROM "+tnames+","+texam+"  WHERE "+clnregs2+" = "+clnregs+" ";
    
    Connection conn = sqlDataBaseConnection.sqliteconnect();
    
    try{
    Statement st = conn.createStatement();
    ResultSet rst = st.executeQuery(query);
    
    while(rst.next()){
        
        names.add(rst.getString(colnames));
        regs.add(rst.getString(clnregs));
        scores.add(rst.getString(colscore));
        
    }
    
    conn.close();
    }catch(SQLException exc){
    
        JOptionPane.showConfirmDialog(null,exc);
    
    
    }



}

public static List<String> getArrayOfNamesInExam(){

return names;

}

public static List<String> getArrayOfScoresInExam(){

return scores;

}
public static List<String> getArrayOfRegsExam(){

return regs;

}

public static void populateComboBoxClasses(JFXComboBox box,String column,String table){
        
        String query = "SELECT "+column+" FROM "+table+"";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<String> rows = FXCollections.observableArrayList();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           
           while(rst.next()){
                   
                   
                   rows.add(rst.getString(column));
           
           }
           
           box.setItems(rows);
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 021 Source Course Functions <br>"+exc+"</HTML>");
        }
        
        
    
    }

public static ObservableList<String>  getTableItems(String column,String table){
        
        String query = "SELECT "+column+" FROM "+table+"";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<String> rows = FXCollections.observableArrayList();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           
           while(rst.next()){
                   
                   
                   rows.add(rst.getString(column));
           
           }
           
         
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 021 Source Course Functions <br>"+exc+"</HTML>");
        }
        
        return rows;
    
    }


public static void populateComboBoxClasses(ComboBox box,String column,String table){
        
        String query = "SELECT "+column+" FROM "+table+"";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<String> rows = FXCollections.observableArrayList();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           
           while(rst.next()){
                   
                   
                   rows.add(rst.getString(column));
           
           }
           
           box.setItems(rows);
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 021 Source Course Functions <br>"+exc+"</HTML>");
        }
    
    }

public static void populateUnitsComboBox(JFXComboBox box,String column,String table){
        
        String query = "SELECT "+column+" FROM "+table+"";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        ObservableList<String> rows = FXCollections.observableArrayList();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
            rows.add("Select None");
           while(rst.next()){
                   
                   
                   rows.add(rst.getString(column));
           
           }
           
           box.setItems(rows);
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 021 Source Course Functions <br>"+exc+"</HTML>");
        }
    
    }

public static void populateComboBoxClassesTwo(JComboBox box){
        
        String query = "SELECT CohoteName FROM createdgroups";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           while(rst.next()){
                   
                   box.addItem(rst.getString("CohoteName"));
           
           }
                    
        conn.close();
        
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 021 Source Course Functions <br>"+exc+"</HTML>");
        }
    
    }
      

public static List<String> getUnitsInExamT(String examt){
    
    
 int count = 0;
 List<String> Units=new ArrayList<>();
 
 Connection conn = sqlDataBaseConnection.sqliteconnect();
 
 try{
 
     DatabaseMetaData meta = conn.getMetaData();
     ResultSet rst = meta.getColumns(null, null, examt,null);
     
     while(rst.next()){
      String column = rst.getString("COLUMN_NAME");
      
         if(column.equals("STDREG") || column.equals("STDCOURSE") ){
         
         }else{
         
             Units.add(column);
         }
         
     
     }
 conn.close();
 
 }catch(SQLException exc){
 
JOptionPane.showMessageDialog(null,"<HTML>Error Number 22 Source Course Functions <br>"+exc+"</HTML>");  
 }
 
 return Units;
 
 }


public static List<String> getStudentsRegNosExamT(String examt){
 List<String> stdregs = new ArrayList<>();
 
        String query = "SELECT STDREG FROM "+examt+"";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           while(rst.next()){
                   
                   stdregs.add(rst.getString("STDREG"));
           
           }
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 023 Source Course Functions <br>"+exc+"</HTML>");
        }
        
        return stdregs;
    
    }


public static String getStudentCourse(String regno){
 
        String query = "SELECT StudentCourse FROM Students_2017 WHERE StudentRegCode = '"+regno+"'";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        String stdCname = "";
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           if(rst.next()){
                   
                  stdCname =rst.getString("StudentCourse");
           
           }
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 023 Source Course Functions <br>"+exc+"</HTML>");
        }
        
        return stdCname;
    
    }

public static String getStudentYear(String regno){
 
        String query = "SELECT YearOfStudy FROM Students_2017 WHERE StudentRegCode = '"+regno+"'";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        String stdCname = "";
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           if(rst.next()){
                   
                  stdCname =rst.getString("YearOfStudy");
           
           }
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 023 Source Course Functions <br>"+exc+"</HTML>");
        }
        
        return stdCname;
    
    }



public static String getStudentsScores(String exam1,String exam2,String unitcode,String regno){
    

 String exam1score = exam1+"."+unitcode;
 String exam2score = exam2+"."+unitcode;
 String examscore = "-/-";
 String tdreg1 = exam1+"."+"STDREG";
 String tdreg2 = exam2+"."+"STDREG";
 
        String query = "SELECT "+exam1score+","+exam2score+" FROM "+exam1+","+exam2+" WHERE "+tdreg1+" ='"+regno+"' AND  "+tdreg2+"='"+regno+"'  ";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           while(rst.next()){
                   
                   String score1 = rst.getString(exam1score);
                   String score2 = rst.getString(exam2score);
                   examscore = score1+"/"+score2;
           
           }
                    
        conn.close();
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 023 Source Course Functions <br>"+exc+"</HTML>");
        }
        
        return examscore;
    
    }
   
public static String getUnitName(String unitcode){
        
        String query = "SELECT UnitName FROM unitstable WHERE UnitCode = '"+unitcode+"'";
        String unitname = "";
        
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           while(rst.next()){
                   
               unitname= rst.getString("UnitName");
           
           }
                    
        conn.close();
        
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 024 Source Course Functions <br>"+exc+"</HTML>");
        }
        
        return unitname;
    
    }

public static String  getCourseName(String coursecode){
    
    String[] nameone = coursecode.split("_");
    String coursename = "";
        
        String query = "SELECT CourseName FROM CourseTable WHERE CourseAbreviation ='"+nameone[0]+"' ";
       
        Connection conn = sqlDataBaseConnection.sqliteconnect();
        
        try{
        
            Statement st = conn.createStatement();
            
            ResultSet rst = st.executeQuery(query);
           while(rst.next()){
                   
                   coursename= rst.getString("CourseName");
           
           }
                    
        conn.close();
        
        }catch(SQLException exc){
        
        JOptionPane.showMessageDialog(null,"<HTML>Error Number 024 Source Course Functions <br>"+exc+"</HTML>");
        }
        
    return coursename;
    }
   

public static String getTerm(String examname){

    String[] examone = examname.split("_");
    
    String term = examone[0];
    String term1 = examone[1];
    String term3 = examone[2];
    if(term3.equals("1")){
    
        term3= "ONE";
    }else if(term3.equals("2")){
    
    term3= "TWO";
    }else if(term3.equals("3")){
    
    term3= "THREE";
    }
String termname = term+" "+term1+" "+term3;

return termname.toUpperCase();

}

}