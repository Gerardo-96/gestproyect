/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package manager;

import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Rol;
import model.Usuario;
import utils.DBCon;
import utils.Encript;

/**
 *
 * @author usuario
 */
public class UsuarioManager {

    Encript encript = new Encript();

    public Usuario getById(Integer id) throws SQLException {
        Usuario usuario = new Usuario();
        Connection conn = null;
        PreparedStatement stmt = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT ID_USUARIO, USERNAME, NOMBRE, APELLIDO,FECHA_CREACION,"
                + "PASSWORD FROM USUARIO "
                + "WHERE ID_USUARIO = ?";
        try {
            conn = dbcon.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                usuario.setIdUsuario(rs.getInt(1));
                usuario.setUserName(rs.getString(2));
                usuario.setNombre(rs.getString(3));
                usuario.setApellido(rs.getString(4));
                usuario.setFechaCreacion(rs.getDate(5));
                usuario.setPassword(encript.desencriptar(rs.getString(6)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return usuario;
    }

    public boolean add(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "INSERT INTO USUARIO(USERNAME,NOMBRE,APELLIDO,FECHA_CREACION,PASSWORD) "
                + "VALUES(?,?,?,?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, (usuario.getUserName().toUpperCase()));
            pst.setString(2, usuario.getNombre());
            pst.setString(3, usuario.getApellido());
            pst.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            pst.setString(5, encript.encriptar(usuario.getPassword()));
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

    public boolean delete(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "DELETE FROM USUARIO WHERE USERNAME = ?";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, (usuario.getUserName().toUpperCase()));
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

    public boolean update(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "UPDATE USUARIO SET USERNAME=?,NOMBRE=?,APELLIDO=?,PASSWORD=? "
                + "WHERE USERNAME=?";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, (usuario.getUserName().toUpperCase()));
            pst.setString(2, usuario.getNombre());
            pst.setString(3, usuario.getApellido());
            pst.setString(4, encript.encriptar(usuario.getPassword()));
            pst.setString(5, usuario.getUserName().toUpperCase());
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

    public List<Usuario> listAll() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList();
        Usuario usuario = new Usuario();
        Connection conn = null;
        Statement stmt = null;
        DBCon dbcon = new DBCon();
        RolManager rm = new RolManager();
        String sql = "SELECT USERNAME,NOMBRE,APELLIDO,FECHA_CREACION, PASSWORD,ID_USUARIO FROM USUARIO";
        try {
            conn = dbcon.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                usuario.setUserName(rs.getString(1));
                usuario.setNombre(rs.getString(2));
                usuario.setApellido(rs.getString(3));
                usuario.setFechaCreacion(rs.getDate(4));
                usuario.setPassword(encript.desencriptar(rs.getString(5)));
                usuario.setIdUsuario(rs.getInt(6));
                usuario.setRoles(rm.getRolesPorUsuario(rs.getString(1)));
                listaUsuarios.add(usuario);
                usuario = new Usuario();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return listaUsuarios;
    }

    public boolean authenticate(String userName, String password) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT * FROM USUARIO WHERE USERNAME=? AND PASSWORD =?";

        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, userName.toUpperCase());
            pst.setString(2, encript.encriptar(password));
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
            return false;
        }

    }

    public boolean asignarRol(Usuario usuario, Rol rol) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();

        String sql = "INSERT INTO ROL_USUARIO(ID_USUARIO,ID_ROL) "
                + "VALUES(?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, usuario.getIdUsuario());
            pst.setInt(2, rol.getIdRol());
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

    public static void main(String[] args) throws SQLException {
        UsuarioManager um = new UsuarioManager();
//        Usuario usuario = new Usuario();
//        usuario.setUserName("ROSSVI");
//        usuario.setNombre("Rossana");
//        usuario.setApellido("Villegas");
//        usuario.setPassword("contraseña");
//        usuario.setFechaCreacion(new java.sql.Date(new java.util.Date().getTime()));
//        um.add(usuario);
        um.listAll();
    }
}
