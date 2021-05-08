/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Proyecto;
import model.Tarea;
import model.Usuario;
import utils.DBCon;

/**
 *
 * @author Ross
 */
public class ProyectoManager {

    public boolean add(Proyecto proyecto) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "INSERT INTO PROYECTO(NOMBRE,ID_LIDER) "
                + "VALUES(?,?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, proyecto.getNombre());
            pst.setInt(2, proyecto.getLider().getIdUsuario());
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }

    public boolean update(Proyecto proyecto) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "UPDATE PROYECTO SET NOMBRE=? WHERE ID_PROYECTO=?";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, proyecto.getNombre());
            pst.setInt(2, proyecto.getIdProyecto());
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }

    public boolean asignarUsuarioProyecto(List<Usuario> usuarios, Integer idProyecto) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        String sql = "INSERT INTO PROYECTO_USUARIO(ID_PROYECTO,ID_USUARIO) "
                + "VALUES(?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idProyecto);
            for (Usuario usuario : usuarios) {
                pst.setInt(2, usuario.getIdUsuario());
                pst.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }

    }
    public boolean asignarTareaProyecto(List<Tarea> tareas, Integer idProyecto) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        String sql = "INSERT INTO PROYECTO_TAREA(ID_PROYECTO,ID_TAREA) "
                + "VALUES(?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idProyecto);
            for (Tarea tarea : tareas) {
                pst.setInt(2, tarea.getIdTarea());
                pst.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }

    }

}
