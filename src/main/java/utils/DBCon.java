package utils;

/**
 *
 * @author usuario
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBCon {

    public Connection getConnection() {
        Connection con = null;
        String dbName = "gestprodb";
        String usuario = "gestpro";
        String contraseña = "GestPro2021";
        String dbURL = "jdbc:postgresql://localhost:5432/" + dbName;
        try {
            String dbDriver = "org.postgresql.Driver";
            Class.forName(dbDriver);
            con = DriverManager.getConnection(dbURL,usuario,contraseña);
            System.out.println("DB Connecting");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Database.getConnection() Error -->" + e.getMessage());
        }
        return con;
    }

    public void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
    
//    public static void main(String[] args) {
//        DBCon conn = new DBCon();
//        conn.getConnection();
//    }
}
