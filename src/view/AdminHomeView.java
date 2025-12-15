package view;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import model.User;

public class AdminHomeView {
	public static StackPane create(User currentUser) {
		StackPane pane = new StackPane();
		Label greeting = new Label("Welcome " + currentUser.getName());
		greeting.setStyle("-fx-font-size: 50px; -fx-text-fill: #000080;");
		pane.setPadding(new Insets(20));
		pane.getChildren().add(greeting);
		pane.setStyle("-fx-background-color: #7EF9FF;");
		return pane;
	}
}
