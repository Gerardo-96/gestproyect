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
        String sql = "SELECT CODIGO_ROL,DESCRIPCION,FECHA_CREACION FROM ROL";
        try {
            conn = dbcon.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) { 
                rol.setCodigoRol(rs.getString(1));
                rol.setDescripcion(rs.getString(2));
                rol.setFechaCreacion(rs.getDate(3));
                listaRoles.add(rol);
                rol = new Rol();
            }
        }catch(SQLException ex) {
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
            pst.setString(1, rol.getCodigoRol());
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
   public static void main(String[] args) throws SQLException {
   RolManager um = new RolManager();
        Rol rol = new Rol();
       rol.setCodigoRol("Admin");
        rol.setDescripcion("Administrador");
       rol.setFechaCreacion(new java.sql.Date(new java.util.Date().getTime()));

       um.add(rol);
   }
}
