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
@ManagedBean(name = "ProyectoBean")
@SessionScoped
public class ProyectoBean  implements Serializable {
    
    public String crear(){
        return "crearProyecto";
    }
    
    public String modificar(){
        return "modificarProyecto";
    }
    
    public String listarProyectos(){
         return "listarProyectos";
    }
    
}
