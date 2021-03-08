/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author ronaldma
 */
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import manager.RolManager;
import manager.UsuarioManager;
import model.Rol;
import utils.Util;

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String password;
    private String message;
    private String userName;
    private RolManager rolMng = new RolManager();
    private List<Rol> roles;

    public LoginBean() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }
    
    

    public String loginProject() {
        try {
            UsuarioManager usuarioMng = new UsuarioManager();
            boolean result = usuarioMng.authenticate(userName, password);
            if (result) {
                roles = rolMng.getRolesPorUsuario(userName);
                // get Http Session and store username
                HttpSession session = Util.getSession();
                session.setAttribute("userName", userName);
                session.setAttribute("roles", roles);
                return "inicio";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "Login Invalido!",
                        "Favor Reintentar!"));

                // invalidate session, and redirect to other pages
                //message = "Invalid Login. Please Try Again!";
                return "login";
            }
        } catch (Exception ex) {
            Logger.getLogger(LoginBean.class.getName()).log(Level.SEVERE, null, ex);
            return "NOLogin";
        }
    }

    public String logout() {
        HttpSession session = Util.getSession();
        session.invalidate();
        return "login";
    }
}
