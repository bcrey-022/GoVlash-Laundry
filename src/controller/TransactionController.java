package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Service;
import model.Transaction;
import model.User;
import repository.TransactionRepo;

public class TransactionController {
	private TransactionRepo transRepo;
	private static List<Transaction> tempTransaction = new ArrayList<>();;
	private String message = "";
	
	public TransactionController() {
	    this.transRepo = new TransactionRepo();
	}

    public List<Transaction> getTempTransaction() {
        return tempTransaction;
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
		String tempId = "TEMP-" + UUID.randomUUID();
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
		Transaction t = new Transaction(tempId, service, customer, null, null, date, "Pending", weight, notes);
		tempTransaction.add(t);
		message = "Success";
		return t;
	}
	
	public boolean assignStaff(String tempId, User receptionist, User staff) {
		if (staff == null) {
            message = "Laundry staff must be selected.";
            return false;
        }
		Transaction temp = findTempById(tempId);
		if(temp == null) {
			System.out.println("Transaction not found. Please check again.");
			return false;
		}
		temp.setReceptionist(receptionist);
		temp.setLaundryStaff(staff);
		return saveTransaction(tempId);
	}
	
	public boolean saveTransaction(String tempId) {
		Transaction temp = findTempById(tempId);
		if (temp == null) {
            message = "Transaction not found.";
            return false;
        }
		String trId = transRepo.findLastID("TR");
        temp.setTransactionId(trId);
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
	
	public List<Transaction> viewLSTransaction(){
		return transRepo.viewLSTransaction();
	}
	
	private Transaction findTempById(String id) {
        for (Transaction t : tempTransaction) {
            if (t.getTransactionId().equals(id)) {
                return t;
            }
        }
        return null;
    }

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
