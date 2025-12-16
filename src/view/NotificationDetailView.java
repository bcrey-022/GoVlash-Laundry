package view;


import controller.NotificationController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Notification;

public class NotificationDetailView {
	private StackPane sp = new StackPane();
	public static StackPane create(int id) {
		VBox box = new VBox(15);
		box.setPadding(new Insets(20));
        box.setAlignment(Pos.TOP_LEFT);
		NotificationController nc = new NotificationController();
		Notification notif = nc.getNotificationDetail(id);
		box.getChildren().add(new Label("ID: " + notif.getNotifId()));
		box.getChildren().add(new Label("Message: " + notif.getMessage()));
		box.getChildren().add(new Label("Status: " + (notif.isStatus() ? "Read" : "Unread")));
		box.getChildren().add(new Label("Created Time: " + notif.getCreatedTime()));
		StackPane sp = new StackPane(box);
		sp.setPadding(new Insets(20));
		Button backBtn = new Button("Back");
		backBtn.setOnAction(e ->{
            nc.updateStatus(notif.getNotifId());
            notif.setStatus(true);
            StackPane root = CustomerNotificationView.create(CustomerNotificationView.getCurrent());
            box.getScene().setRoot(root);
		});
		return sp;
	}


}
