package controller;

import java.util.ArrayList;
import java.util.List;

import model.Service;
import repository.ServiceRepo;

public class ServiceController {
	ArrayList<Service> services = new ArrayList<>();
	private Service service;
	private ServiceRepo serviceRepo;
	private String message = "";
	
	public ServiceController() {
		this.serviceRepo = new ServiceRepo();
	}

	public boolean addNewService(String name, String description, int price, int duration) {
		if(name.isEmpty() || name.length() > 50 || name == null) {
			message = "Service Name cannot be empty and maximum character are 50 characters!";
			return false;
		}
		if(description.isEmpty() || description.length() > 250 || description == null) {
			message = "Service Description cannot be empty and maximum character are 250 characters!";
			return false;
		}
		if(price < 0) {
			message = "Price must be at least Rp 0!";
			return false;
		}
		if(duration < 0 && duration > 31) {
			message = "Duration must be between 1 and 30!";
			return false;
		}
		int id = generateServiceId();
		service = new Service(id, name, description, price, duration);
		serviceRepo.insertNewService(service);
		return true;
	}
	
	public List<Service> getAllServices(){
		return serviceRepo.getAllServices();
	}
	
	public List<Service> getAllServiceName(){
		return serviceRepo.getAllServiceName();
	}
	
	public boolean updateService(int id, String name, String description, int price, int duration) {
		return serviceRepo.updateService(id, name, description, price, duration);
	}
	
	public boolean deleteService(int id) {
		return serviceRepo.deleteService(id);
	}
	
	public int generateServiceId() {
		int lastId = serviceRepo.findLastID();
		lastId++;
		return lastId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
