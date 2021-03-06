
package Controller;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import Model.Game;
import Model.Status;
import Plants.Plants;
import Plants.ShootingPlant;
import Plants.Sunflower;
import View.View;


/**
 * The Controller is a class used in the MVC pattern which
 * will call both game and view in order to update them by the actions from the user.
 *
 * Reflections: Our controller class is what ran the whole Game. This class also
 * controlled the actions for all the actions the user may perform.
 * 
 * If we had more time, I feel like it would have been a wise idea to separate and decouple the 
 * two functionalities of the class. Maybe have one class responsible for running the game, and 
 * another class that is responsible for performing actions when a user clicks something on the view.
 * 
 * @author Group 1
 * @since November 4,2018

 */
public class Controller implements ActionListener,Serializable{
	private View view;
	private Game game;
	private TimeLine timeline;
	private Builder customGame =null;
	private ObjectPersistance file;
	private Status gameMode = Status.turnMode;


	public Controller() {
		game = new Game();
		view = new View(this);
		startView();
		timeline = new TimeLine();
		view.enableButtons(false);
		file = new ObjectPersistance();
	}
	/**
	 * 	Listen for actions that happen on a certain button or label, and deal with it accordingly
	 * 
	 * 	@param None
	 * @return None
	 */
	//Initialize the components by adding the respective actionlistners/actioncommands to each action used.
	@Override
	public void actionPerformed(ActionEvent e) {
		//listening for level of dificulty
		if(e.getActionCommand().equals("easy")) {
			this.game.levelDistinguish(1);
			//this.game.startLevel(1);
			view.disableLevel();
			view.enableFunctionButtons();
			updateView();
			view.promptMessage("\t"+"Level is 'Easy' "+ "\n"+ "Simulate the Game!");
			timeline = new TimeLine(game);
		}else if(e.getActionCommand().equals("Intermediate")) {
			this.game.levelDistinguish(2);
			//this.game.startLevel(1);
			view.disableLevel();
			view.enableFunctionButtons();
			updateView();
			view.promptMessage("\t"+"Level is 'Intermediate' "+ "\n"+ "Simulate the Game!");
			timeline = new TimeLine(game);
		}else if(e.getActionCommand().equals("Hard")) {
			this.game.levelDistinguish(3);
			//this.game.startLevel(1);
			view.disableLevel();
			view.enableFunctionButtons();
			updateView();
			view.promptMessage("\t"+"Level is 'Hard' "+ "\n"+ "Simulate the Game!");
			timeline = new TimeLine(game);
			//listening to player options
		}else if(e.getActionCommand().equals("Help")){
			view.helpPrompt();
		}else if (e.getActionCommand().equals("Undo")){
			undo();
			updateView();
		}else if (e.getActionCommand().equals("Redo")){
			redo();
			updateView();
		}else if (e.getActionCommand().equals("newGame")){
			this.game = new Game();
			//this.view = new View(this);
			startView();
		}else if (e.getActionCommand().equals("save")){
			
			file.save(this.game);	
			view.promptMessage("Game File is Save. 'save.obj'");

		}else if (e.getActionCommand().equals("load")){
			Game temp = file.load();
			this.game = temp;
			view.promptMessage("Game File is Loaded. Enjoy the Game");
			if(game.getLayout().getStatus().equals(Status.created)) { // need to check because of the buttonenable
				startView();
			}else {
				
				view.disableLevel();
				view.enableFunctionButtons();
				updateView();
				
			}
		}else if (e.getActionCommand().equals("exit")){
			System.exit(0);
		}
		//listening to game buttons
		else if (e.getActionCommand().equals("simulate")) {
			simulate();
		}else if (e.getActionCommand().equals("Purchase")) {
			String info ="";
			boolean moneyFlag = false;
			boolean coolDownFlag = false;
			String plant = "";
			if(view.getBuySunflower().isSelected()) { 
				plant = "Sunflower";
				if(game.purchaseValidate(plant)) {
					if(game.coolDownValidate(plant)) {
						view.disEnableFunctionButtons();
						moneyFlag = true;
						coolDownFlag = true;
					}else {
						moneyFlag = true;
						coolDownFlag = false;
					}
				}else {
					moneyFlag = false;
				}
				info = game.purchasePlant(plant,moneyFlag,coolDownFlag);
			}else if (view.getBuyShooterPlant().isSelected()) {
				plant = "ShootingPlant";
				if(game.purchaseValidate(plant)) {
					if(game.coolDownValidate(plant)) {
						view.disEnableFunctionButtons();
						moneyFlag = true;
						coolDownFlag = true;
					}else {
						moneyFlag = true;
						coolDownFlag = false;
					}
				}else {
					moneyFlag = false;
				}
				info = game.purchasePlant(plant,moneyFlag,coolDownFlag);

			}else if (view.getBuyPotatoMine().isSelected()) {
				plant = "PotatoMine";
				if(game.purchaseValidate(plant)) {
					if(game.coolDownValidate(plant)) {
						view.disEnableFunctionButtons();
						moneyFlag = true;
						coolDownFlag = true;
					}else {
						moneyFlag = true;
						coolDownFlag = false;
					}
				}else {
					moneyFlag = false;
				}
				info = game.purchasePlant(plant,moneyFlag,coolDownFlag);
			}else if (view.getBuyChomper().isSelected()) {
				plant = "Chomper";
				if(game.purchaseValidate(plant)) {
					if(game.coolDownValidate(plant)) {
						view.disEnableFunctionButtons();
						moneyFlag = true;
						coolDownFlag = true;
					}else {
						moneyFlag = true;
						coolDownFlag = false;
					}
				}else {
					moneyFlag = false;
				}
				info = game.purchasePlant(plant,moneyFlag,coolDownFlag);
			}
			view.updateInfo(info);
			updateView();
		}
		else if(e.getActionCommand().equals("grid")){
			if(game.getPurchasedPlant()==null ) {
				//view.promptMessage(("Please Buy the Plant"));
			}else{
				JButton btn = (JButton)e.getSource();
				int row = (int)btn.getClientProperty("row");
				int col = (int)btn.getClientProperty("col");
				if(game.checkObjectGrid(row, col)) {
					view.updateInfo(game.placePlantOnGrid(row, col));
					view.loadlayout(game.getLayout());
					view.enableFunctionButtons();
					save();
				}
			}	
		}
		else if (e.getActionCommand().equals("levelBuilder")) {
			try {
				customGame = new Builder();
				customGame.readFile();
				custom();
				view.disableLevel();
				view.enableFunctionButtons();
				updateView();
				view.promptMessage("\t"+" Starting the custom Game "+ "\n"+ "Simulate the Game!");
				timeline = new TimeLine(game);
				//custom();
			} catch (SAXException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParserConfigurationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void custom() {
		//	int customTime = (int)customGame.getTime(); // this will be used when user put the cusotom time to simulate.

		List<Object> wave = customGame.getWave();
		this.gameMode = Status.customMode; // changing the status of this gamemode to customMode 
		this.game = new Game(wave);
		//this.view = new View(this);
		startView();

		//game.customGame(array[0],array[1]], array[2]);


	}

	/**
	 * 	Saves the state of the game if the user chooses to continue the game later
	 * 
	 * 	@param None
	 * @return None
	 */
	public void save() {
		timeline.addNext(this.game);
	}	

	/**
	 * 	Allows the user to go back to his/her previous move and make another move, the board also 
	 * goes back to the previous position
	 * 
	 * 	@param None
	 * @return None
	 */
	public void undo() {
		if(!timeline.isUndoAvailable()) {
			view.promptMessage("This is very First State, Can't Undo");
		}else {
			this.game=timeline.undo();
		}
	}

	/**
	 * 	Allows the user to go forward in time, if an undo was clicked previously, the board 
	 * is also affected
	 * 
	 * 	@param None
	 * @return None
	 */
	public void redo() {
		if(!timeline.isRedoAvailable()) {
			view.promptMessage("This is the Last State, Can't Redo");
		}else {
			this.game=timeline.redo();
		}
	}

	/**
	 * 	Allows the user to play the game
	 * 
	 * 	@param None
	 * @return None
	 */
	public void simulate() {
		game.simulate(this.gameMode);
		updateView();
		checkStatus(game.getLayout().getStatus());
		save();
	}


	/**
	 * 	Lets the user see the GUI of the board with the avaliable options
	 * 
	 * 	@param None
	 * @return None
	 */
	public void startView() {
		//view.updatePoints(game.getSunpoint());
		view.updateInfo("Hope You Enjoy!!");
		view.enableLevelButtons();
		view.loadlayout(game.getLayout());
		view.disEnableFunctionButtons();
	}

	/**
	 * 	Updates the view when an event has occured
	 * 
	 * 	@param None
	 * @return None
	 */
	public void updateView() {
		view.updatePoints(game.getSunpoint());
		view.loadlayout(game.getLayout());
	}

	/**
	 * 	Checks if the game has been won or if the game is still playable
	 * 
	 * 	@param status - if the game has benn won or not
	 * @return None
	 */
	public void checkStatus(Status status) {

		if(customGame == null || customGame.isEmpty()) {
			if (status == Status.win){
				view.win(); // ask want to continue to wave?
				this.game = new Game();
				view.loadlayout(game.getLayout());
			}else if(status == Status.loose) {
				view.loose();
				this.game = new Game();
				view.loadlayout(game.getLayout());
				view.disEnableFunctionButtons();
			}
		}else {
			if (status == Status.win){
				view.waveWin();
				this.game = new Game(this.game,customGame.getWave());
				this.game.setLayoutStatus(Status.start);
				view.loadlayout(game.getLayout());
			}else if(status == Status.loose) {
				view.loose();
				this.game = new Game();
				view.loadlayout(game.getLayout());
				view.disEnableFunctionButtons();
			}
		}
	}

	/**
	 * 	Creates an instance of the controller so it can be played
	 * 
	 * 	@param String []arg - arguments the user enters
	 * @return None
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	public static void main(String[] arg) throws SAXException, IOException, ParserConfigurationException {
		//Layout layout = new Layout();
		//	
		Controller c = new Controller();
		//View v = new View(c);
		//Builder b = new Builder();
		//b.readFile();

	}
}
