package model;

public class Transaction {
	private String transactionId;
	private Service service;
	private User customer;
	private User receptionist;
	private User laundryStaff;
	private String date;
	private String status;
	private int weight;
	private String notes;
	public Transaction(String transactionId, Service service, User customer, User receptionist, User laundryStaff,
			String date, String status, int weight, String notes) {
		super();
		this.transactionId = transactionId;
		this.service = service;
		this.customer = customer;
		this.receptionist = receptionist;
		this.laundryStaff = laundryStaff;
		this.date = date;
		this.status = status;
		this.weight = weight;
		this.notes = notes;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Service getService() {
		return service;
	}
	public void setService(Service service) {
		this.service = service;
	}
	public User getCustomer() {
		return customer;
	}
	public void setCustomer(User customer) {
		this.customer = customer;
	}
	public User getReceptionist() {
		return receptionist;
	}
	public void setReceptionist(User receptionist) {
		this.receptionist = receptionist;
	}
	public User getLaundryStaff() {
		return laundryStaff;
	}
	public void setLaundryStaff(User laundryStaff) {
		this.laundryStaff = laundryStaff;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
	