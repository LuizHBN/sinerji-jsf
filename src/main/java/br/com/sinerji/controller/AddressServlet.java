package br.com.sinerji.controller;

import br.com.sinerji.model.address.Address;
import br.com.sinerji.model.address.AddressDTO;
import br.com.sinerji.model.user.User;
import br.com.sinerji.model.user.UserDTO;
import br.com.sinerji.service.AddressService;
import br.com.sinerji.service.UserService;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/address")
public class AddressServlet extends HttpServlet {

    @EJB
    private AddressService addressService;

    @Override
    public void init() throws ServletException {
        addressService = new AddressService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            processGetRequest(idParam, resp);
        } else {
            sendErrorResponse(resp, "Parâmetro 'id' não encontrado", HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void processGetRequest(String idParam, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(idParam);
            Address address = addressService.findAddressById(id);
            if (address != null) {
                sendJsonResponse(resp, address);
            } else {
                sendErrorResponse(resp, "Endereço não encontrado para o ID: " + id, HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(resp, "ID inválido", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            sendErrorResponse(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processPostPutDeleteRequest(req, resp, "adicionar", addressService::addNewAddress, HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostPutDeleteRequest(req, resp, "atualizar", addressService::updateAddress, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostPutDeleteRequest(req, resp, "excluir", addressService::deleteAddress, HttpServletResponse.SC_OK);
    }

    private void processPostPutDeleteRequest(HttpServletRequest req, HttpServletResponse resp, String action, AddressServlet.ActionFunction actionFunction, int successStatusCode) throws IOException {
        try {
            String json = addressService.requestToString(req);
            AddressDTO addressDTO = new Gson().fromJson(json, AddressDTO.class);
            actionFunction.apply(addressDTO);
            resp.setStatus(successStatusCode);
        } catch (Exception e) {
            sendErrorResponse(resp, "Erro ao " + action + " usuário: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, Address address) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(address));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void sendErrorResponse(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.getWriter().write(message);
        resp.setStatus(statusCode);
    }

    @FunctionalInterface
    interface ActionFunction {
        void apply(AddressDTO addressDTO) throws Exception;
    }
}
