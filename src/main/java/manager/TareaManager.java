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
import model.Tarea;
import utils.DBCon;

/**
 *
 * @author Ross
 */
public class TareaManager {
    
     public boolean add(Tarea tarea) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "INSERT INTO TAREA(ESTADO,NOMBRE, DESCRIPCION, ID_PADRE) "
                + "VALUES(?,?,?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, tarea.getEstado());
            pst.setString(2, tarea.getNombre());
            pst.setString(3, tarea.getDescripcion());
            pst.setInt(4, tarea.getIdTareaPadre());
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TareaManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }
    public boolean update(Tarea tarea) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "UPDATE TAREA SET ESTADO=?,NOMBRE=?, DESCRIPCION=? WHERE ID_TAREA=?";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, tarea.getEstado());
            pst.setString(2, tarea.getNombre());
            pst.setString(3, tarea.getDescripcion());
            pst.setInt(4, tarea.getIdTarea());
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TareaManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }
    
}
