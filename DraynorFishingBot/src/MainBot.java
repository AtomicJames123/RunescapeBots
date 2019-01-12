import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.container.impl.bank.BankLocation;
import org.dreambot.api.script.AbstractScript;	
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.api.wrappers.interactive.Player;
import org.dreambot.api.script.Category;
import org.dreambot.api.methods.input.Keyboard;
import org.dreambot.api.methods.interactive.Players;
import org.dreambot.api.methods.map.Tile;
import org.dreambot.api.methods.skills.Skill;
import org.dreambot.api.randoms.RandomEvent;

@ScriptManifest(author = "UltimaJ", name = "FishingScript", version = 1.0, description = "Simple Fishing Script", category = Category.FISHING)
	public class MainBot extends AbstractScript {
	
		public void onStart() {
			log("Welcome to FishingBot by James");
			log("Report any issues or bugs to the developer");
		}
	
		public void onExit() {
			log("Hope you enjoyed!");
		}
		
		private enum State {
			FISH, BANK, WAIT, COMBAT
		}
		
		private State getState() {
			if (!getInventory().isFull()) {
				return State.FISH;
			}
			
			if (getInventory().isFull()) {
				return State.BANK;
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
			
			if (!getInventory().contains("Small fishing net")) {
				getBank().open(BankLocation.DRAYNOR);
				getBank().withdraw("Small fishing net");
				getBank().close();
			}
			
			switch(getState()) {
				
				case FISH:
					log("Fishing");
					
					GameObject fishingspot = getGameObjects().closest("Fishing spot");
					
					if (fishingspot != null) {
						if (getDialogues().inDialogue()) {
							getDialogues().clickContinue();
						}

						fishingspot.interact("Small Net");
						sleepWhile(fishingspot::exists, Calculations.random(5000, 10000));
					}
					break;
				
				case BANK:
					log("Banking");
					if (getBank().isOpen()) {
						getBank().depositAllExcept("Small fishing net");
						sleepUntil(() -> getBank().isOpen(), 2000);
					}
					getBank().close();
					break;
					
				case COMBAT:
					log("In Combat");
					getBank().open(BankLocation.DRAYNOR); // run to bank which is the safe spot
					sleepUntil(() - > getLocalPlayer().isInCombat(), 10000);
					break;
				
				case WAIT:
					log("Waiting for fishing spot to return");
					getBank().open(BankLocation.DRAYNOR); // wait in bank until fishing spot returns
					break;
			}
			
										
			return Calculations.random(300, 400); // Script runs on a random generate number between 500 and 600
		}
	}