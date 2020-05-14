package charizard_flight;

public class Player_attack {
	int x;
	int y;
	int attack;
	
	Player_attack(int x, int y, int attack) {
		this.x = x;
		this.y = y;
		this.attack = attack;
	}
	public void move() {
		x += 15;
	}
}
