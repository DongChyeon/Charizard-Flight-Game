package charizard_flight;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Game extends Thread{
	private Image player;
	private Image fire;
	private Image shield = new ImageIcon(Main.class.getResource("../images/shield.png")).getImage();
	private Image backgroundImage = new ImageIcon(Main.class.getResource("../images/Game_screen.png")).getImage();
	private Image victory = new ImageIcon(Main.class.getResource("../images/victory.png")).getImage();

	private int playerX, playerY, playerHP;
	private int playerwidth, playerheight;
	private int attack, attackspeed, speed;	// 플레이어 위치, 공격 위치, 공격력, 공격주기, 이동속도
	private int score;
	private int backgroundX;
	private int cnt; //주기
	private int spawnX, spawnY;

	private boolean up, down, left, right, shooting;	// 키 입력을 받기 위한 변수
	private boolean boss, meteor, hyperbeam, thunder, removeThunder, protect;	// 보스 행동 조절
	private boolean win, over;	// 쓰레드 종료

	ArrayList<Player_attack> playerAttackList = new ArrayList<Player_attack>();	// 플레이어 공격 배열
	ArrayList<Enemy> enemyList = new ArrayList<Enemy>();	// 적 배열
	ArrayList<Enemy_attack> enemyAttackList = new ArrayList<Enemy_attack>();	// 저 공격 배열

	Player_attack playerAttack;
	Enemy enemy;
	Enemy_attack enemyAttack;

	public static Boss BOSS = new Boss();

	public void playerDraw(Graphics2D g) {
		g.drawImage(player, playerX, playerY, null);	// 플레이어 그리기

		for(int i=0; i<playerAttackList.size(); i++) {
			playerAttack = (Player_attack)(playerAttackList.get(i));
			g.drawImage(fire, playerAttack.x, playerAttack.y, null);	// 플레이어 공격 그리기
		}
	}

	public void enemyDraw(Graphics2D g) {
		for(int i=0; i<enemyList.size(); i++) {
			enemy = (Enemy)(enemyList.get(i));
			switch (enemy.type) {
				case "Pigeon":
					g.setColor(Color.GREEN);
					g.fillRect(enemy.x, enemy.y-20, enemy.HP*7, 10);
					break;
				case "Dragonite":
					g.setColor(Color.GREEN);
					g.fillRect(enemy.x, enemy.y-20, enemy.HP*5, 10);
					break;
				case "Rayquaza":
					g.setColor(Color.BLUE);
					g.fillRect(enemy.x-15, enemy.y-20, 300, 30);
					g.setColor(Color.GREEN);
					g.fillRect(enemy.x-15, enemy.y-20, (int)(enemy.HP/2), 30);
					break;
				case "MegaRayquaza":
					if (protect)
						g.drawImage(shield, enemy.x-43, enemy.y-40, null);
					g.setColor(Color.BLUE);
					g.fillRect(enemy.x-15, enemy.y-20, (int)(enemy.HP/6), 30);
					break;
			}
			g.drawImage(enemy.EnemyImage, enemy.x, enemy.y, null);
			enemy.move();
			if(enemy.x < 0) {
				enemyList.remove(enemyList.get(i));
				i--;
			}
		}
		for(int j=0; j<enemyAttackList.size(); j++) {
			enemyAttack = (Enemy_attack)(enemyAttackList.get(j));
			g.drawImage(enemyAttack.EnemyAttackImage, enemyAttack.x, enemyAttack.y, null);
		}
	}

	public void backgroundDraw(Graphics2D g) {
		g.drawImage(backgroundImage, backgroundX, 0, null);
		backgroundX--;
		if(backgroundX < -760)	// backgroundX = 그림 가로픽셀 +1280
			g.drawImage(backgroundImage, Main.SCREEN_WIDTH-((backgroundX*-1)-760), 0, null);
		if(backgroundX == -2040)	// -(그림 가로길이)
			backgroundX=0;
	}

	public void gameInfoDraw(Graphics g) {
		if(win) {
			g.drawImage(victory, 440, 305, null);
		}
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : "+score, 40, 90);
		g.drawString("HP : ", 40, 140);
		g.setColor(Color.RED);
		g.fillRect(140, 110, 300, 40);
		g.setColor(Color.GREEN);
		g.fillRect(140, 110, playerHP*3, 40);
	}

	@Override
	public void run() {
		Charizard_flight.audio.PlayLoop("src/audio/backgroundmusic.wav");
		BOSS.start();
		Init();	// 초기화
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			while (!over && !win) {
				KeyProcess();
				EnemySpawnProcess();
				playerAttackprocess();
				EnemyAttackprocess();
				Crashcheck();
				try {
					Thread.sleep(20);	// 0.02초간의 쓰레드 슬립
					cnt++;
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	public void Init() {
		player = new ImageIcon(Main.class.getResource("../images/player.png")).getImage();
		fire = new ImageIcon(Main.class.getResource("../images/charizard_attack.png")).getImage();
		backgroundImage = new ImageIcon(Main.class.getResource("../images/Game_screen.png")).getImage();

		enemyList.clear();
		playerAttackList.clear();
		enemyAttackList.clear();

		backgroundX = 0;
		playerX = 10;
		playerY = 300;
		playerHP = 100;
		playerwidth = 230;
		playerheight = 125;
		attack = 5;
		attackspeed = 12;
		speed = 7;
		score = 0;
		cnt = 0;
		left = false;
		right = false;
		up = false;
		down = false;
		shooting = false;
		boss = false;
		BOSS.start(false);
		BOSS.setMegaRayquaza(false);
		win = false;
		over = false;

		try {
			Thread.sleep(1000);	// 1초 대기
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}	// 게임 초기화 메소드

	private void KeyProcess() {
		if (up && playerY-speed > 30) {
			playerY-=speed;
		}
		if (down && playerY+speed+playerheight < 720) {
			playerY+=speed;
		}
		if (left && playerX-speed > 0) {
			playerX-=speed;
		}
		if (right && playerX+speed+playerwidth < 1280) {
			playerX+=speed;
		}
		if (shooting) {
			if (cnt%attackspeed == 0) {	// 안에 있는 숫자가 작을수록 공격주기가 짧음
				if (attack == 10)
					playerAttack = new Player_attack(playerX+150, playerY+70, attack);
				else
					playerAttack = new Player_attack(playerX+200, playerY+30, attack);
				playerAttackList.add(playerAttack);
			}
			else
				return;
		}
	}	// 키 입력 받기 메소드

	private void MegaEvolution() {
		int x = playerX;
		int y = playerY;
		Charizard_flight.audio.PlaySound("src/audio/charizard-megax.wav");
		player = new ImageIcon(Main.class.getResource("../images/megastone.png")).getImage();
		playerwidth = 185;
		playerheight = 100;
		playerAttackList.clear();
		try {
			Thread.sleep(1000);	// 1초 대기
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
		playerX = x;
		playerY = y;
		player = new ImageIcon(Main.class.getResource("../images/Mega_player.png")).getImage();
		fire = new ImageIcon(Main.class.getResource("../images/Mega_charizard_attack.png")).getImage();
		attack = 10;
		attackspeed = 6;
		speed = 10;
	}

	private void EnemySpawnProcess() {
		if (score >= 15000 && !boss) {
			enemyList.clear();
			Charizard_flight.audio.PlaySound("src/audio/RayquazaRoar.wav");
			enemy = new Enemy(900, 225, 600, "Rayquaza");
			enemyList.add(enemy);
			enemyAttackList.clear();
			playerAttackList.clear();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			boss = true;
			BOSS.start(boss);
			backgroundImage = new ImageIcon(Main.class.getResource("../images/Game_screen2.png")).getImage();
		}	// 보스 생성

		else if (!boss && cnt!=0) {
			if (cnt%300 == 0) {
				for (int i=0; i<3; i++) {
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = (int)(Math.random()*591)+30;
					enemy = new Enemy(spawnX, spawnY, 10, "Pigeon");
					enemyList.add(enemy);
				}
			}
			if (cnt%500 == 0) {
				Charizard_flight.audio.PlaySound("src/audio/meteor_falling.wav");
				for (int i=0; i<2; i++) {
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = (int)(Math.random()*591)+30;
					enemy = new Enemy(spawnX, spawnY, 50, "Meteorite");
					enemyList.add(enemy);
				}
			}
			if (cnt%700 == 0) {
				spawnX = (int)(Math.random()*181)+1000;
				spawnY = (int)(Math.random()*591)+30;
				enemy = new Enemy(spawnX, spawnY, 20, "Dragonite");
				enemyList.add(enemy);
			}
		}
	}

	private void EnemyAttackprocess() {
		if(boss) {
			meteor = (BOSS.isMeteor());
			if(!hyperbeam && BOSS.isHyperbeam())
				Charizard_flight.audio.PlaySound("src/audio/HyperBeam.wav");
			hyperbeam = (BOSS.isHyperbeam());
			if(!thunder && BOSS.isThunder())
				Charizard_flight.audio.PlaySound("src/audio/thunder.wav");
			thunder = (BOSS.isThunder());
			removeThunder = (BOSS.isRemoveThunder());
			protect = (BOSS.isProtect());
		}

		for(int i=0; i<enemyList.size(); i++) {
			if (cnt%100 == 0) {
				enemy = (Enemy)(enemyList.get(i));
				switch (enemy.type) {
					case "Pigeon":
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y+25, "PigeonAttack");
						enemyAttackList.add(enemyAttack);
						break;
					case "Dragonite":
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y, "DragoniteAttack1");
						enemyAttackList.add(enemyAttack);
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y+50, "DragoniteAttack2");
						enemyAttackList.add(enemyAttack);
						enemyAttack = new Enemy_attack(enemy.x-30, enemy.y+100, "DragoniteAttack3");
						enemyAttackList.add(enemyAttack);
						break;
				}
			}

			if (boss) {
				if (enemy.type == "Rayquaza" && meteor == true && cnt%60 == 0) {
					Charizard_flight.audio.PlaySound("src/audio/meteor_falling.wav");
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = playerY;
					enemyAttack = new Enemy_attack(spawnX, spawnY, "DracoMeteor");
					enemyAttackList.add(enemyAttack);
				}
				else if (enemy.type == "MegaRayquaza" && meteor == true && cnt%40 == 0) {
					Charizard_flight.audio.PlaySound("src/audio/meteor_falling.wav");
					spawnX = (int)(Math.random()*181)+1000;
					spawnY = playerY;
					enemyAttack = new Enemy_attack(spawnX, spawnY, "DracoMeteor");
					enemyAttackList.add(enemyAttack);
				}
				else if(enemy.type == "Rayquaza" && hyperbeam && cnt%2 == 0) {
					enemyAttack = new Enemy_attack(enemy.x-100, enemy.y+100, "Hyperbeam");
					enemyAttackList.add(enemyAttack);
				}
				else if(enemy.type == "MegaRayquaza" && hyperbeam && cnt%2 == 0) {
					enemyAttack = new Enemy_attack(enemy.x-100, enemy.y+100, "MegaHyperbeam");
					enemyAttackList.add(enemyAttack);
				}
				else if (enemy.type == "MegaRayquaza" && thunder == true && cnt%5 == 0) {
					spawnX = BOSS.getThunderX();
					enemyAttack = new Enemy_attack(spawnX, 30, "Thunder");
					enemyAttackList.add(enemyAttack);
				}
			}	// 보스 행동 처리
		}

		for(int j=0; j<enemyAttackList.size(); j++) {
			enemyAttack = (Enemy_attack)(enemyAttackList.get(j));
			enemyAttack.fire();

			if(removeThunder && enemyAttack.type == "Thunder") {
				enemyAttackList.remove(enemyAttackList.get(j));
			}

			if(enemyAttack.x < playerX+playerwidth && playerX < enemyAttack.x + enemyAttack.width && enemyAttack.y + enemyAttack.height > (playerY + 50) && enemyAttack.y < (playerY + 50) + (playerheight - 50)) {
				Charizard_flight.audio.PlaySound("src/audio/hitten_sound.wav");
				playerHP -= enemyAttack.attack;
				enemyAttackList.remove(enemyAttackList.get(j));
			}
			if(enemyAttack.x < 0) {
				enemyAttackList.remove(enemyAttackList.get(j));
			}
		}

		if (playerHP <= 0) {
			enemyList.clear();
			playerAttackList.clear();
			enemyAttackList.clear();
			over = true;
			Charizard_flight.gameOver();
		}
	}

	private void playerAttackprocess() {
		try {
			for(int i=0; i<playerAttackList.size(); i++) {
				playerAttack = (Player_attack)(playerAttackList.get(i));
				playerAttack.move();
				for(int j=0; j<enemyList.size(); j++) {
					enemy = (Enemy)(enemyList.get(j));
					if (playerAttack.x + 50 > enemy.x && playerAttack.x + 50 < enemy.x + enemy.width && playerAttack.y + 50 > enemy.y && playerAttack.y < enemy.y+enemy.height) {
						if (enemy.type != "MegaRayquaza" || !protect)
							enemy.HP -= attack;
						playerAttackList.remove(playerAttackList.get(i));
						if (enemy.HP <= 0) {
							Charizard_flight.audio.PlaySound("src/audio/Ember.wav");
							switch (enemy.type) {
								case "Pigeon":
									score += 1000;
									enemyList.remove(enemyList.get(j));
									j--;
									break;
								case "Dragonite":
									score += 2000;
									enemyList.remove(enemyList.get(j));
									j--;
									break;
								case "Rayquaza":
									score += 20000;
									Charizard_flight.audio.PlaySound("src/audio/RayquazaRoar.wav");
									enemyAttackList.clear();
									try {
										Thread.sleep(2000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									enemyList.remove(enemyList.get(j));
									j--;
									enemy = new Enemy(900, 225, 1800, "MegaRayquaza");
									enemyList.add(enemy);
									Charizard_flight.audio.PlaySound("src/audio/charizard-megax.wav");
									MegaEvolution();
									BOSS.setMegaRayquaza(true);
									break;
								case "MegaRayquaza":
									score += 30000;
									enemyList.remove(enemyList.get(j));
									j--;
									win = true;
							}
						}
					}
				}
				if(playerAttack.x > 1240) {
					playerAttackList.remove(playerAttackList.get(i)); // 플레이어 미사일 지우기
					i--;
				}
			}
		} catch (Exception e) {}
	}

	private void Crashcheck() {
		for(int i=0; i<enemyList.size(); i++) {
			enemy = (Enemy)(enemyList.get(i));
			if (playerX + playerwidth > enemy.x && enemy.x+enemy.width > playerX && (playerY + 50) + (playerheight - 50) > enemy.y && enemy.y + enemy.height > (playerY + 50)) {
				switch (enemy.type) {
					case "Pigeon":
						playerHP -= 10;
						Charizard_flight.audio.PlaySound("src/audio/hitten_sound.wav");
						enemyList.remove(enemyList.get(i));
						i--;
						break;
					case "Meteorite":
						playerHP -= 30;
						Charizard_flight.audio.PlaySound("src/audio/hitten_sound.wav");
						enemyList.remove(enemyList.get(i));
						i--;
						break;
					case "Rayquaza":
					case "MegaRayquaza":
						playerHP -= 1;
						Charizard_flight.audio.PlaySound("src/audio/hitten_sound.wav");
						break;
				}
			}
		}
	}

	public void true_Up() {
		if(up)
			return;
		up = true;
	}

	public void true_Down() {
		if(down)
			return;
		down = true;
	}

	public void true_Left() {
		if(left)
			return;
		left = true;
	}

	public void true_Right() {
		if(right)
			return;
		right = true;
	}

	public void true_Shooting() {
		if(shooting)
			return;
		shooting = true;
	}

	public void false_Up() {
		if(!up)
			return;
		up = false;
	}

	public void false_Down() {
		if(!down)
			return;
		down = false;
	}

	public void false_Left() {
		if(!left)
			return;
		left = false;
	}

	public void false_Right() {
		if(!right)
			return;
		right = false;
	}

	public void false_Shooting() {
		if(!shooting)
			return;
		shooting = false;
	}

	public boolean isWin() {
		return win;
	}

	public boolean isOver() {
		return over;
	}

}