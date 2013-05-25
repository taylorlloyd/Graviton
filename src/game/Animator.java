package game;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class Animator extends BGPane {
	AnimatablePane content;
	int offset = 600;
	boolean complete=false;
	public Animator(AnimatablePane content) {
		super();
		this.content = content;
		new Repainter(this);
		setLayout(new GridLayout(1,1));
		add(content);

	}
	public void paint(Graphics g) {
		g.drawImage(background, 0, 0, null);
		if(offset > 0) {
			g.translate(0,offset);
			offset -= 10;
		} else {
			if(!complete)
			{
				complete = true;
				System.out.println("Animation Complete");
			}
		}
		content.paint(g.create(), false);
	}
	public void animateOut() {
		//clear screen to background
	}

}
class Repainter extends Thread {
	Animator a;
	public Repainter(Animator a) {
		this.a = a;
		start();
	}
	public void run() {
		while(!a.complete) {
			try {
				sleep(50);
			} catch (InterruptedException e) {}
			a.repaint();
		}
	}
}