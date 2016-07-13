package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class hotel {
    private Connection conn = null;

    private final String db_user = "root";
    private final String db_pass = "";
    private final String db_driver = "com.mysql.jdbc.Driver";
    private final String db_url = "jdbc:mysql://localhost:3306/hotelreservationdb"; 
    
    
    //private String hotelname;
    //private String hotelinfo;
    //private String hoteladdress;
    //private String hoteltelephone;
   // private int userid;
   // private int budget;
  //  private int state_id;
    //private int status;
    private PreparedStatement pstmt;
    ResultSet rs;
    
    public hotel(){
        
    }
    



     //--------------------------------------------------------------------------
   
    public void initializeJdbc() throws SQLException {
        try {
            Class.forName(db_driver);
            conn = DriverManager.getConnection(db_url, db_user, db_pass);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    //--------------------------------------------------------------------------
    
    public boolean addHotel(String hotelname, String hotelinfo, String address, String hotelphone, int user_id, int STATE_ID) throws SQLException {
         try {

            initializeJdbc();

            pstmt = conn.prepareStatement("insert into hotelreservationdb.hotel "
                    + "(hotelname, hotelinfo, address, hotelphone, user_id, STATE_ID) values (?, ?, ?, ?, ?, ?)");
            
            pstmt.setString(1, hotelname);
            pstmt.setString(2, hotelinfo);
            pstmt.setString(3, address);
            pstmt.setString(4, hotelphone);
            pstmt.setInt(5, user_id);
            pstmt.setInt(6, STATE_ID);


            pstmt.executeUpdate();
          
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            return false;
        }    

        return true;
    }
    
     //--------------------------------------------------------------------------
    
     public String takeHotelname(String hotelname) throws SQLException {
        try {
        
        initializeJdbc();
        pstmt = conn.prepareStatement("select hotelname from hotel where hotelname = ?");
        pstmt.setString(1, hotelname);
        rs = pstmt.executeQuery();
        rs.first();

        return rs.getString("hotelname");
    }
        catch(Exception ex){
          System.out.println("Exception: " + ex.getMessage());
            return "";  
        }
        
    }   
    
     //-------------------------------------------------------------------------- 
     
     public int takeHotelId(String hotelname) throws SQLException {
        try {
        
        initializeJdbc();
        pstmt = conn.prepareStatement("select hotelid from hotel where hotelname = ?");
        pstmt.setString(1, hotelname);
        rs = pstmt.executeQuery();
        rs.first();

        return rs.getInt("hotelid");
    }
        catch(Exception ex){
          System.out.println("Exception: " + ex.getMessage());
            return 0;  
        }
        
    }   
    
     //--------------------------------------------------------------------------
     
     public String checkType(String hotelname, String password) throws SQLException {
        initializeJdbc();
        if(!hotelname.equals("")&&!password.equals("")){    
        pstmt = conn.prepareStatement("select hoteltype_id from hotel where hotelname = ? and password = ? ");
        pstmt.setString(1, hotelname);
        pstmt.setString(2, password);

        rs = pstmt.executeQuery();
        rs.first();
        if(rs.first()==false)
            return "-1";
        
        return rs.getString("hoteltype_id");
        }
        else{
            return "-1";
        }

        
    }
    //--------------------------------------------------------------------------
     
     public String checkTypePages(String hotelname) throws SQLException {
        initializeJdbc();
        if(hotelname!=null){    
        pstmt = conn.prepareStatement("select hoteltype_id from hotel where hotelname = ?");
        pstmt.setString(1, hotelname);

        rs = pstmt.executeQuery();
        rs.first();
        if(rs.first()==false)
            return "-1";
        
        return rs.getString("hoteltype_id");
        }
        else{
            return "-1";
        }

        
    }    
    
    //--------------------------------------------------------------------------
     
     public boolean checkRegistered(String hotelname, String password) throws SQLException {
        initializeJdbc();

        pstmt = conn.prepareStatement("select * from hotel where hotelname = ? and password = ? and hoteltype_id = ? ");
        pstmt.setString(1, hotelname);
        pstmt.setString(2, password);
        pstmt.setString(3, "2");

        rs = pstmt.executeQuery();
        rs.first();

        if (rs.first()) {
            return true;
        } else {
            return false;
        }
    }

    //--------------------------------------------------------------------------
     
      public boolean checkAdmin(String hotelname, String password) throws SQLException {
        initializeJdbc();

        pstmt = conn.prepareStatement("select * from hotel where hotelname = ? and password = ? and hoteltype_id = ?");
        pstmt.setString(1, hotelname);
        pstmt.setString(2, password);
        pstmt.setString(3, "1");
        
        rs = pstmt.executeQuery();
        rs.first();

        if (rs.first()) {
            return true;
        } else {
            return false;
        }
    }

    //--------------------------------------------------------------------------
      
      public ResultSet returnTable(String sql) throws SQLException {
        initializeJdbc();

        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        rs.beforeFirst();
        return rs;
    }
      
      //--------------------------------------------------------------------------
    
     public ResultSet takeCities() throws SQLException {
        initializeJdbc();


        pstmt = conn.prepareStatement("select * from city ");       

        rs = pstmt.executeQuery();

        return rs;
    }

    //--------------------------------------------------------------------------
     
     
     public String takeCitynameFromID(String id) throws SQLException {
         
        initializeJdbc();
        
        pstmt = conn.prepareStatement("select CITY_NAME from city where CITY_ID = ? ");       
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        rs.first();
        if(rs.first()==false)
            return "-1";
        String s = rs.getString("CITY_NAME");
        return s;
    }

    //--------------------------------------------------------------------------
     
     public String takeCityIDFromName(String city) throws SQLException {
         
        initializeJdbc();
        
        pstmt = conn.prepareStatement("select CITY_ID from city where CITY_NAME = ? ");       
        pstmt.setString(1, city);
        rs = pstmt.executeQuery();
        rs.first();
        String s = rs.getString("CITY_ID");
        return s;
    }

    //--------------------------------------------------------------------------
     
    
    public ResultSet takeStates(int city_id) throws SQLException {
        initializeJdbc();


        pstmt = conn.prepareStatement("select * from state where CITY_ID = ? ");       
        pstmt.setInt(1, city_id);
        rs = pstmt.executeQuery();

        return rs;
    }


    //--------------------------------------------------------------------------

         
     public String takeStatenameFromID(String id) throws SQLException {
        initializeJdbc();


        pstmt = conn.prepareStatement("select STATE_NAME from state where STATE_ID = ? ");       
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        rs.first();
        if(rs.first()==false)
            return "-1";

        return rs.getString("STATE_NAME");
    }

    //--------------------------------------------------------------------------
     
        public String takeCitynameFromS(String id) throws SQLException {
        initializeJdbc();


        pstmt = conn.prepareStatement("select CTYI_ID from state where STATE_ID = ? ");       
        pstmt.setString(1, id);
        rs = pstmt.executeQuery();
        rs.first();

        String il_id = rs.getString("CITY_ID");
        
        pstmt = conn.prepareStatement("select CITY_NAME from city where CITY_ID = ? ");       
        pstmt.setString(1, il_id);
        rs = pstmt.executeQuery();
        rs.first();
        
        return rs.getString("CITY_NAME");
    }

    
       //--------------------------------------------------------------------------
     
        public String roomPropertyToString(String[] property) throws SQLException {
        
        String s= "";
        
        for(int i=0; i<property.length; i++){
            s=s+property[i]+" ";
            
        }

        return s;
        
    }
      
    //-------------------------------------------------------------------------- 
      
       public boolean checkPassword(String p){            
            
        boolean str=false;
        boolean intt=false;
        boolean realPassword=false;        
        
        if(p.length()>=8&&p.length()<=16){
        for(int i=0; i<p.length(); i++){
           char c=p.charAt(i);
           if(c>=48&&c<=57)
               intt=true;
           else
               str=true;            
        }
        if(str==true&&intt==true)
            realPassword=true;
        }
        else
        realPassword=false;
        
        return realPassword;
        
    }
    

    //--------------------------------------------------------------------------
       
       public boolean checkInt(String num){            
 
        boolean intt=false;
        if(num.length()<3){
        for(int i=0; i<num.length(); i++){
           char c=num.charAt(i);
           if(c>=48&&c<=57)
               intt=true;
           else
               return false;
      }       
        }
        return intt;
        
    }
    //--------------------------------------------------------------------------
       
        public boolean checkTel(String num){      
 
        boolean intt=false;
        if(num.length()==11){
        for(int i=0; i<num.length(); i++){
           char c=num.charAt(i);
           if(c>=48&&c<=57)
               intt=true;
           else
               return false;
      }       
        }
        return intt;
        
    }
        //--------------------------------------------------------------------------
        
        public boolean checkMail(String mail){
        boolean at=false;
        boolean nok=false;
        for(int i=0; i<mail.length(); i++){
            char c=mail.charAt(i);
            if(c==64)
                at=true;
            if(c==46)
                nok=true;            
        }
        if(at==true&&nok==true)
            return true;       
        
        return false;
    }
}