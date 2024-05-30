package br.com.sinerji.controller.user;

import br.com.sinerji.model.user.User;
import br.com.sinerji.model.user.UserDTO;
import br.com.sinerji.service.UserService;
import com.google.gson.Gson;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name = "userBean")
@RequestScoped
public class UserBean implements Serializable {

    @Inject
    private UserService userService;

    User user;
    private UserDTO userDTO = new UserDTO();

    @PostConstruct
    public void init() {
        try {
            //TODO -> método find all
            user = userService.findUserById(4L);
        } catch (Exception e) {
            addMessage("Error", "Could not load users: " + e.getMessage());
        }
    }

    public void addUser() {
        try {
            userService.addNewUser(userDTO);
            addMessage("Success", "User added successfully");
            init(); // Refresh the list of users
        } catch (Exception e) {
            addMessage("Error", "Could not add user: " + e.getMessage());
        }
    }

    public void updateUser() {
        try {
            userService.updateUser(userDTO);
            addMessage("Success", "User updated successfully");
            init(); // Refresh the list of users
        } catch (Exception e) {
            addMessage("Error", "Could not update user: " + e.getMessage());
        }
    }

    public void deleteUser(Long id) {
        try {
            userService.deleteUser(new UserDTO(id));
            addMessage("Success", "User deleted successfully");
            init(); // Refresh the list of users
        } catch (Exception e) {
            addMessage("Error", "Could not delete user: " + e.getMessage());
        }
    }

    public User getUser() {
        return user;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    private void addMessage(String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(summary, detail));
    }
}
