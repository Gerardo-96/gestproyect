/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Rol;
import utils.DBCon;

/**
 *
 * @author Ross
 */
public class RolManager {
    
    public boolean add(Rol rol) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        
        String sql = "INSERT INTO ROL(CODIGO_ROL,DESCRIPCION,FECHA_CREACION) "
                + "VALUES(?,?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rol.getCodigoRol());
            pst.setString(2, rol.getDescripcion());
            pst.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
          ;
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RolManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }
   public static void main(String[] args) throws SQLException {
   RolManager um = new RolManager();
        Rol rol = new Rol();
       rol.setCodigoRol("Admin");
        rol.setDescripcion("Administrador");
       rol.setFechaCreacion(new java.sql.Date(new java.util.Date().getTime()));

       um.add(rol);
   }
}
