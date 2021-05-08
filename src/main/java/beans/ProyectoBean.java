/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import manager.UsuarioManager;
import model.Proyecto;
import model.Usuario;

/**
 *
 * @author rmareco
 */
@ManagedBean(name = "ProyectoBean")
@SessionScoped
public class ProyectoBean implements Serializable {

    private Proyecto proyecto;
    private List<SelectItem> usuarios;
    private List<Integer> usuariosProyecto;

    public String crear() {
        return "crearProyecto";
    }

    public String agregar() {

        return "inicio";
    }

    public String siguiente() {
        UsuarioManager um = new UsuarioManager();
        List<Usuario> usuariosTemp = new ArrayList<>;
      try {
           usuarios = um.listAll();
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
            return "agregarProyecto";        }
        return "agregarUsuarioProyecto";
    }

    public String modificar() {
        return "modificarProyecto";
    }

    public String listarProyectos() {
        return "listarProyectos";
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public List<Integer> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Integer> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Integer> getUsuariosProyecto() {
        return usuariosProyecto;
    }

    public void setUsuariosProyecto(List<Integer> usuariosProyecto) {
        this.usuariosProyecto = usuariosProyecto;
    }
    
}
