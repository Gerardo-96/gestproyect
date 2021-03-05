/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import manager.RolManager;
import model.Rol;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "RolBean")
@SessionScoped
public class RolBean {
    Rol rol = new Rol();
    RolManager rolMg = new RolManager();
    List<Rol> roles = new ArrayList();
    
    public String agregar() {
        try {
            if (rolMg.add(rol)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ok", "Rol agregado con Exitoso!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", " al intentar agregar el rol!"));
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        limpiarCampos();
        return "inicio";
    }
    
    public void listarRoles() {
        try {
            roles = rolMg.listAll();
            if (roles.isEmpty()) {//Pregunta si la lista esta vacia
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Atencion", " La lista esta vacia!"));
            } else {
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String eliminarRol() {
        reload();
        try {
            if (rolMg.delete(rol)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ok", " Rol Borrado con Exitoso!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", " al intentar borrar el rol!"));
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listarRoles";
    }

    public String editarRol() {
        reload();
        try {
            if (rolMg.update(rol)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ok", " El rol se actualizo con Exitoso!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", " al intentar actualizar el rol!"));
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listarRoles";
    }
    
    public void reload() {
        try {
            roles.clear();
            roles = rolMg.listAll();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    private void limpiarCampos() {
        rol = new Rol();
        roles.clear();
    }
}
