package controller;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.User;
import repository.UserRepo;

public class AuthController {
	ArrayList<User> users = new ArrayList<>();
	private User user;
	private UserRepo userRepo;
	public String message = "";
	
	public AuthController() {
		userRepo = new UserRepo();
		users = (ArrayList<User>) userRepo.getAllUsers();
	}

	public User loginAuth(String email, String password) {
		User user = userRepo.findEmail(email);
		if (user == null) {
			message = "Email not registered!";
			return null;
		}
		if (!user.getPassword().equals(password)) {
			message = "Password not match!";
			return null;
		}
		message = "Success";
	    return user;
	}
	
	public boolean registerUser(String username, String password, String confirmPass, String role, String gender, String email, String dob) {
		if(dob == null || dob.isEmpty()) {
			message = "DOB can't be null.";
			return false;
		}
		LocalDate ld = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate today = LocalDate.now();
		int age = Period.between(ld, today).getYears();
		String id = null;
		if(username == null || username.isEmpty()) {
			message = "Fill the username!";
			return false;
		}
		if(role == null) {
			message = "Role can't be null!";
			return false;
		}
		if(role.equals("Customer")) {
			if(!email.endsWith("@email.com")) {
				message = "Email must be end with @email.com!";
				return false;
			}
			if(age < 12) {
				message = "User must be at least 12 years old!";
				return false;
			}
			id = userRepo.findLastID("CUS");
		}
		else if(role.equals("Admin")) {
			if(!email.endsWith("@govlash.com")) {
				message = "Please use email ended with @govlash.com!";
				return false;
			}
			if(age < 17) {
				message = "User must be at least 17 years old!";
				return false;
			}
			id = userRepo.findLastID("ADM");
		}
		if(password == null || confirmPass == null || password.isEmpty() || confirmPass.isEmpty()) {
			message = "Fill the password!";
			return false;
		}
		if(!password.equals(confirmPass)) {
			message = "Password does not match!";
			return false;
		}
		if(password.length() < 6) {
			message = "Password length must be at least 6 character";
			return false;
		}
		if(gender == null) {
			message = "Choose Gender";
			return false;
		}
		if(!gender.equals("Male") && !gender.equals("Female")) {
			message = "Gender must be Male of Female";
			return false;
		}
		user = new User(id, username, email, password, gender, dob, role);
		userRepo.insertNewUser(user);
		message = "Success";
		return true;
	}
	
	public boolean registerEmployee(String username, String password, String confirmPass, String gender, String role, String email, String dob) {
		if(dob == null || dob.isEmpty()) {
			message = "DOB can't be null.";
			return false;
		}
		LocalDate ld = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		LocalDate today = LocalDate.now();
		int age = Period.between(ld, today).getYears();
		String id = null;
		if(username == null || username.isEmpty()) {
			message = "Fill the username!";
			return false;
		}
		if(password == null || confirmPass == null || password.isEmpty() || confirmPass.isEmpty()) {
			message = "Fill the password!";
			return false;
		}
		if(!password.equals(confirmPass)) {
			message = "Password does not match!";
			return false;
		}
		if(password.length() < 6) {
			message = "Password length must be at least 6 character";
			return false;
		}
		if(gender == null) {
			message = "Choose Gender";
			return false;
		}
		if(!gender.equals("Male") && !gender.equals("Female")) {
			message = "Gender must be Male of Female";
			return false;
		}
		if(role == null || role.isEmpty()) {
			message = "Role can't be null!";
			return false;
		}
		if(role.equals("Laundry Staff") || role.equals("Receptionist")) {
			if(!email.endsWith("@govlash.com")) {
				message = "Please use email ended with @govlash.com!";
				return false;
			}
			if(age < 17) {
				message = "User must be at least 17 years old!";
				return false;
			}
			id = userRepo.findLastID("EMP");
		}
		
		user = new User(id, username, email, password, gender, dob, role);
		userRepo.insertNewUser(user);
		message = "Success";
		return true;
	}
	
	public String getMessage() {
		return message;
	}
}
