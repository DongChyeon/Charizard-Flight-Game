package charizard_flight;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy_attack {
	int x, y, attack, width, height;
	String type;
	Image EnemyAttackImage;
	
	Enemy_attack(int x, int y, String type) {
		// 미사일타입 1일시 위쪽 대각선, 2일시 직진, 3일시 아래쪽 대각선
		switch (type) {
			case "PigeonAttack":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/PigeonAttack.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 40;
				this.height = 30;
				this.attack = 10;
				this.type = "PigeonAttack";
				break;
			case "DragoniteAttack1":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/DragoniteAttack.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 37;
				this.height = 50;
				this.attack = 10;
				this.type = "DragoniteAttack1";
				break;
			case "DragoniteAttack2":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/DragoniteAttack.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 37;
				this.height = 50;
				this.attack = 10;
				this.type = "DragoniteAttack2";
				break;
			case "DragoniteAttack3":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/DragoniteAttack.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 37;
				this.height = 50;
				this.attack = 10;
				this.type = "DragoniteAttack3";
				break;
			case "Hyperbeam":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/Hyperbeam.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 100;
				this.height = 100;
				this.attack = 3;
				this.type = "Hyperbeam";
				break;
			case "MegaHyperbeam":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/MegaHyperbeam.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 100;
				this.height = 100;
				this.attack = 5;
				this.type = "MegaHyperbeam";
				break;
			case "DracoMeteor":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/Meteor.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 300;
				this.height = 100;
				this.attack = 20;
				this.type = "DracoMeteor";
				break;
			case "Thunder":
				this.EnemyAttackImage = new ImageIcon(Main.class.getResource("../images/Thunder.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 164;
				this.height = 690;
				this.attack = 10;
				this.type = "Thunder";
				break;
		}
	}
	public void fire() {
		switch(type) {
			case "PigeonAttack": 
				this.x -= 7;
				break;
			case "DragoniteAttack2": 
				this.x -= 5;
				break;
			case "DragoniteAttack1":
				this.x -= 5;
				this.y -= 2;
				break;
			case "DragoniteAttack3":
				this.x -= 5;
				this.y += 2;
				break;
			case "Hyperbeam":
				this.x -= 10;
				break;
			case "MegaHyperbeam":
			case "DracoMeteor":
				this.x -= 15;
				break;
		}
	}
}
