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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import manager.ProyectoManager;
import manager.TareaManager;
import manager.UsuarioManager;
import model.Proyecto;
import model.Tarea;
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
    List<Tarea> tareas;
    private List<Integer> tareasProyecto;
    private List<Integer> usuariosProyecto;
    private SelectItem liderSelected = new SelectItem();
    private List<Proyecto> proyectos;
    private Usuario liderObject;

    public String crear() {
        UsuarioManager um = new UsuarioManager();
        List<Usuario> usuariosTemp = new ArrayList<>();
        usuarios = new ArrayList<>();
        proyecto = new Proyecto();
//        liderSelected = new SelectItem();
        try {
            usuariosTemp = um.listAll();
            SelectItem selectItem = new SelectItem();
            for (Usuario usuario : usuariosTemp) {
                usuarios.add(new SelectItem(String.valueOf(usuario.getIdUsuario()),usuario.getUserName()));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
            return "inicio";
        }
        return "crearProyecto";
    }

    public String agregarProyecto() {
        ProyectoManager pm = new ProyectoManager();
//        proyecto.setIdLider((Integer) liderSelected.getValue());
        try {
            if (pm.add(proyecto)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro Exitoso!", " Exitoso!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "inicio";
    }

    public String agregarTareas() {
        ProyectoManager pm = new ProyectoManager();
        try {
            if (pm.asignarTareaProyecto(tareasProyecto, proyecto.getIdProyecto())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro Exitoso!", " Exitoso!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "proyecto";
    }

    public String renderProyecto() {
        UsuarioManager um = new UsuarioManager();
        List<Usuario> usuariosTemp = new ArrayList<>();
        SelectItem selectItem = new SelectItem();
        usuarios = new ArrayList<>();
        try {
            usuariosTemp = um.listAll();
            for (Usuario usuario : usuariosTemp) {
                selectItem.setLabel(usuario.getUserName());
                selectItem.setValue(usuario.getIdUsuario());
                usuarios.add(selectItem);
                selectItem = new SelectItem();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
            return "agregarProyecto";
        }
        return "proyecto";
    }

    public String asignarTareas() {
        TareaManager tm = new TareaManager();
        tareas = new ArrayList<>();
        try {
            tareas = tm.listAll();
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "agregarTareaProyecto";
    }

    public String editarProyecto() {
        ProyectoManager pm = new ProyectoManager();
        try {
            pm.update(proyecto);
            if (tareasProyecto != null && !tareasProyecto.isEmpty()) {
                pm.asignarTareaProyecto(tareasProyecto, proyecto.getIdProyecto());
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProyectoBean.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "listarProyectos";
    }

    public String modificar() {

        return "modificarProyecto";
    }

    public String listarProyectos() {
        ProyectoManager pm = new ProyectoManager();
        try {
            proyectos = pm.listAll();
            if (proyectos.isEmpty()) {//Pregunta si la lista esta vacia
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "La lista esta vacia ", "Atención!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "listarProyectos";
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    public List<SelectItem> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<SelectItem> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Integer> getUsuariosProyecto() {
        return usuariosProyecto;
    }

    public void setUsuariosProyecto(List<Integer> usuariosProyecto) {
        this.usuariosProyecto = usuariosProyecto;
    }

    public SelectItem getLiderSelected() {
        return liderSelected;
    }

    public void setLiderSelected(SelectItem liderSelected) {
        this.liderSelected = liderSelected;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    public Usuario getLiderObject() {
        return liderObject;
    }

    public void setLiderObject(Usuario liderObject) {
        this.liderObject = liderObject;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<Integer> getTareasProyecto() {
        return tareasProyecto;
    }

    public void setTareasProyecto(List<Integer> tareasProyecto) {
        this.tareasProyecto = tareasProyecto;
    }
    
    public void prueba (ValueChangeEvent event){
        System.out.println(event.getOldValue() + " new : " + event.getNewValue());
    }

}
