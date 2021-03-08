/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Rol;

/**
 *
 * @author usuario
 */
@ManagedBean(name = "Facelets")
@SessionScoped
public class Facelets {
    
    public boolean poseeRol(List<Rol> roles, String rolValidate){
        for(Rol rol : roles){
            if(rol.getCodigoRol().equalsIgnoreCase(rolValidate)){
                return true;
            }
        }
        return false;
    }
    
}
