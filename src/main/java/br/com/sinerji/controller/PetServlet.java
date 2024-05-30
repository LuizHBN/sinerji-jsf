package br.com.sinerji.controller;


import br.com.sinerji.model.pet.Pet;
import br.com.sinerji.model.pet.PetDTO;
import br.com.sinerji.service.PetService;
import com.google.gson.Gson;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/pet")
public class PetServlet extends HttpServlet {
    @EJB
    private PetService petService;

    @Override
    public void init() throws ServletException {
        petService = new PetService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");

        if (idParam != null && !idParam.isEmpty()) {
            processGetRequest(idParam, resp);
        } else {
            List<Pet> pets = petService.findAllPets();
            sendJsonResponse(resp, pets);
        }
    }

    private void processGetRequest(String idParam, HttpServletResponse resp) throws IOException {
        try {
            long id = Long.parseLong(idParam);
            Pet pet = petService.findPetById(id);
            if (pet != null) {
                sendJsonResponse(resp, pet);
            } else {
                sendErrorResponse(resp, "Pet não encontrado para o ID: " + id, HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException e) {
            sendErrorResponse(resp, "ID inválido", HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            sendErrorResponse(resp, e.getMessage(), HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        processPostPutDeleteRequest(req, resp, "adicionar", petService::addNewPet, HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostPutDeleteRequest(req, resp, "atualizar", petService::updatePet, HttpServletResponse.SC_OK);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPostPutDeleteRequest(req, resp, "excluir", petService::deletePet, HttpServletResponse.SC_OK);
    }

    private void processPostPutDeleteRequest(HttpServletRequest req, HttpServletResponse resp, String action, PetServlet.ActionFunction actionFunction, int successStatusCode) throws IOException {
        try {
            String json = petService.requestToString(req);
            PetDTO petDTO = new Gson().fromJson(json, PetDTO.class);
            actionFunction.apply(petDTO);
            resp.setStatus(successStatusCode);
        } catch (Exception e) {
            sendErrorResponse(resp, "Erro ao " + action + " pet: " + e.getMessage(), HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendJsonResponse(HttpServletResponse resp, Pet pet) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(pet));
        resp.setStatus(HttpServletResponse.SC_OK);
    }
    private void sendJsonResponse(HttpServletResponse resp, List<Pet> pets) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(pets));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    private void sendErrorResponse(HttpServletResponse resp, String message, int statusCode) throws IOException {
        resp.getWriter().write(message);
        resp.setStatus(statusCode);
    }


    @FunctionalInterface
    interface ActionFunction {
        void apply(PetDTO petDTO) throws Exception;
    }
}
