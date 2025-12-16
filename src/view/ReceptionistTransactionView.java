package view;

import java.util.List;

import controller.TransactionController;
import controller.UserController;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
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
		assignCol.setCellFactory(col -> new TableCell<>() {
		    private final Button btnAssign = new Button("Assign");
		    private final ComboBox<User> cbLs = new ComboBox<>();
		    private final Button btnSubmit = new Button("Submit");
		    private final HBox assignBox = new HBox(15, cbLs, btnSubmit);
		    {
		        cbLs.getItems().setAll(uc.getLaundryStaff());
		        assignBox.setVisible(false);
		        assignBox.setManaged(false);
		        btnAssign.setOnAction(e -> {
		            btnAssign.setVisible(false);
		            btnAssign.setManaged(false);

		            assignBox.setVisible(true);
		            assignBox.setManaged(true);
		        });
		        btnSubmit.setOnAction(e -> {
		            Transaction t = getTableView().getItems().get(getIndex());
		            String tempId = t.getTransactionId();
		            User staff = cbLs.getValue();
		            if (staff == null) {
		                System.out.println("Select laundry staff first!");
		                return;
		            }
		            boolean success = tc.assignStaff(tempId, currentUser, staff);
		            if (success) {
		                setGraphic(null);
		                table.getItems().setAll(tc.getTempTransaction());
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
		        btnAssign.setVisible(true);
		        btnAssign.setManaged(true);

		        assignBox.setVisible(false);
		        assignBox.setManaged(false);
		        setGraphic(new HBox(5, btnAssign, assignBox));
		    }
		});
		table.getColumns().addAll(idCol, serviceCol, customerCol, dateCol, statusCol, weightCol, noteCol, assignCol);
		table.getItems().setAll(tc.getTempTransaction());
		StackPane sp = new StackPane(table);
		return sp;
	}

}
