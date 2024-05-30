package br.com.sinerji.service;

import br.com.sinerji.model.pet.Pet;
import br.com.sinerji.model.user.User;
import br.com.sinerji.model.user.UserDTO;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Stateless
public class UserService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("per_unit");
    @PersistenceContext
    private EntityManager entityManager = emf.createEntityManager();

    public User findUserById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user == null){
            throw new EntityNotFoundException("Usuário não encontrado para o ID: " + id);
        }
        return user;
    }
    public List<User> findAllUsers() {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        return query.getResultList();
    }

    @Transactional
    public void addNewUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        try {
            User user = new User(userDTO);
            persistOnDB(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to persist user", e);

        }
    }

    public void updateUser(UserDTO userDTO){
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        try {
            User user = findUserById(userDTO.getId());
            updateUserFromDTO(user, userDTO);
            updateOnDB(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }
    public void deleteUser(UserDTO userDTO){
        if (userDTO == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        try {
            User user = findUserById(userDTO.getId());
            deleteOnDB(user);
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

    private void persistOnDB(User user){
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }
    private void updateOnDB(User user){
        entityManager.getTransaction().begin();
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }
    private void deleteOnDB(User user){
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        entityManager.getTransaction().commit();
    }

    private void updateUserFromDTO(User user, UserDTO userDTO) {
        if (userDTO.getName() != null && !userDTO.getName().isEmpty()) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getAge() > 0) {
            user.setAge(userDTO.getAge());
        }
        if (userDTO.getGender() != null && userDTO.getGender().getDeclaringClass().isEnum()) {
            user.setGender(userDTO.getGender());
        }
        if (userDTO.getPhone() != null && !userDTO.getPhone().isEmpty()) {
            user.setPhone(userDTO.getPhone());
        }
    }

}
