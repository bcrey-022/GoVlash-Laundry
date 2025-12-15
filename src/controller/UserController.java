package controller;

import java.util.List;

import model.User;
import repository.UserRepo;

public class UserController {
	private UserRepo userRepo;
	public UserController() {
		this.userRepo = new UserRepo();
	}
	public List<User> getAllEmployee(){
		return userRepo.getAllEmployee();
	}
	public List<User> getLaundryStaff(){
		return userRepo.getLaundryStaff();
	}
}
