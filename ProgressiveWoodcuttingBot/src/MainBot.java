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
	
	@ScriptManifest(author = "UltimaJ", name = "ProgressiveWoodcutter", version = 1.2, description = "Progressive WoodcutterBot that upgrades axes and trees up to willow", category = Category.WOODCUTTING)
	public class MainBot extends AbstractScript {
		private String TreeValue;
		private static final Tile Oak_Tile = new Tile(3218, 3205, 0);
	
		public void onStart() {
			log("Welcome to WoodcutterBot by James");
		}
	
		public void onExit() {
			log("Hope you enjoyed!");
		}
		
		public void GetTreeValue() {
			if (getSkills().getRealLevel(Skill.WOODCUTTING) < 15){
				TreeValue = "Tree";
			}
			
			if (getSkills().getRealLevel(Skill.WOODCUTTING) >= 15 && getSkills().getRealLevel(Skill.WOODCUTTING) < 30) {
				TreeValue = "Oak";
			}
			
			if (getSkills().getRealLevel(Skill.WOODCUTTING) >= 30 && getSkills().getRealLevel(Skill.WOODCUTTING) < 99) {
				TreeValue = "Willow";
			}
			
			if (getSkills().getRealLevel(Skill.WOODCUTTING) == 99) {
				//go get skill cape
			}
		}
		
		public void getAxe() {
			
			while (!getInventory().contains("Bronze axe")) {
				getBank().open(BankLocation.DRAYNOR);
				if (getLocalPlayer().isStandingStill()) {
					GameObject Booth = getGameObjects().closest("Bank booth");
					
					if (Booth != null) {
						Booth.interact("Bank");
						if (getBank().isOpen()) {
							
							if (getInventory().isFull()) {
								getBank().depositAllItems();
							}
							
							if (getBank().contains("Bronze axe") && getSkills().getRealLevel(Skill.WOODCUTTING) < 6) {
								getBank().withdraw("Bronze axe");
								getBank().close();
							}
							
							else if (getBank().contains("Iron axe") && getSkills().getRealLevel(Skill.WOODCUTTING) < 6) {
								getBank().withdraw("Iron axe");
								getBank().close();
							}
							
							else if (getBank().contains("Steel axe") && getSkills().getRealLevel(Skill.WOODCUTTING) >= 6 && getSkills().getRealLevel(Skill.WOODCUTTING) < 21) {
								getBank().withdraw("Steel axe");
								getBank().close();
							}
							
							else if (getBank().contains("Mithril axe") && getSkills().getRealLevel(Skill.WOODCUTTING) >= 21 && getSkills().getRealLevel(Skill.WOODCUTTING) < 31) {
								getBank().withdraw("Mithril axe");
								getBank().close();
							}
							
							else if (getBank().contains("Adamant axe") && getSkills().getRealLevel(Skill.WOODCUTTING) >= 31 && getSkills().getRealLevel(Skill.WOODCUTTING) < 41) {
								getBank().withdraw("Adamant axe");
								getBank().close();
							}
							
							else if (getBank().contains("Rune axe") && getSkills().getRealLevel(Skill.WOODCUTTING) >= 41 && getSkills().getRealLevel(Skill.WOODCUTTING) < 61) {
								getBank().withdraw("Rune axe");
								getBank().close();
							}
							
							else if (getBank().contains("Dragon axe") && getSkills().getRealLevel(Skill.WOODCUTTING) >= 61) {
								getBank().withdraw("Dragon axe");
								getBank().close();
							}
							
							else if (!getBank().contains("Dragon axe") && getSkills().getRealLevel(Skill.WOODCUTTING) >= 61) {
								getBank().withdraw("Rune axe");
								getBank().close();
							}
							
							else {
								if (getClient().isLoggedIn()) {
									getRandomManager().disableSolver(RandomEvent.LOGIN);
									getRandomManager().disableSolver(RandomEvent.WELCOME_SCREEN);
									getTabs().logout();
								}
								
								log("Need to purchase an axe that corresponds to your woodcutting level in order for the script to continue");
							}
						}
					}
				}
			}
		}
		
		public void IsInventoryFull() {
			if (getInventory().isFull()) {
				getBank().open(BankLocation.DRAYNOR);
				if (getLocalPlayer().isStandingStill()) {
					GameObject Booth = getGameObjects().closest("Bank Booth");
					
					if (Booth != null) {
						Booth.interact("Bank");
						if (getBank().isOpen()) {
							
							if (getInventory().contains("Dragon Axe")) {
								getBank().depositAllExcept("Dragon Axe");
							}
							
							if (getInventory().contains("Rune Axe")) {
								getBank().depositAllExcept("Rune Axe");
							}
							
							if (getInventory().contains("Adamant Axe")) {
								getBank().depositAllExcept("Adamant Axe");
							}
							
							if (getInventory().contains("Mithril Axe")) {
								getBank().depositAllExcept("Mithril Axe");
							}
							
							if (getInventory().contains("Steel Axe")) {
								getBank().depositAllExcept("Steel Axe");
							}
							
							if (getInventory().contains("Iron Axe")) {
								getBank().depositAllExcept("Iron Axe");
							}
							
							if (getInventory().contains("Bronze Axe")) {
								getBank().depositAllExcept("Bronze Axe");
							}
							
							getBank().close();
						}
						
						else {
							
						}
					}
				}
				
			}
		}
	
		@Override
		public int onLoop() {
			
			if (getInventory().isFull()) {
				IsInventoryFull();
			}
			
			getAxe();
			GetTreeValue();
			
			GameObject tree = getGameObjects().closest(TreeValue);
			
			if (tree != null) {
				if (getDialogues().inDialogue()) {
					getDialogues().clickContinue();
				}
				while (getLocalPlayer().isInCombat()) {
					getWalking().walk(Oak_Tile);
				}
				tree.interact("Chop down");
				sleep(500,600);
				sleepUntil(() -> !getLocalPlayer().isAnimating(), 25000);
			}
										
			return Calculations.random(300, 400); // Script runs on a random generate number between 500 and 600
		}
	}