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
import model.LineaBase;
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
        String sql = "";
        if (tarea.getIdTareaPadre() != null) {
            sql = "INSERT INTO TAREA(ESTADO,NOMBRE,DESCRIPCION, VERSION,PRIORIDAD,OBSERVACION, EDITABLE, ID_PADRE) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
        } else {
            sql = "INSERT INTO TAREA(ESTADO,NOMBRE,DESCRIPCION, VERSION,PRIORIDAD,OBSERVACION, EDITABLE) "
                    + "VALUES(?,?,?,?,?,?,?)";
        }

        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, tarea.getEstado());
            pst.setString(2, tarea.getNombre());
            pst.setString(3, tarea.getDescripcion());
            pst.setString(4, tarea.getVersion());
            pst.setString(5, tarea.getPrioridad());
            pst.setString(6, tarea.getObservacion());
            pst.setBoolean(7, true);
            if (tarea.getIdTareaPadre() != null) {
                pst.setInt(8, tarea.getIdTareaPadre());

            }
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
        String sql = "";
        if (tarea.getIdTareaPadre() != 0) {
            sql = "UPDATE TAREA SET ESTADO=?,NOMBRE=?, DESCRIPCION=?, VERSION=?, PRIORIDAD=?, OBSERVACION=?, EDITABLE=?, ID_PADRE =? WHERE ID_TAREA=?";
        } else {
            sql = "UPDATE TAREA SET ESTADO=?,NOMBRE=?, DESCRIPCION=?, VERSION=?, PRIORIDAD=?, OBSERVACION=?, EDITABLE=? WHERE ID_TAREA=?";
        }
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, tarea.getEstado());
            pst.setString(2, tarea.getNombre());
            pst.setString(3, tarea.getDescripcion());
            pst.setString(4, tarea.getVersion());
            pst.setString(5, tarea.getPrioridad());
            pst.setString(6, tarea.getObservacion());
            pst.setBoolean(7, tarea.isEditable());
            if (tarea.getIdTareaPadre() != 0) {
                pst.setInt(8, tarea.getIdTareaPadre());
                pst.setInt(9, tarea.getIdTarea());
            } else {
                pst.setInt(8, tarea.getIdTarea());
            }
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
        String sql = "SELECT ID_TAREA, NOMBRE, ESTADO, DESCRIPCION, VERSION, PRIORIDAD, OBSERVACION, ID_PADRE, EDITABLE "
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
                tarea.setVersion(rs.getString(5));
                tarea.setPrioridad(rs.getString(6));
                tarea.setObservacion(rs.getString(7));
                tarea.setIdTareaPadre(rs.getInt(8));
                tarea.setEditable(rs.getBoolean(9));
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

    public List<Tarea> listarTareasEditables() throws SQLException {
        List<Tarea> listaTareas = new ArrayList();
        Tarea tarea = new Tarea();
        Connection conn = null;
        Statement stmt = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT ID_TAREA, NOMBRE, ESTADO, DESCRIPCION, VERSION, PRIORIDAD, OBSERVACION, ID_PADRE, EDITABLE "
                + "FROM TAREA WHERE EDITABLE = TRUE";
        try {
            conn = dbcon.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                tarea.setIdTarea(rs.getInt(1));
                tarea.setNombre(rs.getString(2));
                tarea.setEstado(rs.getString(3));
                tarea.setDescripcion(rs.getString(4));
                tarea.setVersion(rs.getString(5));
                tarea.setPrioridad(rs.getString(6));
                tarea.setObservacion(rs.getString(7));
                tarea.setIdTareaPadre(rs.getInt(8));
                tarea.setEditable(rs.getBoolean(9));
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

    public boolean addLineaBase(String nombreLineaBase, List<Tarea> tareas) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        PreparedStatement pstLB = null;
        PreparedStatement pstT = null;

        DBCon dbcon = new DBCon();
        String sqlLineaBase = "INSERT INTO LINEA_BASE(NOMBRE) "
                + "VALUES(?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sqlLineaBase);
            pst.setString(1, nombreLineaBase);
            pst.execute();
        } catch (SQLException ex) {
            Logger.getLogger(TareaManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        LineaBase lineaBase = getUltimaLineaBaseByNombre(nombreLineaBase);
        String sqlLBTarea = "INSERT INTO LINEA_BASE(ID_LINEA_BASE,ID_TAREA) "
                + "VALUES(?,?)";
        String sqlTarea = "UPDATE TAREA EDITABLE = ? WHERE ID_TAREA = ?";

        try {
            conn = dbcon.getConnection();
            pstLB = conn.prepareStatement(sqlLBTarea);
            pstT = conn.prepareStatement(sqlTarea);
            pstLB.setInt(1, lineaBase.getIdLineaBase());
            for (Tarea tarea : tareas) {
                pstLB.setInt(2, tarea.getIdTarea());
                pstT.setBoolean(1, tarea.isEditable());
                pstT.setInt(2, lineaBase.getIdLineaBase());
                pstT.execute();
                pstLB.execute();
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(TareaManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }

    public boolean esLineaBase(Integer idTarea) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT EDITABLE "
                + "FROM TAREA "
                + "WHERE ID_TAREA = ?";
        try {
            conn = dbcon.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idTarea);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                return rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    public LineaBase getUltimaLineaBaseByNombre(String nombre) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        LineaBase lineaBase = new LineaBase();
        DBCon dbcon = new DBCon();
        String sql = "SELECT ID_LINEA_BASE, NOMBRE"
                + "FROM LINEA_BASE "
                + "WHERE NOMBRE = ?"
                + "ORDER BY ID_LINEA_BASE DESC";
        try {
            conn = dbcon.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lineaBase.setIdLineaBase(rs.getInt(1));
                lineaBase.setNombre(rs.getString(1));
                break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return lineaBase;
    }

}
