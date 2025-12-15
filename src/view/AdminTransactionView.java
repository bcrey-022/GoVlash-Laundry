package view;

import controller.NotificationController;
import controller.TransactionController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.Transaction;

public class AdminTransactionView {

	public static StackPane create() {
		TransactionController tc = new TransactionController();
		NotificationController nc = new NotificationController();
		TableView<Transaction> table = new TableView<>();
		TableColumn<Transaction, String> idCol = new TableColumn<>("Transaction ID");
		TableColumn<Transaction, String> serviceCol = new TableColumn<>("Service");
		TableColumn<Transaction, String> customerCol = new TableColumn<>("Customer");
		TableColumn<Transaction, String> receptionistCol = new TableColumn<>("Receptionist");
		TableColumn<Transaction, String> laundryStaffCol = new TableColumn<>("Laundry Staff");
		TableColumn<Transaction, String> dateCol = new TableColumn<>("Transaction Date");
		TableColumn<Transaction, Integer> weightCol = new TableColumn<>("Weight");
		TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
		TableColumn<Transaction, String> noteCol = new TableColumn<>("Notes");
		TableColumn<Transaction, Void> sendNotifCol = new TableColumn<>("Send Notification");
		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		serviceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getService().getName()));
		customerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getId()));
		receptionistCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getReceptionist().getName()));
		laundryStaffCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getLaundryStaff().getName()));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		noteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
		sendNotifCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Send Notification");        
            {
            	if(statusCol.getText().equals("Ready to Pick Up")) {
            		btn.setVisible(true);
                    btn.setManaged(true);
            	}
            	else {
            		btn.setVisible(false);
                    btn.setManaged(false);
            	}
                btn.setOnAction(e -> {
                   nc.addNewNotification(customerCol.getText(), idCol.getText());
                   nc.sendNotification(customerCol.getText());
                   btn.setVisible(false);
                   btn.setManaged(false);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
		table.getColumns().addAll(idCol, serviceCol, customerCol, receptionistCol, laundryStaffCol, dateCol, statusCol, weightCol, noteCol, sendNotifCol);
		ObservableList<Transaction> masterData = FXCollections.observableArrayList(tc.viewAllTransaction());
		FilteredList<Transaction> filteredData = new FilteredList<>(masterData, t -> true);
		table.setItems(filteredData);
		CheckBox filter = new CheckBox("View Finished Transaction");
		filter.selectedProperty().addListener((obs, oldVal, checked)->{
			if(checked) {
				filteredData.setPredicate(t ->"Finished".equalsIgnoreCase(t.getStatus()));
			}
			else {
				filteredData.setPredicate(t -> true);
			}
		});		
		StackPane sp = new StackPane(table, filter);
		return sp;
	}

}
