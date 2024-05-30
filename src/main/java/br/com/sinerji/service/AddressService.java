package br.com.sinerji.service;

import br.com.sinerji.model.address.Address;
import br.com.sinerji.model.address.AddressDTO;
import br.com.sinerji.model.pet.Pet;
import br.com.sinerji.model.user.User;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Stateless
public class AddressService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("per_unit");
    @PersistenceContext
    private EntityManager entityManager = emf.createEntityManager();

    public Address findAddressById(Long id) {
        Address address = entityManager.find(Address.class, id);
        if (address == null){
            throw new EntityNotFoundException("Endereço não encontrado para o ID: " + id);
        }
        return address;
    }
    public List<Address> findAllAddress() {
        TypedQuery<Address> query = entityManager.createQuery("SELECT a FROM Address a", Address.class);
        return query.getResultList();
    }

    @Transactional
    public void addNewAddress(AddressDTO addressDTO) {
        if (addressDTO == null) {
            throw new IllegalArgumentException("AddressDTO cannot be null");
        }
        try {

            Long userId = addressDTO.getUserId();
            if (userId == null) {
                throw new IllegalArgumentException("User ID cannot be null");
            }


            UserService userService = new UserService();
            try {
                User user = userService.findUserById(userId);
                Address address = new Address(addressDTO);

                address.setUser(user);
                System.out.println(address);

                persistOnDB(address);
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Failed to persist address", e);
            }

            } catch (Exception e){
                throw new EntityNotFoundException(e.getMessage());
            }


    }


    public void updateAddress(AddressDTO addressDTO){
        if (addressDTO == null) {
            throw new IllegalArgumentException("AddressDTO cannot be null");
        }
        try {
            Address address = findAddressById(addressDTO.getId());
            updateAddressFromDTO(address, addressDTO);
            updateOnDB(address);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());

        }
    }
    public void deleteAddress(AddressDTO addressDTO){
        if (addressDTO == null) {
            throw new IllegalArgumentException("AddressDTO cannot be null");
        }
        try {
            Address address= findAddressById(addressDTO.getId());
            deleteOnDB(address);
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

    private void persistOnDB(Address address){
        entityManager.getTransaction().begin();
        entityManager.persist(address);
        entityManager.getTransaction().commit();
    }
    private void updateOnDB(Address address){
        entityManager.getTransaction().begin();
        entityManager.merge(address);
        entityManager.getTransaction().commit();
    }
    private void deleteOnDB(Address address){
        entityManager.getTransaction().begin();
        entityManager.remove(address);
        entityManager.getTransaction().commit();
    }

    private void updateAddressFromDTO(Address address, AddressDTO addressDTO) {
        if (addressDTO.getStreet() != null && !addressDTO.getStreet().isEmpty()) {
            address.setStreet(addressDTO.getStreet());
        }
        if (addressDTO.getState() != null && !addressDTO.getState().isEmpty()) {
            address.setState(addressDTO.getState());
        }
        if (addressDTO.getCity() != null && !addressDTO.getCity().isEmpty()) {
            address.setCity(addressDTO.getCity());
        }
        if (addressDTO.getNumber() != null && !addressDTO.getNumber().isEmpty()) {
            address.setNumber(addressDTO.getNumber());
        }
        if (addressDTO.getAdditional() != null && !addressDTO.getAdditional().isEmpty()) {
            address.setAdditional(addressDTO.getAdditional());
        }
        if (addressDTO.getZipCode() != null && !addressDTO.getZipCode().isEmpty()) {
            address.setZipCode(addressDTO.getZipCode());
        }
        if (addressDTO.getCountry() != null && !addressDTO.getCountry().isEmpty()) {
            address.setCountry(addressDTO.getCountry());
        }
    }


}
