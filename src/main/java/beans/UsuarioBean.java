/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Usuario;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "UsuarioBean")
@SessionScoped
public class UsuarioBean {
    
    Usuario usuario = new Usuario();
    List<Usuario> usuarios = new ArrayList();
    
    public String listarUsuarios(){
        return "listarUsuarios";
    }
    
    public String agregar(Usuario usuario){
        return "inicio";
    }
    
    public String eliminarUsuario(){
        reload();
        return "listarUsuarios";
    }
    
    public String editarUsuario(){
        reload();
        return "listarUsuarios";
    }
    
    public void reload(){
        listarUsuarios();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
    
}
