package com.ivan.prueba.userManagement;

import com.ivan.prueba.userManagement.entities.User;
import com.ivan.prueba.userManagement.repositories.UserRepository;
import com.ivan.prueba.userManagement.services.UserServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceImpTest {

	@InjectMocks
	private UserServiceImp userService;

	@Mock
	private UserRepository repository;

	private AutoCloseable closeable;

	@BeforeEach
	void setUp() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	void findAll_DebeRetornarListaDeUsuarios() {
		List<User> users = new ArrayList<>();
		users.add(User.builder().id(1L).username("usuario1").password("password1").build());
		users.add(User.builder().id(2L).username("usuario2").password("password2").build());
		when(repository.findAll()).thenReturn(users);

		List<User> result = userService.findAll();

		assertNotNull(result);
		assertEquals(2, result.size());
		verify(repository).findAll();
	}

	@Test
	void findById_DebeRetornarUsuario_CuandoExisteId() {
		Long id = 1L;
		User user = User.builder().id(id).username("usuario1").password("password1").build();
		when(repository.findById(id)).thenReturn(Optional.of(user));

		Optional<User> result = userService.findById(id);

		assertTrue(result.isPresent());
		assertEquals(id, result.get().getId());
		verify(repository).findById(id);
	}

	@Test
	void save_DebeGuardarUsuario() {
		User user = User.builder().username("usuario1").password("password1").build();
		when(repository.save(any(User.class))).thenReturn(user);

		User result = userService.save(user);

		assertNotNull(result);
		assertEquals(user.getUsername(), result.getUsername());
		verify(repository).save(any(User.class));
	}

	@Test
	void update_DebeActualizarUsuario_CuandoExisteId() {
		Long id = 1L;
		User existingUser = User.builder()
				.id(id)
				.username("usuario1")
				.password("password1")
				.build();
		User newUser = User.builder()
				.username("usuarioActualizado")
				.password("passwordActualizado")
				.build();

		when(repository.findById(id)).thenReturn(Optional.of(existingUser));
		when(repository.save(any(User.class))).thenReturn(existingUser);

		Optional<User> result = userService.update(id, newUser);

		assertTrue(result.isPresent());
		assertEquals(newUser.getUsername(), result.get().getUsername());
		verify(repository).findById(id);
		verify(repository).save(any(User.class));
	}

	@Test
	void update_DebeRetornarVacio_CuandoNoExisteId() {
		Long id = 1L;
		User usuario = User.builder().username("usuario1").password("password1").build();
		when(repository.findById(id)).thenReturn(Optional.empty());

		Optional<User> result = userService.update(id, usuario);

		assertFalse(result.isPresent());
		verify(repository).findById(id);
		verify(repository, never()).save(any(User.class));
	}

	@Test
	void delete_DebeEliminarUsuario_CuandoExisteId() {
		Long id = 1L;
		User user = User.builder().id(id).username("usuario1").password("password1").build();
		when(repository.findById(id)).thenReturn(Optional.of(user));

		Optional<User> result = userService.delete(id);

		assertTrue(result.isPresent());
		assertEquals(id, result.get().getId());
		verify(repository).findById(id);
		verify(repository).delete(user);
	}

	@Test
	void delete_DebeRetornarVacio_CuandoNoExisteId() {
		Long id = 1L;
		when(repository.findById(id)).thenReturn(Optional.empty());

		Optional<User> result = userService.delete(id);

		assertFalse(result.isPresent());
		verify(repository).findById(id);
		verify(repository, never()).delete(any(User.class));
	}
}