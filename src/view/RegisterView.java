package view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import controller.AuthController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RegisterView extends Application{
	private AuthController auth = new AuthController();

	@Override
	public void start(Stage primaryStage) throws Exception {
		Label title = new Label("Register Page");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label lblName = new Label("Name: ");
        TextField txtName = new TextField();
        txtName.setPromptText("Enter name");
        Label lblEmail = new Label("Email: ");
		TextField txtEmail = new TextField();
		Label lblRole = new Label("Role: ");
        ComboBox<String> cbRole = new ComboBox<>();
        cbRole.getItems().addAll("Admin", "Customer");
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
        gp.add(lblEmail, 0, 1);
        gp.add(txtEmail, 1, 1);
        gp.add(lblPass, 0, 2);
        gp.add(txtPass, 1, 2);
        gp.add(lblCPass, 0, 3);
        gp.add(txtCPass, 1, 3);
        gp.add(lblGender, 0, 4);
        gp.add(cbGender, 1, 4);
        gp.add(lblDob, 0, 5);
        gp.add(dpDob, 1, 5);
        gp.add(lblRole, 0, 6);
        gp.add(cbRole, 1, 6);
        Button btnRegis = new Button("Register");
        btnRegis.setPrefWidth(100);
        Hyperlink linkLogin = new Hyperlink("Have an account? Login here!");
        linkLogin.setOnAction(e -> {
            LoginView loginView = new LoginView();
            try {
                loginView.start(new Stage()); 
                ((Stage) linkLogin.getScene().getWindow()).close(); 
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        });
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill : #D30000;");
        btnRegis.setOnAction(e -> {
        	String username = txtName.getText();
            String email = txtEmail.getText();
            String pass = txtPass.getText();
            String confirmPass = txtCPass.getText(); 
            String gender = cbGender.getValue();
            String role = cbRole.getValue();
            LocalDate dob = dpDob.getValue();
            String dobStr = (dob != null) ? dob.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : null;
            if(username.isEmpty() || email.isEmpty() || pass.isEmpty() || confirmPass.isEmpty() || role.isEmpty() || gender.isEmpty() || dob == null) {
            	lblMessage.setText("Fill all the data!");
            }
            boolean success = auth.registerUser(username, pass, confirmPass, role, gender, email, dobStr);
            if (success) {
            	LoginView loginView = new LoginView();
                try {
                    loginView.start(new Stage()); 
                    ((Stage) btnRegis.getScene().getWindow()).close(); 
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return;
                }
            } else {
                lblMessage.setText(auth.getMessage());
            }
        });
        VBox root = new VBox(10, title, gp, btnRegis, lblMessage, linkLogin);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f0f0;");
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Register Page");
        primaryStage.show();		
	}
	
	public static void main(String[] args) {
        launch(args);
    }
	
}
