package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MetalButton extends JPanel implements MouseListener{
	Image bg;
	JLabel timer;
	boolean enabled = true;
	ArrayList<ActionListener> actionListeners;
	public MetalButton(String str) {
		actionListeners = new ArrayList<ActionListener>();
		timer = new JLabel(str);
		timer.setFont(DataManager.loadFont("text.ttf").deriveFont(22f));
		timer.setForeground(new Color(15,120,0));
		bg=DataManager.loadImage("blank.png");
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill=gbc.BOTH;
		gbc.anchor = gbc.CENTER;
		gbc.insets = new Insets(6,0,0,0);
		add(timer,gbc);
		addMouseListener(this);
	}
	public void setText(String s) {
		timer.setText(s);
	}
	public String getText() {
		return timer.getText();
	}
	public void addActionListener(ActionListener al) {
		actionListeners.add(al);
	}
	public void triggerAction() {
		ActionEvent ae = new ActionEvent(this, 0, getText());
		ae.setSource(this);
		for(ActionListener al : actionListeners.toArray(new ActionListener[0]))
			al.actionPerformed(ae);
	}
	public void paintComponent(Graphics g) {
		g.drawImage(bg, 0, 0, null);
	}
	public void paint(Graphics g) {
		super.paint(g);
		if(enabled) return;
		g.setColor(new Color(180,180,180,120));
		g.fillRect(0, 0, 174, 34);
	}
	public Dimension getSize() {
		return new Dimension(174,34);
	}
	public Dimension getMinimumSize() {
		return new Dimension(174,34);
	}
	public Dimension getMaximumSize() {
		return new Dimension(174,34);
	}
	public Dimension getPreferredSize() {
		return new Dimension(174,34);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if(enabled)
			triggerAction();
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		if(enabled)
			timer.setForeground(new Color(60,165,0));
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e) {
		timer.setForeground(new Color(15,120,0));
		repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
}
