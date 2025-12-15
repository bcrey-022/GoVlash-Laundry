package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import databaseconnector.Connect;
import model.Notification;

public class NotificationRepo {
	public Connection con;
	
	public NotificationRepo() {
		con = Connect.getInstance().getConnection();
	}
	
	public boolean addNewNotification(Notification notif) {
		String query = "INSERT INTO notification(notificationId, customerId, message, status, createdTime) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(query)){
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate ld = LocalDate.parse(notif.getCreatedTime(), fmt);
			ps.setInt(1, notif.getNotifId());
			ps.setString(2, notif.getCustomerId());
			ps.setString(3, notif.getMessage());
			ps.setBoolean(4, notif.isStatus());
			ps.setDate(5, java.sql.Date.valueOf(ld));
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public int findLastID() {
		String query = "SELECT notificationId FROM notification ORDER BY notificationId DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(query);){
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getInt("notificationId");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public boolean deleteNotification(int notifId) {
		String query = "DELETE FROM notification WHERE notificationId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, notifId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public String sendNotification(String custId){
		String query = "SELECT notificationId, message FROM notification WHERE customerId = ? ORDER BY notificationId DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, custId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				int notifId = rs.getInt("notificationId");
				String message = rs.getString("message");
				updateStatus(notifId);
				return message;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateStatus(int notifId) {
		String query = "UPDATE notification SET status = true WHERE notificationId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, notifId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Notification> getNotificationByUser(String userId){
		List<Notification> notifs = new ArrayList<>();
		String query = "SELECT notificationId, message FROM notification WHERE customerId = ?";
		try (PreparedStatement ps = con.prepareStatement(query);){
			ps.setString(1, userId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				notifs.add(new Notification(rs.getInt("notifId"), rs.getString("customerId"), rs.getString("message"), rs.getBoolean("status"), rs.getDate("createdTime").toString()));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return notifs;
	}
	
	public Notification getNotificationDetail(int notifId){
		List<Notification> notifs = new ArrayList<>();
		String query = "SELECT notificationId, message, status, createdTime FROM notification where notificationId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, notifId);
			ResultSet rs = ps.executeQuery();			
			while(rs.next()) {
				notifs.add(new Notification(rs.getInt("notifId"), rs.getString("customerId"), rs.getString("message"), rs.getBoolean("status"), rs.getDate("createdTime").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (Notification) notifs;
	}
	
	public List<Notification> getFinishedNotification(String custId){
		List<Notification> notifs = new ArrayList<>();
		String query = "SELECT notificationId, message, status, createdTime FROM notification where customerId = ? AND status = true";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setString(1, custId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				notifs.add(new Notification(rs.getInt("notifId"), rs.getString("customerId"), rs.getString("message"), rs.getBoolean("status"), rs.getDate("createdTime").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifs;
	}
}
