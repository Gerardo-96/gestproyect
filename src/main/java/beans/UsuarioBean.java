/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import static java.lang.Integer.getInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import manager.RolManager;
import manager.UsuarioManager;
import model.Rol;
import model.Usuario;
import org.primefaces.util.LangUtils;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "UsuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

    Usuario usuario = new Usuario();
    UsuarioManager usuarioMg = new UsuarioManager();
    List<Usuario> usuarios = new ArrayList();
    RolManager rolMg = new RolManager();
    List<Usuario> filtros = new ArrayList();
    private String confirmarPass;
    private List<SelectItem> roles = new ArrayList();
    private String rolSelected;    

    public String renderUsuario() {
        return "usuario";
    }

    public String renderAsignarRol() {
        List<Rol> rolesTem;
        SelectItem selectItem = new SelectItem();
        if (roles.isEmpty()) {
            rolesTem = obtenerRoles();
            for (Rol rol : rolesTem) {
                selectItem.setLabel(rol.getCodigoRol());
                selectItem.setValue(rol.getCodigoRol());
                roles.add(selectItem);
                selectItem = new SelectItem();
            }
        }
        return "agregarRolUsuario";
    }

    public String asignarRol() {
        try {
            
            if (usuarioMg.asignarRol(usuario, rolMg.getRolPorCodigo(rolSelected))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro Exitoso!", " Exitoso!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        rolSelected = null;
        return "usuario";
    }

    public String listarUsuarios() {
        try {
            usuarios = usuarioMg.listAll();
            if (usuarios.isEmpty()) {//Pregunta si la lista esta vacia
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Atencion", " La lista esta vacia!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listarUsuarios";
    }

    public String agregar() {
        String pass = usuario.getPassword();
        String confirmarpass = confirmarPass;
        if (pass.equals(confirmarpass)) {
            try {
                if (usuarioMg.add(usuario)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Registro Exitoso!", " Exitoso!"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "No coinciden las contraseñas", " Error!"));
            return "";
        }
        limpiarCampos();
        return "inicio";
    }

    public String eliminarUsuario() {
        try {
            if (usuarioMg.delete(usuario)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ok", " Registro Borrado con Exitoso!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", " al intentar borrar el registrar!"));
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        reload();
        return "listarUsuarios";
    }

    public String editarUsuario() {
        try {
            if (usuarioMg.update(usuario)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Ok", " El registro se actualizo con Exitoso!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error", " al intentar actualizar el registrar!"));
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public List<Rol> obtenerRoles() {
        try {
            return rolMg.listAll();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            return null;
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

    public List<Usuario> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<Usuario> filtros) {
        this.filtros = filtros;
    }

    public String getConfirmarPass() {
        return confirmarPass;
    }

    public void setConfirmarPass(String confirmarPass) {
        this.confirmarPass = confirmarPass;
    }

    public List<SelectItem> getRoles() {
        return roles;
    }

    public void setRoles(List<SelectItem> roles) {
        this.roles = roles;
    }

    public String getRolSelected() {
        return rolSelected;
    }

    public void setRolSelected(String rolSelected) {
        this.rolSelected = rolSelected;
    }

    private void limpiarCampos() {
        usuario = new Usuario();
        confirmarPass = "";
        usuarios.clear();
        roles.clear();
        rolSelected = null;
    }

}
