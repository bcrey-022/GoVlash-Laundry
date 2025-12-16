package view;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import model.User;

public class ProfileView{

	public static StackPane create(User currentUser) {
		Label profile = new Label("Profile");
		Label lblId = new Label("ID: ");
		Label txtId = new Label();
		txtId.setText(currentUser.getId());
		Label lblName = new Label("Name: ");
		Label txtName = new Label();
		txtName.setText(currentUser.getName());
		Label lblEmail = new Label("Email: ");
		Label txtEmail = new Label();
		txtEmail.setText(currentUser.getEmail());
		Label lblPassword = new Label("Password: ");
		Label txtPassword = new Label();
		txtPassword.setText(currentUser.getPassword());
		Label lblRole = new Label("Role");
		Label txtRole = new Label();
		txtRole.setText(currentUser.getRole());
		Label lblDob = new Label("Date of Birth: ");
		Label txtDob = new Label();
		txtDob.setText(currentUser.getDob());
		Label lblGender = new Label("Gender");
		Label txtGender = new Label();
		txtGender.setText(currentUser.getGender());
		GridPane gp = new GridPane();
		gp.add(lblId, 0, 0);
		gp.add(txtId, 1, 0);
		gp.add(lblName, 0, 1);
		gp.add(txtName, 1, 1);
		gp.add(lblEmail, 0, 2);
		gp.add(txtEmail, 1, 2);
		gp.add(lblPassword, 0, 3);
		gp.add(txtPassword, 1, 3);
		gp.add(lblRole, 0, 4);
		gp.add(txtRole, 1, 4);
		gp.add(lblDob, 0, 5);
		gp.add(txtDob, 1, 5);
		gp.add(lblGender, 0, 6);
		gp.add(txtGender, 1, 6);
		StackPane sp = new StackPane(profile, gp);
		return sp;
	}

}
