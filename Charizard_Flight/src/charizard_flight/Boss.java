package charizard_flight;

public class Boss extends Thread {
	
	private boolean meteor, hyperbeam, thunder, removeThunder, protect;
	private boolean start = false;
	private boolean megaRayquaza = false;
	private int thunderX;
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			while (start) {
				meteor = true;
				protect = false;
				try {
					Thread.sleep(7000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				meteor = false;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hyperbeam = true;
				protect = true;
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				hyperbeam = false;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (megaRayquaza) {
					thunderX = 900;
					for (int i = 0; i < 5; i++) {
						thunder = true;
						try {
							Thread.sleep(500);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						thunder = false;
						removeThunder = true;
						thunderX -= 200;
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						removeThunder = false;
					}
				}
			}
		}
	}

	public int getThunderX() {
		return thunderX;
	}

	public boolean isMeteor() {
		return meteor;
	}

	public boolean isHyperbeam() {
		return hyperbeam;
	}

	public boolean isThunder() {
		return thunder;
	}

	public boolean isRemoveThunder() {
		return removeThunder;
	}
	
	public boolean isProtect() {
		return protect;
	}
	
	public void start(boolean start) {
		this.start = start;
	}

	public void setMegaRayquaza(boolean megaRayquaza) {
		this.megaRayquaza = megaRayquaza;
	}

}
