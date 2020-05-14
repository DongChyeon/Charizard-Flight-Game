package charizard_flight;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Charizard_flight extends JFrame{
	private Image screenImage;
	private Graphics screenGraphic;
	private Image backgroundImage = new ImageIcon(Main.class.getResource("../images/Main_screen.png")).getImage();
	private Image Icon = new ImageIcon(Main.class.getResource("../images/Icon.png")).getImage();
	
	private ImageIcon startButtonImage = new ImageIcon(Main.class.getResource("../images/startButton.png"));
	private ImageIcon startButton_PImage = new ImageIcon(Main.class.getResource("../images/startButton_pressed.png"));
	private ImageIcon explainButtonImage = new ImageIcon(Main.class.getResource("../images/explainButton.png"));
	private ImageIcon explainButton_PImage = new ImageIcon(Main.class.getResource("../images/explainButton_pressed.png"));
	private static ImageIcon retryButtonImage = new ImageIcon(Main.class.getResource("../images/retryButton.png"));
	private ImageIcon retryButton_PImage = new ImageIcon(Main.class.getResource("../images/retryButton_pressed.png"));
	
	private int mouseX, mouseY;

	private JLabel menuBar = new JLabel(new ImageIcon(Main.class.getResource("../images/menuBar.png")));
	private JButton quitButton = new JButton(new ImageIcon(Main.class.getResource("../images/quitButton.png")));
	private JButton startButton = new JButton(startButtonImage);
	private JButton explainButton = new JButton(explainButtonImage);
	private static JButton retryButton = new JButton(retryButtonImage);
	
	private boolean isGameScreen = false;
	
	public static Game game = new Game();
	public static Audio audio = new Audio();
	
	public Charizard_flight() {
		setTitle("CHARIZARD FLIGHT");
		setUndecorated(true);
		setSize(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		setResizable(false);			
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		setIconImage(Icon);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getXOnScreen();
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		quitButton.setBounds(1250, 00, 30, 30);
		quitButton.setBorderPainted(false);
		quitButton.setContentAreaFilled(false);
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				System.exit(0);
			}
		});
		startButton.setPressedIcon(startButton_PImage);
		startButton.setBounds(800, 420, 400, 80);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playGame();
			}
		});
		explainButton.setPressedIcon(explainButton_PImage);
		explainButton.setBounds(800, 530, 400, 80);
		explainButton.setBorderPainted(false);
		explainButton.setContentAreaFilled(false);
		explainButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				backgroundImage = new ImageIcon(Main.class.getResource("../images/Explain_screen.png")).getImage();
				startButton.setBounds(440, 530, 400, 80);
				explainButton.setVisible(false);
				//게임 설명화면
			}
		});
		retryButton.setPressedIcon(retryButton_PImage);
		retryButton.setBounds(440, 335, 400, 80);
		retryButton.setBorderPainted(false);
		retryButton.setContentAreaFilled(false);
		retryButton.setVisible(false);
		retryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Charizard_flight.game.Init();
				retryButton.setVisible(false);
				setFocusable(true);
			}
		});
		add(quitButton);
		add(menuBar);
		add(startButton);
		add(explainButton);
		add(retryButton);
	}
	
	public void playGame() {
		addKeyListener(new KeyListener());
		setFocusable(true);
		isGameScreen = true;
		startButton.setVisible(false);
		explainButton.setVisible(false);
		game.start();
	}
	
	public static void gameOver() {
		retryButton.setVisible(true);
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(Main.SCREEN_WIDTH, Main.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D) screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics2D g) {
		if(!isGameScreen)
			g.drawImage(backgroundImage, 0, 0, null);
		if(isGameScreen) {
			Charizard_flight.game.backgroundDraw(g);
			Charizard_flight.game.enemyDraw(g);
			Charizard_flight.game.playerDraw(g);
			Charizard_flight.game.gameInfoDraw(g);
		}
		paintComponents(g);
		this.repaint();
	}

}
