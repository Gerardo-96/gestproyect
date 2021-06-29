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
    private String nombreLineaBase;
    private List<Tarea> tareasEditables;

    public String crear() {
        tarea = new Tarea();
        tarea.setVersion("1.0.0");
        return "agregarTarea";
    }

    public String renderTarea() {
        TareaManager tm = new TareaManager();
        List<Tarea> tareasTemp = new ArrayList<>();
        tareasItem = new ArrayList<>();
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
        if (tareaPadreSelected != null) {
            tarea.setIdTareaPadre((Integer) tareaPadreSelected.getValue());
        }
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
        return "listarTareas";
    }

    public String editarTarea() {
        TareaManager tm = new TareaManager();
        if (tareaPadreSelected != null) {
            tarea.setIdTareaPadre((Integer) tareaPadreSelected.getValue());
        }
        try {
            if (tm.esLineaBase(tarea.getIdTarea())) {
                if (tm.update(tarea)) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "La tarea actualizo con Exito", " Exito!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al intentar actualizar la tarea", " Error!"));
                    return "";
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "No se puede actualizar la tarea porque forma parte de una linea base", " Error!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "listarTareas";
    }

    public String aumentarVersion(String versionActual) {
        String[] parts = versionActual.trim().split("\\.");
        String version = "";
        parts[parts.length - 1] = String.valueOf(Integer.parseInt(parts[parts.length - 1]) + 1);
        for (int i = parts.length - 1; i > 0; i--) {
            if (parts[i].equals("10")) {
                parts[i] = "0";
                parts[i - 1] = String.valueOf(Integer.parseInt(parts[i - 1]) + 1);
            } else {
                parts[i] = String.valueOf(Integer.parseInt(parts[i]) + 1);
            }
        }
        version = parts[0];
        for (int i = 1; i < parts.length; i++) {
            version = version.concat(".").concat(parts[i]);
        }
        return version;
    }

    public String agregarLineaBase() {
        TareaManager tm = new TareaManager();
//        tareasEditables = tm.listarTareasEditables();
        return "agregarLineaBase";
    }

    public String crearLineaBase() {
        TareaManager tm = new TareaManager();
        tarea.setIdTareaPadre((Integer) tareaPadreSelected.getValue());
        try {
            if (tm.addLineaBase(nombreLineaBase, tareasSeleccionadas(tareasEditables))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Registro Exitoso!", " Exitoso!"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TareaManager.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        return "inicio";
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

    public String getNombreLineaBase() {
        return nombreLineaBase;
    }

    public void setNombreLineaBase(String nombreLineaBase) {
        this.nombreLineaBase = nombreLineaBase;
    }

    public List<Tarea> getTareasEditables() {
        return tareasEditables;
    }

    public void setTareasEditables(List<Tarea> tareasEditables) {
        this.tareasEditables = tareasEditables;
    }

    public List<Tarea> tareasSeleccionadas(List<Tarea> tareasEditables) {
        List<Tarea> seleccionadas = new ArrayList<>();
        for (Tarea tareaEditable : tareasEditables) {
            if (tareaEditable.isSeleccionada()) {
                seleccionadas.add(tareaEditable);
            }
        }
        return seleccionadas;
    }
}
