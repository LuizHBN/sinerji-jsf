package br.com.sinerji.service;

import br.com.sinerji.model.address.Address;
import br.com.sinerji.model.address.AddressDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class AddressServiceTest {
    private AddressService addressService;
    private Long newId;
    private AddressDTO addressDTO = new AddressDTO(1L,5L,
            "Av. Paulista"
            ,"São Paulo"
            ,"São Paulo"
            , "1987"
            ,null
            ,"001020-21"
            ,"Brasil");

    @BeforeEach
    public void setUp() {
        addressService = new AddressService();

    }

    @AfterEach
    public void tearDown() {
    }
    @Test
    public void testAddNewAddress() {
        addressDTO.setId(null);
        addressService.addNewAddress(addressDTO);
        addressDTO.setId(1L);

    }

    @Test
    public void testFindAddressById() {
        Long addressId = 4L;

        Address address = addressService.findAddressById(addressId);

        // Verificar se o resultado é o esperado
        assertNotNull(address);
        assertEquals(addressId, address.getId());
    }

    @Test
    public void testFindAddressById_WithNonexistentId() {
        // Cenário de teste
        Long nonexistentId = -1L;

        // Executar o método a ser testado e verificar se uma EntityNotFoundException é lançada
        assertThrows(EntityNotFoundException.class, () -> addressService.findAddressById(nonexistentId));
    }



    @Test
    public void testAddNewAddress_WithNullAddressDTO() {
        assertThrows(IllegalArgumentException.class, () -> addressService.addNewAddress(null));
    }

    @Test
    public void testUpdateAddress() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(4L);

        addressService.updateAddress(addressDTO);

    }

    @Test
    public void testUpdateAddress_WithInvalidData() {
        assertThrows(RuntimeException.class, () -> addressService.updateAddress(null));
    }

    @Test
    public void testDeleteAddress_WithInvalidData() {
        // Executar o método a ser testado com dados inválidos que causariam uma exceção e verificar se uma RuntimeException é lançada
        assertThrows(RuntimeException.class, () -> addressService.deleteAddress(null));
    }





}