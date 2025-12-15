package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import databaseconnector.Connect;
import model.Service;

public class ServiceRepo {
	private Connection con;
	public ServiceRepo() {
		con = Connect.getInstance().getConnection();
	}
	
	public boolean insertNewService(Service service) {
		String query = "INSERT INTO service(serviceId, serviceName, serviceDescription, servicePrice, serviceDuration) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, service.getId());
			ps.setString(2, service.getName());
			ps.setString(3, service.getDescription());
			ps.setInt(4, service.getPrice());
			ps.setInt(5, service.getDuration());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Service> getAllServices(){
		List<Service> services = new ArrayList<>();
		String query = "SELECT * FROM service";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				services.add(new Service(rs.getInt("serviceId"), rs.getString("serviceName"), rs.getString("serviceDescription"), rs.getInt("servicePrice"), rs.getInt("serviceDuration")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return services;
	}
	
	public List<Service> getAllServiceName(){
		List<Service> services = new ArrayList<>();
		String query = "SELECT serviceId, serviceName FROM service";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				services.add(new Service(rs.getInt("serviceId"), rs.getString("serviceName")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return services;
	}
	
	public boolean deleteService(int id) {
		String query = "DELETE FROM service WHERE serviceId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, id);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updateService(int id, String name, String description, int price, int duration) {
		String query = "UPDATE service SET serviceName = ?, serviceDescription = ?, servicePrice = ?, serviceDuration = ? WHERE serviceId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, name);
			ps.setString(2, description);
			ps.setInt(3, price);
			ps.setInt(4, duration);
			ps.setInt(5, id);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int findLastID() {
		String query = "SELECT serviceId FROM service ORDER BY serviceId DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(query);){
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("serviceId");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}
