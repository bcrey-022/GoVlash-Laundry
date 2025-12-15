package controller;

import java.time.LocalDate;
import java.util.List;

import model.Notification;
import repository.NotificationRepo;

public class NotificationController {
	private Notification notif;
	private NotificationRepo notifRepo;
	
	public NotificationController() {
		this.notifRepo = new NotificationRepo();
	}

	public Notification addNewNotification(String custId, String transactionID) {
		if(custId.contains("EMP") || custId.contains("ADM") || custId.isEmpty() || custId == null) {
			System.out.println("Refer the right user!");
		}
		String message = "Your order with ID: " + transactionID + " is finished and ready for pickup. Thank you for choosing our service!";
		boolean status = false;
		String createdTime = LocalDate.now().toString();
		int notifId = generateNotificationId();
		notif = new Notification(notifId, custId, message, status, createdTime);
		notifRepo.addNewNotification(notif);
		return notif;
	}
	
	public String sendNotification(String custId) {
		return notifRepo.sendNotification(custId);
	}
	
	public List<Notification> getNotificationByUser(String userId){
		return notifRepo.getNotificationByUser(userId);
	}
	
	public Notification getNotificationDetail(int notifId){
		return notifRepo.getNotificationDetail(notifId);
	}
	
	public boolean deleteNotification(int notifId) {
		return notifRepo.deleteNotification(notifId);
	}
	
	public List<Notification> getFinishedNotification(String custId){
		return notifRepo.getFinishedNotification(custId);
	}
	
	public boolean updateStatus(int notifId) {
		return notifRepo.updateStatus(notifId);
	}
	
	public int generateNotificationId() {
		int lastId = notifRepo.findLastID();
		lastId++;
		return lastId;
	}
}
