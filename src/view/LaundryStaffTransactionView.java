package view;

import controller.TransactionController;
import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Transaction;
import model.User;

public class LaundryStaffTransactionView {

	public static StackPane create(User currentUser) {
		TransactionController tc = new TransactionController();
		UserController uc = new UserController();
		TableView<Transaction> table = new TableView<>();
		TableColumn<Transaction, String> idCol = new TableColumn<>("Transaction ID");
		TableColumn<Transaction, String> serviceCol = new TableColumn<>("Service");
		TableColumn<Transaction, String> customerCol = new TableColumn<>("Customer");
		TableColumn<Transaction, String> receptionistCol = new TableColumn<>("Receptionist");
		TableColumn<Transaction, String> dateCol = new TableColumn<>("Transaction Date");
		TableColumn<Transaction, Integer> weightCol = new TableColumn<>("Weight");
		TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
		TableColumn<Transaction, String> noteCol = new TableColumn<>("Notes");
		TableColumn<Transaction, Void> doCol = new TableColumn<>("Do the Order");
		TableColumn<Transaction, Void> markCol = new TableColumn<>("Mark as Finished");
		Label lblMessage = new Label();
		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		serviceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getService().getName()));
		customerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getId()));
		receptionistCol.setCellValueFactory(data -> {
			User r = data.getValue().getReceptionist();
		    return new SimpleStringProperty(r == null ? "-" : r.getName());
		});
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		noteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
		doCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Do the Order");        
            {
                btn.setOnAction(e -> {
                	Transaction t = getTableRow().getItem();
                	if(t == null) return;
                	boolean success = tc.updateStatus(t.getTransactionId(), "Washing");
                	if(success) {
                		getTableView().getItems().setAll(tc.viewLSTransaction());
                		lblMessage.setText("Transaction accepted to washing");
                	}
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                TableRow<Transaction> row = getTableRow();
                if (row == null) {
                    setGraphic(null);
                    return;
                }
                Transaction t = row.getItem();
                if (t == null || !"Pending".equals(t.getStatus())) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        });
		markCol.setCellFactory(cols -> new TableCell<>() {
			private final Button btn = new Button("Mark As Finished");
			{
				btn.setOnAction(f -> {
					Transaction t = getTableRow().getItem();
					if(t == null) return;
					boolean success = tc.updateStatus(t.getTransactionId(), "Finished");
					if(success) {
						table.getItems().setAll(tc.viewLSTransaction());
						lblMessage.setText("Transaction marked as finished");
					}
				});
			}
			@Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    return;
                }
                TableRow<Transaction> row = getTableRow();
                if (row == null) {
                    setGraphic(null);
                    return;
                }
                Transaction t = row.getItem();
                if (t == null || !"Washing".equals(t.getStatus())) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
		});
		table.getColumns().addAll(idCol, serviceCol, customerCol, receptionistCol, dateCol, statusCol, weightCol, noteCol, doCol, markCol);
		table.getItems().setAll(tc.viewLSTransaction());
		VBox root = new VBox(10, table, lblMessage);
		StackPane sp = new StackPane(root);
		return sp;
	}

}
