package br.com.sinerji.service;

import br.com.sinerji.model.pet.Pet;
import br.com.sinerji.model.pet.PetDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceTest {
    private PetService petService;
    private PetDTO petDTO;

    @BeforeEach
    void setUp() {
        petService = new PetService();
        petDTO = new PetDTO();
        petDTO.setId(1L);
        petDTO.setOwnerId(4L);
        petDTO.setName("Buddy");
        petDTO.setType("DOG");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindPetById() {
        Long petId = 1L;

        Pet pet = petService.findPetById(petId);

        assertNotNull(pet);
        assertEquals(petId, pet.getId());
    }

    @Test
    void testFindPetById_WithNonexistentId() {
        Long nonexistentId = -1L;

        assertThrows(EntityNotFoundException.class, () -> petService.findPetById(nonexistentId));
    }

    @Test
    void testAddNewPet() {
        petDTO.setId(null);
        petService.addNewPet(petDTO);
        petDTO.setId(1L);
    }

    @Test
    void testAddNewPet_WithNullPetDTO() {
        assertThrows(IllegalArgumentException.class, () -> petService.addNewPet(null));
    }

    @Test
    void testUpdatePet() {
        petService.updatePet(petDTO);
        // Assert whatever update means for your system
    }

    @Test
    void testUpdatePet_WithInvalidData() {
        assertThrows(RuntimeException.class, () -> petService.updatePet(null));
    }

    @Test
    void testDeletePet_WithInvalidData() {
        assertThrows(RuntimeException.class, () -> petService.deletePet(null));
    }
}
