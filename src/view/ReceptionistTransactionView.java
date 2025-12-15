package view;

import java.util.List;

import controller.NotificationController;
import controller.TransactionController;
import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import model.Transaction;
import model.User;

public class ReceptionistTransactionView {

	public static StackPane create(User currentUser) {
		TransactionController tc = new TransactionController();
		UserController uc = new UserController();
		TableView<Transaction> table = new TableView<>();
		TableColumn<Transaction, String> idCol = new TableColumn<>("Transaction ID");
		TableColumn<Transaction, String> serviceCol = new TableColumn<>("Service");
		TableColumn<Transaction, String> customerCol = new TableColumn<>("Customer");
		TableColumn<Transaction, String> dateCol = new TableColumn<>("Transaction Date");
		TableColumn<Transaction, Integer> weightCol = new TableColumn<>("Weight");
		TableColumn<Transaction, String> statusCol = new TableColumn<>("Status");
		TableColumn<Transaction, String> noteCol = new TableColumn<>("Notes");
		TableColumn<Transaction, Void> assignCol = new TableColumn<>("Assign Order");
		idCol.setCellValueFactory(new PropertyValueFactory<>("transactionId"));
		serviceCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getService().getName()));
		customerCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCustomer().getId()));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		weightCol.setCellValueFactory(new PropertyValueFactory<>("weight"));
		statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
		noteCol.setCellValueFactory(new PropertyValueFactory<>("notes"));
		ComboBox<User> cbLs = new ComboBox<>();
    	cbLs.getItems().addAll(uc.getLaundryStaff());
		assignCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Assign Order to Laundry Staff");        
            {
                btn.setOnAction(e -> {
                	Transaction t = getTableView().getItems().get(getIndex());
                	cbLs.setVisible(true);
                	cbLs.setManaged(true);
                	User getLS = cbLs.getValue();
                	Button submit = new Button("Assign");
                	submit.setOnAction(f ->{
                		boolean success1 = tc.assignStaffAndSave(t.getTransactionId(), currentUser, getLS);
                    	if(success1) {
                    		boolean success2 = tc.updateStatus(idCol.getText(), "Send To Laundry Staff");
                    		if(success2) {
                    			table.getItems().setAll(tc.viewPendingTransaction());
                    			cbLs.setVisible(false);
                    			cbLs.setManaged(false);
                    		}
                    	}
                	});                	
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
		table.getColumns().addAll(idCol, serviceCol, customerCol, dateCol, statusCol, weightCol, noteCol, assignCol);
		StackPane sp = new StackPane(table);
		return sp;
	}

}
