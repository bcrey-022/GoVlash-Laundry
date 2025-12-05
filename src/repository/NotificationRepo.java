package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public void addNewNotification(Notification notif) {
		String query = "INSERT INTO notification(notificationId, customerId, message, status, createdTime) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement ps = con.prepareStatement(query)){
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate ld = LocalDate.parse(notif.getCreatedTime(), fmt);
			ps.setInt(1, notif.getNotifId());
			ps.setInt(2, notif.getCustomerId());
			ps.setString(3, notif.getMessage());
			ps.setBoolean(4, notif.isStatus());
			ps.setDate(5, java.sql.Date.valueOf(ld));
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
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
	
	public String sendNotification(int custId){
		String query = "SELECT message FROM notification WHERE customerId = ? ORDER BY notificationId DESC LIMIT 1";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			ps.setInt(1, custId);
			if(rs.next()) {
				return rs.getString("message");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean updateStatus(int notifId) {
		String query = "UPDATE service SET status = true WHERE notificationId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ps.setInt(1, notifId);
			int row = ps.executeUpdate();
			return row > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Notification> getNotificationDetail(int notifId){
		List<Notification> notifs = new ArrayList<>();
		String query = "SELECT notificationId, message, status, createdTime FROM notification where notificationId = ?";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			ps.setInt(1, notifId);
			while(rs.next()) {
				notifs.add(new Notification(rs.getInt("notifId"), rs.getInt("customerId"), rs.getString("message"), rs.getBoolean("status"), rs.getDate("createdTime").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifs;
	}
	
	public List<Notification> getFinishedNotification(int custId){
		List<Notification> notifs = new ArrayList<>();
		String query = "SELECT notificationId, message, status, createdTime FROM notification where customerId = ? AND status = true";
		try (PreparedStatement ps = con.prepareStatement(query)){
			ResultSet rs = ps.executeQuery();
			ps.setInt(1, custId);
			while(rs.next()) {
				notifs.add(new Notification(rs.getInt("notifId"), rs.getInt("customerId"), rs.getString("message"), rs.getBoolean("status"), rs.getDate("createdTime").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return notifs;
	}
}
