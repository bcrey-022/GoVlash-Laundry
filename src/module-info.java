module GoVlashLaundry {
	exports controller;
	exports view;
	exports main;
	exports model;
	exports repository;
	exports databaseconnector;

	requires java.sql;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.graphics;
	
	opens controller to javafx.fxml;
	opens view to javafx.fxml;
}