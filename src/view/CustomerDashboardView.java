package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.User;

public class CustomerDashboardView extends Application{
	private User currentUser;
	private BorderPane layout;
	private StackPane content;

	public CustomerDashboardView(User user) {
		this.currentUser = user;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		layout = new BorderPane();
		content = new StackPane();
		layout.setCenter(content);
		layout.setTop(buildNavbar());
		loadHomePage();
		Scene scene = new Scene(layout, 1000, 650);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Customer Dashboard");
		primaryStage.show();
	}
	
	public HBox buildNavbar() {
		HBox navbar = new HBox();
		navbar.setPadding(new Insets(12, 20, 12, 20));
		navbar.setSpacing(20);
		Label logo = new Label("GoVlash Laundry");
		logo.setStyle("-fx-text-fill: #F0F8FF; -fx-font-size: 20px; -fx-font-weight: bold;");
		HBox spacer = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
		HBox rightBtn = new HBox();
		Button btnHome = new Button("Home");
		Button btnOrder = new Button("Order");
		Button btnNotif = new Button("Notification");
		Button btnHistory = new Button("History");
		Button btnProfile = new Button("Profile");
		Button btnLogout = new Button("Logout");
		btnHome.setOnAction(e -> loadHomePage());
		btnOrder.setOnAction(e -> loadOrderPage());
		btnNotif.setOnAction(e -> loadNotifPage());
		btnHistory.setOnAction(e -> loadHistoryPage());
		btnProfile.setOnAction(e -> loadProfilePage());
		btnLogout.setOnAction(e -> {
			LoginView lv = new LoginView();
			try {
				lv.start(new Stage());
				((Stage) btnLogout.getScene().getWindow()).close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
		});
		rightBtn.getChildren().addAll(btnHome, btnOrder, btnNotif, btnHistory, btnProfile, btnLogout);
		rightBtn.setAlignment(Pos.CENTER_RIGHT);
		rightBtn.setStyle("-fx-background-color: transparent; -fx-padding: 6 12; -fx-text-fill: white; -fx-font-size: 15px;");
		rightBtn.setSpacing(10);
		navbar.getChildren().addAll(logo, spacer, rightBtn);
		navbar.setStyle("-fx-background-color: #000080; -fx-padding: 12 20;");
		return navbar;
	}
	private void loadOrderPage() {
		content.getChildren().setAll(CustomerOrderView.create(currentUser));
	}

	private void loadProfilePage() {
		content.getChildren().setAll(ProfileView.create(currentUser));
	}

	private void loadHistoryPage() {
		content.getChildren().setAll(CustomerHistoryView.create(currentUser));
	}

	private void loadNotifPage() {
		content.getChildren().setAll(CustomerNotificationView.create(currentUser));
	}

	private void loadHomePage() {
		content.getChildren().setAll(CustomerHomeView.create(currentUser));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
