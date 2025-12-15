package view;

import controller.TransactionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.Service;
import model.Transaction;
import model.User;

public class CustomerHistoryView{

	public static StackPane create(User currentUser) {
		TransactionController tc = new TransactionController();
		TableView<Transaction> table = new TableView<>();
		TableColumn<Transaction, String> trIdCol = new TableColumn<>("Transaction ID");
		TableColumn<Transaction, String> serviceCol = new TableColumn<>("Service Name");
		TableColumn<Transaction, String> receptionistCol = new TableColumn<>("Receptionist");
		TableColumn<Transaction, String> laundryStaffCol = new TableColumn<>("Laundry Staff");
		TableColumn<Transaction, String> dateCol = new TableColumn<>("Transaction Date");
		TableColumn<Transaction, Integer> weightCol = new TableColumn<>("Weight");
		TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
		TableColumn<Transaction, String> noteCol = new TableColumn<>("Notes");
		trIdCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		serviceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getService().getName()));
		receptionistCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReceptionist().getName()));
		laundryStaffCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLaundryStaff().getName()));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		noteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
		table.getColumns().addAll(trIdCol, serviceCol, receptionistCol, laundryStaffCol, dateCol, statusCol, weightCol, noteCol);
		table.getItems().addAll(tc.viewTransactionHistory(currentUser.getId()));
		StackPane sp = new StackPane(table);
		return sp;
	}

}
