package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.AuthController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import model.User;

public class AdminEmployeeView {
	
	public static StackPane create() {
		UserController uc = new UserController();
		AuthController auth = new AuthController();
		TableView<User> table = new TableView<>();
		TableColumn<User, String> idCol = new TableColumn<>("Employee ID");
		TableColumn<User, String> nameCol = new TableColumn<>("Name");
		TableColumn<User, String> emailCol = new TableColumn<>("Email");
		TableColumn<User, String> genderCol = new TableColumn<>("Gender");
		TableColumn<User, String> dobCol = new TableColumn<>("Date Of Birth");
		TableColumn<User, String> roleCol = new TableColumn<>("Role");
		idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
		genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
		dobCol.setCellValueFactory(new PropertyValueFactory<>("dob"));
		roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
		table.getColumns().addAll(idCol, nameCol, emailCol, genderCol, dobCol, roleCol);
		table.getItems().addAll(uc.getAllEmployee());
		Button btnAdd = new Button("Add New Employee");
		btnAdd.setVisible(true);
		btnAdd.setManaged(true);
		VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
		Label title = new Label("Add New Employee");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label lblName = new Label("Name: ");
        TextField txtName = new TextField();
        txtName.setPromptText("Enter name");
        Label lblRole = new Label("Role: ");
        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("Laundry Staff", "Receptionist");
        Label lblEmail = new Label("Email: ");
		TextField txtEmail = new TextField();
        txtEmail.setPromptText("Enter email");
        Label lblPass = new Label("Password: ");
        PasswordField txtPass = new PasswordField();
        txtPass.setPromptText("Enter Password");
        Label lblCPass = new Label("Confirm Password");
        PasswordField txtCPass = new PasswordField();
        txtCPass.setPromptText("Enter Password Again");
        Label lblGender = new Label("Gender: ");
        ComboBox<String> cbGender = new ComboBox<>();
        cbGender.getItems().addAll("Male", "Female");       
        Label lblDob = new Label("Date Of Birth: ");
        DatePicker dpDob = new DatePicker();            
        GridPane gp = new GridPane();
        gp.add(lblName, 0, 0);
        gp.add(txtName, 1, 0);
        gp.add(lblRole, 0, 1);
        gp.add(cbRole, 1, 1);
        gp.add(lblEmail, 0, 2);
        gp.add(txtEmail, 1, 2);
        gp.add(lblPass, 0, 3);
        gp.add(txtPass, 1, 3);
        gp.add(lblCPass, 0, 4);
        gp.add(txtCPass, 1, 4);
        gp.add(lblGender, 0, 5);
        gp.add(cbGender, 1, 5);
        gp.add(lblDob, 0, 6);
        gp.add(dpDob, 1, 6);
        Button btnRegis = new Button("Register");
        btnRegis.setPrefWidth(100);
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill : #D30000;");
        btnRegis.setOnAction(e -> {
        	String username = txtName.getText();
            String email = txtEmail.getText();
            String pass = txtPass.getText();
            String confirmPass = txtCPass.getText(); 
            String role = cbRole.getValue();
            String gender = cbGender.getValue();
            LocalDate dob = dpDob.getValue();
            String dobStr = (dob != null) ? dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
            if(username.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || role.isEmpty() || gender.isEmpty() || dob == null) {
            	lblMessage.setText("Fill all the data!");
            }
            boolean success = auth.registerEmployee(username, pass, confirmPass, gender, role, email, dobStr);
            if (success) {
            	table.getItems().setAll(uc.getAllEmployee());
            	root.setVisible(false);
            	root.setManaged(false);
            	btnAdd.setVisible(true);
        		btnAdd.setManaged(true);
            } else {
                lblMessage.setText(auth.getMessage());
                return;
            }
        });
        root.getChildren().addAll(title, gp, btnRegis, lblMessage);
        root.setVisible(false);
        root.setManaged(false);
        btnAdd.setOnAction(e ->{
        	root.setVisible(true);
        	root.setManaged(true);
        	btnAdd.setVisible(false);
    		btnAdd.setManaged(false);
        });
        StackPane sp = new StackPane();
        sp.getChildren().addAll(table, btnAdd, root);
        return sp;
	}
}
