package controller;

import java.util.ArrayList;
import java.util.List;

import model.Service;
import repository.ServiceRepo;

public class ServiceController {
	ArrayList<Service> services = new ArrayList<>();
	private Service service;
	private ServiceRepo serviceRepo;
	
	public Service addNewService(String name, String description, int price, int duration) {
		if(name.isEmpty() || name.length() > 51) {
			System.out.println("Service Name cannot be empty and maximum character are 50 characters!");
		}
		if(description.isEmpty() || description.length() > 251) {
			System.out.println("Service Description cannot be empty and maximum character are 250 characters!");
		}
		if(price < 0) {
			System.out.println("Price must be at least Rp 0!");
		}
		if(duration < 0 && duration > 31) {
			System.out.println("Duration must be between 1 and 30!");
		}
		int id = generateServiceId();
		service = new Service(id, name, description, price, duration);
		serviceRepo.insertNewService(service);
		return service;
	}
	
	public List<Service> getAllServices(){
		return serviceRepo.getAllServices();
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
}
