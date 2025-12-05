package controller;

import java.util.List;

import model.User;
import repository.UserRepo;

public class UserController {
	private UserRepo userRepo;
	public List<User> getAllEmployee(){
		return userRepo.getAllEmployee();
	}
}
