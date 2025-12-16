package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import databaseconnector.Connect;
import model.Service;
import model.Transaction;
import model.User;

public class TransactionRepo {
	private Connection con;

	public TransactionRepo() {
		con = Connect.getInstance().getConnection();
	}
	
	public boolean addNewTransaction(Transaction transaction) {
		String query = "INSERT INTO transaction(transactionId, serviceId, customerId, receptionistId, laundryStaffId, transactionDate, status, weight, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(query)){
			LocalDate date = LocalDate.parse(transaction.getDate());
			ps.setString(1, transaction.getTransactionId());
			ps.setInt(2, transaction.getService().getId());
			ps.setString(3, transaction.getCustomer().getId());
			ps.setString(4, transaction.getReceptionist().getId());
			ps.setString(5, transaction.getLaundryStaff().getId());
			ps.setDate(6, java.sql.Date.valueOf(date));
			ps.setString(7, transaction.getStatus());
			ps.setInt(8, transaction.getWeight());
			ps.setString(9, transaction.getNotes());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Transaction> viewPendingTransaction(){
		List<Transaction> tr = new ArrayList<>();
		String query = "SELECT t.transactionId, s.serviceId, s.serviceName, s.serviceDescription, s.servicePrice, s.serviceDuration, c.userId AS customerId, c.userName AS customerName, r.userId AS receptionistId, r.userName AS receptionistName, l.userId AS laundryStaffId, l.userName AS laundryStaffName, t.transactionDate, t.status, t.weight, t.notes FROM transaction t JOIN service s ON t.serviceId = s.serviceId LEFT JOIN users c ON t.customerId = c.userId LEFT JOIN users r ON t.receptionistId = r.userId LEFT JOIN users l ON t.laundryStaffId = l.userId WHERE status = 'Pending'";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Service service = new Service(rs.getInt("serviceId"), rs.getString("serviceName"), rs.getString("serviceDescription"), rs.getInt("servicePrice"), rs.getInt("serviceDuration"));
				User customer = null;
				if(rs.getString("customerId") != null) {
					customer = new User(rs.getString("customerId"), rs.getString("customerName"), null, null, null, null, null);
				}
				User receptionist = null;
				if (rs.getString("receptionistId") != null) {
				    receptionist = new User(rs.getString("receptionistId"), rs.getString("receptionistName"), null, null, null, null, null);
				}
				User staff = null;
				if (rs.getString("laundryStaffId") != null) {
				    staff = new User(
				        rs.getString("laundryStaffId"),
				        rs.getString("laundryStaffName"),
				        null, null, null, null, null
				    );
				}
				Transaction t = new Transaction(rs.getString("transactionId"), service, customer, receptionist, staff, rs.getString("transactionDate"), rs.getString("status"), rs.getInt("weight"), rs.getString("notes"));
				tr.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tr;
	}
	
	public List<Transaction> viewLSTransaction(){
		List<Transaction> tr = new ArrayList<>();
		String query = "SELECT t.transactionId, s.serviceId, s.serviceName, s.serviceDescription, s.servicePrice, s.serviceDuration, c.userId AS customerId, c.userName AS customerName, r.userId AS receptionistId, r.userName AS receptionistName, l.userId AS laundryStaffId, l.userName AS laundryStaffName, t.transactionDate, t.status, t.weight, t.notes FROM transaction t JOIN service s ON t.serviceId = s.serviceId LEFT JOIN users c ON t.customerId = c.userId LEFT JOIN users r ON t.receptionistId = r.userId LEFT JOIN users l ON t.laundryStaffId = l.userId WHERE status = 'Pending' OR status = 'Washing'";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Service service = new Service(rs.getInt("serviceId"), rs.getString("serviceName"), rs.getString("serviceDescription"), rs.getInt("servicePrice"), rs.getInt("serviceDuration"));
				User customer = null;
				if(rs.getString("customerId") != null) {
					customer = new User(rs.getString("customerId"), rs.getString("customerName"), null, null, null, null, null);
				}
				User receptionist = null;
				if (rs.getString("receptionistId") != null) {
				    receptionist = new User(rs.getString("receptionistId"), rs.getString("receptionistName"), null, null, null, null, null);
				}
				User staff = null;
				if (rs.getString("laundryStaffId") != null) {
				    staff = new User(
				        rs.getString("laundryStaffId"),
				        rs.getString("laundryStaffName"),
				        null, null, null, null, null
				    );
				}
				Transaction t = new Transaction(rs.getString("transactionId"), service, customer, receptionist, staff, rs.getString("transactionDate"), rs.getString("status"), rs.getInt("weight"), rs.getString("notes"));
				tr.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tr;
	}
	
	public List<Transaction> viewAllTransaction(){
		List<Transaction> tr = new ArrayList<>();
		String query = "SELECT t.transactionId, s.serviceId, s.serviceName, s.serviceDescription, s.servicePrice, s.serviceDuration, c.userId AS customerId, c.userName AS customerName, r.userId AS receptionistId, r.userName AS receptionistName, l.userId AS laundryStaffId, l.userName AS laundryStaffName, t.transactionDate, t.status, t.weight, t.notes FROM transaction t JOIN service s ON t.serviceId = s.serviceId LEFT JOIN users c ON t.customerId = c.userId LEFT JOIN users r ON t.receptionistId = r.userId LEFT JOIN users l ON t.laundryStaffId = l.userId";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Service service = new Service(rs.getInt("serviceId"), rs.getString("serviceName"), rs.getString("serviceDescription"), rs.getInt("servicePrice"), rs.getInt("serviceDuration"));
				User customer = null;
				if(rs.getString("customerId") != null) {
					customer = new User(rs.getString("customerId"), rs.getString("customerName"), null, null, null, null, null);
				}
				User receptionist = null;
				if (rs.getString("receptionistId") != null) {
				    receptionist = new User(rs.getString("receptionistId"), rs.getString("receptionistName"), null, null, null, null, null);
				}
				User staff = null;
				if (rs.getString("laundryStaffId") != null) {
				    staff = new User(
				        rs.getString("laundryStaffId"),
				        rs.getString("laundryStaffName"),
				        null, null, null, null, null
				    );
				}
				Transaction t = new Transaction(rs.getString("transactionId"), service, customer, receptionist, staff, rs.getString("transactionDate"), rs.getString("status"), rs.getInt("weight"), rs.getString("notes"));
				tr.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tr;
	}
	
	public List<Transaction> viewTransactionHistory(String custId){
		List<Transaction> tr = new ArrayList<>();
		String query = "SELECT t.transactionId, s.serviceId, s.serviceName, s.serviceDescription, s.servicePrice, s.serviceDuration, c.userId AS customerId, c.userName AS customerName, r.userId AS receptionistId, r.userName AS receptionistName, l.userId AS laundryStaffId, l.userName AS laundryStaffName, t.transactionDate, t.status, t.weight, t.notes FROM transaction t JOIN service s ON t.serviceId = s.serviceId LEFT JOIN users c ON t.customerId = c.userId LEFT JOIN users r ON t.receptionistId = r.userId LEFT JOIN users l ON t.laundryStaffId = l.userId WHERE customerId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, custId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Service service = new Service(rs.getInt("serviceId"), rs.getString("serviceName"), rs.getString("serviceDescription"), rs.getInt("servicePrice"), rs.getInt("serviceDuration"));
				User customer = null;
				if(rs.getString("customerId") != null) {
					customer = new User(rs.getString("customerId"), rs.getString("customerName"), null, null, null, null, null);
				}
				User receptionist = null;
				if (rs.getString("receptionistId") != null) {
				    receptionist = new User(rs.getString("receptionistId"), rs.getString("receptionistName"), null, null, null, null, null);
				}
				User staff = null;
				if (rs.getString("laundryStaffId") != null) {
				    staff = new User(
				        rs.getString("laundryStaffId"),
				        rs.getString("laundryStaffName"),
				        null, null, null, null, null
				    );
				}
				Transaction t = new Transaction(rs.getString("transactionId"), service, customer, receptionist, staff, rs.getString("transactionDate"), rs.getString("status"), rs.getInt("weight"), rs.getString("notes"));
				tr.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tr;
	}
	
	public boolean updateStatus(String transactionId, String status) {
		String query = "UPDATE transaction SET status = ? WHERE transactionId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, status);
			ps.setString(2, transactionId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String findLastID(String prefix) {
		String query = "SELECT transactionId FROM transaction ORDER BY transactionId DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(query);){
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				String lastId = rs.getString("transactionId");
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
}
