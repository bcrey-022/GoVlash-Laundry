package view;

import java.time.LocalDate;

import controller.ServiceController;
import controller.TransactionController;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Service;
import model.Transaction;
import model.User;

public class CustomerOrderView {

	public static StackPane create(User currentUser) {
		TransactionController tc = new TransactionController();
		ServiceController sc = new ServiceController();
		Label title = new Label("Order the Laundry");
		VBox box = new VBox(10);
		Label lblService = new Label("Choose Service: ");
		ComboBox<Service> cbService = new ComboBox<>();
		cbService.getItems().addAll(sc.getAllServiceName());
		Label lblWeight = new Label("Weight: ");
		TextField txtWeight = new TextField();
		txtWeight.setPromptText("Enter weight");
		Label lblNotes = new Label("Notes: ");
		TextField txtNotes = new TextField();
		txtNotes.setPromptText("Enter notes");
		GridPane gp = new GridPane();
		gp.add(lblService, 0, 0);
		gp.add(cbService, 1, 0);
		gp.add(lblWeight, 0, 1);
		gp.add(txtWeight, 1, 1);
		gp.add(lblNotes, 0, 2);
		gp.add(txtNotes, 1, 2);
		Button btnSubmit = new Button("Submit");
		btnSubmit.setPrefWidth(100);
		Label lblMessage = new Label();
		btnSubmit.setOnAction(e ->{
			Service selectService = cbService.getValue();
			int weight = 0;
			try {
				weight = Integer.parseInt(txtWeight.getText());
			} catch (Exception e2) {
				lblMessage.setText("Weight must be integer");
				return;
			}
			String notes = txtNotes.getText();
			if(selectService == null || weight == 0) {
				lblMessage.setText("Fill all the data");
				return;
			}
			Transaction temp = tc.createTempTransaction(selectService, currentUser, weight, notes);
			if(temp == null) {
				lblMessage.setText(tc.getMessage());
				return;
			}
			lblMessage.setText(tc.getMessage());
			txtNotes.clear();
			txtWeight.clear();
		});
		StackPane sp = new StackPane();
		sp.getChildren().addAll(title, gp, lblMessage, btnSubmit);
		return sp;
	}

}
