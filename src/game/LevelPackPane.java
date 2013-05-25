package game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class LevelPackPane extends AnimatablePane {
	public static Image background;
	boolean back=true;
	public LevelPackPane(LevelPack pack) {
		if(background == null)
			background = DataManager.loadImage("horizon.png");
		setBackground(new Color(0,0,0,0));
		setLayout(new GridBagLayout());
		InternalPane buttonlist = new InternalPane();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill=gbc.HORIZONTAL;
		gbc.weightx=1;
		gbc.insets=new Insets(5, 10, 5, 10);//tlbr
		for(int i=0;i<pack.getLevelCount();i++) {
			buttonlist.add(new LevelButton(pack.getLevel(i)),gbc);
			gbc.gridy++;
		}
		JScrollPane jsp = new JScrollPane(buttonlist);
		jsp.setBorder(BorderFactory.createEmptyBorder());
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.fill=gbc.NONE;
		gbc.insets=new Insets(0, 0, 0, 0);
		add(new TitlePane(pack),gbc);
		gbc.gridy=1;
		gbc.fill=gbc.VERTICAL;
		gbc.weightx=1;
		gbc.weighty=1;
		add(jsp,gbc);
		
	}
	public void paintComponent(Graphics g) {
		paintComponent(g,back);
	}
	public void paint(Graphics g, boolean paintBackground){
		back=paintBackground;
		paint(g);
		back=true;
	}
	@Override
	public void paintComponent(Graphics g, boolean paintBackground) {
		if(paintBackground) 
			g.drawImage(background, 0, 0, null);
	}
}
class InternalPane extends JPanel {
	public InternalPane() {
		super();
		setBackground(new Color(0,0,0,0));
		setLayout(new GridBagLayout());
	}
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.width =400;
		return d;
	}
	public Dimension getMinimumSize() {
		Dimension d = super.getMinimumSize();
		d.width =400;
		return d;
	}
	public Dimension getMaximumSize() {
		Dimension d = super.getMaximumSize();
		d.width =400;
		return d;
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		//g2.drawImage(LevelPackPane.background, -1*this.getX(), -1*this.getY(), null);
		g2.setColor(new Color(0,0,0,160));
		g2.fillRoundRect(6, -25, getWidth()-12, getHeight()+25, 14, 14);
		g2.setColor(new Color(255,255,255,80));
		g2.setStroke(new BasicStroke(6));
		g2.drawRoundRect(3, -28, getWidth()-6, getHeight()+25, 14, 14);
	}
}
class TitlePane extends JPanel implements ActionListener{
	public TitlePane(LevelPack pack) {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		Font font=DataManager.loadFont("text.ttf");
		JButton back = new JButton(new ImageIcon(DataManager.loadImage("back.png")));
		back.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		back.setRolloverIcon(new ImageIcon(DataManager.loadImage("back_over.png")));
		back.addActionListener(this);
		back.setActionCommand("back");
		back.setBorderPainted(false);
		JLabel name = new JLabel(pack.name.substring(0,1).toUpperCase() + pack.name.substring(1).toLowerCase());
		name.setFont(font.deriveFont((float)30));
		name.setForeground(new Color(255,255,255,80));
		JLabel complete = new JLabel("Complete: "+GameState.highestCompleteLevel(pack.name)+"/"+pack.getLevelCount());
		complete.setFont(font.deriveFont((float)20));
		complete.setForeground(new Color(255,255,255,80));
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.insets=new Insets(10,0,0,0);
		add(back, gbc);
		gbc.gridx=1;
		gbc.insets=new Insets(10, 30, 0, 30);//tlbr
		add(name, gbc);
		gbc.insets=new Insets(10, 0, 0, 0);//tlbr
		gbc.gridx=2;
		add(complete, gbc);
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(0,0,0,160));
		g2.fillRoundRect(6, 6, getWidth()-12, getHeight()+25, 14, 14);
		g2.setColor(new Color(255,255,255,80));
		g2.setStroke(new BasicStroke(6));
		g2.drawRoundRect(3, 3, getWidth()-6, getHeight()+25, 14, 14);
		
	}
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.width =400;
		return d;
	}
	public Dimension getMinimumSize() {
		Dimension d = super.getMinimumSize();
		d.width =400;
		return d;
	}
	public Dimension getMaximumSize() {
		Dimension d = super.getMaximumSize();
		d.width =400;
		return d;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Window.current.toPane(Window.current.levelPane);
	}
}