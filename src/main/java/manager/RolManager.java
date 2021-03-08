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
import utils.DBCon;

/**
 *
 * @author Ross
 */

public class RolManager {

    public List<Rol> listAll() throws SQLException {
        List<Rol> listaRoles = new ArrayList();
        Rol rol = new Rol();
        Connection conn = null;
        Statement stmt = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT ID_ROL, CODIGO_ROL,DESCRIPCION,FECHA_CREACION FROM ROL";
        try {
            conn = dbcon.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                rol.setIdRol(rs.getInt(1));
                rol.setCodigoRol(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
                rol.setFechaCreacion(rs.getDate(4));
                listaRoles.add(rol);
                rol = new Rol();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return listaRoles;
    }

    public boolean delete(Rol rol) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "DELETE FROM ROL WHERE CODIGO_ROL=?";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, (rol.getCodigoRol().toUpperCase()));
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }

    public boolean update(Rol rol) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "UPDATE ROL SET CODIGO_ROL=?,DESCRIPCION=? WHERE CODIGO_ROL=?";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, (rol.getCodigoRol().toUpperCase()));
            pst.setString(2, rol.getDescripcion());
            pst.setString(3, (rol.getCodigoRol().toUpperCase()));
            pst.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }
    }

    public boolean add(Rol rol) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "INSERT INTO ROL(CODIGO_ROL,DESCRIPCION,FECHA_CREACION) "
                + "VALUES(?,?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, rol.getCodigoRol().toUpperCase());
            pst.setString(2, rol.getDescripcion());
            pst.setDate(3, new java.sql.Date(new java.util.Date().getTime()));
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

    public List<Rol> getRolesPorUsuario(String usuario) throws SQLException {
        List<Rol> listaRoles = new ArrayList();
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        Statement stmt = null;
        Rol rol = new Rol();

        String sql = "SELECT R.ID_ROL, R.CODIGO_ROL, R.DESCRIPCION, R.FECHA_CREACION "
                + "FROM ROL_USUARIO RU "
                + "JOIN USUARIO U "
                + "ON U.ID_USUARIO = RU.ID_USUARIO "
                + "JOIN ROL R "
                + "ON R.ID_ROL = RU.ID_ROL "
                + "WHERE U.USERNAME =? ";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, usuario.toUpperCase());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                rol.setIdRol(rs.getInt(1));
                rol.setCodigoRol(rs.getString(2));
                rol.setDescripcion(rs.getString(3));
                rol.setFechaCreacion(rs.getDate(4));
                listaRoles.add(rol);
                rol = new Rol();
            }
        } catch (SQLException ex) {
            Logger.getLogger(RolManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return null;
        }
        return listaRoles;
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
