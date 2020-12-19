package com.JFSCapstoneBackend.Controller;

import javax.validation.constraints.Email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.JFSCapstoneBackend.Model.User;
import com.JFSCapstoneBackend.Repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserRepository uRepo;

	@PostMapping("/createUser")
	public int createUser(@RequestBody User inputUser) {
		boolean validationFlag;
		try {
			validationFlag = uRepo.existsByUserEmail(inputUser.getUserEmail());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Error while validating User Email");
		}
		try {
			if (validationFlag) {
				User user = uRepo.findByUserEmail(inputUser.getUserEmail());
				user.setUserName(inputUser.getUserName());
				user.setUserPassword(inputUser.getUserPassword());
				uRepo.save(user);
				return user.getUserId();

			} else {
				uRepo.save(inputUser);
				return inputUser.getUserId();
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Error while Creating the User");
		}
	}

	@PostMapping("/createAdminUser")
	public void createAdminUser(User inputUser) {
		boolean validationFlag;
		try {
			validationFlag = uRepo.existsByUserEmail(inputUser.getUserEmail());
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Error while validating User Email");
		}
		try {
			if (validationFlag) {
				User user = uRepo.findByUserEmail(inputUser.getUserEmail());
				user.setUserName(inputUser.getUserName());
				if (StringUtils.isNotBlank(inputUser.getUserPassword()))
					user.setUserPassword(inputUser.getUserPassword());
				else
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin User Password cannot be Blank");
				user.setIsAdmin(true);
				uRepo.save(user);

			} else {
				uRepo.save(inputUser);
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Error while Creating the User");
		}
	}

	@GetMapping("/login")
	public boolean logIn(@RequestParam(value = "userEmail") @Email String userEmail,
			@RequestParam(value = "userPassword") String userPassword) {
		User user;
		try {
			user = uRepo.findByUserEmail(userEmail);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Internal Server Error while validating the login");
		}
		if (null != user && user.getUserPassword().equals(userPassword) && user.isAdmin())
			return true;
		else
			return false;
	}

	@GetMapping(value = "/userDetails")
	public User getUserDetails(@RequestParam(value = "userId") String userId) {
		int userIdInt = Integer.parseInt(userId);
		User u=uRepo.findById(userIdInt).get();
		u.setUserPassword(null);
		u.setIsAdmin(false);
		return u;
	}

}
