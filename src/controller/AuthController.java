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
	
	public boolean loginAuth(String email, String password) {
		User user = userRepo.findEmail(email);
		if (user == null) {
			System.out.println("Email not registered!");
			return false;
		}
		if (!user.getPassword().equals(password)) {
			System.out.println("Password not match!");
			return false;
		}
	    return true;
	}
	
	public User register(String username, String password, String confirmPass, String gender, String role, String email, String dob) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate ld = LocalDate.parse(dob, fmt);
		LocalDate today = LocalDate.now();
		int age = Period.between(ld, today).getYears();
		if(username.isEmpty()) {
			System.out.println("Fill the username!");
		}
		if(password.isEmpty() || confirmPass.isEmpty()) {
			System.out.println("Fill the password!");
		}
		if(!password.equals(confirmPass)) {
			System.out.println("Password does not match!");
		}
		if(password.length() < 6) {
			System.out.println("Password length must be at least 6 character");
		}
		if(!gender.equals("Male") && !gender.equals("Female")) {
			System.out.println("Gender must be Male of Female");
		}
		if(role.equals("Customer")) {
			if(!email.endsWith("@email.com")) {
				System.out.println("Email must be end with @email.com!");
			}
			if(age >= 12) {
				System.out.println("User must be at least 12 years old!");
			}
		}
		else if(role.equals("Admin") || role.equals("Laundry Staff") || role.equals("Receptionist")) {
			if(!email.endsWith("@govlash.com")) {
				System.out.println("Please use email ended with @govlash.com");
			}
			if(age >= 17) {
				System.out.println("User must be at least 17 years old!");
			}
		}
		else {
			System.out.println("Role must be Customer, Admin, Laundry Staff, or Receptionist!");
		}
		int id = generateUserId();
		user = new User(id, username, email, password, gender, dob, role);
		userRepo.insertNewUser(user);
		return user;
	}
	
	public int generateUserId() {
		int lastId = userRepo.findLastID();
		lastId++;
		return lastId;
	}
}
