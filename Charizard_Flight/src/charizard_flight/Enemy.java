package charizard_flight;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy {
	int x, y, width, height, HP;
	String type;
	Image EnemyImage;
	
	Enemy(int x, int y, int HP, String type) {
		switch(type){
			case "Pigeon" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/Pigeon.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 80;
				this.height = 86;
				this.HP = HP;
				this.type = "Pigeon";
				break;
			case "Meteorite" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/Meteor.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 300;
				this.height = 100;
				this.HP = HP;
				this.type = "Meteorite";
				break;
			case "Dragonite" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/Dragonite.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 113;
				this.height = 150;
				this.HP = HP;
				this.type = "Dragonite";
				break;
			case "Rayquaza" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/Rayquaza.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 262;
				this.height = 300;
				this.HP = HP;
				this.type = type;
				break;
			case "MegaRayquaza" :
				this.EnemyImage = new ImageIcon(Main.class.getResource("../images/MegaRayquaza.png")).getImage();
				this.x = x;
				this.y = y;
				this.width = 293;
				this.height = 300;
				this.HP = HP;
				this.type = type;
				break;
		}
	}
	
	public void move() {
		switch (type) {
			case "Pigeon":
			case "Dragonite":
				this.x-=2;
				break;
			case "Meteorite":
				this.x-=10;
				break;
			case "Rayquaza":
				break;
			case "MegaRayquaza":
				break;
			default:
				break;
		}
	}
}
