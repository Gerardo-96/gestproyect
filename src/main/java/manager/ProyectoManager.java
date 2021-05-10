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

        String sql = "INSERT INTO PROYECTO(NOMBRE,DESCRIPCION,ID_LIDER) "
                + "VALUES(?,?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, proyecto.getNombre());
            pst.setString(2, proyecto.getDescripcion());
            pst.setInt(3, proyecto.getIdLider());
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

        String sql = "UPDATE PROYECTO SET NOMBRE=?, SET DESCRIPCION =? WHERE ID_PROYECTO=?";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, proyecto.getNombre());
            pst.setString(1, proyecto.getDescripcion());
            pst.setInt(3, proyecto.getIdProyecto());
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
    public boolean asignarTareaProyecto(List<Integer> idTareas, Integer idProyecto) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        String sql = "INSERT INTO PROYECTO_TAREA(ID_PROYECTO,ID_TAREA) "
                + "VALUES(?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, idProyecto);
            for (Integer tarea : idTareas) {
                pst.setInt(2, tarea);
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

    public List<Proyecto> listAll() throws SQLException {
        List<Proyecto> listaProyectos = new ArrayList();
        Proyecto proyecto = new Proyecto();
        Connection conn = null;
        Statement stmt = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT ID_PROYECTO, NOMBRE, DESCRIPCION, ID_LIDER"
                + "FROM PROYECTO";
        try {
            conn = dbcon.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                proyecto.setIdProyecto(rs.getInt(1));
                proyecto.setNombre(rs.getString(2));
                proyecto.setDescripcion(rs.getString(3));
                proyecto.setIdLider(rs.getInt(4));
                listaProyectos.add(proyecto);
                proyecto = new Proyecto();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return listaProyectos;
    }
    
}
