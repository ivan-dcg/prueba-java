package com.ivan.prueba.userManagement.controllers;

import com.ivan.prueba.userManagement.entities.User;
import com.ivan.prueba.userManagement.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testList() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        when(userService.findAll()).thenReturn(users);

        List<User> result = userController.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userService).findAll();
    }

    @Test
    void testView() {
        User user = new User();
        //user.setId(1L);
        Optional<User> userOptional = Optional.of(user);

        when(userService.findById(1L)).thenReturn(userOptional);

        ResponseEntity<?> response = userController.view(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).findById(1L);
    }

    @Test
    void testView_UserNotFound() {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.view(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testCreate_Valid() {
        User user = new User();
        when(userService.save(user)).thenReturn(user);

        when(bindingResult.hasFieldErrors()).thenReturn(false);

        ResponseEntity<?> response = userController.create(user, bindingResult);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).save(user);
    }

    @Test
    void testCreate_Invalid() {
        when(bindingResult.hasFieldErrors()).thenReturn(true);

        User user = new User();
        ResponseEntity<?> response = userController.create(user, bindingResult);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testUpdate_Valid() {
        User user = new User();
        //user.setId(1L);
        Optional<User> updatedUser = Optional.of(user);

        when(bindingResult.hasFieldErrors()).thenReturn(false);
        when(userService.update(eq(1L), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<?> response = userController.update(user, bindingResult, 1L);

        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).update(1L, user);
    }

    @Test
    void testUpdate_Invalid() {
        when(bindingResult.hasFieldErrors()).thenReturn(true);

        User user = new User();
        ResponseEntity<?> response = userController.update(user, bindingResult, 1L);

        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testDelete() {
        User user = new User();
        //user.setId(1L);
        Optional<User> userOptional = Optional.of(user);

        when(userService.delete(1L)).thenReturn(userOptional);

        ResponseEntity<?> response = userController.delete(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(user, response.getBody());
        verify(userService).delete(1L);
    }

    @Test
    void testDelete_UserNotFound() {
        when(userService.delete(1L)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.delete(1L);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}