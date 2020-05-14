package charizard_flight;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	
	private Clip clip;
	
	public void PlaySound(String PathName) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(PathName);
			AudioInputStream audioStream =
				AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
			clip.setFramePosition(0);
			clip.start();
		}
		catch (LineUnavailableException e) { e.printStackTrace(); }
		catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}	// ����� ���
	
	public void PlayLoop(String PathName) {
		try {
			clip = AudioSystem.getClip();
			File audioFile = new File(PathName);
			AudioInputStream audioStream =
				AudioSystem.getAudioInputStream(audioFile);
			clip.open(audioStream);
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		}
		catch (LineUnavailableException e) { e.printStackTrace(); }
		catch (UnsupportedAudioFileException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}	// ���ѷ���
}
