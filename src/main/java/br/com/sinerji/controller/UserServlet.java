package br.com.sinerji.controller;

import br.com.sinerji.model.pet.Pet;
import br.com.sinerji.model.user.User;
import br.com.sinerji.model.user.UserDTO;
import br.com.sinerji.service.UserService;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @EJB
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            processGetRequest(idParam, resp);
        } else {
            List<User> users = userService.findAllUsers();
            sendJsonResponse(resp, users);
        }
    }

    private void processGetRequest(String idParam, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(idParam);
            User user = userService.findUserById(id);
            if (user != null) {
                sendJsonResponse(resp, user);
            } else {
                sendErrorResponse(resp, "Usuário não encontrado para o ID: " + id, HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(resp, "ID inválido", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            sendErrorResponse(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processPostPutDeleteRequest(req, resp, "adicionar", userService::addNewUser, HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostPutDeleteRequest(req, resp, "atualizar", userService::updateUser, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostPutDeleteRequest(req, resp, "excluir", userService::deleteUser, HttpServletResponse.SC_OK);
    }

    private void processPostPutDeleteRequest(HttpServletRequest req, HttpServletResponse resp, String action, ActionFunction actionFunction, int successStatusCode) throws IOException {
        try {
            String json = userService.requestToString(req);
            UserDTO userDTO = new Gson().fromJson(json, UserDTO.class);
            actionFunction.apply(userDTO);
            resp.setStatus(successStatusCode);
        } catch (Exception e) {
            sendErrorResponse(resp, "Erro ao " + action + " usuário: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, User user) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(user));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    private void sendJsonResponse(HttpServletResponse resp, List<User> users) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(users));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void sendErrorResponse(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.getWriter().write(message);
        resp.setStatus(statusCode);
    }

    @FunctionalInterface
    interface ActionFunction {
        void apply(UserDTO userDTO) throws Exception;
    }
}
