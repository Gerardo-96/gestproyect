/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Rol;
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
    
    public List<Tarea> listAll() throws SQLException {
        List<Tarea> listaTareas = new ArrayList();
        Tarea tarea = new Tarea();
        Connection conn = null;
        Statement stmt = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT ID_TAREA, NOMBRE, ESTADO, DESCRIPCION, ID_PADRE, EDITABLE "
                + "FROM TAREA";
        try {
            conn = dbcon.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tarea.setIdTarea(rs.getInt(1));
                tarea.setNombre(rs.getString(2));
                tarea.setEstado(rs.getString(3));
                tarea.setDescripcion(rs.getString(4));
                tarea.setIdTareaPadre(rs.getInt(5));
                tarea.setEditable(rs.getBoolean(5));
                listaTareas.add(tarea);
                tarea = new Tarea();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return listaTareas;
    }
    
}
