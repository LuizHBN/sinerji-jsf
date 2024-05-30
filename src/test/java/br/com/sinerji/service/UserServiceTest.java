package br.com.sinerji.service;

import br.com.sinerji.model.user.Genders;
import br.com.sinerji.model.user.User;
import br.com.sinerji.model.user.UserDTO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;
    private Long newId;
    private UserDTO userDTO = new UserDTO(1L, "John Doe", 25,"123456789", Genders.MALE);

    @BeforeEach
    public void setUp() {
        userService = new UserService();
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testAddNewUser() {
        userDTO.setId(null);
        userService.addNewUser(userDTO);
        userDTO.setId(1L);
    }

    @Test
    public void testFindUserById() {
        Long userId = 4L;

        User user = userService.findUserById(userId);

        assertNotNull(user);
        assertEquals(userId, user.getId());
    }

    @Test
    public void testFindUserById_WithNonexistentId() {

        Long nonexistentId = -1L;


        assertThrows(EntityNotFoundException.class, () -> userService.findUserById(nonexistentId));
    }

    @Test
    public void testAddNewUser_WithNullUserDTO() {
        assertThrows(IllegalArgumentException.class, () -> userService.addNewUser(null));
    }

    @Test
    public void testUpdateUser() {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(4L);

        userService.updateUser(userDTO);
    }

    @Test
    public void testUpdateUser_WithInvalidData() {
        assertThrows(RuntimeException.class, () -> userService.updateUser(null));
    }

    @Test
    public void testDeleteUser_WithInvalidData() {
        // Executar o método a ser testado com dados inválidos que causariam uma exceção e verificar se uma RuntimeException é lançada
        assertThrows(RuntimeException.class, () -> userService.deleteUser(null));
    }

}
