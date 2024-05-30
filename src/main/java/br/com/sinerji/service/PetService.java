package br.com.sinerji.service;


import br.com.sinerji.model.address.AddressDTO;
import br.com.sinerji.model.pet.Pet;
import br.com.sinerji.model.pet.PetDTO;
import br.com.sinerji.model.pet.PetTypes;
import br.com.sinerji.model.user.User;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class PetService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("per_unit");
    @PersistenceContext
    private EntityManager entityManager = emf.createEntityManager();

    public Pet findPetById(Long id) {
        Pet pet = entityManager.find(Pet.class, id);
        if (pet == null){
            throw new EntityNotFoundException("Pet não encontrado para o ID: " + id);
        }
        return pet;
    }
    public List<Pet> findAllPets() {
        TypedQuery<Pet> query = entityManager.createQuery("SELECT p FROM Pet p", Pet.class);
        return query.getResultList();
    }

    @Transactional
    public void addNewPet(PetDTO petDTO) {
        if (petDTO == null) {
            throw new IllegalArgumentException("PetDTO cannot be null");
        }
        try {
            Long userId = petDTO.getOwnerId();
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null");
            }

            UserService userService = new UserService();
            try {
                User user = userService.findUserById(userId);
                Pet pet = new Pet(petDTO);

                pet.setOwner(user);

                persistOnDB(pet);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Failed to persist pet", e);
            }

        } catch (Exception e){
            throw new EntityNotFoundException(e.getMessage());
        }


    }


    public void updatePet(PetDTO petDTO){
        if (petDTO == null) {
            throw new IllegalArgumentException("PetDTO cannot be null");
        }
        try {
            Pet pet = findPetById(petDTO.getId());
            updatePetFromDTO(pet, petDTO);
            updateOnDB(pet);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }

    public void deletePet(PetDTO petDTO){
        if (petDTO == null) {
            throw new IllegalArgumentException("PetDTO cannot be null");
        }
        try {
           Pet pet = findPetById(petDTO.getId());
            deleteOnDB(pet);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }


    public String requestToString(HttpServletRequest req) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
        StringBuilder jsonBuilder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonBuilder.append(line);
        }

        return jsonBuilder.toString();
    }
    @Transactional
    private void persistOnDB(Pet pet){
        entityManager.getTransaction().begin();
        entityManager.persist(pet);
        entityManager.getTransaction().commit();
    }
    @Transactional
    private void updateOnDB(Pet pet){
        entityManager.getTransaction().begin();
        entityManager.merge(pet);
        entityManager.getTransaction().commit();
    }
    @Transactional
    private void deleteOnDB(Pet pet){
        entityManager.getTransaction().begin();
        entityManager.remove(pet);
        entityManager.getTransaction().commit();
    }

    private void updatePetFromDTO(Pet pet, PetDTO petDTO) {
        if (petDTO.getType() != null && !petDTO.getType().isEmpty()) {
            pet.setType(PetTypes.valueOf(petDTO.getType()));
        }
        if (petDTO.getName() != null && !petDTO.getName().isEmpty()) {
            pet.setName(petDTO.getName());
        }
        if (petDTO.getBirthDate() != null) {
            pet.setBirthDate(petDTO.getBirthDate());
        }
        if (petDTO.getAdditionalInformation() != null && !petDTO.getAdditionalInformation().isEmpty()) {
            pet.setAdditionalInformation(petDTO.getAdditionalInformation());
        }

    }


}
