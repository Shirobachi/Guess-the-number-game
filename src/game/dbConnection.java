 package game;

import java.sql.Connection;
import java.sql.DriverManager;
 public class dbConnection {
    public Connection dbConn;

    public Connection getConnection(){
        String nazwa = "db_f180";
        String konto = "f180";
        String pass = "NDM3_3eee";
        String url = "jdbc:mysql://front.mikr.us/"+nazwa;

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbConn = DriverManager.getConnection(url,konto,pass);
        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return dbConn;
    }


}