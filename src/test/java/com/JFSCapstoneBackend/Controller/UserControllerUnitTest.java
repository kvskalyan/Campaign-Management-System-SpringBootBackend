package com.JFSCapstoneBackend.Controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import com.JFSCapstoneBackend.testUtil;
import com.JFSCapstoneBackend.Model.User;
import com.JFSCapstoneBackend.Repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class UserControllerUnitTest extends testUtil{
	
	@Mock
	UserRepository uRepo;
	
	@InjectMocks
	UserController userController;
	
	User user;
	
	@Before
	public void init() throws JsonParseException, JsonMappingException, IOException {
		MockitoAnnotations.initMocks(this);
		user = (User) convertJsonToObject("testUser.json", User.class);
	}
	
	@Test
	public void test_createUser() {
		when(uRepo.existsByUserEmail(any())).thenReturn(false);
		assertNotNull(userController.createUser(user));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createUser1() {
		when(uRepo.existsByUserEmail(any())).thenThrow(new NoSuchElementException());
		userController.createUser(user);
	}
	
	@Test
	public void test_createUser2() {
		when(uRepo.existsByUserEmail(any())).thenReturn(true);
		when(uRepo.findByUserEmail(any())).thenReturn(user);
		assertNotNull(userController.createUser(user));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createUser3() {
		when(uRepo.existsByUserEmail(any())).thenReturn(true);
		when(uRepo.findByUserEmail(any())).thenThrow(new NoSuchElementException());
		assertNotNull(userController.createUser(user));
	}
	
	@Test
	public void test_createAdminUser() {
		when(uRepo.existsByUserEmail(any())).thenReturn(false);
		userController.createAdminUser(user);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createAdminUser1() {
		when(uRepo.existsByUserEmail(any())).thenThrow(new NoSuchElementException());
		userController.createAdminUser(user);
	}
	
	@Test
	public void test_createAdminUser2() {
		when(uRepo.existsByUserEmail(any())).thenReturn(true);
		when(uRepo.findByUserEmail(any())).thenReturn(user);
		userController.createAdminUser(user);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createAdminUser3() {
		when(uRepo.existsByUserEmail(any())).thenReturn(true);
		user.setUserPassword(null);
		when(uRepo.findByUserEmail(any())).thenReturn(user);
		userController.createAdminUser(user);
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_createAdminUser4() {
		when(uRepo.existsByUserEmail(any())).thenReturn(true);
		when(uRepo.findByUserEmail(any())).thenThrow(new NoSuchElementException());
		userController.createAdminUser(user);
	}
	
	@Test
	public void test_login() {
		when(uRepo.findByUserEmail(any())).thenReturn(user);
		assertTrue(userController.logIn("venkatsaikalyan@gmail.com", "12345Test"));
	}
	
	@Test
	public void test_login1() {
		when(uRepo.findByUserEmail(any())).thenReturn(user);
		assertFalse(userController.logIn("venkatsaikalyan@gmail.com", "12345Test12345"));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void test_login2() {
		when(uRepo.findByUserEmail(any())).thenThrow(new NoSuchElementException());
		userController.logIn("venkatsaikalyan@gmail.com", "12345Test12345");
	}
	
	@Test
	public void test_getUserDetails() {
		when(uRepo.findById(any())).thenReturn(Optional.of(user));
		assertNotNull(userController.getUserDetails("1"));
	}

}
