
import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardController{
	
	@FXML
	JFXTextField goldval;
	@FXML
	JFXButton editGold;
	Boolean b = true;

	public DashboardController() {} 
	
	public void initialize() {
			goldval.setText(Utilities.getgold());
		}
	
	public void enableGoldVal() {
		goldval.setEditable(true);
		goldval.requestFocus();
		editGold.setOpacity(1);
	}
	public void callAddSale() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainMenu.class.getResource("AddSale.fxml"));
			Scene scene = new Scene(loader.load());
			Stage s = new Stage();
			s.initModality(Modality.WINDOW_MODAL);
			s.initOwner(goldval.getScene().getWindow());
			s.setScene(scene);
			s.centerOnScreen();
			s.initStyle(StageStyle.UNDECORATED);
			s.show();

	}
	public void updateGold() {
		Utilities.setGold(goldval.getText());
		goldval.setEditable(false);
		editGold.setOpacity(0);	
	}
	
	public void openStocks() throws IOException {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainMenu.class.getResource("StockTable.fxml"));
			Scene scene = new Scene(loader.load());
			Stage s = new Stage();
			s.initModality(Modality.WINDOW_MODAL);
			s.initOwner(goldval.getScene().getWindow());
			s.setScene(scene);
			s.centerOnScreen();
			s.show();
	}
}
