import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.script.AbstractScript;	
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.NPC;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.script.Category;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.randoms.RandomEvent;

@ScriptManifest(author = "UltimaJ", name = "FishingScript", version = 1.1, description = "Simple Fishing Script", category = Category.FISHING)
	public class FishingBot extends AbstractScript {
		static private boolean StartScript = false;
		private boolean Banking = false;
		private String EquipmentValue;
		private String LocationValue;
		
		public boolean getStartScript() {
			return StartScript;
		}
		
		public void setStartScript(boolean Value) {
			StartScript = Value;
		}
		
		public boolean getBanking() {
			return Banking;
		}
		
		public void setBanking(boolean Value) {
			Banking = Value;
		}
		
		public String getEquipmentValue() {
			return EquipmentValue;
		}
		
		public void setEquipmentValue(String Value) {
			EquipmentValue = Value;
		}
		
		public String getLocationValue() {
			return LocationValue;
		}
		
		public void setLocationValue(String Value) {
			LocationValue = Value;
		}
		
		public void onStart() {
			FishingBotGUI GUI = new FishingBotGUI();
			GUI.setVisible(true);
			log("Welcome to FishingBot by James");
			log("Report any issues or bugs to the developer");
		}
	
		public void onExit() {
			log("Hope you enjoyed!");
		}
		
		private enum State {
			FISH, BANK_DRAYNOR, BANK_SEERS, BANK_EDGEVILLE, DROP, WAIT, COMBAT
		}
		
		private State getState() {
			if (!getInventory().isFull()) {
				return State.FISH;
			}
			
			if (getInventory().isFull() && Banking == true && LocationValue.equals("Draynor Village")) {
				return State.BANK_DRAYNOR;
			}
			
			if (getInventory().isFull() && Banking == true && LocationValue.equals("Seers Village")) {
				return State.BANK_SEERS;
			}
			
			if (getInventory().isFull() && Banking == true && LocationValue.equals("Barbarian Village")) {
				return State.BANK_EDGEVILLE;
			}
			
			if (getInventory().isFull() && Banking == false) {
				return State.DROP;
			}
			
			if (getGameObjects().closest("Fishing Spot") == null) {
				return State.WAIT;
			}
			
			if (getLocalPlayer().isInCombat()) {
				return State.COMBAT;
			}
			
			return null;
		}
	
		@Override
		public int onLoop() {
			if (StartScript) {
				if (!getInventory().contains(EquipmentValue)) {
					getBank().getClosestBankLocation();
					
					if (EquipmentValue.equals("Fishing rod")) {
						getBank().withdraw((item) -> item != null && (item.getName().equals("Fishing rod") || item.getName().equals("Fishing bait")));
					}
					
					else {
						getBank().withdraw(EquipmentValue);
					}
					
					getBank().close();
				}
				
				switch(getState()) {
					
					case FISH:
						log("Fishing");
						if (getBank().isOpen()) {
							getBank().close();
						}
						
						NPC fishingspot = getNpcs().closest("Fishing spot");
						
						if (fishingspot != null && EquipmentValue.equals("Small fishing net")) {
							if (getDialogues().inDialogue()) {
								getDialogues().clickContinue();
							}
	
							fishingspot.interact("Small Net");
							sleepWhile(fishingspot::exists, Calculations.random(5000, 10000));
						} // Fishing case for small fishing net
						
						if (fishingspot != null && EquipmentValue.equals("Fishing rod")) {
							if (getDialogues().inDialogue()) {
								getDialogues().clickContinue();
							}
	
							fishingspot.interact("Bait");
							sleepWhile(fishingspot::exists, Calculations.random(5000, 10000));
						} // Fishing case for fishing rod
						
						break;
					
					case BANK_DRAYNOR:
						log("Banking");
						getBank().open(BankLocation.DRAYNOR);
						
						if (getBank().isOpen() && EquipmentValue.equals("Small fishing net")) {
							getBank().depositAllExcept(EquipmentValue);
							sleepUntil(() -> getBank().isOpen(), 2000);
						} // Banking case for small fishing net
						
						if (getBank().isOpen() && EquipmentValue.equals("Fishing rod")) {
							getBank().depositAllExcept((item) -> item != null && (item.getName().equals("Fishing rod") || item.getName().equals("Fishing bait")));
							sleepUntil(() -> getBank().isOpen(), 2000);
						} // Banking case for fishing rod
						
						getBank().close();
						break;
						
					case BANK_SEERS:
						log("Banking");
						getBank().open(BankLocation.SEERS);
						
						if (getBank().isOpen() && EquipmentValue.equals("Small fishing net")) {
							getBank().depositAllExcept(EquipmentValue);
							sleepUntil(() -> getBank().isOpen(), 2000);
						} // Banking case for small fishing net
						
						if (getBank().isOpen() && EquipmentValue.equals("Fishing rod")) {
							getBank().depositAllExcept((item) -> item != null && (item.getName().equals("Fishing rod") || item.getName().equals("Fishing bait")));
							sleepUntil(() -> getBank().isOpen(), 2000);
						} // Banking case for fishing rod
						
						getBank().close();
						break;
						
					case BANK_EDGEVILLE:
						log("Banking");
						getBank().open(BankLocation.EDGEVILLE);
						
						if (getBank().isOpen() && EquipmentValue.equals("Small fishing net")) {
							getBank().depositAllExcept(EquipmentValue);
							sleepUntil(() -> getBank().isOpen(), 2000);
						} // Banking case for small fishing net
						
						if (getBank().isOpen() && EquipmentValue.equals("Fishing rod")) {
							getBank().depositAllExcept((item) -> item != null && (item.getName().equals("Fishing rod") || item.getName().equals("Fishing bait")));
							sleepUntil(() -> getBank().isOpen(), 2000);
						} // Banking case for fishing rod
						
						getBank().close();
						break;
					
					case DROP:
						log("Dropping");
						if (EquipmentValue.equals("Small fishing net")) {
							getInventory().dropAllExcept(EquipmentValue);
						} // Dropping case for small fishing net
						
						if (EquipmentValue.equals("Fishing rod")) {
							//getInventory().dropAll("Fishing rod");
							getInventory().dropAll((item) -> item != null && (item.getName().equals("Fishing rod") || item.getName().equals("Fishing bait")));
						} // Dropping case for small fishing net
						
						break;
					
					case COMBAT:
						log("In Combat");
						getBank().getClosestBankLocation(); // run to bank which is the safe spot
						sleepUntil(() -> getLocalPlayer().isInCombat(), 10000);
						break;
					
					case WAIT:
						log("Waiting for fishing spot to return");
						getBank().getClosestBankLocation(); // wait in bank until fishing spot returns
						break;
				}
			
			}							
			return Calculations.random(300, 400); // Script runs on a random generate number between 500 and 600
		}
	}