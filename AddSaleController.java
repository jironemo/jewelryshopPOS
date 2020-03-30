import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class AddSaleController {
	
	@FXML 
	JFXButton exit;
	@FXML
	JFXTextField cus_name,cus_phone,cus_addr,item_id;
	@FXML
	TextArea item_desc;
	

	
	//constructor for no reason what so ever;
	public AddSaleController() {}
	
	//method to work once the scene is initialized
	public void initialize() {

		// method that will work when TAB Key is pressed on item id text field;
		item_id.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER) || ke.getCode().equals(KeyCode.TAB))
	            {
	                updateItemDesc();
	            }
	        }
	    });
	}
	
	
	///exit this stage/ AddSale form
	public void exit() {
		((Stage)exit.getScene().getWindow()).close();
	}
	
	
	///add sale data to Database
	public void addData() {
		Alert alert2 = new Alert(AlertType.INFORMATION);

		if(someFieldsNULL() == false) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setContentText("အေရာင္းစာရင္းထည့္သြင္းမည္။");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK){

				Customer buyer = new Customer(cus_name.getText(),cus_addr.getText(),cus_phone.getText());
				buyer.add();
				Sale sale = new Sale(Character.getNumericValue(item_id.getText().charAt(0)),buyer.getID());
				sale.add();
				System.out.println(sale.id+" "+sale.customerID + " "+sale.itemID);
				Item.setSold(sale.itemID);
			} else {
			    // ... user chose CANCEL or closed the dialog
			}

		}
		else {
			alert2.setContentText("မေအာင္ျမင္ပါ");
			alert2.show();
		}
	}
	
	public void updateItemDesc() {
		///refresh the item description field
		item_desc.setText("");
		
		if(someFieldsNULL() == false) {
			int a = Character.getNumericValue(item_id.getText().charAt(0));
			try {
				System.out.println(a);
				//get a connection and data from Stock;
				Connection c = Connector.connect();
				String sql = "SELECT * FROM Stock where ID = "+a+" and stock_status = 'stock';";
				Statement s = c.createStatement();
				ResultSet rs = s.executeQuery(sql);
				
				if(rs.next() != false) {
					item_desc.setText("ပစၥည္းအမည္: "+rs.getString(2)+"\n"+"ပစၥည္းအေလးခ်ိန္: "+
							new Weight(rs.getString(3)).getKyat()+" က်ပ္ "+new Weight(rs.getString(3)).getPel()+
							" ပဲ"+new Weight(rs.getString(3)).getYway()+" ေ႐ြး "+"\n အေလ်ာ့တြက္: "+
							new Weight(rs.getString(4)).getKyat()+" က်ပ္ "+new Weight(rs.getString(4)).getPel()+
							" ပဲ"+new Weight(rs.getString(4)).getYway()+" ေ႐ြး ");
				}
				else {
					item_desc.setText("ပစၥည္းမရွိပါ ျပန္လည္စစ္ေဆးေပးပါ");
				}
				c.close();
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private boolean someFieldsNULL() {
		boolean a = false;
		if(cus_name.getText().equals(null) || cus_name.getText().equals("")) {
			if(cus_phone.getText().equals(null)||cus_name.getText().equals("")) {
				if(cus_addr.getText().equals(null) || cus_addr.getText().equals("")) {
					if(item_id.getText().equals(null)||item_id.getText().equals("")) {
						a =  true;
					}
				}
			}
		}
		else {
			a = false;
		}
		return a;
	}
}