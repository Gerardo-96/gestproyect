/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author rmareco
 */
@ManagedBean(name = "TareaBean")
@SessionScoped
public class TareaBean implements Serializable {

    public String crear() {
        return "crearTarea";
    }

    public String modificar() {
        return "modificarTarea";
    }

    public String listarTareas() {
        return "listarTarea";
    }

}
