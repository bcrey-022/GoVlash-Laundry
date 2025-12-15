package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Service;
import model.Transaction;
import model.User;
import repository.TransactionRepo;

public class TransactionController {
	private TransactionRepo transRepo;
	private List<Transaction> tempTransaction;
	private String message = "";
	
	public TransactionController() {
	    this.transRepo = new TransactionRepo();
	}
	
	public Transaction createTempTransaction(Service service, User customer, int weight, String notes) {
		if (transRepo == null) {
	        transRepo = new TransactionRepo();
	    }

	    if (tempTransaction == null) {
	        tempTransaction = new ArrayList<>();
	    }
	    if (service == null) {
	        message = "Service must be selected.";
	        return null;
	    }
		String transactionId = transRepo.findLastID("TR");
		if(weight < 2 || weight > 50) {
			message = "Weight must be between 2 and 50.";
			return null;
		}
		if(notes == null) {
			notes = "";
		}
		if(notes.length() > 250) {
			message = "Notes total character can't more than 250 characters.";
			return null;
		}
		String date = LocalDate.now().toString();
		Transaction t = new Transaction(transactionId, service, customer, null, null, date, "Pending", weight, notes);
		tempTransaction.add(t);
		message = "Success";
		return t;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean assignStaffAndSave(String transactionId, User receptionist, User staff) {
		Transaction temp = null;
		for (Transaction t : tempTransaction) {
			if(t.getTransactionId().equals(transactionId)) {
				temp = t;
				break;
			}
		}
		if(temp == null) {
			System.out.println("Transaction not found. Please check again.");
			return false;
		}
		temp.setReceptionist(receptionist);
		temp.setLaundryStaff(staff);
		boolean insert = transRepo.addNewTransaction(temp);
		if(insert) {
			tempTransaction.remove(temp);
		}
		return insert;		
	}
	
	public List<Transaction> viewPendingTransaction(){
		return transRepo.viewPendingTransaction();
	}
	
	public List<Transaction> viewAllTransaction(){
		return transRepo.viewAllTransaction();
	}
	
	public List<Transaction> viewTransactionHistory(String custId){
		return transRepo.viewTransactionHistory(custId);
	}
	
	public boolean updateStatus(String transactionId, String status) {
		return transRepo.updateStatus(transactionId, status);
	}
	
	public List<Transaction> viewAssignedOrder(String laundryStaffId){
		return transRepo.viewAssignedOrder(laundryStaffId);
	}
	
	public List<Transaction> viewLSTransaction(){
		return transRepo.viewLSTransaction();
	}
	
}
