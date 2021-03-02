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
import model.Usuario;
import utils.DBCon;
import utils.Encript;

/**
 *
 * @author usuario
 */
public class UsuarioManager {
        Encript encript = new Encript();

    public boolean add(Usuario usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        
        String sql = "INSERT INTO USUARIO(USERNAME,NOMBRE,APELLIDO,FECHA_CREACION,PASSWORD) "
                + "VALUES(?,?,?,?,?,?)";
        try {
            conn = dbcon.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, (usuario.getUserName().toUpperCase()));
            pst.setString(2, usuario.getNombre());
            pst.setString(3, usuario.getApellido());
            pst.setDate(4, usuario.getFechaCreacion());
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
    
    
    public List<Usuario> listAll() throws SQLException {
        List<Usuario> listaUsuarios = new ArrayList();
        //Usuario usuario = new Usuario();
        Connection conn = null;
        Statement stmt = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT USERNAME,NOMBRE,APELLIDO,FECHA_CREACION FROM USUARIO";
        try {
            conn = dbcon.getConnection();
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
           /* while (rs.next()) { //Este dejo comentado nomas, esta parte ya me perdi... 
                                   // pero algo asi deberia ser, ya no puedo pensar
                String user = rs.getString(1);
                String nombre = rs.getString(2);
                String apellido = rs.getString(3);
                Date fechaCreacion = rs.getDate(4);
                usuario = new Usuario(user, nombre, apellido, fechaCreacion);
                listaUsuarios.add(usuario);
            }*/
        }catch(SQLException ex) {
            Logger.getLogger(UsuarioManager.class.getName()).log(Level.SEVERE, null, ex);
            if (conn != null) {
                conn.close();
            }
        }
        return listaUsuarios;
    }
    
    public boolean authenticate(String userName,String password)throws SQLException {
        Connection conn = null;
        PreparedStatement pst = null;
        DBCon dbcon = new DBCon();
        String sql = "SELECT * FROM ROLES_USUARIOS WHERE USUARIO=?";

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

//    public static void main(String[] args) throws SQLException {
//        UsuarioManager um = new UsuarioManager();
//        Usuario usuario = new Usuario();
//        usuario.setUserName("RONALDMA");
//        usuario.setNombre("Ronaldo");
//        usuario.setApellido("Mareco");
//        usuario.setPassword("contraseña");
//        usuario.setFechaCreacion(new java.sql.Date(new java.util.Date().getTime()));
//        usuario.setIdPerfil(1);
//        um.add(usuario);
//    }
}
