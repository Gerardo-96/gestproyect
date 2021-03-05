/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import manager.UsuarioManager;
import model.Usuario;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "UsuarioBean")
@SessionScoped
public class UsuarioBean {

    Usuario usuario = new Usuario();
    UsuarioManager usuarioMg = new UsuarioManager();
    List<Usuario> usuarios = new ArrayList();
    private String confirmarPass;

    public void listarUsuarios() {
        try {
            usuarios = usuarioMg.listAll();
            if (usuarios.isEmpty()) {//Pregunta si la lista esta vacia
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Atencion", " La lista esta vacia!"));
            } else {//Aca hay que recorrer la lista e imprimir
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String agregar() {
        String pass = usuario.getPassword();
        String confirmarpass = confirmarPass;
        if (pass.equals(confirmarpass)) {
            try {
                if (usuarioMg.add(usuario)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Registro", " Exitoso!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error", " al registrar usuario!"));
                    return "";
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error", " Las contraseñas no coinciden!"));
            return "";
        }
        limpiarCampos();
        return "inicio";
    }

    public String eliminarUsuario() {
        reload();
        return "listarUsuarios";
    }

    public String editarUsuario() {
        reload();
        return "listarUsuarios";
    }

    public void reload() {
        try {
            usuarios.clear();
            usuarios = usuarioMg.listAll();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public String getConfirmarPass() {
        return confirmarPass;
    }

    public void setConfirmarPass(String confirmarPass) {
        this.confirmarPass = confirmarPass;
    }

    private void limpiarCampos() {
        usuario = new Usuario();
        confirmarPass = "";
        usuarios.clear();
    }

}
