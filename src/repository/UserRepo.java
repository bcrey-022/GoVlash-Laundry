package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import databaseconnector.Connect;
import model.User;

public class UserRepo {
	private Connection con;

	public UserRepo() {
		con = Connect.getInstance().getConnection();
	}
	
	public boolean insertNewUser(User user) {
		String query = "INSERT INTO users(userId, userName, userEmail, userPassword, userGender, userDOB, userRole) VALUES (?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(query)){
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate ld = LocalDate.parse(user.getDob(), fmt);
			ps.setString(1, user.getId());
			ps.setString(2, user.getName());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getGender());
			ps.setDate(6, java.sql.Date.valueOf(ld));
			ps.setString(7, user.getRole());
			return ps.executeUpdate() > 0;			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String findLastID(String prefix) {
		String query = "SELECT userId FROM users WHERE userId LIKE ? ORDER BY userId DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(query);){
			ps.setString(1, prefix + "%");
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String lastId = rs.getString("userId");
				int number = Integer.parseInt(lastId.substring(prefix.length()));
				number++;
				return prefix + String.format("%03d", number);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prefix + "001";
	}
	
	public List<User> getAllEmployee(){
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users WHERE users.userRole != 'Customer'";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getString("userId"), rs.getString("userName"), rs.getString("userEmail"), rs.getString("userPassword"), rs.getString("userGender"), rs.getDate("userDOB").toString(), rs.getString("userRole")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public User findEmail(String email) {
		String query = "SELECT * FROM users WHERE userEmail = ?";
		User user = null;
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User(rs.getString("userId"), rs.getString("userName"), rs.getString("userEmail"), rs.getString("userPassword"), rs.getString("userGender"), rs.getDate("userDOB").toString(), rs.getString("userRole"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		String query = "SELECT * FROM users";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getString("userId"), rs.getString("userName"), rs.getString("userEmail"), rs.getString("userPassword"), rs.getString("userGender"), rs.getDate("userDOB").toString(), rs.getString("userRole")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public List<User> getLaundryStaff(){
		List<User> users = new ArrayList<>();
		String query = "SELECT userId, userName FROM users WHERE users.userRole = 'Laundry Staff'";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				users.add(new User(rs.getString("userId"), rs.getString("userName")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
}
