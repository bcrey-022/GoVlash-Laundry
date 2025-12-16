package model;

public class Notification {
	private int notifId;
	private String customerId;
	private String message;
	private boolean status;
	private String createdTime;
	public Notification(int notifId, String customerId, String message, boolean status, String createdTime) {
		super();
		this.notifId = notifId;
		this.customerId = customerId;
		this.message = message;
		this.status = status;
		this.createdTime = createdTime;
	}
	public Notification(int notifId, String message) {
		super();
		this.notifId = notifId;
		this.message = message;
	}
	public int getNotifId() {
		return notifId;
	}
	public void setNotifId(int notifId) {
		this.notifId = notifId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
}
