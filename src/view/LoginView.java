package view;

import controller.AuthController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class LoginView extends Application {

    private AuthController auth = new AuthController();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Label title = new Label("Login Page");
        title.setStyle("-fx-font-size: 20px;" + "-fx-font-weight: bold;");
        Label lblEmail = new Label("Email:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Enter email");
        HBox emailRow = new HBox(32, lblEmail, txtEmail);
        Label lblPassword = new Label("Password:");
        PasswordField txtPassword = new PasswordField();
        txtPassword.setPromptText("Enter password");
        HBox passRow = new HBox(10, lblPassword, txtPassword);
        Button btnLogin = new Button("Login");
        btnLogin.setPrefWidth(100);
        Hyperlink linkRegis = new Hyperlink("Don't have an account? Register here!");
        linkRegis.setOnAction(e -> {
            RegisterView registerView = new RegisterView();
            try {
                registerView.start(new Stage()); 
                ((Stage) linkRegis.getScene().getWindow()).close(); 
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        });
        Label lblMessage = new Label();
        lblMessage.setStyle("-fx-text-fill : #D30000;");
        btnLogin.setOnAction(e -> {
            String email = txtEmail.getText();
            String pass = txtPassword.getText();
            if(email.isEmpty() || pass.isEmpty()) {
    			lblMessage.setText("Fill all the data!");
    		}
            User current = auth.loginAuth(email, pass);
            if (current != null) {
                if(email.endsWith("@email.com")) {
                	CustomerDashboardView cdv = new CustomerDashboardView(current);
                	try {
						cdv.start(new Stage());
						((Stage) btnLogin.getScene().getWindow()).close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
                }
                else if(email.endsWith("@govlash.com")) {
                	AdminDashboardView adv = new AdminDashboardView(current);
                	try {
						adv.start(new Stage());
						((Stage) btnLogin.getScene().getWindow()).close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}
                }
            } else {
                lblMessage.setText(auth.getMessage());
                return;
            }
        });
        VBox root = new VBox(10, title, emailRow, passRow, btnLogin, lblMessage, linkRegis);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f0f0f0;");
        Scene scene = new Scene(root, 350, 350);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
