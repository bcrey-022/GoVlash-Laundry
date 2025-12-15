package view;

import controller.NotificationController;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import model.Notification;
import model.User;

public class CustomerNotificationView{
	private static StackPane sp = new StackPane();
	private static User current;
	public static StackPane create(User currentUser) {
		current = currentUser;
		NotificationController nc = new NotificationController();
		TableView<Notification> table = new TableView<>();
		TableColumn<Notification, Integer> idCol = new TableColumn<>("ID");
		TableColumn<Notification, String> messageCol = new TableColumn<>("Message");
		TableColumn<Notification, Void> detailCol = new TableColumn<>("Detail");
		TableColumn<Notification, Void> deleteCol = new TableColumn<>("Delete");
		TableColumn<Notification, Void> markCol = new TableColumn<>("Mark As Read");
		idCol.setCellValueFactory(new PropertyValueFactory<>("notifId"));
		messageCol.setCellValueFactory(new PropertyValueFactory<>("message"));
		detailCol.setCellFactory(col -> new TableCell<>() {
            private final Hyperlink link = new Hyperlink("Get Detail");
            {
                link.setOnAction(e -> {
                    Notification notif = getTableView().getItems().get(getIndex());
                    int id = notif.getNotifId();
                    detailView(id);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : link);
            }
        });
		deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Delete");

            {
                btn.setOnAction(e -> {
                    Notification notif = getTableView().getItems().get(getIndex());
                    nc.deleteNotification(notif.getNotifId());
                    getTableView().getItems().remove(notif); 
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : btn);
            }
        });
		markCol.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button("Mark As Read");

            {
                btn.setOnAction(e -> {
                    Notification notif = getTableView().getItems().get(getIndex());
                    nc.updateStatus(notif.getNotifId());
                    notif.setStatus(true);
                    getTableView().refresh();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Notification notif = getTableView().getItems().get(getIndex());
                    btn.setDisable(notif.isStatus());
                    setGraphic(btn);
                }
            }
        });
		table.getColumns().addAll(idCol, messageCol, detailCol, deleteCol, markCol);
		table.getItems().addAll(nc.getNotificationByUser(currentUser.getId()));
		ScrollPane scrollPane = new ScrollPane(table);
		scrollPane.setFitToWidth(true);
		sp.getChildren().addAll(scrollPane);
		return sp;
	}
	private static void detailView(int id) {
		sp.getChildren().setAll(NotificationDetailView.create(id));
	}
	public static User getCurrent() {
		return current;
	}
	public static void setCurrent(User current) {
		CustomerNotificationView.current = current;
	}
}
