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
import javax.faces.model.SelectItem;
import manager.TareaManager;
import model.Tarea;
import model.Usuario;

/**
 *
 * @author rmareco
 */
@ManagedBean(name = "TareaBean")
@SessionScoped
public class TareaBean implements Serializable {

    private Tarea tarea;
    private List<Tarea> tareas;
    private List<SelectItem> tareasItem;
    private SelectItem tareaPadreSelected;

    public String crear() {
        return "agregarTarea";
    }

    public String renderTarea() {
        TareaManager tm = new TareaManager();
        List<Tarea> tareasTemp = new ArrayList<>();
        try {
            tareasTemp = tm.listAll();
            SelectItem selectItem = new SelectItem();
            for (Tarea tareaTemp : tareasTemp) {
                selectItem.setLabel(tareaTemp.getNombre());
                selectItem.setValue(tareaTemp.getIdTarea());
                tareasItem.add(selectItem);
                selectItem = new SelectItem();
            }
        } catch (SQLException ex) {
            Logger.getLogger(TareaBean.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "tarea";
    }

    public String agregar() {
        TareaManager tm = new TareaManager();
        tarea.setIdTareaPadre((Integer) tareaPadreSelected.getValue());
        try {
            if (tm.add(tarea)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro Exitoso!", " Exitoso!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TareaManager.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "inicio";
    }

    public String modificar() {
        return "modificarTarea";
    }

    public String listarTareas() {
        TareaManager tm = new TareaManager();
        try {
            tareas = tm.listAll();
            if (tareas.isEmpty()) {//Pregunta si la lista esta vacia
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "La lista esta vacia ", "Atención!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TareaBean.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "listarTarea";
    }

    public String editarTarea() {
        TareaManager tm = new TareaManager();
        tarea.setIdTareaPadre((Integer) tareaPadreSelected.getValue());
        try {
            if (tm.update(tarea)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "El rol se actualizo con Exito", " Exito!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Error al intentar actualizar el rol", " Error!"));
                return "";
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listarTareas";
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public List<SelectItem> getTareasItem() {
        return tareasItem;
    }

    public void setTareasItem(List<SelectItem> tareasItem) {
        this.tareasItem = tareasItem;
    }

    public SelectItem getTareaPadreSelected() {
        return tareaPadreSelected;
    }

    public void setTareaPadreSelected(SelectItem tareaPadreSelected) {
        this.tareaPadreSelected = tareaPadreSelected;
    }

}
