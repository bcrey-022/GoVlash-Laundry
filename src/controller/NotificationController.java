package controller;

import java.util.List;

import model.Notification;
import repository.NotificationRepo;

public class NotificationController {
	private Notification notif;
	private NotificationRepo notifRepo;
	
	public List<Notification> getFinishedNotification(int custId){
		return notifRepo.getFinishedNotification(custId);
	}
	
	
}
