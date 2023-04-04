// specify the package
package userinterface;

// system imports

import impresario.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Scout;
import model.ScoutCollection;

import java.util.Collections;
import java.util.List;
import java.util.Vector;

/** The class containing the Scout View  for the Library application */
//==============================================================
public class ScoutCollectionView extends View
{

	// GUI components
	private TableView<Scout> table = new TableView<Scout>();

	protected Button backButton;

	// For showing error message
	protected MessageView statusLog;

	// constructor for this class -- takes a model object
	//----------------------------------------------------------
	public ScoutCollectionView(IModel scout)
	{
		super(scout, "ScoutView");

		// create a container for showing the contents
		VBox container = new VBox(10);
		container.setPadding(new Insets(15, 5, 5, 5));

		// Add a title for this panel
		container.getChildren().add(createTitle());

		// create our GUI components, add them to this Container
		container.getChildren().add(createFormContent());

		container.getChildren().add(createStatusLog("             "));

		getChildren().add(container);

		populateFields();

		myModel.subscribe("UpdateStatusMessage", this);
	}


	// Create the title container
	//-------------------------------------------------------------
	private Node createTitle()
	{
		HBox container = new HBox();
		container.setAlignment(Pos.CENTER);

		Text titleText = new Text(" Boy Scout Troop 209 Tree Sales: Scout Search Results ");
		titleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		titleText.setWrappingWidth(300);
		titleText.setTextAlignment(TextAlignment.CENTER);
		titleText.setFill(Color.DARKGREEN);
		container.getChildren().add(titleText);

		return container;
	}

	// Create the main form content
	//------------------------------------------------------------
	private VBox createFormContent()
	{
		VBox vbox = new VBox(10);

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		//grid.setHgap(10);
		//grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text prompt = new Text("Search Results");
		prompt.setWrappingWidth(400);
		prompt.setTextAlignment(TextAlignment.CENTER);
		prompt.setFill(Color.BLACK);
		grid.add(prompt, 0, 0, 2, 1);


		table.setEditable(true);


		HBox btnCont = new HBox(10);
		btnCont.setAlignment(Pos.CENTER);
		backButton = new Button("Back");
		backButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				clearErrorMessage();
				table.getItems().clear();
				myModel.stateChangeRequest("Cancel", null);
			}
		});

		btnCont.getChildren().add(backButton);

		vbox.getChildren().add(table);
		vbox.getChildren().add(btnCont);

		return vbox;
	}


	// Create the status log field
	//-------------------------------------------------------------
	protected MessageView createStatusLog(String initialMessage)
	{
		statusLog = new MessageView(initialMessage);

		return statusLog;
	}

	//-------------------------------------------------------------
	public void populateFields()
	{
		TableColumn scoutIdCol = new TableColumn("Scout Id");
		scoutIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		TableColumn lastNameCol = new TableColumn("Last Name");
		lastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
		TableColumn firstNameCol = new TableColumn("First Name");
		firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
		TableColumn middleNameCol = new TableColumn("Middle Name");
		middleNameCol.setCellValueFactory(new PropertyValueFactory<>("MiddleName"));
        TableColumn dateOfBirthCol = new TableColumn("Date Of Birth");
		dateOfBirthCol.setCellValueFactory(new PropertyValueFactory<>("DateOfBirth"));
        TableColumn phoneNumberCol = new TableColumn("Phone Number");
		phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        TableColumn emailCol = new TableColumn("Email");
		emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
		TableColumn troopIDCol = new TableColumn("Troop ID");
		troopIDCol.setCellValueFactory(new PropertyValueFactory<>("TroopID"));
        TableColumn statusCol = new TableColumn("Status");
		statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        TableColumn dateStatusUpdatedCol = new TableColumn("Date Status Updated");
		dateStatusUpdatedCol.setCellValueFactory(new PropertyValueFactory<>("DateStatusUpdated"));

		Vector scoutList = (Vector) myModel.getState("GetScoutList");

		ObservableList<Scout> observableList = FXCollections.observableList(scoutList);
		List<Scout> list = Collections.list(scoutList.elements());

		table.getColumns().addAll(scoutIdCol, firstNameCol, lastNameCol, middleNameCol, 
            dateOfBirthCol, phoneNumberCol, emailCol, troopIDCol, statusCol, dateStatusUpdatedCol);
		table.setItems(observableList);
	}

	/**
	 * Update method
	 */
	//---------------------------------------------------------
	public void updateState(String key, Object value)
	{

	}

	/**
	 * Display error message
	 */
	//----------------------------------------------------------
	public void displayErrorMessage(String message)
	{
		statusLog.displayErrorMessage(message);
	}

	/**
	 * Display info message
	 */
	//----------------------------------------------------------
	public void displayMessage(String message)
	{
		statusLog.displayMessage(message);
	}

	/**
	 * Clear error message
	 */
	//----------------------------------------------------------
	public void clearErrorMessage()
	{
		statusLog.clearErrorMessage();
	}

	private void processAction(Event e)
	{

	}

}

//---------------------------------------------------------------
//	Revision History:
//


