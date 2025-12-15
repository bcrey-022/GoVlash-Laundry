package view;

import controller.ServiceController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.Service;

public class AdminServiceView {

	public static StackPane create() {
		ServiceController sc = new ServiceController();
		TableView<Service> table = new TableView<>();
		TableColumn<Service, String> idCol = new TableColumn<>("Service ID");
		TableColumn<Service, String> nameCol = new TableColumn<>("Name");
		TableColumn<Service, String> descCol = new TableColumn<>("Description");
		TableColumn<Service, Integer> priceCol = new TableColumn<>("Price");
		TableColumn<Service, Integer> durationCol = new TableColumn<>("Duration (Days)");
		TableColumn<Service, Void> deleteCol = new TableColumn<>("Delete");
		TableColumn<Service, Void> updateCol = new TableColumn<>("Update");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		descCol.setCellValueFactory(new PropertyValueFactory<>("description"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
		deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Delete");

            {
                btn.setOnAction(e -> {
                    Service service = getTableView().getItems().get(getIndex());
                    sc.deleteService(service.getId());
                    getTableView().getItems().remove(service); 
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
		VBox roots = new VBox(10);
		roots.setPadding(new Insets(20));
        roots.setAlignment(Pos.CENTER);
        roots.setVisible(false);
    	roots.setManaged(false);
		updateCol.setCellFactory(col -> new TableCell<>() {
			private final Button btn = new Button("Update");			
			{
				btn.setOnAction(e -> {
					roots.setVisible(true);
					roots.setManaged(true);
					Service s = getTableView().getItems().get(getIndex());
					roots.getChildren().clear();
					Label title = new Label("Update Service");
					title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
					Label lblId = new Label("Service ID: ");
					Label txtId = new Label();
					txtId.setText(String.valueOf(s.getId()));
			        Label lblName = new Label("Service Name: ");
			        TextField txtName = new TextField();
			        txtName.setText(s.getName());
			        Label lblDesc = new Label("Description: ");
					TextField txtDesc = new TextField();
			        txtDesc.setText(s.getDescription());
			        Label lblPrice = new Label("Price: ");
			        TextField txtPrice = new TextField();
			        txtPrice.setText(String.valueOf(s.getPrice()));
			        Label lblDuration = new Label("Duration (Days): ");
			        TextField txtDuration = new TextField();
			        txtDuration.setText(String.valueOf(s.getDuration()));  
			        GridPane gp = new GridPane();
			        gp.add(lblId, 0, 0);
			        gp.add(txtId, 1, 0);
			        gp.add(lblName, 0, 1);
			        gp.add(txtName, 1, 1);
			        gp.add(lblDesc, 0, 2);
			        gp.add(txtDesc, 1, 2);
			        gp.add(lblPrice, 0, 3);
			        gp.add(txtPrice, 1, 3);
			        gp.add(lblDuration, 0, 4);
			        gp.add(txtDuration, 1, 4);
			        Button btnUpdate = new Button("Update");
			        btnUpdate.setPrefWidth(100);
			        Label lblMessage = new Label();
			        lblMessage.setStyle("-fx-text-fill : #D30000;");
			        btnUpdate.setOnAction(f -> {
			        	String name = txtName.getText();
			        	String desc = txtDesc.getText();
			        	int price = 0;
			        	int duration = 0;
			        	try {
			        		price = Integer.parseInt(txtPrice.getText());
						} catch (Exception e2) {
							lblMessage.setText("Price must be integer");
							return;
						}
			        	try {
							duration = Integer.parseInt(txtDuration.getText());
						} catch (Exception e2) {
							lblMessage.setText("Duration must be integer");
							return;
						}
			            if(name.isEmpty() || desc.isEmpty() || price == 0 || duration == 0) {
			            	lblMessage.setText("Fill all the data!");
			            	return;
			            }
			            boolean success = sc.updateService(s.getId(), name, desc, price, duration);
			            if (success) {
			            	table.getItems().setAll(sc.getAllServices());
			            	roots.setVisible(false);
			            	roots.setManaged(false);
			            } else {
			                lblMessage.setText(sc.getMessage());
			                return;
			            }
			        });
			        roots.getChildren().addAll(title, gp, btnUpdate, lblMessage);
				});
			}
			@Override
		    protected void updateItem(Void item, boolean empty) {
		        super.updateItem(item, empty);
		        setGraphic(empty ? null : btn);
		    }
		});
		table.getColumns().addAll(idCol, nameCol, descCol, priceCol, durationCol, deleteCol, updateCol);
		table.getItems().addAll(sc.getAllServices());
		Button btnAdd = new Button("Add New Service");
		btnAdd.setVisible(true);
		btnAdd.setManaged(true);
		VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
		Label title = new Label("Add New Service");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label lblName = new Label("Service Name: ");
        TextField txtName = new TextField();
        txtName.setPromptText("Enter name");
        Label lblDesc = new Label("Description: ");
		TextField txtDesc = new TextField();
        txtDesc.setPromptText("Enter description");
        Label lblPrice = new Label("Price: ");
        TextField txtPrice = new TextField();
        txtPrice.setPromptText("Enter price");
        Label lblDuration = new Label("Duration (Days): ");
        TextField txtDuration = new TextField();
        txtDuration.setPromptText("Enter duration");  
        GridPane gp = new GridPane();
        gp.add(lblName, 0, 0);
        gp.add(txtName, 1, 0);
        gp.add(lblDesc, 0, 1);
        gp.add(txtDesc, 1, 1);
        gp.add(lblPrice, 0, 2);
        gp.add(txtPrice, 1, 2);
        gp.add(lblDuration, 0, 3);
        gp.add(txtDuration, 1, 3);
        Button btnRegis = new Button("Add");
        btnRegis.setPrefWidth(100);
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill : #D30000;");
        btnRegis.setOnAction(e -> {
        	String name = txtName.getText();
        	String desc = txtDesc.getText();
        	int price = 0;
        	int duration = 0;
        	try {
        		price = Integer.parseInt(txtPrice.getText());
			} catch (Exception e2) {
				lblMessage.setText("Price must be integer");
				return;
			}
        	try {
				duration = Integer.parseInt(txtDuration.getText());
			} catch (Exception e2) {
				lblMessage.setText("Duration must be integer");
				return;
			}
            if(name.isEmpty() || desc.isEmpty() || price == 0 || duration == 0) {
            	lblMessage.setText("Fill all the data!");
            	return;
            }
            boolean success = sc.addNewService(name, desc, price, duration);
            if (success) {
            	table.getItems().setAll(sc.getAllServices());
            	root.setVisible(false);
            	root.setManaged(false);
            	btnAdd.setVisible(true);
        		btnAdd.setManaged(true);
            } else {
                lblMessage.setText(sc.getMessage());
                return;
            }
        });
        root.getChildren().addAll(title, gp, btnRegis, lblMessage );
        root.setVisible(false);
        root.setManaged(false);
        btnAdd.setOnAction(e ->{
        	root.setVisible(true);
        	root.setManaged(true);
        	btnAdd.setVisible(false);
    		btnAdd.setManaged(false);
        });
        StackPane sp = new StackPane();
        sp.getChildren().addAll(table, btnAdd, root, roots);
        return sp;
	}

}
